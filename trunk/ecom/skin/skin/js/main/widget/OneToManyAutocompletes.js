
// Один элемент
msh.widget.OneToManyAuto = function(theSerial, theChild, theTr, theTitle
        , theRemoveFunction, theAddFunction, theRecalcFunction
        , theVocName, theIsView, theParentId, theParentAutocomplete,theViewAction) {
	//alert("one="+theVocName+"#"+theParentId) ;
    var theActionLink = null ;
    var theTitleLabel = null ;
    var theIsRemoveAction = false ;
    var theInput = null ;
    var theHidden = null ;
    var theAutocomplete = null ;


    // идентфикикатор элемента
    this.getSerial = function() {
        return theSerial;
    }

    // создание
    function ce(aName) {
        return document.createElement(aName);
    }

    // добавление строки в таблицу
    this.add = function() {
        var tdLabel = ce("td") ;
        tdLabel.style.className = "label";
        var label = ce("label") ;
        theTitleLabel = label;

        var tdField = ce("td") ;
        var input = ce("input") ;
        if (theIsView) {
        	if (theViewAction!=null && theViewAction!=""&& theViewAction!="null") {
        		input = ce("a") ;
        		input.href=theViewAction+"?id="+(theChild.value ? theChild.value : "") ;
        		input.innerHTML = theChild.name ? theChild.name : "";
        	} else {
        		input = ce("input") ;
        		input.value = theChild.name ? theChild.name : "" ;
   		        input.className = "viewOnly maxHorizontalSize" ;
	            input.disabled = true ;
	            input.style.background = "none" ;
	            input.style.border = "none" ;
	            input.style.color = 'black' ;
	            input.style.className = "" ;
            }
        }else{
        	input = ce("input") ;
        	input.className = "autocomplete maxHorizontalSize" ;
        }
        tdField.appendChild(input);
        //input.style.width = "100%";

        theInput = input;
        theInput.id = "otma_input_"+theSerial ;
        //theInput.size = 50 ; // todo

        var div = ce("div") ;
        tdField.appendChild(div) ;

        var hidden = ce("hidden") ;
        hidden.id = "otma_hidden_"+theSerial ;
        theHidden = hidden ;



        var tdLink = ce("td") ;
        var a = ce("a") ;
        a.innerHTML = "Добавить";
        a.href = "javascript:void(0)";
        tdLink.appendChild(a);
        theActionLink = a;

        //tdLabel.appendChild(label);
        theTr.appendChild(tdLabel);
        theTr.appendChild(tdField);
        if(!theIsView) theTr.appendChild(tdLink);
        //label.innerHTML = theTitle;
        theActionLink.onclick = onClick;

        theHidden.value = "" ;
        theHidden.value = theChild.value ? theChild.value : "" ;
        if (theIsView) {
        	//theInput.className = 'viewOnly horizontalFill' ;
        	
        }else{
        	theInput.value = theChild.name ? theChild.name : "" ;
        }
        

        addAutocomlete(div) ;


        if(theIsView) {

        }
        eventutil.addEventListener(theInput, eventutil.EVENT_BLUR, theRecalcFunction);

    }

    function addAutocomlete(aDiv) {
    	if (!theIsView) {
	        theAutocomplete = new msh_autocomplete.Autocomplete() ;
	        theAutocomplete.setUrl("simpleVocAutocomplete/"+theVocName) ;
	        theAutocomplete.setIdFieldId(theHidden) ;
	        theAutocomplete.setNameFieldId(theInput) ;
	        theAutocomplete.setDivId(aDiv) ;
	        theAutocomplete.setVocKey(theVocName) ;
	        theAutocomplete.setVocTitle(theTitle) ;
	        theAutocomplete.build() ;
	        if (theParentId!=null && theParentId!=""  && theParentId!="null")  {
	        	//alert("parent="+theParentId);
	        	theAutocomplete.setParentId(theParentId) ;
	        	
	        }
	        if (theParentAutocomplete!=null && theParentAutocomplete!="" && theParentAutocomplete!="null") {
	        	theAutocomplete.setParent(theParentAutocomplete+"Autocomplete") ;
	        }
        }
        /*
        eventutil.addEventListener(theInput, "keydown", 
			  	function(aEvent) {
			  		if (aEvent.keyCode == 107) {
			  			onClick;
			  		}
			  		if (aEvent.keyCode == 109) {
			  			onClick;
			  		}
			            
				  	}) ; */
        
        //a.setVocId(theHidden.value) ;
    }
    // нажатие на ссылку
    function onClick() {
        if (theIsRemoveAction) {
            //theHidden.parentNode.removeChild(theHidden);
            theTr.parentNode.removeChild(theTr);
            theRemoveFunction(theSerial);
        } else {
            theAddFunction();
        }
    }

    // тип команды true - удалить, false - добавить
    this.setIsRemoveAction = function(aIsRemove) {
        theIsRemoveAction = aIsRemove;
        theActionLink.innerHTML = aIsRemove ? "Убрать" : "Добавить еще";
        theActionLink.title = aIsRemove ? "Убрать "+theTitle : "Добавить еще один "+theTitle ;
        theActionLink.className = "manyToManyActionLink" ;

    }
    // показывать метку
    this.setTitleVisibled = function(aVisibled) {
        theTitleLabel.innerHTML = aVisibled ? theTitle : "";
        //        alert(theTitleLabel.innerHTML +" "+aVisibled)
    }

    // фокус
    this.focus = function() {
		   theInput.focus() ;
		   theInput.select() ;
    }
    // Очистить данные
    this.clearData = function() {
        theInput.value="" ;
        theHidden.value="" ;
    }
    this.setValue = function(aId,aValue) {
        theInput.value=aValue ;
        theHidden.value=aId ;
    }
    // Присвоить Родителя Id
    this.setParentId = function(aParentId) {
    	theParentId = aParentId ;
        theAutocomplete.setParentId(theParentId) ;
    }

    this.getJson = function() {
        theChild["value"] = theHidden.value;
        return theChild ;
    }

}

