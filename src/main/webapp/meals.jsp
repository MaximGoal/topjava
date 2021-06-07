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
<a ></a>
    <c:forEach var="item" items="${mealList}">
       <li>
           <c:out value="${item.toString()}"/>
       </li>
    </c:forEach>
</body>
</html>
