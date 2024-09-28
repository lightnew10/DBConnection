package fr.lightnew.test;

import fr.lightnew.DBConnect;
import fr.lightnew.TypeSQL;

import java.util.HashMap;
import java.util.Map;

public class MainTest {
    public static void main(String[] args) {
        DBConnect dbConnect = new DBConnect("root", "", "testingddb");
        Map<String, TypeSQL> values = new HashMap<>();
        values.put("name", TypeSQL.VARCHAR);
        dbConnect.createTable("name_table", true, values);
    }
}
