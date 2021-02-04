package site.lgong.design.proxy;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 代理模式：为其他对象提供一种代理以控制对这个对象的访问。
 */
public class ProxyPattern {

    public static void main(String[] args) throws SQLException {

        String jdbcUrl = "localhost:3306/test";

        String jdbcUsername = "root";

        String jdbcPassword = "root";

        DataSource lazyDataSource = new LazyDataSource(jdbcUrl, jdbcUsername, jdbcPassword);
        System.out.println("get lazy connection...");
        try (Connection conn1 = lazyDataSource.getConnection()) {
            // 并没有实际打开真正的Connection
        }
        System.out.println("get lazy connection...");
        try (Connection conn2 = lazyDataSource.getConnection()) {
            try (PreparedStatement ps = conn2.prepareStatement("SELECT * FROM students")) { // 打开了真正的Connection
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        System.out.println(rs.getString("name"));
                    }
                }
            }
        }
    }

}
