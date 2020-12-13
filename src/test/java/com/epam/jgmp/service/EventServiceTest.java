package com.epam.jgmp.service;

import com.epam.jgmp.config.TestConfig;
import com.epam.jgmp.exception.ApplicationException;
import com.epam.jgmp.model.Event;
import com.epam.jgmp.storage.BookingStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventServiceTest {

  ApplicationContext context;
  BookingStorage bookingStorage;
  EventService eventService;
  Event event;

  public EventServiceTest() {}

  @BeforeEach
  public void setUp() {
    context = new AnnotationConfigApplicationContext(TestConfig.class);
    eventService = context.getBean(EventService.class);
    bookingStorage = context.getBean(BookingStorage.class);
    event = Mockito.mock(Event.class);
  }

  @Test
  public void getEventByIdTest() {
    Assertions.assertEquals(event, eventService.createEvent(event));
    Assertions.assertEquals(event, eventService.getEventById(event.getId()));
  }

  @Test
  public void getNotExistingEventByIdTest() {
    Assertions.assertThrows(ApplicationException.class, () -> eventService.getEventById(1000L));
  }

  @Test
  public void getEventsByTitleTest() {
    Assertions.assertNotNull(eventService.getEventsByTitle("Disco", 1, 1));
  }

  @Test
  public void getEventForDayTest() throws ParseException {
    String inputString = "2020-06-28";
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date inputDate = dateFormat.parse(inputString);
    Assertions.assertNotNull(eventService.getEventsForDay(inputDate, 1, 1));
  }

  @Test
  public void createEventTest() {
    Assertions.assertEquals(event, eventService.createEvent(event));
  }

  @Test
  public void updateEventTest() {
    Assertions.assertEquals(event, eventService.createEvent(event));
    Assertions.assertEquals(event, eventService.updateEvent(event));
  }

  @Test
  public void updateNotExistingEventTest() {
    Mockito.when(event.getId()).thenReturn(100L);
    Assertions.assertThrows(ApplicationException.class, () -> eventService.updateEvent(event));
  }

  @Test
  public void deleteEventTest() {
    Assertions.assertEquals(event, eventService.createEvent(event));
    Assertions.assertTrue(eventService.deleteEvent(event.getId()));
  }

  @AfterEach
  public void cleanUp() {
    bookingStorage.cleanStorage();
  }
}
