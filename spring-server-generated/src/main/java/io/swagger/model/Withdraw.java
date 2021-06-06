package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Withdraw
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-27T13:17:09.505Z[GMT]")


public class Withdraw   {
  @JsonProperty("withdrawAmount")
  private Integer withdrawAmount = null;

  public Withdraw withdrawAmount(Integer withdrawAmount) {
    this.withdrawAmount = withdrawAmount;
    return this;
  }

  /**
   * Get withdrawAmount
   * @return withdrawAmount
   **/
  @Schema(example = "20", required = true, description = "")
      @NotNull

    public Integer getWithdrawAmount() {
    return withdrawAmount;
  }

  public void setWithdrawAmount(Integer withdrawAmount) {
    this.withdrawAmount = withdrawAmount;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Withdraw withdraw = (Withdraw) o;
    return Objects.equals(this.withdrawAmount, withdraw.withdrawAmount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(withdrawAmount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Withdraw {\n");
    
    sb.append("    withdrawAmount: ").append(toIndentedString(withdrawAmount)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
