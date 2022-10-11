package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import context.DBContext;
import model.ResultSheet;

public class ResultsheetDAO {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	// admin_resultsheetdata.jsp
	public List<ResultSheet> getList(String rs_search, int activePage, int itemPerPage) throws SQLException {
		List<ResultSheet> list = new ArrayList<>();
		String query = "SELECT *\n" 
						+ "FROM dbo.ResultSheet\n"
						+ "WHERE lottery_com LIKE ?\n"
						+ "ORDER BY lottery_date DESC\n"
						+ "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

		try {
			conn = new DBContext().getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, "%" + rs_search + "%");
			ps.setInt(2, (activePage - 1) * itemPerPage);
			ps.setInt(3, itemPerPage);
			
			rs = ps.executeQuery();

			while (rs.next()) {
				list.add(new ResultSheet(rs.getInt("lottery_id"), 
											rs.getString("lottery_com"),
											rs.getDate("lottery_date"), 
											rs.getString("lottery_8"), 
											rs.getString("lottery_7"),
											rs.getString("lottery_6"), 
											rs.getString("lottery_5"), 
											rs.getString("lottery_4"),
											rs.getString("lottery_3"), 
											rs.getString("lottery_2"), 
											rs.getString("lottery_1"),
											rs.getString("lottery_sp")));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		conn.close();

		if (list.size() > 0) {
			
			return list;
		} else {
			
			return null;
		}
	}
	
	// lấy data lotteryCOM trong DB
	public List<String> getLotteryCom() throws SQLException {
		List<String> list = new ArrayList<>();
		String query = "SELECT DISTINCT lottery_com\n"
						+ "FROM dbo.LotteryCOM";
		try {
			conn = new DBContext().getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				list.add(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		conn.close();
		
		if (list.size() > 0) {

			return list;
		} else {

			return null;
		}		
	}
	
	// lấy data 1 RS trong DB
	public ResultSheet getRS(String id) throws SQLException {
		String query = "SELECT *\n" 
						+ "FROM dbo.ResultSheet\n" 
						+ "WHERE lottery_id=?";

		try {
			conn = new DBContext().getConnection();
			ps = conn.prepareStatement(query);
			ps.setInt(1, Integer.parseInt(id));
			
			rs = ps.executeQuery();

			while (rs.next()) {
				return new ResultSheet(rs.getInt("lottery_id"), 
										rs.getString("lottery_com"), 
										rs.getDate("lottery_date"),
										rs.getString("lottery_8"),
										rs.getString("lottery_7"), 
										rs.getString("lottery_6"),
										rs.getString("lottery_5"), 
										rs.getString("lottery_4"), 
										rs.getString("lottery_3"),
										rs.getString("lottery_2"), 
										rs.getString("lottery_1"), 
										rs.getString("lottery_sp"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		conn.close();

		return null;
	}

	public ResultSheet getLastestRS(String lottery_com) throws SQLException {
		String query = "SELECT TOP(1) *\n" 
						+ "FROM dbo.ResultSheet\n" 
						+ "WHERE lottery_com LIKE ?\n"
						+ "ORDER BY lottery_date DESC";

		try {
			conn = new DBContext().getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, "%" + lottery_com + "%");
			
			rs = ps.executeQuery();

			while (rs.next()) {
				return new ResultSheet(rs.getInt("lottery_id"), 
										rs.getString("lottery_com"), 
										rs.getDate("lottery_date"),
										rs.getString("lottery_8"), 
										rs.getString("lottery_7"), 
										rs.getString("lottery_6"),
										rs.getString("lottery_5"), 
										rs.getString("lottery_4"), 
										rs.getString("lottery_3"),
										rs.getString("lottery_2"), 
										rs.getString("lottery_1"), 
										rs.getString("lottery_sp"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		conn.close();

		return null;
	}
	
	public int countInDatabase(String rs_search) throws SQLException{
		String query = "SELECT COUNT(*)\n"
						+ "FROM dbo.ResultSheet\n"
						+ "WHERE lottery_com LIKE ?";
		
		try {
			conn = new DBContext().getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, "%" + rs_search + "%");
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		conn.close();
		
		return 0;		
	}
	
	// check sự tồn tại của RS trong DB
	public ResultSheet checkResultsheetExist(String lottery_com, Date lottery_date) throws SQLException {
		String query = "SELECT *\n" 
						+ "FROM dbo.ResultSheet\n" 
						+ "WHERE lottery_com = ? AND lottery_date = ?";		
		
		try {
			java.sql.Date sqlDate = new java.sql.Date(lottery_date.getTime());

			conn = new DBContext().getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, lottery_com);
			ps.setDate(2, sqlDate);
			
			rs = ps.executeQuery();

			while (rs.next()) {
				return new ResultSheet(rs.getInt("lottery_id"), 
										rs.getString("lottery_com"), 
										rs.getDate("lottery_date"),
										rs.getString("lottery_8"), 
										rs.getString("lottery_7"), 
										rs.getString("lottery_6"),
										rs.getString("lottery_5"), 
										rs.getString("lottery_4"), 
										rs.getString("lottery_3"),
										rs.getString("lottery_2"), 
										rs.getString("lottery_1"), 
										rs.getString("lottery_sp"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		conn.close();

		return null;
	}
	
	// check sự tồn tại của RS trong DB
		public String checkCtxsExist(String ctxs) throws SQLException {
			String query = "SELECT *\n" 
							+ "FROM dbo.LotteryCOM\n" 
							+ "WHERE lottery_com = ? ";		
			
			try {
				conn = new DBContext().getConnection();
				ps = conn.prepareStatement(query);
				ps.setString(1, ctxs);
				
				rs = ps.executeQuery();

				while (rs.next()) {
					return rs.getString("lottery_com");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			conn.close();

			return null;
		}
	
	public boolean addCtxs(String ctxs) throws SQLException {
		String query = "INSERT INTO dbo.LotteryCOM(lottery_com)\n"
						+ "VALUES (?)";
		
		try {
			conn = new DBContext().getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, ctxs);
			
			ps.executeUpdate();
			
			return true;
		} catch (Exception e) {
			System.out.println("Có lỗi xảy ra trong quá trình làm việc với SQL Server.\n"
        			+ "Chi tiết: " + e.getMessage());
		}
		conn.close();
		
		return false;
	}
	
	public boolean add(String lottery_com, Date lottery_date, String giai8, String giai7, String giai6, String giai5,
			String giai4, String giai3, String giai2, String giai1, String gdb) throws SQLException {
		String query = "INSERT INTO dbo.ResultSheet\n"
						+ "(lottery_com, lottery_date, lottery_8, lottery_7, lottery_6, lottery_5, lottery_4, lottery_3, lottery_2, lottery_1, lottery_sp)\n"
						+ "VALUES\n" 
						+ "(?,?,?,?,?,?,?,?,?,?,?)";
		
		try {
			conn = new DBContext().getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, lottery_com);
			//System.out.println("lottery_date: " + lottery_date);
			java.sql.Date sqlDate = new java.sql.Date(lottery_date.getTime());
			//System.out.println("sqlDate: " + sqlDate);
			ps.setDate(2, sqlDate);
			ps.setString(3, giai8);
			ps.setString(4, giai7);
			ps.setString(5, giai6);
			ps.setString(6, giai5);
			ps.setString(7, giai4);
			ps.setString(8, giai3);
			ps.setString(9, giai2);
			ps.setString(10, giai1);
			ps.setString(11, gdb);
			
			ps.executeUpdate();
			
			return true;
		} catch (Exception e) {
			System.out.println("Có lỗi xảy ra trong quá trình làm việc với SQL Server.\n"
        			+ "Chi tiết: " + e.getMessage());
		}
		conn.close();
		
		return false;
	}
	
	public boolean update(String lottery_com, Date lottery_date, String giai8, String giai7, String giai6, String giai5,
			String giai4, String giai3, String giai2, String giai1, String gdb) throws SQLException {
		String query = "UPDATE dbo.ResultSheet\r\n"
						+ "SET lottery_8=?, lottery_7=?, lottery_6=?, lottery_5=?, lottery_4=?, lottery_3=?, lottery_2=?, lottery_1=?, lottery_sp=?\n"
						+ "WHERE lottery_com=? AND lottery_date=?";
		
		try {
			conn = new DBContext().getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(10, lottery_com);
			//System.out.println("lottery_date: " + lottery_date);
			java.sql.Date sqlDate = new java.sql.Date(lottery_date.getTime());
			//System.out.println("sqlDate: " + sqlDate);
			ps.setDate(11, sqlDate);			
			ps.setString(1, giai8);
			ps.setString(2, giai7);
			ps.setString(3, giai6);
			ps.setString(4, giai5);
			ps.setString(5, giai4);
			ps.setString(6, giai3);
			ps.setString(7, giai2);
			ps.setString(8, giai1);
			ps.setString(9, gdb);
			
			ps.executeUpdate();
			
			return true;
		} catch (Exception e) {
			System.out.println("Có lỗi xảy ra trong quá trình làm việc với SQL Server.\n"
        			+ "Chi tiết: " + e.getMessage());
		}
		conn.close();
		
		return false;
	}
	
	public boolean delete(String id) throws SQLException {
		String query = "DELETE FROM dbo.ResultSheet\n" 
						+ "WHERE lottery_id=?";

		try {
			conn = new DBContext().getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, id);
			
			ps.executeUpdate();
			
			return true;
		} catch (Exception e) {
			System.out.println("Có lỗi xảy ra trong quá trình làm việc với SQL Server.\n"
        			+ "Chi tiết: " + e.getMessage());
		}
		conn.close();
		
		return false;
	}
	
	public boolean deleteList(StringBuilder idList) throws SQLException {
		String query = "DELETE FROM dbo.ResultSheet\r\n"
						+ "WHERE lottery_id IN ("+ idList +")";

		try {
			conn = new DBContext().getConnection();
			conn.setAutoCommit(false);// dữ liệu sẽ chỉ update vào database khi gọi lệnh commit()
			
			ps = conn.prepareStatement(query);			
			ps.executeUpdate();
			
			conn.commit();
			conn.setAutoCommit(true);
			return true;
			
		} catch (Exception e) {
			System.out.println("Có lỗi xảy ra trong quá trình làm việc với SQL Server.\n"
        			+ "Chi tiết: " + e.getMessage());
			
			conn.rollback();
		}
		
		conn.close();
		
		return false;
	}
	
}
