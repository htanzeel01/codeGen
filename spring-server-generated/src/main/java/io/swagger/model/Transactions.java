package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Transactions
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-27T13:17:09.505Z[GMT]")

@Entity
@Table(name="Transactions")
public class Transactions   {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @JsonProperty("from")
  private String accountFrom = null;

  @JsonProperty("to")
  private String to = null;

  @JsonProperty("amount")
  private BigDecimal amount = null;

  @JsonProperty("userPerforming")
  private String userPerforming = null;

  @JsonProperty("transactionDate")
  private String transactionDate = null;

  public Transactions id(Integer id) {
    this.id = id;
    return this;
  }
  public Transactions(){

  };
  public Transactions(Integer id, String accountFrom, String to, BigDecimal amount, String userPerforming, String transactionDate) {
    this.id = id;
    this.accountFrom = accountFrom;
    this.to = to;
    this.amount = amount;
    this.userPerforming = userPerforming;
    this.transactionDate = transactionDate;
  }
/*
  /**
   * Get id
   * @return id
   **/
  //@Schema(example = "12345", required = true, description = "")
      //@NotNull

    //public Integer getId() {
    //return id;
  //}

  //public void setId(Integer id) {
    //.id = id;
  //}

  public Transactions from(String accountFrom) {
    this.accountFrom = accountFrom;
    return this;
  }

  /**
   * Get from
   * @return from
   **/
  @Schema(example = "IBAN5555", required = true, description = "")
      @NotNull

    public String getFrom() {
    return accountFrom;
  }

  public void setFrom(String from) {
    this.accountFrom = from;
  }

  public Transactions to(String to) {
    this.to = to;
    return this;
  }

  /**
   * Get to
   * @return to
   **/
  @Schema(example = "IBAN6666", required = true, description = "")
      @NotNull

    public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
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
    this.amount = amount;
  }

  public Transactions userPerforming(String userPerforming) {
    this.userPerforming = userPerforming;
    return this;
  }

  /**
   * Get userPerforming
   * @return userPerforming
   **/
  @Schema(example = "54N45GHS", required = true, description = "")
      @NotNull

    public String getUserPerforming() {
    return userPerforming;
  }

  public void setUserPerforming(String userPerforming) {
    this.userPerforming = userPerforming;
  }

  public Transactions transactionDate(String transactionDate) {
    this.transactionDate = transactionDate;
    return this;
  }

  /**
   * Get transactionDate
   * @return transactionDate
   **/
  @Schema(example = "15-05-2021", required = true, description = "")
      @NotNull

    public String getTransactionDate() {
    return transactionDate;
  }

  public void setTransactionDate(String transactionDate) {
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
        Objects.equals(this.accountFrom, transactions.accountFrom) &&
        Objects.equals(this.to, transactions.to) &&
        Objects.equals(this.amount, transactions.amount) &&
        Objects.equals(this.userPerforming, transactions.userPerforming) &&
        Objects.equals(this.transactionDate, transactions.transactionDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, accountFrom, to, amount, userPerforming, transactionDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Transactions {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    from: ").append(toIndentedString(accountFrom)).append("\n");
    sb.append("    to: ").append(toIndentedString(to)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    userPerforming: ").append(toIndentedString(userPerforming)).append("\n");
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
