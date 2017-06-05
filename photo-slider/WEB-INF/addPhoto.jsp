<%@ page contentType="text/html;Charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="" tagdir="/WEB-INF/tags"%>

<html lang="en">
	<head>
		<meta charset="UTF-8">
			<title></title>
		</head>


		<body>

			<section id="middle-section">
				<header><h3>Voeg een foto toe via deze pagina</h3></header>
				<form method="POST" action="${context}/uploadphoto" class="dropzone needsclick dz-clickable" id="uploadForm">
				</form>

				<header>
					Wilt u de foto's meteen aan een thema toevoegen? <br>
					<button class="big-button" onclick="showThemeSelectionPopup()"> Voeg toe aan thema </button>
				</header>

			<script type="text/javascript" src="${context}/webroot/dropzoneconfig.js"></script>
		</body>

	</html>
