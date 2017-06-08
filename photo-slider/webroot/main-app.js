/*
 * main-app.js
 * * Copyright (C) 2017 hugo <hugo@hugo-PC>
 *
 * Distributed under terms of the MIT license.
 * 
 * Dit script bevat alle hoofd-functionaliteit van de user interface.
 */
'use strict';

// Het frame waarin de onderdelen van de applicatie geladen worden
var appFrame;
// De onderdelen van de applicatie.
var appParts = new Object();

//Media die geselecteerd wordt
var selectedMedia = new Object();
var selectedThemes = new Object();

var ready = false;
var selectedThemeId;
var shownImage;
var addPhotoDiv;


/**
 * Stuur een httpRequest naar de server doe vervolgens iets met de
 * response text.
 * @param {*} address 
 * @param {*} readyStateAction 
 */
function ajaxCall(method, address, readyStateAction, params) {
	var xhr = new XMLHttpRequest();
	xhr.open(method, address, true);
	xhr.onreadystatechange = function () {
		if (this.readyState !== 4) return;
		if (this.status !== 200) {
			console.log('response geeft errorcode' + this.status);
			return;
		}
		readyStateAction(this.responseText);
	};
	xhr.send(params);
}

/**
 * Dit is een object voor het aanmaken en beheren van het 
 * frame waarin de applicatie getoond wordt.
 * @param {*} appFrameId 
 */
function AppFrame(appFrameId) {
	var me = this;
	this.frameElement = document.getElementById(appFrameId);

	/** 
	 * Maak het frame zichtbaar.
	 */
	this.setVisible = function () {
		this.frameElement.id = 'app-frame';
	}

	/**
	 * Laad de inhoud van een httpResponse in het frame.
	 */
	this.loadPage = function (page, callback) {
		ajaxCall('GET', page, function (responseText) {
			me.frameElement.innerHTML = null;
			me.frameElement.innerHTML = responseText;
			if (typeof callback === 'function') {
				callback();
			}
		}, null);
	};

	/**
	 * Laad een appPart object in het frame.
	 */
	this.loadObject = function (myObject) {
		this.loadPage(myObject.url, myObject.onLoadCallback);
	};
}


/**
 * Gedeelte van de applicatie dat geladen kan worden.
 * @param {*} url 
 * @param {*} onLoadCallBack 
 */
function AppPart(url, onLoadCallback) {
	this.url = url;
	this.onLoadCallback = onLoadCallback;

	this.load = function (loadMe) {
		loadMe(this);
	};
}


/**
 * Maak het appFrame klaar voor gebruik en schuif welkomstsectie omhoog.
 * @param {*} appPart 
 */
function getReady(appPart) {
	// Als de welkomstsetie al omhoog bewogen is, dan wrdt gewoon de pagina geladen.
	if (ready == true) {
		appFrame.loadObject(appPart);
		return;
	}
	appFrame = new AppFrame('hidden-app-frame');
	appFrame.loadObject(appPart);
	// Beweeg de welkomst sectie omhoog met deze functie.
	function moveUp(callback, id, oldTop, newTop) {
		var element = document.getElementById(id);
		var tp = parseInt(element.style.top);
		if (isNaN(tp)) {
			tp = oldTop;
		}
		console.log(tp + " is tp");
		var interval = setInterval(move, 100);
		function move() {
			if (tp <= newTop) {
				clearInterval(interval);
				if (typeof callback === 'function') {
					callback();
				}
			}
			else {
				element.style.top = tp + "%";
				tp -= 5;
			}
		}
	}
	moveUp(function () {
		var welcomeSection = document.getElementById('welcome-section');
		var wrapper = document.createElement('SECTION');
		var title = document.createElement('H1');
		var buttonCollection = document.getElementsByClassName('top-button');
		var buttons = Array.prototype.slice.call(buttonCollection);
		wrapper.id = 'top-section';
		title.textContent = 'PhotoSlider';
		wrapper.appendChild(title);
		for (var i in buttons) {
			var button = buttons[i];
			wrapper.appendChild(button);
		}
		document.body.appendChild(wrapper);
		document.body.appendChild(appFrame.frameElement);
		appFrame.setVisible();
		welcomeSection.remove();
		ready = true;
	}, 'welcome-section', 0, -90);
}

/**
 * Laad de inhoud van een httpresponse in een div.
 * @param {*} contentUrl 
 * @param {*} method 
 * @param {*} params 
 * @param {*} element 
 * @param {*} standardContent 
 */ 
