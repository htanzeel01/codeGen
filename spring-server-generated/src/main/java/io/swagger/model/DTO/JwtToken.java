package io.swagger.model.DTO;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.UserToCreate;
import io.swagger.service.UserToCreateService;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;

/**
 * JwtToken
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-27T13:17:09.505Z[GMT]")


public class JwtToken   {
  @JsonProperty("userId")
  private Integer userId = null;

  @JsonProperty("key")
  private String key = null;

  @JsonProperty("value")
  private String value = null;

  public JwtToken userId(Integer userId) {
    this.userId = userId;
    return this;
  }

  /**
   * Get userId
   * @return userId
   **/
  @Schema(example = "2", required = true, description = "")
      @NotNull

    public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public JwtToken key(String key) {
    this.key = key;
    return this;
  }

  /**
   * Get key
   * @return key
   **/
  @Schema(example = "JWT_Token", required = true, description = "")
      @NotNull

    public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public JwtToken value(String value) {
    this.value = value;
    return this;
  }

  /**
   * Get value
   * @return value
   **/
  @Schema(example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c", required = true, description = "")
      @NotNull

    public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JwtToken jwtToken = (JwtToken) o;
    return Objects.equals(this.userId, jwtToken.userId) &&
        Objects.equals(this.key, jwtToken.key) &&
        Objects.equals(this.value, jwtToken.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, key, value);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class JwtToken {\n");
    
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
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
