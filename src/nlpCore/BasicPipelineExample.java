package nlpCore;

import doc_init.web_crawer.*;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.*;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BasicPipelineExample {

    public static ArrayList<String> loc_store = new ArrayList<>();
    public static ArrayList<String> date_store = new ArrayList<>();

    public static String process_path = "modify_doc\\";
    public static String text = "";
    public static String gene_path = "data\\";
    public static ArrayList<Integer> location = new ArrayList<>();
    public static ArrayList<Integer> date_time = new ArrayList<>();
    public static int date_choose = 0;

    public static String year_val = "", month_val = "", date_from = "", date_to = "";
    private static final String year = "(2018|2019)";
    private static final String month = "(Jan(?:uary)?|Feb(?:ruary)?|Mar(?:ch)?|Apr(?:il)?|May|June|July|" +
            "Aug(?:ust)?|Sep(?:tember)?|Oct(?:ober)?|Nov(?:ember)?|Dec(?:ember)?)";
    private static final String date = "(\\d{1,2})(?:th)?[ ]*-[ ]*(\\d{1,2})(?:th)?";
    private static final String time_1 = month + " " + date + " " + year;
    private static final String time_2 = date + " " + month + " " + year;
    // don't consider over two months.
    /*
    private static final String text_test = "(Jan(?:uary)?|Feb(?:ruary)?|Mar(?:ch)?|Apr(?:il)?|May|June|July|"+
            "Aug(?:ust)?|Sep(?:tember)?|Oct(?:ober)?|Nov(?:ember)?|Dec(?:ember)?)";
  */

    public static String readFiletoString(String file_path, int i) {
        File file = new File(file_path + "md_" + i + ".txt");
        return doc_init.file_process.readToString(file);
    }

    //http://www.cs.wisc.edu/dbworld/messages/2018-09/1537275377.html
    public static boolean RE_find_date(String tokens) {
        Pattern p_1 = Pattern.compile(time_1);
        Pattern p_2 = Pattern.compile(time_2);
        Matcher matcher;
        int choose = 0;
        matcher = p_1.matcher(tokens);
        if (matcher.find()) {
            System.out.println("date found !!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println(matcher.group(0));
            choose = 1;
        } else {
            matcher = p_2.matcher(tokens);
            if (matcher.find()) {
                System.out.println("date found !!!!!!!!!!!!!!!!!!!!!!!!!!!");
                System.out.println(matcher.group(0));
                choose = 2;
            }
        }
        if (choose != 0) {
            /*
            for (int i = 0; i < 5; i++) {
                System.out.println(matcher.group(i));
            }
            */
            year_val = matcher.group(4);
            if (choose == 1) {
                month_val = matcher.group(1);
                date_from = matcher.group(2);
                date_to = matcher.group(3);
            } else if (choose == 2) {
                date_from = matcher.group(1);
                date_to = matcher.group(2);
                month_val = matcher.group(3);
            }
            //      System.out.println(date_from + date_to + month_val + year_val);
        }
        return choose != 0;
    }


    // just simply consider this "date,date,date,date" situation.
    public static boolean find_date(List<String> args, int country_index) {
        // first time . search country's rightward words.
        int index;
        List<String> sibiling_right = args.subList(country_index + 1, args.size());
        if ((index = find_date(sibiling_right)) != -1) {
            System.out.println("find date_right ok!!!!");
            for (int i = 0; i < date_choose; i++) {
                date_time.add(index + i + country_index + 1);
            }
            return true;
        } else {
            List<String> sibling_left = args.subList(0, country_index);
            if ((index = find_date(sibling_left)) != -1) {
                System.out.println("find date_left ok!!!!!");
                for (int j = 0; j < date_choose; j++) {
                    date_time.add(index + j);
                }
                return true;
            }
        }
        return false;
    }

    public static int find_date(List<String> args) {
        int index;
        List<String> sub_datelist_1 = Arrays.asList("DATE", "DATE", "DATE", "DATE");
        List<String> sub_datelist_2 = Arrays.asList("DATE", "DATE");
        if ((index = Collections.indexOfSubList(args, sub_datelist_1)) != -1) {
            System.out.println("find date_1!!!!!!!!!");
            date_choose = 4;
            return index;
        } else if ((index = Collections.indexOfSubList(args, sub_datelist_2)) != -1) {
            System.out.println("find date_2!!!!!!!!!");
            date_choose = 2;
            return index;
        }
        //     System.out.println("oh no!  i haven't find date");
        return -1;
    }


    // still consider multiple location situation. for example:
    //id: 14 http://www.cs.wisc.edu/dbworld/messages/2018-09/1537369056.html
    //Golden Bay Beach Hotel 5*, Larnaca, Cyprus, June 9-12, 2019
    //id: 18 http://www.cs.wisc.edu/dbworld/messages/2018-09/1537451213.html
    //Turin, Italy, at Centro Congressi Lingotto.
    //
    // Bengalaru, India,  bengalaru is identified as people name.
    //Lisbon Portugal    Limassol
    public static int find_location(List<String> args) {
        // just country and city , organization and state
        int index;
        // result is unused.
        String matcher_right = "ORGANIZATION";
        int right_right_sum = 0, left_right_sum = 0;
        String matcher_left = "CITY|STATE_OR_PROVINCE";
        //     String matcher_special = "Setubal|Bengalaru";
        int skip_blank_right = 0, skip_blank_left = 0;
        if ((index = args.indexOf("COUNTRY")) != -1) {
            for (int j = index + 1; j < args.size(); j++) {
                if (skip_blank_right >= 2) {
                    break;
                }
                String elem = args.get(j);
                if (right_right_sum > 2) {
                    break;
                } else if (elem.equals(matcher_right)) {
                    location.add(j);
                    right_right_sum++;
                } else if (elem.equals("O")) {
                    skip_blank_right++;
                    continue;
                } else if (elem.matches(matcher_left)) {
                    location.add(j);
                } else
                    break;
            }
            if (location.size() != 0)
                Collections.reverse(location);
            location.add(index);
            for (int i = index - 1; i >= 0; i--) {
                String elem = args.get(i);
                if (left_right_sum > 2) {
                    break;
                } else if (elem.equals(matcher_right)) {
                    location.add(i);
                    left_right_sum++;
                } else if (elem.equals("O")) {
                    skip_blank_left++;
                    continue;
                } else if (elem.matches(matcher_left)) {
                    location.add(i);
                } else {
                    break;
                }
                if (skip_blank_left >= 2) {
                    break;
                }
            }
            Collections.reverse(location);
        }
        return index;   // index is the country index.
        // return result
        // result is the city num
        // but in this program, result , i haven't use it.
    }

    public static void data_Clear() {
        location.clear();
        date_time.clear();

    }

    // public static String text_ = "";

    public static void main(String[] args) {
        int init_flag = 0;
        int debug_set = 0;
        int upper_case = doc_init.web_crawer.test_file();
        Properties props = new Properties();
        //props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,regexner"); parse
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner");
        props.setProperty("coref.algorithm", "neural");
        // props.put("regexner.mapping","org/foo/resources/jg-regexner.txt" );
        if (init_flag == 0) {
            for (int tmp = 0; tmp < upper_case; tmp++) {
                // tmp = 2333;
                System.out.println("this is " + tmp + " situation.");
                text = readFiletoString(process_path, tmp);
                CoreDocument document = new CoreDocument(text);
                StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
                pipeline.annotate(document);


                if (debug_set == 1) {
                    int i = 0;
                    for (CoreLabel token : document.tokens()) {
                        System.out.println(i++);
                        System.out.println("Example: token");
                        System.out.println(token);
                        System.out.println(token.value());
                        System.out.println();
                    }
                }


                if (document.tokens().size() <= 20) {
                    System.out.println(" doc size is too small !!!  ");
                    loc_store.add(" ");
                    date_store.add(" ");
                    continue;
                }

                boolean ok_location = true;
                boolean ok_date = !RE_find_date(text);
                int date_wrong_ = 0;
                // it means that if re_find is ok , then corenlp doesn't need to extract date.

                int loc_index;

                // maybe use entityMentions via CoreSentence sentence
                for (CoreSentence sentence : document.sentences()) {
                               /*
                    String sentenceText = sentence.text();
                    System.out.println("Example: sentence");
                    System.out.println(sentenceText);
                    System.out.println();
*/
                    List<String> nerTags = sentence.nerTags();
//           /*
                    System.out.println("Example: ner tags");
                    System.out.println(nerTags);
                    System.out.println();
//*/
                    if (ok_location && (loc_index = find_location(nerTags)) != -1) {
                        System.out.println("location found !!!!!!!!!!!!!!!!!!!!!!!!!!!");
                        ok_location = false;
                        String str_joint = "";
                        for (int element : location) {
                            //       System.out.println(sentence.tokens());
                            String str = sentence.tokens().get(element).value();
                            str_joint += str + " ";
                            System.out.print(str);
                            System.out.print(' ');
                        }
                        if (tmp == 17) {
                            System.out.println("debug now!");
                        }
                        loc_store.add(str_joint);
                        System.out.print("\n");
                        if (ok_date) {
                            if (find_date(nerTags, loc_index)) {
                                System.out.println("date_3|4 found !!!!!!!!!!!!!!!!!!!!!!!!!!!");
                                ok_date = false;
                                date_wrong_ = 1;
                                String date_joint = "";
                                for (int elem : date_time) {
                                    String str = sentence.tokens().get(elem).value();
                                    System.out.print(str);
                                    System.out.print(' ');
                                    date_joint += str + " ";
                                }
                                date_store.add(date_joint);
                                System.out.print("\n");
                                break;
                            }
                        }
                    }
                }
                if (!ok_date && date_wrong_ == 0) {
                    String date_joint = "";
                    date_joint = year_val + " " + month_val + " " + date_from + " " + date_to;
                    date_store.add(date_joint);
                } else if (ok_date && date_wrong_ == 0) {
                    date_store.add(" ");
                }
                if (ok_location) {
                    loc_store.add(" ");
                }

                data_Clear();
            }
            doc_init.web_crawer.Write_Name(loc_store, upper_case, 0, "location", gene_path);
            doc_init.web_crawer.Write_Name(date_store, upper_case, 0, "date", gene_path);
        }
    }
}

