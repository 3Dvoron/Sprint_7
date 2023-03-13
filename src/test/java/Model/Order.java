package Model;

import POJO.OrderField;
import POJO.OrderListField;
import Util.ScooterRestClient;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.util.List;

import static io.restassured.RestAssured.given;

public class Order extends ScooterRestClient {

    @Step("Create Order")
    public ValidatableResponse createOrder(String firstName, String lastName, String address, int metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        OrderField order = new OrderField(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        return given()
                .spec(getBaseReqSpec())
                .body(order)
                .when()
                .post(CREATE_ORDER)
                .then();
    }
    @Step("Get Order List")
    public ValidatableResponse getOrderList() {
        OrderListField order = new OrderListField();
        return given()
                .spec(getBaseReqSpec())
                .when()
                .get(CREATE_ORDER)
                .then();
    }
}