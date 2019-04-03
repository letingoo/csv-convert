import entity.TestEntity;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List list = new ArrayList();
        list.add(new TestEntity("123", "2019-03-28"));
        list.add(new TestEntity("abc", "2019-03-29"));

        System.out.println(CsvExportUtil.exportCsv(list));
    }
}
