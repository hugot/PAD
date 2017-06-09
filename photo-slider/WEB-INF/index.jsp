<%@ page contentType="text/html;Charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="" tagdir="/WEB-INF/tags"%>


<%-- 

Author: Hugo Thunnissen 

Dit is de hoofdpagina van de applicatie. In de div et de id "app-frame" worden alle pagina's geladen.

--%>

<html lang="en">
	<head>
		<link href="${context}/webroot/style/altosheet.css" rel="stylesheet" type="text/css" media="screen" />
		<link href="${context}/webroot/style/dropzone.css" rel="stylesheet" type="text/css" media="screen" />
		<meta charset="UTF-8">
			<title>Photoslider backend</title>
		</head>

		<body>
		<script type="text/javascript" src="${context}/webroot/dropzone.js"></script>
		<script type="text/javascript" src="${context}/webroot/main-app.js"></script>
			<div id="welcome-section">
				<section>
					<span>
						<h1> Welkom </h1>
						<p> Laten we meteen beginnen, wat wilt u doen?</p>
					</span>
					<button class="big-button top-button" onclick="getReady(appParts['themeManagement']);" ><h3>Beheer Thema's en foto's</h3></button>
					<button class="big-button top-button" onclick="getReady(appParts['uploadAudio']);"><h3>Upload Muziek</h3></button>
					<button class="big-button top-button" onclick="getReady(appParts['uploadPhoto']);"><h3>Upload foto's</h3></button>
					<button class="big-button top-button" onclick="getReady(appParts['settings']);"><h3>Instellingen</h3></button>
				</section>
			</div>
			<div name="app-frame" id="hidden-app-frame"></div>
		</body>
		<%@include file="popups.jsp" %>
	</html>
