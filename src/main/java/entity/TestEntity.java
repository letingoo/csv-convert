package entity;

import annotation.ExcelField;

public class TestEntity {

    @ExcelField(name = "名称")
    private String name;

    @ExcelField(name = "日期")
    private String date;


    public TestEntity(String name, String date) {
        this.name = name;
        this.date = date;
    }
}
