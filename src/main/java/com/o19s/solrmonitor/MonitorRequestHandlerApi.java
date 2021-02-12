package com.o19s.solrmonitor;

import com.google.common.collect.ImmutableMap;
import org.apache.solr.api.Api;
import org.apache.solr.common.SolrException;
import org.apache.solr.common.util.Utils;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.response.SolrQueryResponse;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 *
 */
public class MonitorRequestHandlerApi extends MonitorRequestHandler {
  @Override
  public Collection<Api> getApis() {
    return Collections.singleton(getApiImpl());
  }

  private Api getApiImpl() {
    return new Api(Utils.getSpec("monitor")) {
      @Override
      public void call(SolrQueryRequest req, SolrQueryResponse rsp) {
        String path = req.getPath();
        String target =  mapping.get(path);
        if(target != null) req.getContext().put("path", target);
        try {
          handleRequest(req, rsp);
        } catch (RuntimeException e) {
          throw e;
        } catch (Exception e){
          throw new SolrException(SolrException.ErrorCode.BAD_REQUEST,e );
        }
      }
    };
  }

  @Override
  public Boolean registerV1() {
    return Boolean.FALSE;
  }

  @Override
  public Boolean registerV2() {
    return Boolean.TRUE;
  }

  private static final Map<String, String> mapping = ImmutableMap.<String,String>builder()
    .put("/monitor", DOC_PATH)
    .put(JSON_PATH, DOC_PATH)
    .put("/monitor/json/commands", JSON_PATH)
    .build();

}
