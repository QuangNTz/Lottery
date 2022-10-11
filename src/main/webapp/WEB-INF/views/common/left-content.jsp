<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<div class="left-content col-md-2 col-sm-12 mb-3">
		<h6>XỔ SỐ MIỀN NAM</h6>
		<c:if test="${sessionScope.login_user.account_role == '1' || sessionScope.login_user.account_role == '2'}">
			<form action="add_ctxs" class="input-group mt-3" method="post">
				<input  type="text" class="form-control" name="ctxs" placeholder="Tên Công ty XS" value="${ctxs_input}">
				<button class="btn btn-info" type="submit">Thêm CTXS</button>
			</form>
			<p class="form-error" style="color: red; text-align: center;">${ctsx_mess}</p>
		</c:if>
		<ul class="list-group">
			<c:forEach items="${sessionScope.lcList}" var="o">
				<li class="list-group-item"><a href="/Lottery/?id=${o}">${o}</a></li>
			</c:forEach>
		</ul>
	</div>
	