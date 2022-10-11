package controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import context.AppConfig;
import context.RandomPassword;
import dao.AccountDAO;
import mail.SendEmailSSL;
import model.Account;

@Controller
public class AccountController  {
	// số lượng item/page
	private int itemPerPage = AppConfig.accPage;
		
// Xử lý của ADMIN ----------------------------------------------------------------------------------------------------------

	// load Accountdata from DB(accountdata.jsp)(DB)
	@RequestMapping(value = {"/loadaccountdata"})
	public String loadAccPage(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException{
		// Setting Unicode cho request (để nhận đúng tiếng Việt khi get parameter), response
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		
		HttpSession session = request.getSession(true);		
		// account_role = 1,2 mới dc load trang admin
		if (!AuthController.checkUser(session, response)) {
			
			return null;
		}
		
		AccountDAO accountDAO = new AccountDAO();

		// get parameter từ pages-nav
		String page = request.getParameter("page");
		
		String mess = (String) request.getAttribute("mess");	

		// set khởi gán cho active page của Account
		int activePage = (page == null) ? 1 : Integer.parseInt(page);
		// Khi bắt đầu search page = null
		// page có giá trị khi nhấn link phân trang page-nav

		// đếm sl RS có trong DB
		int countAcc = accountDAO.countInDatabase("");
		// trang kết thúc của list
		int endPage = (int) Math.ceil((float) countAcc / itemPerPage);
		// put vào HashMap
		HashMap<String, Integer> accountMap = new HashMap<>();
		accountMap.put("activePage", activePage);
		accountMap.put("endPage", endPage);

		List<Account> accountList = accountDAO.getList("", activePage, itemPerPage);
		
		request.setAttribute("mess", mess);
		session.setAttribute("accList", accountList);
		session.setAttribute("accMap", accountMap);

		return "admin/accountdata";
	}
	
	// search Accountdata from DB(search_accountdata.jsp)(DB)
	@RequestMapping(value = "/search_accountdata")
	public String loadAccPage_Search(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException{
		// Setting Unicode cho request (để nhận đúng tiếng Việt khi get parameter), response 
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		
		HttpSession session = request.getSession(true);		
		// account_role = 1,2 mới dc load trang admin
		if (!AuthController.checkUser(session, response)) {
			
			return null;
		}
		
		AccountDAO accountDAO = new AccountDAO();
		
		// get parameter từ pages-nav
		String page = request.getParameter("page");
		String usr_search = request.getParameter("usr_search");

		// set khởi gán cho active page của Account
		int activePage = (page == null)? 1 : Integer.parseInt(page);
		// Khi bắt đầu search page = null
		// page có giá trị khi nhấn link phân trang page-nav
		
		// đếm sl RS có trong DB
		int countAcc = accountDAO.countInDatabase(usr_search);
		// trang kết thúc củ list
		int endPage = (int) Math.ceil((float) countAcc / itemPerPage);
		
		List<Account> acc_searchList = accountDAO.getList(usr_search, activePage, itemPerPage);
		
		if (acc_searchList == null) {
			request.setAttribute("mess", "Không tìm thấy kết quả phù hợp với yêu cầu của bạn");
			request.setAttribute("usr_search", usr_search);
			
			return "admin/search_accountdata";
		} else {
			// set accountList vào acc_searchList,
			request.setAttribute("acc_searchList", acc_searchList);
			request.setAttribute("activePage", activePage);
			request.setAttribute("endPage", endPage);
			request.setAttribute("usr_search", usr_search);
			
			return "admin/search_accountdata";
		}		
	}
	
	// load trang form add newAccount
	@RequestMapping(value = "/add_account", method = RequestMethod.GET)
	public String loadAddAccFormPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession(true);		
		// account_role = 1,2 mới dc load trang admin
		if (!AuthController.checkUser(session, response)) {
			
			return null;
		}

		return "admin/add_account";
	}
	
	// save newAccount_form vào database(DB)
	@RequestMapping(value = "/add_account", method = RequestMethod.POST)
	public String addAccData(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException, NoSuchAlgorithmException {
		// Setting Unicode cho request (để nhận đúng tiếng Việt khi get parameter), response 
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		
		HttpSession session = request.getSession(true);
		String mess;
		@SuppressWarnings("rawtypes")
		HashMap hashMap = (HashMap) session.getAttribute("accMap");
		int activePage = (int) hashMap.get("activePage");

		// collect data from a add user form
		String user_mail = request.getParameter("user_mail");
		String password = request.getParameter("password");
		int account_role = Integer.parseInt(request.getParameter("account_role"));
		String user_name = request.getParameter("user_name");
		String phone_number = request.getParameter("phone_number");
		int user_status = Integer.parseInt(request.getParameter("user_status"));
		
		// Không dc tạo admin1
		if (account_role == 1) {
			request.setAttribute("mess", "Không được tạo admin1");
			// set value tạm trên form
			Account account = new Account(user_mail, password, account_role, user_name, phone_number, user_status);
			setRequestAttributeAccForm(request, account);

			// quay lại form add Account
			return "admin/add_account";
		}

		// kiểm tra syntax input data: nếu ko thỏa
		if (!user_mail.matches(AppConfig.regexMail) || !password.matches(AppConfig.regexPwd) || !phone_number.matches(AppConfig.regexPhone)) {

			request.setAttribute("mess", "Sai cấu trúc email, password hoặc phone number");
			// set value tạm trên form
			Account account = new Account(user_mail, password, account_role, user_name, phone_number, user_status);
			setRequestAttributeAccForm(request, account);

			// quay lại form add Account
			return "admin/add_account";
		}

		// check sự tồn tại của Account trong DB
		Account acc = new AccountDAO().checkAccountExist(user_mail);
		if (acc == null) {
			Boolean addAccData_status = new AccountDAO().add(user_mail, password, account_role, user_name, phone_number, user_status);
						
			if (addAccData_status) {
				mess = "Thêm mới User thành công";				
			} else {
				mess = "Thêm mới User thất bại";
			}
			
			// quay về trang load Account có trong DB
			request.setAttribute("mess", mess);		
			RequestDispatcher rd = request.getRequestDispatcher("/loadaccountdata" 
																	+ "?page=" + activePage);
			rd.forward(request, response);
			
		} else {
			request.setAttribute("mess", "Account already exist");
			// set value tạm hiện trên form
			Account account = new Account(user_mail, password, account_role, user_name, phone_number, user_status);
			setRequestAttributeAccForm(request, account);

			// quay lại form add newAccount
			return "admin/add_account";
		}
		
		return null;
	}
	
	// load trang form update_account.jsp(DB)
	@RequestMapping(value = "/update_account", method = RequestMethod.GET)
	public String updateAccFormPage(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		// Setting Unicode cho request (để nhận đúng tiếng Việt khi get parameter), response
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");

		HttpSession session = request.getSession(true);		
		// account_role = 1,2 mới dc load trang admin
		if (!AuthController.checkUser(session, response)) {
			
			return null;
		}
		
		String user_mail = request.getParameter("id");
		Account account = new AccountDAO().getAcc(user_mail);				
		
		setRequestAttributeAccForm(request, account);

		session.setAttribute("acctemp", account);
		// load trang update_account.jsp
		return "admin/update_account";
	}

	// save updateAccount_form vào database(DB)
	@RequestMapping(value = "/update_account", method = RequestMethod.POST)
	public String updateAccData(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException{
		// Setting Unicode cho request (để nhận đúng tiếng Việt khi get parameter), response
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		
		HttpSession session = request.getSession(true);
		String mess;
		@SuppressWarnings("rawtypes")
		HashMap hashMap = (HashMap) session.getAttribute("accMap");		
		int activePage = (int) hashMap.get("activePage");
		Account acctemp = (Account) session.getAttribute("acctemp");

		// collect data from a add user form
		String user_mail = request.getParameter("user_mail");
		int account_role = Integer.parseInt(request.getParameter("account_role"));
		String user_name = request.getParameter("user_name");
		String phone_number = request.getParameter("phone_number");
		int user_status = Integer.parseInt(request.getParameter("user_status"));
		
		// Không cho thay đổi user_mail
		if (!user_mail.equals(acctemp.getUser_mail())) {
			mess = "Không được thay đổi user_mail của tài khoản";
			
			// quay về trang load Account có trong DB
			request.setAttribute("mess", mess);	
			RequestDispatcher rd = request.getRequestDispatcher("/loadaccountdata" 
																	+ "?page=" + activePage);
			rd.forward(request, response);
			return null;
		}
		// Không cho thay đổi user thành admin1
		if (acctemp.getAccount_role() == 0 && account_role == 1) {
			mess = "Không được thay đổi user thành admin1";			
			// quay về trang load Account có trong DB
			request.setAttribute("mess", mess);		
			RequestDispatcher rd = request.getRequestDispatcher("/loadaccountdata" 
																	+ "?page=" + activePage);
			rd.forward(request, response);
			return null;
		// Không cho thay đổi admin2 thành admin1 hay user
		} else if (acctemp.getAccount_role() == 2 && account_role == 1) {
			mess = "Không được thay đổi admin2 thành admin1";			
			// quay về trang load Account có trong DB
			request.setAttribute("mess", mess);		
			RequestDispatcher rd = request.getRequestDispatcher("/loadaccountdata" 
																	+ "?page=" + activePage);
			rd.forward(request, response);
			return null;
		} else if (acctemp.getAccount_role() == 2 && account_role == 0) {
			mess = "Không được thay đổi admin2 thành user";			
			// quay về trang load Account có trong DB
			request.setAttribute("mess", mess);		
			RequestDispatcher rd = request.getRequestDispatcher("/loadaccountdata" 
																	+ "?page=" + activePage);
			rd.forward(request, response);	
			return null;
		}
		
		// kiểm tra syntax input data: nếu ko thỏa
		if (!phone_number.matches(AppConfig.regexPhone)) {
			request.setAttribute("mess", "Sai cấu trúc phone number");
			// set value tạm trên form
			Account account = new Account(user_mail, null, account_role, user_name, phone_number, user_status);
			setRequestAttributeAccForm(request, account);

			// quay lại form update Account
			return "admin/update_account";
		}

		AccountDAO accountDAO = new AccountDAO();		
		boolean updateAccData_status = accountDAO.update(user_mail, account_role, user_name, phone_number, user_status);				
		
		if (updateAccData_status) {
			mess = "Cập nhật User thành công";
		} else {
			mess = "Cập nhật User thất bại";
		}		
		
		// quay về trang load Account có trong DB
		request.setAttribute("mess", mess);		
		RequestDispatcher rd = request.getRequestDispatcher("/loadaccountdata" 
																+ "?page=" + activePage);
		rd.forward(request, response);
		return null;
	}
	
	// delete Account
	@RequestMapping(value = "/deleteAcc")
	public String deleteAcc(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ServletException{
		// Setting Unicode cho request (để nhận đúng tiếng Việt khi get parameter), response
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		
		HttpSession session = request.getSession(true);
		// account_role = 1,2 mới dc load trang admin
		if (!AuthController.checkUser(session, response)) {
			
			return null;
		}
		
		String mess;
		@SuppressWarnings("rawtypes")
		HashMap hashMap = (HashMap) session.getAttribute("accMap");		
		int activePage = (int) hashMap.get("activePage");
		
		// get idAcc	
		String id = request.getParameter("id");

		// delete User theo id=user_mail
		AccountDAO accountDAO = new AccountDAO();
		boolean deleteAccData_status = accountDAO.delete(id);		
		if (deleteAccData_status) {
			mess = "Xóa User thành công";
		} else {
			mess = "Xóa User thất bại";
		}		
		// quay về trang load Account có trong DB
		request.setAttribute("mess", mess);		
		RequestDispatcher rd = request.getRequestDispatcher("/loadaccountdata" 
																+ "?page=" + activePage);
		rd.forward(request, response);		
		return null;	
	}

	// delete Account List
	@RequestMapping(value = "/deleteAccList")
	public String deleteAccList(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException{
		// Setting Unicode cho request (để nhận đúng tiếng Việt khi get parameter), response
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		
		HttpSession session = request.getSession(true);
		// account_role = 1,2 mới dc load trang admin
		if (!AuthController.checkUser(session, response)) {
			
			return null;
		}
		
		String mess;
		@SuppressWarnings("rawtypes")
		HashMap hashMap = (HashMap) session.getAttribute("accMap");		
		int activePage = (int) hashMap.get("activePage");
		
		// get idAcc
		String[] id = request.getParameterValues("id");
		if (id == null) {
			mess = "Bạn chưa chọn đối tượng để xóa";
			
			// quay về trang load Account có trong DB
			request.setAttribute("mess", mess);		
			RequestDispatcher rd = request.getRequestDispatcher("/loadaccountdata" 
																	+ "?page=" + activePage);
			rd.forward(request, response);			
			return null;
		}
		
		StringBuilder idList = new StringBuilder();
		for (String accID : id) {
			idList.append("'").append(accID).append("',");
		}
		// xóa dấu "," cuối
		idList.deleteCharAt(idList.length() - 1);

		// delete User theo id=user_mail
		AccountDAO accountDAO = new AccountDAO();		
		boolean deleteAccListData_status = accountDAO.deleteList(idList);
		
		if (deleteAccListData_status) {			
			mess = "Xóa nhiều User thành công";
		} else {
			mess = "Xóa nhiều User thất bại";
		}		
		
		// quay về trang load Account có trong DB
		request.setAttribute("mess", mess);		
		RequestDispatcher rd = request.getRequestDispatcher("/loadaccountdata" 
																+ "?page=" + activePage);
		rd.forward(request, response);		
		return null;
	}
	
	// reset Account password from admin
	@RequestMapping(value = "/resetAccPassword")
	public String resetAccPassword(HttpServletRequest request, HttpServletResponse response) throws SQLException, NoSuchAlgorithmException, IOException {
		// Setting Unicode cho request (để nhận đúng tiếng Việt khi get parameter), response
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		
		HttpSession session = request.getSession(true);
		// account_role = 1,2 mới dc load trang admin
		if (!AuthController.checkUser(session, response)) {
			
			return null;
		}
		
		// get idAcc
		String id = request.getParameter("id");		
		
		String newPass = new RandomPassword().getRanDomPassword();
		
		String recipientsEmail = id;
        String subject = "Mât khẩu mới reset từ Lottery";
        String text = "Xin chào " + id
                		+ "\n\n Đây là mật khẩu mới reset của bạn : " + newPass
                		+ "\n dùng để đăng nhập trang Lottery";
        
        SendEmailSSL ssl = new SendEmailSSL();
        boolean sendMailStatus = ssl.sendMail(recipientsEmail, subject, text);
        
        String mess;
        if (sendMailStatus) {
        	// câp nhật new pass vào DB
			AccountDAO accountDAO = new AccountDAO();
			accountDAO.newUserPassword(id, newPass);
			
			mess = "Reset mật khẩu " + id + " thành công";
			request.setAttribute("mess", mess);
			// quay về trang quản lý acc
			return "admin/accountdata";
			
		} else {
			mess = "Reset mật khẩu " + id + " thất bại";
			request.setAttribute("mess", mess);
			// quay về trang quản lý acc
			return "admin/accountdata";
		}        
	}
	
// Xử lý của ADMIN ----------------------------------------------------------------------------------------------------------
	
	public static void setRequestAttributeAccForm(HttpServletRequest request, Account acc) {		
		request.setAttribute("usr", acc.getUser_mail());
		request.setAttribute("pwd", acc.getPassword());
		request.setAttribute("role", acc.getAccount_role());
		request.setAttribute("name", acc.getUser_name());
		request.setAttribute("fnum", acc.getPhone_number());
		request.setAttribute("status", acc.getUser_status());		
	}
}
