<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>

<table width="100%">
    <tr bgcolor="grey" style="font-weight: bold">
        <th>Дата и время</th>
        <th>Описание</th>
        <th>Калории</th>
    </tr>
<c:forEach var="mealTo" items="${meals}">
    <tr style="color:${mealTo.isExcess() ? 'red' : 'green'}">
        <c:set var="formatter" value="${formatter}" />
        <td>${mealTo.getDateTime().format(formatter)}</td>
        <td>${mealTo.getDescription()}</td>
        <td>${mealTo.getCalories()}</td>
    </tr>
</c:forEach>
</table>

</body>
</html>