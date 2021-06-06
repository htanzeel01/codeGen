package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.security.core.GrantedAuthority;

/**
 * Gets or Sets userType
 */
public enum UserTypeEnum implements GrantedAuthority {
  EMPLOYEE("Employee"),

  CUSTOMER("Customer");

  private String value;

  UserTypeEnum(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static UserTypeEnum fromValue(String text) {
    for (UserTypeEnum b : UserTypeEnum.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }

  @Override
  public String getAuthority() {
    return name();
  }
}
