package fr.lightnew;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class DBGetter {


    /**
     * Default execute query
     */
    private MultiValuedMap<Object, Object> executeQuery(DBConnect dbConnect, String query, List<String> row, Object... parameters) {
        dbConnect.reConnect();
        if (dbConnect.getConnection() == null)
            throw new RuntimeException("connection to your database is null");

        MultiValuedMap<Object, Object> list = new ArrayListValuedHashMap<>();

        try (PreparedStatement statement = dbConnect.getConnection().prepareStatement(query)) {
            for (int i = 0; i < parameters.length; i++) {
                statement.setObject(i + 1, parameters[i]);
            }

            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    for (String select : row) {
                        list.put(result.getRow(), result.getObject(select));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    /**
     * Getting all data and selected value
     * @param dbConnect Connection to database
     * @param nameTable The name of table where the code getting a future selection
     * @param selects Select the desired data
     * @return list of all select data
     * <p>
     * Example return data -> {1=[test, test1], 2=[bigtest, test]}
     */
    public MultiValuedMap<Object, Object> getAllData(DBConnect dbConnect, String nameTable, String... selects) {
        dbConnect.reConnect();
        if (dbConnect.getConnection() == null)
            throw new RuntimeException("connection to your database is null");
        if (nameTable == null || nameTable.isEmpty())
            throw new RuntimeException("parameter 'nameTable' is null or empty");

        String query;
        if (selects.length > 0) {
            query = "SELECT " + String.join(",", selects) + " FROM " + nameTable;
        } else {
            query = "SELECT * FROM " + nameTable;
        }

        return executeQuery(dbConnect, query, Arrays.asList(selects));
    }

    /**
     * Getting all data and selected value with your custom data
     * @param nameTable Getting with your custom data
     */
    public MultiValuedMap<Object, Object> getSpecificDataWithCustomParameter(DBConnect dbConnect, String nameTable, List<String> row, String customParameter) {
        dbConnect.reConnect();
        if (nameTable == null || nameTable.isEmpty())
            throw new RuntimeException("parameter 'nameTable' is null or empty");

        String query = "SELECT " + String.join(",", row) + " FROM " + nameTable + " " + customParameter;
        return executeQuery(dbConnect, query, row);
    }


    /**
     * Getting data with where in table
     */
    public MultiValuedMap<Object, Object> getSpecificDataWhere(DBConnect dbConnect, String nameTable, List<String> row, String where, String value) {
        dbConnect.reConnect();
        if (nameTable == null || nameTable.isEmpty())
            throw new RuntimeException("parameter 'nameTable' is null or empty");

        String query = "SELECT " + String.join(",", row) + " FROM " + nameTable + " WHERE " + where + " = ?";
        return executeQuery(dbConnect, query, row, value);
    }

    public MultiValuedMap<Object, Object> getDataQueryWhere(DBConnect dbConnect, String nameTable, String where, String value, String... selects) {
        dbConnect.reConnect();
        if (nameTable == null || nameTable.isEmpty())
            throw new RuntimeException("parameter 'nameTable' is null or empty");

        String query = "SELECT " + String.join(",", selects) + " FROM " + nameTable + " WHERE " + where + " = ?";
        return executeQuery(dbConnect, query, Arrays.asList(selects), value);
    }


    public Boolean verifyIsExist(DBConnect dbConnect, String table, String row, String valueRow) {
        dbConnect.reConnect();
        try {
            PreparedStatement statement = dbConnect.getConnection().prepareStatement("SELECT " + row + " FROM " + table +";");
            ResultSet result = statement.executeQuery();
            while (result.next())
                if (result.getString(row).equalsIgnoreCase(valueRow))
                    return true;
        } catch (SQLException e) {e.printStackTrace();}
        return false;
    }
}
