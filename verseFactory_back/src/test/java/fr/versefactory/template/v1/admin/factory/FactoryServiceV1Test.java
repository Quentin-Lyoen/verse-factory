package fr.versefactory.template.v1.admin.factory;

import fr.versefactory.template.v1.admin.openapi.payload.FactoryDto;
import fr.versefactory.template.v1.admin.openapi.payload.FactoryPetDto;
import fr.versefactory.template.v1.admin.openapi.payload.PetDto;
import fr.versefactory.template.v1.admin.factory.representations.FactoryRepresentationV1;
import fr.versefactory.template.v1.admin.pet.PetRepositoryV1;
import fr.versefactory.template.v1.admin.pet.PetMapperV1;
import fr.versefactory.template.v1.admin.pet.representations.PetRepresentationV1;
import fr.versefactory.template.storage.tables.records.FactoryRecord;
import fr.versefactory.template.storage.tables.records.PetRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FactoryServiceV1Test {

    @Mock
    private FactoryRepositoryV1 repository;

    @Mock
    private FactoryMapperV1 mapper;

    @Mock
    private PetRepositoryV1 petRepository;

    @Mock
    private PetMapperV1 petMapper;

    @InjectMocks
    private FactoryServiceV1 service;

    @Test
    void getFactoryByUserId_shouldReturnMappedDto_whenFound() {
        UUID userId = UUID.randomUUID();
        UUID factoryId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        FactoryRecord record = new FactoryRecord(factoryId, userId, BigDecimal.valueOf(1250.50), now);
        FactoryRepresentationV1 representation = new FactoryRepresentationV1(record);

        FactoryDto expectedDto = new FactoryDto(factoryId, userId, BigDecimal.valueOf(1250.50));

        when(repository.findByUserId(userId)).thenReturn(Optional.of(representation));
        when(mapper.toDto(representation)).thenReturn(expectedDto);

        FactoryDto result = service.getFactoryByUserId(userId);

        assertNotNull(result);
        assertEquals(expectedDto.getId(), result.getId());
        assertEquals(expectedDto.getUserId(), result.getUserId());
        assertEquals(expectedDto.getBalance(), result.getBalance());

        verify(repository, times(1)).findByUserId(userId);
        verify(mapper, times(1)).toDto(representation);
    }

    @Test
    void getFactoryByUserId_shouldThrowNotFoundException_whenNotFound() {
        UUID userId = UUID.randomUUID();
        when(repository.findByUserId(userId)).thenReturn(Optional.empty());

        assertThrows(fr.versefactory.template.exception.NotFoundException.class,
                () -> service.getFactoryByUserId(userId));

        verify(repository, times(1)).findByUserId(userId);
        verify(mapper, never()).toDto(any());
    }

    @Test
    void getPetsByFactoryUserId_shouldReturnPets_whenFactoryExists() {
        UUID userId = UUID.randomUUID();
        UUID factoryId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        FactoryRecord factoryRecord = new FactoryRecord(factoryId, userId, BigDecimal.valueOf(100.0), now);
        FactoryRepresentationV1 factoryRepresentation = new FactoryRepresentationV1(factoryRecord);

        UUID factoryPetId = UUID.randomUUID();
        UUID petId = UUID.randomUUID();
        FactoryPetDto factoryPetDto = new FactoryPetDto(factoryPetId, petId, "Chien", "COMMON");
        factoryPetDto.setIncomePerSecond(BigDecimal.valueOf(1.5));
        factoryPetDto.setBaseCost(BigDecimal.valueOf(50.0));

        when(repository.findByUserId(userId)).thenReturn(Optional.of(factoryRepresentation));
        when(petRepository.findFactoryPetsByFactoryId(factoryId)).thenReturn(List.of(factoryPetDto));

        List<FactoryPetDto> result = service.getPetsByFactoryUserId(userId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(factoryPetId, result.get(0).getId());
        assertEquals(petId, result.get(0).getPetId());
        assertEquals("Chien", result.get(0).getName());
        assertEquals("COMMON", result.get(0).getRarity());

        verify(repository, times(1)).findByUserId(userId);
        verify(petRepository, times(1)).findFactoryPetsByFactoryId(factoryId);
    }

    @Test
    void getPetsByFactoryUserId_shouldThrowNotFoundException_whenFactoryDoesNotExist() {
        UUID userId = UUID.randomUUID();
        when(repository.findByUserId(userId)).thenReturn(Optional.empty());

        assertThrows(fr.versefactory.template.exception.NotFoundException.class,
                () -> service.getPetsByFactoryUserId(userId));

        verify(repository, times(1)).findByUserId(userId);
        verify(petRepository, never()).findAllByFactoryId(any());
        verify(petMapper, never()).toDto(any());
    }

    @Test
    void addPetToFactory_shouldAddPetAndReturnDto_whenFactoryAndPetExist() {
        UUID userId = UUID.randomUUID();
        UUID factoryId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        FactoryRecord factoryRecord = new FactoryRecord(factoryId, userId, BigDecimal.valueOf(100.0), now);
        FactoryRepresentationV1 factoryRepresentation = new FactoryRepresentationV1(factoryRecord);

        UUID petId = UUID.randomUUID();
        PetRecord petRecord = new PetRecord(petId, "Chien", "COMMON", BigDecimal.valueOf(1.5),
                BigDecimal.valueOf(50.0));
        PetRepresentationV1 petRepresentation = new PetRepresentationV1(petRecord);
        PetDto petDto = new PetDto(petId, "Chien", "COMMON");

        when(repository.findByUserId(userId)).thenReturn(Optional.of(factoryRepresentation));
        when(petRepository.findById(petId)).thenReturn(Optional.of(petRepresentation));
        when(petMapper.toDto(petRepresentation)).thenReturn(petDto);

        PetDto result = service.addPetToFactory(userId, petId);

        assertNotNull(result);
        assertEquals(petId, result.getId());
        assertEquals("Chien", result.getName());

        verify(repository, times(1)).findByUserId(userId);
        verify(petRepository, times(1)).findById(petId);
        verify(petRepository, times(1)).addPetToFactory(factoryId, petId);
        verify(petMapper, times(1)).toDto(petRepresentation);
    }

    @Test
    void addPetToFactory_shouldThrowNotFoundException_whenFactoryDoesNotExist() {
        UUID userId = UUID.randomUUID();
        UUID petId = UUID.randomUUID();
        when(repository.findByUserId(userId)).thenReturn(Optional.empty());

        assertThrows(fr.versefactory.template.exception.NotFoundException.class,
                () -> service.addPetToFactory(userId, petId));

        verify(repository, times(1)).findByUserId(userId);
        verify(petRepository, never()).findById(any());
        verify(petRepository, never()).addPetToFactory(any(), any());
        verify(petMapper, never()).toDto(any());
    }

    @Test
    void addPetToFactory_shouldThrowNotFoundException_whenPetDoesNotExist() {
        UUID userId = UUID.randomUUID();
        UUID factoryId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        FactoryRecord factoryRecord = new FactoryRecord(factoryId, userId, BigDecimal.valueOf(100.0), now);
        FactoryRepresentationV1 factoryRepresentation = new FactoryRepresentationV1(factoryRecord);

        UUID petId = UUID.randomUUID();
        when(repository.findByUserId(userId)).thenReturn(Optional.of(factoryRepresentation));
        when(petRepository.findById(petId)).thenReturn(Optional.empty());

        assertThrows(fr.versefactory.template.exception.NotFoundException.class,
                () -> service.addPetToFactory(userId, petId));

        verify(repository, times(1)).findByUserId(userId);
        verify(petRepository, times(1)).findById(petId);
        verify(petRepository, never()).addPetToFactory(any(), any());
        verify(petMapper, never()).toDto(any());
    }

    @Test
    void updateFactoryBalance_shouldUpdateAndReturnDto_whenFactoryExists() {
        UUID userId = UUID.randomUUID();
        UUID factoryId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        FactoryRecord factoryRecord = new FactoryRecord(factoryId, userId, BigDecimal.valueOf(100.0), now);
        FactoryRepresentationV1 factoryRepresentation = new FactoryRepresentationV1(factoryRecord);

        UUID petId1 = UUID.randomUUID();
        PetRecord petRecord1 = new PetRecord(petId1, "Chien", "COMMON", BigDecimal.valueOf(1.5),
                BigDecimal.valueOf(50.0));
        PetRepresentationV1 petRepresentation1 = new PetRepresentationV1(petRecord1);

        UUID petId2 = UUID.randomUUID();
        PetRecord petRecord2 = new PetRecord(petId2, "Dragon", "LEGENDARY", BigDecimal.valueOf(10.0),
                BigDecimal.valueOf(1000.0));
        PetRepresentationV1 petRepresentation2 = new PetRepresentationV1(petRecord2);

        FactoryRecord updatedRecord = new FactoryRecord(factoryId, userId, BigDecimal.valueOf(111.5), now);
        FactoryRepresentationV1 updatedRepresentation = new FactoryRepresentationV1(updatedRecord);
        FactoryDto expectedDto = new FactoryDto(factoryId, userId, BigDecimal.valueOf(111.5));

        when(repository.findByUserId(userId)).thenReturn(Optional.of(factoryRepresentation));
        when(petRepository.findAllByFactoryId(factoryId)).thenReturn(List.of(petRepresentation1, petRepresentation2));
        when(repository.findById(factoryId)).thenReturn(Optional.of(updatedRepresentation));
        when(mapper.toDto(updatedRepresentation)).thenReturn(expectedDto);

        FactoryDto result = service.updateFactoryBalance(userId);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(111.5), result.getBalance());

        verify(repository, times(1)).findByUserId(userId);
        verify(petRepository, times(1)).findAllByFactoryId(factoryId);
        verify(repository, times(1)).updateBalance(factoryId, BigDecimal.valueOf(111.5));
        verify(repository, times(1)).findById(factoryId);
        verify(mapper, times(1)).toDto(updatedRepresentation);
    }

    @Test
    void updateFactoryBalance_shouldThrowNotFoundException_whenFactoryDoesNotExist() {
        UUID userId = UUID.randomUUID();
        when(repository.findByUserId(userId)).thenReturn(Optional.empty());

        assertThrows(fr.versefactory.template.exception.NotFoundException.class,
                () -> service.updateFactoryBalance(userId));

        verify(repository, times(1)).findByUserId(userId);
        verify(petRepository, never()).findAllByFactoryId(any());
        verify(repository, never()).updateBalance(any(), any());
    }
}
