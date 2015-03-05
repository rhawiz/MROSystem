package apps.mrosystem;

/**
 * Created by RAWAND on 29/11/2014.
 *
 * Database Query class handle database queries and store data retrieved from the database if applicable.
 */

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;

import apps.mrosystem.datasource.Datasource;


public class DBQuery {


    String sqlQuery;
    ResultSet resultSet;
    Statement statement;
    int columnCount;
    int rowCount;
    private ArrayList<ArrayList<String>> resultsArray;
    String queryType;
    java.sql.Connection dbConnection;

    public DBQuery(String query){
        this.sqlQuery = query;
        queryType = query.trim().split(" ")[0];
    }


    private void close() {
		if(dbConnection != null){
			try {
				dbConnection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}


	/**
     * Run query on database connection.
     *
     * @return True if successfully queried database and false if it fails for any reason.
     */
    public boolean run(){
    	try {
			dbConnection = Datasource.getInstance().getConnection();
		} catch (SQLException | IOException | PropertyVetoException | NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			close();
			return false;
		}
        if(queryType.equals("INSERT") ||queryType.equals("UPDATE")) {
            try {
            	
				statement = dbConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                statement.executeUpdate(sqlQuery);
                dbConnection.close();
                columnCount = 0;
                rowCount = 0;
                
            } catch (SQLException | NullPointerException e) {
                e.printStackTrace();
                close();
                return false;
            } 
        }
        else{
            try {
                statement = dbConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                resultSet = statement.executeQuery(sqlQuery);
                dbConnection.close();
                columnCount = resultSet.getMetaData().getColumnCount();
                resultSet.last();
                rowCount = resultSet.getRow();
            } catch (SQLException | NullPointerException e) {
				System.err.println(e.getStackTrace());
                e.printStackTrace();
                close();
                return false;
            } 
        }

        close();
        return true;

    }


    /**
     * Get the number of rows the query returned from the database
     *
     * @return Integer number of rows
     */
    public int getRowCount(){
        return rowCount;
    }

    /**
     * Get the number of columns returned from the database.
     *
     * @return Integer number of columns
     */
    public int getColumnCount(){
        return columnCount;
    }


    /**
     * Return the sql query that was run
     *
     * @return String sql Query
     */
    public String getSqlQuery() {
        return sqlQuery;
    }


    /**
     * Return the query results as an ArrayList
     * @return
     */
    public ArrayList<ArrayList<String>> getArray(){
    	try {
			constructArray();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return resultsArray;
    }


    /**
     * Construct the result arraylist
     * @throws SQLException
     */
    private void constructArray() throws SQLException{
    	if(resultsArray != null)
    		return;
    		
        resultsArray = new ArrayList<ArrayList<String>>();
        if(rowCount > 0) {
        //Only construct array if the query is expecting results. I.E. SELECT only.
            resultSet.first();
            for (int i = 0; i < rowCount; i++) {
                ArrayList temp_array = new ArrayList();
                for (int j = 1; j <= columnCount; j++) {
                    temp_array.add(resultSet.getString(resultSet.getMetaData().getColumnName(j)));
                }
                resultSet.next();
                resultsArray.add(temp_array);
            }
        }
    }

    /**
     * Get results by row and column
     *
     * @param row Row
     * @param col Column
     * @return String result at row, col
     */
    public String get(int row, int col){
    	try {
			constructArray();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if(row < rowCount && col < columnCount){
            return (String) resultsArray.get(row).get(col);
        }
        return null;
    }


    /**
     * Overload function, get indicated row as ArrayList
     * @param row Row
     * @return ArrayList row
     */
    public ArrayList<String> get(int row){
    	try {
			constructArray();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return resultsArray.get(row);
    }
    
    public ResultSet getResultSet(){
    	return resultSet;
    }




}
