import com.google.gson.Gson;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTest extends BaseTest {

    private static final String URL = "https://qa-scooter.praktikum-services.ru";
    private static final String loginForClassConfig = "ksyusha11";
    private static final String passwordForClassConfig = "12345";
    private static final String courierName = "ksyusha";

    @BeforeClass
    public static void createCourier() {
        Courier courier = new Courier(loginForClassConfig, passwordForClassConfig, courierName);
        given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(String.format("%s/api/v1/courier", URL))
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));
    }

    @Test
    public void createWithoutAttr() {
        Courier courier = new Courier();
        given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void createRepeatCourier() {
        Courier courier = new Courier(loginForClassConfig, passwordForClassConfig, courierName);
        given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @AfterClass
    public static void deleteCourier() {
        Courier courier = new Courier(loginForClassConfig, passwordForClassConfig);
        CourierResponse courierResponse = given()
                .header("Content-type", "application/json")
                .body(courier)
                .post((String.format("%s/api/v1/courier/login", URL)))
                .body().as(CourierResponse.class);

        String newUrl = String.format("%s/api/v1/courier/%s", URL, courierResponse.getId());
        given()
                .header("Content-type", "application/json")
                .when()
                .delete(newUrl)
                .then()
                .statusCode(200)
                .body("ok", equalTo(true));
    }
}
