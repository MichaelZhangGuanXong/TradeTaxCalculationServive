package com.nu.tax.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nu.tax.constant.TradeOperationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/*
   This class is to hold stock trade operation inputted from STDIN as string
   fasterXMl open source API is used to do conversion between Json String and Java object
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Trade implements Serializable {
    public static final double MAX_AMOUNT_NOT_PAY_TAX = 20000.00;

    @JsonProperty("operation")
    private TradeOperationType operation;

    @JsonProperty("unit-cost")
    private double unitCost;

    @JsonProperty("quantity")
    private long quantity;

    public double getAmount() {
        return unitCost * quantity;
    }

    public boolean isTaxableAndDeductible() {
        return getAmount() > MAX_AMOUNT_NOT_PAY_TAX;
    }
}
