package com.epam.jgmp.service;


import com.epam.jgmp.config.TestConfig;
import com.epam.jgmp.exception.ApplicationException;
import com.epam.jgmp.model.Event;
import com.epam.jgmp.model.Ticket;
import com.epam.jgmp.model.User;
import com.epam.jgmp.storage.BookingStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TicketServiceTest {

  ApplicationContext context;
  BookingStorage bookingStorage;
  TicketService ticketService;
  Ticket ticket;

  public TicketServiceTest() {}

  @BeforeEach
  public void setUp() {
    context = new AnnotationConfigApplicationContext(TestConfig.class);
    ticketService = context.getBean(TicketService.class);
    bookingStorage = context.getBean(BookingStorage.class);
    ticket = Mockito.mock(Ticket.class);
  }

  @Test
  public void bookTicketTest() {
    Assertions.assertNotNull(ticketService.bookTicket(1, 1, 111, Ticket.Category.STANDARD));
  }

  @Test
  public void bookTicketForAlreadyBookedPlaceTest() {
    Assertions.assertThrows(ApplicationException.class, () -> ticketService.bookTicket(1, 1, 1, Ticket.Category.STANDARD));
  }

  @Test
  public void getTicketsByUserTest() {
    User user = Mockito.mock(User.class);
    Mockito.when(user.getId()).thenReturn(1L);
    Assertions.assertEquals(3, ticketService.getBookedTickets(user, 1, 1).size());
  }

  @Test
  public void getTicketsByEventTest() {
    Event event = Mockito.mock(Event.class);
    Mockito.when(event.getId()).thenReturn(1L);
    Assertions.assertEquals(3, ticketService.getBookedTickets(event, 1, 1).size());
  }

  @Test
  public void cancelTicketTest() {
    Assertions.assertTrue(ticketService.cancelTicket(1));
  }

  @AfterEach
  public void cleanUp() {
    bookingStorage.cleanStorage();
  }
}
