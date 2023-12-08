package hr.java.production.model;

/**
 * Zatvoreno sučelje koje označava tehničke artikle i definira trajanje jamstva u mjesecima.
 * Dozvoljava implementaciju samo za podsučelje Laptop.
 */
public sealed interface Technical permits Laptop {

    /**
     * Vraća trajanje jamstva u mjesecima.
     *
     * @return Trajanje jamstva.
     */
    Integer warrantyMonths();
}
