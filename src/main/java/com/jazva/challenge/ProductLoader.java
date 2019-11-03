package com.jazva.challenge;

import com.jazva.challenge.entity.Inventory;
import com.jazva.challenge.entity.Product;
import com.jazva.challenge.resource.ProductRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Loads stored objects from the file system and builds up
 * the appropriate objects to add to the data source.
 *
 */
@Component
public class ProductLoader implements InitializingBean {
    @Value("classpath:data/products.txt")
    private Resource products;

    @Autowired
    DataSource dataSource;
    @Autowired
    ProductRepository repository;

    /**
     * Load the products into the data source after
     * the application is ready.
     *
     * @throws Exception In case something goes wrong while we load
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        try ( BufferedReader br = new BufferedReader(new InputStreamReader(products.getInputStream()))) {
            String line;
            Map<String, Product> products = new HashMap<>();
            while ((line = br.readLine()) != null) {
                String[] properties = line.split(" ");
                StringBuilder productName = new StringBuilder();
                productName.append(properties[1]).append(" ").append(properties[2]);

                Product product = new Product();
                product.setName(productName.toString());

                Inventory inventory = new Inventory();
                inventory.setAddress(properties[0]);
                if(properties.length > 3) {
                    inventory.setCount(Integer.parseInt(properties[3]));
                } else {
                    inventory.setCount(0);
                }

                if (!products.containsKey(productName.toString())) {
                    product.setInventories(new ArrayList<>(Collections.singleton(inventory)));
                    products.put(productName.toString(), product);
                } else {
                    products.get(productName.toString()).getInventories().add(inventory);
                }
            }
            for (Product product : products.values()) {
                repository.save(product);
            }
        }
    }

}
