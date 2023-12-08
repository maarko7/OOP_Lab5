package hr.java.production.model;

import hr.java.production.enums.City;

import java.util.Objects;

/**
 * Klasa koja predstavlja adresu sačinjenu od ulice, kućnog broja, grada i poštanskog broja.
 */
public class Address {

    private String street;
    private String houseNumber;
    private City city;

    /**
     * Konstruktor koji inicijalizira objekt klase Address s navedenim informacijama.
     *
     * @param street      Naziv ulice
     * @param houseNumber Kućni broj
     * @param city        Grad (enumeracija "City")
     */
    public Address(String street, String houseNumber, City city) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(street, address.street) && Objects.equals(houseNumber, address.houseNumber) && city == address.city;
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, houseNumber, city);
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
