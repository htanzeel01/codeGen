package io.swagger.model;

import java.time.LocalDateTime;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * DepositResult
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-27T13:17:09.505Z[GMT]")


public class DepositResult   {
  @JsonProperty("success")
  private String success = null;

  @JsonProperty("CurrentBalance")
  private BigDecimal currentBalance = null;

  @JsonProperty("time")
  private LocalDateTime time = null;

  public DepositResult success(String success) {
    this.success = success;
    return this;
  }

  /**
   * Get success
   * @return success
   **/
  @Schema(example = "Deposit success", required = true, description = "")
      @NotNull

    public String getSuccess() {
    return success;
  }

  public void setSuccess(String success) {
    this.success = success;
  }

  public DepositResult currentBalance(BigDecimal currentBalance) {
    this.currentBalance = currentBalance;
    return this;
  }

  /**
   * Get currentBalance
   * @return currentBalance
   **/
  @Schema(example = "120", required = true, description = "")
      @NotNull

    @Valid
    public BigDecimal getCurrentBalance() {
    return currentBalance;
  }

  public void setCurrentBalance(BigDecimal currentBalance) {
    this.currentBalance = currentBalance;
  }

  public DepositResult time(LocalDateTime time) {
    this.time = time;
    return this;
  }

  /**
   * Get time
   * @return time
   **/
  @Schema(example = "02:00 PM", required = true, description = "")

  public LocalDateTime getTime() {
    return time;
  }

  public void setTime(LocalDateTime time) {
    this.time = time;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DepositResult depositResult = (DepositResult) o;
    return Objects.equals(this.success, depositResult.success) &&
        Objects.equals(this.currentBalance, depositResult.currentBalance) &&
        Objects.equals(this.time, depositResult.time);
  }

  @Override
  public int hashCode() {
    return Objects.hash(success, currentBalance, time);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DepositResult {\n");
    
    sb.append("    success: ").append(toIndentedString(success)).append("\n");
    sb.append("    currentBalance: ").append(toIndentedString(currentBalance)).append("\n");
    sb.append("    time: ").append(toIndentedString(time)).append("\n");
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
