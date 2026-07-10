package fr.versefactory.template.v1.common.account;

import fr.versefactory.template.v1.common.CommonControllerV1;
import fr.versefactory.template.v1.common.openapi.endpoint.AccountsApi;
import fr.versefactory.template.v1.common.openapi.payload.AccountDto;
import fr.versefactory.template.v1.common.openapi.payload.CreateAccountRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CommonAccountControllerV1 extends CommonControllerV1 implements AccountsApi {

    private final CommonAccountServiceV1 service;

    @Override
    public ResponseEntity<AccountDto> createAccount(CreateAccountRequest request) {
        try {
            UUID userId = service.createAccount(request.getName(), request.getEmail(), request.getPassword());
            AccountDto dto = new AccountDto();
            dto.setId(userId);
            dto.setUsername(request.getName());
            dto.setEmail(request.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (Exception e) {
            throw new RuntimeException("Creation failed: " + e.getMessage(), e);
        }
    }
}
