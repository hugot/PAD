<%@ page contentType="text/html;Charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="" tagdir="/WEB-INF/tags"%>
<html lang="en">
<head>
	<link href="web/mainstylesheet.css" rel="stylesheet" type="text/css" media="screen" />
	<meta charset="UTF-8">
		<title>Photoslider backend</title>
</head>

<body>
	<%@include file="header.jsp"%>
	<h1> Welkom </h1>
	<div id="main">
		<p>
			Dit is de photoslider backend	
		</p>
		<form action="${pageContext.request.contextPath}/processview" method="post">
		<input type="submit" name="button1" value="button 1"/>
		</form>
		<c:if test="${greeting != null}">
			<h2>${greeting}</h2>
		</c:if>
	</div>
</body>
</html>
