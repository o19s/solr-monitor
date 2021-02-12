package com.o19s.solrmonitor;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.Term;
import org.apache.lucene.monitor.MatchingQueries;
import org.apache.lucene.monitor.Monitor;
import org.apache.lucene.monitor.MonitorQuery;
import org.apache.lucene.monitor.QueryMatch;
import org.apache.lucene.search.TermQuery;
import org.junit.Test;

import java.io.IOException;

/**
 *
 */
public class MonitorTests extends MonitorTestBase {

  @Test
  public void testSingleTermQueryMatchesSingleDocument() throws IOException {

    Document doc = new Document();
    doc.add(newTextField(FIELD, "This is a test document", Field.Store.NO));

    try (Monitor monitor = newMonitor()) {
      monitor.register(new MonitorQuery("query1", new TermQuery(new Term(FIELD, "test"))));

      MatchingQueries<QueryMatch> matches = monitor.match(doc, QueryMatch.SIMPLE_MATCHER);
      assertNotNull(matches.getMatches());
      assertEquals(1, matches.getMatchCount());
      assertNotNull(matches.matches("query1"));
    }
  }
  
}
