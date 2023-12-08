package hr.java.production.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Klasa koja predstavlja artikl. Svaki artikl ima ime, kategoriju, dimenzije, trošak proizvodnje,
 * prodajnu cijenu i popust.
 */
public class Item extends NamedEntity {

//    private String name;
    private Category category;
    private BigDecimal width;
    private BigDecimal height;
    private BigDecimal length;
    private BigDecimal productionCost;
    private BigDecimal sellingPrice;

    private Discount discount;

    /**
     * Konstruktor koji inicijalizira objekt klase Item s navedenim atributima.
     *
     * @param name           Ime artikla
     * @param category       Kategorija artikla
     * @param width          Širina artikla
     * @param height         Visina artikla
     * @param length         Dužina artikla
     * @param productionCost Trošak proizvodnje artikla
     * @param sellingPrice   Prodajna cijena artikla
     * @param discount       Popust na artikl
     */
    public Item(String name, Category category, BigDecimal width, BigDecimal height,
                BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice,
                Discount discount) {
        super(name);
        this.category = category;
        this.width = width;
        this.height = height;
        this.length = length;
        this.productionCost = productionCost;
        this.sellingPrice = sellingPrice;
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Item item = (Item) o;
        return Objects.equals(category, item.category) && Objects.equals(width, item.width) && Objects.equals(height, item.height)
                && Objects.equals(length, item.length) && Objects.equals(productionCost, item.productionCost)
                && Objects.equals(sellingPrice, item.sellingPrice) && Objects.equals(discount, item.discount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), category, width, height, length, productionCost, sellingPrice, discount);
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public BigDecimal calculateVolume() {
        return width.multiply(height).multiply(length);
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public BigDecimal getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(BigDecimal productionCost) {
        this.productionCost = productionCost;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    @Override
    public String toString() {
        return "kategorija = " + category +
                ", širina = " + width +
                ", visina = " + height +
                ", dužina = " + length +
                ", cijena proizvodnje = " + productionCost +
                ", maloprodajna cijena = " + sellingPrice +
                ", popust = " + discount +
                super.toString();
    }
}
