package edu.northeastern.afinal.ui.product.comparator;

import java.util.Comparator;

import edu.northeastern.afinal.ui.product.ProductItemCard;

public class ProductPriceComparator implements Comparator<ProductItemCard> {
    @Override
    public int compare(ProductItemCard o1, ProductItemCard o2) {
        return Double.compare(o1.getPrice(),o2.getPrice());
    }
}