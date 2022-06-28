import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.BeforeClass;

public class BaseTest {
    @BeforeClass
    public void setUp() {
        RequestSpecification specification = RestAssured.given();
        specification.baseUri("https://qa-scooter.praktikum-services.ru");
        specification.header("Content-type", "application/json");
    }

}
