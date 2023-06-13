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

public class LoginTest {
    User user;
    UserClient userClient;
    String accessToken;

    @Before
    public void start() {
        user = User.getRandomDataRegister();
        userClient = new UserClient();
        accessToken = userClient.create(user)
                .statusCode(SC_OK)
                .extract().path("accessToken");
    }

    @After
    public void finish() {
        boolean isDelete = userClient.delete(accessToken)
                .statusCode(SC_ACCEPTED).extract().path("success");
        assertTrue(isDelete);
    }

    @Test
    @DisplayName("login under an existing user")
    public void loginUnderAnExistingUserTest() {
        boolean isSuccess = userClient.login(UserLogin.getLogin(user))
                .statusCode(SC_OK).extract().path("success");
        assertTrue(isSuccess);
    }

    @Test
    @DisplayName("login with an invalid username and password")
    public void loginIncorrectNameAndPasswordTest() {
        user.setEmail(UserGeneration.getRandomString(5));
        user.setPassword(UserGeneration.getRandomString(1));
        String error = userClient.login(UserLogin.getLogin(user))
                .statusCode(SC_UNAUTHORIZED).extract().path("message");
        assertEquals("email or password are incorrect", error);
    }


}
