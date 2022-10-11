<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
<!-- Required meta tags Login form -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Change password Page</title>
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

	<div class="middle-content col-lg-7 col-md-7 col-sm-12">
		<div class="row justify-content-center">
			<div class="col-md-6 contents">
				<div class="row justify-content-center">
					<div class="col-md-12">
						<div class="form-block">
							<div class="mb-4">
								<h3>Thay đổi mật khẩu tài khoản</h3>
							</div>
							<!-- Change mk form -->
							<form class="needs-validation" action="change_accpassword" method="post" novalidate>
								<p class="form-error" style="color: red">${mess}</p>
								<div class="form-group first mb-4">
									<i class="fas fa-envelope fa-lg me-3 fa-fw"></i>
									<label for="user_mail">Tài khoản</label>										
									<input type="text" id="user_mail" class="form-control" name="user_mail" value="${sessionScope.login_user.user_mail}" placeholder="_____@___.___" required readonly>
									<div class="invalid-feedback" style="text-align: left;">Hãy nhập địa chỉ email.</div>										
								</div>
								<div class="form-group last mb-4">
									<i class="fas fa-key fa-lg me-3 fa-fw"></i>
									<label for="password">Mật khẩu cũ</label>										
									<input type="password" id="password" class="form-control" name="oldpassword" placeholder="Hãy nhập mật khẩu cũ" required>
									<div class="invalid-feedback" style="text-align: left;">Hãy nhập mật khẩu cũ.</div>
								</div>
								<div class="form-group last mb-4">
									<i class="fa-solid fa-lock"></i>
									<label for="re_password">Mật khẩu mới</label>										
									<input type="password" id="re_password" class="form-control" name="newpassword" placeholder="Hãy nhập mật khẩu mới" required>
									<div class="invalid-feedback" style="text-align: left;">Hãy nhập mật khẩu mới.</div>
								</div>
								<div class="form-group last mb-4">
									<i class="fa-solid fa-lock"></i>
									<label for="re_password">Nhập lại Mật khẩu mới</label>										
									<input type="password" id="re_password" class="form-control" name="re_newpassword" placeholder="Hãy nhập lại mật khẩu mới" required>
									<div class="invalid-feedback" style="text-align: left;">Hãy nhập lại mật khẩu mới.</div>
								</div>
																
								<div class="d-flex justify-content-center mx-4 mb-3 mb-lg-4">
									<button type="Submit" class="btn btn-block btn-primary btn-lg">Change</button>
								</div>									
								
							</form>
							<!-- Change mk form -->
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