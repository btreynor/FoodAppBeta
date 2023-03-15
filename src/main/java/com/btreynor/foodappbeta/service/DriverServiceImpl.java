package com.btreynor.foodappbeta.service;

import com.btreynor.foodappbeta.model.Driver;
import com.btreynor.foodappbeta.repository.DriverRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverServiceImpl implements UserService<Driver> {

    @Autowired
    DriverRepository driverRepository;

    @Override
    public Driver addUser(String userName, String phoneNumber, String address,
                          String city, String state, String zip) {
        if (this.getUserIdByName(userName) == null) {
            Driver driver = new Driver(userName, phoneNumber, address, city, state, zip);
            driverRepository.save(driver);
            System.out.println("Driver added to the database");
            return driver;
        }
        System.out.println("Driver can't be added to the database");
        return null;
    }

    @Override
    public int deleteUser(String id) {
        if (this.getUser(id).isPresent()) {
            driverRepository.deleteById(id);
            System.out.println("Driver deleted from the database");
            return 1;
        }
        System.out.println("Driver can't be deleted from the database");
        return -1;
    }

    @Override
    public Optional<Driver> getUser(String id) {
        if (id != null) {
            return driverRepository.findById(id);
        }
        return Optional.empty();
    }

    @Override
    public String getUserIdByName(String userName) {
        List<Driver> drivers = this.getUsers();
        for (Driver driver : drivers) {
            if (driver.getUserName().equals(userName)) {
                return driver.getId();
            }
        }
        System.out.println("Given userName doesn't found in driver database");
        return null;
    }

    @Override
    public Optional<Driver> getUserByName(String userName) {
        return this.getUser(getUserIdByName(userName));
    }

    @Override
    public List<Driver> getUsers() {
        return driverRepository.findAll();
    }


    @Override
    public int updatePhoneNumber(String id, String newNumber) {
        Optional<Driver> driver = this.getUser(id);
        if (driver.isPresent()) {
            driver.get().setPhoneNumber(newNumber);
            driverRepository.save(driver.get());
            System.out.println("Update the phone number");
            return 1;
        }
        System.out.println("Can't update the phone number");
        return -1;
    }

    @Override
    public int updateAddress(String id, String address, String city, String state,
                             String zip) {
        Optional<Driver> driver = this.getUser(id);
        if (driver.isPresent()) {
            driver.get().setAddress(address);
            driver.get().setCity(city);
            driver.get().setState(state);
            driver.get().setZip(zip);
            driverRepository.save(driver.get());
            System.out.println("Update the address");
            return 1;
        }
        System.out.println("Can't update the address");
        return -1;
    }
}
