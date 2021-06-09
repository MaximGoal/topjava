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
<h3><a href="/topjava/index.html">Home</a></h3>
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
        <th scope="col">Edit</th>
        <th scope="col">Delete</th>
    </tr>
    </thead>
    <tbody id="mainTable">
        <c:forEach var="item" items="${mealToList}">
        <tr>
            <c:choose>
            <c:when test="${item.isExcess()}">
                    <td style="color: darkolivegreen">${item.getId()}</td>
                    <td style="color: darkolivegreen">${item.getDescription()}</td>
                    <td style="color: darkolivegreen">${item.getDateTime()}</td>
                    <td style="color: darkolivegreen">${item.getCalories()}</td>
            </c:when>
            <c:otherwise>
                    <td style="color: darkred">${item.getId()}</td>
                    <td style="color: darkred">${item.getDescription()}</td>
                    <td style="color: darkred">${item.getDateTime()}</td>
                    <td style="color: darkred">${item.getCalories()}</td>
            </c:otherwise>
            </c:choose>
                    <td>
                    <form action="/topjava/meals/edit?id=${item.getId()}" method="post">
                        <input type="submit" name="button" value="Edit">
                    </form>
                    </td>
                    <td>
                        <form action="/topjava/meals/delete?id=${item.getId()}" method="post">
                            <input type="submit" name="button" value="Delete">
                        </form>
                    </td>
        </tr>
        </c:forEach>
    </tbody>
</table>
</body>
</html>
