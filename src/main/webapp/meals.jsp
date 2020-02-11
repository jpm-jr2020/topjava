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
<a href="meals?action=add">Добавить</a>
<table width="100%">
    <tr bgcolor="grey" style="font-weight: bold">
        <th>Дата и время</th>
        <th>Описание</th>
        <th>Калории</th>
        <th colspan="2">Действия</th>
    </tr>
<c:forEach var="mealTo" items="${meals}">
    <tr style="color:${mealTo.isExcess() ? 'red' : 'green'}">
        <c:set var="formatter" value="${formatter}" />
        <td>${mealTo.dateTime.format(formatter)}</td>
        <td>${mealTo.description}</td>
        <td>${mealTo.calories}</td>
        <td><form method="post" action="meals">
            <input type="hidden" name="id" value="${mealTo.id}">
            <input type="submit" name="action" value="delete">
        </form></td>
        <td><form method="get" action="meals">
            <input type="hidden" name="id" value="${mealTo.id}">
            <input type="submit" name="action" value="edit">
        </form></td>
    </tr>
</c:forEach>
</table>

</body>
</html>