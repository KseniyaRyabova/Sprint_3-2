import io.restassured.RestAssured;
import org.junit.Before;

import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.given;

public class BaseTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

}
