package hr.java.production.model;

import java.util.*;

/**
 * Klasa koja predstavlja trgovinu. Svaka trgovina ima ime, web adresu i niz artikala koje prodaje.
 */
public class Store extends NamedEntity {

//    private String name;
    private String webAddress;
    private Set<Item> items;

    /**
     * Konstruktor koji inicijalizira objekt klase Store s navedenim imenom, web adresom i artiklima.
     *
     * @param name       Ime trgovine
     * @param webAddress Web adresa trgovine
     * @param items      Niz artikala koje trgovina prodaje
     */
    public Store(String name, String webAddress, Set<Item> items) {
        super(name);
        this.webAddress = webAddress;
        this.items = items;
    }

    public Store(String name, String webAdress) {
        super(name);
        this.webAddress = webAdress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Store store = (Store) o;
        return Objects.equals(webAddress, store.webAddress) && Objects.equals(items, store.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), webAddress, items);
    }

    public String getWebAddress() {
        return webAddress;
    }

    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

//    public void sortItemsByVolume() {
//        List<Item> itemList = new ArrayList<>(items);
//        itemList.sort(Comparator.comparing(Item::calculateVolume));
//
//        System.out.println("Sortirani artikli po volumenu:");
//        for (Item item : itemList) {
//            System.out.println("Naziv: " + item.getName() + ", Volumen: " + item.calculateVolume());
//        }
//
//        items = new HashSet<>(itemList);
//    }
}
