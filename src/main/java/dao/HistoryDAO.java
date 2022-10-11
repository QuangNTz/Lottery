package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import context.DBContext;
import model.History;

public class HistoryDAO {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;	
	
	// user_history.jsp
	public List<History> getCheckHistory(String usr_search, String user_mail, int activePage, int itemPerPage) throws SQLException {
		List<History> list = new ArrayList<>();
		String query = "SELECT H.history_id, H.history_date, H.user_mail, H.lottery_id, R.lottery_com, R.lottery_date, H.history_number, H.history_result\n"
				+ "FROM dbo.History H\n"
				+ "LEFT JOIN dbo.ResultSheet R ON H.lottery_id=R.lottery_id\n"
				+ "WHERE user_mail=? AND (H.history_result LIKE ? OR R.lottery_com LIKE ?)\n"
				+ "ORDER BY history_date DESC\n"
				+ "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
		

		try {
			conn = new DBContext().getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, user_mail);
			ps.setString(2, "%" + usr_search + "%");
			ps.setString(3, "%" + usr_search + "%");
			ps.setInt(4, (activePage - 1) * itemPerPage);
			ps.setInt(5, itemPerPage);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				list.add(new History(rs.getInt("history_id"),
										rs.getTimestamp("history_date"),
										rs.getString("user_mail"), 
										rs.getInt("lottery_id"),
										rs.getString("lottery_com"),
										rs.getDate("lottery_date"),
										rs.getString("history_number"),
										rs.getString("history_result")));
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
	
	public int countUserHistory(String usr_search, String user_mail) throws SQLException {
		String query = "SELECT COUNT(*)\n"
						+ "FROM dbo.History H\n"
						+ "LEFT JOIN dbo.ResultSheet R ON H.lottery_id=R.lottery_id\n"
						+ "WHERE user_mail=? AND (H.history_result LIKE ? OR R.lottery_com LIKE ?)\n";
		
		try {
			conn = new DBContext().getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, user_mail);
			ps.setString(2, "%" + usr_search + "%");
			ps.setString(3, "%" + usr_search + "%");
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
	
	public boolean addCheckHistory(String user_mail, int lottery_id, String history_number, String history_result) throws SQLException {
		String query = "INSERT INTO dbo.History\n" 
						+ "(user_mail,lottery_id,history_number,history_result)\n"
						+ "VALUES\n" 
						+ "(?,?,?,?)";
		
		try {
			conn = new DBContext().getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, user_mail);
			ps.setInt(2, lottery_id);
			ps.setString(3, history_number);
			ps.setString(4, history_result);
			
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
		String query = "DELETE FROM dbo.History\n" 
						+ "WHERE history_id=?";

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
		String query = "DELETE FROM dbo.History\r\n"
						+ "WHERE history_id IN ("+ idList +")";

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
