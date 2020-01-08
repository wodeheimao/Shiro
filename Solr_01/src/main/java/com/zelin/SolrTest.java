package com.zelin;

import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class SolrTest {

    private SolrMananger solrMananger;
    @Before
    public void testBefore(){
        solrMananger = new SolrMananger();
    }

    @Test
    public void test01() throws IOException, SolrServerException {
        solrMananger.addSolrIndex();
        System.out.println("添加成功");
    }

    @Test
    public void test02() throws IOException, SolrServerException {
        solrMananger.deleteSolrIndex();
        System.out.println("删除成功");
    }

    @Test
    public void test03() throws SolrServerException {
        solrMananger.searchByIndex();
    }

    @Test
    public void test04() throws SolrServerException {
        solrMananger.searchByHighlighting();
    }
}
