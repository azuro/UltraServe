package com.azuro.ultraserve.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.azuro.ultraserve.data.entity.NewsItem;
import com.azuro.ultraserve.data.entity.Account;

public class Database {
	Connection dbConnection;

	//Singleton Creation(For Efficient Database use)
	public static Database getInstance() {
		return DatabaseHolder.INSTANCE;
	}

	private static class DatabaseHolder {
		private static final Database INSTANCE = new Database();
	}
	
	protected Database() {//Here to  Defend Instantiation
		try {
			Class.forName("org.hsqldb.JDBCDriver");
			dbConnection = DriverManager.getConnection("jdbc:hsqldb:file:RotMG;shutdown=true","ultranoob","ultraserve");
		} catch (Exception e) {//Silently Let the Error Fall through
			System.err.println("Error connecting to Database! Restart your computer and try again");
		}
	}
	//----------------------------------------------
	
	//Create SQL Query for Querying Database
	public PreparedStatement CreateQuery(String sql) throws SQLException{
		return dbConnection.prepareStatement(sql);
	}
	
	@SuppressWarnings("finally")
	public List<NewsItem> GetNews(Account acc){
		List<NewsItem> ret = new ArrayList<NewsItem>();
		try {
			//----------------------------------------------
			//Get General(Global) News to Populate News Tab
			//----------------------------------------------
			PreparedStatement cmd = CreateQuery("SELECT icon, title, text, link, date FROM news ORDER BY date LIMIT 10;");
			cmd.closeOnCompletion();
			final ResultSet row = cmd.executeQuery();
			while(row.next()){
				ret.add(new NewsItem(){
					String Icon = row.getString("icon");
					String Title = row.getString("title");
					String TagLine = row.getString("text");
					String Link = row.getString("link");
					long Date = row.getLong("date");
				});
			}
			//Close ResultSet to save Resources
			if(row!=null)
				row.close();
			//----------------------------------------------
			//Get Account Specific News to Populate News Tab
			//----------------------------------------------
			if(acc!=null){
				cmd = CreateQuery("SELECT charId, characters.charType, level, death.totalFame, time FROM characters, death WHERE dead = TRUE AND characters.accId=? AND death.accId=? AND characters.charId=death.chrId;");
				cmd.setLong(1, acc.AccountId);
				cmd.setLong(2, acc.AccountId);
				final ResultSet accrow = cmd.executeQuery();
				while(accrow.next()){
					ret.add(new NewsItem(){
						String Icon = "fame";
						//TODO Add News. Finish XML Parsing First
						String Title = String.format("Your %s died at level %d", row.getInt("charType"),row.getInt("level"));
						String TagLine = row.getString("text");
						String Link = row.getString("link");
						long Date = row.getLong("date");
					});
				}
			}
			Collections.sort(ret, new Comparator<NewsItem>(){

				@Override
				public int compare(NewsItem firstItem, NewsItem secondItem) {
					return firstItem.Date < secondItem.Date ? -1
							: firstItem.Date > secondItem.Date ? 1
									:0;
				}
				
				
			});
			return ret.subList(0,10);
			
		//Error Handling	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			return ret.subList(0,10);
		}
	}
	
	static final String[] randNames = {
		 "Darq", "Deyst", "Drac", "Drol",
         "Eango", "Eashy", "Eati", "Eendi", "Ehoni",
         "Gharr", "Iatho", "Iawa", "Idrae", "Iri", "Issz", "Itani",
         "Laen", "Lauk", "Lorz",
         "Oalei", "Odaru", "Oeti", "Orothi", "Oshyu",
         "Queq", "Radph", "Rayr", "Ril", "Rilr", "Risrr",
         "Saylt", "Scheev", "Sek", "Serl", "Seus",
         "Tal", "Tiar", "Uoro", "Urake", "Utanu",
         "Vorck", "Vorv", "Yangu", "Yimi", "Zhiar"
	};
	
	public static Account CreateGuestAccount(String UUID){
		
	}
}
