<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
    <title>Show All Employees</title>
</head>
<body>
<table border="1">
    <thead>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Email</th>
        <th>Age</th>
        <th>Citizenship</th>
        <th>Department</th>
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
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>