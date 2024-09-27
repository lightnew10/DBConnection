package fr.lightnew.test;

import fr.lightnew.DBConnect;

public class Main {

    public static void main(String[] args) {
        DBConnect dbConnect = new DBConnect("root", "", 3306, "test");
        dbConnect.createTable(ClazzTest.class);
    }
}
