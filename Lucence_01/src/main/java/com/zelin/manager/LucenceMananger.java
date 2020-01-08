package com.zelin.manager;

import com.sun.xml.internal.bind.v2.model.core.ID;
import org.apache.commons.io.FileUtils;
import org.apache.lucene.document.*;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class LucenceMananger {

    //得到索引输出对象流IndexWriter
    private IndexWriter getIndexWriter() throws IOException {
        //IndexWriter的directory
        Directory directory = FSDirectory.open(new File("d:/my_index"));
        //定义解析器对象
        Analyzer analyzer = new StandardAnalyzer();
        //输出流的配置对象
        IndexWriterConfig config = new IndexWriterConfig(Version.LATEST,analyzer);
        return new IndexWriter(directory,config);
    }

    //获取searcher
    private IndexSearcher getSearcher() throws IOException {
        Directory directory =FSDirectory.open(new File("d:/my_index"));
        IndexReader indexReader = DirectoryReader.open(directory);
        return  new IndexSearcher(indexReader);
    }

    //打印查询结果
    private void printInfo(IndexSearcher searcher, TopDocs search) throws IOException {
        //遍历查询结果
        for (ScoreDoc scoreDoc : search.scoreDocs) {
            //获得文档id
            int id = scoreDoc.doc;
            //获取文档
            Document doc = searcher.doc(id);
            IndexableField fileName = doc.getField("fileName");
            IndexableField filePath = doc.getField("filePath");
            IndexableField fileSize = doc.getField("fileSize");
            IndexableField fileContent = doc.getField("fileContent");
            System.out.println("----------------------------------");
            System.out.println("fileName = " + fileName.stringValue());
            System.out.println("filePath = " + filePath.stringValue());
            System.out.println("fileSize = " + fileSize.stringValue());
            System.out.println("fileContent = " + fileContent.stringValue());
            System.out.println("----------------------------------");
        }
    }


    //1.建立索引
    public void addIndexAll() throws IOException {
        IndexWriter indexWriter = getIndexWriter();
        //需要建立索引库的对象文件夹
        File files = new File("D:\\searchdocument");
        for (File file : files.listFiles()) {
            //建立文件名域
            String fileName = file.getName();
            TextField textField = new TextField("fileName",fileName, Field.Store.YES);
            //文件路径域
            String filePath = file.getAbsolutePath();
            StoredField storedField = new StoredField("filePath",filePath);
            //文件大小域
            long size = FileUtils.sizeOf(file);
            LongField longField = new LongField("fileSize",size,Field.Store.YES);
            //文件内容域
            String fileContent = FileUtils.readFileToString(file);
            TextField textField1 = new TextField("fileContent",fileContent,Field.Store.YES);

            //新建文档类型
            Document document = new Document();
            document.add(textField);
            document.add(storedField);
            document.add(longField);
            document.add(textField1);

            //使用indexwriter将索引写入文件中
            indexWriter.addDocument(document);
        }
        indexWriter.close();
    }

    //根据索引查询
    public void searchIndex() throws IOException {
        //获取一个IndexSearcher对象
        IndexSearcher searcher = getSearcher();
        //新建查询条件
        Term term = new Term("fileName","aaaa.txt");
        //查询query
        Query query =new TermQuery(term);
        //开始查询，条件和返回条数
        TopDocs search = searcher.search(query, 5);
        //得到记录数
        int totalHits = search.totalHits;
        System.out.println("totalHits = " + totalHits);
        printInfo(searcher, search);
        searcher.getIndexReader().close();

    }

    //添加一个索引
    public void addIdex() throws IOException {
        IndexWriter indexWrite = getIndexWriter();
        Document doc = new Document();
        IndexableField fileAField = new TextField("fieldA","fielda",Field.Store.YES);
        doc.add(fileAField);
        indexWrite.addDocument(doc);
        indexWrite.close();
    }

    //修改索引库
    public void updateIndex() throws IOException {
        IndexWriter indexWriter = getIndexWriter();
        Term term = new Term("fieldA","fielda");
        Document doc = new Document();
        doc.add(new TextField("fileCC","filecc", Field.Store.YES));
        doc.add(new TextField("fileDD","filedd", Field.Store.YES));
        indexWriter.updateDocument(term,doc);
        indexWriter.close();
    }

    //删除索引
    public void deleteIndex() throws IOException {
        IndexWriter indexWriter = getIndexWriter();
        Term term = new Term("fileCC","filecc");
        indexWriter.deleteDocuments(term);
        indexWriter.close();
    }
    //删除所有索引
    public void deleteAll() throws IOException {
        IndexWriter indexWriter = getIndexWriter();
        indexWriter.deleteAll();
        indexWriter.close();
    }

    //-----------------利用Query的子类操作索引--------------------
    //matchDocsQuery子类
    public void matchDocsQuery() throws IOException {
        IndexSearcher searcher = getSearcher();
        Query query = new MatchAllDocsQuery();
        TopDocs search = searcher.search(query, 10);
        printInfo(searcher,search);
        searcher.getIndexReader().close();
    }

    //termQuery子类
    public void termQuery() throws IOException {
        IndexSearcher searcher = getSearcher();
        Term term = new Term("fileName","aaaa.txt");
        Query query = new TermQuery(term);
        TopDocs search = searcher.search(query, 10);
        printInfo(searcher,search);
        searcher.getIndexReader().close();
    }
    //NumericRangeuery子类
    public void numericRangeuery() throws IOException {
        IndexSearcher searcher = getSearcher();
        Query query = NumericRangeQuery.newLongRange("fileSize", 10L, 100L,true,true);
        TopDocs search = searcher.search(query, 10);
        printInfo(searcher,search);
        searcher.getIndexReader().close();
    }
    //booleanQuery子类
    public void booleanQuery() throws IOException {
        IndexSearcher searcher = getSearcher();
        BooleanQuery query = new BooleanQuery();

        Query query1 = new TermQuery(new Term("fileName","aaaa.txt"));
        Query query2 = new TermQuery(new Term("fileContent","aaaa"));
        query.add(query1,BooleanClause.Occur.MUST); //must -- and
        query.add(query2,BooleanClause.Occur.SHOULD); //should -- or

        TopDocs search = searcher.search(query, 10);
        printInfo(searcher,search);
        searcher.getIndexReader().close();
    }

    //-----------------利用QueryParser进行查询--------------------
    public void queryParse1() throws IOException, ParseException {
        IndexSearcher searcher = getSearcher();
        //使用IKanlayzer
        Analyzer anlayzer = new IKAnalyzer();
        //使用queryParse
        QueryParser queryParser = new QueryParser("fileContent",anlayzer);
        Query query = queryParser.parse("spring");
        TopDocs search = searcher.search(query, 10);
        printInfo(searcher,search);
        searcher.getIndexReader().close();
    }

    public void queryParse2() throws IOException, ParseException {
        IndexSearcher searcher = getSearcher();
        //使用IKanlayzer
        Analyzer anlayzer = new IKAnalyzer();
        //使用queryParse
        QueryParser queryParser = new QueryParser("fileContent",anlayzer);
        Query query = queryParser.parse("+fileContent:spring fileName:1111.txt");
        TopDocs search = searcher.search(query, 10);
        printInfo(searcher,search);
        searcher.getIndexReader().close();
    }

    public void queryParse3() throws IOException, ParseException {
        IndexSearcher searcher = getSearcher();
        //使用IKanlayzer
        Analyzer anlayzer = new IKAnalyzer();
        //使用queryParse
        String[] fields = {"fileName","fileContent"};
        QueryParser queryParser = new MultiFieldQueryParser(fields,anlayzer);
        Query query = queryParser.parse("+fileContent:spring");
        TopDocs search = searcher.search(query, 10);
        printInfo(searcher,search);
        searcher.getIndexReader().close();
    }
}
