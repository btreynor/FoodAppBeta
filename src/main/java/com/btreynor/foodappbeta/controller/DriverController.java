package com.btreynor.foodappbeta.controller;

import com.btreynor.foodappbeta.exception.OrderAlreadyDeliverException;
import com.btreynor.foodappbeta.exception.OrderAlreadyFinishException;
import com.btreynor.foodappbeta.exception.OrderNotExistException;
import com.btreynor.foodappbeta.exception.OrderNotFinishedException;
import com.btreynor.foodappbeta.exception.PasswordNotMatchException;
import com.btreynor.foodappbeta.exception.UserAlreadyExistException;
import com.btreynor.foodappbeta.exception.UserNotExistException;
import com.btreynor.foodappbeta.model.Driver;
import com.btreynor.foodappbeta.model.Order;
import com.btreynor.foodappbeta.service.DriverServiceImpl;
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
@RequestMapping("/api/driver")
public class DriverController {

    private final DriverServiceImpl driverService;
    private final OrderServiceImpl orderService;

    @Autowired
    public DriverController(DriverServiceImpl driverService, OrderServiceImpl orderService) {
        this.driverService = driverService;
        this.orderService = orderService;
    }

    @GetMapping(path = "{id}")
    public Driver getDriverById(@PathVariable("id") String id)
            throws UserNotExistException {
        return driverService.getUser(id)
                .orElseThrow(() -> new UserNotExistException("User doesn't exist"));
    }

    @PostMapping(path = "/login")
    public Driver loginDriver(@RequestBody String jsonUser)
            throws UserNotExistException, JSONException {

        JSONObject user = new JSONObject(jsonUser);
        String userName = user.getString("userName");
        Optional<Driver> driver = driverService.getUserByName(userName);
        if (driver.isEmpty()) {
            throw new UserNotExistException("User doesn't exist");
        }
        return driver.get();
    }

    @PostMapping(path = "/register")
    public Driver registerDriver(@RequestBody String jsonUser)
            throws UserAlreadyExistException, JSONException {

        JSONObject user = new JSONObject(jsonUser);
        String userName = user.getString("userName");
        String phoneNumber = user.getString("phoneNumber");
        String address = user.getString("address");
        String city = user.getString("city");
        String state = user.getString("state");
        String zip = user.getString("zip");
        Driver driver = driverService
                .addUser(userName, phoneNumber, address, city, state, zip);
        if (driver == null) {
            throw new UserAlreadyExistException("User already exists, please login");
        }
        return driver;
    }

    @PostMapping(path = "/logout")
    public int logoutDriver() {
        System.out.println("logout the user");
        return 1;
    }

    @GetMapping(path = "/pendingOrders/" + "{id}")
    public List<Order> getPendingOrders(@PathVariable("id") String id)
            throws UserNotExistException {
        if (driverService.getUser(id).isEmpty()) {
            throw new UserNotExistException("User doesn't exist");
        }
        if (orderService.driverGetActiveOrder(id) != null) {
            System.out.println("Driver already has an active order");
            return null;
        }
        return orderService.getAllPendingOrders();
    }

    @GetMapping(path = "/myActiveOrder/" + "{id}")
    public Order getActiveOrder(@PathVariable("id") String id)
            throws UserNotExistException {
        if (driverService.getUser(id).isEmpty()) {
            throw new UserNotExistException("User doesn't exist");
        }
        return orderService.driverGetActiveOrder(id);
    }

    @GetMapping(path = "/myOrderHistory/" + "{id}")
    public List<Order> getOrderHistory(@PathVariable("id") String id)
            throws UserNotExistException {
        if (driverService.getUser(id).isEmpty()) {
            throw new UserNotExistException("User doesn't exist");
        }
        return orderService.driverFindPastOrders(id);
    }

    @PostMapping(path = "/accept")
    public int acceptOrder(@RequestBody String jsonOrder)
            throws UserNotExistException, OrderNotExistException, OrderAlreadyDeliverException, JSONException {
        JSONObject order = new JSONObject(jsonOrder);
        System.out.println(order);
        String orderId = order.getString("orderId");
        String driverId = order.getString("driverId");
        if (driverService.getUser(driverId).isEmpty()) {
            throw new UserNotExistException("User doesn't exist");
        }
        int res = orderService.acceptOrder(orderId, driverId);
        if (res == -1) {
            throw new OrderNotExistException("Order doesn't exist");
        }
        if (res == 0) {
            throw new OrderAlreadyDeliverException("Order in cart or already in delivery");
        }
        return res;
    }

    @PostMapping(path = "/finish")
    public int finishOrder(@RequestBody String jsonOrder)
            throws OrderNotExistException, OrderAlreadyFinishException, JSONException {
        JSONObject order = new JSONObject(jsonOrder);
        String id = order.getString("orderId");
        int res = orderService.finishOrder(id);
        if (res == -1) {
            throw new OrderNotExistException("Order doesn't exist");
        }
        if (res == 0) {
            throw new OrderAlreadyFinishException("Order already finished");
        }
        return res;
    }

    @DeleteMapping(path = "{id}")
    public int deleterDriver(@PathVariable("id") String id)
            throws UserNotExistException, OrderNotFinishedException {
        if (orderService.driverGetActiveOrder(id) != null) {
            throw new OrderNotFinishedException("You still have active orders, please finish them first");
        }
        int res = driverService.deleteUser(id);
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
        int res = driverService.updatePhoneNumber(id, phoneNumber);
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
        int res = driverService.updateAddress(id, address, city, state, zip);
        if (res == -1) {
            throw new UserNotExistException("User doesn't exist");
        }
        return res;
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({UserNotExistException.class, PasswordNotMatchException.class,
            UserAlreadyExistException.class, OrderNotExistException.class,
            OrderAlreadyDeliverException.class, OrderAlreadyFinishException.class,
            OrderNotFinishedException.class})
    public String handleException(Exception e) {
        return e.getMessage();
    }
}