// Несколько элементов Autocomplete
msh.widget.OneToManyAutocompletes = function(theInstallDiv, theForm, theFieldName, theTitle, theVocName, theIsView
	, theParentId, theParentAutocomplete, theViewAction) {

    var theTbody = null ;
    var theLegend = null ;
    var theAutos = new Array() ;
    var theLastSerial = 1 ;
	//alert("many="+theVocName+"#"+theParentId) ;

    // установка
    this.install = function() {
        // поиск полей в форме
//        var hiddens = Form.getInputs(theForm, "hidden", theFieldName) ;
        buildSurroundView();

        // через json
        var value = $(theFieldName).value ;
        //alert(value) ;
        if(value!=null && value!="null" && value!="") {
	        var json = eval('(' + $(theFieldName).value + ')') ;
	        //alert(theFieldName.value) ;
	        var childs = json.childs ;
	        for (var i = 0; i < childs.length; i++) {
	            var tr = ce("tr") ;
	            theTbody.appendChild(tr);
	            var one = new msh.widget.OneToManyAuto(getNextSerial(), childs[i], tr, theTitle
	                    , onRemove, onAdd,recalc
	                    , theVocName, theIsView,theParentId, theParentAutocomplete, theViewAction) ;
	            one.add();
	            theAutos.push(one);
	        }
	        if(childs.length==0) {
	            onAdd() ;
	        }
        } else {
            onAdd() ;
        }
        onUpdate();
    }
    this.setIds = function(aJson) {
    	//alert(aJson) ;
        if(aJson!=null && aJson!="null" && aJson!="") {
	        var json = eval('(' + aJson + ')') ;
	        //alert(theFieldName.value) ;
	        var childs = json.childs ;
	        var len = theAutos.length ;
	        for (var i = 0; i < childs.length; i++) {
	        	var child = childs[i] ;
	            if (i<len) {
	            	theAutos[i].setValue(child.value ? child.value : "",child.name ? child.name : "");
	            } else {
		            var tr = ce("tr") ;
		            theTbody.appendChild(tr);
		            var one = new msh.widget.OneToManyAuto(getNextSerial(), child, tr, theTitle
		                    , onRemove, onAdd,recalc
		                    , theVocName, theIsView,theParentId, theParentAutocomplete) ;
		            one.add();
		            theAutos.push(one);
	            }
	        }
        } 
        onUpdate();
    }
    
    this.setParentId = function(aParentId) {
    	//alert(aParentId) ;
    	theParentId=aParentId ;
        for (var i = 0; i < theAutos.length ; i++) {
        	//alert(theAutos[i]) ;
            theAutos[i].setParentId(theParentId);
        }
    }
    this.clearData = function() {
        for (var i = 0; i < theAutos.length ; i++) {
            theAutos[i].clearData() ;
        }
        
    }

    function getNextSerial() {
        return ++theLastSerial;
    }


    // при удалении строки
    function onRemove(aAutoId) {
        var ar = new Array() ;
        var remotedIndex = 0 ;

        for (var i = 0; i < theAutos.length; i++) {
            if (aAutoId != theAutos[i].getSerial()) {
                ar.push(theAutos[i]);
            } else {
                theAutos[i<theAutos.length-1 ? i+1 : 0].focus();
            }
        }
        theAutos = ar;
        onUpdate();
    }

    // при добавлении
    function onAdd() {
        //var hidden = ce("hidden") ;
        //hidden.name = theFieldName;
        //theForm.appendChild(hidden);
        var tr = ce("tr") ;
        theTbody.appendChild(tr);
        var child = new Object() ;
        child["id"] = "" ;
        child["value"] = "" ;

        var one = new msh.widget.OneToManyAuto(getNextSerial(), child, tr, theTitle
                , onRemove, onAdd, recalc
                , theVocName, theIsView, theParentId, theParentAutocomplete) ;
        one.add();
        one.focus();
        theAutos.push(one);
        onUpdate();
    }

    // обновить
    function onUpdate() {
        setTitleVisibled(theAutos.length != 1);
        for (var i = 0; i < theAutos.length - 1; i++) {
            theAutos[i].setIsRemoveAction(true);
        }
        theAutos[theAutos.length - 1].setIsRemoveAction(false);

        recalc() ;
    }

    function recalc() {
        var json = new Object() ;

        var childs = new Array() ;
        for (var i = 0; i < theAutos.length ; i++) {
            childs.push(theAutos[i].getJson()) ;
        }
        json["childs"] = childs ;
        $(theFieldName).value = JSON.stringify(json) ;
    }

    function setTitleVisibled(aVisibled) {
        if (aVisibled) {
            theLegend.parentNode.style.border = "1px solid #999";
        } else {
            theLegend.parentNode.style.border = "none";
        }
        for (var i = 0; i < theAutos.length; i++) {
            theAutos[i].setTitleVisibled(!aVisibled);
        }
        theLegend.innerHTML = aVisibled ? theTitle : "";
    }

    function ce(aName) {
        return document.createElement(aName);
    }

    function buildSurroundView() {
        var fieldset = ce("fieldset") ;
        var legend = ce("legend") ;
        var table = ce("table") ;
        table.border = '0';
        var tbody = ce("tbody") ;

        table.appendChild(tbody);
        fieldset.appendChild(legend);
        fieldset.appendChild(table);
        theInstallDiv.innerHTML = "";
        theInstallDiv.appendChild(fieldset);
        legend.innerHTML = theTitle;
        theTbody = tbody;
        theLegend = legend;
    }
}
