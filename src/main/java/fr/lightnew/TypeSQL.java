package fr.lightnew;

import lombok.Getter;

@Getter
public enum TypeSQL {
    TINYINT("TINYINT"),
    SMALLINT("SMALLINT"),
    MEDIUMINT("MEDIUMINT"),
    INT("INT"),
    BIGINT("BIGINT"),
    FLOAT("FLOAT"),
    DOUBLE("DOUBLE"),
    DECIMAL("DECIMAL(10,2)"),
    DATE("DATE"),
    DATETIME("DATETIME"),
    TIMESTAMP("TIMESTAMP"),
    TIME("TIME"),
    YEAR("YEAR"),
    CHAR("CHAR(100)"),
    VARCHAR("VARCHAR(255)"),
    TINYTEXT("TINYTEXT"),
    TEXT("TEXT"),
    MEDIUMTEXT("MEDIUMTEXT"),
    LONGTEXT("LONGTEXT"),
    BINARY("BINARY(16)"),
    VARBINARY("VARBINARY(255)"),
    TINYBLOB("TINYBLOB"),
    BLOB("BLOB"),
    MEDIUMBLOB("MEDIUMBLOB"),
    LONGBLOB("LONGBLOB"),
    JSON("JSON"),
    GEOMETRY("GEOMETRY");

    private String type;

    TypeSQL(String type) {
        this.type = type;
    }
}
