package Model;
import java.sql.*;
public class DBConnect{    
    //Static instance of connection, only one will ever exist
    private static Connection connection = null;        
    //Returns single instance of connection
    public static Connection getConnection(){        
        //If instance has not been created yet, create it
        if(DBConnect.connection == null){
            initConnection();
        }
        return DBConnect.connection;
    }    
    //Gets JDBC connection instance
    private static void initConnection(){            
        try{        
            Class.forName("com.mysql.jdbc.Driver"); 
            System.out.print("成功加载驱动");
            String url = "jdbc:mysql://localhost:3306/class?useUnicode=true&characterEncoding=utf8";
            String user = "root";
            String pw = "123";
            DBConnect.connection =
                         DriverManager.getConnection(url, user, pw); 
            System.out.print("成功连接到数据库");
        }
        catch (ClassNotFoundException e){        
            System.out.println(e.getMessage());
            System.exit(0);
        }
        catch (SQLException e){            
            System.out.println(e.getMessage());
            System.exit(0);
        }
        catch (Exception e){        
        }        
    }
}