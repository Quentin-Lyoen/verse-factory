package fr.versefactory.template.v1.admin.openapi.payload;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.math.BigDecimal;
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
 * FactoryUpgradeDto
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.11.0")
public class FactoryUpgradeDto implements Serializable {

  private static final long serialVersionUID = 1L;

  private String upgradeId;

  private String name;

  private @Nullable String description;

  private String type;

  private @Nullable Integer maxLevel;

  private Integer level;

  private @Nullable BigDecimal cost;

  public FactoryUpgradeDto() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public FactoryUpgradeDto(String upgradeId, String name, String type, Integer level) {
    this.upgradeId = upgradeId;
    this.name = name;
    this.type = type;
    this.level = level;
  }

  public FactoryUpgradeDto upgradeId(String upgradeId) {
    this.upgradeId = upgradeId;
    return this;
  }

  /**
   * Get upgradeId
   * @return upgradeId
   */
  @NotNull 
  @Schema(name = "upgradeId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("upgradeId")
  public String getUpgradeId() {
    return upgradeId;
  }

  public void setUpgradeId(String upgradeId) {
    this.upgradeId = upgradeId;
  }

  public FactoryUpgradeDto name(String name) {
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

  public FactoryUpgradeDto description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
   */
  
  @Schema(name = "description", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public FactoryUpgradeDto type(String type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
   */
  @NotNull 
  @Schema(name = "type", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("type")
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public FactoryUpgradeDto maxLevel(Integer maxLevel) {
    this.maxLevel = maxLevel;
    return this;
  }

  /**
   * Get maxLevel
   * @return maxLevel
   */
  
  @Schema(name = "maxLevel", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("maxLevel")
  public Integer getMaxLevel() {
    return maxLevel;
  }

  public void setMaxLevel(Integer maxLevel) {
    this.maxLevel = maxLevel;
  }

  public FactoryUpgradeDto level(Integer level) {
    this.level = level;
    return this;
  }

  /**
   * Get level
   * @return level
   */
  @NotNull 
  @Schema(name = "level", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("level")
  public Integer getLevel() {
    return level;
  }

  public void setLevel(Integer level) {
    this.level = level;
  }

  public FactoryUpgradeDto cost(BigDecimal cost) {
    this.cost = cost;
    return this;
  }

  /**
   * Get cost
   * @return cost
   */
  @Valid 
  @Schema(name = "cost", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("cost")
  public BigDecimal getCost() {
    return cost;
  }

  public void setCost(BigDecimal cost) {
    this.cost = cost;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FactoryUpgradeDto factoryUpgradeDto = (FactoryUpgradeDto) o;
    return Objects.equals(this.upgradeId, factoryUpgradeDto.upgradeId) &&
        Objects.equals(this.name, factoryUpgradeDto.name) &&
        Objects.equals(this.description, factoryUpgradeDto.description) &&
        Objects.equals(this.type, factoryUpgradeDto.type) &&
        Objects.equals(this.maxLevel, factoryUpgradeDto.maxLevel) &&
        Objects.equals(this.level, factoryUpgradeDto.level) &&
        Objects.equals(this.cost, factoryUpgradeDto.cost);
  }

  @Override
  public int hashCode() {
    return Objects.hash(upgradeId, name, description, type, maxLevel, level, cost);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FactoryUpgradeDto {\n");
    sb.append("    upgradeId: ").append(toIndentedString(upgradeId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    maxLevel: ").append(toIndentedString(maxLevel)).append("\n");
    sb.append("    level: ").append(toIndentedString(level)).append("\n");
    sb.append("    cost: ").append(toIndentedString(cost)).append("\n");
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

