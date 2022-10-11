package controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.restfb.types.User;

import accessfacebook.RestFB;
import accessgoogle.GooglePojo;
import accessgoogle.GoogleUtils;
import context.AppConfig;
import context.RandomPassword;
import dao.AccountDAO;
import mail.SendEmailSSL;
import model.Account;

@Controller
public class AuthController {	
// Xử lý của USER login, logout, register, reset password, login google ----------------------------------------------------------------------------------------------------------
	
	// check User điều hướng
	public static boolean checkUser(HttpSession session, HttpServletResponse response) throws IOException {
			
		Account login_user = (Account) session.getAttribute("login_user");
		// account_role = 1,2 mới dc load trang admin
		if (login_user == null) {
			
			response.sendRedirect("/Lottery/");
			return false;
		} else if (login_user.getAccount_role() == 0) {
			
			response.sendRedirect("/Lottery/");
			return false;
		}
		
		return true;
	}	
	
	// load trang login
	@RequestMapping(value = {"/login"}, method = RequestMethod.GET)
	public String loginPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		Cookie arr[] = request.getCookies();
		if (arr != null) {
			for (Cookie ck : arr) {
				if (ck.getName().equals("userC")) {
					request.setAttribute("user_cookie", ck.getValue());
				}
			}
		}

