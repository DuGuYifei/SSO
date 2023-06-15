package eeapp.dao;

import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;
import javax.sql.DataSource;
import org.h2.jdbcx.JdbcDataSource;

/**
 * The DatabaseConnection class implements a data access object that
 * provides access to the database.
 */
public class DatabaseConnection {

    /**
     * The DB_URL is the database connection information.
     */
    private static final String DB_URL = "jdbc:h2:mem:testdb";

    /**
     * The DB_USER is the database user name.
     */
    private static final String DB_USER = "admin";

    /**
     * The DB_PASSWORD is the database password.
     */
    private static final String DB_PASSWORD = "admin";

    /**
     * The getConnection method returns a connection to the database.
     * @return a connection to the database
     * @throws SQLException if there is an error connecting to the database
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    /**
     * The getDataSource method returns a data source to the database.
     * @return a data source to the database
     */
    public static DataSource getDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(DB_URL);
        dataSource.setUser(DB_USER);
        dataSource.setPassword(DB_PASSWORD);
        return dataSource;
    }

    /**
     * The writeUUID method writes a UUID to a byte array.
     * @param bytes the byte array
     * @return the UUID
     */
    public static UUID readUUID(byte[] bytes) {
        long msb = 0;
        long lsb = 0;
        assert bytes.length == 16;
        for (int i=0; i<8; i++)
            msb = (msb << 8) | (bytes[i] & 0xff);
        for (int i=8; i<16; i++)
            lsb = (lsb << 8) | (bytes[i] & 0xff);
        return new UUID(msb, lsb);
    }

    /**
     * The writeUUID method writes a UUID to a byte array.
     * @param uuid the UUID
     * @return the byte array
     */
    public static byte[] writeUUID(UUID uuid) {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.putLong(uuid.getMostSignificantBits());
        buffer.putLong(uuid.getLeastSignificantBits());
        return buffer.array();
    }
}