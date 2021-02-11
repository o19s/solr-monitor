package com.o19s.solrmonitor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class MonitorRequestHandlerTest {

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void handleRequestBody() {
  }

  @Test
  public void getDescription() {
    MonitorRequestHandler handler = new MonitorRequestHandler();
    assertNotNull(handler.getDescription());
  }
}
