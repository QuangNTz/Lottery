<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Admin Page - Account Search</title>
<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet">
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
		<ul class="nav nav-tabs">
			<li class="nav-item">
				<a class="nav-link active" href="/Lottery/loadaccountdata">User</a>
			</li>
			<li class="nav-item">
				<a class="nav-link" href="/Lottery/loadresultsheetdata">Result sheet</a>
			</li>
		</ul>

		<div class="tab-content flex-column justify-content-between">				
			<!-- Account -->
			<form action="deleteAccList" method="post">
				<div class="user-info">
					<div class="btn-group btn-group-margin">
						<div>
							<a class="btn btn-primary" href="/Lottery/add_account"><i class="fa-solid fa-user-plus"></i></a>
							<button type="submit" class="btn btn-danger" onclick="return confirm('Bạn có muốn xóa những tài khoản đã chọn?')">Xóa chọn</button>								
						</div>
						<div class="d-flex">
							<!-- <button id="getselected" class="btn btn-light">GetSelected</button>
							<input class="form-control me-2" id="search" type="search" placeholder="Search"	aria-label="Search">
							<button type="button" class="btn btn-outline-info" onclick="">Search</button> -->
						</div>
					</div>

					<table class="table table-hover">
						<thead class="table-danger text-center align-middle">
							<tr>
								<th scope="col">
									<div class="form-check">
										<input type="checkbox" class="form-check-input select-all" name="select-all">
									</div>
								</th>
								<th scope="col">Tài khoản</th>
								<th scope="col">Mật mã</th>
								<th scope="col">Vai trò (role)</th>
								<th scope="col">Tên</th>
								<th scope="col">Số điện thoại</th>
								<th scope="col">Lịch sử dò số</th>
								<th scope="col">Trạng thái</th>
								<th scope="col" style="width: 10%">Sửa / Xóa</th>
							</tr>
						</thead>
						<tbody class="text-center align-middle">
							<c:forEach items="${acc_searchList}" var="o">
								<tr>
									<td>
										<div class="form-check">
											<input type="checkbox" class="form-check-input select-item" name="id" value="${o.user_mail}" ${o.account_role == 1 ? 'disabled' : ''}>
										</div>
									</td>
									<td>${o.user_mail}</td>
									<td>*****</td>
									<td>
										<c:choose>
											<c:when test = "${o.account_role == 1}">
									           admin1
									        </c:when>
									        <c:when test = "${o.account_role == 2}">
									           admin2
									        </c:when>
									        <c:when test = "${o.account_role == 0}">
									           user
									        </c:when>
								      	</c:choose>
									</td>
									<td>${o.user_name}</td>
									<td>${o.phone_number}</td>
									<td>
										<a class="btn btn-info" href="/Lottery/loaduserhistorydata?history_scope=${o.user_mail}"><i class="fa-solid fa-window-maximize"></i></a>																		
									</td>
									<td>
										<c:choose>
											<c:when test = "${o.user_status == 1}">
									           active
									        </c:when>
									        <c:when test = "${o.user_status == 0}">
									           inactive
									        </c:when>
								      	</c:choose>
									</td>
									<td>											
										<c:if test="${o.account_role != sessionScope.login_user.account_role && o.account_role != 1}">
											<div class="btn-group btn-group-justified">
												<a class="btn btn-info" href="/Lottery/update_account?id=${o.user_mail}"><i class="fa-solid fa-pen-to-square"></i></a>
												<c:if test="${o.user_status == 1}">
													<a class="btn btn-danger" href="/Lottery/deleteAcc?id=${o.user_mail}" onclick="return confirm('Bạn có muốn xóa account ${o.user_mail}?')"><i class="fa-solid fa-user-xmark"></i></a>
												</c:if>
											</div>
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</form>
			<div class="pages-nav">
				<h6 style="color: red">${mess}</h6>				
				
				<form class="d-flex" action="search_accountdata">
					<table>
						<tr>
							<td><input type="search" class="form-control me-2" name="usr_search" value="${usr_search}" placeholder="Tên tkhoản - Số đthoại" aria-label="Search"></td>								
							<td><button type="submit" class="btn btn-outline-info"><i class="fa-solid fa-magnifying-glass"></i></button></td>
						</tr>						
					</table>
				</form>
				
				<ul class="pagination justify-content-center">
					<c:forEach begin="1" end="${endPage}" var="i">
						<li	class="page-item ${activePage == i ? 'active' : '' }" aria-current="${activePage == i ? 'page' : '' }">
							<a class="page-link" href="/Lottery/search_accountdata?usr_search=${usr_search}&page=${i}">${i}</a>
						</li>
					</c:forEach>
				</ul>
			</div>				
			<!-- Account -->
		</div>
	</div>

	<!-- right-content -->
	<jsp:include page="../common/right-content.jsp"></jsp:include>
</div>

<!-- Footer -->
<jsp:include page="../common/footer.jsp"></jsp:include>

<!-- Bootstrap JS -->
<script src="//cdn.bootcss.com/jquery/2.2.1/jquery.min.js"></script>
<script src="//cdn.bootcss.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

<!-- xử lý select-checkbox script -->
<script src="static/js/select-checkbox.js"></script>
<!-- xử lý delete_multi_acc script -->
<script src="static/js/delete_multi_acc.js"></script>

</body>

</html>