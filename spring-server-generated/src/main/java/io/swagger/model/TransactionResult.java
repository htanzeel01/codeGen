package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TransactionResult
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-27T13:17:09.505Z[GMT]")


public class TransactionResult   {
  @JsonProperty("message")
  private String message = null;

  @JsonProperty("success")
  private String success = null;

  public TransactionResult message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Get message
   * @return message
   **/
  @Schema(example = "Finaly you made it", required = true, description = "")
      @NotNull

    public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public TransactionResult success(String success) {
    this.success = success;
    return this;
  }

  /**
   * Get success
   * @return success
   **/
  @Schema(example = "Transaction success", required = true, description = "")
      @NotNull

    public String getSuccess() {
    return success;
  }

  public void setSuccess(String success) {
    this.success = success;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TransactionResult transactionResult = (TransactionResult) o;
    return Objects.equals(this.message, transactionResult.message) &&
        Objects.equals(this.success, transactionResult.success);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message, success);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransactionResult {\n");
    
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    success: ").append(toIndentedString(success)).append("\n");
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
