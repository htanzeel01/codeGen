package io.swagger.model;

import java.time.LocalDateTime;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Transactions
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-27T13:17:09.505Z[GMT]")

@Entity
public class Transactions   {
  @Id
  @GeneratedValue
  @JsonProperty("id")
  @Schema(hidden = true)
  private Integer id = null;

  @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
  @JoinColumn(name = "iban")
  @JsonProperty("accountfrom")
  private Account accountfrom = null;

  @JsonProperty("to")
  private String accountto = null;

  @JsonProperty("amount")
  private BigDecimal amount = null;

@Schema(hidden = true)
  @JsonProperty("transactionDate")
  private LocalDateTime transactionDate = LocalDateTime.now();

  @JsonProperty("UserPerforming")
  private UserTypeEnum userperforming = null;

  public BigDecimal getDayLimit() {
    return dayLimit;
  }

  public void setDayLimit(BigDecimal dayLimit) {
    this.dayLimit = BigDecimal.valueOf(5000);
  }

  @Schema(hidden = true)
  @JsonProperty("DailyLimit")
  private BigDecimal dayLimit = null;

  public UserTypeEnum getUserperforming() {
    return userperforming;
  }

  public void setUserperforming(UserTypeEnum userperforming) {
    this.userperforming = userperforming;
  }

  public Transactions id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   **/
  @Schema(example = "12345", required = true, description = "")
      @NotNull

    public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Transactions from(Account from) {
    this.accountfrom = from;
    return this;
  }

  /**
   * Get from
   * @return from
   **/
  @Schema(example = "IBAN5555", required = true, description = "")

  public Account getAccountfrom() {
    return accountfrom;
  }

  public void setAccountfrom(Account from) {
    this.accountfrom = from;
  }

  public Transactions to(String to) {
    this.accountto = to;
    return this;
  }

  /**
   * Get to
   * @return to
   **/
  @Schema(example = "IBAN6666", required = true, description = "")
      @NotNull

    public String getAccountto() {
    return accountto;
  }

  public void setAccountto(String to) {
    this.accountto = to;
  }

  public Transactions amount(BigDecimal amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Get amount
   * @return amount
   **/
  @Schema(example = "25.8", required = true, description = "")
      @NotNull

    @Valid
    public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    if (amount.compareTo(BigDecimal.ZERO)<0){
      throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Balance too low");
    }
    this.amount = amount;
  }



  /**
   * Get userPerforming
   * @return userPerforming
   **/
  @Schema(example = "54N45GHS", required = true, description = "")
      @NotNull


  public Transactions transactionDate(LocalDateTime transactionDate) {
    this.transactionDate = transactionDate;
    return this;
  }

  /**
   * Get transactionDate
   * @return transactionDate
   **/
  @Schema(example = "15-05-2021", required = true, description = "")

  public LocalDateTime getTransactionDate() {
    return transactionDate;
  }

  public void setTransactionDate(LocalDateTime transactionDate) {
    this.transactionDate = transactionDate;
  }



  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Transactions transactions = (Transactions) o;
    return Objects.equals(this.id, transactions.id) &&
        Objects.equals(this.accountfrom, transactions.accountfrom) &&
        Objects.equals(this.accountto, transactions.accountto) &&
        Objects.equals(this.amount, transactions.amount) &&
            Objects.equals(this.userperforming, transactions.userperforming) &&
        Objects.equals(this.transactionDate, transactions.transactionDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, accountfrom, accountto, amount,  transactionDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Transactions {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    from: ").append(toIndentedString(accountfrom)).append("\n");
    sb.append("    to: ").append(toIndentedString(accountto)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    transactionDate: ").append(toIndentedString(transactionDate)).append("\n");
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
