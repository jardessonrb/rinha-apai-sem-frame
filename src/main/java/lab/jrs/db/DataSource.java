package lab.jrs.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {

    private DataSource() {}

    public static Connection getConnection() throws SQLException {
        HikariConfig config = new HikariConfig();
        HikariDataSource ds = null;
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/rinha-backend?createDatabaseIfNotExist=true");
        config.setUsername("postgres");
        config.setPassword("admin");
        config.setDriverClassName("org.postgresql.Driver");
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        config.setMaximumPoolSize(20);
        config.setConnectionTimeout(300000);
        config.setConnectionTimeout(120000);
        config.setLeakDetectionThreshold(300000);
        ds = new HikariDataSource( config );
        return ds.getConnection();
    }
}
