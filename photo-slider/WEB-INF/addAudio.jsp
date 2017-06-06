<%@ page contentType="text/html;Charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="" tagdir="/WEB-INF/tags"%>

<html lang="en">
			<section id="middle-section">
				<header><h3>Voeg audio toe via deze pagina</h3></header>
				<form action="${context}/uploadaudio" class="dropzone needsclick dz-clickable" id="uploadForm">
				</form>

				<header>	Wilt u de audio meteen aan een thema toevoegen?
					<br>
					<button class="big-button" onclick="showThemeSelectionPopup()"> Voeg toe aan thema </button>
				</header>
			</section>
	</html>
