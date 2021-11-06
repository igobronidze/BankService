package com.egs.bankservice.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.egs.bankservice.controller.model.user.UserAddRequest;
import com.egs.bankservice.controller.model.user.UserResponse;
import com.egs.bankservice.service.users.UsersService;
import com.egs.bankservice.service.users.UsersServiceImpl;

@RestController
@RequestMapping("api/users")
public class UsersController {

    private final UsersService usersService;

    @Autowired
    public UsersController(UsersServiceImpl usersService) {
        this.usersService = usersService;
    }

    @PostMapping("add")
    public long addUser(@RequestBody UserAddRequest userAddRequest) {
        return usersService.addUser(userAddRequest);
    }

    @GetMapping("get/{id}")
    public UserResponse getUserById(@PathVariable(value = "id") long id) {
        return usersService.getUserById(id);
    }

    @GetMapping("getByPersonId")
    public UserResponse getUserByPersonalId(@RequestParam(value = "personalId") String personalId) {
        return usersService.getUserByPersonalId(personalId);
    }

    @DeleteMapping("delete/{id}")
    public void deleteUser(@PathVariable(value = "id") long id) {
        usersService.deleteUser(id);
    }
}
