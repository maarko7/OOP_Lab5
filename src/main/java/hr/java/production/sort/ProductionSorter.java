package hr.java.production.sort;

import hr.java.production.model.Item;

import java.util.Comparator;

/**
 * Klasa koja implementira sučelje Comparator za sortiranje objekata klase Item po cijeni.
 */
public class ProductionSorter implements Comparator<Item> {
        private final boolean ascendingOrder;   //uzlazno 'true', silazno 'false'

        /**
         * Konstruktor koji omogućava odabir uzlaznog ili silaznog redoslijeda sortiranja.
         *
         * @param ascendingOrder true ako se sortira uzlazno, false ako se sortira silazno.
         */
        public ProductionSorter(boolean ascendingOrder) {
            this.ascendingOrder = ascendingOrder;
        }

        @Override
        public int compare(Item item1, Item item2) {

            int result = item1.getSellingPrice().compareTo(item2.getSellingPrice());

            return ascendingOrder ? result : -result;
        }

}
