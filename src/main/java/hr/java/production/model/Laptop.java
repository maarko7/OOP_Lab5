package hr.java.production.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Klasa koja predstavlja prijenosno računalo (laptop). Laptop je specifičan tip artikla koji ima određeni broj mjeseci jamstva.
 */
public final class Laptop extends Item implements Technical{

    Integer warrantyMonths;

    /**
     * Konstruktor koji inicijalizira objekt klase Laptop s navedenim atributima.
     *
     * @param name            Ime laptopa
     * @param category        Kategorija laptopa
     * @param width           Širina laptopa
     * @param height          Visina laptopa
     * @param length          Dužina laptopa
     * @param productionCost  Trošak proizvodnje laptopa
     * @param sellingPrice    Prodajna cijena laptopa
     * @param discount        Popust na laptop
     * @param warrantyMonths  Broj mjeseci jamstva laptopa
     */
    public Laptop(String name, Category category, BigDecimal width,
                  BigDecimal height, BigDecimal length,
                  BigDecimal productionCost, BigDecimal sellingPrice,
                  Discount discount, Integer warrantyMonths) {
        super(name, category, width, height, length, productionCost, sellingPrice, discount);
        this.warrantyMonths = warrantyMonths;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Laptop laptop = (Laptop) o;
        return Objects.equals(warrantyMonths, laptop.warrantyMonths);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), warrantyMonths);
    }

    public Integer getWarrantyMonths() {
        return warrantyMonths;
    }

    public void setWarrantyMonths(Integer warrantyMonths) {
        this.warrantyMonths = warrantyMonths;
    }

    @Override
    public Integer warrantyMonths() {
        return warrantyMonths;
    }

    public Double calculatePrice() {
        return getSellingPrice().doubleValue() - getProductionCost().doubleValue();
    }

    @Override
    public String toString() {
        return "Laptop: " +
                "garancija = " + warrantyMonths +
                " mjeseci, "
                + super.toString();
    }
}
