package com.satish.repository;

import com.satish.entity.Customer;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Custom method to avoid N+1 problem by using EntityGraph
    @EntityGraph(attributePaths = {"addresses"})
    @Query("SELECT c FROM Customer c")
    List<Customer> findAllCustomersWithAddresses();

    @Query("SELECT c FROM Customer c LEFT JOIN FETCH c.addresses")
    List<Customer> fetchCustomersWithAddresses();
}
