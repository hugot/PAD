<%@ page contentType="text/html;Charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="" tagdir="/WEB-INF/tags"%>

<html lang="en">
	<head>
		<meta charset="UTF-8">
			<title></title>
		</head>


		<body>

			<section id="main-section">
				<h1>Voeg een foto toe via deze pagina</h1>	

				<form method="POST" action="${context}/uploadphoto" class="dropzone needsclick dz-clickable" id="uploadForm">
				</form>

				<form class="hidden-form" method="GET" action="${context}/addsessiontotheme">
					Wilt u de foto's meteen aan een thema toevoegen? <br>
					<input type="submit" value="Voeg toe aan thema" name="Voeg toe aan thema" id="big-button" />
				</form>

			<script type="text/javascript" src="${context}/webroot/dropzoneconfig.js"></script>
		</body>

	</html>
