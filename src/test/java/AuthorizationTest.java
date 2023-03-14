import model.Courier;
import util.ScooterRestClient;
import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static java.net.HttpURLConnection.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AuthorizationTest extends ScooterRestClient {
    public int idCourier;
    private String login = RandomStringUtils.randomAlphabetic(10);
    private String password = RandomStringUtils.randomAlphabetic(10);
    private String firstName = RandomStringUtils.randomAlphabetic(10);
    Courier courier = new Courier();

    @Before
    public void setup() {
        courier.createCourier(login, password, firstName);
    }

    @Test
    @Description("Этот тест проверяет что можно авторизоваться")
    public void authorizationTest() {
        ValidatableResponse loginResponse = courier.loginCourier(login, password);
        idCourier = loginResponse.extract().path("id");
        int statusCode = loginResponse.extract().statusCode();
        assertEquals("Status code is incorrect", HTTP_OK, statusCode);
        assertTrue("Courier ID is not created", idCourier != 0);
    }

    @Test
    @Description("Этот тест проверяет что невозможно авторизоваться без логина")
    public void authorizationWithoutLoginTest() {
        ValidatableResponse loginResponse = courier.loginCourier(emptyString, password);
        int statusCode = loginResponse.extract().statusCode();
        String expectedMessage = loginResponse.extract().path("message");
        assertEquals("Status code is incorrect", HTTP_BAD_REQUEST, statusCode);
        assertEquals("Message is incorrect", "Недостаточно данных для входа", expectedMessage);
    }

    @Test
    @Description("Этот тест проверяет что невозможно авторизоваться без пароля")
    public void authorizationWithoutPasswordTest() {
        ValidatableResponse loginResponse = courier.loginCourier(login, emptyString);
        int statusCode = loginResponse.extract().statusCode();
        String expectedMessage = loginResponse.extract().path("message");
        assertEquals("Status code is incorrect", HTTP_BAD_REQUEST, statusCode);
        assertEquals("Message is incorrect", "Недостаточно данных для входа", expectedMessage);
    }

    @Test
    @Description("Этот тест проверяет что невозможно авторизоваться с неправильным паролем")
    public void authorizationWrongPasswordTest() {
        ValidatableResponse loginResponse = courier.loginCourier(login, randomString);
        int statusCode = loginResponse.extract().statusCode();
        String expectedMessage = loginResponse.extract().path("message");
        assertEquals("Status code is incorrect", HTTP_NOT_FOUND, statusCode);
        assertEquals("Message is incorrect", "Учетная запись не найдена", expectedMessage);
    }

    @Test
    @Description("Этот тест проверяет что невозможно авторизоваться с неправильным логином")
    public void authorizationWrongLoginTest() {
        ValidatableResponse loginResponse = courier.loginCourier(randomString, password);
        int statusCode = loginResponse.extract().statusCode();
        String expectedMessage = loginResponse.extract().path("message");
        assertEquals("Status code is incorrect", HTTP_NOT_FOUND, statusCode);
        assertEquals("Message is incorrect", "Учетная запись не найдена", expectedMessage);
    }

    @After
    public void clean() {
        ValidatableResponse loginResponse = courier.loginCourier(login, password);
        idCourier = loginResponse.extract().path("id");
        courier.delete(idCourier);
    }
}
