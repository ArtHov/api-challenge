package com.jazva.challenge.resource;

import com.jazva.challenge.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {

    Product findByName(String name);
}
