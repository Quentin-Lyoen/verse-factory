package fr.versefactory.template.v1.admin.factory;

import fr.versefactory.template.v1.admin.openapi.payload.FactoryDto;
import fr.versefactory.template.v1.admin.openapi.payload.PetDto;
import java.util.List;
import fr.versefactory.template.config.security.TestSecurityConfig;
import fr.versefactory.template.config.security.user.UserDetailsImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FactoryControllerV1.class)
@Import(TestSecurityConfig.class)
@ActiveProfiles("test")
class FactoryControllerV1Test {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FactoryServiceV1 service;

    @Test
    void getConnectedUserFactory_shouldReturnFactory_whenAuthenticated() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID factoryId = UUID.randomUUID();
        FactoryDto factoryDto = new FactoryDto(factoryId, userId, BigDecimal.valueOf(150.0));

        UserDetailsImpl principal = UserDetailsImpl.builder()
                .keycloakId(userId)
                .username("testuser")
                .build();
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());

        when(service.getFactoryByUserId(userId)).thenReturn(factoryDto);

        mockMvc.perform(get("/v1/admin/factory")
                        .with(authentication(auth))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(factoryId.toString()))
                .andExpect(jsonPath("$.userId").value(userId.toString()))
                .andExpect(jsonPath("$.balance").value(150.0));

        verify(service, times(1)).getFactoryByUserId(userId);
    }

    @Test
    void getConnectedUserFactoryPets_shouldReturnPetsList_whenAuthenticated() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID petId1 = UUID.randomUUID();
        PetDto pet1 = new PetDto(petId1, "Chien", "COMMON");
        pet1.setIncomePerSecond(BigDecimal.valueOf(1.5));
        pet1.setBaseCost(BigDecimal.valueOf(50.0));

        UserDetailsImpl principal = UserDetailsImpl.builder()
                .keycloakId(userId)
                .username("testuser")
                .build();
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());

        when(service.getPetsByFactoryUserId(userId)).thenReturn(List.of(pet1));

        mockMvc.perform(get("/v1/admin/factory/pets")
                        .with(authentication(auth))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(petId1.toString()))
                .andExpect(jsonPath("$[0].name").value("Chien"))
                .andExpect(jsonPath("$[0].rarity").value("COMMON"))
                .andExpect(jsonPath("$[0].incomePerSecond").value(1.5))
                .andExpect(jsonPath("$[0].baseCost").value(50.0));

        verify(service, times(1)).getPetsByFactoryUserId(userId);
    }

    @Test
    void addPetToConnectedUserFactory_shouldAddPetAndReturnCreated_whenAuthenticated() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID petId = UUID.randomUUID();
        PetDto petDto = new PetDto(petId, "Chien", "COMMON");
        petDto.setIncomePerSecond(BigDecimal.valueOf(1.5));
        petDto.setBaseCost(BigDecimal.valueOf(50.0));

        UserDetailsImpl principal = UserDetailsImpl.builder()
                .keycloakId(userId)
                .username("testuser")
                .build();
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());

        when(service.addPetToFactory(userId, petId)).thenReturn(petDto);

        mockMvc.perform(post("/v1/admin/factory/pets")
                        .with(authentication(auth))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"petId\":\"" + petId.toString() + "\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(petId.toString()))
                .andExpect(jsonPath("$.name").value("Chien"))
                .andExpect(jsonPath("$.rarity").value("COMMON"))
                .andExpect(jsonPath("$.incomePerSecond").value(1.5))
                .andExpect(jsonPath("$.baseCost").value(50.0));

        verify(service, times(1)).addPetToFactory(userId, petId);
    }

    @Test
    void updateConnectedUserFactoryBalance_shouldReturnUpdatedFactory_whenAuthenticated() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID factoryId = UUID.randomUUID();
        FactoryDto factoryDto = new FactoryDto(factoryId, userId, BigDecimal.valueOf(150.0));

        UserDetailsImpl principal = UserDetailsImpl.builder()
                .keycloakId(userId)
                .username("testuser")
                .build();
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());

        when(service.updateFactoryBalance(userId)).thenReturn(factoryDto);

        mockMvc.perform(post("/v1/admin/factory/update-balance")
                        .with(authentication(auth))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(factoryId.toString()))
                .andExpect(jsonPath("$.userId").value(userId.toString()))
                .andExpect(jsonPath("$.balance").value(150.0));

        verify(service, times(1)).updateFactoryBalance(userId);
    }
}
