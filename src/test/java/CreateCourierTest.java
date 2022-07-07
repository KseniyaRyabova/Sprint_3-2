import dto.Courier;
import dto.CourierResponse;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTest extends BaseTest {

    private static final String loginForClassConfig = "ksyusha11";
    private static final String passwordForClassConfig = "12345";
    private static final String courierName = "ksyusha";
    private static final String loginForClassConfig2 = "ksyusha12";

    @BeforeClass
    public static void createCourier() {
        Courier courier = new Courier(loginForClassConfig, passwordForClassConfig, courierName);
        given().spec(specification)
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));
    }

    @AfterClass
    public static void deleteCourier() {
        List<Courier> couriers = Arrays.asList(new Courier(loginForClassConfig, passwordForClassConfig),
                new Courier(loginForClassConfig2, passwordForClassConfig));

        for (Courier courier : couriers) {
            CourierResponse courierResponse = given().spec(specification)
                    .body(courier)
                    .post("/api/v1/courier/login")
                    .body().as(CourierResponse.class);
            given().spec(specification)
                    .when()
                    .delete(String.format("/api/v1/courier/%s", courierResponse.getId()))
                    .then()
                    .statusCode(200)
                    .body("ok", equalTo(true));
        }
    }

    //создание курьера без одного из полей
    @Test
    public void createWithoutAttr() {
        Courier courier = new Courier();
        given().spec(specification)
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void createWithoutOneAttr() {
        Courier courier = new Courier(loginForClassConfig2, passwordForClassConfig);
        given().spec(specification)
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    //создание курьера, который уже существует
    @Test
    public void createRepeatCourier() {
        Courier courier = new Courier(loginForClassConfig, passwordForClassConfig, courierName);
        given().spec(specification)
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }
}
