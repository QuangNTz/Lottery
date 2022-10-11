package controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import model.MyFile;

@Controller
public class UploadFileController {

	@RequestMapping("/uploadavatafile")
	public String uploadAvataForm(Model model) {
		model.addAttribute("myFile", new MyFile());

		return "uploadfile/uploadavatafile";
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public String uploadFile(HttpServletRequest request, HttpServletResponse response, MyFile myFile) {
		MultipartFile multipartFile = myFile.getMultipartFile();
		// get user_mail_id
		String id = request.getParameter("id");        
        @SuppressWarnings("deprecation")
		String imgServer_path = request.getRealPath("/") + "statics\\images\\account_img";
        try {
			File file = new File(imgServer_path, id + ".jpg");
			multipartFile.transferTo(file);
			
			request.setAttribute("mess", "upload ảnh thành công");
		} catch (Exception e) {
			e.printStackTrace();
			
			request.setAttribute("mess", "upload ảnh thất bại");
		}
		
		return "uploadfile/uploadavatafile";
	}
}
