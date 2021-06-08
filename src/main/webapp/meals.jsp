<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 06.06.2021
  Time: 15:17
  To change this template use File | Settings | File Templates.
--%>
<%@page import="java.util.List"%>
<%@page import="java.util.Arrays"%>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr/>
<h1>Meal list</h1>

<a href="/topjava/meals/add">Add new meal</a>
<form>

</form>

<table style="margin-top: 10px" class="table" border="1" bordercolor="blue">
    <thead class="thead-dark">
    <tr>
        <th scope="col">#</th>
        <th scope="col">Description</th>
        <th scope="col">Date & Time</th>
        <th scope="col">Calories</th>
    </tr>
    </thead>
    <tbody id="mainTable">
        <c:forEach var="item" items="${mealList}" varStatus="loop">
        <tr>
                <td>${loop.index}</td>
                <td>${item.getDescription()}</td>
                <td>${item.getDateTime()}</td>
                <td>${item.getCalories()}</td>
        </tr>
        </c:forEach>
    </tbody>
</table>
</body>
</html>
