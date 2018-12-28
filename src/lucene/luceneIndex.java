package lucene;

import nlpCore.*;
import doc_init.*;

import java.io.*;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.*;

public class luceneIndex {
    private static final String index_path = "lucene\\index";
    private static final String origin_path = "modify_doc\\";

    private static Directory ram_dir;
    private static Directory fs_dir;
    private static Analyzer analyzer;
    private static IndexWriterConfig iwc;
    private static IndexWriter indexWriter;
    private static File[] files;

    private static void init() throws IOException {
        ram_dir = new RAMDirectory();
        analyzer = new StandardAnalyzer();
        iwc = new IndexWriterConfig(analyzer);
        iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
        //     iwc.setRAMBufferSizeMB(4096.0);
        indexWriter = new IndexWriter(ram_dir, iwc);
        files = new File(origin_path).listFiles();
        fs_dir = FSDirectory.open(Paths.get(index_path));
        //ref link: https://www.jianshu.com/p/1f3ba892fc64
    }



    public static void create_index(File file) throws IOException {
        //     BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        int count = 0;
        int least_length = 20;
        String content;
        boolean flag = false;
        if ((content = doc_init.file_process.readToString(file)) != null) {
            if (content.length() < least_length)
                return;
            Document doc_lucene = new Document();
            content = doc_init.file_process.file_clean(content);
            doc_lucene.add(new StringField("filename_id", file.getName(), Store.YES));
            doc_lucene.add(new StringField("from", file.getName(), Store.YES));
            doc_lucene.add(new StringField("to", file.getName(), Store.YES));
            doc_lucene.add(new StringField("description", file.getName(), Store.YES));
            doc_lucene.add(new StringField("theme", file.getName(), Store.YES));
            // generate
            // <a href="https://www.openstreetmap.org/search?query=&quot;Bengaluru, India&quot;&layers=C">
            // Bengaluru, India</a>
            doc_lucene.add(new StringField("location", file.getName(), Store.YES));
            doc_lucene.add(new StringField("deadline", file.getName(), Store.YES));
        }

    }

    //  ref link: https://www.jianshu.com/p/1f3ba892fc64

    public static void main(String[] args) throws IOException {
        init();
        if (fs_dir.listAll().length == 0) {
            for (File file : files) {
                System.out.println("create index for " + file.getName());
                create_index(file);
                System.out.println(file.getName() + " ok! ");
            }
            indexWriter.forceMerge(1);
            indexWriter.close();
        }
        for (String file : ram_dir.listAll()) {
            fs_dir.copyFrom(ram_dir, file, file, IOContext.DEFAULT);
        }
    }

}