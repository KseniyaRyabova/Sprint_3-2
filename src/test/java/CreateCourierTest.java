import api.CourierResponse;
import model.Courier;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTest extends BaseTest {

    private static final String loginForClassConfig = "ksyusha11";
    private static final String passwordForClassConfig = "12345";
    private static final String courierName = "ksyusha";

    @BeforeClass
    public static void createCourier() {
        Courier courier = new Courier(loginForClassConfig, passwordForClassConfig, courierName);
        given()
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));
    }

    @Test
    public void createWithoutAttr() {
        Courier courier = new Courier();
        given()
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
                .body(courier)
                .post("/api/v1/courier/login")
                .body().as(CourierResponse.class);

        given()
                .when()
                .delete(String.format("/api/v1/courier/%s", courierResponse.getId()))
                .then()
                .statusCode(200)
                .body("ok", equalTo(true));
    }
}
