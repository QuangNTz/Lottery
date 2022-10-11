package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import dao.ResultsheetDAO;
import model.ResultSheet;

@Controller
public class ResultsheetController {
	// số lượng item/page
	private int itemPerPage = AppConfig.rsPage;

// Xử lý của ADMIN ----------------------------------------------------------------------------------------------------------
	
	// load RSdata(resultsheetdata.jsp)(DB)
	@RequestMapping(value = "/loadresultsheetdata")
	public String loadRSPage(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException{
		// Setting Unicode cho request (để nhận đúng tiếng Việt khi get parameter), response
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
				
		HttpSession session = request.getSession(true);
		// account_role = 1,2 mới dc load trang admin
		if (!AuthController.checkUser(session, response)) {
			
			return null;
		}

		// get parameter từ pages-nav		
		String page = request.getParameter("page");
		
		String mess = (String) request.getAttribute("mess");
		
		ResultsheetDAO resultsheetDAO = new ResultsheetDAO();

		// set khởi gán cho active page của RS
		int activePage = (page == null)? 1 : Integer.parseInt(page);
		// Khi bắt đầu search page = null
		// page có giá trị khi nhấn link phân trang page-nav

		// đếm sl rs có trong DB
		int countRS = resultsheetDAO.countInDatabase("");
		// trang kết thúc củ list
		int endPage = (int) Math.ceil((float) countRS / itemPerPage);
		// put vào HashMap
		HashMap<String, Integer> resultsheetMap = new HashMap<>();
		resultsheetMap.put("activePage", activePage);
		resultsheetMap.put("endPage", endPage);

		List<ResultSheet> resultsheetList = resultsheetDAO.getList("", activePage, itemPerPage);		
		
		request.setAttribute("mess", mess);
		session.setAttribute("rsList", resultsheetList);// RS datalist
		session.setAttribute("rsMap", resultsheetMap);

		return "admin/resultsheetdata";
	}
	
	// search RSdata(search_resultsheetdata.jsp)(DB)
	@RequestMapping(value = "/search_resultsheetdata")
	public String loadRSPage_Search(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException{
		// Setting Unicode cho request (để nhận đúng tiếng Việt khi get parameter), response 
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		
		HttpSession session = request.getSession(true);
		// account_role = 1,2 mới dc load trang admin
		if (!AuthController.checkUser(session, response)) {
			
			return null;
		}
		
		// get parameter từ pages-nav
		String page = request.getParameter("page");
		String rs_search = request.getParameter("rs_search");
		ResultsheetDAO resultsheetDAO = new ResultsheetDAO();

		// set khởi gán cho active page của Account
		int activePage = (page == null)? 1 : Integer.parseInt(page);
		// Khi bắt đầu search page = null
		// page có giá trị khi nhấn link phân trang page-nav

		// đếm sl RS có trong DB
		int countAcc = resultsheetDAO.countInDatabase(rs_search);
		// trang kết thúc củ list
		int endPage = (int) Math.ceil((float) countAcc / itemPerPage);
		
		List<ResultSheet> rs_searchList = resultsheetDAO.getList(rs_search, activePage, itemPerPage);

		if (rs_searchList == null) {
			request.setAttribute("mess", "Không tìm thấy kết quả phù hợp với yêu cầu của bạn");
			request.setAttribute("rs_search", rs_search);			
			
			return "admin/search_resultsheetdata";
		} else {
			// set rsList vào rs_searchList,
			request.setAttribute("rs_searchList", rs_searchList);
			request.setAttribute("activePage", activePage);
			request.setAttribute("endPage", endPage);
			request.setAttribute("rs_search", rs_search);
			
			return "admin/search_resultsheetdata";
		}			
	}
	
	// save new CTXS
	@RequestMapping(value = "/add_ctxs")
	public String addCtxs(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		// Setting Unicode cho request (để nhận đúng tiếng Việt khi get parameter), response
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		
		HttpSession session = request.getSession(true);
		// account_role = 1,2 mới dc load trang admin
		if (!AuthController.checkUser(session, response)) {
			
			return null;
		}
		
		ResultsheetDAO resultsheetDAO = new ResultsheetDAO();
		
		String ctxs = request.getParameter("ctxs");
		
		// check sự tồn tại của Ctxs trong DB
		String ctxs_db = resultsheetDAO.checkCtxsExist(ctxs);
		
		if (ctxs_db == null) {
			boolean addCtxs_status = resultsheetDAO.addCtxs(ctxs);
			if (addCtxs_status) {
				request.setAttribute("ctsx_mess", "Thêm CTXS thành công");
				request.setAttribute("ctxs_input", ctxs);
				List<String> lotteryComList = resultsheetDAO.getLotteryCom();
				session.setAttribute("lcList", lotteryComList);
				
			} else {
				request.setAttribute("ctsx_mess", "Thêm CTXS thất bại");
				request.setAttribute("ctxs_input", ctxs);
			}
			// quay lại form add newRS
			return "admin/add_resultsheet";
			
		} else {			
			request.setAttribute("ctsx_mess", "CTXS đã tồn tại");
			request.setAttribute("ctxs_input", ctxs);

			// quay lại form add newRS
			return "admin/add_resultsheet";
		}		
	}	
	
	// load trang form add newRS(DB)
	@RequestMapping(value = "/add_resultsheet", method = RequestMethod.GET)
	public String loadAddRSFormPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession(true);
		// account_role = 1,2 mới dc load trang admin
		if (!AuthController.checkUser(session, response)) {
			
			return null;
		}

		return "admin/add_resultsheet";
	}

	// save newRS_form vào database
	@RequestMapping(value = "/add_resultsheet", method = RequestMethod.POST)
	public String addRSData(HttpServletRequest request, HttpServletResponse response) throws ParseException, SQLException, IOException, ServletException {
		// Setting Unicode cho request (để nhận đúng tiếng Việt khi get parameter), response
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");

		// get parameter
		String lottery_com = request.getParameter("lottery_com");
		String lottery_date_str = request.getParameter("lottery_date");
		Date lottery_date = new SimpleDateFormat("dd-MM-yyyy").parse(lottery_date_str);
		
		String g8 = request.getParameter("g8");
		String g7 = request.getParameter("g7");
		String g6_1 = request.getParameter("g6_1");
		String g6_2 = request.getParameter("g6_2");
		String g6_3 = request.getParameter("g6_3");
		String g6 = g6_1 + "," + g6_2 + "," + g6_3;
		String g5 = request.getParameter("g5");
		String g4_1 = request.getParameter("g4_1");
		String g4_2 = request.getParameter("g4_2");
		String g4_3 = request.getParameter("g4_3");
		String g4_4 = request.getParameter("g4_4");
		String g4_5 = request.getParameter("g4_5");
		String g4_6 = request.getParameter("g4_6");
		String g4_7 = request.getParameter("g4_7");
		String g4 = g4_1 + "," + g4_2 + "," + g4_3 + "," + g4_4 + "," + g4_5 + "," + g4_6 + "," + g4_7;
		String g3_1 = request.getParameter("g3_1");
		String g3_2 = request.getParameter("g3_2");
		String g3 = g3_1 + "," + g3_2;
		String g2 = request.getParameter("g2");
		String g1 = request.getParameter("g1");
		String gdb = request.getParameter("gdb");

		CheckInputResultSheet check = new CheckInputResultSheet();
		System.out.println("----- check RS data input -----");
		// validate input data: nếu ko thỏa
		if (!check.checkGiai_8Input(g8) || !check.checkGiai_7Input(g7) || !check.checkGiai_5_6Input(g6_1) || !check.checkGiai_5_6Input(g6_2)
				|| !check.checkGiai_5_6Input(g6_3) || !check.checkGiai_5_6Input(g5) || !check.checkGiai_1_4Input(g4_1)
				|| !check.checkGiai_1_4Input(g4_2) || !check.checkGiai_1_4Input(g4_3) || !check.checkGiai_1_4Input(g4_4)
				|| !check.checkGiai_1_4Input(g4_5) || !check.checkGiai_1_4Input(g4_6) || !check.checkGiai_1_4Input(g4_7)
				|| !check.checkGiai_1_4Input(g3_1) || !check.checkGiai_1_4Input(g3_2) || !check.checkGiai_1_4Input(g2)
				|| !check.checkGiai_1_4Input(g1) || !check.checkGiai_dbInput(gdb)) {

			// gán giá trị thông báo error trên form trả về
			if (!check.checkGiai_8Input(g8)) {
				request.setAttribute("g8_e", "false");
			}
			if (!check.checkGiai_7Input(g7)) {
				request.setAttribute("g7_e", "false");
			}
			if (!check.checkGiai_5_6Input(g6_1) || !check.checkGiai_5_6Input(g6_2) || !check.checkGiai_5_6Input(g6_3)) {
				request.setAttribute("g6_e", "false");
			}
			if (!check.checkGiai_5_6Input(g5)) {
				request.setAttribute("g5_e", "false");
			}
			if (!check.checkGiai_1_4Input(g4_1) || !check.checkGiai_1_4Input(g4_2) || !check.checkGiai_1_4Input(g4_3) || !check.checkGiai_1_4Input(g4_4)
					|| !check.checkGiai_1_4Input(g4_5) || !check.checkGiai_1_4Input(g4_6) || !check.checkGiai_1_4Input(g4_7)) {
				request.setAttribute("g4_e", "false");
			}
			if (!check.checkGiai_1_4Input(g3_1) || !check.checkGiai_1_4Input(g3_2)) {
				request.setAttribute("g3_e", "false");
			}
			if (!check.checkGiai_1_4Input(g2)) {
				request.setAttribute("g2_e", "false");
			}
			if (!check.checkGiai_1_4Input(g1)) {
				request.setAttribute("g1_e", "false");
			}
			if (!check.checkGiai_dbInput(gdb)) {
				request.setAttribute("gdb_e", "false");
			}

			request.setAttribute("mess", "Giá trị nhập vào chưa đúng");
			ResultSheet rs = new ResultSheet(0, lottery_com, lottery_date, g8, g7, g6, g5, g4, g3, g2, g1, gdb);
			// set value tạm trên form
			setRequestAttributeRsForm(request, rs);

			// quay lại form add newRS
			return "admin/add_resultsheet";
		}

		// check sự tồn tại của RS trong DB
		ResultSheet resultSheet = new ResultsheetDAO().checkResultsheetExist(lottery_com, lottery_date);
		if (resultSheet == null) {
			ResultsheetDAO resultsheetDAO = new ResultsheetDAO();			
			boolean addRSData_status = resultsheetDAO.add(lottery_com, lottery_date, g8, g7, g6, g5, g4, g3, g2, g1, gdb);
						
			String mess;
			if (addRSData_status) {
				mess = "Thêm vé dò thành công";
			} else {
				mess = "Thêm vé dò thất bại";
			}
			
			HttpSession session = request.getSession(true);
			@SuppressWarnings("rawtypes")
			HashMap hashMap = (HashMap) session.getAttribute("rsMap");			
			int activePage = (int) hashMap.get("activePage");
			
			// quay về trang load RS có trong DB
			request.setAttribute("mess", mess);		
			RequestDispatcher rd = request.getRequestDispatcher("/loadresultsheetdata" 
																	+ "?page=" + activePage);
			rd.forward(request, response);

		} else {
			request.setAttribute("mess", "Result sheet already exist");
			ResultSheet rs = new ResultSheet(0, lottery_com, lottery_date, g8, g7, g6, g5, g4, g3, g2, g1, gdb);
			// set value tạm trên form
			setRequestAttributeRsForm(request, rs);

			// quay lại form add newRS
			return "admin/add_resultsheet";
		}
		
		return null;
	}
	
	// load trang form update_resultsheet.jsp(DB)
	@RequestMapping(value = "/update_resultsheet", method = RequestMethod.GET)
	public String updateRSFormPage(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		// Setting Unicode cho request (để nhận đúng tiếng Việt khi get parameter), response
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		
		HttpSession session = request.getSession(true);
		// account_role = 1,2 mới dc load trang admin
		if (!AuthController.checkUser(session, response)) {
			
			return null;
		}

		String lottery_id = request.getParameter("id");
		ResultSheet rs = new ResultsheetDAO().getRS(lottery_id);
		
		// set value tạm trên form update rs
		setRequestAttributeRsForm(request, rs);
		
		session.setAttribute("rstemp", rs);		
		// load trang update_resultsheet.jsp
		return "admin/update_resultsheet";
	}

	// save updateRS_form vào database(DB)
	@RequestMapping(value = "/update_resultsheet", method = RequestMethod.POST)
	public String updateRSData(HttpServletRequest request, HttpServletResponse response) throws ParseException, SQLException, IOException, ServletException {
		// Setting Unicode cho request (để nhận đúng tiếng Việt khi get parameter), response
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		
		HttpSession session = request.getSession(true);
		String mess;
		@SuppressWarnings("rawtypes")
		HashMap hashMap = (HashMap) session.getAttribute("rsMap");		
		int activePage = (int) hashMap.get("activePage");
		ResultSheet rstemp = (ResultSheet) session.getAttribute("rstemp");

		String lottery_com = request.getParameter("lottery_com");
		String lottery_date_str = request.getParameter("lottery_date");	
		Date lottery_date = new SimpleDateFormat("dd-MM-yyyy").parse(lottery_date_str);
		
		String g8 = request.getParameter("g8");
		String g7 = request.getParameter("g7");
		String g6_1 = request.getParameter("g6_1");
		String g6_2 = request.getParameter("g6_2");
		String g6_3 = request.getParameter("g6_3");
		String g6 = g6_1 + "," + g6_2 + "," + g6_3;
		String g5 = request.getParameter("g5");
		String g4_1 = request.getParameter("g4_1");
		String g4_2 = request.getParameter("g4_2");
		String g4_3 = request.getParameter("g4_3");
		String g4_4 = request.getParameter("g4_4");
		String g4_5 = request.getParameter("g4_5");
		String g4_6 = request.getParameter("g4_6");
		String g4_7 = request.getParameter("g4_7");
		String g4 = g4_1 + "," + g4_2 + "," + g4_3 + "," + g4_4 + "," + g4_5 + "," + g4_6 + "," + g4_7;
		String g3_1 = request.getParameter("g3_1");
		String g3_2 = request.getParameter("g3_2");
		String g3 = g3_1 + "," + g3_2;
		String g2 = request.getParameter("g2");
		String g1 = request.getParameter("g1");
		String gdb = request.getParameter("gdb");		
		
		// validate input data: nếu ko thỏa
		CheckInputResultSheet check = new CheckInputResultSheet();
		System.out.println("----- check RS data input -----");
		if (!check.checkGiai_8Input(g8) || !check.checkGiai_7Input(g7)
				|| !check.checkGiai_5_6Input(g6_1) || !check.checkGiai_5_6Input(g6_2) || !check.checkGiai_5_6Input(g6_3)
				|| !check.checkGiai_5_6Input(g5)
				|| !check.checkGiai_1_4Input(g4_1) || !check.checkGiai_1_4Input(g4_2) || !check.checkGiai_1_4Input(g4_3)
				|| !check.checkGiai_1_4Input(g4_4)
				|| !check.checkGiai_1_4Input(g4_5) || !check.checkGiai_1_4Input(g4_6) || !check.checkGiai_1_4Input(g4_7)
				|| !check.checkGiai_1_4Input(g3_1) || !check.checkGiai_1_4Input(g3_2)
				|| !check.checkGiai_1_4Input(g2)
				|| !check.checkGiai_1_4Input(g1)
				|| !check.checkGiai_dbInput(gdb)) {

			// gán giá trị thông báo error trên form trả về
			if (!check.checkGiai_8Input(g8)) {
				request.setAttribute("g8_e", "false");
			}
			if (!check.checkGiai_7Input(g7)) {
				request.setAttribute("g7_e", "false");
			}
			if (!check.checkGiai_5_6Input(g6_1) || !check.checkGiai_5_6Input(g6_2) || !check.checkGiai_5_6Input(g6_3)) {
				request.setAttribute("g6_e", "false");
			}
			if (!check.checkGiai_5_6Input(g5)) {
				request.setAttribute("g5_e", "false");
			}
			if (!check.checkGiai_1_4Input(g4_1) || !check.checkGiai_1_4Input(g4_2) || !check.checkGiai_1_4Input(g4_3)
					|| !check.checkGiai_1_4Input(g4_4)
					|| !check.checkGiai_1_4Input(g4_5) || !check.checkGiai_1_4Input(g4_6) || !check.checkGiai_1_4Input(g4_7)) {
				request.setAttribute("g4_e", "false");
			}
			if (!check.checkGiai_1_4Input(g3_1) || !check.checkGiai_1_4Input(g3_2)) {
				request.setAttribute("g3_e", "false");
			}
			if (!check.checkGiai_1_4Input(g2)) {
				request.setAttribute("g2_e", "false");
			}
			if (!check.checkGiai_1_4Input(g1)) {
				request.setAttribute("g1_e", "false");
			}
			if (!check.checkGiai_dbInput(gdb)) {
				request.setAttribute("gdb_e", "false");
			}

			request.setAttribute("mess", "Giá trị nhập vào chưa đúng");
			ResultSheet rs = new ResultSheet(0, lottery_com, lottery_date, g8, g7, g6, g5, g4, g3, g2, g1, gdb);
			// set value tạm trên form
			setRequestAttributeRsForm(request, rs);

			// quay lại update_resultsheet.jsp để sửa lại data chưa phù hợp
			return "admin/update_resultsheet";
		}
		
		String lottery_date_rstemp = new SimpleDateFormat("dd-MM-yyyy").format(rstemp.getLottery_date());		
		// Không cho thay đổi CTXS và ngày xổ
		if (!lottery_com.equals(rstemp.getLottery_com()) || !(lottery_date_str.equals(lottery_date_rstemp))) {
			request.setAttribute("mess", "Không được thay đổi CTXS và ngày xổ số");
						
			// quay về trang load RS có trong DB
			RequestDispatcher rd = request.getRequestDispatcher("/loadresultsheetdata" 
																	+ "?page=" + activePage);
			rd.forward(request, response);
			return null;
		}

		ResultsheetDAO resultsheetDAO = new ResultsheetDAO();		
		Boolean updateRSData_status = resultsheetDAO.update(lottery_com, lottery_date, g8, g7, g6, g5, g4, g3, g2, g1, gdb);
			
		if (updateRSData_status) {
			mess = "Cập nhật vé dò thành công";
		} else {
			mess = "Cập nhật vé dò thất bại";
		}		
		
		// quay về trang load RS có trong DB
		request.setAttribute("mess", mess);
		RequestDispatcher rd = request.getRequestDispatcher("/loadresultsheetdata" 
																+ "?page=" + activePage);
		rd.forward(request, response);		
		return null;
	}
	
	// delete Result sheet
	@RequestMapping(value = "/deleteRS")
	public String deleteRS(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ServletException  {
		// Setting Unicode cho request (để nhận đúng tiếng Việt khi get parameter), response
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		
		HttpSession session = request.getSession(true);
		// account_role = 1,2 mới dc load trang admin
		if (!AuthController.checkUser(session, response)) {
			
			return null;
		}
				
		@SuppressWarnings("rawtypes")
		HashMap hashMap = (HashMap) session.getAttribute("rsMap");		
		int activePage = (int) hashMap.get("activePage");		
				
		String id = request.getParameter("id");

		// delete Result sheet theo id=lottery_id
		ResultsheetDAO resultsheetDAO = new ResultsheetDAO();		
		Boolean deleteRSData_status = resultsheetDAO.delete(id);		
		
		String mess;
		if (deleteRSData_status) {
			mess = "Xóa vé dò thành công";
		} else {
			mess = "Xóa vé dò thất bại";
		}
		
		// quay về trang load RS có trong DB
		request.setAttribute("mess", mess);		
		RequestDispatcher rd = request.getRequestDispatcher("/loadresultsheetdata" 
																+ "?page=" + activePage);
		rd.forward(request, response);		
		return null;
	}

	// delete Resultsheet List
	@RequestMapping(value = "/deleteRSList")
	public String deleteRSList(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ServletException {
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
		HashMap hashMap = (HashMap) session.getAttribute("rsMap");		
		int activePage = (int) hashMap.get("activePage");
		
		// get idRS
		String[] id = request.getParameterValues("id");
		if (id == null) {
			mess = "Bạn chưa chọn đối tượng để xóa";
			
			// quay về trang load Account có trong DB
			request.setAttribute("mess", mess);		
			RequestDispatcher rd = request.getRequestDispatcher("/loadresultsheetdata" 
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

		// delete Result sheet theo id=lottery_id
		ResultsheetDAO resultsheetDAO = new ResultsheetDAO();		
		Boolean deleteRSListData_status = resultsheetDAO.deleteList(idList);
		
		if (deleteRSListData_status) {
			mess = "Xóa nhiều vé dò thành công";
		} else {
			mess = "Xóa nhiều vé dò thất bại";
		}		
		
		// quay về trang load RS có trong DB
		request.setAttribute("mess", mess);		
		RequestDispatcher rd = request.getRequestDispatcher("/loadresultsheetdata" 
																+ "?page=" + activePage);
		rd.forward(request, response);		
		return null;
	}
	
// Xử lý của ADMIN ----------------------------------------------------------------------------------------------------------
	
	// set rs data cho table
	public static void setRequestAttributeRsForm(HttpServletRequest request, ResultSheet rs) {
		// slit string[] ra từng giải thưởng nhỏ
		String g6[] = rs.getGiai6().split(",");
		String g4[] = rs.getGiai4().split(",");
		String g3[] = rs.getGiai3().split(",");

		// set value hiện trên RS table
		request.setAttribute("lottery_com", rs.getLottery_com());		
		request.setAttribute("lottery_date", new SimpleDateFormat("dd-MM-yyyy").format(rs.getLottery_date()));
		request.setAttribute("g8", rs.getGiai8());
		request.setAttribute("g7", rs.getGiai7());
		request.setAttribute("g6_1", g6[0]);
		request.setAttribute("g6_2", g6[1]);
		request.setAttribute("g6_3", g6[2]);
		request.setAttribute("g5", rs.getGiai5());
		request.setAttribute("g4_1", g4[0]);
		request.setAttribute("g4_2", g4[1]);
		request.setAttribute("g4_3", g4[2]);
		request.setAttribute("g4_4", g4[3]);
		request.setAttribute("g4_5", g4[4]);
		request.setAttribute("g4_6", g4[5]);
		request.setAttribute("g4_7", g4[6]);
		request.setAttribute("g3_1", g3[0]);
		request.setAttribute("g3_2", g3[1]);
		request.setAttribute("g2", rs.getGiai2());
		request.setAttribute("g1", rs.getGiai1());
		request.setAttribute("gdb", rs.getGdb());		
	}

	// check kết quả dò số
	public static String checkResult(HttpServletRequest request, String num, ResultSheet rs) {
		String gt_g8 = "100.000 đ";
		String gt_g7 = "200.000 đ";
		String gt_g6 = "400.000 đ";
		String gt_g5 = "1.000.000 đ";
		String gt_g4 = "3.000.000 đ";
		String gt_g3 = "10.000.000 đ";
		String gt_g2 = "15.000.000 đ";
		String gt_g1 = "30.000.000 đ";
		String gt_gdb_phu = "50.000.000 đ";
		String gt_gdb = "2.000.000.000 đ";
		
		// split string[] ra các giải thưởng nhỏ
		String g6[] = rs.getGiai6().split(",");
		String g4[] = rs.getGiai4().split(",");
		String g3[] = rs.getGiai3().split(",");		

		// giải 2 số
		String g8 = rs.getGiai8();
		// giải 3 số
		String g7 = rs.getGiai7();
		// giải 4 số
		String g6_1=g6[0];
		String g6_2  = g6[1];
		String g6_3  = g6[2];
		String g5  = rs.getGiai5();
		// giải 5 số
		String g4_1  = g4[0];
		String g4_2  = g4[1];
		String g4_3  = g4[2];
		String g4_4  = g4[3];
		String g4_5  = g4[4];
		String g4_6  = g4[5];
		String g4_7  = g4[6];
		String g3_1  = g3[0];
		String g3_2  = g3[1];
		String g2  = rs.getGiai2();
		String g1  = rs.getGiai1();
		// giải 6 số
		String gdb  = rs.getGdb();
		
		if (num.equals(gdb)) {
			String mess = "Bạn đã trúng giải đặc biệt trị giá " + gt_gdb;
			request.setAttribute("mess", mess);
			return "gdb";
		} else if (num.substring(num.length()-5).equals(gdb.substring(gdb.length()-5))) {
			String mess = "Bạn đã trúng giải phụ đặc biệt trị giá " + gt_gdb_phu;
			request.setAttribute("mess", mess);
			return "gdb";
		} else if (num.substring(num.length()-5).equals(g1)) {
			String mess = "Bạn đã trúng giải nhất trị giá " + gt_g1;
			request.setAttribute("mess", mess);
			return "g1";
		} else if (num.substring(num.length()-5).equals(g2)) {
			String mess = "Bạn đã trúng giải nhì trị giá " + gt_g2;
			request.setAttribute("mess", mess);
			return "g2";
		} else if (num.substring(num.length()-5).equals(g3_1)) {
			String mess = "Bạn đã trúng giải ba trị giá " + gt_g3;
			request.setAttribute("mess", mess);
			return "g3_1";
		} else if (num.substring(num.length()-5).equals(g3_2)) {
			String mess = "Bạn đã trúng giải ba trị giá " + gt_g3;
			request.setAttribute("mess", mess);
			return "g3_2";
		} else if (num.substring(num.length()-5).equals(g4_1)) {
			String mess = "Bạn đã trúng giải tư trị giá " + gt_g4;
			request.setAttribute("mess", mess);
			return "g4_1";
		} else if (num.substring(num.length()-5).equals(g4_2)) {
			String mess = "Bạn đã trúng giải tư trị giá " + gt_g4;
			request.setAttribute("mess", mess);
			return "g4_2";
		} else if (num.substring(num.length()-5).equals(g4_3)) {
			String mess = "Bạn đã trúng giải tư trị giá " + gt_g4;
			request.setAttribute("mess", mess);
			return "g4_3";
		} else if (num.substring(num.length()-5).equals(g4_4)) {
			String mess = "Bạn đã trúng giải tư trị giá " + gt_g4;
			request.setAttribute("mess", mess);
			return "g4_4";
		} else if (num.substring(num.length()-5).equals(g4_5)) {
			String mess = "Bạn đã trúng giải tư trị giá " + gt_g4;
			request.setAttribute("mess", mess);
			return "g4_5";
		} else if (num.substring(num.length()-5).equals(g4_6)) {
			String mess = "Bạn đã trúng giải tư trị giá " + gt_g4;
			request.setAttribute("mess", mess);
			return "g4_6";
		} else if (num.substring(num.length()-5).equals(g4_7)) {
			String mess = "Bạn đã trúng giải tư trị giá " + gt_g4;
			request.setAttribute("mess", mess);
			return "g4_7";
		} else if (num.substring(num.length()-4).equals(g5)) {
			String mess = "Bạn đã trúng giải năm trị giá " + gt_g5;
			request.setAttribute("mess", mess);
			return "g5";
		} else if (num.substring(num.length()-4).equals(g6_1)) {
			String mess = "Bạn đã trúng giải sáu trị giá " + gt_g6;
			request.setAttribute("mess", mess);
			return "g6_1";
		} else if (num.substring(num.length()-4).equals(g6_2)) {
			String mess = "Bạn đã trúng giải sáu trị giá " + gt_g6;
			System.out.println("mess = " + mess);
			request.setAttribute("mess", mess);
			return "g6_2";
		} else if (num.substring(num.length()-4).equals(g6_3)) {
			String mess = "Bạn đã trúng giải sáu trị giá " + gt_g6;
			request.setAttribute("mess", mess);
			return "g6_3";
		} else if (num.substring(num.length()-3).equals(g7)) {
			String mess = "Bạn đã trúng giải bảy trị giá " + gt_g7;
			request.setAttribute("mess", mess);
			return "g7";
		} else if (num.substring(num.length()-2).equals(g8)) {
			String mess = "Bạn đã trúng giải tám trị giá " + gt_g8;
			request.setAttribute("mess", mess);
			return "g8";
		} else {
			String mess = "Rất tiếc bạn đã không trúng giải";
			request.setAttribute("mess", mess);
			return null;
		}		
	}
}
