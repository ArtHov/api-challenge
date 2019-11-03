package com.jazva.challenge.service;

import java.util.Map;

/**
 * Provides services for getting Products.
 *
 * @author Artur
 */
public interface ProductService {

    /**
     * Finds quantity of a product item by the product id.
     *
     * @param productId
     * @return
     */
    Map<String, Integer> getProductInventory(Long productId);

    /**
     * Update the quantity of a product in specified location.
     *
     * @param name
     * @return
     */
    Map<String, Integer> updateQuantity(String name, String location, Integer quantity);

    /**
     * Reset quantity of a product item by the product id.
     *
     * @param productId
     * @return
     */
    Map<String, Integer> resetProductInventory(Long productId);

    /**
     * Get total inventory count of a product item by the product id.
     *
     * @param productId
     * @return
     */
    Integer getProductTotalInventory(Long productId);
}
