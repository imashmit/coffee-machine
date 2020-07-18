package com.dunzo.test.dto;

import com.dunzo.test.service.Beverage;
import com.dunzo.test.service.CoffeeMachine;
import com.dunzo.test.service.Ingredient;

import java.util.Map;

public class CoffeeMachineDTO {
    public CoffeeMachine coffeeMachine;
    Map<String, Ingredient> ingredientMap;
    Map<String, Beverage> beverageMap;

    public Map<Integer, Beverage> getMenu() {
        return menu;
    }

    public void setMenu(Map<Integer, Beverage> menu) {
        this.menu = menu;
    }

    Map<Integer, Beverage> menu;


    public Map<String, Ingredient> getIngredientMap() {
        return ingredientMap;
    }

    public void setIngredientMap(Map<String, Ingredient> ingredientMap) {
        this.ingredientMap = ingredientMap;
    }

    public CoffeeMachine getCoffeeMachine() {
        return coffeeMachine;
    }

    public void setCoffeeMachine(CoffeeMachine coffeeMachine) {
        this.coffeeMachine = coffeeMachine;
    }

    public Map<String, Beverage> getBeverageMap() {
        return beverageMap;
    }

    public void setBeverageMap(Map<String, Beverage> beverageMap) {
        this.beverageMap = beverageMap;
    }
}
