<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>



<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table style="width: 70%" border="2">
    <colgroup>
        <col span="1" style="width: 25%;">
        <col span="1" style="width: 50%;">
        <col span="1" style="width: 25%;">
    </colgroup>

    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>

    <c:forEach var="meal" items="${requestScope.meals}">
        <c:if test="${meal.excess}">
            <tr style="color: red; background: white">
        </c:if>
        <c:if test="${!meal.excess}">
            <tr style="color: green; background: white">
        </c:if>
                <td><c:out value="${meal.dateTimeString}"/></td>
                <td><c:out value="${meal.description}"/></td>
                <td><c:out value="${meal.calories}"/></td>
            </tr>
    </c:forEach>
</table>
</body>
</html>