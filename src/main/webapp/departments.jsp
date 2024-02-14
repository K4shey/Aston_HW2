<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
    <title>Show All Departments</title>
</head>
<body>
<h3><a href="index.jsp">Home</a></h3>
<hr>
<h2>Departments list</h2>
<table border="1">
    <thead>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th colspan=2>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${requestScope.departments}" var="department">
        <tr>
            <td><c:out value="${department.id}" /></td>
            <td><c:out value="${department.name}" /></td>
            <td><a href="departments?action=update&id=<c:out value="${department.id}"/>">Update</a></td>
            <td><a href="departments?action=delete&id=<c:out value="${department.id}"/>">Delete</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<p><a href="departments?action=create">Add Department</a></p>
</body>
</html>
