package controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import dao.HistoryDAO;
import model.History;

@Controller
public class HistoryController {
	// số lượng item/page
	private int itemPerPage = AppConfig.userHistoryPage;

	//load history data của User from DB(userhistorydata.jsp)(DB)
	@RequestMapping(value="/loaduserhistorydata")
	public String loadUserHistoryPage(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, SQLException {
		// Setting Unicode cho request (để nhận đúng tiếng Việt khi get parameter), response
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		
		HttpSession session = request.getSession(true);
		
		// get parameter từ pages-nav
		String page = request.getParameter("page");
		
		String history_scope = request.getParameter("history_scope");
		System.out.println("history_scope = " + history_scope);				
		
		session.setAttribute("history_scope", history_scope);
		
		HistoryDAO historyDAO = new HistoryDAO();
		
		// set khởi gán cho active page của Account
		int activePage = (page == null)? 1 : Integer.parseInt(page);
		// Khi bắt đầu search page = null
		// page có giá trị khi nhấn link phân trang page-nav
		
		// đếm sl RS có trong DB
		int countHistory = historyDAO.countUserHistory("", history_scope);
		// trang kết thúc của list
		int endPage = (int) Math.ceil((float) countHistory / itemPerPage);
		// put vào HashMap
		HashMap<String, Integer> userHistoryMap = new HashMap<>();
		userHistoryMap.put("activePage", activePage);
		userHistoryMap.put("endPage", endPage);
		
		List<History> userHistoryList = historyDAO.getCheckHistory("", history_scope, activePage, itemPerPage);
		
		session.setAttribute("hisList", userHistoryList);
		session.setAttribute("hisMap", userHistoryMap);	
		
		return "userhistorydata";
	}
	
	//load history data của User from DB(userhistorydata.jsp)(DB)
	@RequestMapping(value="/search_userhistorydata")
	public String loadUserHistoryPage_Search(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, SQLException {
		// Setting Unicode cho request (để nhận đúng tiếng Việt khi get parameter), response
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		
		HttpSession session = request.getSession(true);
		String history_scope = (String) session.getAttribute("history_scope");
		System.out.println("history_scope = " + history_scope);
		
		HistoryDAO historyDAO = new HistoryDAO();
		//Account acc = new Account();
		//acc = (Account) session.getAttribute("login_user");
		
		// get parameter từ pages-nav
		String usr_search = request.getParameter("usr_search");
		String page = request.getParameter("page");
		
		// set khởi gán cho active page của Account
		int activePage = (page == null)? 1 : Integer.parseInt(page);
		// Khi bắt đầu search page = null
		// page có giá trị khi nhấn link phân trang page-nav
		
		// đếm sl RS có trong DB
		int countHistory = historyDAO.countUserHistory(usr_search, history_scope);
		// trang kết thúc của list
		int endPage = (int) Math.ceil((float) countHistory / itemPerPage);			
		
		List<History> userHistoryList = historyDAO.getCheckHistory(usr_search, history_scope, activePage, itemPerPage);
		
		if (userHistoryList == null) {
			request.setAttribute("mess", "Không tìm thấy kết quả phù hợp với yêu cầu của bạn");
			// set accountList vào acc_searchList,
			session.setAttribute("hisList", userHistoryList);
			request.setAttribute("activePage", activePage);
			request.setAttribute("endPage", endPage);
			request.setAttribute("usr_search", usr_search);
			
			return "search_userhistorydata";
			
		} else {
			// set accountList vào acc_searchList,
			session.setAttribute("hisList", userHistoryList);
			request.setAttribute("activePage", activePage);
			request.setAttribute("endPage", endPage);
			request.setAttribute("usr_search", usr_search);
			
			return "search_userhistorydata";
		}		
	}
	
	// delete History
	@RequestMapping(value = "/deleteHis")
	public String deleteRS(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ServletException  {
		// Setting Unicode cho request (để nhận đúng tiếng Việt khi get parameter), response
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		
		HttpSession session = request.getSession(true);
		String mess;
		@SuppressWarnings("rawtypes")
		HashMap hashMap = (HashMap) session.getAttribute("hisMap");		
		int activePage = (int) hashMap.get("activePage");
				
		String id = request.getParameter("id");

		// delete Result sheet theo id=lottery_id
		HistoryDAO historyDAO = new HistoryDAO();		
		Boolean deleteHisData_status = historyDAO.delete(id);		
		
		if (deleteHisData_status) {
			mess = "Xóa lịch sử dò vé số thành công";
		} else {
			mess = "Xóa lịch sử dò vé số thất bại";
		}
		
		String history_scope = (String) session.getAttribute("history_scope");
		// quay về trang load RS có trong DB
		request.setAttribute("mess", mess);		
		RequestDispatcher rd = request.getRequestDispatcher("/loaduserhistorydata"
																+ "?history_scope=" + history_scope
																+ "&page=" + activePage);
		rd.forward(request, response);		
		return null;
	}

	// delete History List
	@RequestMapping(value = "/deleteHisList", method = RequestMethod.POST)
	public String deleteRSList(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ServletException {
		// Setting Unicode cho request (để nhận đúng tiếng Việt khi get parameter), response
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		
		HttpSession session = request.getSession(true);		
		String mess;
		String history_scope = (String) session.getAttribute("history_scope");
		@SuppressWarnings("rawtypes")
		HashMap hashMap = (HashMap) session.getAttribute("hisMap");		
		int activePage = (int) hashMap.get("activePage");
		
		// get idHis
		String[] id = request.getParameterValues("id");
		if (id == null) {
			mess = "Bạn chưa chọn đối tượng để xóa";
			
			// quay về trang load Account có trong DB
			request.setAttribute("mess", mess);		
			RequestDispatcher rd = request.getRequestDispatcher("/loaduserhistorydata" 
																	+ "?history_scope=" + history_scope
																	+ "&page=" + activePage);
			rd.forward(request, response);			
			return null;
		}
				
		StringBuilder idList = new StringBuilder();
		for (String accID : request.getParameterValues("id")) {
			idList.append("'").append(accID).append("',");
		}
		idList.deleteCharAt(idList.length() - 1);

		// delete Result sheet theo id=lottery_id
		HistoryDAO historyDAO = new HistoryDAO();		
		Boolean deleteHisListData_status = historyDAO.deleteList(idList);
		
		if (deleteHisListData_status) {
			mess = "Xóa nhiều lịch sử dò vé số thành công";
		} else {
			mess = "Xóa nhiều lịch sử dò vé số thất bại";
		}		
		
		// quay về trang load RS có trong DB
		request.setAttribute("mess", mess);		
		RequestDispatcher rd = request.getRequestDispatcher("/loaduserhistorydata" 
																+ "?history_scope=" + history_scope
																+ "&page=" + activePage);
		rd.forward(request, response);		
		return null;
	}
}
