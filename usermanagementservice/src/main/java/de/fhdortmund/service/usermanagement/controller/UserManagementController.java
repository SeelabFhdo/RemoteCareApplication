package de.fhdortmund.service.usermanagement.controller;

import com.google.gson.Gson;
import com.sun.javaws.exceptions.InvalidArgumentException;
import de.fhdortmund.service.usermanagement.enumeration.UserRole;
import de.fhdortmund.service.usermanagement.service.UserService;
import java.security.InvalidParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import de.fhdortmund.service.usermanagement.entities.User;

import java.security.Principal;
import sun.plugin.dom.exception.InvalidAccessException;


/**
 * Created by phil on 29.12.16.
 */
@RestController
@RequestMapping(value = "/resources/user", produces = "application/json")
public class UserManagementController {

  private UserService userService;

  @Autowired
  public UserManagementController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Request mapping for creating a user
   *
   * @param email email adress of the user
   * @param surName lastname of the user
   * @param firstName firstname of the user
   * @param username username, must be unique
   * @param password password of the user
   * @param userRoleValue role of the new user
   * @return id of the user
   */
  @PutMapping
  public String register(Principal principal,
      @RequestParam String email,
      @RequestParam String surName,
      @RequestParam String firstName,
      @RequestParam String username,
      @RequestParam String password,
      @RequestParam String userRoleValue) {

    if (this.isAdmin(principal.getName()) == false) {
      throw new InvalidAccessException("Only Admins are allowed to register nur Users.");
    }

    if (!email.contains("@")) {
      throw new InvalidParameterException("Check the Email address.");
    }

    if (userService.findByEmail(email) != null) {
      throw new InvalidParameterException("Email in use!");
    } else if (userService.findByUsername(username) != null) {
      throw new InvalidParameterException("Username in use!");
    } else {
      UserRole userRole = UserRole.USER;
      if ("admin".equals(userRoleValue.toLowerCase()) == true) {
        userRole = UserRole.ADMIN;
      }

      User user = new User();
      user.setEmail(email);
      user.setSurname(surName);
      user.setFirstname(firstName);
      user.setUsername(username);
      user.setPassword(password);
      user.setUserRole(userRole);
      user = userService.saveUser(user);
      Gson gson = new Gson();
      return gson.toJson(user);
    }
  }

  /**
   * Request mapping for deleting a user
   *
   * @param id of the useraccount
   * @return status message
   */
  @DeleteMapping("/{id}")
  @ResponseBody
  public String delete(@PathVariable long id) {
    try {
      User user = new User(id);
      userService.deleteUser(user);
    } catch (Exception ex) {
      return "Error deleting the user:" + ex.toString();
    }
    return "User succesfully deleted!";
  }

  /**
   * Request mapping for getting the user by username
   *
   * @param username of the user
   * @return user object
   */
  @GetMapping("/{username}")
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<String> getByUsername(Principal principal, @PathVariable String username) {
    User user = new User();
    try {
      user = userService.findByUsername(username);
    } catch (Exception ex) {
      return null;
    }
    Gson gson = new Gson();
    return ResponseEntity.ok(gson.toJson(user));
  }

  /**
   * Request mapping for the user role
   *
   * @param principal of the user
   * @return Message and Status
   */
  @GetMapping("/rest/user/role")
  @ResponseBody
  public ResponseEntity<String> getUserRole(Principal principal, String userName) {
    User user = new User();
    try {
      user = userService.findByUsername(principal.getName());
    } catch (Exception exception) {
      exception.printStackTrace();
      return null;
    }

    if (user != null) {
      Gson gson = new Gson();
      return ResponseEntity.ok(gson.toJson(user.getUserRole()));
    }
    return ResponseEntity.badRequest().body("User not Found");
  }

  /**
   * Changes the user Data
   *
   * @param principal login user
   */
  @PostMapping
  @ResponseBody
  public String modifyUser(Principal principal, String email, String surName, String firstName,
      String username, String password, String userRoleValue) {
    User user = userService.findByUsername(username);
    if (user != null) {
      UserRole userRole = UserRole.USER;
      if ("admin".equals(userRoleValue.toLowerCase()) == true) {
        userRole = UserRole.ADMIN;
      }
      user.setEmail(email);
      user.setSurname(surName);
      user.setFirstname(firstName);
      user.setPassword(password);
      user.setUserRole(userRole);
      userService.saveUser(user);
      Gson gson = new Gson();
      return gson.toJson(user);
    }

    throw new InvalidParameterException("Wrong Username.");
  }

  private boolean isAdmin(String userName) {
    User user = userService.findByUsername(userName);
    if (user.getUserRole() == UserRole.ADMIN) {
      return true;
    }
    return false;
  }
}
