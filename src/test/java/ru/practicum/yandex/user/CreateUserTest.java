package ru.practicum.yandex.user;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.practicum.yandex.model.user.User;
import ru.practicum.yandex.model.user.UserClient;
import ru.practicum.yandex.model.user.UserLogin;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreateUserTest {
    User user;
    UserClient userClient;

    boolean isDelete;
    String accessToken;
    boolean isCreate;

    @Before
    public void start() {
        user = User.getRandomDataRegister();
        userClient = new UserClient();
    }

    @After
    public void finish() {
        if (accessToken != null) {
            isDelete = userClient.delete(accessToken)
                    .statusCode(SC_ACCEPTED).extract().path("success");
            assertTrue(isDelete);
        }
    }


    @Test
    @DisplayName("Registration of a unique user ")
    public void createUniqueUserTest() {
        isCreate = userClient.create(user)
                .statusCode(SC_OK).extract().path("success");
        assertTrue(isCreate);
        accessToken = userClient.login(UserLogin.getLogin(user))
                .statusCode(SC_OK).extract().path("accessToken");

    }

    @Test
    @DisplayName("Registration of a registered user")
    public void createSecondRegisteredUserTest() {
        userClient.create(user)
                .statusCode(SC_OK).extract().path("success");
        String error = userClient.create(user)
                .statusCode(SC_FORBIDDEN).extract().path("message");
        assertEquals("User already exists", error);
        accessToken = userClient.login(UserLogin.getLogin(user))
                .statusCode(SC_OK).extract().path("accessToken");
    }

    @Test
    @DisplayName("Registration of a user without a name")
    public void createUserNotNameTest() {
        user.setName("");
        String error = userClient.create(user)
                .statusCode(SC_FORBIDDEN).extract().path("message");
        assertEquals("Email, password and name are required fields", error);
        accessToken = userClient.login(UserLogin.getLogin(user))
                .extract().path("accessToken");

    }


}
