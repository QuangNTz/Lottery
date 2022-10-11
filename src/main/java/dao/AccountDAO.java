package dao;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import context.DBContext;
import context.EncryptorMd5;
import model.Account;

public class AccountDAO {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	// admin_accountdata.jsp
	public List<Account> getList(String usr_search, int activePage, int itemPerPage) throws SQLException {
		List<Account> list = new ArrayList<>();
		String query = "SELECT * FROM dbo.Account\n"
						+ "WHERE user_mail LIKE ? OR phone_number LIKE ?\n"
						+ "ORDER BY account_role\n" 
						+ "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
		
		try {
			conn = new DBContext().getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, "%" + usr_search + "%");
			ps.setString(2, "%" + usr_search + "%");
			ps.setInt(3, (activePage - 1) * itemPerPage);
			ps.setInt(4, itemPerPage);
			
			rs = ps.executeQuery();

			while (rs.next()) {				
				list.add(new Account(rs.getString("user_mail"), 
										rs.getString("password"), 
										rs.getInt("account_role"), 
										rs.getString("user_name"),
										rs.getString("phone_number"),
										rs.getInt("user_status")));
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
	
	// lấy data 1 Account trong DB	
	public Account getAcc(String id) throws SQLException {
		String query = "SELECT *\n" 
						+ "FROM dbo.Account\n" 
						+ "WHERE user_mail=?";

		try {
			conn = new DBContext().getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, id);
			
			rs = ps.executeQuery();

			while (rs.next()) {
				return new Account(rs.getString("user_mail"), 
									rs.getString("password"), 
									rs.getInt("account_role"), 
									rs.getString("user_name"),
									rs.getString("phone_number"),
									rs.getInt("user_status"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		conn.close();

		return null;
	}	
	
	// lấy data 1 Account trong DB	
	public Account login(String user_mail, String password) throws SQLException, NoSuchAlgorithmException {
		String query = "SELECT *\n"
						+ "FROM dbo.Account\n"
						+ "WHERE user_mail=? AND password=?";		
		String hashedPass = new EncryptorMd5().encryptString(password);
		
	try {
		conn = new DBContext().getConnection();
		ps = conn.prepareStatement(query);
		ps.setString(1, user_mail);
		ps.setString(2, hashedPass);
		
		rs = ps.executeQuery();
	
		while (rs.next()) {
			return new Account(rs.getString("user_mail"), 
								rs.getString("password"), 
								rs.getInt("account_role"), 
								rs.getString("user_name"),
								rs.getString("phone_number"),
								rs.getInt("user_status"));
		}
	
	} catch (Exception e) {
		e.printStackTrace();
	}
	conn.close();
	
	return null;		
	}

	public int countInDatabase(String urs_search) throws SQLException{
		String query = "SELECT COUNT(*)\n"
						+ "FROM dbo.Account\n"
						+ "WHERE user_mail LIKE ? OR phone_number LIKE ?";
		
		try {
			conn = new DBContext().getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, "%" + urs_search + "%");
			ps.setString(2, "%" + urs_search + "%");
			
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
	
	// check sự tồn tại của Account trong DB
	public Account checkAccountExist(String user) throws SQLException {
		String query = "SELECT *\n" 
						+ "FROM dbo.Account\n" 
						+ "WHERE user_mail=?";

		try {
			conn = new DBContext().getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, user);
			
			rs = ps.executeQuery();

			while (rs.next()) {
				return new Account(rs.getString("user_mail"), 
									rs.getString("password"), 
									rs.getInt("account_role"),
									rs.getString("user_name"),
									rs.getString("phone_number"),
									rs.getInt("user_status"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		conn.close();

		return null;
	}
	
	public boolean add(String user_mail, String password, int account_role, String user_name, String phone_number, int user_status) throws SQLException, NoSuchAlgorithmException  {
		String query = "INSERT INTO dbo.Account\n" 
						+ "(user_mail,password,account_role,user_name,phone_number,user_status)\n"
						+ "VALUES\n" 
						+ "(?,?,?,?,?,?)";		
		String hashedPass = new EncryptorMd5().encryptString(password);

		try {
			conn = new DBContext().getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, user_mail);
			ps.setString(2, hashedPass);
			ps.setInt(3, account_role);
			ps.setString(4, user_name);
			ps.setString(5, phone_number);
			ps.setInt(6, user_status);
			
			ps.executeUpdate();
			
			return true;
		} catch (Exception e) {
			System.out.println("Có lỗi xảy ra trong quá trình làm việc với SQL Server.\n"
        			+ "Chi tiết: " + e.getMessage());
		}
		conn.close();
		
		return false;	
	}
	
	public boolean update(String user_mail, int account_role, String user_name, String phone_number, int user_status) throws SQLException {
		String query = "UPDATE dbo.Account\n"
						+ "SET account_role=?, user_name=?, phone_number=?, user_status=?\n"
						+ "WHERE user_mail=?";

		try {
			conn = new DBContext().getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(5, user_mail);
			ps.setInt(1, account_role);
			ps.setString(2, user_name);
			ps.setString(3, phone_number);
			ps.setInt(4, user_status);
			
			ps.executeUpdate();
			
			return true;
		} catch (Exception e) {
			System.out.println("Có lỗi xảy ra trong quá trình làm việc với SQL Server.\n"
        			+ "Chi tiết: " + e.getMessage());
		}
		conn.close();
		
		return false;
	}
	
	public boolean newUserPassword(String id, String newPass) throws SQLException, NoSuchAlgorithmException {
		String query = "UPDATE dbo.Account\n"
				+ "SET password=?\n"
				+ "WHERE user_mail=?";
		String hashedPass = new EncryptorMd5().encryptString(newPass);

		try {
			conn = new DBContext().getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, hashedPass);
			ps.setString(2, id);
			
			ps.executeUpdate();
			
			return true;
		} catch (Exception e) {
			System.out.println("Có lỗi xảy ra trong quá trình làm việc với SQL Server.\n"
					+ "Chi tiết: " + e.getMessage());
		}
		conn.close();
		
		return false;
	}
		
	public boolean delete(String id) throws SQLException{
		// update account status từ 1->0
		String query = "UPDATE dbo.Account\n" 
						+ "SET user_status='0'\n" 
						+ "WHERE user_mail=?";		

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
		// update account status từ 1->0
		String query = "UPDATE dbo.Account\n" 
						+ "SET user_status='0'\n" 
						+ "WHERE user_mail IN (" + idList + ")";		
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
