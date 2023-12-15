package connection;

import java.sql.*;

public class DBConnection {
    public Connection getConnection() {
        String URL = "jdbc:mysql://localhost:3306/data_mart?useUnicode=yes&characterEncoding=UTF-8";
        String USERNAME = "root";
        String PASSWORD = "";
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("connect successfully!");
        } catch (Exception ex) {
            System.out.println("connect failure!");
            ex.printStackTrace();
        }
        return conn;
    }

    public void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Status isClosed: " + connection.isClosed());
                System.out.println("Connection closed successfully!");
                System.out.println("------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String query = "select * from ketquaxoso_mart";
        try {
            Connection connection = new DBConnection().getConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                System.out.println(rs.getString(2));
            }
            rs.close();
            ps.close();
            new DBConnection().closeConnection(connection);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
