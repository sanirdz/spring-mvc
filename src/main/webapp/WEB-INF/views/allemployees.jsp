<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>University Enrollments</title>

<style>
tr:first-child {
	font-weight: bold;
	background-color: #C6C9C4;
}
</style>
<meta name="viewport" content="initial-scale=1, maximum-scale=1">
<link rel='stylesheet' href='webjars/bootstrap/3.3.6/css/bootstrap.min.css'>
</head>


<body>
	<div class="page-header">
		<h2>List of Employas</h2>
	</div>
	<div class="row">
		<div class="col-md-6">
			<table class="table">
				<tr>
					<td>NAME</td>
					<td>Joining Date</td>
					<td>Salary</td>
					<td>SSN</td>
					<td></td>
				</tr>
				<c:forEach items="${employees}" var="employee">
					<tr>
						<td>${employee.name}</td>
						<td>${employee.joiningDate}</td>
						<td>${employee.salary}</td>
						<td><a href="<c:url value='/edit-${employee.ssn}-employee' />">${employee.ssn}</a></td>
						<td><a href="<c:url value='/delete-${employee.ssn}-employee' />">delete</a></td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6">
			<a href="<c:url value='/new' />">Add New Employee</a>
		</div>
	</div>
	<script type="text/javascript" src="webjars/jquery/2.1.4/jquery.min.js"></script>
	<script type="text/javascript" src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>