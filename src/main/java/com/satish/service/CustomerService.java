package com.satish.service;

import com.satish.dto.AddressDto;
import com.satish.dto.CustomerDto;
import com.satish.entity.Customer;
import com.satish.mapper.AddressMapper;
import com.satish.mapper.CustomerMapper;
import com.satish.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    Logger log = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final AddressMapper addressMapper;

    public CustomerService(CustomerRepository customerRepository,
                           CustomerMapper customerMapper,
                           AddressMapper addressMapper){
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.addressMapper = addressMapper;

    }

    /**
     * Save a customer and it's addresses.
     *
     * @param customerDto a customer with it's addresses
     * @return the saved customer
     */
    public CustomerDto saveCustomer(CustomerDto customerDto){
        log.info("saveCustomer() service starts: CustomerDto: {}", customerDto);

        Customer customer = customerMapper.toEntity(customerDto);
        customer.getAddresses().clear();
        addAddress(customerDto, customer);
        customer = customerRepository.save(customer);
        return customerMapper.toDto(customer);
    }

    /**
     * Adds a list of addresses to a customer.
     *
     * @param customerDto the customer dto that contains the addresses to add
     * @param customer the customer to add the addresses to
     */
    private void addAddress(CustomerDto customerDto, Customer customer) {
        if(customerDto.getAddresses() != null && !customerDto.getAddresses().isEmpty()){
            for(AddressDto addressDto : customerDto.getAddresses()){
                customer.addAddress(addressMapper.toEntity(addressDto));
            }
        }
    }

    /**
     * Retrieve all customers.
     *
     * @return a list of all customers
     */
    public List<CustomerDto> getCustomersWithNPlusOneProblem() {
        log.info("getCustomersWithNPlusOneProblem() service starts : with N+1 Problem");
        return customerMapper.toDto(customerRepository.findAll());
    }


    public List<CustomerDto> getCustomersWithoutNPlusOneProblem() {
        log.info("==========================================================================");
        log.info("getCustomersWithoutNPlusOneProblem() service starts : without N+1 Problem");
        return customerMapper.toDto(customerRepository.findAllCustomersWithAddresses());
    }

    public List<CustomerDto> fetchCustomersWithAddresses() {
        log.info("==========================================================================");
        log.info("fetchCustomersWithAddresses() service starts : without N+1 Problem");
        return customerMapper.toDto(customerRepository.fetchCustomersWithAddresses());
    }
}
