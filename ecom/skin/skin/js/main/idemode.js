var theTagsDialog = null ;
var theIdeModeJspPath = null ;

function trace(aStr) {
	try {
		console.debug(aStr) ;
	} catch (e) {}	
}

msh.idemode.IdeWidget = function (aId, theJspPath) {
	var theElement = $(aId) ;
	var theGuid = aId ;
	var theEditDialog = null ;
	
	var theMenu = new Ext.menu.Menu({
	    id: 'dropDownMenu',
	    tooltip: "tool",
	    items: [
	    	'guid: '+aId
	    	, '-'
	        , new Ext.menu.Item({
	            text: 'Edit',
	            handler: showEditDialog,
	        })
	        , '-'
	        , new Ext.menu.Item({
	            text: 'Insert after',
	            handler: function() {insertOption("after") } 
	        })
	        , new Ext.menu.Item({
	            text: 'Insert before',
	            handler: function() {insertOption("before") }
	        })
	        , new Ext.menu.Item({
	            text: 'Insert into',
	            handler: function() {insertOption("into") }
	        })
	        , '-'
	        , new Ext.menu.Item({
	            text: 'Delete',
	            handler: deleteTag
	        })
	    ]
	});	

	
	//Element.addClassName(theElement.parentNode, "idetagParent") ;
	eventutil.addEventListener(theElement, eventutil.EVENT_MOUSE_OVER, hightlightContainer);
	eventutil.addEventListener(theElement, eventutil.EVENT_MOUSE_OUT, hightlightContainerOff);

	eventutil.addEventListener(theElement, eventutil.EVENT_CLICK, showMenu);

	function reloadPage() {
		trace("reloading ...");
	    window.location.reload( false );
	}
	
	function showError(aError) {
		alert(aError) ;
	}
	
	function deleteTag() {
		Ext.Msg.show({
		   title:'Deleting tag...',
		   msg: 'Are you sure you want to delete?',
		   buttons: {"yes":"Delete", "no":"No"},
		   fn: function(aButtonName, aText) {
		   		trace(aButtonName) ;
		   		if(aButtonName=="yes") {
					IdeModeService.deleteTag(theJspPath, theGuid, {
						callback: reloadPage
						, errorHander: showError
					}) ;
		   		}
			}
		});	
	}
	
	function hightlightContainer() {
		Element.addClassName(theElement.parentNode, "ideParentHightlight") ;
	}
	
	function hightlightContainerOff() {
		Element.removeClassName(theElement.parentNode, "ideParentHightlight") ;
	}

	function showMenu() {
		theMenu.show(aId) ;
	}
	
	function insertOption(aMode) {
		new msh.idemode.TagsDialog().show(function(aTagName) {
			IdeModeService.getTagInfo(aTagName, {
				callback: function (aTagInfo) {
					new msh.idemode.EditDialog(aTagInfo, []).show(function(aValues) {
						trace("insert [mode="+aMode
							+", guid="+theGuid
							+", jspPath="+theJspPath
							+", tag="+aTagName
							+", values="+aValues
						) ;
						IdeModeService.insertTag(aMode, aTagName, aValues, theGuid, theJspPath, {
							callback: reloadPage
							, errorHander: showError
						}) ;
					}) ;
				}
				, errorHandler: showError
			}) ; 
		}) ;
	}
	
	function showEditDialog() {
		if(theEditDialog==null) {
			IdeModeService.getEditTagMessage(aId,theJspPath, {
				callback: function (aMessage) {
					theEditDialog = new msh.idemode.EditDialog(aMessage.tagInfo, aMessage.values) ;
					theEditDialog.show(save) ;
				}
				, errorHandler: showError
			}) ; 
		} else {
			theEditDialog.show(save) ;
		}
	}
	
	function save(aValues) {
		IdeModeService.saveTag(theGuid,theJspPath, aValues, {
			callback: reloadPage
			, errorHandler: showError
		}) ; 
	}
	
}

msh.idemode.TagsDialog = function() {

	var theForm = null ;
	var theStore = null ;
	var theField = null ;
	var theCallback = null ;
	
	var theDialog = new Ext.BasicDialog("addingtag", {
		autoCreate: true,
		draggable: false,
		width: 600,
		height: 200,
		modal: true,
		proxyDrag: false,
		shadow: true,
		collapsible: false,
		resizable: false,
		closable: true,
		title: "Adding tag..."
	});
	
	theDialog.body.setStyle('padding', '8pt');
	
	theDialog.addButton('Next', submit, this);
	//theDialog.addButton('Cancel', cancel, this);

	
	function submit() {
		theCallback(theField.getValue()) ;
		theDialog.hide() ;
	}	

	this.show = function(aCallback) {
		theCallback = aCallback ;
		if(theField==null) {
			IdeModeService.listTags({
				callback: function (aTags) {
					theForm = new Ext.form.Form({url: 'MyFormURL'});
					
					//var dataStates = [
				    //    ['AL', 'Alabama'],
				    //    ['AK', 'Alaska']] ;
				    
					theStore = new Ext.data.SimpleStore({
					    fields: ['tagid','tag'],
					    data : aTags
					});	
									
					theField = new Ext.form.ComboBox({
						store: theStore,
						id: "tagid",
						name: "tagid",
					    displayField:'tag',
					    typeAhead: true,
					    mode: 'local',
					    triggerAction: 'all',
					    emptyText:'Select a tag...',
					    selectOnFocus:true
						, fieldLabel: "JSP Tag"
						, width: 450
					});
					theForm.add(theField);
					
					theForm.render(theDialog.body);
				}
				, errorHandler:function(aError) {
					alert(aError) ;
	            }				
			}) ; 
		}
		theDialog.show();
		
	}

}

