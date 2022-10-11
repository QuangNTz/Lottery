<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
<!-- Required meta tags Login form -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Login Page</title>
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
		<div class="row justify-content-center">
			<div class="col-md-6 contents">
				<div class="row justify-content-center">
					<div class="col-md-12">
						<div class="form-block">
							<div class="mb-4">
								<h3>Sign In</h3>
							</div>
							<!-- Login form -->
							<form class="needs-validation" action="login" method="post" novalidate>
								<p class="form-error" style="color: red">${mess}</p>
								<div class="form-group first mb-4">
									<i class="fas fa-envelope fa-lg me-3 fa-fw"></i>
									<label for="user_mail">Tài khoản</label>										
									<input type="text" id="user_mail" class="form-control" name="user_mail" value="${user_cookie}" placeholder="_____@___.___" required>
									<div class="invalid-feedback" style="text-align: left;">Hãy nhập địa chỉ email.</div>										
								</div>									
								<div class="form-group last mb-4">
									<i class="fas fa-key fa-lg me-3 fa-fw"></i>
									<label for="password">Mật khẩu</label>										
									<input type="password" id="password" class="form-control" name="password" placeholder="" required>
									<div class="invalid-feedback" style="text-align: left;">Hãy nhập mật khẩu.</div>
								</div>
								<div class="d-flex mb-5 align-items-center justify-content-between">
									<label class="control control--checkbox mb-0">
										<span class="caption">Remember me</span>
										<input type="checkbox" name="remember"/>
										<span class="control__indicator"></span>
									</label>
									<span class="ml-auto"><a href="reset_accpassword" class="forgot-pass">Forgot Password</a></span>
								</div>
																
								<div class="d-flex justify-content-center mx-4 mb-3 mb-lg-4">
									<button type="Submit" class="btn btn-block btn-primary btn-lg">Log In</button>
								</div>
								<div class="divider d-flex align-items-center my-4">
					            	<p class="text-center fw-bold mx-3 mb-0 text-muted">or sign in with</p>
					          	</div>
								<!-- <div class="d-flex justify-content-center mx-4 mb-3 mb-lg-4">
							    	<a class="btn btn-outline-dark" 
							    		href="https://accounts.google.com/o/oauth2/auth?
											scope=email profile
											&redirect_uri=http://localhost:8080/Lottery/login-google
											&response_type=code
											&client_id=670916929336-93mpklm59ascig2o5lh2qe91sd45nvat.apps.googleusercontent.com
											&approval_prompt=force" role="button" style="text-transform:none">
							    		<img width="20px" style="margin-bottom:3px; margin-right:5px" alt="Google sign-in" src="https://upload.wikimedia.org/wikipedia/commons/thumb/5/53/Google_%22G%22_Logo.svg/512px-Google_%22G%22_Logo.svg.png" />
							    		Login with Google
								    </a>
							  	</div> -->							  	
							  	<div class="social-login text-center">
							  		<a href="https://accounts.google.com/o/oauth2/auth?
											scope=email profile
											&redirect_uri=http://localhost:8080/Lottery/login-google
											&response_type=code
											&client_id=670916929336-93mpklm59ascig2o5lh2qe91sd45nvat.apps.googleusercontent.com
											&approval_prompt=force" class="google"><span class="icon-google mr-3"></span> 
				                    </a>
				                    <a href="https://www.facebook.com/dialog/oauth?
				                    		client_id=909706986670570&redirect_uri=http://localhost:8080/Lottery/login-facebook" class="facebook">
				                      <span class="icon-facebook mr-3"></span> 
				                    </a>				                    
				              	</div>
							</form>
							<!-- Login form -->
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