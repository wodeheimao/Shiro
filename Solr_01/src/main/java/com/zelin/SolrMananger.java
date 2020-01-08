package com.zelin;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.SolrParams;

import javax.management.Query;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class SolrMananger {

    //添加索引库
    public void addSolrIndex() throws IOException, SolrServerException {
        SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/");
        //新建一个document对象
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", "12346");
        doc.addField("product_name", "阿乐");
        doc.addField("product_price", 18.9);
        solrServer.add(doc);
        solrServer.commit();
    }

    //删除索引库
    public void deleteSolrIndex() throws IOException, SolrServerException {
        SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/");

        solrServer.deleteById("12345");
        String query = "product_name:阿乐";
        solrServer.deleteByQuery(query);
        solrServer.commit();
    }

    //简单查询
    public void searchByIndex() throws SolrServerException {
        SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/");

        //定义查询条件
        SolrQuery params = new SolrQuery();
        params.setQuery("*:*");
        params.addFilterQuery("product_name:幸福");                   //过滤查询
        params.addSort("product_price", SolrQuery.ORDER.desc);  //排序
        //查询
        QueryResponse response = solrServer.query(params);
        //得到结果集
        SolrDocumentList results = response.getResults();
        long numFound = results.getNumFound();
        System.out.println("总数 = " + numFound);
        //遍历结果集
        for (SolrDocument doc : results) {
            String product_name = (String) doc.getFieldValue("product_name");
            float product_price = (Float) doc.getFieldValue("product_price");
            String product_catalog_name = (String) doc.getFieldValue("product_catalog_name");
            System.out.println("product_name = " + product_name);
            System.out.println("product_price = " + product_price);
            System.out.println("product_catalog_name = " + product_catalog_name);
            System.out.println("---------------------------------------------------------");
        }
    }

    //高亮查询
    public void searchByHighlighting() throws SolrServerException {
        SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/");

        //定义查询条件
        SolrQuery params = new SolrQuery();
        params.setQuery("幸福");
        params.set("df", "product_name");
        params.set("fl", "id", "product_name", "product_price", "product_catalog_name");

        //设置高亮
        params.setHighlight(true);
        params.addHighlightField("product_name");
        params.setHighlightSimplePre("<span style='color:red'>");
        params.setHighlightSimplePost("</span>");
        //查询
        QueryResponse response = solrServer.query(params);
        //得到结果集
        SolrDocumentList results = response.getResults();
        //得到高亮返回结果集
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

        long numFound = results.getNumFound();
        System.out.println("总数 = " + numFound);
        for (SolrDocument doc : results) {
            String id = (String) doc.getFieldValue("id");
            String product_name = (String) doc.getFieldValue("product_name");
            float product_price = (Float) doc.getFieldValue("product_price");
            String product_catalog_name = (String) doc.getFieldValue("product_catalog_name");
            //根据id得到高亮对象map结果集
            Map<String, List<String>> stringListMap = highlighting.get(id);
            List<String> list = stringListMap.get("product_name");
            if (list != null) {
                String highLighting_name = list.get(0);
                System.out.println("highLighting_name = " + highLighting_name);
            }
            System.out.println("id = " + id);
            System.out.println("product_name = " + product_name);
            System.out.println("product_price = " + product_price);
            System.out.println("product_catalog_name = " + product_catalog_name);
            System.out.println("---------------------------------------------------------");
        }

    }


}
