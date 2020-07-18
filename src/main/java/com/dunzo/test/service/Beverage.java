package com.dunzo.test.service;

import com.dunzo.test.utils.Constants;

import java.util.Map;
import java.util.Objects;

public class Beverage {
    private String name;
    private Map<Ingredient, Integer>  composition;


    public Beverage(String name, Map<Ingredient, Integer> composition) {
        this.name = name;
        this.composition = composition;
    }

    public String getName() {
        return this.name;
    }


    public String getBeverage() {
        String errMsg = "";
        if (!Objects.isNull(composition) && composition.size() > 0) {
            synchronized (Beverage.class) {
                for (Map.Entry<Ingredient, Integer> entry : composition.entrySet()) {
                    Ingredient ingredient = entry.getKey();
                    Integer requiredQuantity = entry.getValue();

                    if (ingredient.getCurrentQuantity() == 0) {
                        errMsg = this.name + Constants.CANNOT_BE_PREPARED_BECAUSE + ingredient.getName()
                                + Constants.IS_NOT_AVAILABLE;
                        break;
                    } else if (ingredient.getCurrentQuantity() < requiredQuantity) {
                        errMsg = this.name + Constants.CANNOT_BE_PREPARED_BECAUSE + ingredient.getName()
                                + Constants.IS_NOT_SUFFICIENT;
                        break;
                    }
                }
                if (errMsg.length() != 0) {
                    throw new RuntimeException(errMsg);
                }
                for (Map.Entry<Ingredient, Integer> entry : composition.entrySet()) {
                    Ingredient ingredient = entry.getKey();
                    Integer requiredQuantity = entry.getValue();
                    ingredient.useIngredient(requiredQuantity);
                }
            }
        }
        return null;
    }
}
