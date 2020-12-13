package com.epam.jgmp.dao;

import com.epam.jgmp.config.TestConfig;
import com.epam.jgmp.dao.implementation.UserDao;
import com.epam.jgmp.model.User;
import com.epam.jgmp.storage.BookingStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class UserDaoTest {

  ApplicationContext context;
  BookingStorage bookingStorage;
  Dao<User> userDao;
  User user;

  public UserDaoTest() {}

  @BeforeEach
  public void setUp() {

    context = new AnnotationConfigApplicationContext(TestConfig.class);
    bookingStorage = context.getBean(BookingStorage.class);
    userDao = context.getBean(UserDao.class);

    user = Mockito.mock(User.class);
    Mockito.when(user.getId()).thenReturn(10L);
  }

  @Test
  public void createTest() {
    Assertions.assertNull(userDao.create(user));
    Assertions.assertEquals(user, userDao.read(user.getId()));
  }

  @Test
  public void readTest() {
    Assertions.assertNull(userDao.create(user));
    Assertions.assertEquals(user, userDao.read(user.getId()));
  }

  @Test
  public void readAllTest() {
    Assertions.assertNull(userDao.create(user));
    Assertions.assertNotNull(userDao.readAll());
  }

  @Test
  public void updateTest() {
    Assertions.assertNull(userDao.create(user));
    Assertions.assertEquals(user, userDao.update(user));
  }

  @Test
  public void deleteTest() {
    Assertions.assertNull(userDao.create(user));
    Assertions.assertEquals(user, userDao.delete(user.getId()));
  }

  @Test
  public void getMaxIdWhenStorageNotEmptyTest() {
    Assertions.assertNull(userDao.create(user));
    Assertions.assertNotNull(userDao.getMaxId());
  }

  @Test
  public void getMaxIdWhenStorageIsEmptyTest() {
    bookingStorage.getUsers().clear();
    Assertions.assertNotNull(userDao.getMaxId());
  }

  @AfterEach
  public void cleanUp() {
    bookingStorage.cleanStorage();
  }
}
