package hr.java.production.sort;

import hr.java.production.model.Item;

import java.util.Comparator;

public class VolumeComparator implements Comparator<Item> {

    @Override
    public int compare(Item o1, Item o2) {
        return o1.calculateVolume().compareTo(o2.calculateVolume());
    }
}
