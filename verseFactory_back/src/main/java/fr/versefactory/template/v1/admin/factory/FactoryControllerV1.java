package fr.versefactory.template.v1.admin.factory;

import fr.versefactory.template.v1.admin.AdminControllerV1;
import fr.versefactory.template.v1.admin.openapi.endpoint.FactoryApi;
import fr.versefactory.template.v1.admin.openapi.payload.FactoryDto;
import fr.versefactory.template.v1.admin.openapi.payload.FactoryPetDto;
import fr.versefactory.template.v1.admin.openapi.payload.FactoryUpgradeDto;
import fr.versefactory.template.v1.admin.openapi.payload.PetDto;
import fr.versefactory.template.v1.admin.openapi.payload.AddPetToFactoryRequest;
import fr.versefactory.template.config.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class FactoryControllerV1 extends AdminControllerV1 implements FactoryApi {

    private final FactoryServiceV1 service;

    @Override
    public ResponseEntity<FactoryDto> getConnectedUserFactory() throws Exception {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return ResponseEntity.ok(service.getFactoryByUserId(userDetails.getKeycloakId()));
    }

    @Override
    public ResponseEntity<List<FactoryPetDto>> getConnectedUserFactoryPets() throws Exception {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return ResponseEntity.ok(service.getPetsByFactoryUserId(userDetails.getKeycloakId()));
    }

    @Override
    public ResponseEntity<List<FactoryUpgradeDto>> getConnectedUserFactoryUpgrades() throws Exception {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return ResponseEntity.ok(service.getUpgradesByFactoryUserId(userDetails.getKeycloakId()));
    }

    @Override
    public ResponseEntity<PetDto> addPetToConnectedUserFactory(AddPetToFactoryRequest addPetToFactoryRequest) throws Exception {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        PetDto addedPet = service.addPetToFactory(userDetails.getKeycloakId(), addPetToFactoryRequest.getPetId());
        return ResponseEntity.status(HttpStatus.CREATED).body(addedPet);
    }

    @Override
    public ResponseEntity<FactoryDto> updateConnectedUserFactoryBalance() throws Exception {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return ResponseEntity.ok(service.updateFactoryBalance(userDetails.getKeycloakId()));
    }

    @Override
    public ResponseEntity<Void> deletePetFromConnectedUserFactory(UUID id) throws Exception {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        service.deletePetFromFactory(userDetails.getKeycloakId(), id);
        return ResponseEntity.noContent().build();
    }
}
