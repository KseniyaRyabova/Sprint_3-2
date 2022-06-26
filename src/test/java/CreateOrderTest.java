import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest extends BaseTest{
    private OrderRequest orderRequest;

    public CreateOrderTest(OrderRequest orderRequest) {
        this.orderRequest = orderRequest;
    }

    @Parameterized.Parameters
    public static Object[] getData() {
        return new Object[][] {
                {new OrderRequest("firstName", "Naruto", "Konoha, 142 apt.",
                        4, "+7 800 355 35 35", 4, "2020-06-06",
                        "comment", new String[]{"BLACK"})},
                {new OrderRequest("firstName", "Naruto", "Konoha, 142 apt.",
                        4, "+7 800 355 35 35", 4, "2020-06-06",
                        "comment", new String[]{"BLACK, GRAY"})},
                {new OrderRequest("firstName", "Naruto", "Konoha, 142 apt.",
                        4, "+7 800 355 35 35", 4, "2020-06-06",
                        "comment", new String[]{"GRAY"})},
                {new OrderRequest("firstName", "Naruto", "Konoha, 142 apt.",
                        4, "+7 800 355 35 35", 4, "2020-06-06",
                        "comment")},
        };
    }

    @Test()
    public void createOrderWithColor() {
        given()
                .header("Content-type", "application/json")
                .body(orderRequest)
                .when()
                .post("/api/v1/orders")
                .then()
                .statusCode(201).body("track", notNullValue());
    }
}


