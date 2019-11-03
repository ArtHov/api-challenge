package com.jazva.challenge.controller;

import com.jazva.challenge.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controller will handle requests to the products service... Contains
 * appropriate rest endpoints.
 *
 * @author Artur
 */
@RestController
@RequestMapping(path = "/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * Get product quantity by the given product id.
     *
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Map<String, Integer> getProductInventory(@PathVariable(value = "id") Long id) {
        return productService.getProductInventory(id);
    }

    /**
     * Update product quantity for given location
     *
     * @param name
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/{name}/location/{location}")
    public Map<String, Integer> getProductInventories(@PathVariable(value = "name") String name,
                                              @PathVariable(value = "location") String location,
                                              @RequestBody Integer quantity) {
        return productService.updateQuantity(name, location, quantity);
    }

    /**
     * Reset product quantity by the given product id.
     *
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}/reset")
    public Map<String, Integer> resetProductInventory(@PathVariable(value = "id") Long id) {
        return productService.resetProductInventory(id);
    }

    /**
     * Get Product Total Inventory  by the given product id.
     *
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}/total/inventory")
    public Integer getProductTotalInventory(@PathVariable(value = "id") Long id) {
        return productService.getProductTotalInventory(id);
    }
}
