<%@ page contentType="text/html;Charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="" tagdir="/WEB-INF/tags"%>


<%-- 

Author: Hugo Thunnissen 

Via deze view kunnen foto's toegevoegd worden

--%>

<html lang="en">
	<head>
		<link href="${context}/webroot/style/altosheet.css" rel="stylesheet" type="text/css" media="screen" />
		<meta charset="UTF-8">
			<title>Photoslider backend</title>
		</head>

		<body>
			<div id="welcome-section">
				<section>
					<span>
						<h1> Welkom </h1>
						<p> Laten we meteen beginnen, wat wilt u doen?</p>
					</span>
					<button class="big-button" onclick="loadPage('/thememanagement');" ><h3>Beheer Thema's en foto's</h3></button>
					<button class="big-button" onclick="loadPage('/uploadaudio'));"><h3>Upload Muziek</h3></button>
					<button class="big-button" onclick="loadPage('/uploadphoto');"><h3>Upload foto's</h3></button>
					<button class="big-button" onclick="loadPage('/settingmanagement');"><h3>Instellingen</h3></button>
					<button class="big-button"><h3>Klik hier</h3></button>
					<button class="big-button"><h3>Klik hier</h3></button>
				</section>
			</div>
			<div name="app-frame" id="hidden-app-frame"></div>
		</body>
		<script type="text/javascript" src="${context}/webroot/move-section.js"></script>
		<script type="text/javascript" src="${context}/webroot/make-popup.js"></script>
		<script type="text/javascript" src="${context}/webroot/sound-interaction.js"></script>
	</html>