/**
 * EDIT DIALOG
 */
msh.idemode.EditDialog = function(theTagInfo, theValues) {
	var theHeight = theTagInfo.attributes.length * 40 ;
	var theCallback = null ;
	
	var theDialog = new Ext.BasicDialog(theTagInfo.tagName, {
		autoCreate: true,
		draggable: false,
		width: 500,
		height: theHeight>400 ? theHeight : 400,
		modal: true,
		proxyDrag: false,
		shadow: true,
		collapsible: false,
		resizable: false,
		closable: true,
		title: theTagInfo.tagName
	});
	
	var theForm = new Ext.form.Form({url: 'MyFormURL'});

	for(var i = 0 ; i<theTagInfo.attributes.length; i++) {
		var attr = theTagInfo.attributes[i] ;
		
		var field ;
		trace("attr.typeName = "+attr.typeName +" = "+theValues[attr.name] +" "+!attr.required) ;
		if(attr.typeName == "boolean") {
			field = new Ext.form.Checkbox({
				msgTarget: 'under',
				allowBlank: !attr.required,
				id: attr.name,
				name: attr.name,
				fieldLabel: attr.name,
				width:300,
				blankText: 'Enter value'
			});
			field.setValue(theValues[attr.name]) ;
		} else if(attr.typeName == "int") {
			field = new Ext.form.NumberField({
				msgTarget: 'under',
				allowBlank: !attr.required,
				id: attr.name,
				name: attr.name,
				fieldLabel: attr.name,
				width:50,
				blankText: 'Enter value',
				value: theValues[attr.name]
			});
		} else {
			field = new Ext.form.TextField({
				msgTarget: 'under',
				allowBlank: !attr.required,
				id: attr.name,
				name: attr.name,
				fieldLabel: attr.name,
				width:300,
				blankText: 'Enter value',
				value: theValues[attr.name]
			});
			if(attr.name=='guid') {
				field.setDisabled(true) ;
			}
		
		}
		theForm.add(field);
			
	}
	
	theDialog.addButton('Save', submit, this);
	theDialog.addButton('Cancel', cancel, this);

	
		
	theDialog.body.setStyle('padding', '8pt');
	theForm.render(theDialog.body);
	
	this.show = function(aCallback) {
		theCallback = aCallback ;
		trace("callback = "+theCallback) ;
		theDialog.show();
	}
	
	function cancel() {
		theDialog.hide() ;
	}

	function submit() {
		if(theForm.isValid()) {
			theCallback(theForm.getValues()) ;
		} else {
			Ext.MessageBox.alert('Errors', 'Please fix the errors noted.');
		}
		
	}
}

msh.idemode.init = function(aJspPath) {
	theIdeModeJspPath = aJspPath ;
	trace("msh.idemode.init() ...") ;
	//Ext.QuickTips.init();

	allNodes = document.getElementsByClassName("idetag");
	for(i = 0; i < allNodes.length; i++) {
		new msh.idemode.IdeWidget(allNodes[i].id, aJspPath) ;
	}
	trace("msh.idemode.init() DONE") ;
	
	if(getCookie("showTags")=='true') {
		msh.idemode.showIdeTags() ;
	} else {
		msh.idemode.hideIdeTags() ;
	}
}

msh.idemode.hideIdeTags = function() {
	if(Element.hasClassName($("side"), "hideIdeTags")) {
		Element.removeClassName($("side"), "hideIdeTags") ;
		Element.removeClassName($("content"), "hideIdeTags") ;
		$('ideModeHideIdeTags').innerHTML='Hide TAGS' ;
		setCookie("showTags","true") ;
	} else {
		Element.addClassName($("side"), "hideIdeTags") ;
		Element.addClassName($("content"), "hideIdeTags") ;
		$('ideModeHideIdeTags').innerHTML='Show TAGS' ;
		setCookie("showTags","false") ;
		//deleteCookie("showTags") ;
	}
}

msh.idemode.showIdeTags = function() {
	Element.removeClassName($("side"), "hideIdeTags") ;
	Element.removeClassName($("content"), "hideIdeTags") ;
	$('ideModeHideIdeTags').innerHTML='Hide TAGS' ;
}

msh.idemode.getJspPath = function() {
	return $('ideMode_jspPath').innerHTML ;
}
msh.idemode.addGuids = function() {
	IdeModeService.addGuids(msh.idemode.getJspPath(), {
		callback: function() {
		    window.location.reload( false );
		}
	}) ;
}

msh.idemode.addNewForm = function() {
	var form = prompt("New form name (whithot Form-suffix)") ;
	if(form!=null) {
		IdeModeService.addFromTemplate(form, {
			callback: function() {
			    window.location = "entityList-"+form+".do" ;
			}
			,errorHandler: function(aError) {
			    alert(aError);
			}
		}) ;
	}
	//alert(form) ;
}


//Ext.onReady(function() {
//	msh.idemode.init() ;
//});


