package com.btreynor.foodappbeta.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService<T> {

    T addUser(String userName, String phoneNumber, String address,
              String city, String state, String zip);

    int deleteUser(String id);

    Optional<T> getUser(String id);

    String getUserIdByName(String userName);

    Optional<T> getUserByName(String userName);

    List<T> getUsers();

    int updatePhoneNumber(String id, String newNumber);

    int updateAddress(String id, String address, String city, String state, String zip);
}
