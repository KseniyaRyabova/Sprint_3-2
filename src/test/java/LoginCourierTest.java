import dto.Courier;
import dto.CourierResponse;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest extends BaseTest {

    private static final String loginForClassConfig = "ksyusha14";
    private static final String passwordForClassConfig = "12345";
    private static Courier courier = new Courier(loginForClassConfig, passwordForClassConfig);


    //создание курьера перед тестами в классе
    //смущает, что такое предусловие может упасть и весь класс не будет выполнен
    @BeforeClass
    public static void createCourier() {
        given().spec(specification)
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));
    }

    /* В доке не описан этот кейс, но в задании требуется.
    * Поэтому сделала проверку на 400 ошибку*/
    @Test
    public void authCourierWithoutAttr() {
        given().spec(specification)
                .body(new Courier(loginForClassConfig, ""))
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(400);}

    @Test
    public void authCourierWithValidData() {
        given().spec(specification)
                .body(new Courier(loginForClassConfig, passwordForClassConfig))
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    public void authCourierWithNotValidData() {
        given().spec(specification)
                .body(new Courier(loginForClassConfig, "passw"))
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(404);
    }

    @Test
    public void authNotExistingCourier() {
        Courier courier = new Courier("NotExisting","NotExisting");
        given().spec(specification)
                .body(courier)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(404);}

    //удаление курьера после теста
    @AfterClass
    public static void deleteCourier() {
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

