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

    switch (operation) {
      case "create":
        rsp.add("create", create());
        break;
      case "register":
        rsp.add("register", register(req.getParams().get("id"), req.getParams().get(CommonParams.Q)));
      case "match":
        rsp.add("match", match(req.getParams().get("doc")));
      case "getQueryIds":
        rsp.add("getQueryIds", getQueryIds());
        break;
      case "getQueryCount":
        rsp.add("getQueryCount", getQueryCount());
        break;
      case "getQuery":
        rsp.add("getQuery", match(req.getParams().get("id")));
        break;
      case "deleteById":
        rsp.add("deleteById", match(req.getParams().get("id")));
        break;
      case "clear":
        rsp.add("clear", clear());
        break;
      default:
        break;
    }
  }

  String create() {
    return "create called";
  }

//  delete() / close()
  String register(String id, String queryString) {
    return String.format("register called with name '%s' and value '%s'", id, queryString);
  }
//  purgeCache()

  String match(String doc) {
    return String.format("match called with doc '%s'", doc);
  }

  String getQueryIds() {
    return "getQueryIds called";
  }

  String getQueryCount() {
    return "getQueryCount called";
  }

//  getQueryCacheStats()
  String getQuery(String queryId) {
    return String.format ("getQueryCount called for queryId '%s'", queryId);
  }
//  getDisjunctCount()
  String deleteById(String queryId) {
    return String.format ("deleteById called for queryId '%s'", queryId);
  }

  String clear() {
    return "clear called";
  }
//  close()


}
