<%@ page contentType="text/html;Charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="" tagdir="/WEB-INF/tags"%>

<html lang="en">
	<head>
		<link href="${context}/webroot/style/mainstylesheet.css" rel="stylesheet" type="text/css" media="screen" />
		<link href="${context}/webroot/style/dropzone.css" rel="stylesheet" type="text/css" media="screen" />
		<meta charset="UTF-8">
			<title></title>
		</head>


		<body>
			<%@include file="header.jsp"%>

			<section id="main">
				<h1>Voeg een foto toe via deze pagina</h1>	
				<script type="text/javascript" src="${context}/webroot/dropzone.js"></script>

				<form action="${context}/uploadphoto" class="dropzone needsclick dz-clickable" id="uploadForm">
				</form>
			</section>
			<c:if test="${message != null}">
				<p>${message}</p>
			</c:if>
			<script type="text/javascript" src="${context}/webroot/dropzoneconfig.js"></script>
		</body>

	</html>
