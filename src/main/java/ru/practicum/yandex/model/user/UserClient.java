package ru.practicum.yandex.model.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.practicum.yandex.config.Config;

public class UserClient extends Config {

    private static final String REGISTER = "auth/register";

    private static final String LOGIN = "auth/login";

    private static final String NEW_USER = "auth/user";

    private static final String CHANGE_USER = "auth/user";

    @Step("register")
    public ValidatableResponse create(User user) {
        return getSpec()
                .body(user)
                .when()
                .post(REGISTER)
                .then().log().all()
                .assertThat();
    }

    @Step("change user")
    public ValidatableResponse change(String accessToken, User user) {
        return getSpec()
                .header("Authorization", accessToken)
                .body(user)
                .when()
                .patch(CHANGE_USER)
                .then().log().all()
                .assertThat();
    }

    @Step("login user")
    public ValidatableResponse login(UserLogin userLogin) {
        return getSpec()
                .body(userLogin)
                .when()
                .post(LOGIN)
                .then().log().all()
                .assertThat();
    }

    @Step("Delete")
    public ValidatableResponse delete(String accessToken) {
        return getSpec()
                .header("Authorization", accessToken)
                .when()
                .delete(NEW_USER)
                .then().log().all()
                .assertThat();
    }


}
