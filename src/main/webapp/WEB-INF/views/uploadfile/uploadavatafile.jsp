<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">
<title>Upload avata</title>
<link href="https://fonts.googleapis.com/css?family=Roboto:300,400&display=swap" rel="stylesheet">
<link rel="stylesheet" href="static/fonts/icomoon/style.css">
<link rel="stylesheet" href="static/css/owl.carousel.min.css">
<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet">
<!-- Login Style CSS -->
<link rel="stylesheet" href="static/css/style.css">
<!-- My Style CSS -->
<link rel="stylesheet" href="static/css/mystyle.css">
<!-- Font Awesome -->
<script src="https://kit.fontawesome.com/a4ec82e01d.js"></script>

</head>

<body>
<!-- Header -->
<jsp:include page="../common/header.jsp"></jsp:include>

<!-- Body -->
<div id="main-content" class="row justify-content-center">
	<!-- left-content -->
	<jsp:include page="../common/left-content.jsp"></jsp:include>

	<div class="middle-content col-md-7 col-sm-12">
		<div class="row d-flex justify-content-center align-items-center h-100">
				<div class="col-lg-12 col-xl-11">
					<div class="text-black" >
						<div class="p-md-5">
							<div class="row justify-content-center">
								<div class="col-md-10 col-lg-8 col-xl-6 order-2 order-lg-1">
									<p class="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4">Upload ảnh avata</p>										
									<!-- Uploadfile form -->
									<form id="formUploadFile" class="mx-1 mx-md-4 needs-validation" method="POST" action="uploadFile?id=${sessionScope.login_user.user_mail}" enctype="multipart/form-data" novalidate>
										<p class="form-error" style="color: red">${mess}</p>										
										<div class="form-group first mb-4">
											<i class="fa-solid fa-file-export"></i>
											<label for="multipartFile">Chọn file cần upload .jpg (kt 100x100) </label>										
											<input type="file" id="multipartFile" class="form-control" name="multipartFile" required>											
											<div class="invalid-feedback" style="text-align: left;">Hãy chọn file cần upload.</div>										
										</div>										
										<div class="d-flex justify-content-center mx-4 mb-3 mb-lg-4">
											<button type="Submit" class="btn btn-primary btn-lg">Upload</button>
										</div>
									</form>
									<!-- Uploadfile form -->									
								</div>
								<div class="col-md-10 col-lg-4 col-xl-6 d-flex align-items-center order-1 order-lg-2">
									<img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-registration/draw1.webp"
										class="img-fluid" alt="Sample image">
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
	</div>

	<!-- right-content -->
	<jsp:include page="../common/right-content.jsp"></jsp:include>
</div>

<!-- Footer -->
<jsp:include page="../common/footer.jsp"></jsp:include>

<!-- xử lý validate input form Bootstrap script -->
<script src="static/js/validate_inputform.js"></script>

<!-- Required JS Login form -->
<script src="static/js/jquery-3.3.1.min.js"></script>
<script src="static/js/popper.min.js"></script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/main.js"></script>
	
</body>
</html>