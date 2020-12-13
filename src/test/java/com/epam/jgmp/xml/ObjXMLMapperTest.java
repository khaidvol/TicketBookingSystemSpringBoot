package com.epam.jgmp.xml;

import com.epam.jgmp.config.TestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.List;

public class ObjXMLMapperTest {

  ApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestConfig.class);
  ObjXMLMapper objXMLMapper = applicationContext.getBean("objXMLMapper", ObjXMLMapper.class);

  @Test
  public void xmlToObj() throws IOException {
    List<XMLTicket> xmlTickets = objXMLMapper.xmlToObj();
    Assertions.assertNotNull(xmlTickets);
    Assertions.assertEquals(3, xmlTickets.size());
  }
}
