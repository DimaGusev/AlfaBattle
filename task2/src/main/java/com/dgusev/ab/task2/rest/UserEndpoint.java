package com.dgusev.ab.task2.rest;

import com.dgusev.ab.task2.exception.UserNotFoundException;
import com.dgusev.ab.task2.model.UserAnalitic;
import com.dgusev.ab.task2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserEndpoint {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/admin/health")
    public String health() {
        return "{\"status\":\"UP\"}";
    }

    @GetMapping(value = "/analytic")
    public List<UserAnalitic> getAnalytics() {
        List<UserAnalitic> result = userService.getAnalitics();
        return result;
    }

    @GetMapping(value = "/analytic/{userId}")
    public UserAnalitic getAnalytics(@PathVariable String userId) {
        UserAnalitic result = userService.getByUserId(userId);
        if (result == null) {
            throw new UserNotFoundException();
        }
        return result;
    }
}
