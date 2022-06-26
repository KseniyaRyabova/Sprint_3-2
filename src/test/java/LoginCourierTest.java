import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class LoginCourierTest extends BaseTest {

    /* В доке не описан этот кейс, но в задании требуется.
    * Поэтому сделала проверку на 400 ошибку*/
    @Test
    public void authCourierWithoutAttr() {
        given()
                .header("Content-type", "application/json")
                .body(new Courier("ksyusha", ""))
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(400);}

    @Test
    public void authCourierWithValidData() {
        given()
                .header("Content-type", "application/json")
                .body(new Courier("ksyusha", "12345"))
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    public void authCourierWithNotValidData() {
        given()
                .header("Content-type", "application/json")
                .body(new Courier("log", "passw"))
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(404);
    }

    @Test
    public void authNotExistingCourier() {
        Courier courier = new Courier("NotExisting","NotExisting");
        given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(404);}
}