		return "login";
	}
	
	// do login
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String doLogin(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, NoSuchAlgorithmException {
		// Setting Unicode cho request (để nhận đúng tiếng Việt khi get parameter), response 
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String user_mail = request.getParameter("user_mail");
		String password = request.getParameter("password");
		String remember = request.getParameter("remember");
		
		if (!user_mail.matches(AppConfig.regexMail) || !password.matches(AppConfig.regexPwd)) {
			request.setAttribute("mess", "Sai cấu trúc email hoặc password");
			request.setAttribute("user_cookie", user_mail);
			
			return "login";
		}
		
		Account acc = new AccountDAO().login(user_mail, password);
		
		if (acc == null) {
			request.setAttribute("mess", "Tài khoản hoặc mật khẩu không đúng");
			request.setAttribute("user_cookie", user_mail);
			
			return "login";
			
		} else {
			// acc còn hiệu lực
			if (acc.getUser_status() == 1) {
				// set session
				HttpSession session = request.getSession(true);				

				session.setAttribute("login_user", acc);
				@SuppressWarnings("deprecation")
				String imgServer_path = request.getRealPath("/") + "statics\\images\\account_img";
				File file = new File(imgServer_path, acc.getUser_mail() + ".jpg");
				boolean avata =  (file.exists()) ? true : false;
				
				session.setAttribute("avata", avata);
				session.setAttribute("welcomeToUser", "Chào mừng " + acc.getUser_name());
				
				// set cookie
				if (remember != null) {
					Cookie ck = new Cookie("userC", user_mail);
					ck.setMaxAge(180);
					
					response.addCookie(ck);
				} else {
					Cookie arr[] = request.getCookies();
					if (arr != null) {
						for (Cookie ck : arr) {
							if (ck.getName().equals("userC")) {
								// xóa cookie
								ck.setMaxAge(0);
								
								response.addCookie(ck);
							}
						}
					}
				}
				
				// chuyển hướng trang tùy acc_role
				if (acc.getAccount_role() == 1 || acc.getAccount_role() == 2) {
					response.sendRedirect("/Lottery/loadaccountdata");
					
				} else {
					response.sendRedirect("/Lottery/");
				}
			// acc bị khóa
			} else {
				request.setAttribute("mess", "Account đang bị tạm khóa hãy liên hệ Admin");
				request.setAttribute("user_cookie", user_mail);
				
				return "login";
			}		
		}
		
		return null;		
	}
	
	// do logout
	@RequestMapping(value = "/logout")
	public String doLogout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
		HttpSession session = request.getSession(true);
		
		String mess = (String) request.getAttribute("mess");
		
		// xóa user đăng nhập
		session.removeAttribute("login_user");
		session.removeAttribute("avata");
		session.removeAttribute("welcomeToUser");
		
		// lấy user_cookie trường hợp có chọn rememberme khi login
		Cookie arr[] = request.getCookies();
		if (arr != null) {
			for (Cookie ck : arr) {
				if (ck.getName().equals("userC")) {
					request.setAttribute("user_cookie", ck.getValue());
				}
			}
		}
		
		request.setAttribute("mess", mess);
		// chuyển đến trang login.jsp		
		return "login";
	}	
	
	// load trang register
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registerPage(HttpServletRequest request, HttpServletResponse response) {
		
		return "register";
	}
	
	// do register
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String doRegister(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, SQLException, NoSuchAlgorithmException {
		// Setting Unicode cho request (để nhận đúng tiếng Việt khi get parameter), response 
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");

		String user_name = request.getParameter("user_name");
		String user_mail = request.getParameter("user_mail");		
		String phone_number = request.getParameter("phone_number");
		
		if (!user_mail.matches(AppConfig.regexMail) || !phone_number.matches(AppConfig.regexPhone)) {
			request.setAttribute("mess", "Sai cấu trúc email hoặc phone number");
			request.setAttribute("name", user_name);
			request.setAttribute("usr", user_mail);
			
			return "register";
		}
		
		Account acc = new AccountDAO().getAcc(user_mail);
		
		if (acc == null) {
			AccountDAO accountDAO = new AccountDAO();	
			
			// random password cho acc mới tạo tài khoản
			String password = new RandomPassword().getRanDomPassword();
			
			String recipientsEmail = user_mail;
	        String subject = "Mât khẩu tạo mới Account từ Lottery";
	        String text = "Xin chào " + user_mail
	                		+ "\n\n Đây là mật khẩu mới tạo cho tài khoản của bạn : " + password
	                		+ "\n dùng để đăng nhập trang Lottery";
	        // sendmail
	        SendEmailSSL ssl = new SendEmailSSL();
	        boolean sendMailStatus = ssl.sendMail(recipientsEmail, subject, text);
	        
	        String mess;
	        if (sendMailStatus) {
	        	// add new Acc vào DB
	        	boolean addAccData_status = accountDAO.add(user_mail, password, 0, user_name, phone_number, 1);
				if (addAccData_status) {
					mess = "Tạo tài khoản mới thành công, mời bạn login";	
					request.setAttribute("mess", mess);
					
					return "login";
					
				} else {
					mess = "Tạo tài khoản mới thất bại, mời bạn đăng ký lại thông tin";
					request.setAttribute("mess", mess);
					
					return "register";
				}	        	
				
			} else {
				mess = "Tạo tài khoản mới thất bại, mời bạn đăng ký lại thông tin";
				request.setAttribute("mess", mess);
				
				return "register";
			}						
			
		} else {
			request.setAttribute("mess", "Account đã tồn tại, bạn hãy chọn 1 tên tài khoản khác");
			
			request.setAttribute("name", user_name);
			request.setAttribute("usr", user_mail);			
			
			return "register";
		}		
	}
	
	// load trang reset acc password
	@RequestMapping(value = "/reset_accpassword", method = RequestMethod.GET)
	public String reset_accpasswordPage() {
		
		return "reset_accpassword";
	}
	
	// do reset password do user forgot password
	@RequestMapping(value = "/reset_accpassword", method = RequestMethod.POST)
	public String doReset_accpassword(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, SQLException, NoSuchAlgorithmException {
		// Setting Unicode cho request (để nhận đúng tiếng Việt khi get parameter), response 
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String user_mail = request.getParameter("user_mail");
		
		if (!user_mail.matches(AppConfig.regexMail)) {
			request.setAttribute("mess", "Sai cấu trúc email");
			request.setAttribute("user_cookie", user_mail);
			
			return "reset_accpassword";			
		}
		
		Account acc = new AccountDAO().getAcc(user_mail);
		
		if (acc == null) {
			request.setAttribute("mess", "Tài khoản không tồn tại");
			request.setAttribute("user_cookie", user_mail);
			
			return "reset_accpassword";
			
		} else {
			// acc còn hiệu lực
			if (acc.getUser_status() == 1) {
				// random password cho acc mới tạo tài khoản
				String newPass = new RandomPassword().getRanDomPassword();
				
				String recipientsEmail = user_mail;
		        String subject = "Mât khẩu mới reset theo yêu cầu của bạn từ Lottery";
		        String text = "Xin chào " + user_mail
		                		+ "\n\n Đây là mật khẩu mới reset của bạn : " + newPass
		                		+ "\n dùng để đăng nhập trang Lottery";
		        
		        SendEmailSSL ssl = new SendEmailSSL();
		        boolean sendMailStatus = ssl.sendMail(recipientsEmail, subject, text);
		        
		        String mess;        
		        if (sendMailStatus) {
		        	// câp nhật new pass vào DB
					AccountDAO accountDAO = new AccountDAO();
					accountDAO.newUserPassword(user_mail, newPass);
					
					mess = "Reset mật khẩu " + user_mail + " thành công";
					request.setAttribute("mess", mess);
					request.setAttribute("user_cookie", user_mail);
					
					return "login";
					
				} else {
					mess = "Reset mật khẩu " + user_mail + " thất bại";
					request.setAttribute("mess", mess);
					request.setAttribute("user_cookie", user_mail);
					
					return "reset_accpassword";
				}						
			// acc bị khóa
			} else {
				request.setAttribute("mess", "Account đang bị tạm khóa hãy liên hệ Admin");
				request.setAttribute("user_cookie", user_mail);
				
				return "reset_accpassword";
			}		
		}
	}
	
	// load trang change acc password
	@RequestMapping(value = "/change_accpassword", method = RequestMethod.GET)
	public String change_accpasswordPage(HttpServletRequest request, HttpServletResponse response) {		
		Cookie arr[] = request.getCookies();
		if (arr != null) {
			for (Cookie ck : arr) {
				if (ck.getName().equals("userC")) {
					request.setAttribute("user_cookie", ck.getValue());
				}
			}
		}
		
		return "change_accpassword";
	}
	
	// do change password do yêu cầu từ user
	@RequestMapping(value = "/change_accpassword", method = RequestMethod.POST)
	public String doChange_accpassword(HttpServletRequest request, HttpServletResponse response) throws NoSuchAlgorithmException, SQLException, IOException, ServletException {
		// Setting Unicode cho request (để nhận đúng tiếng Việt khi get parameter), response 
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		
		// collect data from a add user form
		String user_mail = request.getParameter("user_mail");
		String oldpassword = request.getParameter("oldpassword");
		String newpassword = request.getParameter("newpassword");
		String re_newpassword = request.getParameter("re_newpassword");
		
		if (!oldpassword.matches(AppConfig.regexPwd) || !newpassword.matches(AppConfig.regexPwd)) {
		request.setAttribute("mess", "Sai cấu trúc email hoặc password");
		request.setAttribute("user_cookie", user_mail);
		
		return "change_accpassword";			
		}
		
		Account acc = new AccountDAO().login(user_mail, oldpassword);
		
		if (acc == null) {
			request.setAttribute("mess", "Mật khẩu cũ chưa chính xác");
			
			return "change_accpassword";
			
		} else if (oldpassword.equals(newpassword)){
			request.setAttribute("mess", "Mật khẩu cũ và mật khẩu mới phải khác nhau");
			
			return "change_accpassword";
			
		} else if (!newpassword.equals(re_newpassword)){
			request.setAttribute("mess", "Mật khẩu mới không giống nhau");
			
			return "change_accpassword";
			
		} else {
			String recipientsEmail = user_mail;
	        String subject = "Mât khẩu mới thay đổi theo yêu cầu của bạn từ Lottery";
	        String text = "Xin chào " + user_mail
	                		+ "\n\n Đây là mật khẩu mới thay đổi của bạn : " + newpassword
	                		+ "\n dùng để đăng nhập trang Lottery";
	        
	        SendEmailSSL ssl = new SendEmailSSL();
	        boolean sendMailStatus = ssl.sendMail(recipientsEmail, subject, text);
	        
	        String mess;
	        if (sendMailStatus) {
	        	// câp nhật new pass vào DB
				AccountDAO accountDAO = new AccountDAO();
				accountDAO.newUserPassword(user_mail, newpassword);
				
				mess = "Thay đổi mật khẩu " + user_mail + " thành công"
						+ "\nMời bạn đăng nhập lại trang web";
				// logout và login lại
				request.setAttribute("mess", mess);
				request.setAttribute("user_cookie", user_mail);
				
				RequestDispatcher rd = request.getRequestDispatcher("/logout");
				rd.forward(request, response);
				
				return null;
				
			} else {
				mess = "Thay đổi mật khẩu " + user_mail + " thất bại";
				request.setAttribute("mess", mess);
				
				return "change_accpassword";
			}
		}		
	}	
	
	@RequestMapping(value = "/login-google")
	public String loginGoogle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, NoSuchAlgorithmException {
		
		HttpSession session = request.getSession(true);
		
		String code = request.getParameter("code");
		String mess;
		
		if (code == null || code.isEmpty()) {
			mess = "Không thể login với tài khoản google bạn đã chọn, mời bạn đăng nhập lại";	
			request.setAttribute("mess", mess);
			
			return "login";
		} else {
			String accessToken = GoogleUtils.getToken(code);
			// info của user get dc từ Google API
			GooglePojo googlePojo = GoogleUtils.getUserInfo(accessToken);
			// user info
			String user_mail = googlePojo.getEmail();
			// random password cho acc mới tạo tài khoản
			String password = new RandomPassword().getRanDomPassword();			
			String user_name = googlePojo.getName();		
			String phone_number = "0123.123.123";
			
			Account acc = new AccountDAO().getAcc(user_mail);
			
			if (acc == null) {
				AccountDAO accountDAO = new AccountDAO();
				Account account = new Account(user_mail, password, 0, user_name, phone_number, 1);
				
				// add new Acc vào DB
	        	boolean addAccData_status = accountDAO.add(user_mail, password, 0, user_name, phone_number, 1);
	        	
	        	if (addAccData_status) {
	        		// set session
	    			session.setAttribute("login_user", account);
	    			session.setAttribute("welcomeToUser", "Chào mừng " + account.getUser_name());

	    			response.sendRedirect("/Lottery/");
					
				} else {
					mess = "Không thể tạo tài khoản mới, mời bạn đăng nhập lại";
					request.setAttribute("mess", mess);
					
					return "login";
				}								
			} else if (acc.getUser_status() == 1) {
				// set session
    			session.setAttribute("login_user", acc);
    			session.setAttribute("welcomeToUser", "Chào mừng " + acc.getUser_name());			

    			response.sendRedirect("/Lottery/");
				
			} else {
				request.setAttribute("mess", "Account đang bị tạm khóa hãy liên hệ Admin");
				request.setAttribute("user_cookie", user_mail);
				
				return "login";
			}			
		}
		
		return null;
	}
	
	@RequestMapping(value = "/login-facebook")
	public String loginFacebook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, NoSuchAlgorithmException {
		
		HttpSession session = request.getSession(true);
		
		String code = request.getParameter("code");
		String mess;
	    
	    if (code == null || code.isEmpty()) {
	    	
	    	return "login";
	    } else {
	      String accessToken = RestFB.getToken(code);
	      // info của user get dc từ RestFB
	      User user = RestFB.getUserInfo(accessToken);
	      // random password cho acc mới tạo tài khoản
	      String password = new RandomPassword().getRanDomPassword();
	      String id = user.getId();
	      String user_name = user.getName();
	      String phone_number = "0123.123.123";
	      
	      Account acc = new AccountDAO().getAcc(id);
			
			if (acc == null) {
				AccountDAO accountDAO = new AccountDAO();
				Account account = new Account(id, password, 0, user_name, phone_number, 1);
				
				// add new Acc vào DB
	        	boolean addAccData_status = accountDAO.add(id, password, 0, user_name, phone_number, 1);
	        	
	        	if (addAccData_status) {
	        		// set session
	    			session.setAttribute("login_user", account);
	    			session.setAttribute("welcomeToUser", "Chào mừng " + account.getUser_name());

	    			response.sendRedirect("/Lottery/");
					
				} else {
					mess = "Không thể tạo tài khoản mới, mời bạn đăng nhập lại";
					request.setAttribute("mess", mess);
					
					return "login";
				}								
			} else if (acc.getUser_status() == 1) {
				// set session
	  			session.setAttribute("login_user", acc);
	  			session.setAttribute("welcomeToUser", "Chào mừng " + acc.getUser_name());			

	  			response.sendRedirect("/Lottery/");
				
			} else {
				request.setAttribute("mess", "Account đang bị tạm khóa hãy liên hệ Admin");
				request.setAttribute("user_cookie", id);
				
				return "login";
			}	      
	      
	      return null;
	    }
	}
}
