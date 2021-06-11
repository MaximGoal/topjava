<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 07.06.2021
  Time: 23:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update Meal</title>
</head>
<body>

<h3><a href="/topjava/index.html">Home</a></h3>
<hr/>
<h1>Update meal ${meal.getDescription()} at ${meal.getDate()}</h1>

<form name="form_create" action="/topjava/meals/edit" method="post">
    <input type="hidden" placeholder="id" name="id"  value="${meal.getId()}">
    <table>
        <tbody>
            <tr>
                <td><label>Description</label></td>
                <td><input type="text" placeholder="Description" name="desc" value="${meal.getDescription()}"></td>
            </tr>
            <tr>
                <td><label>DateTime</label></td>
                <td><input type="text" placeholder="DateTime" name="dateTime" value="yyyy-MM-dd hh:mm a"></td>
            </tr>
            <tr>
                <td><label>Calories</label></td>
                <td><input type="text" placeholder="Calories" name="calories"  value="${meal.getCalories()}"></td>
            </tr>
            <tr>
                <td><input type="submit" value="Update"></td>
            </tr>
        </tbody>
    </table>

<%--    <label>Description</label>--%>
<%--    <input type="text" placeholder="Description" name="desc">--%>
<%--    </br>--%>
<%--    <label>DateTime</label>--%>
<%--    <input type="text" placeholder="DateTime" name="dateTime">--%>
<%--    </br>--%>
<%--    <label>Calories</label>--%>
<%--    <input type="text" placeholder="Calories" name="calories">--%>
<%--    </br>--%>
<%--    <input type="submit" value="Create">--%>
<%--    <button type="button">--%>
<%--        Create--%>
<%--    </button>--%>
</form>

</body>
</html>
