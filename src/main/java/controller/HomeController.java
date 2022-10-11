package controller;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dao.HistoryDAO;
import dao.ResultsheetDAO;
import model.Account;
import model.ResultSheet;

@Controller
public class HomeController {

	// load trang home.jsp(DB)
	@RequestMapping(value = "/")
	public String homePage(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException, SQLException, ParseException {
		// Setting Unicode cho request (để nhận đúng tiếng Việt khi get parameter),response
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		
		HttpSession session = request.getSession(true);
		
		String lotteryCom = request.getParameter("id");
		
		ResultsheetDAO resultsheetDAO = new ResultsheetDAO();
		List<String> lotteryComList = resultsheetDAO.getLotteryCom();
		ResultSheet resultSheet;
		
		if (lotteryCom == null) {
			resultSheet = resultsheetDAO.getLastestRS("");			
		} else {
			resultSheet = resultsheetDAO.getLastestRS(lotteryCom);
		}		
		
		if (resultSheet == null) {			
			request.setAttribute("mess", "Không có dữ liệu CTXS ");
			request.setAttribute("table_display", "hidden");
			session.setAttribute("lcList", lotteryComList);
			
		} else {
			// set attribute cho bảng RS trong trang home.jsp
			ResultsheetController.setRequestAttributeRsForm(request, resultSheet);
			session.setAttribute("home_rs", resultSheet);
			session.setAttribute("lcList", lotteryComList);
			System.out.println("---------------------------home_rs");
			System.out.println("resultSheet.getLottery_com(): " + resultSheet.getLottery_com() + ", " 
								+ "\nresultSheet.getLottery_date(): " + resultSheet.getLottery_date());
		}
		
		// tới trang home
		return "home";
	}
	
	// dò vé số
	@RequestMapping(value = "/checkticket", method = RequestMethod.POST)
	public String checkTicket(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException, SQLException, ParseException {
		// Setting Unicode cho request (để nhận đúng tiếng Việt khi get parameter),response
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		
		HttpSession session = request.getSession(true);
		ResultsheetDAO resultsheetDAO = new ResultsheetDAO();
		ResultSheet resultSheet = new ResultSheet();
		
		// get parameter
		String number = request.getParameter("number");
		String com = request.getParameter("com");
		String date_str = request.getParameter("date");		
		Date date = null;
		if (date_str != null && date_str != "") {
			date = new SimpleDateFormat("dd-MM-yyyy").parse(date_str);
		}		
		
		if (number != null) {
			resultSheet = (ResultSheet) session.getAttribute("home_rs");
		}
		
		if(number != null && !com.equals("--Chọn tỉnh--") && date_str != null){
			resultSheet = resultsheetDAO.checkResultsheetExist(com, date);
		}		
		
		if (resultSheet == null) {
			request.setAttribute("number", number);
			request.setAttribute("com", com);
			request.setAttribute("date", date_str);
			
			request.setAttribute("mess", "Không có dữ liệu CTXS ");
			request.setAttribute("table_display", "hidden");
			
		} else {
			ResultsheetController.setRequestAttributeRsForm(request, resultSheet);
			System.out.println("check---------------------------home_rs");
			System.out.println("resultSheet.getLottery_com(): " + resultSheet.getLottery_com() + ", " 
								+ "\nresultSheet.getLottery_date(): " + resultSheet.getLottery_date());
						
			request.setAttribute("number", number);
			request.setAttribute("com", com);
			request.setAttribute("date", date_str);
			
			// xử lý dò số và thông báo kết quả
			String bingo = ResultsheetController.checkResult(request, number, resultSheet);
			// lấy thông báo kết quả dò vé số
			String mess = (String) request.getAttribute("mess");
			
			request.setAttribute("bingo", bingo);
			
			Account acc = (Account) session.getAttribute("login_user");
			if (acc != null) {
				HistoryDAO historyDAO = new HistoryDAO();
				historyDAO.addCheckHistory(acc.getUser_mail(), resultSheet.getLottery_id(), number, mess);
				
			} else {
				mess = mess + "<br>"
						+ "Hãy đăng nhập, đăng ký tài khoản để có thể lưu lịch sử dò vé số của bạn";
				
				request.setAttribute("mess", mess);
			}
		}
		
		// tới trang home
		return "home";
	}

}
