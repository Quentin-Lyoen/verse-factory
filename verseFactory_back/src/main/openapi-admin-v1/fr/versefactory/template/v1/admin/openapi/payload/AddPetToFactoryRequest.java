package fr.versefactory.template.v1.admin.openapi.payload;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.UUID;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * AddPetToFactoryRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.11.0")
public class AddPetToFactoryRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private UUID petId;

  public AddPetToFactoryRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public AddPetToFactoryRequest(UUID petId) {
    this.petId = petId;
  }

  public AddPetToFactoryRequest petId(UUID petId) {
    this.petId = petId;
    return this;
  }

  /**
   * Get petId
   * @return petId
   */
  @NotNull @Valid 
  @Schema(name = "petId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("petId")
  public UUID getPetId() {
    return petId;
  }

  public void setPetId(UUID petId) {
    this.petId = petId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddPetToFactoryRequest addPetToFactoryRequest = (AddPetToFactoryRequest) o;
    return Objects.equals(this.petId, addPetToFactoryRequest.petId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(petId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddPetToFactoryRequest {\n");
    sb.append("    petId: ").append(toIndentedString(petId)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

