package fr.versefactory.template.v1.admin.pet;

import fr.versefactory.template.v1.core.TemplateServiceV1;
import fr.versefactory.template.v1.admin.openapi.payload.PetDto;
import fr.versefactory.template.v1.admin.pet.representations.PetRepresentationV1;
import fr.versefactory.template.exception.NotFoundException;
import fr.versefactory.template.exception.ErrorMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetServiceV1 extends TemplateServiceV1 {

    private final PetRepositoryV1 repository;
    private final PetMapperV1 mapper;

    public List<PetDto> getAllPets() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public PetDto getPetById(UUID id) {
        PetRepresentationV1 representation = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.NOT_FOUND_RESOURCE));
        return mapper.toDto(representation);
    }
}
