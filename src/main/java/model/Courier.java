package model;

import io.restassured.RestAssured;
import pojo.CourierField;
import util.ScooterRestClient;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class Courier extends ScooterRestClient {

    @Step("Create account")
    public ValidatableResponse createCourier(String login, String password, String firstName) {
        CourierField courierField = new CourierField(login, password, firstName);
        return RestAssured.given()
                .spec(getBaseReqSpec())
                .body(courierField)
                .when()
                .post(ScooterRestClient.CREATE_COURIER_URI)
                .then();
    }

    @Step("Authorization")
    public ValidatableResponse loginCourier(String login, String password) {
        CourierField courierField = new CourierField(login, password);
        return RestAssured.given()
                .spec(getBaseReqSpec())
                .body(courierField)
                .when()
                .post(ScooterRestClient.AUTHORIZATION_URI)
                .then();
    }

    @Step("Delete account")
    public ValidatableResponse delete(int id) {
        return RestAssured.given()
                .spec(getBaseReqSpec())
                .body("id")
                .when()
                .delete(ScooterRestClient.CREATE_COURIER_URI + id)
                .then();
    }
}