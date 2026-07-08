package fr.versefactory.template.v1.admin.pet;

import fr.versefactory.template.v1.admin.openapi.payload.PetDto;
import fr.versefactory.template.config.security.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PetControllerV1.class)
@Import(TestSecurityConfig.class)
@ActiveProfiles("test")
class PetControllerV1Test {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PetServiceV1 service;

    @Test
    void getAllPets_shouldReturnPetsList() throws Exception {
        UUID id1 = UUID.randomUUID();
        PetDto pet1 = new PetDto(id1, "Dog", "COMMON");
        pet1.setIncomePerSecond(BigDecimal.valueOf(1.5));
        pet1.setBaseCost(BigDecimal.valueOf(100.0));

        UUID id2 = UUID.randomUUID();
        PetDto pet2 = new PetDto(id2, "Dragon", "LEGENDARY");
        pet2.setIncomePerSecond(BigDecimal.valueOf(50.0));
        pet2.setBaseCost(BigDecimal.valueOf(5000.0));

        when(service.getAllPets()).thenReturn(List.of(pet1, pet2));

        mockMvc.perform(get("/v1/admin/pets")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(id1.toString()))
                .andExpect(jsonPath("$[0].name").value("Dog"))
                .andExpect(jsonPath("$[0].rarity").value("COMMON"))
                .andExpect(jsonPath("$[0].incomePerSecond").value(1.5))
                .andExpect(jsonPath("$[0].baseCost").value(100.0))
                .andExpect(jsonPath("$[1].id").value(id2.toString()))
                .andExpect(jsonPath("$[1].name").value("Dragon"))
                .andExpect(jsonPath("$[1].rarity").value("LEGENDARY"))
                .andExpect(jsonPath("$[1].incomePerSecond").value(50.0))
                .andExpect(jsonPath("$[1].baseCost").value(5000.0));

        verify(service, times(1)).getAllPets();
    }

    @Test
    void getPetById_shouldReturnPet_whenFound() throws Exception {
        UUID petId = UUID.randomUUID();
        PetDto pet = new PetDto(petId, "Cat", "RARE");
        pet.setIncomePerSecond(BigDecimal.valueOf(2.5));
        pet.setBaseCost(BigDecimal.valueOf(200.0));

        when(service.getPetById(petId)).thenReturn(pet);

        mockMvc.perform(get("/v1/admin/pets/" + petId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(petId.toString()))
                .andExpect(jsonPath("$.name").value("Cat"))
                .andExpect(jsonPath("$.rarity").value("RARE"))
                .andExpect(jsonPath("$.incomePerSecond").value(2.5))
                .andExpect(jsonPath("$.baseCost").value(200.0));

        verify(service, times(1)).getPetById(petId);
    }

    @Test
    void getPetById_shouldReturn404_whenNotFound() throws Exception {
        UUID petId = UUID.randomUUID();
        when(service.getPetById(petId)).thenThrow(new fr.versefactory.template.exception.NotFoundException(
                fr.versefactory.template.exception.ErrorMessages.NOT_FOUND_RESOURCE
        ));

        mockMvc.perform(get("/v1/admin/pets/" + petId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value(fr.versefactory.template.exception.ErrorMessages.NOT_FOUND_RESOURCE.getMessage()));

        verify(service, times(1)).getPetById(petId);
    }
}
