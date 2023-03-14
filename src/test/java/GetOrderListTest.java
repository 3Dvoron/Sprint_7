import model.Order;
import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import java.util.List;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class GetOrderListTest {
    Order order = new Order();

    @Test
    @Description("Этот тест проверяет список заказов")
    public void orderListTest() {
        ValidatableResponse orderListResponse = order.getOrderList();
        int statusCode = orderListResponse.extract().statusCode();
        List<Integer> idOrder = orderListResponse.extract().path("orders.id");
        assertEquals("Status code is incorrect", HTTP_OK, statusCode);
        assertThat("Order list empty", idOrder, notNullValue());
    }
}
