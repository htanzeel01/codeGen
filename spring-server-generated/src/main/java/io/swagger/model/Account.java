package io.swagger.model;

import java.math.RoundingMode;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Account
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-27T13:17:09.505Z[GMT]")

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "Account")
public class Account {
  @Id
  @Column(name = "iban", columnDefinition = "VARCHAR(255)")
  private String iban;

  @JsonProperty("name")
  private String name ;

  @JsonProperty("balance")
  private BigDecimal balance;
  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "userId",nullable = true)
  private UserToCreate user;

  @OneToMany(mappedBy = "accountfrom",cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
  private Set<Transactions> transactions;

  @Schema(hidden = true)

  public String getIban() {
    return iban;
  }

  public void setIban(String iban) {
    if(!iban.contains("NL")&&!iban.contains("INHO")) {
      throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Not a valid iban");
    }
    this.iban = iban;
  }

  public UserToCreate getUser() {
    return user;
  }

  public void setUser(UserToCreate user) {
    this.user = user;
  }


  public Account(String name, BigDecimal balance, Account.AccountTypeEnum accountType) {
    this.name = name;
    this.balance = balance;
    this.accountType = accountType;
  }
  public Account(){}


  /**
   * Gets or Sets accountType
   */
  public enum AccountTypeEnum {
    SAVINGS("savings"),
    
    CURRENT("current");

    private String value;

    AccountTypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static AccountTypeEnum fromValue(String text) {
      for (AccountTypeEnum b : AccountTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("account_type")
  private AccountTypeEnum accountType;

  public Account name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   **/
  @Schema(example = "Mr Joshua", required = true, description = "")
      @NotNull

    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Account balance(BigDecimal balance) {
    this.balance = balance;
    return this;
  }

  /**
   * Get balance
   * @return balance
   **/
  @Schema(example = "100", required = true, description = "")
      @NotNull

    @Valid
    public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    if (balance.compareTo(BigDecimal.ZERO)<0){
      throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Balance too low");
    }
    this.balance = balance;
  }

  public Account accountType(AccountTypeEnum accountType) {
    this.accountType = accountType;
    return this;
  }

  /**
   * Get accountType
   * @return accountType
   **/
  @Schema(example = "savings", required = true, description = "")

  public AccountTypeEnum getAccountType() {
    return accountType;
  }

  public void setAccountType(AccountTypeEnum accountType) {
    this.accountType = accountType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Account account = (Account) o;
    return Objects.equals(this.name, account.name) &&
        Objects.equals(this.balance, account.balance) &&
        Objects.equals(this.accountType, account.accountType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, balance, accountType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Account {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
    sb.append("    accountType: ").append(toIndentedString(accountType)).append("\n");
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
