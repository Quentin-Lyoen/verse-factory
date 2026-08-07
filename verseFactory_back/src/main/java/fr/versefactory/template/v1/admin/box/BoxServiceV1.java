package fr.versefactory.template.v1.admin.box;

import fr.versefactory.template.exception.BadRequestException;
import fr.versefactory.template.exception.ErrorMessages;
import fr.versefactory.template.exception.NotFoundException;
import fr.versefactory.template.v1.admin.box.representations.BoxPetDropRepresentationV1;
import fr.versefactory.template.v1.admin.box.representations.BoxRepresentationV1;
import fr.versefactory.template.v1.admin.factory.FactoryRepositoryV1;
import fr.versefactory.template.v1.admin.factory.representations.FactoryRepresentationV1;
import fr.versefactory.template.v1.admin.openapi.payload.BoxDto;
import fr.versefactory.template.v1.admin.openapi.payload.PetDto;
import fr.versefactory.template.v1.admin.pet.PetMapperV1;
import fr.versefactory.template.v1.admin.pet.PetRepositoryV1;
import fr.versefactory.template.v1.admin.pet.representations.PetRepresentationV1;
import fr.versefactory.template.v1.core.TemplateServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoxServiceV1 extends TemplateServiceV1 {

    private final BoxRepositoryV1 repository;
    private final BoxMapperV1 mapper;
    private final PetRepositoryV1 petRepository;
    private final PetMapperV1 petMapper;
    private final FactoryRepositoryV1 factoryRepository;

    public List<BoxDto> getAllBoxes() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public PetDto openBox(UUID userId, UUID boxId) {
        BoxRepresentationV1 boxRepresentation = repository.findById(boxId)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.NOT_FOUND_RESOURCE));

        FactoryRepresentationV1 factoryRepresentation = factoryRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.NOT_FOUND_RESOURCE));

        BigDecimal price = boxRepresentation.getBox().getPrice();
        BigDecimal currentBalance = factoryRepresentation.getFactory().getBalance() != null
                ? factoryRepresentation.getFactory().getBalance()
                : BigDecimal.ZERO;

        if (price != null && price.compareTo(BigDecimal.ZERO) > 0) {
            if (currentBalance.compareTo(price) < 0) {
                throw new BadRequestException(ErrorMessages.BAD_REQUEST_INSUFFICIENT_BALANCE);
            }
        }

        List<BoxPetDropRepresentationV1> drops = repository.findPetsByBoxId(boxId);
        if (drops.isEmpty()) {
            throw new BadRequestException(ErrorMessages.NOT_FOUND_RESOURCE);
        }

        double totalWeight = drops.stream()
                .mapToDouble(d -> d.getBoxPetRecord().getDropChance() != null ? d.getBoxPetRecord().getDropChance().doubleValue() : 0.0)
                .sum();

        if (totalWeight <= 0) {
            throw new BadRequestException(ErrorMessages.NOT_FOUND_RESOURCE);
        }

        double roll = ThreadLocalRandom.current().nextDouble() * totalWeight;
        double cumulativeWeight = 0.0;
        UUID wonPetId = null;

        for (BoxPetDropRepresentationV1 drop : drops) {
            double chance = drop.getBoxPetRecord().getDropChance() != null ? drop.getBoxPetRecord().getDropChance().doubleValue() : 0.0;
            cumulativeWeight += chance;
            if (roll < cumulativeWeight) {
                wonPetId = drop.getBoxPetRecord().getPetId();
                break;
            }
        }

        if (wonPetId == null) {
            wonPetId = drops.get(drops.size() - 1).getBoxPetRecord().getPetId();
        }

        PetRepresentationV1 petRepresentation = petRepository.findById(wonPetId)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.NOT_FOUND_RESOURCE));

        petRepository.addPetToFactory(factoryRepresentation.getFactory().getId(), petRepresentation.getPet().getId());

        if (price != null && price.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal newBalance = currentBalance.subtract(price);
            factoryRepository.updateBalance(factoryRepresentation.getFactory().getId(), newBalance);
        }

        return petMapper.toDto(petRepresentation);
    }

    public List<PetDto> getPetsByBoxId(UUID boxId) {
        repository.findById(boxId)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.NOT_FOUND_RESOURCE));

        return repository.findAllPetsByBoxId(boxId).stream()
                .map(petMapper::toDto)
                .collect(Collectors.toList());
    }
}

