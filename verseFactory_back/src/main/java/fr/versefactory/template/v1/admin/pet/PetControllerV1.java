package fr.versefactory.template.v1.admin.pet;

import fr.versefactory.template.v1.admin.AdminControllerV1;
import fr.versefactory.template.v1.admin.openapi.endpoint.PetsApi;
import fr.versefactory.template.v1.admin.openapi.payload.PetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PetControllerV1 extends AdminControllerV1 implements PetsApi {

    private final PetServiceV1 service;

    @Override
    public ResponseEntity<List<PetDto>> getAllPets() throws Exception {
        return ResponseEntity.ok(service.getAllPets());
    }

    @Override
    public ResponseEntity<PetDto> getPetById(UUID id) throws Exception {
        return ResponseEntity.ok(service.getPetById(id));
    }
}
