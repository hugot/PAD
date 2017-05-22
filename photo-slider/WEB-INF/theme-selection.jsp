<%@ page contentType="text/html;Charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="" tagdir="/WEB-INF/tags"%>

<html lang="en">
<head>
	<meta charset="UTF-8">
	<title></title>
		<link href="${context}/webroot/style/mainstylesheet.css" rel="stylesheet" type="text/css" media="screen" />
</head>
<body>
	<%@include file="header.jsp"%>
	<section id="main-section">
		<section id="selection-bar"> 
			<header><h3>Kies een Thema</h3></header>
			<%-- ----- THEMA's ----- --%>
		            ${themes}
		</section>
	</section>
	<%@include file="footer.jsp"%>
</body>
</html>
