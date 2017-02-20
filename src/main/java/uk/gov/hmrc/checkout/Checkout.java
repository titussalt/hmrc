package uk.gov.hmrc.checkout;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * Calculates the total cost of fruit in a shopping basket
 */
class Checkout {

    BigDecimal calculateBasket(List<Fruit> fruitPurchased) {

        return fruitPurchased
                .stream()
                .filter(Objects::nonNull)
                .map(Fruit::getCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2);
    }

}
