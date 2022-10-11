<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Home Page</title>
<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet">
<!-- My Style CSS -->
<link rel="stylesheet" href="static/css/mystyle.css">
<!-- Font Awesome -->
<script src="https://kit.fontawesome.com/a4ec82e01d.js"></script>
<!-- Datepicker -->
<script src="https://code.jquery.com/jquery-3.6.1.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>
<script>
	$(function() {
		$("#datepicker").datepicker({
			dateFormat: "dd-mm-yy",
			showAnim: "slide",
			showButtonPanel: true,
			changeMonth: true,
	      	changeYear: true,
	    });	
	});
</script>
<!-- Datepicker -->

</head>

<body>
<!-- Header -->
<jsp:include page="./common/header.jsp"></jsp:include>

<!-- Body -->
<div id="main-content" class="row justify-content-center">
	<!-- left-content -->
	<jsp:include page="./common/left-content.jsp"></jsp:include>

	<div class="middle-content col-md-7 col-sm-12">
		<form name="myform" action="checkticket" onsubmit="return validate()" method="post">
			<div class="row mt-3 justify-content-evenly">
				<div class="input-group col-md col-sm col-xs mb-3" style="min-width: 250px">
					<span class="input-group-text" id="basic-addon1">Tỉnh:</span>
					<select class="form-select" name="com" aria-label="Default select example">
						<option selected>--Chọn tỉnh--</option>
						<c:forEach items="${sessionScope.lcList}" var="o">
							<option value="${o}" ${com == o ? 'selected' : ''}>${o}</option>
						</c:forEach>
					</select>
				</div>
				<div class="input-group col-md col-sm col-xs mb-3" style="min-width: 250px">
					<span class="input-group-text" id="basic-addon1">Ngày:</span>
					<input type="text" id="datepicker" class="form-control" size="30" name="date" value="${date}" readonly>
					<label class="input-group-btn" for="datepicker">
		                <span class="btn btn-outline-secondary">
		                    <i class="fa-solid fa-calendar-days"></i>
		                </span>
		            </label>
				</div>
				<div class="input-group col-md col-sm col-xs mb-3" style="min-width: 250px">
					<span class="input-group-text" id="basic-addon1">Số vé dò</span>
					<input type="text" class="form-control" name="number" maxlength="6" placeholder="Số cần dò" value="${number}">
				</div>
			</div>
			
			<button type="submit" class="btn btn-info btn-doveso">Dò vé</button>
			<p style="color: red">${mess}</p>
		</form>

		<table class="table table-hover table-bordered ${table_display}">
			<thead class="table-danger text-center align-middle">
				<tr><th colspan="14">${lottery_com} ngày ${lottery_date}</th></tr>
			</thead>
			<tbody class="text-center align-middle">
				<tr>
					<td class="tablerow_title">Giải tám</td>
					<td colspan="12" class="${bingo=='g8' ? 'bingo' : ''}">${g8}</td>
					<td>100,000</td>
				</tr>
				<tr>
					<td>Giải bảy</td>
					<td colspan="12" class="${bingo=='g7' ? 'bingo' : ''}">${g7}</td>
					<td>200,000</td>
				</tr>
				<tr>
					<td>Giải sáu</td>
					<td colspan="3" class="${bingo=='g6_1' ? 'bingo' : ''}">${g6_1}</td>
					<td colspan="6" class="${bingo=='g6_2' ? 'bingo' : ''}">${g6_2}</td>
					<td colspan="3" class="${bingo=='g6_3' ? 'bingo' : ''}">${g6_3}</td>
					<td>400,000</td>
				</tr>
				<tr>
					<td>Giải năm</td>
					<td colspan="12" class="${bingo=='g5' ? 'bingo' : ''}">${g5}</td>
					<td>1,000,000</td>
				</tr>
				<tr>
					<td rowspan="2">Giải tư</td>
					<td colspan="3" class="${bingo=='g4_1' ? 'bingo' : ''}">${g4_1}</td>
					<td colspan="3" class="${bingo=='g4_2' ? 'bingo' : ''}">${g4_2}</td>
					<td colspan="3" class="${bingo=='g4_3' ? 'bingo' : ''}">${g4_3}</td>
					<td colspan="3" class="${bingo=='g4_4' ? 'bingo' : ''}">${g4_4}</td>
					<td rowspan="2">3,000,000</td>
				</tr>
				<tr>
					<td colspan="3" class="${bingo=='g4_5' ? 'bingo' : ''}">${g4_5}</td>
					<td colspan="6" class="${bingo=='g4_6' ? 'bingo' : ''}">${g4_6}</td>
					<td colspan="3" class="${bingo=='g4_7' ? 'bingo' : ''}">${g4_7}</td>
				</tr>
				<tr>
					<td>Giải ba</td>
					<td colspan="6" class="${bingo=='g3_1' ? 'bingo' : ''}">${g3_1}</td>
					<td colspan="6" class="${bingo=='g3_2' ? 'bingo' : ''}">${g3_2}</td>
					<td>10,000,000</td>
				</tr>
				<tr class="text-center align-middle">
					<td>Giải nhì</td>
					<td colspan="12" class="${bingo=='g2' ? 'bingo' : ''}">${g2}</td>
					<td>15,000,000</td>
				</tr>
				<tr>
					<td>Giải nhất</td>
					<td colspan="12" class="${bingo=='g1' ? 'bingo' : ''}">${g1}</td>
					<td>30,000,000</td>
				</tr>
				<tr>
					<td>Đặc biệt</td>
					<td colspan="12" class="gdb ${bingo=='gdb' ? 'bingo' : ''}">${gdb}</td>
					<td>2 tỷ vnđ</td>
				</tr>
			</tbody>
		</table>
	</div>

	<!-- right-content -->
	<jsp:include page="./common/right-content.jsp"></jsp:include>
</div>

<!-- Footer -->
<jsp:include page="./common/footer.jsp"></jsp:include>

<script type="text/javascript">
	'use strict';	
	function validate(){		
		let com = myform.com.value;
		let date = myform.date.value;
		let number = myform.number.value;
		
		if(number === ""){
			alert("Hãy nhập số cần dò")
			return false;
		}
		if(number.length < 5){
			alert("Hãy nhập số vé cần dò có 5 đến 6 chữ số")
			return false;
		}		
		if(com !== "--Chọn tỉnh--" && date === ""){
			alert("Hãy nhập ngày cần dò số")
			return false;
		}		
	}	
</script>

	
</body>
</html>