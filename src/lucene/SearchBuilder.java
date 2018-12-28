package lucene;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;

//import org.apache.lucene.analysis.TokenStream;
//import org.apache.lucene.analysis.standard.StandardAnalyzer;

public class SearchBuilder {
    public static void doSearch(String indexDir, String queryStr) throws IOException, ParseException {
        Directory directory = FSDirectory.open(Paths.get(indexDir));
        DirectoryReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);
       // Analyzer analyzer = new StandardAnalyzer();
        Analyzer analyzer = new EnglishAnalyzer();
        QueryParser parser = new QueryParser("content", analyzer);
        Query query = parser.parse(queryStr);

        long startTime = System.currentTimeMillis();
        TopDocs docs = searcher.search(query, 10);

        long endTime = System.currentTimeMillis();
        System.out.println("查找" + queryStr + "所用时间：" + (endTime - startTime));
        System.out.println("查询到" + docs.totalHits + "条记录");


        //遍历查询结果
        for (ScoreDoc scoreDoc : docs.scoreDocs) {
            Document doc = searcher.doc(scoreDoc.doc);
            String content = doc.get("content");
        }
        reader.close();
    }

    public static void main(String[] args) {
        String indexDir = "lucene\\";
        String q = "content\\"; //查询这个字符串
        try {
            doSearch(indexDir, q);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
