package Util;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.RandomStringUtils;

public class ScooterRestClient {

    protected static final String BASE_URI = "https://qa-scooter.praktikum-services.ru/api/v1/";
    protected static final String CREATE_COURIER_URI = BASE_URI + "courier/";
    protected static final String AUTHORIZATION_URI = CREATE_COURIER_URI + "login/";
    protected static final String CREATE_ORDER = BASE_URI + "orders/";
    protected static final String randomString = RandomStringUtils.randomAlphabetic(10);
    protected static final String emptyString = "";

    protected RequestSpecification getBaseReqSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(BASE_URI)
                .build();
    }

}
