<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Admin Page - Update Account</title>
<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet">
<!-- My Style CSS -->
<link rel="stylesheet" href="static/css/mystyle.css">

</head>

<body>
<!-- Header -->
<jsp:include page="../common/header.jsp"></jsp:include>

<!-- Body -->
<div id="main-content" class="row justify-content-center">
	<!-- left-content -->
	<jsp:include page="../common/left-content.jsp"></jsp:include>

	<div class="middle-content col-md-7 col-sm-12">
		<!-- Tab -->
		<ul class="nav nav-tabs">								
			<li class="nav-item">
				<a class="nav-link active" href="#">Update User</a>
			</li>
		</ul>
		
		<div class="tab-content">
			<!-- Add User form -->
			<form class="needs-validation" action="update_account" method="post" novalidate>
				<button type="submit" class="btn btn-info btn-addUser">Cập nhật</button>
				<a class="btn btn-warning" href="/Lottery/resetAccPassword?id=${usr}">Reset mật khẩu</a>
				
				<table class="table table-hover">
					<thead class="table-danger text-center align-middle">							
						<tr class="text-center align-middle">
							<th colspan="14"><p class="form-error" style="color: red">${mess}</p></th>
						</tr>
					</thead>
					<tbody class="text-center align-middle">
						<tr>
							<td class="tablerow_title">Tài khoản</td>
							<td colspan="12">
								<input type="text" style="color: blue" class="form-control" name="user_mail" placeholder="_____@___.___" value="${usr}" required readonly>
								<div class="invalid-feedback" style="text-align: left;">Hãy nhập địa chỉ email.</div>
							</td>
						</tr>
						<tr>
							<td>Mật mã</td>
							<td colspan="12">
								<input type="text" class="form-control" name="" placeholder="" value="*****" required readonly>
								<div class="invalid-feedback" style="text-align: left;">Hãy nhập mật mã.</div>
							</td>
						</tr>
						<tr>
							<td>Vai trò (role)</td>
							<td colspan="12">
								<select class="form-select" name="account_role" aria-label="Default select example">
									<option ${role == '2' ? 'disabled' : ''} value="0" selected>user</option>
									<option disabled value="1">admin1</option>
									<option value="2" ${role == '2' ? 'selected' : ''}>admin2</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>Tên</td>
							<td colspan="12">
								<input type="text" class="form-control"	name="user_name" placeholder="Nguyễn Văn A" value="${name}"	required>
								<div class="invalid-feedback" style="text-align: left;">Hãy nhập họ tên.</div>
							</td>
						</tr>
						<tr>
							<td>Số điện thoại</td>
							<td colspan="12">
								<input type="text" class="form-control"	name="phone_number" placeholder="0123.123.123" value="${fnum}" required>
								<div class="invalid-feedback" style="text-align: left;">Hãy nhập số điện thoại.</div>
							</td>
						</tr>
						<tr>
							<td>Trạng thái</td>
							<td colspan="12">
								<select class="form-select"	name="user_status" aria-label="Default select example">
									<option value="1" selected>active</option>
									<option value="0" ${status == '0' ? 'selected' : ''}>inactive</option>
								</select>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
			<!-- Add User form -->
		</div>
	</div>

	<!-- right-content -->
	<jsp:include page="../common/right-content.jsp"></jsp:include>
</div>

<!-- Footer -->
<jsp:include page="../common/footer.jsp"></jsp:include>

<!-- xử lý validate input form Bootstrap script -->
<script src="static/js/validate_inputform.js"></script>
	
</body>

</html>