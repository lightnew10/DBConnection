package fr.lightnew;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class DBSetter {

    public void insertData(DBConnect dbConnect, String nameTable, List<String> columns, List<Object> values) {
        dbConnect.reConnect();
        if (dbConnect.getConnection() == null)
            throw new RuntimeException("Connection to your database is null");
        if (columns.size() != values.size())
            throw new IllegalArgumentException("Number of columns does not match number of values");

        String columnPart = String.join(",", columns);
        String valuePlaceholders = String.join(",", Collections.nCopies(values.size(), "?"));

        String query = "INSERT INTO " + nameTable + " (" + columnPart + ") VALUES (" + valuePlaceholders + ")";

        try (PreparedStatement statement = dbConnect.getConnection().prepareStatement(query)) {
            for (int i = 0; i < values.size(); i++) {
                statement.setObject(i + 1, values.get(i));
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateData(DBConnect dbConnect, String nameTable, List<String> columns, List<Object> values, String whereClause, Object whereValue) {
        dbConnect.reConnect();
        if (dbConnect.getConnection() == null)
            throw new RuntimeException("Connection to your database is null");
        if (columns.size() != values.size())
            throw new IllegalArgumentException("Number of columns does not match number of values");

        String setPart = String.join(" = ?, ", columns) + " = ?";

        String query = "UPDATE " + nameTable + " SET " + setPart + " WHERE " + whereClause + " = ?";

        try (PreparedStatement statement = dbConnect.getConnection().prepareStatement(query)) {
            for (int i = 0; i < values.size(); i++) {
                statement.setObject(i + 1, values.get(i));
            }
            statement.setObject(values.size() + 1, whereValue);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
