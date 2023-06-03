package application;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connexion{
    private static Connection conn=null ;
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/stage","root","");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public static Connection getConnection() {
        return  conn;
    }
}