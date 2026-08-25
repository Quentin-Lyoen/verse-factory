package fr.versefactory.template.v1.admin.factory;

import fr.versefactory.template.v1.core.TemplateServiceV1;
import fr.versefactory.template.v1.admin.openapi.payload.FactoryDto;
import fr.versefactory.template.v1.admin.factory.representations.FactoryRepresentationV1;
import fr.versefactory.template.exception.BadRequestException;
import fr.versefactory.template.exception.NotFoundException;
import fr.versefactory.template.exception.ErrorMessages;
import fr.versefactory.template.v1.admin.pet.PetRepositoryV1;
import fr.versefactory.template.v1.admin.pet.PetMapperV1;
import fr.versefactory.template.v1.admin.openapi.payload.FactoryPetDto;
import fr.versefactory.template.v1.admin.openapi.payload.FactoryUpgradeDto;
import fr.versefactory.template.v1.admin.openapi.payload.PetDto;
import fr.versefactory.template.v1.admin.factory.config.UpgradeConfigService;
import fr.versefactory.template.v1.admin.factory.representations.FactoryUpgradeRepresentationV1;
import fr.versefactory.template.v1.admin.pet.representations.PetRepresentationV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FactoryServiceV1 extends TemplateServiceV1 {

    private final FactoryRepositoryV1 repository;
    private final FactoryMapperV1 mapper;
    private final PetRepositoryV1 petRepository;
    private final PetMapperV1 petMapper;
    private final UpgradeConfigService upgradeConfigService;

    public FactoryDto getFactoryByUserId(UUID userId) {
        FactoryRepresentationV1 representation = repository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.NOT_FOUND_RESOURCE));
        return mapper.toDto(representation);
    }

    public List<FactoryPetDto> getPetsByFactoryUserId(UUID userId) {
        FactoryRepresentationV1 representation = repository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.NOT_FOUND_RESOURCE));
        return petRepository.findFactoryPetsByFactoryId(representation.getFactory().getId());
    }

    public List<FactoryUpgradeDto> getUpgradesByFactoryUserId(UUID userId) {
        FactoryRepresentationV1 representation = repository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.NOT_FOUND_RESOURCE));

        List<FactoryUpgradeRepresentationV1> upgrades = repository.findUpgradesByFactoryId(representation.getFactory().getId());

        return upgrades.stream()
                .map(upgradeRep -> {
                    FactoryUpgradeDto dto = mapper.toDto(upgradeRep);
                    BigDecimal nextLevelCost = upgradeConfigService.getNextLevelCost(dto.getUpgradeId(), dto.getLevel());
                    dto.setCost(nextLevelCost);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public PetDto addPetToFactory(UUID userId, UUID petId) {
        FactoryRepresentationV1 factoryRepresentation = repository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.NOT_FOUND_RESOURCE));

        int maxCapacity = factoryRepresentation.getFactory().getMaxSize() != null
                ? factoryRepresentation.getFactory().getMaxSize()
                : 6;

        int currentPetCount = petRepository.countByFactoryId(factoryRepresentation.getFactory().getId());
        if (currentPetCount >= maxCapacity) {
            throw new BadRequestException(ErrorMessages.BAD_REQUEST_FACTORY_FULL, maxCapacity);
        }

        PetRepresentationV1 petRepresentation = petRepository.findById(petId)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.NOT_FOUND_RESOURCE));

        petRepository.addPetToFactory(factoryRepresentation.getFactory().getId(), petId);

        return petMapper.toDto(petRepresentation);
    }

    @org.springframework.transaction.annotation.Transactional
    public FactoryDto updateFactoryBalance(UUID userId) {
        FactoryRepresentationV1 factoryRepresentation = repository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.NOT_FOUND_RESOURCE));

        List<PetRepresentationV1> pets = petRepository.findAllByFactoryId(factoryRepresentation.getFactory().getId());

        BigDecimal sumOfIncome = pets.stream()
                .map(p -> p.getPet().getIncomePerSecond())
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal currentBalance = factoryRepresentation.getFactory().getBalance();
        if (currentBalance == null) {
            currentBalance = BigDecimal.ZERO;
        }
        BigDecimal newBalance = currentBalance.add(sumOfIncome);

        repository.updateBalance(factoryRepresentation.getFactory().getId(), newBalance);

        FactoryRepresentationV1 updatedRepresentation = repository.findById(factoryRepresentation.getFactory().getId())
                .orElseThrow(() -> new NotFoundException(ErrorMessages.NOT_FOUND_RESOURCE));

        return mapper.toDto(updatedRepresentation);
    }

    public void deletePetFromFactory(UUID userId, UUID factoryPetId) {
        FactoryRepresentationV1 factoryRepresentation = repository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.NOT_FOUND_RESOURCE));

        boolean removed = petRepository.removePetFromFactory(factoryPetId, factoryRepresentation.getFactory().getId());
        if (!removed) {
            throw new NotFoundException(ErrorMessages.NOT_FOUND_RESOURCE);
        }
    }
}
