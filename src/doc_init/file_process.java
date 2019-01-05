package doc_init;

import org.apache.lucene.store.Directory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class file_process {
    private static final String modify_path = "modify_doc\\";
    private static final String origin_path = "doc\\";

    private static final String text_0 = "font-face";
    private static final String text_1 = "<PRE.*?>(.*)</PRE>";
    //    private static final String text_2 = "<p>(.*)</p>";
    private static final String text_3 = "<HEAD>(?s).*</HEAD>";
    private static final String text_4 = "<ul>(?s).*<ul>";
    //  private static final String text_5 = "\\t|\\r|\\n";
    private static final String text_5 = "\\t|\\r";
    private static final String text_6 = "<a(?s).*?/a>";
    private static final String text_7 = "\\+|-{2,}|\\||\\*{2,}|={2,}";
    private static final String text_8 = "https:.*?\\s";
    private static final String text_9 = "DBWorld Message";
    private static final String text_10 = "http:.*?\\s";
    private static final String text_11 = ",|\\.|#|$|@|!";
    // maybe use text_16 rather than text_11 about processing comma.
    private static final String text_12 = "www.*?(org|com|cn)";
    private static final String text_13 = "\\{(.|\\s)*?\\}";
    private static final String text_14 = "&.*?;";
    private static final String text_15 = "/\\*(.|\\s)*?\\*/";
    private static final String text_16 = "\\n[ ]\\n";
    private static final String html_tag = "<.*?>";
    private static final String html_tag_1 = "h\\d+";
    /*
        private static Directory fs_dir;

        private static File[] files;

        private static void init() {
            files = new File(origin_path).listFiles();
        }
    */

    public static String duplicate_replace(String line, String pattern_) {
        Pattern p = Pattern.compile(pattern_);
        Matcher m = p.matcher(line);
        line = m.replaceAll(" ");
        return line;
    }

    public static String file_clean(String line) {
        line = line.replaceAll("[^\\x00-\\x7F]", " ");
        line = line.replaceAll("\\n{2,}", "\n");
    //    line = line.replaceAll(",{2,}", ",");
        String index[] = {text_9, text_0, text_5, text_4, text_3, text_6, text_7, text_8};
        String index_2[] = {text_15, text_10, text_12, text_11, text_13, text_14};
        Matcher matcher;
        for (int i = 0; i < index.length; i++) {
            line = duplicate_replace(line, index[i]);
        }

        Pattern pattern = Pattern.compile(text_1);
        matcher = pattern.matcher(line);
        if (matcher.find()) {
            line = matcher.group(1);
        }
        line = duplicate_replace(line, html_tag);
        line = duplicate_replace(line, html_tag_1);
        for (int j = 0; j < index_2.length; j++) {
            line = duplicate_replace(line, index_2[j]);
        }

        line = duplicate_replace(line, html_tag);
        line = line.replaceAll(":{1,}", ":");
        line = line.replaceAll("[ ]+", " ");
        line = line.replaceAll("\\n{1,}", "\n");
        line = line.replaceAll(text_16,"\n");
        return line.trim();
    }

    public static String readToString(File file) {
        Long file_len = file.length();
        byte[] file_content = new byte[file_len.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(file_content);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(file_content);
    }

    static void WriteStringToFile(String tokens, int i) {
        File file;
        try {
            file = doc_init.web_crawer.CreateFile(modify_path + "md_" + i + ".txt");
            tokens = file_clean(tokens);
            FileOutputStream outStream = new FileOutputStream(file);
            outStream.write(tokens.getBytes());
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delete_file(int begin) {
        try {
            int index_1 = doc_init.web_crawer.test_file();
            for (int j = begin; j < index_1; j++) {
                File file = new File("modify_doc/" + j + ".txt");
                if (file.exists()) {
                    if (file.delete()) {
                        System.out.println(file.getName() + " 文件已被删除！");
                    } else {
                        System.out.println("文件删除失败！");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void modify_file(File file, int i) {
        String content = readToString(file);
        WriteStringToFile(content, i);
    }

    public static void main(String[] args) throws IOException {
        //    init();
        int i = 0;
        int init_flag = 0;
        if (init_flag == 0) {
            int upper_case = doc_init.web_crawer.test_file();
            for (int j = 0; j < upper_case; j++) {
                File file = new File(origin_path + j + ".txt");
                System.out.println("Modify file for " + file.getName());
                modify_file(file, j);
                System.out.println("modify done ok !!!!");
            }
        } else if (init_flag == 1) {
            delete_file(0);
        } else if (init_flag == 2) {
            // debug
            String test_1 = "diaifja\nlasjisdf";
            test_1 = file_clean(test_1);
            System.out.println(test_1);
        }
        System.out.println("init ok!!!" + init_flag);

    }
}

