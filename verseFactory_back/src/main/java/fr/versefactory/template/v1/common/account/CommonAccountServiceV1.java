package fr.versefactory.template.v1.common.account;

import fr.versefactory.template.storage.Tables;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonAccountServiceV1 {

    private final Keycloak keycloak;
    private final DSLContext dslContext;

    @Value("${keycloak.admin.realm}")
    private String realm;

    @Transactional
    public UUID createAccount(String name, String email, String password) {
        UsersResource usersResource = keycloak.realm(realm).users();

        UserRepresentation user = new UserRepresentation();
        user.setUsername(name);
        user.setEmail(email);
        user.setEnabled(true);
        user.setEmailVerified(true);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        credential.setTemporary(false);

        user.setCredentials(Collections.singletonList(credential));

        try (Response response = usersResource.create(user)) {
            if (response.getStatus() == 201) {
                String path = response.getLocation().getPath();
                String userId = path.substring(path.lastIndexOf('/') + 1);
                UUID keycloakId = UUID.fromString(userId);

                log.info("Created user in keycloak with ID: {}", keycloakId);

                // Add to app_user
                dslContext.insertInto(Tables.APP_USER)
                        .set(Tables.APP_USER.ID, keycloakId)
                        .set(Tables.APP_USER.USERNAME, name)
                        .execute();

                // Initialize factory
                var factoryRecord = dslContext.insertInto(Tables.FACTORY)
                        .set(Tables.FACTORY.USER_ID, keycloakId)
                        .set(Tables.FACTORY.BALANCE, BigDecimal.ZERO)
                        .returning(Tables.FACTORY.ID)
                        .fetchOne();

                if (factoryRecord != null) {
                    UUID factoryId = factoryRecord.getId();
                    dslContext.selectFrom(Tables.PET)
                            .where(Tables.PET.NAME.equalIgnoreCase("Chien"))
                            .fetchOptional()
                            .ifPresent(dogPet -> {
                                dslContext.insertInto(Tables.FACTORY_PET)
                                        .set(Tables.FACTORY_PET.FACTORY_ID, factoryId)
                                        .set(Tables.FACTORY_PET.PET_ID, dogPet.getId())
                                        .execute();
                                log.info("Assigned default pet 'Chien' (ID: {}) to factory (ID: {}) for user {}", dogPet.getId(), factoryId, keycloakId);
                            });
                }

                return keycloakId;
            } else {
                log.error("Failed to create keycloak user: status {}", response.getStatus());
                throw new RuntimeException("Failed to create Keycloak user. Status: " + response.getStatus());
            }
        }
    }
}
