package Time_process;

import doc_init.file_process;
import doc_init.pickup_Topic;
import doc_init.web_crawer;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {
    Timer timer;
    private static final long period = 24 * 3600 * 1000;

    public TimerTest() {
        Date time = getTime();
        System.out.println("指定时间time=" + time);
        timer = new Timer();
        //test_test  choose this way.
        // 1000 means 1s, 2000 means 2 second.
        // 1000 is delay, 2000 is period
        timer.schedule(new TimerTaskTest(), 1000, 2000);
        //timer.schedule(new TimerTaskTest(),time,period);
    }

    public Date getTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        Date time = calendar.getTime();
        return time;
    }

    public static void main(String[] args) {
        new TimerTest();
    }

    public class TimerTaskTest extends TimerTask {
        @Override
        public void run() {
            try {
                String[] args = {""};
                Date date = new Date(this.scheduledExecutionTime());
                System.out.println("本次执行该线程的时间为：" + date);
                web_crawer.main(args, 1);
                file_process.main(args, 0);
                nlpCore.BasicPipelineExample.main(args);
                pickup_Topic.main(args);
                lucene.luceneIndex.main(args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
