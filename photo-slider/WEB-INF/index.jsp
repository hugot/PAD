<%@ page contentType="text/html;Charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="" tagdir="/WEB-INF/tags"%>
<html lang="en">
<head>
	<link href="webroot/style/mainstylesheet.css" rel="stylesheet" type="text/css" media="screen" />
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
		<input type="submit" name="themes" value="Beheer Thema's"/>
		<input type="submit" name="addPhoto" value="Voeg een foto toe"/>
		</form>
	</div>
</body>
</html>
