package fr.lightnew;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

@Getter
public class DBConnect {

    private Connection connection;
    private final String username;
    private final String password;
    private final String database;
    private final int port;

    /**
     * Simple utilisation
     * @param username
     * @param password
     * @param port
     * @param database
     * @apiNote
     * DBConnect dbConnect = new DBConnect(username, password, port, database);
     * dbConnect.getConnection();
     */
    public DBConnect(String username, String password, int port, String database) {
        this.username = username;
        this.password = password;
        this.port = port;
        this.database = database;
        createDataBase();
        connectSQL();
    }

    public DBConnect(String username, String password, String database) {
        this(username, password, 3306, database);
    }

    /**
     * Set new connection
     */
    private void connectSQL() {
        final String url = "jdbc:mysql://" + "localhost" + ":" + port + "/" + database + "?useUnicode=true&characterEncoding=UTF-8";
        try {
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(true);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void reConnect() {
        try {
            if (connection.isClosed())
                connectSQL();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Create default database
     */
    private void createDataBase() {
        final String url = "jdbc:mysql://" + "localhost" + ":" + port + "/"  + "?useUnicode=true&characterEncoding=UTF-8";
        try {
            connection = DriverManager.getConnection(url, username, password);
            String query = "CREATE DATABASE IF NOT EXISTS " + database;
            try {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            connection.setAutoCommit(true);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create fancy a new table
     * @param index The index is used to auto increment indicating each new entry
     * @param value The value is content of your table
     */
    public void createTable(String nameTable, boolean index, Map<String, TypeSQL> value) {
        reConnect();
        if (value == null || value.isEmpty())
            throw new RuntimeException("parameter 'value' is null or empty");
        StringBuilder builder = new StringBuilder("(");
        if (index)
            builder.append("id int NOT NULL AUTO_INCREMENT PRIMARY KEY,");
        value.forEach((string, typeSQL) -> builder.append(string)
                .append(" ")
                .append(typeSQL.getType())
                .append(","));
        if (builder.charAt(builder.length() - 1) == ',')
            builder.deleteCharAt(builder.length() - 1);

        builder.append(");");
        createTableCustomQuery(nameTable, builder.toString());
    }

    /**
     *
     * @param customValue The custom value is content of your table
     */
    public void createTable(String nameTable, String customValue) {
        reConnect();
        if (customValue == null || customValue.isEmpty())
            throw new RuntimeException("parameter 'value' is null or empty");
        createTableCustomQuery(nameTable, "(" + customValue + ")");
    }

    /**
     *
     * @param customQuery Create your custom query
     */
    public void createTableCustomQuery(String nameTable, String customQuery) {
        reConnect();
        if (connection == null)
            throw new RuntimeException("connection to your database is null");
        if (nameTable == null || nameTable.isEmpty())
            throw new RuntimeException("parameter 'nameTable' is null or empty");
        if (customQuery == null || customQuery.isEmpty())
            throw new RuntimeException("parameter 'value' is null or empty");
        nameTable = nameTable.replace(" ", "_");
        String query = "CREATE TABLE IF NOT EXISTS " + nameTable + " " + customQuery;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            System.out.println(statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}