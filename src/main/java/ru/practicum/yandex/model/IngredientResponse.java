package ru.practicum.yandex.model;

import java.util.List;

public class IngredientResponse {

    private boolean success;

    private List<Ingredient> data;

    public IngredientResponse() {

    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Ingredient> getData() {
        return data;
    }

    public void setData(List<Ingredient> data) {
        this.data = data;
    }
}
