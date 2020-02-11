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
        <td>${mealTo.getDateTime().format(formatter)}</td>
        <td>${mealTo.getDescription()}</td>
        <td>${mealTo.getCalories()}</td>
        <td><a href="meals?action=delete&id=${mealTo.getId()}">Удалить</a></td>
        <td><a href="meals?action=edit&id=${mealTo.getId()}">Изменить</a></td>
    </tr>
</c:forEach>
</table>

</body>
</html>