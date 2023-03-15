package com.btreynor.foodappbeta.service;

import com.btreynor.foodappbeta.model.Customer;
import com.btreynor.foodappbeta.repository.CustomerRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


@Service
public class CustomerServiceImpl implements UserService<Customer> {

    @Autowired
    CustomerRepository customerRepository;


    @Override
    public Customer addUser(String userName, String phoneNumber, String address,
                            String city, String state, String zip) {
        if (this.getUserIdByName(userName) == null) {

            Customer customer = new Customer(userName, phoneNumber, address, city, state, zip);
            customerRepository.save(customer);
            System.out.println("Customer added to the database");
            return customer;
        }
        System.out.println("Customer can't be added to the database");
        return null;
    }

    @Override
    public int deleteUser(String id) {
        if (this.getUser(id).isPresent()) {
            customerRepository.deleteById(id);
            System.out.println("Customer deleted from the database");
            return 1;
        }
        System.out.println("Customer can't be deleted from the database");
        return -1;
    }

    @Override
    public Optional<Customer> getUser(String id) {
        if (id != null) {
            return customerRepository.findById(id);
        }
        return Optional.empty();
    }

    @Override
    public String getUserIdByName(String userName) {
        List<Customer> customers = this.getUsers();
        for (Customer customer : customers) {
            if (customer.getUserName().equals(userName)) {
                return customer.getId();
            }
        }
        System.out.println("Given userName doesn't found in customer database");
        return null;
    }

    @Override
    public Optional<Customer> getUserByName(String userName) {
        return this.getUser(getUserIdByName(userName));
    }

    @Override
    public List<Customer> getUsers() {
        return customerRepository.findAll();
    }

    @Override
    public int updatePhoneNumber(String id, String newNumber) {
        Optional<Customer> customer = this.getUser(id);
        if (customer.isPresent()) {
            customer.get().setPhoneNumber(newNumber);
            customerRepository.save(customer.get());
            System.out.println("Update the phone number");
            return 1;
        }
        System.out.println("Can't update the phone number");
        return -1;
    }

    @Override
    public int updateAddress(String id, String address, String city, String state,
                             String zip) {
        Optional<Customer> customer = this.getUser(id);
        if (customer.isPresent()) {
            customer.get().setAddress(address);
            customer.get().setCity(city);
            customer.get().setState(state);
            customer.get().setZip(zip);
            customerRepository.save(customer.get());
            System.out.println("Update the address");
            return 1;
        }
        System.out.println("Can't update the address");
        return -1;
    }
}
