package com.zelin.service.impl;

import com.zelin.pojo.Product;
import com.zelin.pojo.ProductVo;
import com.zelin.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private SolrTemplate solrTemplate;

    public List<Product> findByProductVo(ProductVo productVo) {
        List<Product> products = new ArrayList<Product>();
        //新建高亮查询query
        HighlightQuery query = new SimpleHighlightQuery();

        //高亮查询的条件
        if(StringUtils.isNotBlank(productVo.getQueryString())){
            Criteria criteria = new Criteria("product_keywords").is(productVo.getQueryString());
            query.addCriteria(criteria);
        }else{
            //添加查询的criteria标准
            query.addCriteria(new Criteria("product_keywords"));
        }

        //过滤查询，分类查询Cata_log_Name
        if(StringUtils.isNotBlank(productVo.getCatalogName())){
            SimpleQuery filterQuery = new SimpleQuery();
            Criteria criteria = new Criteria("product_catalog_name").is(productVo.getCatalogName());
            //添加查询的criteria标准
            filterQuery.addCriteria(criteria);
            //添加过滤查询实例
            query.addFilterQuery(filterQuery);
        }

        //过滤查询，按价格查询
        if(StringUtils.isNotBlank(productVo.getPrice())){
            //拆分查询添加价格  “0-9”--》 0   9
            String[] split = productVo.getPrice().split("-");
            //新建过滤查询
            SimpleQuery filterQuery = new SimpleQuery();
            //大于较小的查询价格
            filterQuery.addCriteria(new Criteria("product_price").greaterThan(split[0]));
            //判断大的价格是否为*
            if(!split[1].equals("*")){
                filterQuery.addCriteria(new Criteria("product_price").lessThanEqual(split[1]));
            }
            query.addFilterQuery(filterQuery);
        }

        //过滤查询，按价格排序查询
        if(StringUtils.isNotBlank(productVo.getSort())){
            //新建过滤查询
            SimpleQuery filterQuery = new SimpleQuery();
            String priceSort = productVo.getSort();
            System.out.println("priceSort = " + priceSort);
            if(priceSort.equals("1")){
                Sort sort = new Sort(Sort.Direction.DESC,"product_price");
                query.addSort(sort);
            }else{
                Sort sort = new Sort(Sort.Direction.ASC,"product_price");
                query.addSort(sort);
            }
        }

        //分页查询
        //指定记录开始
        query.setOffset(0L);
        //每一页显示的shu数目
        query.setRows(40);
        //高亮字段和高亮前缀与后缀
        HighlightOptions options = new HighlightOptions();
        options.addField("product_name");
        options.setSimplePrefix("<span style='color:red'>");
        options.setSimplePostfix("</span>");
        query.setHighlightOptions(options);
        //实行高亮查询
        HighlightPage<Product> highlightPage = solrTemplate.queryForHighlightPage("", query, Product.class);
        //获取高亮查询的list结果集
        List<HighlightEntry<Product>> highlighted = highlightPage.getHighlighted();

        for (HighlightEntry<Product> highlightEntry : highlighted) {
            //获得一个商品实例(entity + 高亮字段)
            Product entity = highlightEntry.getEntity();
            List<HighlightEntry.Highlight> highlights = highlightEntry.getHighlights();
            if (null != highlights && highlights.size() > 0){
                HighlightEntry.Highlight highlight = highlights.get(0);
                List<String> snipplets = highlight.getSnipplets();
                if(snipplets !=null && snipplets.size()>0){
                    String name = snipplets.get(0);
                    entity.setName(name);
                }
            }
            products.add(entity);
        }

        return products;
    }
}
