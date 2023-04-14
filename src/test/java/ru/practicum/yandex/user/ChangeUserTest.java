package ru.practicum.yandex.user;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.practicum.yandex.model.user.User;
import ru.practicum.yandex.model.user.UserClient;
import ru.practicum.yandex.model.user.UserGeneration;
import ru.practicum.yandex.model.user.UserLogin;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ChangeUserTest {
    User user;
    UserClient userClient;
    String accessToken;
    boolean isCreate;
    String noToken = "";

    @Before
    public void start() {
        user = User.getRandomDataRegister();
        userClient = new UserClient();
        isCreate = userClient.create(user)
                .statusCode(SC_OK).extract().path("success");
        accessToken = userClient.login(UserLogin.getLogin(user))
                .statusCode(SC_OK).extract().path("accessToken");

    }

    @After
    public void finish() {
        boolean isDelete = userClient.delete(accessToken)
                .statusCode(SC_ACCEPTED).extract().path("success");
        assertTrue(isDelete);
    }

    @Test
    @DisplayName("authorization and email change")
    public void authorizationChangeEmailTest() {
        user.setEmail(UserGeneration.randomEmail());
        boolean isTrue = userClient.change(accessToken, user)
                .statusCode(SC_OK).extract().path("success");
        assertTrue(isTrue);
    }

    @Test
    @DisplayName("authorization and password change")
    public void authorizationChangePasswordTest() {
        user.setPassword(UserGeneration.randomPassword());
        boolean isTrue = userClient.change(accessToken, user)
                .statusCode(SC_OK).extract().path("success");
        assertTrue(isTrue);
    }

    @Test
    @DisplayName("authorization and name change")
    public void authorizationChangeNameTest() {
        user.setName(UserGeneration.randomName());
        boolean isTrue = userClient.change(accessToken, user)
                .statusCode(SC_OK).extract().path("success");
        assertTrue(isTrue);
    }

    @Test
    @DisplayName("No authorization and email change")
    public void noAuthorizationChangeEmailTest() {
        user.setEmail(UserGeneration.randomEmail());
        String error = userClient.change(noToken, user)
                .statusCode(SC_UNAUTHORIZED).extract().path("message");
        assertEquals("You should be authorised", error);
    }

    @Test
    @DisplayName("No authorization and password change")
    public void noAuthorizationChangePasswordTest() {
        user.setPassword(UserGeneration.randomPassword());
        String error = userClient.change(noToken, user)
                .statusCode(SC_UNAUTHORIZED).extract().path("message");
        assertEquals("You should be authorised", error);
    }

    @Test
    @DisplayName("No authorization and name change")
    public void noAuthorizationChangeNameTest() {
        user.setName(UserGeneration.randomName());
        String error = userClient.change(noToken, user)
                .statusCode(SC_UNAUTHORIZED).extract().path("message");
        assertEquals("You should be authorised", error);
    }


}
