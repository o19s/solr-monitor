package com.o19s.solrmonitor;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.monitor.Monitor;
import org.apache.lucene.monitor.MonitorQuery;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Util {
  public static final String FIELD = "field";
  public static final Analyzer ANALYZER = new StandardAnalyzer();

  public static Query parse(String query) {
    QueryParser parser = new QueryParser(FIELD, ANALYZER);
    try {
      return parser.parse(query);
    } catch (ParseException e) {
      throw new IllegalArgumentException(e);
    }
  }

  public static MonitorQuery mq(String id, String query, String... metadata) {
    Query q = parse(query);
    assert metadata.length % 2 == 0;
    Map<String, String> mm = new HashMap<>();
    for (int i = 0; i < metadata.length / 2; i += 2) {
      mm.put(metadata[i], metadata[i + 1]);
    }
    return new MonitorQuery(id, q, query, mm);
  }

  protected static Monitor newMonitor() throws IOException {
    return newMonitor(new StandardAnalyzer());
  }

  protected static Monitor newMonitor(Analyzer analyzer) throws IOException {
    // TODO: randomize presearcher
    return new Monitor(analyzer);
  }

}
