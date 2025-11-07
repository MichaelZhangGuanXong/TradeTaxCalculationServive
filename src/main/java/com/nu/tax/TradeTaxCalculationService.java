package com.nu.tax;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nu.tax.model.TaxPay;
import com.nu.tax.model.Trade;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.nu.tax.constant.TradeOperationType.BUY;

public class TradeTaxCalculationService {
    private final ObjectMapper objectMapper;

    {
        objectMapper = new ObjectMapper();

        objectMapper.setConfig(objectMapper.getDeserializationConfig()
                .with(DeserializationFeature.READ_ENUMS_USING_TO_STRING)
                .with(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS));
    }

    /**
     * Main method to run in command line, it keeps accepting Json string input from STDIN that hold list of stock trade operations, then
     * call tax calculation service to get tax pays, showing calculation result in Json string, when empty line inputs, it stops.
     * @param args  here not command line arguments considered
     * @throws JsonProcessingException  should not happen according to assumption
     */
    public static void main(String[] args) throws JsonProcessingException {
        Scanner scanner = new Scanner(System.in);

        TradeTaxCalculationService service = new TradeTaxCalculationService();

        String inputLine = scanner.nextLine();

        while (!StringUtils.isEmpty(inputLine)) {
            List<TaxPay> taxPays = service.calculate(inputLine);

            System.out.println(service.objectMapper.writeValueAsString(taxPays));

            inputLine = scanner.nextLine();
        }
    }

    /**
     * Stock trade tax calculation service
     * @param inputLine, type String, it holds valid Json operations
     * @return a list that hold tax should be paid for each operation
     * @throws JsonProcessingException  due to assumption that all input should be valid, so this exception should not happen
     */
    public List<TaxPay> calculate(String inputLine) throws JsonProcessingException {
        List<Trade> trades = objectMapper.readValue(inputLine, new TypeReference<List<Trade>>(){});

        List<TaxPay> taxPays = new ArrayList<>();

        double weightedAverageBuyPrice = 0.0d;
        long leftQuantity = 0;
        double deductLoss = 0.0d;

        for (Trade trade : trades) {
            if (trade.getOperation() == BUY) {
                taxPays.add(new TaxPay(0.0d));

                if (leftQuantity == 0) {
                    leftQuantity = trade.getQuantity();
                    weightedAverageBuyPrice = trade.getUnitCost();
                } else {
                    weightedAverageBuyPrice = (weightedAverageBuyPrice * leftQuantity + trade.getAmount()) / (leftQuantity + trade.getQuantity());
                    leftQuantity += trade.getQuantity();
                }
            } else {
                double overallBenefit = trade.getAmount() - weightedAverageBuyPrice * trade.getQuantity() - (trade.isTaxableAndDeductible() ? deductLoss : 0.0d);

                if (overallBenefit < 0.0) {
                    deductLoss = Math.abs(trade.isTaxableAndDeductible () ? overallBenefit : (overallBenefit - deductLoss));
                    taxPays.add(new TaxPay(0.0d));
                } else if (trade.isTaxableAndDeductible()) {
                    taxPays.add(new TaxPay(overallBenefit / 5.00d));
                    deductLoss = 0.0d;
                } else {
                    taxPays.add(new TaxPay(0.0d));
                }

                leftQuantity -= trade.getQuantity();
            }
        }

        return taxPays;
    }
}