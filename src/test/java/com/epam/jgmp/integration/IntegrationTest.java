package com.epam.jgmp.integration;

import com.epam.jgmp.config.TestConfig;
import com.epam.jgmp.facade.BookingFacade;
import com.epam.jgmp.facade.BookingFacadeImpl;
import com.epam.jgmp.model.Event;
import com.epam.jgmp.model.Ticket;
import com.epam.jgmp.model.User;
import com.epam.jgmp.model.implementation.EventImpl;
import com.epam.jgmp.model.implementation.UserImpl;
import com.epam.jgmp.storage.BookingStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.epam.jgmp.model.Ticket.Category.*;

public class IntegrationTest {

  ApplicationContext context;
  BookingStorage bookingStorage;
  BookingFacade bookingFacade;

  @BeforeEach
  public void setUp() {

    context = new AnnotationConfigApplicationContext(TestConfig.class);
    bookingStorage = context.getBean(BookingStorage.class);
    bookingFacade = context.getBean(BookingFacadeImpl.class);
  }

  @Test
  public void testIntegrationScenario() {
    User userAlfred = bookingFacade.createUser(new UserImpl("Alfred", "alfred@gmail.com"));
    User userRobert = bookingFacade.createUser(new UserImpl("Robert", "robert@gmail.com"));
    User userWilliam = bookingFacade.createUser(new UserImpl("William", "william@gmail.com"));

    Event event1 = bookingFacade.createEvent(new EventImpl("Dancing Show", new Date()));

    Ticket ticket1 = bookingFacade.bookTicket(userAlfred.getId(), event1.getId(), 1, STANDARD);
    Ticket ticket2 = bookingFacade.bookTicket(userAlfred.getId(), event1.getId(), 2, PREMIUM);
    Ticket ticket3 = bookingFacade.bookTicket(userAlfred.getId(), event1.getId(), 3, BAR);

    Ticket ticket4 = bookingFacade.bookTicket(userRobert.getId(), event1.getId(), 4, STANDARD);
    Ticket ticket5 = bookingFacade.bookTicket(userRobert.getId(), event1.getId(), 5, PREMIUM);
    Ticket ticket6 = bookingFacade.bookTicket(userRobert.getId(), event1.getId(), 6, BAR);

    Ticket ticket7 = bookingFacade.bookTicket(userWilliam.getId(), event1.getId(), 7, STANDARD);
    Ticket ticket8 = bookingFacade.bookTicket(userWilliam.getId(), event1.getId(), 8, PREMIUM);
    Ticket ticket9 = bookingFacade.bookTicket(userWilliam.getId(), event1.getId(), 9, BAR);

    List<Ticket> expectedTicketsByUser = Arrays.asList(ticket1, ticket2, ticket3);
    List<Ticket> actualTicketsByUser = bookingFacade.getBookedTickets(userAlfred, 1, 1);

    List<Ticket> expectedTicketsByEvent =
        Arrays.asList(
            ticket1, ticket2, ticket3, ticket4, ticket5, ticket6, ticket7, ticket8, ticket9);
    List<Ticket> actualTicketsByEvent = bookingFacade.getBookedTickets(event1, 1, 1);

    Assertions.assertEquals(expectedTicketsByUser, actualTicketsByUser);
    Assertions.assertEquals(expectedTicketsByEvent, actualTicketsByEvent);
    Assertions.assertTrue(bookingFacade.cancelTicket(ticket3.getId()));
    Assertions.assertTrue(bookingFacade.cancelTicket(ticket2.getId()));
    Assertions.assertTrue(bookingFacade.cancelTicket(ticket1.getId()));
  }

  @AfterEach
  public void cleanUp() {
    bookingStorage.cleanStorage();
  }
}
