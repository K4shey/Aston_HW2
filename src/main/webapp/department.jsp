<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Department</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<section>
    <h3><a href="index.jsp">Home</a></h3>
    <hr>
    <h2>${param.action == 'create' ? 'Create department' : 'Edit department'}</h2>
    <jsp:useBean id="department" type="model.Department" scope="request"/>
    <form method="post" action="departments">
        <input type="hidden" name="id" value="${department.id}">
        <dl>
            <dt>Name:</dt>
            <dd><input type="text" value="${department.name}" name="name" required></dd>
        </dl>
        <button type="submit">Save</button>
        <button onclick="window.history.back()" type="button">Cancel</button>
    </form>
</section>
</body>
</html>