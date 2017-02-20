package uk.gov.hmrc.checkout;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;

import static uk.gov.hmrc.checkout.Fruit.APPLE;
import static uk.gov.hmrc.checkout.Fruit.ORANGE;

/**
 * Calculates the total cost of fruit in a shopping basket including application of any offers
 */
class Checkout {

    public BigDecimal calculateBasket(List<Fruit> fruitPurchased) {

        return fruitPurchased
                .stream()
                .filter(Objects::nonNull)
                .map(Fruit::getCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2);
    }

    public BigDecimal calculateBasket(List<Fruit> fruitPurchased, Set<Offer> offers) {

        if (offers == null || offers.isEmpty()) {
            return calculateBasket(fruitPurchased);
        }

        Map<Fruit, BigDecimal> totals = new EnumMap<>(Fruit.class);

        offers.forEach(offer -> {
                    switch (offer) {
                        case APPLE_TWO4ONE:
                            totals.put(APPLE, calculateAppleTwo4OneDiscount(fruitPurchased));
                            break;
                        case ORANGE_THREE4TWO:
                            totals.put(ORANGE, calculateOrangeThree4TwoDiscount(fruitPurchased));
                            break;
                        default:
                            break;
                    }
                }
        );

        Arrays.stream(Fruit.values()).forEach(fruit -> totals.putIfAbsent(fruit, calculateBasket(fruitPurchased, fruit)));
        return totals.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2);
    }

    private BigDecimal calculateBasket(List<Fruit> fruitPurchased, Fruit fruit) {

        return fruitPurchased
                .stream()
                .filter(f -> f == fruit)
                .map(Fruit::getCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateAppleTwo4OneDiscount(List<Fruit> fruitPurchased) {

        int count = Collections.frequency(fruitPurchased, APPLE);

        return APPLE.getCost().multiply(new BigDecimal(Integer.toString(count/2)).add(new BigDecimal(Integer.toString(count % 2))));
    }

    private BigDecimal calculateOrangeThree4TwoDiscount(List<Fruit> fruitPurchased) {

        int count = Collections.frequency(fruitPurchased, ORANGE);

        return ORANGE.getCost().multiply(new BigDecimal(Integer.toString((count/3)*2)).add(new BigDecimal(Integer.toString(count % 3))));
    }

}
