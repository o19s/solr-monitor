# solr-monitor

Easy search alerts with Solr!  This project is a Solr package that exposes the Lucene Monitor API as a request handler.

## Installation

Start Solr with packaging enabled:

```bash
docker run --rm -p 8983:8983 --name solr solr:8.7 -c -Denable.packages=true

docker exec -it solr bash
```

## Lucene Monitor API

See the documentation here for the Lucene hooks that drives the alerting functionality.

https://lucene.apache.org/core/8_5_1/monitor/org/apache/lucene/monitor/package-summary.html
