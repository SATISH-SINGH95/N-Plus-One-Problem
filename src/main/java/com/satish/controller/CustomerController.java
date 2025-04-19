package com.satish.controller;

import com.satish.dto.CustomerDto;
import com.satish.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {

    Logger log = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Saves a customer and their addresses.
     * @param customerDto the customer and addresses to save
     * @return the saved customer
     */
    @PostMapping("/customer")
    public CustomerDto saveCustomer(@RequestBody CustomerDto customerDto) {
        log.info("saveCustomer() controller starts: CustomerDto: {}", customerDto);
        return customerService.saveCustomer(customerDto);
    }

    /**
     * Retrieve all customers with N+1 problem.
     *
     * @return a list of all customers
     */
    @GetMapping("/customers/with-N-PlusOne-Problem")
    public List<CustomerDto> getCustomersWithNPlusOneProblem() {
        log.info("getCustomersWithNPlusOneProblem() controller starts : with N+1 Problem");
        return customerService.getCustomersWithNPlusOneProblem();
    }

    /**
     * Retrieve all customers without N+1 problem.
     *
     * @return a list of all customers
     */
    @GetMapping("/customers/without-N-PlusOne-Problem")
    public List<CustomerDto> getCustomersWithoutNPlusProblem() {
        log.info("getCustomersWithoutNPlusProblem() controller starts : without N+1 Problem");
        return customerService.getCustomersWithoutNPlusOneProblem();
    }

    @GetMapping("/fetch/customers/without-N-PlusOne-Problem")
    public List<CustomerDto> fetchCustomersWithAddresses() {
        log.info("fetchCustomersWithAddresses() controller starts : without N+1 Problem");
        return customerService.fetchCustomersWithAddresses();
    }

}
