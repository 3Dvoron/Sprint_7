import model.Courier;
import util.ScooterRestClient;
import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Test;

import static java.net.HttpURLConnection.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class CreateAccountTest extends ScooterRestClient {
    public int idCourier;
    private String login = RandomStringUtils.randomAlphabetic(10);
    private String password = RandomStringUtils.randomAlphabetic(10);
    private String firstName = RandomStringUtils.randomAlphabetic(10);
    Courier courier = new Courier();

    @Test
    @Description("Этот тест проверяет что можно создать аккаунт курьера проверяя статус код и айди")
    public void createAccountCourier() {
        ValidatableResponse createResponse = courier.createCourier(login, password, firstName);
        int statusCode = createResponse.extract().statusCode();
        boolean isCourierCreated = createResponse.extract().path("ok");
        ValidatableResponse loginResponse = courier.loginCourier(login, password);
        idCourier = loginResponse.extract().path("id");
        assertEquals("Status code is incorrect", HTTP_CREATED, statusCode);
        assertTrue("Model.Courier is not created", isCourierCreated);
    }

    @Test
    @Description("Этот тест проверяет что невозможно создать два одинаковых аккаунта")
    public void createDuplicateAccount() {
        ValidatableResponse createResponse = courier.createCourier(login, password, firstName);
        int statusCode = createResponse.extract().statusCode();
        ValidatableResponse createDuplicate = courier.createCourier(login, password, firstName);
        int statusCodeDuplicate = createDuplicate.extract().statusCode();
        assertEquals("Status code is incorrect", HTTP_CREATED, statusCode);
        assertEquals("Status code is incorrect", HTTP_CONFLICT, statusCodeDuplicate);
    }

    @Test
    @Description("Этот тест проверяет что невозможно создать аккаунт с логином который уже существует")
    public void createIdenticalLoginAccount() {
        courier.createCourier(login, password, firstName);
        ValidatableResponse createIdenticalLogin = courier.createCourier(login, randomString, randomString);
        int statusIdenticalLogin = createIdenticalLogin.extract().statusCode();
        String expectedMessage = createIdenticalLogin.extract().path("message");
        assertEquals("Status code is incorrect", HTTP_CONFLICT, statusIdenticalLogin);
        assertEquals("Message is incorrect", "Этот логин уже используется. Попробуйте другой.", expectedMessage);
    }

    @Test
    @Description("Этот тест проверяет что невозможно созать аккаунт без пароля")
    public void createWithoutPasswordAccount() {
        courier.createCourier(login, password, firstName);
        ValidatableResponse createIdenticalLogin = courier.createCourier(login, emptyString, firstName);
        int statusIdenticalLogin = createIdenticalLogin.extract().statusCode();
        String expectedMessage = createIdenticalLogin.extract().path("message");
        assertEquals("Status code is incorrect", HTTP_BAD_REQUEST, statusIdenticalLogin);
        assertEquals("Message is incorrect", "Недостаточно данных для создания учетной записи", expectedMessage);
    }

    @Test
    @Description("Этот тест проверяет что невозможно созать аккаунт без логина")
    public void createWithoutLoginAccount() {
        courier.createCourier(login, password, firstName);
        ValidatableResponse createIdenticalLogin = courier.createCourier(emptyString, password, firstName);
        int statusIdenticalLogin = createIdenticalLogin.extract().statusCode();
        String expectedMessage = createIdenticalLogin.extract().path("message");
        assertEquals("Status code is incorrect", HTTP_BAD_REQUEST, statusIdenticalLogin);
        assertEquals("Message is incorrect", "Недостаточно данных для создания учетной записи", expectedMessage);
    }

    @After
    public void clean() {
        ValidatableResponse loginResponse = courier.loginCourier(login, password);
        idCourier = loginResponse.extract().path("id");
        courier.delete(idCourier);
    }
}
