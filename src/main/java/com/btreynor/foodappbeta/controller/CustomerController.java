package com.btreynor.foodappbeta.controller;

import com.btreynor.foodappbeta.exception.OrderNotFinishedException;
import com.btreynor.foodappbeta.exception.PasswordNotMatchException;
import com.btreynor.foodappbeta.exception.UserAlreadyExistException;
import com.btreynor.foodappbeta.exception.UserNotExistException;
import com.btreynor.foodappbeta.model.Customer;
import com.btreynor.foodappbeta.model.Order;
import com.btreynor.foodappbeta.service.CustomerServiceImpl;
import com.btreynor.foodappbeta.service.OrderServiceImpl;
import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerServiceImpl customerService;
    private final OrderServiceImpl orderService;

    @Autowired
    public CustomerController(CustomerServiceImpl customerService, OrderServiceImpl orderService) {
        this.customerService = customerService;
        this.orderService = orderService;
    }

    @GetMapping(path = "{id}")
    public Customer getCustomerById(@PathVariable("id") String id)
            throws UserNotExistException {
        return customerService.getUser(id)
                .orElseThrow(() -> new UserNotExistException("User doesn't exist"));
    }

    @PostMapping(path = "/login")
    public Customer loginCustomer(@RequestBody String jsonUser)
            throws UserNotExistException, PasswordNotMatchException, JSONException {

        JSONObject user = new JSONObject(jsonUser);
        String userName = user.getString("userName");
        Optional<Customer> customer = customerService.getUserByName(userName);
        if (customer.isEmpty()) {
            throw new UserNotExistException("User doesn't exist");
        }

        return customer.get();
    }

    @PostMapping(path = "/register")
    public Customer registerCustomer(@RequestBody String jsonUser)
            throws UserAlreadyExistException, JSONException {

        JSONObject user = new JSONObject(jsonUser);
        String userName = user.getString("userName");
        String phoneNumber = user.getString("phoneNumber");
        String address = user.getString("address");
        String city = user.getString("city");
        String state = user.getString("state");
        String zip = user.getString("zip");
        Customer customer = customerService
                .addUser(userName, phoneNumber, address, city, state, zip);
        if (customer == null) {
            throw new UserAlreadyExistException("User already exists, please login");
        }
        return customer;
    }

    @PostMapping(path = "/logout")
    public int logoutCustomer() {
        System.out.println("logout the user");
        return 1;
    }

    @GetMapping(path = "/myCart/" + "{id}")
    public List<Order> getShoppingCart(@PathVariable("id") String id)
            throws UserNotExistException {
        if (customerService.getUser(id).isEmpty()) {
            throw new UserNotExistException("User doesn't exist");
        }
        return orderService.customerCart(id);
    }

    @GetMapping(path = "/myActiveOrders/" + "{id}")
    public List<Order> getActiveOrders(@PathVariable("id") String id)
            throws UserNotExistException {
        if (customerService.getUser(id).isEmpty()) {
            throw new UserNotExistException("User doesn't exist");
        }
        return orderService.customerGetActiveOrders(id);
    }

    @GetMapping(path = "/myOrderHistory/" + "{id}")
    public List<Order> getOrderHistory(@PathVariable("id") String id)
            throws UserNotExistException {
        if (customerService.getUser(id).isEmpty()) {
            throw new UserNotExistException("User doesn't exist");
        }
        return orderService.customerFindPastOrders(id);
    }

    @DeleteMapping(path = "{id}")
    public int deleterCustomer(@PathVariable("id") String id)
            throws UserNotExistException, OrderNotFinishedException {
        if (orderService.customerGetActiveOrders(id).size() != 0) {
            throw new OrderNotFinishedException("You still have active orders, please finish them first");
        }
        int res = customerService.deleteUser(id);
        if (res == -1) {
            throw new UserNotExistException("User doesn't exist");
        }
        return res;
    }


    @PostMapping(path = "/resetPhone")
    public int resetPhoneNumber(@RequestBody String jsonPhone)
            throws UserNotExistException, JSONException {
        JSONObject object = new JSONObject(jsonPhone);
        String id = object.getString("id");
        String phoneNumber = object.getString("phoneNumber");
        int res = customerService.updatePhoneNumber(id, phoneNumber);
        if (res == -1) {
            throw new UserNotExistException("User doesn't exist");
        }
        return res;
    }

    @PostMapping(path = "/resetAddress")
    public int resetAddress(@RequestBody String jsonAddress)
            throws UserNotExistException, JSONException {
        JSONObject object = new JSONObject(jsonAddress);
        String id = object.getString("id");
        String address = object.getString("address");
        String city = object.getString("city");
        String state = object.getString("state");
        String zip = object.getString("zip");
        int res = customerService.updateAddress(id, address, city, state, zip);
        if (res == -1) {
            throw new UserNotExistException("User doesn't exist");
        }
        return res;
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({UserNotExistException.class, PasswordNotMatchException.class,
            UserAlreadyExistException.class, OrderNotFinishedException.class})
    public String handleException(Exception e) {
        return e.getMessage();
    }
}