function loadContentTo(contentUrl, method, params, element, standardContent) {
	console.log('loading content');
	var xhr = new XMLHttpRequest();
	if (standardContent != null) {
		element.innerHTML = null;
		element.appendChild(standardContent);
		ajaxCall(method, contentUrl, function (responseText) {
			element.innerHTML += responseText;
		}, params);
	}
	else{
		ajaxCall(method, contentUrl, function (responseText) {
			element.innerHTML = responseText;
		}, params);
	}
}

// Laad de foto's voor een thema.
function setActiveTheme(themeId) {
	hideAudioSection();
	var element = document.getElementById('photo-section');
	if (addPhotoDiv == null) {
		addPhotoDiv = element.firstChild;
	}
	loadContentTo('/getphotos', 'POST', 'selectedThemeId=' + themeId, element, addPhotoDiv);
	if (selectedThemeId != null) {
		try{
			document.getElementById('selected-theme').id = selectedThemeId;
		} catch (error){
			console.log('thema is niet meer aanwezig in lijst');
		}
	}
	var theme = document.getElementById(themeId);
	theme.id = 'selected-theme';
	document.getElementById('photo-section-header-text').innerText = 'Foto\'s in ' + theme.innerText;
	selectedThemeId = themeId;
}


// Voeg een thema toe 
function addTheme() {
	var themeName = document.getElementById('theme-name');
	loadContentTo('/addtheme', 'POST', 'theme=' + themeName.value, document.getElementById('theme-list'), null);
	hidePopup('theme-creation-popup');;
}

/**
 * Delete thema dat nu actief is.
 */
function deleteActiveTheme() {
	ajaxCall('POST', '/deletetheme', function (responseText) {
		var popupWrapper = document.getElementById('popup-wrapper');
		popupWrapper.innerHTML += responseText;
		popupWrapper.style.visibility = 'visible';
		var themeList = document.getElementById('theme-list')
		ajaxCall('GET', '/getthemes', function(responseText){
			themeList.innerHTML = responseText;
			setActiveTheme(themeList.firstChild.id);
		}, null);
	}, null);
}

//Toon een foto vergroot in een popup.
function showImage(imageId) {
	var image = document.getElementById('image' + imageId);
	var imagePopupWrapper = document.getElementById('popup-wrapper');
	var imagePopup = document.getElementById('image-popup');
	var imageDisplay = document.getElementById('image-display');
	if (imageDisplay.firstChild != null) {
		imageDisplay.removeChild(imageDisplay.firstChild);
	}
	imageDisplay.appendChild(image.cloneNode());
	showPopup(imagePopup.id);
	shownImage = imageId;
}

var oldPosition;
function moveAudioSection() {
	var element = document.getElementById('hideable-right-sidebar');
	if (oldPosition == null) {
		oldPosition = '0px';
	}
	var newPosition = oldPosition;
	oldPosition = element.style.right;
	element.style.right = newPosition;
	if (newPosition == '0px') {
		loadContentTo('/audiosample', 'GET', null,
			document.getElementById('audio-scrollable'),
			null);
		document.getElementById('button-arrow').innerText = '>';
		console.log('hiero');
	}
	else {
		document.getElementById('button-arrow').innerText = '<';
		stopMusic();
	}
}

function hideAudioSection() {
	stopMusic();
	oldPosition = '0px';
	document.getElementById('hideable-right-sidebar').style.right = '-20%';
	document.getElementById('button-arrow').innerText = '<';
}

function playMusic(musicId) {
	var element = document.getElementById('audio');
	loadContentTo('/audiosample', 'POST', 'selectedAudioId=' + musicId, element);
}

function stopMusic() {
	try {
		document.getElementById('audio').firstChild.pause();
	} catch (error) {
		console.log('nothing to see here');
	}
}

function showPhotoSelection(url) {
	var popup = document.getElementById('photo-selection-popup');
	var photoSection = document.getElementById('photo-selection-area');
	function compStyle(element){ return window.getComputedStyle(element);}
	photoSection.style.height = (parseFloat(compStyle(popup).height) - 40) + 'px';
	popup.style.overflow = 'unset';
	photoSection.style.overflow = 'auto';
	loadContentTo(url, 'GET', '', photoSection, null);
	showPopup(popup.id);
}

function selectMedia(divId, mediaId){
	console.log(divId);
	setSelected(document.getElementById(divId));
	if(selectedMedia[mediaId] === undefined ){
		console.log('adding');
		selectedMedia[mediaId] = 1;
	}
	else{
		console.log('removing');
		delete selectedMedia[mediaId];
	}
}

