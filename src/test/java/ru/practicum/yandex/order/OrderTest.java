package ru.practicum.yandex.order;


import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import ru.practicum.yandex.model.order.Order;
import ru.practicum.yandex.model.order.OrderSteps;
import ru.practicum.yandex.model.user.User;
import ru.practicum.yandex.model.user.UserClient;
import ru.practicum.yandex.model.user.UserLogin;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OrderTest {
    Order order;
    OrderSteps orderSteps;
    User user;
    UserClient userClient;
    String accessToken;
    boolean isCreate;
    boolean isDelete;


    @Before
    public void start() {
        order = Order.getOrder();
        orderSteps = new OrderSteps();
        userClient = new UserClient();
        user = User.getRegister();
        isCreate = userClient.create(user)
                .statusCode(SC_OK).extract().path("success");
        accessToken = userClient.login(UserLogin.getLogin(user))
                .statusCode(SC_OK).extract().path("accessToken");
        assertTrue(isCreate);
    }

    @Test
    @DisplayName("Creating an order with ingredients and authorization")
    public void creatingOrderWithIngredientsAuthorizationTest() {
        String name = orderSteps.create(order, accessToken)
                .statusCode(SC_OK).extract().path("name");
        assertEquals("Флюоресцентный бургер", name);
        isDelete = userClient.delete(accessToken)
                .statusCode(SC_ACCEPTED).extract().path("success");
        assertTrue(isDelete);
    }

    @Test
    @DisplayName("Creating an order not ingredients")
    public void creatingOrderNoIngredientsTest() {
        order.setIngredients(new String[]{});
        String error = orderSteps.create(order,accessToken)
                .statusCode(SC_BAD_REQUEST).extract().path("message");
        assertEquals("Ingredient ids must be provided", error);
        isDelete = userClient.delete(accessToken)
                .statusCode(SC_ACCEPTED).extract().path("success");
        assertTrue(isDelete);
    }

    @Test
    @DisplayName("Creating an order not authorization")
    public void notAuthorizationCreateOrderTest() {
        String name = orderSteps.create(order,"")
                .statusCode(SC_OK).extract().path("name");
        assertEquals("Флюоресцентный бургер", name);
    }

    @Test
    @DisplayName("Creating an order with an incorrect hash of ingredients")
    public void creatingOrderWithIncorrectHashIngredients() {
        order.setIngredients(new String[]{"no valid"});
        orderSteps.create(order,accessToken).statusCode(SC_INTERNAL_SERVER_ERROR);
        isDelete = userClient.delete(accessToken)
                .statusCode(SC_ACCEPTED).extract().path("success");
        assertTrue(isDelete);
    }



}
