package hr.java.production.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Klasa koja predstavlja prehrambeni artikl. Nasljeđuje klasu Item i implementira sučelje Edible.
 */
public class FoodItem extends Item implements Edible{

    public static final Integer CALORIES_PER_KILOGRAM = 500;

    BigDecimal weight;

    /**
     * Konstruktor koji inicijalizira objekt klase FoodItem s navedenim atributima.
     *
     * @param name            Ime prehrambenog artikla
     * @param category        Kategorija prehrambenog artikla
     * @param width           Širina prehrambenog artikla
     * @param height          Visina prehrambenog artikla
     * @param length          Dužina prehrambenog artikla
     * @param productionCost  Trošak proizvodnje prehrambenog artikla
     * @param sellingPrice    Cijena prehrambenog artikla
     * @param discount        Popust na prehrambeni artikl
     * @param weight          Težina prehrambenog artikla
     */
    public FoodItem(String name, Category category, BigDecimal width, BigDecimal height,
                    BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice,
                    Discount discount, BigDecimal weight) {
        super(name, category, width, height, length, productionCost, sellingPrice, discount);
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FoodItem foodItem = (FoodItem) o;
        return Objects.equals(weight, foodItem.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), weight);
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    @Override
    public Integer calculateKilocalories() {
        return CALORIES_PER_KILOGRAM * weight.intValue();
    }

    @Override
    public Double calculatePrice(double weight) {
        return getSellingPrice().doubleValue() * weight - getProductionCost().doubleValue();
    }

    @Override
    public String toString() {
        return  getName() + "{" +
                "težina = " + weight +
                super.toString();
    }
}
