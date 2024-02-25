package util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

public class DbUtilTst {
    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection != null)
            return connection;
        else {
            try {
                Properties prop = new Properties();
                InputStream inputStream = DbUtilTst.class.getClassLoader().getResourceAsStream("db/hsqldb.properties");
                prop.load(inputStream);
                String driver = prop.getProperty("database.driver");
                String url = prop.getProperty("database.url");
                String user = prop.getProperty("database.user");
                String password = prop.getProperty("database.password");
                Class.forName(driver);
                connection = DriverManager.getConnection(url, user, password);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return connection;
        }
    }

    public static void dataBaseInitialization() throws SQLException, URISyntaxException, IOException {
        URL urlInit = DbUtilTst.class.getClassLoader().getResource("db/initDB_hsql.sql");
        URL urlPopulate = DbUtilTst.class.getClassLoader().getResource("db/populateDB.sql");

        List<String> fileInit = Files.readAllLines(Paths.get(urlInit.toURI()));
        String sqlInit = String.join("", fileInit);

        List<String> filePopulate = Files.readAllLines(Paths.get(urlPopulate.toURI()));
        String sqlPopulate = String.join("", filePopulate);

        Statement stmt = connection.createStatement();
        stmt.executeUpdate(sqlInit);
        stmt.execute(sqlPopulate);
    }
}