package com.jazva.challenge.service;

import com.jazva.challenge.entity.Inventory;
import com.jazva.challenge.entity.Product;
import com.jazva.challenge.exeption.ex.InvalidArgumentRestException;
import com.jazva.challenge.resource.InventoryRepository;
import com.jazva.challenge.resource.ProductRepository;
import com.jazva.challenge.service.impl.ProductServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;

/**
 * Test for {@link ProductServiceImpl}.
 *
 * @author Artur
 */
@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {

    private static final String LOCATION = "Gyumri";

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private InventoryRepository inventoryRepository;

    /**
     * Testing the getProductInventory() method of the ProductService class.
     */
    @Test
    public void testGetProductInventory() {
        Product product = createProduct();
        Inventory expectedInventory = product.getInventories().get(0);
        Mockito.when(productRepository.findOne(1l)).thenReturn(product);

        Map<String, Integer> inventory = productService.getProductInventory(1l);

        assertEquals(inventory.containsKey(expectedInventory.getAddress()), true);
        assertEquals(inventory.containsValue(expectedInventory.getCount()), true);
    }

    /**
     * Testing the updateQuantity() method of the ProductService class.
     */
    @Test
    public void testUpdateQuantity() {
        Product product = createProduct();
        Inventory expectedInventory = product.getInventories().get(0);
        Mockito.when(productRepository.findByName("asd")).thenReturn(product);
        Mockito.when(inventoryRepository.findByAddressAndProductId(any(String.class), any(Long.class)))
                .thenReturn(expectedInventory);
        Mockito.when(inventoryRepository.save(any(Inventory.class))).thenReturn(expectedInventory);

        Map<String, Integer> inventory = productService.updateQuantity("asd", LOCATION, 5);

        assertEquals(inventory.get(LOCATION).intValue(), 9);
    }

    /**
     * Testing the updateQuantity() method validation for Invalid argument of the ProductService class.
     */
    @Test(expected = InvalidArgumentRestException.class)
    public void testUpdateQuantityException() {
        Product product = createProduct();
        Inventory expectedInventory = product.getInventories().get(0);
        Mockito.when(productRepository.findByName("asd")).thenReturn(product);
        Mockito.when(inventoryRepository.findByAddressAndProductId(any(String.class), any(Long.class)))
                .thenReturn(expectedInventory);
        Mockito.when(inventoryRepository.save(any(Inventory.class))).thenReturn(expectedInventory);

        Map<String, Integer> inventory = productService.updateQuantity("asd", LOCATION, -5);
    }

    /**
     * Testing the resetProductInventory() method of the ProductService class.
     */
    @Test
    public void testResetProductInventory() {
        Product product = createProduct();
        Mockito.when(productRepository.findOne(1l)).thenReturn(product);
        Mockito.when(productRepository.save(any(Product.class))).thenReturn(product);

        Map<String, Integer> inventory = productService.resetProductInventory(1l);

        assertEquals(inventory.get(LOCATION).intValue(), 0);

    }

    /**
     * Testing the getProductTotalInventory() method of the ProductService class.
     */
    @Test
    public void testGetProductTotalInventory() {
        Product product = createProduct();
        Mockito.when(productRepository.findOne(1l)).thenReturn(product);

        int totalCount = productService.getProductTotalInventory(1l);

        assertEquals(totalCount, 11);
    }

    /**
     * Create test object.
     */
    private Product createProduct() {
        Product product = new Product();
        Inventory inventory1 = new Inventory();
        inventory1.setCount(4);
        inventory1.setAddress(LOCATION);
        Inventory inventory2 = new Inventory();
        inventory2.setCount(7);
        inventory2.setAddress("Erevan");
        product.setInventories(Arrays.asList(inventory1,inventory2));

        return product;
    }
}