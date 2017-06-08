    <div id="popup-wrapper">
	<div id="theme-creation-popup" class="hidden-popup" method="POST" action="/addtheme">
		<header>
			<h3>Maak een thema aan</h3>
		</header>
		<p>Geef het thema een naam:</p>
		<input name="theme-name" id="theme-name" placeholder="Thema-naam" type="text"/>
		<br><br>
		<button type="button" class="big-button" value="Maak thema" name="maak-thema" id="maak-thema" onclick="addTheme();" >Maak thema</button>
		<button name="annuleer" class="big-button" type="button" id="annuleer" onclick="hidePopup('theme-creation-popup');">Annuleer</button>
	</div>
	<div id="photo-selection-popup" class="photo-popup hidden-popup">
		<header>Selecteer foto's om toe te voegen aan thema<button class="closing-button" onclick="hidePopup('photo-selection-popup')">X</button></header>
		<div id="photo-selection-area">
		</div>
	</div>
	<div id="delete-confirmation" class=" hidden-popup">
 		<header> Permanent verwijderen?</header>
		 <br>
		Wilt u de foto\'s permanent verwijderen, of alleen uit dit thema?
		<br><br>
		<button class="big-button" onclick="deleteMediaFromTheme();">Alleen uit thema verwijderen</button>
		<button class="big-button" onclick="deleteMediaPermanently();">Permanent verwijderen</button>
	</div>
    	<div id="image-popup" class="photo-popup hidden-popup">
        <header> Foto beheer <button class="closing-button" onclick="hidePopup('image-popup');">X</button></header>
    		<div id="image-display">
    		</div>
    	</div>
		<form id="reset-confirmation" class=" hidden-popup">
			<header><h3>Weet u het zeker?</h3></header>
			<p> Als u op doorgaan klikt, wordt alle media van het apparaat verwijderd
				en wordt het apparaat teruggebracht naar de nieuwstaat. </p>
			<button type="button" onclick="resetApplication()">Doorgaan</button>
			<button type="button" onclick="hidePopup('reset-confirmation');">annuleer</button>
		</form>
		<div id="theme-selection" class="theme-selection hidden-popup">
			<header>Selecteer een thema</header>
			<div id="theme-selection-area" class="scrollable">
			</div>
		</div>
    </div>