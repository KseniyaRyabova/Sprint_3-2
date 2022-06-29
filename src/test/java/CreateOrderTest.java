import dto.OrderRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest extends BaseTest{
    OrderRequest orderRequest;
    private static final String firstName = "firstName";
    private static final String lastName = "lastName";
    private static final String address = "Konoha";
    private static final int metroStation = 4;
    private static final String phone = "+7 800 355 35 35";
    private static final int rentTime = 4;
    private static final String deliveryDate = "2020-06-06";
    private static final String comment = "comment";

    public CreateOrderTest(OrderRequest orderRequest) {
        this.orderRequest = orderRequest;
    }

    @Parameterized.Parameters
    public static Object[] getData() {
        return new Object[][] {
                {new OrderRequest(firstName, lastName, address,
                        metroStation, phone, rentTime, deliveryDate,
                        comment, new String[]{"BLACK"})},
                {new OrderRequest(firstName, lastName, address,
                        metroStation, phone, rentTime, deliveryDate,
                        comment, new String[]{"BLACK, GRAY"})},
                {new OrderRequest(firstName, lastName, address,
                        metroStation, phone, rentTime, deliveryDate,
                        comment, new String[]{"GRAY"})},
                {new OrderRequest(firstName, lastName, address,
                        metroStation, phone, rentTime, deliveryDate,
                        comment, null)},
        };
    }

    @Test()
    public void createOrderWithColor() {
        given().spec(specification)
                .body(orderRequest)
                .when()
                .post("/api/v1/orders")
                .then()
                .statusCode(201).body("track", notNullValue());
    }
}


