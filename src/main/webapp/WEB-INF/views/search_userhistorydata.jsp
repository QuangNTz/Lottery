<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">
<title>Search User History</title>
<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet">
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
		<!-- Tab -->
		<ul class="nav nav-tabs">
			
		</ul>

		<div class="tab-content">
			<!-- Account -->
			<form action="deleteHisList" method="post">
				<div class="user-info">
					<div class="btn-group btn-group-margin">
						<div>
							<button type="submit" id="deletes-btn" class="btn btn-danger" onclick="">Xóa chọn</button>
						</div>
						
						<div class="d-flex">
							<!-- <button id="getselected" class="btn btn-light">GetSelected</button>
							<input class="form-control me-2" id="search" type="search" placeholder="Search"	aria-label="Search">
							<button type="submit" class="btn btn-outline-info" onclick="">Search</button> -->
						</div>
						<div>
							<h6 style="color: red">${mess}</h6>
						</div>
					</div>

					<table class="table table-hover">
						<thead class="table-danger text-center align-middle">
							<tr>
								<th scope="col">
									<div class="form-check">
										<input type="checkbox" class="form-check-input select-all" name="select-all" value="">
									</div>
								</th>
								<th scope="col">Ngày tra cứu</th>
								<th scope="col">CTXS</th>
								<th scope="col">Ngày xổ số</th>
								<th scope="col">Số tra cứu</th>
								<th scope="col">Kết quả dò số</th>
								<th scope="col" style="width: 10%">Xóa</th>
							</tr>
						</thead>
						<tbody class="text-center align-middle">
							<c:forEach items="${sessionScope.hisList}" var="o">
								<c:set var = "h_date" value = "${o.history_date}"/>
								<c:set var = "l_date" value = "${o.lottery_date}"/>
								<tr>
									<td>
										<div class="form-check">
											<input type="checkbox" class="form-check-input select-item" name="id" value="${o.history_id}">
										</div>
									</td>
									
									<td><fmt:formatDate pattern = "HH:mm:ss / dd-MM-yyyy" value = "${h_date}"/></td>
									<td>${o.lottery_com}</td>
									<td><fmt:formatDate pattern = "dd-MM-yyyy" value = "${l_date}"/></td>
									<td>${o.history_number}</td>
									<td>${o.history_result}</td>
									<td>
										<div class="btn-group btn-group-justified">
											<a class="btn btn-danger" href="/Lottery/deleteHis?id=${o.history_id}" onclick="return confirm('Bạn có muốn xóa lịch sử dò vé số lúc <fmt:formatDate pattern = "HH:mm:ss / dd-MM-yyyy" value = "${h_date}"/>?')"><i class="fa-solid fa-trash-can"></i></a>
										</div>										
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</form>
			<div class="pages-nav">				
				<form class="d-flex" action="search_userhistorydata">
					<table>
						<tr>					      	
							<td><input type="search" class="form-control me-2" name="usr_search" value="${usr_search}" placeholder="CTXS - kết quả dò số" aria-label="Search"></td>
							<td><button type="submit" class="btn btn-outline-info"><i class="fa-solid fa-magnifying-glass"></i></button></td>
						</tr>
					</table>
				</form>
				
				<ul class="pagination justify-content-center">
					<c:forEach begin="1" end="${endPage}" var="i">
						<li class="page-item ${activePage == i ? 'active' : '' }" aria-current="${activePage == i ? 'page' : '' }">
							<a class="page-link" href="/Lottery/search_userhistorydata?usr_search=${usr_search}&page=${i}">${i}</a>
						</li>
					</c:forEach>
				</ul>				
			</div>
			<!-- Account -->
			
		</div>
	</div>

	<!-- right-content -->
	<jsp:include page="./common/right-content.jsp"></jsp:include>
</div>

<!-- Footer -->
<jsp:include page="./common/footer.jsp"></jsp:include>

<!-- Bootstrap JS -->
<script src="//cdn.bootcss.com/jquery/2.2.1/jquery.min.js"></script>
<script src="//cdn.bootcss.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

<!-- xử lý select-checkbox script -->
<script src="static/js/select-checkbox.js"></script>
<!-- xử lý delete_multi_his script -->
<script src="static/js/delete_multi_his.js"></script>
	
</body>
</html>