function setSelected(div){
	function bgColor() {return div.style.backgroundColor;}
	if(bgColor() == 'rgb(161, 194, 249)'){
		div.style.backgroundColor = '';
		console.log('blauw naar wit');
	}
	else{
		div.style.backgroundColor = '#A1C2F9';
		console.log('wit naar blauw');
	}
	console.log('niets matchte');
	console.log(bgColor());
	console.log(div.style.backgroundColor);
}

function deleteMedia(){
	showPopup('delete-confirmation');
}

function sendSelectedMediaTo(url){
	var params = '';
	for(var i in selectedMedia){
		params += 'selectedMediaId='+i+'&';
	}
	ajaxCall('POST', url, function(responseText){
		setActiveTheme(selectedThemeId);
	},params);
	hidePopup('photo-selection-popup');
	hidePopup('delete-confirmation');
}

function deleteMediaFromTheme(){
	sendSelectedMediaTo('/managephoto');
}

function addMediaToTheme(callback){
	sendSelectedMediaTo('/addmediatotheme');
}

function deleteMediaPermanently(){
	sendSelectedMediaTo('/deletemedia');
}


function turnMusicOnOff(){
	ajaxCall('POST', '/turnAudioOnOff', loadToPopupWrapper, null);
}
	

function showPopup(popupId) {
	var popupWrapper = document.getElementById('popup-wrapper');
	popupWrapper.style.visibility = 'visible';
	var popup = document.getElementById(popupId);
	popup.className = popup.className.replace('hidden-popup', 'popup');
}

function hidePopup(popupId) {
	var popupWrapper = document.getElementById('popup-wrapper');
	if(popupId == 'photo-selection-popup') selectedMedia = new Object();
	popupWrapper.style.visibility = 'hidden';
	var popup = document.getElementById(popupId);
	popup.className = popup.className.replace(' popup', ' hidden-popup');
	console.log(popup.className);
}

function showThemeSelectionPopup(){
	var selectThemePopup = document.getElementById('theme-selection');
	var themeSection = document.getElementById('theme-')
	ajaxCall('GET', '/addsessiontotheme', function(responseText){
		selectThemePopup.children[1].innerHTML = responseText;
		showPopup(selectThemePopup.id);
	} , null);
}

function selectTheme(elementId, selectThemeId){
	setSelected(document.getElementById(elementId));
	if(selectedThemes[selectThemeId] === undefined ){
		selectedThemes[selectThemeId] = 1;
	} else {
		delete selectedThemes[selectThemeId];
	}
}

function sendSelectedThemesTo(url){
	var params = '';
	for(var i in selectedMedia){
		params += 'selectedThemeId='+i+'&';
	}
	ajaxCall('POST', url, loadToPopupWrapper ,params);
	hidePopup('theme-selection');
}
function resetApplication(){
	ajaxCall('POST', '/settingmanagement', loadToPopupWrapper, 'reset=1');
	hidePopup('reset-confirmation');
}

function loadToPopupWrapper(responseText){
		var popupWrapper = document.getElementById('popup-wrapper');
		popupWrapper.innerHTML += responseText;
		popupWrapper.style.visibility = 'visible';
}
// Maakt een dropzone aan voor het uploaden van foto's
var photoDropzoneConfig = function () {
	Dropzone.autoDiscover = false;
	var dropzoneOptions = {
		dictDefaultMessage: "Sleep foto's naar dit vlak om ze te uploaden",
		paramName: "photo",
		acceptedFiles: "image/*"
	};
	var myDropzone = new Dropzone("form#uploadForm", dropzoneOptions);
}

// Maakt een dropzone aan voor het uploaden van audio.
var audioDropzoneConfig = function () {
	Dropzone.autoDiscover = false;
	var dropzoneOptions = {
		dictDefaultMessage: "Sleep audiobestanden naar dit vlak om ze te uploaden",
		paramName: "sound",
		acceptedFiles: ".mp3",
	};
	var myDropzone = new Dropzone("form#uploadForm", dropzoneOptions);
}

// De pagina's van de applicatie.
appParts['uploadPhoto'] = new AppPart('/uploadphoto', photoDropzoneConfig);
appParts['themeManagement'] = new AppPart('/thememanagement', null);
appParts['uploadAudio'] = new AppPart('/uploadaudio', audioDropzoneConfig);
appParts['settings'] = new AppPart('/settingmanagement', null);
appParts['photoDeletion'] = new AppPart('/deletephotos', null);

(function () {
	getReady(appParts['themeManagement']);
}())