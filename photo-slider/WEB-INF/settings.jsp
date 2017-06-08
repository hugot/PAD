<%@ page contentType="text/html;Charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="" tagdir="/WEB-INF/tags"%>

<%-- 

Author: Hugo Thunnissen

Dit is de settings pagina.

--%>

<html lang="en">
		<body>
		<section style="text-align: center;">
			<section id="middle-section">
				<header><h2> De instellingen van de slideshow</h2></header>
				<form id="settings" action="settingmanagement" method="post">
					<input type="hidden" value="reset" id="reset" name="reset">
					Het is mogelijk om het apparaat te resetten naar de nieuwstaat.<br>
					Druk hiervoor op de onderstaande knop:<br><br>
					<button type="button" class="big-button" onclick="showPopup('reset-confirmation');">reset</button>
				</form>
				<button type="button" class="big-button" onclick="turnMusicOnOff();">Zet het muziek aan/uit</button>
			</section>
			</section>

		</body>
	</html>
