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
 * BuyUpgradeRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.11.0")
public class BuyUpgradeRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private String upgradeId;

  private BigDecimal price;

  public BuyUpgradeRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public BuyUpgradeRequest(String upgradeId, BigDecimal price) {
    this.upgradeId = upgradeId;
    this.price = price;
  }

  public BuyUpgradeRequest upgradeId(String upgradeId) {
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

  public BuyUpgradeRequest price(BigDecimal price) {
    this.price = price;
    return this;
  }

  /**
   * Get price
   * @return price
   */
  @NotNull @Valid 
  @Schema(name = "price", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("price")
  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BuyUpgradeRequest buyUpgradeRequest = (BuyUpgradeRequest) o;
    return Objects.equals(this.upgradeId, buyUpgradeRequest.upgradeId) &&
        Objects.equals(this.price, buyUpgradeRequest.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(upgradeId, price);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BuyUpgradeRequest {\n");
    sb.append("    upgradeId: ").append(toIndentedString(upgradeId)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
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

