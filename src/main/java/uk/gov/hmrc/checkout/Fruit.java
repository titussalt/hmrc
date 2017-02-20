package uk.gov.hmrc.checkout;

import java.math.BigDecimal;

public enum Fruit {

    APPLE(new BigDecimal("0.6")), ORANGE(new BigDecimal("0.25"));

    private BigDecimal cost;

    Fruit(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getCost() {
        return cost;
    }

    }
