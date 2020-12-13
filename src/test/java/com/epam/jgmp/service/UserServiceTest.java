package com.epam.jgmp.service;

import com.epam.jgmp.config.TestConfig;
import com.epam.jgmp.exception.ApplicationException;
import com.epam.jgmp.model.User;
import com.epam.jgmp.storage.BookingStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class UserServiceTest {

  ApplicationContext context;
  BookingStorage bookingStorage;
  UserService userService;
  User user;

  public UserServiceTest() {}

  @BeforeEach
  public void setUp() {
    context = new AnnotationConfigApplicationContext(TestConfig.class);
    userService = context.getBean(UserService.class);
    bookingStorage = context.getBean(BookingStorage.class);
    user = Mockito.mock(User.class);
  }

  @Test
  public void getUserByIdTest() {
    Assertions.assertEquals(user, userService.createUser(user));
    Assertions.assertEquals(user, userService.getUserById(user.getId()));
  }

  @Test
  public void getNotExistingUserByIdTest() {
    Assertions.assertThrows(ApplicationException.class, () -> userService.getUserById(1000L));
  }

  @Test
  public void getUserByEmailTest() {
    Assertions.assertNotNull(userService.getUserByEmail("jack@gmail.com"));
  }

  @Test
  public void getNotExistingUserByEmailTest() {
    Assertions.assertThrows(ApplicationException.class, () -> userService.getUserByEmail("test@gmail.com"));
  }

  @Test
  public void getUsersByNameTest() {
    Assertions.assertNotNull(userService.getUsersByName("Jack", 1, 1));
  }

  @Test
  public void createUserTest() {
    Assertions.assertEquals(user, userService.createUser(user));
  }

  @Test
  public void createUserWithUsedEmailTest() {
    Mockito.when(user.getEmail()).thenReturn("jack@gmail.com");
    Assertions.assertThrows(ApplicationException.class, () -> userService.createUser(user));
  }

  @Test
  public void updateUserTest() {
    userService.createUser(user);
    Assertions.assertEquals(user, userService.updateUser(user));
  }

  @Test
  public void updateNotExistingUserTest() {
    Mockito.when(user.getId()).thenReturn(100L);
    Assertions.assertThrows(ApplicationException.class, () -> userService.updateUser(user));
  }

  @Test
  public void deleteUserTest() {
    Assertions.assertTrue(userService.deleteUser(2));
  }

  @AfterEach
  public void cleanUp() {
    bookingStorage.cleanStorage();
  }
}
