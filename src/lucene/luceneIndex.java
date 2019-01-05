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
import org.apache.lucene.util.Version;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class luceneIndex {
    private static final String index_path = "lucene\\index";
    private static final String origin_path = "modify_doc\\";

  //  private static Directory ram_dir;
    private static Directory fs_dir;
    private static Analyzer analyzer;
    private static IndexWriterConfig iwc;
    private static IndexWriter indexWriter;
    private static File[] files;

    
    private static String[] from = new String[1000];
    private static String[] to = new String[1000];
    private static String[] web_site = new String[1000];
    private static String[] subject = new String[1000];
    private static String[] location = new String[1000];
    private static String[] deadline = new String[1000];
    
    private static void init() throws IOException {
    //    ram_dir = new RAMDirectory();
        analyzer = new StandardAnalyzer();
        iwc = new IndexWriterConfig(analyzer);
        iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);

        files = new File(origin_path).listFiles();
        fs_dir = FSDirectory.open(Paths.get(index_path));

        indexWriter = new IndexWriter(fs_dir, iwc);
        //    indexWriter = new IndexWriter(ram_dir, iwc);
        //ref link: https://www.jianshu.com/p/1f3ba892fc64
    }

    public static void recreate_index() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getField(int rows) throws FileNotFoundException {//最多rows或最大数量个文件
        FileReader fr;
        BufferedReader bf;
        String str;
        String[] strArr;
        int id;
        //from-to
        try {
            fr = new FileReader("..\\..\\data.txt");
            bf = new BufferedReader(fr);
            bf.readLine();
            id = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((str = bf.readLine()) != null && id<=rows) {
                // 读一行减1加一，并处理字符串
                strArr = str.split(" ");
                from[id]=to[id]="unknown";
                if(strArr.length>3)
                    from[id]=strArr[2]+"-"+strArr[3]+"-"+strArr[4];
                if(strArr.length>5)
                    to[id]=strArr[2]+"-"+strArr[3]+"-"+strArr[5];
                id++;
                bf.close();
                fr.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //web_site
        try {
            fr = new FileReader("..\\..\\web_site.txt");
            bf = new BufferedReader(fr);
            bf.readLine();
            id = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((str = bf.readLine()) != null && id<=rows) {
                // 读一行加一，并处理字符串
                str.substring(5);
                while(str.charAt(0)!=' ')
                    str=str.substring(1);
                str=str.substring(1);
                web_site[id++] = str;
                bf.close();
                fr.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //subject
        try {
            fr = new FileReader("..\\..\\subject.txt");
            bf = new BufferedReader(fr);
            bf.readLine();
            id = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((str = bf.readLine()) != null && id<=rows) {
                // 读一行加一，并处理字符串
                str.substring(5);
                while(str.charAt(0)!=' ')
                    str=str.substring(1);
                str=str.substring(1);
                subject[id++] = str;
                bf.close();
                fr.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //location
        try {
            fr = new FileReader("..\\..\\location.txt");
            bf = new BufferedReader(fr);
            bf.readLine();
            id = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((str = bf.readLine()) != null && id<=rows) {
                // 读一行加一，并处理字符串
                str.substring(5);
                while(str.charAt(0)!=' ')
                    str=str.substring(1);
                str=str.substring(1);
                location[id++] = str;
                bf.close();
                fr.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //deadline
        try {
            fr = new FileReader("..\\..\\deadline.txt");
            bf = new BufferedReader(fr);
            bf.readLine();
            id = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((str = bf.readLine()) != null && id<=rows) {
                // 读一行减1加一，并处理字符串
                strArr = str.split(" ");
                deadline[id++] = strArr[2];
                bf.close();
                fr.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "aaa";
    }

    

    public static void create_index(File file) throws IOException {
        //     BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        int count = 0;
        int least_length = 20;
        String content,strid;
        String filename = file.getName();
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(filename);
        int id = Integer.parseInt(strid = m.group(1));
        //boolean flag = false;
        if ((content = doc_init.file_process.readToString(file)) != null) {
            Document doc_lucene = new Document();
            doc_lucene.add(new StringField("filename_id", id+"", Store.YES));
            doc_lucene.add(new StringField("from", from[id], Store.YES));
            doc_lucene.add(new StringField("to", to[id], Store.YES));
            doc_lucene.add(new StringField("description", web_site[id], Store.YES));
            doc_lucene.add(new StringField("subject", subject[id], Store.YES));
            doc_lucene.add(new StringField("location", location[id], Store.YES));
            doc_lucene.add(new StringField("deadline", deadline[id], Store.YES));
//            doc_lucene.add(new StringField("filename_id", id+"", Store.YES));
//            doc_lucene.add(new StringField("from", from.get(id), Store.YES));
//            doc_lucene.add(new StringField("to", to.get(id), Store.YES));
//            doc_lucene.add(new StringField("description", web_site.get(id), Store.YES));
//            doc_lucene.add(new StringField("subject", subject.get(id), Store.YES));
//            doc_lucene.add(new StringField("location", location.get(id), Store.YES));
//            doc_lucene.add(new StringField("deadline", deadline.get(id), Store.YES));
                // generate
                // <a href="https://www.openstreetmap.org/search?query=&quot;Bengaluru, India&quot;&layers=C">
                // Bengaluru, India</a>

            boolean flag = false;
            try {
                indexWriter.addDocument(doc_lucene);
            } catch (RuntimeException e) {
                e.printStackTrace();
                flag = true;
            }
            if (flag == false) {
                indexWriter.flush();
                indexWriter.commit();
            }
        }
    }

    //  ref link: https://www.jianshu.com/p/1f3ba892fc64

    public static void main(String[] args) throws IOException {
        init();
        luceneIndex.getField(20);
        for (File file : files) {
            System.out.println("create index for " + file.getName());
            create_index(file);
            System.out.println(file.getName() + " ok! ");
        }
 //       indexWriter.forceMerge(1);
 //       indexWriter.close();
        /*
        for (String file : ram_dir.listAll()) {
            fs_dir.copyFrom(ram_dir, file, file, IOContext.DEFAULT);
        }
        */
    }

}
