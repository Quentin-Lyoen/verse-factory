package fr.versefactory.template.v1.admin.box;

import fr.versefactory.template.config.security.user.UserDetailsImpl;
import fr.versefactory.template.v1.admin.AdminControllerV1;
import fr.versefactory.template.v1.admin.openapi.endpoint.BoxesApi;
import fr.versefactory.template.v1.admin.openapi.payload.BoxDto;
import fr.versefactory.template.v1.admin.openapi.payload.PetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class BoxControllerV1 extends AdminControllerV1 implements BoxesApi {

    private final BoxServiceV1 service;

    @Override
    public ResponseEntity<List<BoxDto>> getAllBoxes() throws Exception {
        return ResponseEntity.ok(service.getAllBoxes());
    }

    @Override
    public ResponseEntity<PetDto> openBox(UUID id) throws Exception {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return ResponseEntity.ok(service.openBox(userDetails.getKeycloakId(), id));
    }

    @Override
    public ResponseEntity<List<PetDto>> getPetsByBoxId(UUID id) throws Exception {
        return ResponseEntity.ok(service.getPetsByBoxId(id));
    }
}

