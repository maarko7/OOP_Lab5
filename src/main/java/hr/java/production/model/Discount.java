package hr.java.production.model;

import java.util.Objects;

/**
 * Rekordna klasa koja predstavlja popust za artikle. Popust je definiran cjelobrojnom vrijednošću discountAmount.
 */
public record Discount(Integer discountAmount) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discount discount = (Discount) o;
        return Objects.equals(discountAmount, discount.discountAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(discountAmount);
    }
}
