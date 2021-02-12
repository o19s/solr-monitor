package com.o19s.solrmonitor;

import org.apache.solr.SolrTestCaseJ4;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 *
 */
public class MonitorRequestHandlerApiTest extends SolrTestCaseJ4 {

  private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @BeforeClass
  public static void beforeClass() throws Exception {
    initCore("solrconfig-monitor.xml", "schema-monitor.xml");
  }

  @Override
  public void setUp() throws Exception {
    super.setUp();
  }

  @Test
  public void getApis() {
  }

  @Test
  public void registerV1() {
  }

  @Test
  public void registerV2() {
  }
}
