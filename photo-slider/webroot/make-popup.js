/*
 * make-popup.js
 * Copyright (C) 2017 hugo <hugo@supersudomachine>
 *
 * Distributed under terms of the MIT license.
 */
  'use strict';
  
  function showPopup(popupId){
    var popup = document.getElementById(popupId);
    popup.className = "popup";
  }
  
  function hidePopup(popupId){
    var popup = document.getElementById(popupId);
    popup.className = "hidden-popup";
  }
    
