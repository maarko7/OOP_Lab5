package hr.java.production.genericsi;

import hr.java.production.model.Item;
import hr.java.production.model.Store;
import hr.java.production.model.Technical;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TechnicalStore<T extends Technical> extends Store {

    private List<T> technicalItems;

    public TechnicalStore(String name, String webAddress, Set<Item> items, List<T> technicalItems) {
        super(name, webAddress, items);
        this.technicalItems = technicalItems;
    }

    public TechnicalStore(String name, String webAdress) {
        super(name, webAdress);
        this.technicalItems = new ArrayList<>();
    }

    public List<T> getTechnicalItems() {
        return technicalItems;
    }

    public void setTechnicalItems(List<T> technicalItems) {
        this.technicalItems = technicalItems;
    }

    public void addItem(T technicalItem) {
        technicalItems.add(technicalItem);
    }
}
