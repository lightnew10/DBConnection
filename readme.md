[![](https://jitpack.io/v/lightnew10/DBConnection.svg)](https://jitpack.io/#lightnew10/DBConnection)

# How to use ?

```java
import fr.lightnew.DBConnect;
import fr.lightnew.TypeSQL;

import java.util.HashMap;

public class Main {
    
    public DBConnect dbConnect;
    
    public void testing() {
        dbConnect = new DBConnect(username, password, port, database);
        //return connection for prepare statement
        dbConnect.getConnection();

        //Create a table faster
        Map<String, TypeSQL> values = new HashMap<>();
        values.put("content_test", TypeSQL.VARCHAR);
        dbConnect.createTable("name table", true, values);

        //Create a table with custom query
        dbConnect.createTable("name table", "content_test VARCHAR(255), second_content INT");
    }
}
```
