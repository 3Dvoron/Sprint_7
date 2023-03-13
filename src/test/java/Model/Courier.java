package Model;

import POJO.CourierField;
import Util.ScooterRestClient;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class Courier extends ScooterRestClient {

    @Step("Create account")
    public ValidatableResponse createCourier(String login, String password, String firstName) {
        CourierField courierField = new CourierField(login, password, firstName);
        return given()
                .spec(getBaseReqSpec())
                .body(courierField)
                .when()
                .post(CREATE_COURIER_URI)
                .then();
    }

    @Step("Authorization")
    public ValidatableResponse loginCourier(String login, String password) {
        CourierField courierField = new CourierField(login, password);
        return given()
                .spec(getBaseReqSpec())
                .body(courierField)
                .when()
                .post(AUTHORIZATION_URI)
                .then();
    }

    @Step("Delete account")
    public ValidatableResponse delete(int id) {
        return given()
                .spec(getBaseReqSpec())
                .body("id")
                .when()
                .delete(CREATE_COURIER_URI + id)
                .then();
    }
}