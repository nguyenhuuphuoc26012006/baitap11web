package com.phuoc.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * Lop tien ich mo/dong ket noi Database (Data Access Layer).
 * Doc cau hinh tu file config_24162100.properties trong classpath.
 */
public class DBConnection_24162100 {

    private static final Properties PROPS = new Properties();

    static {
        try (InputStream is = DBConnection_24162100.class
                .getClassLoader()
                .getResourceAsStream("config_24162100.properties")) {
            if (is != null) {
                PROPS.load(is);
            }
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (Exception e) {
            throw new RuntimeException("Khong the nap cau hinh Database: " + e.getMessage(), e);
        }
    }

    private DBConnection_24162100() {
    }

    public static Connection getConnection() throws Exception {
        String url = PROPS.getProperty("db.url");
        String user = PROPS.getProperty("db.username");
        String pass = PROPS.getProperty("db.password");
        return DriverManager.getConnection(url, user, pass);
    }

    public static void close(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception ignored) {
        }
    }

    public static String getMailProp(String key) {
        return PROPS.getProperty(key);
    }
}
