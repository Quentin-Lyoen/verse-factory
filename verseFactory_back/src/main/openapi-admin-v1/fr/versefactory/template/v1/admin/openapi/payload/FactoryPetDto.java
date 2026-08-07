package fr.versefactory.template.v1.admin.openapi.payload;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;
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
 * FactoryPetDto
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.11.0")
public class FactoryPetDto implements Serializable {

  private static final long serialVersionUID = 1L;

  private UUID id;

  private UUID petId;

  private String name;

  private String rarity;

  private @Nullable BigDecimal incomePerSecond;

  private @Nullable BigDecimal baseCost;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private @Nullable OffsetDateTime acquiredAt;

  public FactoryPetDto() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public FactoryPetDto(UUID id, UUID petId, String name, String rarity) {
    this.id = id;
    this.petId = petId;
    this.name = name;
    this.rarity = rarity;
  }

  public FactoryPetDto id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   */
  @NotNull @Valid 
  @Schema(name = "id", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public FactoryPetDto petId(UUID petId) {
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

  public FactoryPetDto name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   */
  @NotNull 
  @Schema(name = "name", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public FactoryPetDto rarity(String rarity) {
    this.rarity = rarity;
    return this;
  }

  /**
   * Get rarity
   * @return rarity
   */
  @NotNull 
  @Schema(name = "rarity", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("rarity")
  public String getRarity() {
    return rarity;
  }

  public void setRarity(String rarity) {
    this.rarity = rarity;
  }

  public FactoryPetDto incomePerSecond(BigDecimal incomePerSecond) {
    this.incomePerSecond = incomePerSecond;
    return this;
  }

  /**
   * Get incomePerSecond
   * @return incomePerSecond
   */
  @Valid 
  @Schema(name = "incomePerSecond", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("incomePerSecond")
  public BigDecimal getIncomePerSecond() {
    return incomePerSecond;
  }

  public void setIncomePerSecond(BigDecimal incomePerSecond) {
    this.incomePerSecond = incomePerSecond;
  }

  public FactoryPetDto baseCost(BigDecimal baseCost) {
    this.baseCost = baseCost;
    return this;
  }

  /**
   * Get baseCost
   * @return baseCost
   */
  @Valid 
  @Schema(name = "baseCost", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("baseCost")
  public BigDecimal getBaseCost() {
    return baseCost;
  }

  public void setBaseCost(BigDecimal baseCost) {
    this.baseCost = baseCost;
  }

  public FactoryPetDto acquiredAt(OffsetDateTime acquiredAt) {
    this.acquiredAt = acquiredAt;
    return this;
  }

  /**
   * Get acquiredAt
   * @return acquiredAt
   */
  @Valid 
  @Schema(name = "acquiredAt", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("acquiredAt")
  public OffsetDateTime getAcquiredAt() {
    return acquiredAt;
  }

  public void setAcquiredAt(OffsetDateTime acquiredAt) {
    this.acquiredAt = acquiredAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FactoryPetDto factoryPetDto = (FactoryPetDto) o;
    return Objects.equals(this.id, factoryPetDto.id) &&
        Objects.equals(this.petId, factoryPetDto.petId) &&
        Objects.equals(this.name, factoryPetDto.name) &&
        Objects.equals(this.rarity, factoryPetDto.rarity) &&
        Objects.equals(this.incomePerSecond, factoryPetDto.incomePerSecond) &&
        Objects.equals(this.baseCost, factoryPetDto.baseCost) &&
        Objects.equals(this.acquiredAt, factoryPetDto.acquiredAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, petId, name, rarity, incomePerSecond, baseCost, acquiredAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FactoryPetDto {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    petId: ").append(toIndentedString(petId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    rarity: ").append(toIndentedString(rarity)).append("\n");
    sb.append("    incomePerSecond: ").append(toIndentedString(incomePerSecond)).append("\n");
    sb.append("    baseCost: ").append(toIndentedString(baseCost)).append("\n");
    sb.append("    acquiredAt: ").append(toIndentedString(acquiredAt)).append("\n");
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

