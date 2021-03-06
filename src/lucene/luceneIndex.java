package lucene;

import doc_init.file_process;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.*;
import java.nio.channels.FileLock;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class luceneIndex {
    private static final String index_path = "lucene\\index\\";
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
    private static String[] topics_arr = new String[1000];


    private static void init() throws IOException {
        //    ram_dir = new RAMDirectory();
        //   deleteAllFile(index_path);
        analyzer = new StandardAnalyzer();
        iwc = new IndexWriterConfig(analyzer);
        iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);

        files = new File(origin_path).listFiles();
        fs_dir = FSDirectory.open(Paths.get(index_path));

        indexWriter = new IndexWriter(fs_dir, iwc);
        //    indexWriter = new IndexWriter(ram_dir, iwc);
        //ref link: https://www.jianshu.com/p/1f3ba892fc64

    }


    public static void getField(int rows) throws FileNotFoundException {//最多rows或最大数量个文件
        FileReader fr;
        BufferedReader bf;
        String str;
        String[] strArr;
        int id;
        //from-to
        try {
            fr = new FileReader("data\\date.txt");
            bf = new BufferedReader(fr);
            id = 0;
            // 一次读入一行，直到读入null为文件结束
            while ((str = bf.readLine()) != null && id <= rows) {
                // 读一行减1加一，并处理字符串
                strArr = str.split(" ");
                from[id] = to[id] = "unknown";
                if (strArr.length > 3)
                    from[id] = strArr[2] + "-" + strArr[3];

                if (strArr.length > 5) {
                    from[id] = strArr[2] + "-" + strArr[3] + "-" + strArr[4];
                    to[id] = strArr[2] + "-" + strArr[3] + "-" + strArr[5];
                }
                id++;
                System.out.println(id + " " + from[id - 1] + " " + to[id - 1]);
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //web_site
        try {
            fr = new FileReader("data\\web_site.txt");
            bf = new BufferedReader(fr);
            id = 0;
            // 一次读入一行，直到读入null为文件结束
            while ((str = bf.readLine()) != null && id <= rows) {
                // 读一行加一，并处理字符串
                str = str.substring(5);
                while (str.charAt(0) != ' ')
                    str = str.substring(1);
                str = str.substring(1);
                System.out.println(str);
                web_site[id++] = str;
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //subject
        try {
            fr = new FileReader("data\\subject.txt");
            bf = new BufferedReader(fr);
            id = 0;
            // 一次读入一行，直到读入null为文件结束
            while ((str = bf.readLine()) != null && id <= rows) {
                // 读一行加一，并处理字符串
                str = str.substring(5);
                while (str.charAt(0) != ' ')
                    str = str.substring(1);
                str = str.substring(1);
                System.out.println(str);
                subject[id++] = str;
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //location
        try {
            fr = new FileReader("data\\location.txt");
            bf = new BufferedReader(fr);
            id = 0;
            // 一次读入一行，直到读入null为文件结束
            while ((str = bf.readLine()) != null && id <= rows) {
                // 读一行加一，并处理字符串
                str = str.substring(5);
                while (str.charAt(0) != ' ')
                    str = str.substring(1);
                str = str.substring(1);
                System.out.println(str);
                location[id++] = str;
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //deadline
        try {
            fr = new FileReader("data\\deadline.txt");
            bf = new BufferedReader(fr);
            id = 0;
            // 一次读入一行，直到读入null为文件结束
            while ((str = bf.readLine()) != null && id <= rows) {
                // 读一行减1加一，并处理字符串
                strArr = str.split(" ");
                deadline[id++] = strArr[2];
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //topics
        id = 0;
        // 一次读入一行，直到读入null为文件结束
        int upper_case = doc_init.web_crawer.test_file();
        while (id < upper_case) {
            String filename = "topics\\" + "topic_" + id + ".txt";
            File f = new File(filename);
            String topics = file_process.readToString(f);
            // 读一行减1加一，并处理字符串
            topics_arr[id++] = topics;
        }
        return;
    }


    public static void create_index(File file) throws IOException {
        //     BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        int count = 0;
        int least_length = 20;
        int id = 0;
        String content;
        String filename = file.getName();
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(filename);
        if (m.find())
            id = Integer.parseInt(m.group(0));
        System.out.println("id is " + id);
        //boolean flag = false;
        if ((content = doc_init.file_process.readToString(file)) != null) {
            Document doc_lucene = new Document();
            doc_lucene.add(new TextField("filename_id", id + "", Store.YES));
            doc_lucene.add(new TextField("from", from[id], Store.YES));
            doc_lucene.add(new TextField("to", to[id], Store.YES));
            doc_lucene.add(new TextField("description", web_site[id], Store.YES));
            doc_lucene.add(new TextField("subject", subject[id], Store.YES));
            doc_lucene.add(new TextField("location", location[id], Store.YES));
            doc_lucene.add(new TextField("deadline", deadline[id], Store.YES));
            doc_lucene.add(new TextField("topics", topics_arr[id], Store.YES));

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
        // deleteAllFile(index_path);
        luceneIndex.getField(600);

        for (File file : files) {
            System.out.println("create index for " + file.getName());
            create_index(file);
            System.out.println(file.getName() + " ok! ");
        }
    }

    public static void deleteLock(String url) {
        try {
            FileLock file_lock_real = null;
            File file_lock_file = new File(url + "write.lock");
            if (file_lock_file.exists()) {
                RandomAccessFile randAccessfile = new RandomAccessFile(file_lock_file, "rws");
                file_lock_real = randAccessfile.getChannel().tryLock();
                if (file_lock_real != null && file_lock_file.isDirectory()) {
                    file_lock_file.delete();
                } else if (file_lock_real != null && file_lock_file.isFile()) {
                    file_lock_file.deleteOnExit();
                }
                if (file_lock_real != null) {
                    file_lock_real.release();
                    file_lock_real.channel().close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void deleteAllFile(String url) {
        try {
            deleteLock(url);
            File file = new File(url);
            if (!file.exists()) {
                return;
            }
            if (!file.isDirectory()) {
                return;
            }
            for (File f : file.listFiles()) {
                if (f.isFile())
                    f.delete();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
