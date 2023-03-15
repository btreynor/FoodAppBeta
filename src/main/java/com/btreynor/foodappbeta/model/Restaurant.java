package com.btreynor.foodappbeta.model;

import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class Restaurant extends User {
    private RestaurantInfo information;
    private List<Dish> menu;

    public Restaurant() {
        this.setType("restaurant");
    }

    public Restaurant(String userName, String phoneNumber, String address,
                      String city, String state, String zip, RestaurantInfo information,
                      List<Dish> menu) {
        super(userName, phoneNumber, address, city, state, zip);
        this.setType("restaurant");
        this.information = information;
        this.menu = menu;
    }

    public Restaurant(String userName, String phoneNumber, String address,
                      String city, String state, String zip) {
        super(userName, phoneNumber, address, city, state, zip);
        this.setType("restaurant");
    }

    public RestaurantInfo getInformation() {
        return information;
    }

    public void setInformation(RestaurantInfo information) {
        this.information = information;
    }

    public List<Dish> getMenu() {
        return menu;
    }

    public void setMenu(List<Dish> menu) {
        this.menu = menu;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "information=" + information +
                ", menu=" + menu +
                '}';
    }
}
