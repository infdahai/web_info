package lucene;


import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//import org.apache.lucene.analysis.TokenStream;
//import org.apache.lucene.analysis.standard.StandardAnalyzer;

public class SearchBuilder {
    public static ArrayList<Map<String, String>> doSearch(String queryStr, String searchType, String sortType) throws IOException {
//        Directory directory = FSDirectory.open(Paths.get(indexDir));
//        DirectoryReader reader = DirectoryReader.open(directory);
//        IndexSearcher searcher = new IndexSearcher(reader);
//        Analyzer analyzer = new StandardAnalyzer();
//        //Analyzer analyzer = new EnglishAnalyzer();
//        QueryParser parser = new QueryParser("filename_id", analyzer);
//        Query query = parser.parse(queryStr);
        String indexDir = "C:\\Users\\zta\\Desktop\\other\\webinfo\\lab01\\lucene\\index";
        Directory directory = FSDirectory.open(Paths.get(indexDir));
        IndexReader indexReader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(indexReader);
        QueryParser queryParser = new QueryParser(searchType, new StandardAnalyzer());
        Query query = null;

        ArrayList<Map<String, String>> results = new ArrayList<Map<String, String>>();
        try {
            query = queryParser.parse(queryStr);


        long startTime = System.currentTimeMillis();
        TopDocs docs = null;
        if(sortType.equals("INDEXORDER")) docs = searcher.search(query, 1000, Sort.INDEXORDER);
        else  docs = searcher.search(query, 1000, Sort.RELEVANCE);
        long endTime = System.currentTimeMillis();
        System.out.println("查找" + queryStr + "所用时间：" + (endTime - startTime));
        System.out.println("查询到" + docs.totalHits + "条记录");

        //遍历查询结果
        for (ScoreDoc scoreDoc : docs.scoreDocs) {
            Document doc = searcher.doc(scoreDoc.doc);
            Map<String, String> map = new HashMap();
            map.put("from", doc.get("from"));
            map.put("to", doc.get("to"));
            map.put("description", doc.get("description"));
            map.put("subject", doc.get("subject"));
            map.put("location", doc.get("location"));
            map.put("deadline", doc.get("deadline"));
            map.put("topics", doc.get("topics"));
            System.out.println(doc.get("deadline"));
            results.add(map);
        }
        System.out.println("=======================");
        //reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }


    public static void main(String[] args) {
        String queryStr = "china"; //查询这个字符串


        SearchBuilder search = new SearchBuilder();
        try {
            ArrayList<Map<String, String>> docs = search.doSearch(queryStr, "location", "INDEXORDER");
            docs = search.doSearch(queryStr, "location", "RELEVANCE");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
