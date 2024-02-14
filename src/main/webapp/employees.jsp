<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
    <title>Show All Employees</title>
</head>
<body>
<h3><a href="index.jsp">Home</a></h3>
<hr>
<h2>Employees list</h2>
<table border="1">
    <thead>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Email</th>
        <th>Age</th>
        <th>Citizenship</th>
        <th>Department</th>
        <th colspan=2>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${requestScope.employees}" var="employee">
        <tr>
            <td><c:out value="${employee.id}" /></td>
            <td><c:out value="${employee.name}" /></td>
            <td><c:out value="${employee.email}" /></td>
            <td><c:out value="${employee.age}" /></td>
            <td><c:out value="${employee.citizenship}" /></td>
            <td><c:out value="${employee.department.name}" /></td>
            <td><a href="employees?action=update&id=<c:out value="${employee.id}"/>">Update</a></td>
            <td><a href="employees?action=delete&id=<c:out value="${employee.id}"/>">Delete</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<p><a href="employees?action=create">Add Employee</a></p>
</body>
</html>