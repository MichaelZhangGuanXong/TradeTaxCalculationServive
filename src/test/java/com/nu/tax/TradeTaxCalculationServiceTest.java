package com.nu.tax;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nu.tax.model.TaxPay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
   In this Junit test class includes 9 test cases that defined in assignment,
   and 2 extra cases I added to test accumulation of loss, then can be deducted in several sell operations
 */
public class TradeTaxCalculationServiceTest {

    private TradeTaxCalculationService service;

    @BeforeEach
    public void setup() {
        service = new TradeTaxCalculationService();
    }

    @DisplayName("Test - Case #1")
    @Test
    void testCalculateCase1() throws JsonProcessingException {
        String inputLine = "[{\"operation\":\"buy\",\"unit-cost\":10.00,\"quantity\":100},{\"operation\":\"sell\",\"unit-cost\":15.00,\"quantity\":50},{\"operation\":\"sell\",\"unit-cost\":15.00,\"quantity\":50}]";
        List<TaxPay> taxPays = service.calculate(inputLine);

        assertEquals("[{\"tax\": 0.00}, {\"tax\": 0.00}, {\"tax\": 0.00}]", taxPays.toString());
    }

    @DisplayName("Test - Case #2")
    @Test
    void testCalculateCase2() throws JsonProcessingException {
        String inputLine = "[{\"operation\":\"buy\",\"unit-cost\":10.00,\"quantity\":10000},{\"operation\":\"sell\",\"unit-cost\":20.00,\"quantity\":5000},{\"operation\":\"sell\",\"unit-cost\":5.00,\"quantity\":5000}]";
        List<TaxPay> taxPays = service.calculate(inputLine);

        assertEquals("[{\"tax\": 0.00}, {\"tax\": 10000.00}, {\"tax\": 0.00}]", taxPays.toString());
    }

    @DisplayName("Test - Case #1 + Case #2")
    @Test
    void testCalculateCase1Plus2() throws JsonProcessingException {
        String inputLine = "[{\"operation\":\"buy\",\"unit-cost\":10.00,\"quantity\":100},{\"operation\":\"sell\",\"unit-cost\":15.00,\"quantity\":50},{\"operation\":\"sell\",\"unit-cost\":15.00,\"quantity\":50}," +
                "{\"operation\":\"buy\",\"unit-cost\":10.00,\"quantity\":10000},{\"operation\":\"sell\",\"unit-cost\":20.00,\"quantity\":5000},{\"operation\":\"sell\",\"unit-cost\":5.00,\"quantity\":5000}]";
        List<TaxPay> taxPays = service.calculate(inputLine);

        assertEquals("[{\"tax\": 0.00}, {\"tax\": 0.00}, {\"tax\": 0.00}, {\"tax\": 0.00}, {\"tax\": 10000.00}, {\"tax\": 0.00}]", taxPays.toString());
    }

    @DisplayName("Test - Case #3")
    @Test
    void testCalculateCase3() throws JsonProcessingException {
        String inputLine = "[{\"operation\":\"buy\",\"unit-cost\":10.00,\"quantity\":10000},{\"operation\":\"sell\",\"unit-cost\":5.00,\"quantity\":5000},{\"operation\":\"sell\",\"unit-cost\":20.00,\"quantity\":3000}]";
        List<TaxPay> taxPays = service.calculate(inputLine);

        assertEquals("[{\"tax\": 0.00}, {\"tax\": 0.00}, {\"tax\": 1000.00}]", taxPays.toString());
    }

    @DisplayName("Test - Case #4")
    @Test
    void testCalculateCase4() throws JsonProcessingException {
        String inputLine = "[{\"operation\":\"buy\",\"unit-cost\":10.00,\"quantity\":10000},{\"operation\":\"buy\",\"unit-cost\":25.00,\"quantity\":5000},{\"operation\":\"sell\",\"unit-cost\":15.00,\"quantity\":10000}]";
        List<TaxPay> taxPays = service.calculate(inputLine);

        assertEquals("[{\"tax\": 0.00}, {\"tax\": 0.00}, {\"tax\": 0.00}]", taxPays.toString());
    }

    @DisplayName("Test - Case #5")
    @Test
    void testCalculateCase5() throws JsonProcessingException {
        String inputLine = "[{\"operation\":\"buy\",\"unit-cost\":10.00,\"quantity\":10000},{\"operation\":\"buy\",\"unit-cost\":25.00,\"quantity\":5000},{\"operation\":\"sell\",\"unit-cost\":15.00,\"quantity\":10000},{\"operation\":\"sell\",\"unit-cost\":25.00,\"quantity\":5000}]";
        List<TaxPay> taxPays = service.calculate(inputLine);

        assertEquals("[{\"tax\": 0.00}, {\"tax\": 0.00}, {\"tax\": 0.00}, {\"tax\": 10000.00}]", taxPays.toString());
    }

    @DisplayName("Test - Case #6")
    @Test
    void testCalculateCase6() throws JsonProcessingException {
        String inputLine = "[{\"operation\":\"buy\",\"unit-cost\":10.00,\"quantity\":10000},{\"operation\":\"sell\",\"unit-cost\":2.00,\"quantity\":5000},{\"operation\":\"sell\",\"unit-cost\":20.00,\"quantity\":2000},{\"operation\":\"sell\",\"unit-cost\":20.00,\"quantity\":2000},{\"operation\":\"sell\",\"unit-cost\":25.00,\"quantity\":1000}]";
        List<TaxPay> taxPays = service.calculate(inputLine);

        assertEquals("[{\"tax\": 0.00}, {\"tax\": 0.00}, {\"tax\": 0.00}, {\"tax\": 0.00}, {\"tax\": 3000.00}]", taxPays.toString());
    }

