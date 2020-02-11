<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <meta charset="UTF-8">
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meal</h2>

<form method="post" action="meals">
    <c:set var="formatter" value="${formatter}" />
    <input type="hidden" name="id" value="${mealTo.getId()}">
    Дата и время <input type="datetime-local" name="date" value="${mealTo.getDateTime().format(formatter)}"><br />
    Описание <input type="text" name="description" value="${mealTo.getDescription()}"><br />
    Калории <input type="text" name="calories" value="${mealTo.getCalories()}"><br />
    <input type="submit" name="action" value="${mealTo.getId() == -1 ? 'add' : 'edit'}">
</form>

</body>
</html>