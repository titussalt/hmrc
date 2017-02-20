package uk.gov.hmrc.checkout;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static uk.gov.hmrc.checkout.Fruit.APPLE;
import static uk.gov.hmrc.checkout.Fruit.ORANGE;

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

}
