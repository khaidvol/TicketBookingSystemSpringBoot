package com.epam.jgmp.dao;

import com.epam.jgmp.config.TestConfig;
import com.epam.jgmp.dao.implementation.TicketDao;
import com.epam.jgmp.model.Ticket;
import com.epam.jgmp.storage.BookingStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TicketDaoTest {

  ApplicationContext context;
  BookingStorage bookingStorage;
  Dao<Ticket> ticketDao;
  Ticket ticket;

  public TicketDaoTest() {}

  @BeforeEach
  public void setUp() {

    context = new AnnotationConfigApplicationContext(TestConfig.class);
    bookingStorage = context.getBean(BookingStorage.class);
    ticketDao = context.getBean(TicketDao.class);

    ticket = Mockito.mock(Ticket.class);
    Mockito.when(ticket.getId()).thenReturn(10L);
  }

  @Test
  public void createTest() {
    Assertions.assertNull(ticketDao.create(ticket));
    Assertions.assertEquals(ticket, ticketDao.read(ticket.getId()));
  }

  @Test
  public void readTest() {
    Assertions.assertNull(ticketDao.create(ticket));
    Assertions.assertEquals(ticket, ticketDao.read(ticket.getId()));
  }

  @Test
  public void readAllTest() {
    Assertions.assertNull(ticketDao.create(ticket));
    Assertions.assertNotNull(ticketDao.readAll());
  }

  @Test
  public void updateTest() {
    Assertions.assertNull(ticketDao.create(ticket));
    Assertions.assertEquals(ticket, ticketDao.update(ticket));
  }

  @Test
  public void deleteTest() {
    Assertions.assertNull(ticketDao.create(ticket));
    Assertions.assertEquals(ticket, ticketDao.delete(ticket.getId()));
  }

  @Test
  public void getMaxIdWhenStorageNotEmptyTest() {
    Assertions.assertNull(ticketDao.create(ticket));
    Assertions.assertNotNull(ticketDao.getMaxId());
  }

  @Test
  public void getMaxIdWhenStorageIsEmptyTest() {
    bookingStorage.getTickets().clear();
    Assertions.assertNotNull(ticketDao.getMaxId());
  }

  @AfterEach
  public void cleanUp() {
    bookingStorage.cleanStorage();
  }
}
