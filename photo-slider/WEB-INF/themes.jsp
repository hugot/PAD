<%@ page contentType="text/html;Charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="" tagdir="/WEB-INF/tags"%>

<%-- 

Author: Hugo Thunnissen

Via deze view kunnen de thema's beheerd worden

--%>

<html lang="en">

	<div id="left-selection-bar-wrapper">
		<section id="left-selection-bar">
			<header> 
				<h3>Thema selectie</h3>
			</header>
			<div class="scrollable" id="theme-list">
			<%-- --------------------------- LIJST MET THEMA'S -------------------------------- --%>
			${themes}
			</div>
			<button class="bottom-button" type="button" onclick="showPopup('theme-creation-popup');">Nieuw thema</button>
		</section>
	</div>
	<div id="left-selection-bar-clearance"></div>

	<section id="middle-section">
		<header>
			<h3 id="photo-section-header-text" > Foto's in ${theme}</h3></header><%--
		--%><section id="photo-section"><button class="floating-image" id="example" onclick="showPhotoSelection('/photoselection');">
					<div  class="photo-container">
						<img class="photo" height="150" src="${context}/webroot/icons/plus.jpg">
					</div>
					<h4>Voeg foto's toe</h4>
				</button><%--
			--%>${photos} <%-- <<<<<<<< FOTO'S --%>
		</section>
		<section class="bottom-bar">
			<button class="big-button" onclick="deleteActiveTheme()">Verwijder thema</button>
			<button class="big-button" onclick="showPhotoSelection('/deletephotos')">Verwijder foto's uit thema</button>
		</section>
	</section>
		<section id="hideable-right-sidebar" class="hidden-right-sidebar">
		<button class="show-section-button" onclick="moveAudioSection();"><h3><h3 id="button-arrow"><</h3><br>a<br>u<br>d<br>i<br>o</h3> </button>
		<header><p>Muziek in dit thema</p></header>
			<div class="scrollable" id="audio-scrollable">
			</div>
			<div id="audio">
			<audio controls>
			</div>
			</audio>
		</section>
	<script> selectedThemeId = '${selectedThemeId}'; </script>
</html>