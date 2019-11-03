package com.jazva.challenge.resource;

import com.jazva.challenge.entity.Inventory;
import org.springframework.data.repository.CrudRepository;

public interface InventoryRepository extends CrudRepository<Inventory, Long> {

    Inventory findByAddressAndProductId(String location, Long productId);
}
