<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Employee</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<section>
    <h3><a href="index.jsp">Home</a></h3>
    <hr>
    <h2>${param.action == 'create' ? 'Create employee' : 'Edit employee'}</h2>
    <jsp:useBean id="employee" type="model.Employee" scope="request"/>
    <form method="post" action="employees">
        <input type="hidden" name="id" value="${employee.id}">
        <dl>
            <dt>Name:</dt>
            <dd><input type="text" value="${employee.name}" name="name" required></dd>
        </dl>
        <dl>
            <dt>Email:</dt>
            <dd><input type="text" value="${employee.email}" size=40 name="email" required></dd>
        </dl>
        <dl>
            <dt>Age:</dt>
            <dd><input type="number" value="${employee.age}" name="age" required></dd>
        </dl>
        <dl>
            <dt>Citizenship:</dt>
            <dd><input type="text" value="${employee.citizenship}" name="citizenship"></dd>
        </dl>
        <dl>
            <dt>Department:</dt>
            <dd>
                <select name="department" required>
                    <c:forEach var="department" items="${departments}">
                        <c:choose>
                            <c:when test="${department.id eq employee.department.id}">
                                <option value="${department.id}" selected>${department.name}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${department.id}">${department.name}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </dd>
        </dl>

        <button type="submit">Save</button>
        <button onclick="window.history.back()" type="button">Cancel</button>
    </form>
</section>
</body>
</html>