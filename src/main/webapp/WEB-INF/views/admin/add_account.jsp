<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Admin Page</title>
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
		<!-- Tab -->
		<ul class="nav nav-tabs">
			<li class="nav-item">
				<a class="nav-link active" href="/Lottery/loadaccountdata">Add User</a>
			</li>
		</ul>
		<div class="tab-content">
			<!-- Add User form -->
			<form class="needs-validation" action="add_account" method="post" novalidate>
				<div class="btn-group">
					<div>
						<button type="submit" class="btn btn-info btn-addUser">Thêm mới</button>
						<input type="reset" class="btn btn-warning" value="Reset">
						<a class="btn btn-warning" href="/Lottery/loadaccountdata">Hủy</a>
					</div>
				</div>

				<table class="table table-hover" style="width: 70%">
					<thead class="table-danger text-center align-middle">
						<tr class="text-center align-middle">
							<th colspan="14">
								<p class="form-error" style="color: red">${mess}</p>
							</th>
						</tr>
					</thead>
					<tbody class="text-center align-middle">
						<tr>
							<td class="tablerow_title">Tài khoản</td>
							<td colspan="12">
								<input type="text" class="form-control" name="user_mail" placeholder="_____@___.___" value="${usr}" required>
								<div class="invalid-feedback" style="text-align: left;">Hãy nhập địa chỉ email.</div>
							</td>
						</tr>
						<tr>
							<td>Mật mã <i class="fa-solid fa-eye-slash" id ="toggleBtn" style = "margin-left: 20px; margin-right: -20px; cursor: pointer;" onclick="togglePassword()"></i></td>
							<td colspan="12">
								<input type="password" class="form-control" id="upass" name="password" placeholder="---" value="${pwd}" required>
								<div class="invalid-feedback" style="text-align: left;">Hãy nhập mật mã.</div>
							</td>
						</tr>
						<tr>
							<td>Vai trò (role)</td>
							<td colspan="12">
								<select class="form-select" name="account_role"	aria-label="Default select example">
									<option value="0" selected>user</option>
									<option disabled value="1">admin1</option>
									<option value="2" ${role=='2' ? 'selected' : '' }>admin2</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>Tên</td>
							<td colspan="12">
								<input type="text" class="form-control" name="user_name" placeholder="Nguyễn Văn A" value="${name}" required>
								<div class="invalid-feedback" style="text-align: left;">Hãy nhập họ tên.</div>
							</td>
						</tr>
						<tr>
							<td>Số điện thoại</td>
							<td colspan="12">
								<input type="text" class="form-control" name="phone_number" placeholder="----.---.---" value="${fnum}" required>
								<div class="invalid-feedback" style="text-align: left;">Hãy nhập số điện thoại.</div>
							</td>
						</tr>
						<tr>
							<td>Trạng thái hoạt động</td>
							<td colspan="12">
								<select class="form-select" name="user_status" aria-label="Default select example">
									<option value="1" selected>active</option>
									<option value="0" ${status=='0' ? 'selected' : '' }>inactive</option>
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

<script type="text/javascript">	
	function togglePassword() {
		var upass = document.getElementById('upass');
		var toggleBtn = document.getElementById('toggleBtn');
		if(upass.type == "password"){
			upass.type = "text";
			toggleBtn.className = "fa-solid fa-eye";
		} else {
			upass.type = "password";
			toggleBtn.className = "fa-solid fa-eye-slash";
		}
	}
</script>
	
</body>

</html>