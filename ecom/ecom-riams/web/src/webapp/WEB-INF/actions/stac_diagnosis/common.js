common = {
	// cleanly prints an array/object for the alert(). TODO; REMOVE -- ONLY FOR DEMO.
	dump: function (arr,level) {
		var dumped_text = "";
		if(!level) level = 0;
		
		//The padding given at the beginning of the line.
		var level_padding = "";
		for(var j=0;j<level+1;j++) level_padding += "    ";
		
		if(typeof(arr) == 'object') { //Array/Hashes/Objects
		 for(var item in arr) {
		  var value = arr[item];
		 
		  if(typeof(value) == 'object') { //If it is an array,
		   dumped_text += level_padding + "'" + item + "' ...\n";
		   dumped_text += dump(value,level+1);
		  } else {
		   dumped_text += level_padding + "'" + item + "' => \"" + value + "\"\n";
		  }
		 }
		} else { //Stings/Chars/Numbers etc.
		 dumped_text = "===>"+arr+"<===("+typeof(arr)+")";
		}
		return dumped_text;
	},
	
	sanitize: function(str) {
		if (typeof str != 'string')
			return '';
		str = str.replace(/[^a-zA-Z 0-9]+/g,'');
		if (str.length > 10)
			str = str.substr(0,9);
		return str;
	},
		
	serialize: function(o) { 
		var a = [];
		o.find('input, textarea').each(function() {
			var n = this.name || this.id;
		   var t = this.type;
		   
		   if ( (t == 'checkbox') && !this.checked )
		   	return;
		  
		   a.push({name: n, value: this.value});
		   	
		}).end();
		return a;
	},
	
	trim: function(str) {
		return str.replace(/^\s*|\s*$/g,"");
	}
};