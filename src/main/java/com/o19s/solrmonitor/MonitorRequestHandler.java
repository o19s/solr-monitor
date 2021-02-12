package com.o19s.solrmonitor;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.monitor.MatchingQueries;
import org.apache.lucene.monitor.Monitor;
import org.apache.lucene.monitor.MonitorQuery;
import org.apache.lucene.monitor.QueryMatch;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.solr.common.SolrException;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.handler.RequestHandlerBase;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.response.SolrQueryResponse;

import java.io.IOException;
import java.util.Set;

/**
 *
 */
public class MonitorRequestHandler extends RequestHandlerBase {
  private Monitor monitor;
  public static final String FIELD = "field";
  public static final Analyzer ANALYZER = new StandardAnalyzer();

  @Override
  public String getDescription() {
    return "Solr Monitor API - A reverse index of queries to match documents";
  }

  @Override
  public void init(@SuppressWarnings({"rawtypes"}) NamedList args) {
    super.init(args);
    try {
      monitor = Util.newMonitor();
    }
    catch (IOException e) {
      throw new SolrException(SolrException.ErrorCode.SERVER_ERROR, "The monitor requires an" +
        " op (operation) paramater");
    }
  }

  @Override
  public void handleRequestBody(SolrQueryRequest req, SolrQueryResponse rsp) throws Exception {
    final String operation = req.getParams().get("op");
    if (operation == null) {
      throw new SolrException(SolrException.ErrorCode.SERVER_ERROR, "The monitor requires an" +
        " op (operation) paramater");
    }

//    final SolrIndexSearcher searcher = req.getSearcher();

//    Analyzer analyzer = req.getSchema().getField(indexedField).getType().getQueryAnalyzer();
    rsp.addResponseHeader(req.getParams().toNamedList());

    switch (operation) {
      case "register":
        rsp.add("register", register(
          req.getParams().get(CommonParams.DF),
          req.getParams().get("id"),
          req.getParams().get(CommonParams.Q)));
        break;
      case "match":
        rsp.add("match", match(req.getParams().get(CommonParams.DF), req.getParams().get("doc")));
        break;
      case "getQueryIds":
        rsp.add("getQueryIds", getQueryIds());
        break;
      case "getQueryCount":
        rsp.add("getQueryCount", getQueryCount());
        break;
      case "getQuery":
        rsp.add("getQuery", getQuery(req.getParams().get("id")));
        break;
      case "deleteById":
        rsp.add("deleteById", deleteById(req.getParams().get("id")));
        break;
      case "clear":
        rsp.add("clear", clear());
        break;
      default:
        break;
    }
  }

  String register(String defaultField, String id, String queryString) throws IOException {
    Query parsedQuery = parse(defaultField, queryString);
    monitor.register(new MonitorQuery(id, parsedQuery));
    return String.format("register called with name '%s' and value '%s'", id, queryString);
  }
//  purgeCache()

  String match(String defaultField, String content) throws IOException {
    Document doc = new Document();
    FieldType ft = new FieldType(TextField.TYPE_STORED);
    ft.setTokenized(true);
    ft.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
    Field field = new Field(defaultField, content, ft);
    doc.add(field);

    MatchingQueries<QueryMatch> matches = monitor.match(doc, QueryMatch.SIMPLE_MATCHER);

    return String.format("match called with doc '%s'", matches.toString());
  }

  String getQueryIds() throws IOException {
    Set monitorQueryIds = monitor.getQueryIds();
    String contents = String.join(",", monitorQueryIds);
    return String.format("getQueryIds called: %s", contents);
  }

  String getQueryCount() throws IOException {
    Set monitorQueryIds = monitor.getQueryIds();
    return String.format("getQueryCount called: %s", monitorQueryIds.size());
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

  public Query parse(String defaultField, String query) {
    QueryParser parser = new QueryParser(defaultField, ANALYZER);
    try {
      return parser.parse(query);
    } catch (ParseException e) {
      throw new IllegalArgumentException(e);
    }
  }

  public static final String DOC_PATH = "/monitor/json/docs";
  public static final String JSON_PATH = "/monitor/json";
  public static final String CSV_PATH = "/monitor/csv";
  public static final String BIN_PATH = "/monitor/bin";

}
