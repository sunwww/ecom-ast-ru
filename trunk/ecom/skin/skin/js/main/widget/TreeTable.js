/**
 * @class Вложенная таблица
 *
 * @constructor
 * @param {String} aUrl   обработчик
 * @param {String} aName  имя компонента
 * @param {String} aTitle заголовок
 *
 */
msh.widget.TreeTable = function(theUrl, theName, theTitle, theInput) {

    var theLastSearch = "";

    var theNameField = null ;
    var theEditButton = null ;
    var theData = null ;
    var theThis = this ;

    this.onPageNext = function() {
        go(theData.lastId, theData.lastParentId, true, null) ;
    } ;

    this.onPagePrevious = function() {
        go(theData.firstId, theData.lastParentId, false, null) ;
    } ;

    var theDialog = new msh.widget.TreeTableDialog(theName, theTitle, this);


    this.selectCurrent = function() {
        selectData(theData) ;
    }

    this.clear = function() {
        theDialog.hide() ;
        theNameField.innerHTML = "" ;
        theInput.value = "" ;
    }

    function selectData(data) {
        //alert(data) ;
        theDialog.hide() ;
        //alert("dialog="+this.theDialog) ;
        //var i = data.path.length-1 ;
        //this.theIdField = data.path[data.path.length-1].id ;
        var address = "" ;
        for(var i=0; i<data.path.length; i++) {
            address = address + " " + data.path[i].name ;
        }
        theNameField.innerHTML = address ;
        theInput.value = data.path[data.path.length-1].id ;
    }

    this.setCurrentData = function(aData) {
        theData = aData ;
    }

    /** Установить в узел aNode */
    this.installTo = function (aNode) {
        theField = aNode ;
        var parentNode = aNode.parentNode ;
        parentNode.removeChild(aNode);

        var span = document.createElement("span") ;
        theNameField = span ;
        span.appendChild(document.createTextNode(aNode.value));
        parentNode.appendChild(span);

        var input  = document.createElement("input") ;
        input.id   = theName + "EditButton";
        input.type = 'button';
        input.onclick = this.onEditButtonClick ;
        input.value   = "Изменить";
        parentNode.appendChild(input);
        theEditButton = input ;

        var holderDiv = document.createElement("div") ;
        holderDiv.id = theName + "Holder";
        parentNode.appendChild(holderDiv);
    };

    this.onEditButtonClick = function() {
        theEditButton.blur() ;
        theDialog.show();
        go(theInput.value!=""?theInput.value:null, null, true);
    } ;

    function go(aFromId, aParentId, aIsForward, aSearchString) {
        if(aSearchString) {
            theDialog.setMessage("Поиск "+aSearchString+" ...") ;
        } else {
            theDialog.setMessage("Ждите ...") ;
        }
        if (theTableArrow != null) {
            theTableArrow.remove();
        }
        var pars = "" ;
        if (aParentId != null) pars += "parentId=" + aParentId + "&";
        if (aFromId != null) pars += "fromId=" + aFromId + "&";
        if (!aIsForward) pars += "direction=backward&";
        if (aSearchString != null) pars += "search=" + aSearchString + "&";

        //    var ajax = new Ajax.Updater(this.theName + 'Holder', this.theUrl, {method: 'POST'
        //        , parameters: pars
        //        , onComplete: this.onComplete.bind(this)});

        var cObj = YAHOO.util.Connect.asyncRequest('POST', theUrl, {
            success: onComplete ,
            failure: function (response) {
                alert(response.statusText);
            }
        }, pars);
    } ;


    function onComplete(aResponse) {
    //    alert(aResponse.responseText);
        var data = eval('(' + aResponse.responseText + ')') ;
        theData = data ;
        //this.theData = data ;
        //this.setCurrentData(data) ;
        if(data.rows.length==0) {
            theDialog.hide() ;
            //var i = data.path.length-1 ;
            //this.theIdField = data.path[data.path.length-1].id ;
            var address = "" ;
            for(var i=0; i<data.path.length; i++) {
                address = address + " " + data.path[i].name ;
            }
            theNameField.innerHTML = address ;
            theInput.value = data.path[data.path.length-1].id ;
        } else {
            theDialog.updateContent(data
                    , go, theThis);
        }

        if (theTableArrow != null) {
            theTableArrow.remove();
        }
        theTableArrow = new tablearrow.TableArrow('tab1');
    };

    function search(aFromId, aParentId, aIsForward) {
        theLastSearch = prompt("Найти:", "Поиск по КЛАДР", theLastSearch);
        go(aFromId, aParentId, aIsForward, theLastSearch);
    };

};


function createTestData() {
    return ({
        titles : ["hello", "heee123", "asdf"],
        rows: [
        { id: "1", parentId: "123", cols: [123,123,123]},
        { id: "1", parentId: "123", cols: [123,123,123]},
        { id: "1", parentId: "123", cols: [123,123,123]},
        { id: "1", parentId: "123", cols: [123,123,123]},
        { id: "1", parentId: "123", cols: [123,123,123]},
        { id: "1", parentId: "123", cols: [123,123,123]}
                ]
    });
}

