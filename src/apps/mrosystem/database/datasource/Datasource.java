package apps.mrosystem.database.datasource;

/**
 * Created by RAWAND on 29/11/2014.
 *
 * Data structure to perform database connection activities and store connection states.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Datasource {
    //Only supports MySql for time being.

    private String url;
    private String username;
    private String password;
    private Connection connect;

    public Datasource(String url, String username, String password){
        this.url = url;
        this.username = username;
        this.password = password;
    }
  
    
    /**
     * Default connection
     * @throws SQLException 
     * @throws InstantiationException 
     * @throws IllegalAccessException 
     * @throws ClassNotFoundException 
     */
    public Datasource() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException{
  
        this.url = "//localhost/mrosystem";
        this.username = "sqluser";
        this.password = "123";
        connect();
    }

    public void close(){
        try {
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void connect() throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {

        Class.forName("com.mysql.jdbc.Driver");
        connect = DriverManager.getConnection("jdbc:mysql:"+url,username,password);
    }
    

    public Connection getConnection(){

        return connect;

    }
    

}
