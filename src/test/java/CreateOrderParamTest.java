import model.Order;
import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static java.net.HttpURLConnection.HTTP_CREATED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class CreateOrderParamTest {
    private final List<String> color;
    Order order = new Order();
    private String firstName;
    private String lastName;
    private String address;
    private int metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    public CreateOrderParamTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters()
    public static Object[][] data() {
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("GREY", "BLACK")},
                {List.of()}
        };
    }

    @Before
    public void generateRandomData() {
        firstName = RandomStringUtils.randomAlphabetic(10);
        lastName = RandomStringUtils.randomAlphabetic(10);
        address = RandomStringUtils.randomAlphabetic(10);
        metroStation = Integer.parseInt(RandomStringUtils.randomNumeric(1));
        phone = RandomStringUtils.randomAlphabetic(10);
        rentTime = Integer.parseInt(RandomStringUtils.randomNumeric(1));
        deliveryDate = "2022-06-01";
        comment = RandomStringUtils.randomAlphabetic(10);
    }

    @Test
    @Description("Этот тест проверяет можно ли указать один из цветов — BLACK или GREY, можно ли указать оба цвета, не одного цвета")
    public void createOrderParam() {
        ValidatableResponse createResponse = order.createOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        int statusCode = createResponse.extract().statusCode();
        int track = createResponse.extract().path("track");
        assertEquals("Status code is incorrect", HTTP_CREATED, statusCode);
        assertTrue("Order is not created", track != 0);
    }
}
