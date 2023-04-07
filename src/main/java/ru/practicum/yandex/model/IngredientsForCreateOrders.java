package ru.practicum.yandex.model;

import java.util.List;

public class IngredientsForCreateOrders {

    private List<String> ingredients;

    public IngredientsForCreateOrders() {
    }

    public IngredientsForCreateOrders(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void addIngredient(String ingredient) {
        this.ingredients.add(ingredient);
    }
}
