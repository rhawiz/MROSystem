package apps.mrosystem.database.datasource;

/**
 * Created by RAWAND on 29/11/2014.
 *
 * Data structure to perform database connection activities and store connection states.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Datasource {
    //Only supports MySql for time being.

    private String url;
    private Properties info;
    
    private Connection connect;

    public Datasource(String url, String username, String password){
        this.url = url;
        this.info = new Properties();
        this.info.setProperty("user", username);
        this.info.setProperty("password", password);
    }
  
    
    /**
     * Default connection
     * @throws SQLException 
     * @throws InstantiationException 
     * @throws IllegalAccessException 
     * @throws ClassNotFoundException 
     */
    public Datasource() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException{
  
    	this.url = "//co-project.lboro.ac.uk";
        this.info = new Properties();
        this.info.setProperty("user", "corh");
        this.info.setProperty("password", "592hrc45");
/*        this.url = "//localhost";
        this.info = new Properties();
        this.info.setProperty("user", "sqluser");
        this.info.setProperty("password", "raw12743");*/
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
        connect = DriverManager.getConnection("jdbc:mysql:"+url,info);
    }
    

    public Connection getConnection(){

        return connect;

    }
    

}
