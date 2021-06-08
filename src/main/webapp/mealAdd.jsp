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
    <title>Add new Meal</title>
</head>
<body>

<h3><a href="index.html">Home</a></h3>
<hr/>
<h1>Add new meal</h1>

<form name="form_create" action="/topjava/meals/add" method="post">
    <label>Description</label>
    <input type="text" placeholder="Description" name="desc">
    </br>
    <label>DateTime</label>
    <input type="text" placeholder="DateTime" name="dateTime">
    </br>
    <label>Calories</label>
    <input type="text" placeholder="Calories" name="calories">
    </br>
    <input type="submit" value="Create">
<%--    <button type="button">--%>
<%--        Create--%>
<%--    </button>--%>
</form>

</body>
</html>
