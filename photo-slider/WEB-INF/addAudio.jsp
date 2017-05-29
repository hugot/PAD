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

			<section id="main-section">
				<h1>Voeg audio toe via deze pagina</h1>	
				<script type="text/javascript" src="${context}/webroot/dropzone.js"></script>

				<form action="${context}/uploadaudio" class="dropzone needsclick dz-clickable" id="uploadForm">
				</form>

				<form class="hidden-form" method="GET" action="${context}/addsessiontotheme">
					Wilt u de audio meteen aan een thema toevoegen? <br>
					<input type="submit" value="Voeg toe aan thema" name="Voeg toe aan thema" id="big-button" />
				</form>
				
			</section>
			<script type="text/javascript">var sessionId = ${mediaSessionId}</script>
			<script type="text/javascript" src="${context}/webroot/dropzoneconfig-audio.js"></script>

			<%@include file="footer.jsp"%>
		</body>

	</html>
