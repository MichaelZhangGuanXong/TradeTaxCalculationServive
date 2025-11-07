package com.nu.tax.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/*
  This Class is used to hold calculation result, and can be converted to Json string
  For digital numbers, here just give 1, it should be 2
 */
@Data
@AllArgsConstructor
public class TaxPay implements Serializable {
    @JsonProperty("tax")
    private double payTax;

    @Override
    public String toString() {
        return "{" + "\"tax\": " + halfUpRoundDouble(payTax) + "}";
    }

    private String halfUpRoundDouble(double value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).toString();
    }
}
