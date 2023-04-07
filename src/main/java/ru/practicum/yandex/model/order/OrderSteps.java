package ru.practicum.yandex.model.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.practicum.yandex.config.Config;

public class OrderSteps extends Config {

    private static final String ORDER = "orders";

    @Step("Create order")
    public ValidatableResponse create(Order order, String accessToken) {
        return getSpec()
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(ORDER)
                .then().log().all()
                .assertThat();
    }

    @Step("Get orders")
    public ValidatableResponse getOrders(String accessToken) {
        return getSpec()
                .header("Authorization", accessToken)
                .get(ORDER)
                .then().log().all()
                .assertThat();
    }


}
