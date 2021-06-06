package io.swagger.model;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * UserToCreate
 */
@Entity
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-27T13:17:09.505Z[GMT]")


public class UserToCreate   {


  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int userId;
  @JsonProperty("username")
  private String username = null;

  @JsonProperty("Password")
  private String password = null;

  @JsonProperty("email")
  private String email = null;

  @JsonProperty("firstName")
  private String firstName = null;

  @JsonProperty("lastName")
  private String lastName = null;

  @OneToMany(mappedBy = "user")
  private Set<Account> accounts;



  public UserToCreate(){

  }
  public UserToCreate(String username,String password,String email,String firstName,String lastName, UserTypeEnum userType){
    this.username=username;
    this.password=password;
    this.email=email;
    this.firstName=firstName;
    this.lastName=lastName;
    this.userType=userType;
  }
  /**
   * Gets or Sets userType
   */

  @JsonProperty("userType")
  private UserTypeEnum userType = UserTypeEnum.CUSTOMER;

  public UserToCreate username(String username) {
    this.username = username;
    return this;
  }

  /**
   * Get username
   * @return username
   **/
  @Schema(example = "rickusername", required = true, description = "")
      @NotNull

    public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public UserToCreate password(String password) {
    this.password = password;
    return this;
  }

  /**
   * Get password
   * @return password
   **/
  @Schema(example = "rick123", required = true, description = "")
      @NotNull

    public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public UserToCreate email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
   **/
  @Schema(example = "rick123@gmail.com", required = true, description = "")
      @NotNull

    public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public UserToCreate firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * Get firstName
   * @return firstName
   **/
  @Schema(example = "rick", required = true, description = "")
      @NotNull

    public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public UserToCreate lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  /**
   * Get lastName
   * @return lastName
   **/
  @Schema(example = "Dijk", required = true, description = "")
      @NotNull

    public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public UserToCreate userType(UserTypeEnum userType) {
    this.userType = userType;
    return this;
  }

  /**
   * Get userType
   * @return userType
   **/
  @Schema(example = "Employee", required = true, description = "")
      @NotNull

    public UserTypeEnum getUserType() {
    return userType;
  }

  public void setUserType(UserTypeEnum userType) {
    this.userType = userType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserToCreate userToCreate = (UserToCreate) o;
    return Objects.equals(this.username, userToCreate.username) &&
        Objects.equals(this.password, userToCreate.password) &&
        Objects.equals(this.email, userToCreate.email) &&
        Objects.equals(this.firstName, userToCreate.firstName) &&
        Objects.equals(this.lastName, userToCreate.lastName) &&
        Objects.equals(this.userType, userToCreate.userType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, password, email, firstName, lastName, userType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserToCreate {\n");
    
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    userType: ").append(toIndentedString(userType)).append("\n");
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
