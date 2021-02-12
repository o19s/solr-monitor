package com.o19s.solrmonitor;

import org.apache.lucene.monitor.Monitor;
import org.apache.solr.SolrTestCaseJ4;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.request.SolrQueryRequestBase;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 *
 */
public class MonitorRequestHandlerTest extends SolrTestCaseJ4 {

  private static Monitor monitor;
  private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  protected final ModifiableSolrParams baseParams = new ModifiableSolrParams();

  @BeforeClass
  public static void beforeClass() throws Exception {
    initCore("solrconfig-monitor.xml", "schema-monitor.xml");
  }

  @Override
  public void setUp() throws Exception {
    super.setUp();
    baseParams.clear();
    baseParams.set(CommonParams.QT, "/monitor");
    baseParams.set(CommonParams.WT, "xml");
  }

//  @Test
//  public void handleRequestBody() throws Exception {
//    baseParams.set("field", "name_tagStop");//stop filter (pos inc enabled) index & query
//
//    String doc = "london business school";//just one tag
//
//    log.debug("Test doc: {}", doc);
//    ModifiableSolrParams p = new ModifiableSolrParams();
//    p.add(CommonParams.Q, "*:*");
//    SolrParams params = SolrParams.wrapDefaults(p, baseParams);
//    SolrQueryRequestBase req = new SolrQueryRequestBase(h.getCore(), params) {};
//
////    SolrQueryRequest req = reqDoc(doc, "indent", "on", "omitHeader", "on", "matchText", ""+matchText);
//    String rspStr = h.query(req);
//    req.close();
//    SolrQueryResponse rsp = h.queryAndResponse(req.getParams().get(CommonParams.QT), req);
//
//        /*
//          <?xml version="1.0" encoding="UTF-8"?>
//          <response>
//
//            <lst name="responseHeader">
//              <int name="status">0</int>
//              <int name="QTime">9</int>
//            </lst>
//            <str name="blah">blarg</str>
//          </response>
//      */
//  }

  @Test
  public void testCreate() throws Exception {
    ModifiableSolrParams p = new ModifiableSolrParams();
    p.add("op", "create");
    p.add("name", "testQuery1");
    SolrParams params = SolrParams.wrapDefaults(p, baseParams);
    SolrQueryRequestBase req = new SolrQueryRequestBase(h.getCore(), params) {};
//    SolrQueryResponse rsp = h.queryAndResponse(req.getParams().get(CommonParams.QT), req);
//    String rspStr = h.query(req);
    assertQ(req, "//str[@name='create']");
    req.close();
  }

  @Test
  public void testRegister() throws Exception {
    ModifiableSolrParams p = new ModifiableSolrParams();
    p.add(CommonParams.Q, "+war +peace");
    p.add(CommonParams.DF, "title");
    p.add("op", "register");
    p.add("id", "testQuery1");
    SolrParams params = SolrParams.wrapDefaults(p, baseParams);
    SolrQueryRequestBase req = new SolrQueryRequestBase(h.getCore(), params) {};
//    SolrQueryResponse rsp = h.queryAndResponse(req.getParams().get(CommonParams.QT), req);
//    String rspStr = h.query(req);
    assertQ(req, "//str[@name='register']");
    req.close();
  }
  @Test
  public void getDescription() {
    MonitorRequestHandler handler = new MonitorRequestHandler();
    assertNotNull(handler.getDescription());
  }



}
