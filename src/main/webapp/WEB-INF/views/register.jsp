<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<!-- Required meta tags Login form -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Register Page</title>
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
<jsp:include page="./common/header.jsp"></jsp:include>

<!-- Body -->
<div id="main-content" class="row justify-content-center">
	<!-- left-content -->
	<jsp:include page="./common/left-content.jsp"></jsp:include>

	<div class="middle-content col-md-7 col-sm-12">
			<div class="row d-flex justify-content-center align-items-center h-100">
				<div class="col-lg-12 col-xl-11">
					<div class="text-black" >
						<div class="p-md-5">
							<div class="row justify-content-center">
								<div class="col-md-10 col-lg-8 col-xl-6 order-2 order-lg-1">
									<p class="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4">Sign up</p>										
									<!-- Register form -->
									<form class="mx-1 mx-md-4 needs-validation" action="register" method="post" novalidate>
										<p class="form-error" style="color: red">${mess}</p>
										<div class="d-flex flex-row align-items-center mb-4">
											<i class="fas fa-user fa-lg me-3 fa-fw"></i>
											<div class="form-outline flex-fill mb-0 form-floating">
												<input type="text" class="form-control" id="floatingInputValue"	name="user_name" value="${name}" placeholder="Nguyễn Văn A" required/>
												<label for="floatingInputValue">Tên : Nguyễn Văn A</label>
												<div class="invalid-feedback" style="text-align: left;">Hãy nhập họ tên.</div>
											</div>
										</div>
										<div class="d-flex flex-row align-items-center mb-4">
											<i class="fas fa-envelope fa-lg me-3 fa-fw"></i>
											<div class="form-outline flex-fill mb-0 form-floating">
												<input type="text" id="floatingInput" class="form-control" name="user_mail" value="${usr}" placeholder="abc@gmail.com" required/>
												<label for="floatingInput">Tài khoản : abc@gmail.com</label>
												<div class="invalid-feedback" style="text-align: left;">Hãy nhập địa chỉ email.</div>
											</div>
										</div>
										<div class="d-flex flex-row align-items-center mb-4">
											<i class="fas fa-key fa-lg me-3 fa-fw"></i>
											<div class="form-outline flex-fill mb-0 form-floating">
												<input type="text" id="form3Example4c" class="form-control" name="password" placeholder="" value="Mật khẩu được tạo ngẫu nhiên" required readonly/>
												<label for="floatingInput" style="color: red">Sẽ gửi đến địa chỉ mail đăng ký</label>
												<div class="invalid-feedback" style="text-align: left;">Hãy nhập mật khẩu.</div>
											</div>
										</div>
										<!--<div class="d-flex flex-row align-items-center mb-4">
											<i class="fas fa-key fa-lg me-3 fa-fw"></i>
											<div class="form-outline flex-fill mb-0 form-floating">
												<input type="password" id="form3Example4cd"	class="form-control" placeholder="Repeat your password" required/>
												<label for="floatingInput">Repeat your password</label>
												<div class="invalid-feedback" style="text-align: left;">Please Re-enter password.</div>
											</div>
										</div> -->
										<div class="d-flex flex-row align-items-center mb-4">
											<i class="fas fa-solid fa-phone fa-lg me-3 fa-fw"></i>
											<div class="form-outline flex-fill mb-0 form-floating">
												<input type="text" id="form3Example5c" class="form-control" name="phone_number" placeholder="----.---.---" required/>
												<label for="floatingInput">Số điện thoại : 0123.123.123</label>
												<div class="invalid-feedback" style="text-align: left;">Hãy nhập số điện thoại.</div>
											</div>
										</div>

										<div class="d-flex justify-content-center mx-4 mb-3 mb-lg-4">
											<button type="Submit" class="btn btn-primary btn-lg">Register</button>
										</div>
									</form>
									<!-- Register form -->
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
	<jsp:include page="./common/right-content.jsp"></jsp:include>
</div>

<!-- Footer -->
<jsp:include page="./common/footer.jsp"></jsp:include>

<!-- xử lý validate input form Bootstrap script -->
<script src="static/js/validate_inputform.js"></script>

<!-- Required JS Login form -->
<script src="static/js/jquery-3.3.1.min.js"></script>
<script src="static/js/popper.min.js"></script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/main.js"></script>
	
</body>

</html>