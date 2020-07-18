package com.dunzo.test;

import com.dunzo.test.dto.CoffeeMachineDTO;
import com.dunzo.test.service.Beverage;
import com.dunzo.test.service.CoffeeMachine;
import com.dunzo.test.service.Ingredient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class CoffeeMachineTest {

    private CoffeeMachineDTO setUp(String fileName) {
        Map<String, Ingredient> ingredientNameToObject = new HashMap<>();
        Map<String, Beverage> beverageNameToObject = new HashMap<>();
        System.out.println(System.getProperty("user.dir"));
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            Object obj = jsonParser.parse(new FileReader(fileName));
            jsonObject = (JSONObject) obj;
        } catch (Exception exp) {
            System.out.println(exp);
        }
        JSONObject machine =(JSONObject) jsonObject.get("machine");
        JSONObject outlets =(JSONObject) machine.get("outlets");
        Object numOfOutlets =  outlets.get("count_n");
        JSONObject ingredients = (JSONObject) machine.get("total_items_quantity");
        Map<String, Long> ingredientMap = new HashMap<>(ingredients);
        for (Map.Entry<String, Long> entry : ingredientMap.entrySet()) {
            String name = entry.getKey();
            Integer quantity = (Integer)entry.getValue().intValue();

            Ingredient ingredient = new Ingredient(name, quantity);
            ingredientNameToObject.put(name, ingredient);
        }
        JSONObject beverages = (JSONObject) machine.get("beverages");
        Map<String, Map<String, Long>> beveragesMap = new HashMap<>(beverages);
        for (Map.Entry<String, Map<String, Long>> entry : beveragesMap.entrySet()) {
            String name = entry.getKey();
            Map<String, Long> temp = entry.getValue();
            Map<Ingredient, Integer> composition = new HashMap<>();
            for (Map.Entry<String, Long> ingredient : temp.entrySet()) {
                String name1 = ingredient.getKey();
                Integer quantity = ingredient.getValue().intValue();

                if (ingredientNameToObject.containsKey(name1)) {
                    composition.put(ingredientNameToObject.get(name1), quantity);
                }
            }
            Beverage beverage = new Beverage(name, composition);
            beverageNameToObject.put(name, beverage);
        }

        Map<Integer, Beverage> menu = new HashMap<>();
        int c=1;
        for (Map.Entry<String, Beverage> entry : beverageNameToObject.entrySet()) {
            menu.put(c++, entry.getValue());
        }
        CoffeeMachine coffeeMachine = new CoffeeMachine(((Long)numOfOutlets).intValue(), menu);

        CoffeeMachineDTO coffeeMachineDTO = new CoffeeMachineDTO();

        coffeeMachineDTO.setMenu(menu);
        coffeeMachineDTO.setBeverageMap(beverageNameToObject);
        coffeeMachineDTO.setIngredientMap(ingredientNameToObject);
        coffeeMachineDTO.setCoffeeMachine(coffeeMachine);
        return coffeeMachineDTO;
    }

    @Test
    public void test1Outlet() {

        CoffeeMachineDTO coffeeMachineDTO = setUp("input.json");
        List<Integer> items = new ArrayList<>();
        items.add(1);
        items.add(2);
        items.add(4);
        items.add(3);

        for (int i=0 ; i< items.size(); i++) {
            CoffeeMachine coffeeMachine = coffeeMachineDTO.getCoffeeMachine();
            int finalI = i;
            CompletableFuture.runAsync(() -> coffeeMachine.getBeverage(items.get(finalI)));
        }
    }
}
