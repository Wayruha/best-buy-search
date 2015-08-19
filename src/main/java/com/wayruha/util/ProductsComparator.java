package com.wayruha.util;

import com.wayruha.model.ProductNote;

import java.util.Comparator;

/**
 * Created by Roman on 18.08.2015.
 */
public final class ProductsComparator implements Comparator{

    @Override
    public int compare(Object o1, Object o2) {
        ProductNote note1 = (ProductNote) o1;
        ProductNote note2 = (ProductNote) o2;
        if (note1.getPrice() < note2.getPrice()) return -1;
        else if (note1.getPrice() > note2.getPrice()) return 1;
        else return 0;
    }
}
