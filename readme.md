[![](https://jitpack.io/v/lightnew10/DBConnection.svg)](https://jitpack.io/#lightnew10/DBConnection)

# DBConnection

**DBConnection** is a lightweight library designed to simplify database creation and management in Java. With intuitive methods for creating tables, retrieving, and updating data, this library helps you streamline database operations efficiently.

## Features
- **Quick Database Setup**: Create databases and tables with ease.
- **Flexible Data Retrieval**: Retrieve specific or all data from tables.
- **Data Insertion & Updates**: Insert or update data effortlessly.
- **Custom SQL Queries**: Execute custom queries for more advanced use cases.

## Getting Started

### Installation
Add the following to your Maven `pom.xml`:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.lightnew10</groupId>
        <artifactId>DBConnection</artifactId>
        <version>1.5</version>
    </dependency>
</dependencies>
```

## Usage

### Importing the Library

```java
import fr.lightnew.DBConnect;
import fr.lightnew.DBGetter;
import fr.lightnew.DBSetter;
import fr.lightnew.TypeSQL;
```

### Creating a Table

```java
public class Main {
    private DBConnect dbConnect;

    public void createTable() {
        dbConnect = new DBConnect(username, password, port, database);
        //return connection for a prepare statement
        dbConnect.getConnection();

        //Create a table faster
        Map<String, TypeSQL> values = new HashMap<>();
        //TypeSQL set type of SQL with default data
        //Example with VARCHAR: default = VARCHAR(255)
        //If you want modify this default value set your custom query
        values.put("content_test", TypeSQL.VARCHAR);
        dbConnect.createTable("name_table", true, values);

        //Create a table with custom query
        dbConnect.createTable("name_table", "content_test VARCHAR(255), second_content INT");

        //Create your table with your query
        dbConnect.createTableCustomQuery("name_table", "");
    }
}
```

### Retrieving Data

```java
public void getter() {
    DBGetter dbGetter = new DBGetter();
    //Getting information of column for all tables
    dbGetter.getAllData(dbConnect, "name_table", "id", "name");

    //Getting information of row example with id or other
    dbGetter.getSpecificData(dbConnect, "name_table", "id");

        /*
            Verification if data exist in table
            Example with fake table: "table_name"
            |  id  | name  |
            |  5   | test  |
            |  6   | mac   |
            First String is table
            Second String is ROW
            Third String is VALUE ROW
         */
    dbGetter.verifyIsExist(dbConnect, "table_name", "name", "test");
    //Return true
    dbGetter.verifyIsExist(dbConnect, "table_name", "name", "aaa");
    //Return false
}
```

### Inserting and Updating Data

```java
public void updateAndSett() {
    DBSetter setter = new DBSetter();
    //Setter data's
    setter.insertData(dbConnect, "name_table", List.of("name"), List.of("test"));
        /*Value created
                |  id  | name  |
                |  5   | test  |
                |  6   | mac   |
                |  7   | test  |
         */

    //Update data's
    setter.updateData(dbConnect, "name_table", List.of("name"), List.of("test"), "id", "5");
    //Example: UPDATE name_table SET name = 'test' WHERE id = '5'
}
```

## Contact

For any questions or support, feel free to reach out via Discord: `lightnew`.
