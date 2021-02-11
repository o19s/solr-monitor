package com.o19s.solrmonitor;

import org.apache.solr.handler.RequestHandlerBase;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.response.SolrQueryResponse;

/**
 *
 */
public class MonitorRequestHandler extends RequestHandlerBase {
  @Override
  public void handleRequestBody(SolrQueryRequest req, SolrQueryResponse rsp) throws Exception {
    
  }

  @Override
  public String getDescription() {
    return "Solr Monitor API - A reverse index of queries to match documents";
  }
}
