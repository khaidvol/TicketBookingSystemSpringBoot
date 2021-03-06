package com.epam.jgmp.config;

import com.epam.jgmp.pdf.TicketsPdfBuilder;
import com.epam.jgmp.xml.ObjXMLMapper;
import com.epam.jgmp.xml.XMLTicket;
import com.epam.jgmp.xml.XMLTicketListContainer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@ComponentScan({
  "com.epam.jgmp.storage",
  "com.epam.jgmp.model",
  "com.epam.jgmp.dao",
  "com.epam.jgmp.service",
  "com.epam.jgmp.facade",
  "com.epam.jgmp.xml",
  "com.epam.jgmp.pdf"
})
@PropertySource("classpath:application.properties")
public class TestConfig {

  private final ApplicationContext applicationContext;

  public TestConfig(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  @Bean
  public TicketsPdfBuilder ticketsPdfBuilder() {
    return new TicketsPdfBuilder();
  }

  /** XML to Object Mapping configuration */
  @Bean
  public Jaxb2Marshaller jaxb2Marshaller() {
    Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
    jaxb2Marshaller.setClassesToBeBound(XMLTicket.class, XMLTicketListContainer.class);
    return jaxb2Marshaller;
  }

  @Bean
  public ObjXMLMapper objXMLMapper() {
    ObjXMLMapper objXMLMapper = new ObjXMLMapper();
    objXMLMapper.setMarshaller(jaxb2Marshaller());
    return objXMLMapper;
  }
}
