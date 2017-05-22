/*
 * show-button.js
 * Copyright (C) 2017 hugo <hugo@debbie>
 *
 * Distributed under terms of the MIT license.
 */
(function(){
  'use strict';
  
  function showButton(buttonId){
  	  var button = document.getElementById(buttonId);
  	  button.className = "visible-button";
  }

  function hideButton(buttonId){
  	  var button = document.getElementById(buttonId);
  	  button.className = "hidden-button";
  }
})();
