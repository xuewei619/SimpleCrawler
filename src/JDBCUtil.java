import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCUtil {

	private final static String url = "xxx";

	private final static String username = "root";

	private final static String password = "619";

	// private Connection conn;

	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static boolean insertInfo(String sql, List<Object> params,Connection conn) throws SQLException {
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		int index = 1;
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		int result = pstmt.executeUpdate();
		boolean flag = result > 0 ? true : false;
		return flag;
	}	

}
