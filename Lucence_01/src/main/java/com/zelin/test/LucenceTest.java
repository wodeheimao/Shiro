package com.zelin.test;

import com.zelin.manager.LucenceMananger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class LucenceTest {
    private LucenceMananger lucenceMananger;

    @Before
    public void testBefore(){
        lucenceMananger = new LucenceMananger();
    }

    //测试添加索引
    @Test
    public void test01() throws IOException {
        lucenceMananger.addIndexAll();
        System.out.println(" 添加成功 ");
    }

    //查看标准分析器的分词效果
    @Test
    public void testTokenStream() throws Exception {
        //创建一个标准分析器对象
        Analyzer analyzer = new StandardAnalyzer();
        //获得tokenStream对象
        //第一个参数：域名，可以随便给一个
        //第二个参数：要分析的文本内容
        TokenStream tokenStream = analyzer.tokenStream("test",
                "spring text");
        //添加一个引用，可以获得每个关键词
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        //添加一个偏移量的引用，记录了关键词的开始位置以及结束位置
        OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
        //将指针调整到列表的头部
        tokenStream.reset();
        //遍历关键词列表，通过incrementToken方法判断列表是否结束
        while(tokenStream.incrementToken()) {
            //关键词的起始位置
            System.out.println("start->" + offsetAttribute.startOffset());
            //取关键词
            System.out.println(charTermAttribute);
            //结束位置
            System.out.println("end->" + offsetAttribute.endOffset());
        }
        tokenStream.close();
    }

    //测试查询
    @Test
    public void test02() throws IOException {
        lucenceMananger.searchIndex();
    }

    //测试添加索引
    @Test
    public void test03() throws IOException {
        lucenceMananger.addIdex();
        System.out.println(" 添加成功 ");
    }

    //测试修改索引
    @Test
    public void test04() throws IOException {
        lucenceMananger.updateIndex();
        System.out.println(" 修改成功 ");
    }

    //测试删除索引
    @Test
    public void test05() throws IOException {
        lucenceMananger.deleteIndex();
        System.out.println(" 删除成功 ");
    }

    //测试删除全部索引
    @Test
    public void test06() throws IOException {
        lucenceMananger.deleteAll();
        System.out.println(" 删除成功 ");
    }

    //测试matchDocsQuery
    @Test
    public void test07() throws IOException {
        lucenceMananger.matchDocsQuery();
    }

    //测试termQuery
    @Test
    public void test08() throws IOException {
        lucenceMananger.termQuery();
    }
    //测试numericRangeuery
    @Test
    public void test09() throws IOException {
        lucenceMananger.numericRangeuery();
    }

    //测试booleanQuery
    @Test
    public void test10() throws IOException {
        lucenceMananger.booleanQuery();
    }

    //测试queryParse1
    @Test
    public void test11() throws IOException, ParseException {
        lucenceMananger.queryParse1();
    }
    //测试queryParse2
    @Test
    public void test12() throws IOException, ParseException {
        lucenceMananger.queryParse2();
    }
    //测试queryParse3
    @Test
    public void test13() throws IOException, ParseException {
        lucenceMananger.queryParse3();
    }
}
