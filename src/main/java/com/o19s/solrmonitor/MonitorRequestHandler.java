package com.o19s.solrmonitor;

import org.apache.solr.common.SolrException;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.handler.RequestHandlerBase;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.response.SolrQueryResponse;
import org.apache.solr.schema.SchemaField;
import org.apache.solr.search.SolrIndexSearcher;

/**
 *
 */
public class MonitorRequestHandler extends RequestHandlerBase {

  @Override
  public String getDescription() {
    return "Solr Monitor API - A reverse index of queries to match documents";
  }

  @Override
  public void handleRequestBody(SolrQueryRequest req, SolrQueryResponse rsp) throws Exception {
    final String indexedField = req.getParams().get("field");
    final String operation = req.getParams().get("op");
    if (operation == null) {
      throw new SolrException(SolrException.ErrorCode.SERVER_ERROR, "The monitor requires an" +
        " op (operation) paramater");
    }
    final SchemaField idSchemaField = req.getSchema().getUniqueKeyField();
    if (idSchemaField == null) {
      throw new SolrException(SolrException.ErrorCode.SERVER_ERROR, "The monitor requires a" +
        "uniqueKey in the schema.");
    }

    final SolrIndexSearcher searcher = req.getSearcher();

//    Analyzer analyzer = req.getSchema().getField(indexedField).getType().getQueryAnalyzer();
    rsp.addResponseHeader(req.getParams().toNamedList());
    rsp.add("blah", "blarg");

    if (operation == "create") {
      rsp.add("create", create(req.getParams().get("name")));
    }
    if (operation == "register") {
      rsp.add("register", register(req.getParams().get("id"), req.getParams().get(CommonParams.Q)));
    }
  }

  String create(String name) {
    return String.format("create called with name '%s'", name);
  }

//  delete() / close()
  String register(String id, String queryString) {
    return String.format("register called with name '%s' and value '%s'", id, queryString);
  }
//  purgeCache()
//  match(doc)
//  getQueryIds()
//  getQueryCount()
//  getQueryCacheStats()
//  getQuery(queryId)
//  getDisjunctCount()
//  deleteById(id)
//  clear()
//  close()


}
