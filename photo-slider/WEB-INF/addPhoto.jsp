<%@ page contentType="text/html;Charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="" tagdir="/WEB-INF/tags"%>

<html lang="en">
	<head>
		<link href="${context}/webroot/style/mainstylesheet.css" rel="stylesheet" type="text/css" media="screen" />
		<link href="${context}/webroot/style/dropzone.css" rel="stylesheet" type="text/css" media="screen" />
		<link href="${context}/webroot/style/ThemeSettings.css" rel="stylesheet" type="text/css" media="screen" />
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

				<form method="POST" action="${context}/addsessiontotheme">
					<input type="text" value="${themeId}" name="themeId" id="themeId"/>
					<input type="text" value="${sessionId}" name="sessionId" id="sessionId"/>
					<input type="text" value="hallo" name="addtotheme" id="addtotheme"/>
					<input type="submit" value="upload" name="upload" id="upload" />
				</form>
				
			</section>
			<c:if test="${sessionId != null}">
				<p>whaddup ${sessionId}</p>
			</c:if>
			<script type="text/javascript">var sessionId = ${sessionId}</script>
			<script type="text/javascript" src="${context}/webroot/dropzoneconfig.js"></script>
		</body>

	</html>