    @DisplayName("Test - Case #7")
    @Test
    void testCalculateCase7() throws JsonProcessingException {
        String inputLine = "[{\"operation\":\"buy\",\"unit-cost\":10.00,\"quantity\":10000},{\"operation\":\"sell\",\"unit-cost\":2.00,\"quantity\":5000},{\"operation\":\"sell\",\"unit-cost\":20.00,\"quantity\":2000},{\"operation\":\"sell\",\"unit-cost\":20.00,\"quantity\":2000},{\"operation\":\"sell\",\"unit-cost\":25.00,\"quantity\":1000},{\"operation\":\"buy\",\"unit-cost\":20.00,\"quantity\":10000},{\"operation\":\"sell\",\"unit-cost\":15.00,\"quantity\":5000},{\"operation\":\"sell\",\"unit-cost\":30.00,\"quantity\":4350},{\"operation\":\"sell\",\"unit-cost\":30.00,\"quantity\":650}]";
        List<TaxPay> taxPays = service.calculate(inputLine);

        assertEquals("[{\"tax\": 0.00}, {\"tax\": 0.00}, {\"tax\": 0.00}, {\"tax\": 0.00}, {\"tax\": 3000.00}, {\"tax\": 0.00}, {\"tax\": 0.00}, {\"tax\": 3700.00}, {\"tax\": 0.00}]", taxPays.toString());
    }

    @DisplayName("Test - Case #8")
    @Test
    void testCalculateCase8() throws JsonProcessingException {
        String inputLine = "[{\"operation\":\"buy\",\"unit-cost\":10.00,\"quantity\":10000},{\"operation\":\"sell\",\"unit-cost\":50.00,\"quantity\":10000},{\"operation\":\"buy\",\"unit-cost\":20.00,\"quantity\":10000},{\"operation\":\"sell\",\"unit-cost\":50.00,\"quantity\":10000}]";
        List<TaxPay> taxPays = service.calculate(inputLine);

        assertEquals("[{\"tax\": 0.00}, {\"tax\": 80000.00}, {\"tax\": 0.00}, {\"tax\": 60000.00}]", taxPays.toString());
    }

    @DisplayName("Test - Case #9")
    @Test
    void testCalculateCase9() throws JsonProcessingException {
        String inputLine = "[{\"operation\":\"buy\",\"unit-cost\":5000.00,\"quantity\":10},{\"operation\":\"sell\",\"unit-cost\":4000.00,\"quantity\":5},{\"operation\":\"buy\",\"unit-cost\":15000.00,\"quantity\":5},{\"operation\":\"buy\",\"unit-cost\":4000.00,\"quantity\":2},{\"operation\":\"buy\",\"unit-cost\":23000.00,\"quantity\":2},{\"operation\":\"sell\",\"unit-cost\":20000.00,\"quantity\":1},{\"operation\":\"sell\",\"unit-cost\":12000.00,\"quantity\":10},{\"operation\":\"sell\",\"unit-cost\":15000.00,\"quantity\":3}]";
        List<TaxPay> taxPays = service.calculate(inputLine);

        assertEquals("[{\"tax\": 0.00}, {\"tax\": 0.00}, {\"tax\": 0.00}, {\"tax\": 0.00}, {\"tax\": 0.00}, {\"tax\": 0.00}, {\"tax\": 1000.00}, {\"tax\": 2400.00}]", taxPays.toString());
    }

    @DisplayName("Test - Case #10")
    @Test
    void testCalculateCase10() throws JsonProcessingException {
        String inputLine = "[{\"operation\":\"buy\",\"unit-cost\":10.00,\"quantity\":10000},{\"operation\":\"sell\",\"unit-cost\":4.00,\"quantity\":2000},{\"operation\":\"sell\",\"unit-cost\":5.00,\"quantity\":2000},{\"operation\":\"sell\",\"unit-cost\":20.00,\"quantity\":6000}]";
        List<TaxPay> taxPays = service.calculate(inputLine);

        assertEquals("[{\"tax\": 0.00}, {\"tax\": 0.00}, {\"tax\": 0.00}, {\"tax\": 7600.00}]", taxPays.toString());
    }

    @DisplayName("Test - Case #11")
    @Test
    void testCalculateCase11() throws JsonProcessingException {
        String inputLine = "[{\"operation\":\"buy\",\"unit-cost\":10.00,\"quantity\":10000},{\"operation\":\"sell\",\"unit-cost\":4.00,\"quantity\":2000},{\"operation\":\"sell\",\"unit-cost\":5.00,\"quantity\":2000},{\"operation\":\"sell\",\"unit-cost\":15.00,\"quantity\":4000},{\"operation\":\"sell\",\"unit-cost\":25.00,\"quantity\":1000}]";
        List<TaxPay> taxPays = service.calculate(inputLine);

        assertEquals("[{\"tax\": 0.00}, {\"tax\": 0.00}, {\"tax\": 0.00}, {\"tax\": 0.00}, {\"tax\": 2600.00}]", taxPays.toString());
    }

}
