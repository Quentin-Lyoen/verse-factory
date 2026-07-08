package fr.versefactory.template.v1.admin.factory;

import fr.versefactory.template.v1.core.TemplateRepositoryV1;
import fr.versefactory.template.v1.admin.factory.representations.FactoryRepresentationV1;
import fr.versefactory.template.storage.Tables;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FactoryRepositoryV1 extends TemplateRepositoryV1 {

    public Optional<FactoryRepresentationV1> findByUserId(UUID userId) {
        return dslContext.selectFrom(Tables.FACTORY)
                .where(Tables.FACTORY.USER_ID.eq(userId))
                .fetchOptional()
                .map(FactoryRepresentationV1::new);
    }

    public Optional<FactoryRepresentationV1> findById(UUID id) {
        return dslContext.selectFrom(Tables.FACTORY)
                .where(Tables.FACTORY.ID.eq(id))
                .fetchOptional()
                .map(FactoryRepresentationV1::new);
    }

    public void updateBalance(UUID id, BigDecimal balance) {
        dslContext.update(Tables.FACTORY)
                .set(Tables.FACTORY.BALANCE, balance)
                .set(Tables.FACTORY.LAST_UPDATED_AT, LocalDateTime.now())
                .where(Tables.FACTORY.ID.eq(id))
                .execute();
    }
}
