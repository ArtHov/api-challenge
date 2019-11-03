package com.jazva.challenge.service.impl;

import com.jazva.challenge.entity.Inventory;
import com.jazva.challenge.entity.Product;
import com.jazva.challenge.exeption.ex.InvalidArgumentRestException;
import com.jazva.challenge.resource.InventoryRepository;
import com.jazva.challenge.resource.ProductRepository;
import com.jazva.challenge.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public Map<String, Integer> getProductInventory(Long productId) {
        return productRepository.findOne(productId).getInventories().stream()
                .collect(Collectors.toMap(Inventory::getAddress, Inventory::getCount));
    }

    @Override
    @Transactional
    public Map<String, Integer> updateQuantity(String name, String location, Integer quantity) {
        Long productId = productRepository.findByName(name).getId();
        Inventory entity = inventoryRepository.findByAddressAndProductId(location, productId);

        Integer count = entity.getCount();
        entity.setCount(validateQuantityCount(count, quantity));
        Inventory savedInventory = inventoryRepository.save(entity);

        return Stream.of(savedInventory).collect(Collectors.toMap(Inventory::getAddress, Inventory::getCount));
    }

    @Override
    public Map<String, Integer> resetProductInventory(Long productId) {
        Product product = productRepository.findOne(productId);
        product.getInventories().forEach(inventory ->  inventory.setCount(0));
        Product save = productRepository.save(product);

        return save.getInventories().stream()
                .collect(Collectors.toMap(Inventory::getAddress, Inventory::getCount));
    }

    @Override
    public Integer getProductTotalInventory(Long productId) {
        int totalCount = 0;
        for (Inventory inventory :
                productRepository.findOne(productId).getInventories() ) {
            totalCount += inventory.getCount();
        }

        return totalCount;
    }

    /**
     * Validate received count of product for add or reduce
     *
     * @param count
     * @param quantity
     * @return
     */
    private int validateQuantityCount (Integer count, Integer quantity) {

        if (count + quantity < 0) {
            throw new InvalidArgumentRestException(count.toString());
        }
        return count + quantity;
    }
}
