package hr.java.production.model;

import java.util.Objects;

/**
 * Klasa koja predstavlja kategoriju za artikle. Svaka kategorija ima ime i opis.
 */
public class Category extends NamedEntity {

    private String description;

    /**
     * Konstruktor koji inicijalizira objekt klase Category s navedenim imenom i opisom.
     *
     * @param name        Ime kategorije
     * @param description Opis kategorije
     */
    public Category(String name, String description) {
        super(name);
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Category category = (Category) o;
        return Objects.equals(description, category.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), description);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return getName();
    }
}
