package dev.imfound.killstats.mysql;

import com.zaxxer.hikari.HikariConfig;
import dev.imfound.killstats.enums.Config;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.util.concurrent.TimeUnit;

public class HikariDataSource {

    private static final HikariConfig config = new HikariConfig();
    private static com.zaxxer.hikari.HikariDataSource ds;

    private static final int MAXIMUM_POOL_SIZE = (Runtime.getRuntime().availableProcessors() * 2) + 1;
    private static final int MINIMUM_IDLE = Math.min(MAXIMUM_POOL_SIZE, 10);

    private static final long CONNECTION_TIMEOUT = TimeUnit.SECONDS.toMillis(10);

    static {
        config.setJdbcUrl("jdbc:mysql://" + Config.HOST.getString() + ":" + Config.PORT.getString() + "/" + Config.DATABASE.getString() + "?autoReconnect=true&failOverReadOnly=false&maxReconnects=10&useSSL=false");
        config.setUsername(Config.USERNAME.getString());
        config.setPassword(Config.PASSWORD.getString());
        config.setPoolName("KillStats-pool");
        config.setMaximumPoolSize(MAXIMUM_POOL_SIZE);
        config.setMinimumIdle(MINIMUM_IDLE);
        config.setMaxLifetime(3000);
        config.setConnectionTimeout(CONNECTION_TIMEOUT);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new com.zaxxer.hikari.HikariDataSource(config);
    }

    @SneakyThrows
    public Connection getConnection() {
        return ds.getConnection();
    }

    @SneakyThrows
    public void close() {
        if(!ds.isClosed()) {
            ds.close();
        }
    }

}
