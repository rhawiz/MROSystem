package apps.mrosystem.datasource;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import apps.mrosystem.DatabaseConnection;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;


public class Datasource {

    private static Datasource datasource;
    private BoneCP connectionPool;

    public Datasource() throws IOException, SQLException, PropertyVetoException {
        try {
            // load the database driver (make sure this is in your classpath!)
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        try {
            // setup the connection pool using BoneCP Configuration
            BoneCPConfig config = new BoneCPConfig();
            
            config.setJdbcUrl("jdbc:mysql://localhost/mrosystem");
            config.setUsername("sqluser");
            config.setPassword("123");
            config.setMinConnectionsPerPartition(5);
            config.setMaxConnectionsPerPartition(10);
            config.setPartitionCount(1);
            // setup the connection pool
            connectionPool = new BoneCP(config);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

    }

    public static Datasource getInstance() throws IOException, SQLException, PropertyVetoException {
    	
    	if (datasource == null) {
            datasource = new Datasource();
            
            return datasource;
        } else {
            return datasource;
        }
    }

    public Connection getConnection() throws SQLException {
        return this.connectionPool.getConnection();
    }
    
    public void close() throws SQLException{
    	connectionPool.close();
    	connectionPool.getConnection().close();
    	connectionPool.shutdown();
    }

}