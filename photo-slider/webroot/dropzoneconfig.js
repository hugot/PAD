/*
 * firstscript.js
 * Copyright (C) 2017 hugo <hugo@supersudomachine>
 *
 * Distributed under terms of the MIT license.
 */
(function(){
		'use strict';

		Dropzone.autoDiscover = false;
		var dropzoneOptions = {
				dictDefaultMessage: "Sleep foto's naar dit vlak om ze te uploaden", 
				paramName: "photo",
				acceptedFiles: "image/*"
		}
		var myDropzone = new Dropzone("form#uploadForm", dropzoneOptions);
		myDropzone.on('sending', function(file, xhr, formData){
						formData.append("sessionId", sessionId.toString());
		});
})();
