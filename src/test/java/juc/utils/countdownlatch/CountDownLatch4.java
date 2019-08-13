package juc.utils.countdownlatch;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CountDownLatch4 {

    private static Random random = new Random(System.currentTimeMillis());
    static class Event {
        int id = 0;

        public Event(int id) {
            this.id = id;
        }
    }

    static class Table {
        String tableName;
        long sourceRecordCount = 10;
        long targetCount;
        String sourceColumnSchema = "<table name='a'><column name='col1' type = varchar2/></table>";
        String targetColumnSchema = "";

        public Table(String tableName, long sourceRecordCount) {
            this.tableName = tableName;
            this.sourceRecordCount = sourceRecordCount;
        }
    }

    private static List<Table> capture(Event event) {
        List<Table> list = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            list.add(new Table("table-" + event.id + "-" + i, i * 1000));
        }
        return list;
    }

    @Test
    public void test() {

        Event[] events = {new Event(1),new Event(2)};

    }


    static class TrustSourceRecordCount implements Runnable{

        @Override
        public void run() {
            
        }
    }
}
