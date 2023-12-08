package hr.java.production.genericsi;

import hr.java.production.model.Edible;
import hr.java.production.model.Item;
import hr.java.production.model.Store;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FoodStore<T extends Edible> extends Store {

    private List<T> foodItems;

    public FoodStore(String name, String webAddress, Set<Item> items, List<T> foodItems) {
        super(name, webAddress, items);
        this.foodItems = foodItems;
    }

    public FoodStore(String name, String webAdress){
        super(name, webAdress);
        this.foodItems = new ArrayList<>();
    }

    public List<T> getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(List<T> foodItems) {
        this.foodItems = foodItems;
    }

    public void addItem(T foodItem) {
        foodItems.add(foodItem);
    }

}
