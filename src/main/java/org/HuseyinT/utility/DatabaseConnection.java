package org.YoutubeApp.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Veritabanı bağlantısını yöneten Singleton sınıfıdır.
 * Bu sınıf, uygulama genelinde tek bir veritabanı bağlantısı sağlar ve bağlantıyı yönetir.
 */
public class DatabaseConnection {
	
	// Singleton nesnesi için statik referans
	private static DatabaseConnection instance;
	
	// Veritabanı bağlantı nesnesi
	private Connection connection;
	
	// Veritanı bağlantı bilgileri
	public static final String DB_HOSTNAME = "localhost";
	public static final String DB_NAME = "YoutubeApplicationDB";
	public static final String DB_PORT = "5432";
	public static final String DB_URL = "jdbc:postgresql://"+DB_HOSTNAME+":"+DB_PORT+"/"+DB_NAME;
	public static final String DB_USERNAME = "postgres";
	public static final String DB_PASSWORD ="root";
	
	/**
	 * Private constructor ile dışarıdan nesne oluşturulması engellenir.
	 * Bu constructor, veritabanına bağlantı kurar.
	 */
	private DatabaseConnection() {
		try {
			// Veritabanı bağlantısını oluşturur.
			this.connection = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
		}
		// Bağlantı oluşturulurken bir hata oluşursa, bu hata dışarı fırlatılır.
		catch (SQLException e) {
			throw new RuntimeException("Veritabanı bağlantısı oluşturulamadı",e);
		}
	}
	
	/**
	 * Singleton instance'ını döndüren metod.
	 * Eğer instance daha önce oluşturulmamışsa, yeni bir instance oluşturur.
	 * @return Singleton DatabaseConnection instance.
	 */
	
	public static synchronized DatabaseConnection getInstance() {
		if (instance == null){
			instance = new DatabaseConnection();
		}
		return instance;
	}
	
	/**
	 * Veritabanı bağlantısını döndürür.
	 * Eğer bağlantı mevcutsa, bu metod bağlantıyı sağlar.
	 * @return Veritabanı bağlantısı.
	 */
	public Connection getConnection(){
		try {
			if (connection.isClosed()){
				connection = ConnectionProvider.getInstance().getConnection();
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
}