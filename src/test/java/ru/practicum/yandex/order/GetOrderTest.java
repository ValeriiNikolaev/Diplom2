package ru.practicum.yandex.order;


import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.practicum.yandex.model.order.Order;
import ru.practicum.yandex.model.order.OrderSteps;
import ru.practicum.yandex.model.user.User;
import ru.practicum.yandex.model.user.UserClient;

import java.util.ArrayList;
import java.util.List;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GetOrderTest {
    Order order;
    OrderSteps orderSteps;
    User user;
    UserClient userClient;
    boolean isDelete;
    String accessToken;
    List<Object> actualNumber = new ArrayList<>();
    List<Object> numberOrder = new ArrayList<>();

    @Before
    public void start() throws IndexOutOfBoundsException {
        user = User.getRandomDataRegister();
        userClient = new UserClient();
        order = Order.getOrder();
        orderSteps = new OrderSteps();
        accessToken = userClient.create(user)
                .statusCode(SC_OK).extract().path("accessToken");
        numberOrder.add(0, orderSteps.create(order, accessToken)
                .statusCode(SC_OK).extract().path("order.number"));
    }

    @After
    public void finish() {
        isDelete = userClient.delete(accessToken)
                .statusCode(SC_ACCEPTED).extract().path("success");
        assertTrue(isDelete);
    }

    @Test
    @DisplayName("Get order authorization user")
    public void getOrderAuthorizationUserTest() {
        actualNumber.add(orderSteps.getOrders(accessToken)
                .statusCode(SC_OK).extract().path("orders.number"));
        assertEquals(actualNumber.get(0), numberOrder);
    }

    @Test
    @DisplayName("Get order not authorization user")
    public void getOrderNotAuthorizationUserTest() {
        String error = orderSteps.getOrders("").statusCode(SC_UNAUTHORIZED)
                .extract().path("message");
        assertEquals("You should be authorised", error);
    }


}
