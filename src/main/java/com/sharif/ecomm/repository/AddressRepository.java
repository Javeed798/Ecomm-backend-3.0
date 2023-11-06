package com.sharif.ecomm.repository;

import com.sharif.ecomm.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
