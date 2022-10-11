<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Admin Page- Update RS</title>
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
				<a class="nav-link active" href="#">Update Result sheet</a>
			</li>
		</ul>

		<div class="tab-content">
			<!-- Add Result sheet form -->
			<form class="needs-validation" action="update_resultsheet" method="post" novalidate>
				<div class="input-group mb-3" style="min-width: 250px">
					<span class="input-group-text" id="basic-addon1">Tỉnh:</span>
					<select	class="form-select" style="color: blue" name="lottery_com" aria-label="Default select example" required>
						<option selected disabled value="">--Chọn tỉnh--</option>
						<c:forEach items="${sessionScope.lcList}" var="o">
							<option value="${o}" ${lottery_com == o ? 'selected' : 'disabled'}>${o}</option>
						</c:forEach>
					</select>
				</div>
				<div class="input-group mb-3" style="min-width: 250px">
					<span class="input-group-text" id="basic-addon1">Ngày:</span>
					<input type="text" class="form-control"  style="color: blue" name="lottery_date" value="${lottery_date}" required readonly>
				</div>
				
				<button type="submit" class="btn btn-info btn-addRS">Cập nhật</button>

				<table class="table table-hover table-bordered">
					<thead class="table-danger text-center align-middle">
						<tr>
							<th colspan="14"><p class="form-error" style="color: red">${mess}</p></th>
						</tr>
					</thead>
					<tbody class="text-center align-middle">
						<tr>
							<th class="tablerow_title" ${g8_e == 'false' ? 'style="color: red"' : ''}>Giải tám</th>
							<td colspan="12">
								<input type="text" class="form-control"	name="g8" placeholder="--" value="${g8}" required>
							</td>
							<td>100,000</td>
						</tr>
						<tr>
							<th ${g7_e == 'false' ? 'style="color: red"' : ''}>Giải bảy</th>
							<td colspan="12">
								<input type="text" class="form-control" name="g7" placeholder="---" value="${g7}" required>
							</td>
							<td>200,000</td>
						</tr>
						<tr>
							<th ${g6_e == 'false' ? 'style="color: red"' : ''}>Giải sáu</th>
							<td colspan="3">
								<input type="text" class="form-control"	name="g6_1" placeholder="----" value="${g6_1}" required>
							</td>
							<td colspan="6">
								<input type="text" class="form-control"	name="g6_2" placeholder="----" value="${g6_2}" required>
							</td>
							<td colspan="3">
								<input type="text" class="form-control"	name="g6_3" placeholder="----" value="${g6_3}" required>
							</td>
							<td>400,000</td>
						</tr>
						<tr>
							<th ${g5_e == 'false' ? 'style="color: red"' : ''}>Giải năm</th>
							<td colspan="12">
								<input type="text" class="form-control"	name="g5" placeholder="----" value="${g5}" required>
							</td>
							<td>1,000,000</td>
						</tr>
						<tr>
							<th rowspan="2" ${g4_e == 'false' ? 'style="color: red"' : ''}>Giải tư</th>
							<td colspan="3">
								<input type="text" class="form-control"	name="g4_1" placeholder="-----" value="${g4_1}" required>
							</td>
							<td colspan="3">
								<input type="text" class="form-control"	name="g4_2" placeholder="-----" value="${g4_2}" required>
							</td>
							<td colspan="3">
								<input type="text" class="form-control" name="g4_3" placeholder="-----" value="${g4_3}" required>
							</td>
							<td colspan="3">
								<input type="text" class="form-control"	name="g4_4" placeholder="-----" value="${g4_4}" required>
							</td>
							<td rowspan="2">3,000,000</td>
						</tr>
						<tr>
							<td colspan="3">
								<input type="text" class="form-control"	name="g4_5" placeholder="-----" value="${g4_5}" required>
							</td>
							<td colspan="6">
								<input type="text" class="form-control"	name="g4_6" placeholder="-----" value="${g4_6}" required>
							</td>
							<td colspan="3">
								<input type="text" class="form-control"	name="g4_7" placeholder="-----" value="${g4_7}" required>
							</td>
						</tr>
						<tr>
							<th ${g3_e == 'false' ? 'style="color: red"' : ''}>Giải ba</th>
							<td colspan="6">
								<input type="text" class="form-control"	name="g3_1" placeholder="-----" value="${g3_1}" required>
							</td>
							<td colspan="6">
								<input type="text" class="form-control"	name="g3_2" placeholder="-----" value="${g3_2}" required>
							</td>
							<td>10,000,000</td>
						</tr>
						<tr>
							<th ${g2_e == 'false' ? 'style="color: red"' : ''}>Giải nhì</th>
							<td colspan="12">
								<input type="text" class="form-control"	name="g2" placeholder="-----" value="${g2}" required>
							</td>
							<td>15,000,000</td>
						</tr>
						<tr>
							<th ${g1_e == 'false' ? 'style="color: red"' : ''}>Giải nhất</th>
							<td colspan="12">
								<input type="text" class="form-control"	name="g1" placeholder="-----" value="${g1}" required>
							</td>
							<td>30,000,000</td>
						</tr>
						<tr>
							<th ${gdb_e == 'false' ? 'style="color: red"' : ''}>Đặc biệt</th>
							<td colspan="12" style="font-weight: 800; color: red">
								<em><input type="text" class="form-control" name="gdb" placeholder="------" value="${gdb}" required></em>
							</td>
							<td>2 tỷ vnđ</td>
						</tr>
					</tbody>
				</table>
			</form>
			<!-- Add Result sheet form -->
		</div>
	</div>

	<!-- right-content -->
	<jsp:include page="../common/right-content.jsp"></jsp:include>
</div>

<!-- Footer -->
<jsp:include page="../common/footer.jsp"></jsp:include>

<!-- xử lý validate input form Bootstarp script -->
<script src="static/js/validate_inputform.js"></script>
	
</body>
</html>