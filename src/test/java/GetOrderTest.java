import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrderTest extends BaseTest {

    @Test
    public void getOrderList() {
        Response response = given()
                .header("Content-type", "application/json")
                .get("/api/v1/orders");
        response.then().assertThat().statusCode(200);
        OrderList orderList =
                response.body().as(OrderList.class);
        MatcherAssert.assertThat(orderList.getOrders(), notNullValue());
    }
}
