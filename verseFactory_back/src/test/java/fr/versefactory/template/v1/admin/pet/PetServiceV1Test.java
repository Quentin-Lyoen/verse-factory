package fr.versefactory.template.v1.admin.pet;

import fr.versefactory.template.v1.admin.openapi.payload.PetDto;
import fr.versefactory.template.v1.admin.pet.representations.PetRepresentationV1;
import fr.versefactory.template.storage.tables.records.PetRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetServiceV1Test {

    @Mock
    private PetRepositoryV1 repository;

    @Mock
    private PetMapperV1 mapper;

    @InjectMocks
    private PetServiceV1 service;

    @Test
    void getAllPets_shouldReturnMappedDtos() {
        UUID petId = UUID.randomUUID();
        PetRecord record = new PetRecord(petId, "Cat", "RARE", BigDecimal.valueOf(2.5), BigDecimal.valueOf(200.0));
        PetRepresentationV1 representation = new PetRepresentationV1(record);

        PetDto expectedDto = new PetDto(petId, "Cat", "RARE");
        expectedDto.setIncomePerSecond(BigDecimal.valueOf(2.5));
        expectedDto.setBaseCost(BigDecimal.valueOf(200.0));

        when(repository.findAll()).thenReturn(List.of(representation));
        when(mapper.toDto(representation)).thenReturn(expectedDto);

        List<PetDto> result = service.getAllPets();

        assertNotNull(result);
        assertEquals(1, result.size());
        PetDto resultDto = result.get(0);
        assertEquals(expectedDto.getId(), resultDto.getId());
        assertEquals(expectedDto.getName(), resultDto.getName());
        assertEquals(expectedDto.getRarity(), resultDto.getRarity());
        assertEquals(expectedDto.getIncomePerSecond(), resultDto.getIncomePerSecond());
        assertEquals(expectedDto.getBaseCost(), resultDto.getBaseCost());

        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).toDto(representation);
    }

    @Test
    void getPetById_shouldReturnMappedDto_whenFound() {
        UUID petId = UUID.randomUUID();
        PetRecord record = new PetRecord(petId, "Cat", "RARE", BigDecimal.valueOf(2.5), BigDecimal.valueOf(200.0));
        PetRepresentationV1 representation = new PetRepresentationV1(record);

        PetDto expectedDto = new PetDto(petId, "Cat", "RARE");
        expectedDto.setIncomePerSecond(BigDecimal.valueOf(2.5));
        expectedDto.setBaseCost(BigDecimal.valueOf(200.0));

        when(repository.findById(petId)).thenReturn(Optional.of(representation));
        when(mapper.toDto(representation)).thenReturn(expectedDto);

        PetDto result = service.getPetById(petId);

        assertNotNull(result);
        assertEquals(expectedDto.getId(), result.getId());
        assertEquals(expectedDto.getName(), result.getName());
        assertEquals(expectedDto.getRarity(), result.getRarity());
        assertEquals(expectedDto.getIncomePerSecond(), result.getIncomePerSecond());
        assertEquals(expectedDto.getBaseCost(), result.getBaseCost());

        verify(repository, times(1)).findById(petId);
        verify(mapper, times(1)).toDto(representation);
    }

    @Test
    void getPetById_shouldThrowNotFoundException_whenNotFound() {
        UUID petId = UUID.randomUUID();
        when(repository.findById(petId)).thenReturn(Optional.empty());

        assertThrows(fr.versefactory.template.exception.NotFoundException.class, () -> service.getPetById(petId));

        verify(repository, times(1)).findById(petId);
        verify(mapper, never()).toDto(any());
    }
}
