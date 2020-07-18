package com.dunzo.test.service;

import java.util.Objects;

public class Ingredient {
    private String name;
    private Integer quantity;

    public Ingredient(String name, Integer quantity) {
        if (!Objects.isNull(name)) {
            this.name = name;
        }
        if (!Objects.isNull(quantity)) {
            this.quantity = quantity;
        }
    }

    public Integer getCurrentQuantity() {
        return this.quantity;
    }

    public String getName() {
        return this.name;
    }

    public void useIngredient(Integer quantity) {
        if (!Objects.isNull(quantity)) {
            if (this.quantity < quantity) {
                throw new RuntimeException("Not enough ingredient : " + this.name
                        + "\n Current quantity : " + this.quantity + "\nRequired quantity : "+quantity);
            }
            this.quantity -= quantity;
        }
    }
}
