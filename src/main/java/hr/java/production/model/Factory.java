package hr.java.production.model;

import java.util.Objects;
import java.util.Set;

/**
 * Klasa koja predstavlja tvornicu. Svaka tvornica ima ime, adresu i niz artikala koje proizvodi.
 */
public class Factory extends NamedEntity {

//    private String name;
    private Address address;
    private Set<Item> items;

    /**
     * Konstruktor koji inicijalizira objekt klase Factory s navedenim imenom, adresom i artiklima.
     *
     * @param name    Ime tvornice
     * @param address Adresa tvornice
     * @param items   Niz artikala koje tvornica proizvodi
     */
    public Factory(String name, Address address, Set<Item> items) {
        super(name);
        this.address = address;
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Factory factory = (Factory) o;
        return Objects.equals(address, factory.address) && Objects.equals(items, factory.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), address, items);
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }
}
