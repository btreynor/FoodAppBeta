package com.btreynor.foodappbeta.model;

import jakarta.persistence.Entity;

@Entity
public class Driver extends User {

    public Driver() {
        this.setType("driver");
    }

    public Driver(String userName, String phoneNumber, String address,
                  String city, String state, String zip) {
        super(userName, phoneNumber, address, city, state, zip);
        this.setType("driver");
    }}


