<%@ page contentType="text/html;Charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="" tagdir="/WEB-INF/tags"%>

<html lang="en">
<head>
	<link href="${context}/webroot/style/mainstylesheet.css" rel="stylesheet" type="text/css" media="screen" />
	<meta charset="UTF-8">
	<title></title>
</head>

<body>
	<%@include file="header.jsp"%>
	<h1>Voeg een foto toe via deze pagina</h1>	
	<form method="POST" action="${context}/uploadphoto" enctype="multipart/form-data">
		Naam van de foto:
		<input type="text" name="name" id="name"/>
		<br/>
		Kies een foto:
		<input type="file" name="file" id="file" /> 
		<br/>
		<input type="submit" value="upload" name="upload" id="upload" />
	</form>

	<c:if test="${message != null}">
		<p>${message}</p>
	</c:if>
</body>

</html>
