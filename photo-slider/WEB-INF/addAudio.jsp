<%@ page contentType="text/html;Charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="" tagdir="/WEB-INF/tags"%>

<html lang="en">
			<section id="main-section">
				<h1>Voeg audio toe via deze pagina</h1>	
				<form action="${context}/uploadaudio" class="dropzone needsclick dz-clickable" id="uploadForm">
				</form>

				<form class="hidden-form" method="GET" action="${context}/addsessiontotheme">
					Wilt u de audio meteen aan een thema toevoegen? <br>
					<input type="submit" value="Voeg toe aan thema" name="Voeg toe aan thema" id="big-button" />
				</form>
			</section>
	</html>
