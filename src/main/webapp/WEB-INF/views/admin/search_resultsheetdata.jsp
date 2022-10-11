<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Admin Page - RS Search</title>
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
				<a class="nav-link" href="/Lottery/loadaccountdata">User</a>
			</li>
			<li class="nav-item">
				<a class="nav-link active" href="/Lottery/loadresultsheetdata">Result sheet</a>
			</li>				
		</ul>

		<div class="tab-content flex-column justify-content-between">
			<!-- Result sheet -->
			<form action="deleteRSList" method="post">
				<div class="result-sheet">
					<div class="btn-group btn-group-margin">
						<div>
							<a class="btn btn-primary" href="/Lottery/add_resultsheet"><b>+</b> <i class="fa-solid fa-sheet-plastic"></i></a>
							<button type="submit" class="btn btn-danger" onclick="return confirm('Bạn có muốn xóa những vé dò đã chọn?')">Xóa chọn</button>
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
								<th scope="col">Ngày sổ xố</th>
								<th scope="col">CTXS</th>
								<th scope="col">Số giải đặc biệt</th>
								<th scope="col" style="width: 10%">Sửa / Xóa</th>
							</tr>
						</thead>
						<tbody class="text-center align-middle">
							<c:forEach items="${rs_searchList}" var="o">
								<c:set var = "l_date" value = "${o.lottery_date}"/>
								<tr>
									<td>
										<div class="form-check">
											<input type="checkbox" class="form-check-input select-item" name="id" value="${o.lottery_id}">
										</div>
									</td>
									<td><fmt:formatDate pattern = "dd-MM-yyyy" value = "${l_date}"/></td>
									<td>${o.lottery_com}</td>
									<td>${o.gdb}</td>
									<td>
										<div class="btn-group btn-group-justified">
											<a class="btn btn-info" href="/Lottery/update_resultsheet?id=${o.lottery_id}"><i class="fa-solid fa-pen-to-square"></i></a>
											<a class="btn btn-danger" href="/Lottery/deleteRS?id=${o.lottery_id}" 
												onclick="return confirm('Bạn có muốn xóa vé dò ${o.lottery_com} ngày <fmt:formatDate pattern = "dd-MM-yyyy" value = "${l_date}"/>?')">
												<i class="fa-solid fa-trash-can"></i></a>
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</form>
			<div class="pages-nav">
				<h6 style="color: red">${mess}</h6>
				<form class="d-flex" action="search_resultsheetdata">
					<table>
						<tr>
							<td><input type="search" class="form-control me-2" name="rs_search" value="${rs_search}" placeholder="Cty xổ số" aria-label="Search"></td>							
							<td><button type="submit" class="btn btn-outline-info"><i class="fa-solid fa-magnifying-glass"></i></button></td>
						</tr>						
					</table>						
				</form>
				<ul class="pagination justify-content-center">
					<c:forEach begin="1" end="${endPage}" var="i">
						<li class="page-item ${activePage == i ? 'active' : '' }" aria-current="${activePage == i ? 'page' : '' }">
							<a class="page-link" href="/Lottery/search_resultsheetdata?rs_search=${rs_search}&page=${i}">${i}</a>
						</li>
					</c:forEach>
				</ul>
			</div>
			<!-- Result sheet -->
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
<!-- xử lý delete_multi_rs script -->
<script src="static/js/delete_multi_rs.js"></script>
	
</body>

</html>