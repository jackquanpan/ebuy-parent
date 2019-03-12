<%-- author:潘全科 
         2019-01-15 14:11--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath %>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>异常页面</title>
</head>
<body>
<div style="width: 500px;margin-left:auto;margin-right: auto;margin-top:10%">
<img src="<%=basePath%>static/images/error.gif" style="width: 500px">
    <center style="font-size: 30px">找不到页面了，请返回首页<a href="<%=basePath%>pages/Index.jsp">登陆</a></center>
</div>
</body>
</html>
