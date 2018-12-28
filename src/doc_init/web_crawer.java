package doc_init;

import com.google.protobuf.Type;
import doc_init.file_process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
   doc:
   后续可能采用多线程方法，以及一些智能的爬虫算法(反陷阱)或者使用爬虫框架。

   可参考:
   https://blog.csdn.net/lemon_tree12138/article/details/47447731

 */

public class web_crawer {
    static int index = 0;       // doc id
    static final String file_path = "doc\\";
    //   static final String md_file_path = "modify_doc\\";

    static File CreateFile(String Path) {
        File file = new File(Path);
        try {
            if (!file.exists()) {
                File dir = new File(file.getParent());
                dir.mkdirs();
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public static void Write_Name(ArrayList<String> arrayList, int num, int base, String _filename, String file_path) {
        String FileNamePath = file_path + _filename + ".txt";
        try {
            File fileName = CreateFile(FileNamePath);
            FileOutputStream outStream = new FileOutputStream(fileName);
            for (int j = base; j < num; j++) {
                outStream.write(("id: " + j + " ").getBytes());
                outStream.write(arrayList.get(j).getBytes());
                outStream.write('\n');
            }
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void Write_Name(String[] tokens, int num, int base, String _filename) {
        String FileNamePath = "data\\" + _filename + ".txt";
        try {
            File fileName = CreateFile(FileNamePath);
            FileOutputStream outStream = new FileOutputStream(fileName);
            for (int i = base; i < num; i++) {
                outStream.write(("id: " + i + " ").getBytes());
                outStream.write(tokens[num - i - 1].getBytes());
                outStream.write('\n');
                System.out.println("id: " + i);
            }
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
        static String file_process(String tokens) {
            return doc_init.file_process.file_clean(tokens);
        }
    */
    static void WriteStringToFile(String tokens, String file_path_1, int flag, int i) {
        //     String FilePath = "doc\\";
        String FilePath = file_path_1;
        File file;

        try {

            //    if (flag == 1) {
            file = CreateFile(FilePath + index + ".txt");
            index++;
            //     }
            /*
            else {
                file = CreateFile(FilePath + i + ".txt");
                tokens = file_process(tokens);
            }
            */
            FileOutputStream outStream = new FileOutputStream(file);
            outStream.write(tokens.getBytes());
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // get file id (least but still not used)
    public static int test_file() {
        String FilePath = "doc\\";
        int i = 0;
        int flag = 1;
        do {
            i++;
            File file = new File(FilePath + i + ".txt");
            if (!file.exists()) flag = 0;
        } while (flag == 1);
        return i;
    }

    /*
        static String test_debug(String doc) {

        }
    */

    // skip web redirect
    static String test_true(String doc) {
        String site = new String();
        String wrong_str = "302";
        //   Pattern pattern_site = Pattern.compile("https://reasearch.*?edu/dbworld/messages/.*?html");
        Pattern pattern_1 = Pattern.compile("<a href=\"(https://research.*?html)\">");
        int index_wrong = doc.indexOf(wrong_str);
        Matcher matcher;
        while (index_wrong != -1) {
            matcher = pattern_1.matcher(doc);
            if (matcher.find()) {
                site = matcher.group(1);
            } else return "error";
            doc = sendGet(site);
            index_wrong = doc.indexOf(wrong_str);
        }
        return doc;
    }

    static String sendGet(String url_base) {
        String result = "";
        BufferedReader in = null;

        try {
            URL realurl = new URL(url_base);
            URLConnection connection = realurl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
            connection.setRequestProperty("Cache-Control", "max-age=0");
            connection.setRequestProperty("connection", "Keep-Alive");
            //   connection.setRequestProperty("Cookie",cookie);
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("Header", "");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.connect();
            //       String localtion = connection.getHeaderField("Location") ;
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line + '\n';
            }
        } catch (Exception e) {
            System.out.println("error occured !!!" + e);
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    // function begins from begin(int) , num is file id with help that means the array size.
    public static void doCycle(String[] sites, int begin, int num) {
        String content;
        int len = num;
        index = begin;
        for (int j = len - 1 - begin; j >= 0; j--) {
            System.out.print("id: " + index + " ");
            System.out.println(sites[j]);
            content = sendGet(sites[j]);
            content = test_true(content);
            WriteStringToFile(content, file_path, 1, 0);
            //      WriteStringToFile(content, md_file_path, 0, index);
        }
    }

    // because dbworld web will delete and update file every day . sometimes the num of all files
    // decreases , so we need to delete file with file id that it's more than num.
    public static void delete_file(int begin) {
        try {
            int index_1 = test_file();
            for (int j = begin; j < index_1; j++) {
                File file = new File("doc/" + j + ".txt");
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

    public static void main(String[] args) {
        String url_base = "https://research.cs.wisc.edu/dbworld/browse.html";
        String[] sites = new String[1000];
        String[] ddl = new String[1000];
        String[] descriptions = new String[1000];
        String[] subject = new String[1000];
        int num = 4;
        int init = 0;   // just change init and begin .
        int begin = 568;
        String targetStr = sendGet(url_base);
/* for example

<TBODY>
<TR VALIGN=TOP>
<TD>14-Dec-2018 </TD>
<TD>conf. ann. </TD>
<TD>Giovanni Livraga </TD>
<TD><A HREF="http://www.cs.wisc.edu/dbworld/messages/2018-12/1544787616.html" rel="nofollow">Call for Participation - Blockchain @ ACM SACMAT'19</A> </TD>
<TD>31-Mar-2019</TD>
<TD><A rel="nofollow" HREF="https://blockchain-conf.github.io/">web page</A></TD>
</TR></TBODY>

 */
        String patternStr_ = "<TBODY>\\n<TR VALIGN=TOP>\\n<TD>.*?</TD>\\n<TD>(.*?)</TD>\\n<TD>.*?</TD>" +
                "\\n<TD><A HREF=\"(.*?)\".*?>(.*?)</A> </TD>\\n<TD>(.*?)</TD>\\n"
                + "<TD>(<A.*?HREF=\"(.*?)\".*?web page</A>)?.*?</TD>\\n</TR></TBODY>";

        Pattern pattern = Pattern.compile(patternStr_);
        Matcher matcher = pattern.matcher(targetStr);
        String metting_tag = "conf. ann. "; // lecture tag
        while (matcher.find()) {

            if (matcher.group(1).equals(metting_tag)) {
                sites[num] = matcher.group(2);
                subject[num] = matcher.group(3);
                ddl[num] = matcher.group(4);
                if (matcher.group(5) != null && matcher.group(5).length() != 0) {
                    descriptions[num] = matcher.group(6);
                } else
                    descriptions[num] = "";

                /*
                 for test (print info)
                for (int i = 0; i <= 6; i++) {
                    System.out.println(matcher.group(i));
               }
               */
                num++;
            }
        }

    /*
        for (int j = num - 1; j >= 0; j--) {
            WriteStringToFile(sites[j],file_path,1,0);
        }
*/
        if (init == 0) {
            doCycle(sites, 0, num);
        } else if (init == 1) {             // increase document
            int current_index_1 = test_file();
            doCycle(sites, current_index_1, num);
        } else if (init == 2) {
            Write_Name(sites, num, 0, "filename");
            Write_Name(ddl, num, 0, "deadline");
            Write_Name(descriptions, num, 0, "web_site");
            Write_Name(subject, num, 0, "subject");
        } else if (init == 3) {
            System.out.println(test_file());
        } else if (init == 4) {
            doCycle(sites, begin, num);

            delete_file(index + 1);
        }
        System.out.println("init ok!!!" + init);
    }


}
