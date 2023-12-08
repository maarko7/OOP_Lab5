package hr.java.production.model;

import java.util.Objects;

/**
 * Apstraktna klasa koja predstavlja entitet s imenom. Svaki entitet ima svoje ime.
 */
public abstract class NamedEntity {

    private String name;

    /**
     * Konstruktor koji inicijalizira objekt klase NamedEntity s navedenim imenom.
     *
     * @param name Ime entiteta
     */
    public NamedEntity(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NamedEntity that = (NamedEntity) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    @Override
//    public String toString() {
//        return "NamedEntity{" +
//                "name='" + name + '\'' +
//                '}';
//    }
}
