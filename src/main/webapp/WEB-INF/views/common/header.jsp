<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Header</title>
<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet">
<!-- My Style CSS -->
<link rel="stylesheet" href="static/css/mystyle.css">

</head>

<body>
<div id="header">
	<div class="menu card">
		<nav class="navbar navbar-expand-lg bg-secondary bg-gradient">
			<div class="container-fluid">
				<a class="navbar-brand" href="/Lottery/" title="Go to home page">
					<img alt="Ket qua xo so mien nam" src="static/images/logo.png">
				</a>
				<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
					data-bs-target="#navbarSupportedContent"
					aria-controls="navbarSupportedContent" aria-expanded="false"
					aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				<div class="collapse navbar-collapse" id="navbarSupportedContent">
					<ul class="navbar-nav me-auto mb-2 mb-lg-0">						
						<c:if test="${sessionScope.login_user == null}">
							<li class="nav-item btn-group px-2">
								<a class="nav-link text-white" href="/Lottery/login">Đăng nhập</a>
							</li>									
						</c:if>
						<c:if test="${sessionScope.login_user != null}">
							<li class="nav-item btn-group px-2">
								<a class="nav-link text-white"	href="/Lottery/loaduserhistorydata?history_scope=${sessionScope.login_user.user_mail}">Lịch sử dò số</a>
							</li>									
						</c:if>
						<c:if test="${sessionScope.login_user != null}">
							<li class="nav-item btn-group px-2">
								<a class="nav-link text-white"	href="/Lottery/uploadavatafile">Upload ảnh Avata</a>
							</li>									
						</c:if>
						<c:if test="${sessionScope.login_user != null && sessionScope.login_user.account_role == 0}">
							<li class="nav-item btn-group px-2">
								<a class="nav-link text-white"	href="/Lottery/change_accpassword">Đổi mật khẩu</a>
							</li>									
						</c:if>
						<c:if test="${sessionScope.login_user != null}">
							<li class="nav-item btn-group px-2">
								<a class="nav-link btn-group text-white" href="/Lottery/logout">Đăng xuất</a>
							</li>									
						</c:if>
						<c:if test="${sessionScope.login_user == null}">
							<li class="nav-item btn-group px-2">
								<a class="nav-link text-white" href="/Lottery/register">Đăng ký</a>
							</li>																			
						</c:if>
						<c:if test="${sessionScope.login_user.account_role == '1' || sessionScope.login_user.account_role == '2'}">
							<li class="nav-item btn-group dropdown px-2">
								<a class="nav-link text-white" href="/Lottery/loadaccountdata">Admin</a>
								<a class="nav-link text-white hidden" href="/Lottery/loadresultsheetdata">Admin</a>
								<!-- <a style="color: white;" class="btn dropdown-toggle dropdown-toggle-split" data-bs-toggle="dropdown" aria-expanded="false"></a>
								<ul class="dropdown-menu" aria-labelledby="navbarDropdown">
						        	<li><a class="dropdown-item" href="/Lottery/loadaccountdata">User</a></li>
						        	<li><a class="dropdown-item" href="/Lottery/loadresultsheetdata">Result sheet</a></li>
						        	<li><hr class="dropdown-divider"></li>
						        	<li><a class="dropdown-item" href="#">Something else here</a></li>
						        </ul> -->
							</li>
						</c:if>
					</ul>
					<h6 class="text-warning">${welcomeToUser}</h6>					
					<c:if test="${sessionScope.login_user != null && sessionScope.avata == true}">
						<img style="margin-left: 10px; width: 50px; height: 50px; border-radius: 10px;" alt="" src="static/images/account_img/${sessionScope.login_user.user_mail}.jpg">			
					</c:if>					
					<!-- <form class="d-flex" role="search">
						<input class="form-control me-2" type="search" placeholder="Search"	aria-label="Search">
						<button class="btn btn-outline-info" type="submit">Search</button>
					</form> -->					
				</div>
			</div>
		</nav>
	</div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js"></script>

<!-- xử lý active-item script -->
<script type="text/javascript">
	// add 'active' vào li.navbar-nav
	window.addEventListener('load', function () {
		$(".navbar-nav>li>a[href='" + location.pathname + "']").parent().addClass("active").each(function () {
			$(".navbar-nav>li>a[href='" + $(this).attr("href") + "']").parent().addClass("active")
		});
	})
</script>

</body>

</html>