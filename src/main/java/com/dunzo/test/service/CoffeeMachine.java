package com.dunzo.test.service;

import com.dunzo.test.utils.Constants;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CoffeeMachine {
    private final Integer numOfOutlets;
    private Integer busyOutlets = 0;
    private Map<Integer, Beverage> menu = new HashMap<>();
    private Integer itemNumber;

    public CoffeeMachine(Integer numOfOutlets, Map<Integer, Beverage> menu) {
        this.numOfOutlets = numOfOutlets;
        this.menu = menu;
    }
    public void getBeverage(Integer itemNumber) {
        String msg = "";
        if (!Objects.isNull(itemNumber) && this.menu.containsKey(itemNumber)) {
            try {
                Beverage beverage = this.menu.get(itemNumber);
                Boolean allowed  = isIdle();
                if (allowed) {
                    busyOutlets += 1;
                    beverage.getBeverage();
                    busyOutlets -= 1;
                    msg = beverage.getName() + Constants.IS_PREPARED;
                    System.out.println(msg);
                } else {
                    Thread.sleep(100);
                    getBeverage(itemNumber);
                }
            } catch (Exception exp) {
                msg = exp.getMessage();
                System.out.println(msg);
                busyOutlets -=1;
                throw new RuntimeException(msg);
            }
        }
    }


    private Boolean isIdle() throws InterruptedException {
        if (this.busyOutlets >= numOfOutlets) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
