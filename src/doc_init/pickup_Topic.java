package doc_init;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class pickup_Topic {
    /*
        private static final String text_doc = "The 12th WISTP International Conference on Information Security Theory and Practice (WISTP'2018) seeks original submissions from academia and industry presenting novel research on all theoretical and practical aspects of security and privacy as well as experimental studies of fielded systems the application of security technology the implementation of systems and lessons learned We encourage submissions from other communities such as law business and policy that present these communities' perspectives \n" +
                "Topics of Interest:\n" +
                "- Security and Privacy in Smart Devices\n" +
                " - Biometrics National ID cards\n" +
                " - Embedded Systems Security and TPMs\n" +
                " - Interplay of TPMs and Smart Cards\n" +
                " - Mobile Codes Security\n" +
                " - Mobile Devices Security\n" +
                " - Mobile Malware\n" +
                " - Mobile OSes Security Analysis\n" +
                " - RFID Systems\n" +
                " - Smart Card\n" +
                " - Smart Devices Applications\n" +
                " - Wireless Sensor Node\n" +
                "* Security and Privacy in Networks\n" +
                " - Ad Hoc Networks\n" +
                " - Content Defined Networks\n" +
                " - Delay-Tolerant Networks\n" +
                " - Domestic Networks\n" +
                " - GSM/GPRS/UMTS Systems\n" +
                " - Peer-to-Peer Networks\n" +
                " - Security Issues in Mobile and Ubiquitous Networks\n" +
                " - Sensor Networks: Campus Area Body Area Sensor and Metropolitan Area Networks\n" +
                " - Vehicular Networks\n" +
                " - Wireless Communication: Bluetooth NFC WiFi WiMAX others\n" +
                "* Security and Privacy in Architectures Protocols Policies Systems and Applications\n" +
                " - BYOD Contexts\n" +
                " - Big Data Management\n" +
                " - Cloud Systems\n" +
                " - Critical Infrastructure (e g for Medical or Military Applications)\n" +
                " - Crowdsourcing\n" +
                " - Cyber-Physical Systems\n" +
                " - Data and Computation Integrity\n" +
                " - Digital Rights Management (DRM)\n" +
                " - Distributed Systems\n" +
                " - Grid Computing\n" +
                " - Identity and Trust Management\n" +
                " - Information Assurance\n" +
                " - Information Filtering\n" +
                " - Internet of Things\n" +
                " - Intrusion Detection\n" +
                " - Lightweight cryptography\n" +
                " - Localization Systems (Tracking of People and Goods)\n" +
                " - M2M (Machine to Machine) H2M (Human to Machine) and M2H (Machine to Human)\n" +
                " - Mobile Commerce\n" +
                " - Multimedia Applications\n" +
                " - Public Administration and Governmental Services\n" +
                " - Privacy Enhancing Technologies\n" +
                " - Secure self-organization and self-configuration\n" +
                " - Security Models Architecture and Protocol: for Identification and Authentication Access Control Data Protection\n" +
                " - Security Policies (Human-Computer Interaction and Human Behavior Impact)\n" +
                " - Security Measurements\n" +
                " - Smart Cities\n" +
                " - Social Networks\n" +
                " - Systems Controlling Industrial Processes\n" +
                "IMPORTANT DATES";
                */
    private static final String text_0 = "topics";
    private static final String text_1 = "\\n.*?\\n";
    private static final String text_2 = "\\n";
    private static final String text_3 = "topics.*?\\n.*?topics";
    private static final String text_star_dash = "\\n[ ]?(?:[\\*-])[ ]?(.*?)\\n";
    public static ArrayList<String> topics_list = new ArrayList<>();


    public static ArrayList<String> pickUP(String doc, ArrayList<String> topic_list) {

        int begin = 0, end = 0;
        String sub_doc="";
        Pattern pattern_1;
        Matcher matcher_1;
        Pattern pattern_0 = Pattern.compile(text_3, Pattern.CASE_INSENSITIVE);
        Matcher matcher_0 = pattern_0.matcher(doc);
        int flag = 0;
        if (matcher_0.find()) {
            sub_doc = doc.substring(matcher_0.end());
            flag = 1;
        }
        // ignore uppercase and lowercase
        else {
            pattern_1 = Pattern.compile(text_0, Pattern.CASE_INSENSITIVE);
            matcher_1 = pattern_1.matcher(doc);
            if (matcher_1.find()) {
                sub_doc = doc.substring(matcher_1.start());
                flag = 1;
            }
        }

        if (flag==1) {
            pattern_1 = Pattern.compile(text_1);
            matcher_1 = pattern_1.matcher(sub_doc);
            if (matcher_1.find()) {
                begin = matcher_1.start();
                end = matcher_1.end();
                String res = test_true_topic(begin, end, sub_doc);
                if (!res.equals("")) {
                    topic_list.add(res);
                } else
                    return new ArrayList<>();
            }
            Pattern pattern_2 = Pattern.compile(text_2);
            Matcher matcher_2 = pattern_2.matcher(sub_doc);
            int j = 0;
            while (matcher_2.find()) {
                j++;
                if (j == 2) break;
            }
            end = end - 1;
            while (matcher_2.find()) {
                begin = end;
                end = matcher_2.start();
                String res = test_true_topic(begin, end, sub_doc);
                if (!res.equals("")) {
                    topic_list.add(res);
                } else
                    break;
            }
            return topic_list;

        }
        return new ArrayList<>();
    }

    // doc[begin] == '\n' and doc[end] == '\n'
    public static String test_true_topic(int begin, int end, String doc) {
        String result = "";
        String sub_doc = doc.substring(begin, end + 1);
        //      System.out.println(sub_doc);
        Pattern pattern_stda = Pattern.compile(text_star_dash);
        Matcher matcher_stda = pattern_stda.matcher(sub_doc);
        if (matcher_stda.find()) {
            result = matcher_stda.group(1);
        }
        return result;
    }

    public static final String origin_path = "modify_doc\\";
    public static final String genera_path = "topics\\";

    public static void main(String[] args) {
        int upper_case = doc_init.web_crawer.test_file();
        for (int j = 0; j < upper_case; j++) {
            File file = new File(origin_path + "md_" + j + ".txt");
            System.out.println("topics extract file for " + file.getName());
            if (j == 9) {
                System.out.println(j);
            }
            String content = file_process.readToString(file);
            WriteStringToFile(pickUP(content, topics_list), j);
            System.out.println("topics process done ok !!!!");
            topics_list.clear();
        }

    }

    static void WriteStringToFile(ArrayList<String> str_list, int i) {
        File file;
        try {
            file = doc_init.web_crawer.CreateFile(genera_path + "topic_" + i + ".txt");
            String tokens = "";
            if (str_list.size() == 0) {
                tokens = "";
            } else {
                for (String meta_str : str_list) {
                    tokens += meta_str + "\n";
                }
            }
            FileOutputStream outStream = new FileOutputStream(file);
            outStream.write(tokens.getBytes());
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
