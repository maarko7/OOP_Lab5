package hr.java.production.model;

/**
 * Sučelje koje označava da objekt može biti konzumiran i ima metode za izračun kalorija i cijene.
 */
public interface Edible {

    /**
     * Metoda za izračun broja kalorija.
     *
     * @return Broj kalorija koji objekt pruža.
     */
    Integer calculateKilocalories();

    /**
     * Metoda za izračun cijene na temelju težine.
     *
     * @param weight Težina objekta.
     * @return Cijena objekta na temelju zadane težine.
     */
    Double calculatePrice(double weight);
}

