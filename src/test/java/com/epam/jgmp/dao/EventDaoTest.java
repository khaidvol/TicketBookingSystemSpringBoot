package com.epam.jgmp.dao;

import com.epam.jgmp.config.TestConfig;
import com.epam.jgmp.dao.implementation.EventDao;
import com.epam.jgmp.model.Event;
import com.epam.jgmp.storage.BookingStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class EventDaoTest {

  ApplicationContext context;
  BookingStorage bookingStorage;
  Dao<Event> eventDao;
  Event event;

  public EventDaoTest() {}

  @BeforeEach
  public void setUp() {

    context = new AnnotationConfigApplicationContext(TestConfig.class);
    bookingStorage = context.getBean(BookingStorage.class);
    eventDao = context.getBean(EventDao.class);

    event = Mockito.mock(Event.class);
    Mockito.when(event.getId()).thenReturn(10L);
  }

  @Test
  public void createTest() {
    Assertions.assertNull(eventDao.create(event));
    Assertions.assertEquals(event, eventDao.read(event.getId()));
  }

  @Test
  public void readTest() {
    Assertions.assertNull(eventDao.create(event));
    Assertions.assertEquals(event, eventDao.read(event.getId()));
  }

  @Test
  public void readAllTest() {
    Assertions.assertNull(eventDao.create(event));
    Assertions.assertNotNull(eventDao.readAll());
  }

  @Test
  public void updateTest() {
    Assertions.assertNull(eventDao.create(event));
    Assertions.assertEquals(event, eventDao.update(event));
  }

  @Test
  public void deleteTest() {
    Assertions.assertNull(eventDao.create(event));
    Assertions.assertEquals(event, eventDao.delete(event.getId()));
  }

  @Test
  public void getMaxIdWhenStorageNotEmptyTest() {
    Assertions.assertNull(eventDao.create(event));
    Assertions.assertNotNull(eventDao.getMaxId());
  }

  @Test
  public void getMaxIdWhenStorageIsEmptyTest() {
    bookingStorage.getEvents().clear();
    Assertions.assertNotNull(eventDao.getMaxId());
  }

  @AfterEach
  public void cleanUp() {
    bookingStorage.cleanStorage();
  }
}
