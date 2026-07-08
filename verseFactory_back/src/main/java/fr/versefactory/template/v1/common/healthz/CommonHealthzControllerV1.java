package fr.versefactory.template.v1.common.healthz;

import fr.versefactory.template.v1.common.CommonControllerV1;
import fr.versefactory.template.v1.common.openapi.endpoint.HealthzApi;
import fr.versefactory.template.v1.common.openapi.payload.HealthCheckResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonHealthzControllerV1 extends CommonControllerV1 implements HealthzApi {

    @Override
    public ResponseEntity<HealthCheckResponse> getHealthz() {
        HealthCheckResponse response = new HealthCheckResponse();
        response.setStatus("UP");

        return ResponseEntity.ok()
                .header("Alive-Header", "true")
                .body(response);
    }
}
