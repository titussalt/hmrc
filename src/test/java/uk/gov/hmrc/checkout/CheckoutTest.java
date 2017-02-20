package uk.gov.hmrc.checkout;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static uk.gov.hmrc.checkout.Fruit.APPLE;
import static uk.gov.hmrc.checkout.Fruit.ORANGE;
import static uk.gov.hmrc.checkout.Offer.APPLE_TWO4ONE;
import static uk.gov.hmrc.checkout.Offer.ORANGE_THREE4TWO;

public class CheckoutTest {

    Checkout checkout;

    @Before
    public void setUp() throws Exception {

        checkout = new Checkout();
    }

    @Test
    public void testSimpleCostMixedFruit() throws Exception {

        List<Fruit> input = Arrays.asList(APPLE, APPLE, APPLE, ORANGE);

        BigDecimal result = checkout.calculateBasket(input);

        assertEquals(new BigDecimal("2.05"), result);
    }

    @Test
    public void testSimpleCostAppleOnly() throws Exception {

        List<Fruit> input = Arrays.asList(APPLE, APPLE);

        BigDecimal result = checkout.calculateBasket(input);

        assertEquals(new BigDecimal("1.20"), result);
    }

    @Test
    public void testSimpleCostOrangeOnly() throws Exception {

        List<Fruit> input = Arrays.asList(ORANGE, ORANGE, ORANGE);

        BigDecimal result = checkout.calculateBasket(input);

        assertEquals(new BigDecimal("0.75"), result);
    }

    @Test
    public void testCostWithNullItem() throws Exception {

        List<Fruit> input = Arrays.asList(Fruit.APPLE, null);
        BigDecimal result = checkout.calculateBasket(input);

        assertEquals(new BigDecimal("0.60"), result);
    }

    @Test
    public void testCostWithEmptyInput() throws Exception {

        List<Fruit> input = new ArrayList<>();
        BigDecimal result = checkout.calculateBasket(input);

        assertEquals(BigDecimal.ZERO.setScale(2), result);

    }

    @Test
    public void testCostWithAllFruitAndAllOffers() throws Exception {

        List<Fruit> input = Arrays.asList(APPLE, APPLE, APPLE, APPLE,
                APPLE, ORANGE, ORANGE, ORANGE, ORANGE);
        Set<Offer> offers = new HashSet<>(Arrays.asList(ORANGE_THREE4TWO, APPLE_TWO4ONE));

        BigDecimal result = checkout.calculateBasket(input, offers);
        assertEquals(new BigDecimal("2.55"), result);
    }

    @Test
    public void testCostWithAllFruitAnd241Offer() throws Exception {

        List<Fruit> input = Arrays.asList(APPLE, APPLE, APPLE,
                APPLE, ORANGE, ORANGE, ORANGE, ORANGE);
        Set<Offer> offers = new HashSet<>(Collections.singletonList(APPLE_TWO4ONE));

        BigDecimal result = checkout.calculateBasket(input, offers);
        assertEquals(new BigDecimal("2.20"), result);
    }

    @Test
    public void testCostWithAllFruitAnd342Offer() throws Exception {

        List<Fruit> input = Arrays.asList(APPLE, APPLE, APPLE, APPLE,
                APPLE, ORANGE, ORANGE, ORANGE, ORANGE);
        Set<Offer> offers = new HashSet<>(Collections.singletonList(ORANGE_THREE4TWO));

        BigDecimal result = checkout.calculateBasket(input, offers);
        assertEquals(new BigDecimal("3.75"), result);
    }

    @Test
    public void testCostWithApplesAnd342Offer() throws Exception {

        List<Fruit> input = Arrays.asList(APPLE, APPLE, APPLE, APPLE, APPLE);
        Set<Offer> offers = new HashSet<>(Collections.singletonList(ORANGE_THREE4TWO));

        BigDecimal result = checkout.calculateBasket(input, offers);
        assertEquals(new BigDecimal("3.00"), result);
    }

    @Test
    public void testCostWithOneApple() throws Exception {

        List<Fruit> input = Collections.singletonList(APPLE);

        BigDecimal result = checkout.calculateBasket(input, null);
        assertEquals(new BigDecimal("0.60"), result);
    }

    @Test
    public void testCostWithNoFruitTwoOffers() throws Exception {

        List<Fruit> input = Collections.emptyList();
        Set<Offer> offers = new HashSet<>(Arrays.asList(ORANGE_THREE4TWO, APPLE_TWO4ONE));

        BigDecimal result = checkout.calculateBasket(input, offers);
        assertEquals(BigDecimal.ZERO.setScale(2), result);
    }

    @Test
    public void testCostWithOneAppleTwoOffers() throws Exception {

        List<Fruit> input = Collections.singletonList(APPLE);
        Set<Offer> offers = new HashSet<>(Arrays.asList(ORANGE_THREE4TWO, APPLE_TWO4ONE));

        BigDecimal result = checkout.calculateBasket(input, offers);
        assertEquals(new BigDecimal("0.60"), result);
    }

    @Test
    public void testCostWithZeroApplesAndOneOrangeTwoOffers() throws Exception {

        List<Fruit> input = Collections.singletonList(ORANGE);
        Set<Offer> offers = new HashSet<>(Arrays.asList(ORANGE_THREE4TWO, APPLE_TWO4ONE));

        BigDecimal result = checkout.calculateBasket(input, offers);
        assertEquals(new BigDecimal("0.25"), result);
    }

}