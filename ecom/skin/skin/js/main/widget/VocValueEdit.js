/**
 * Редактирование/Добавление значения в справочник
 */
msh.widget.VocValueEdit = function(theActionUrl, theVocKey, theVocName, theCallbacks) {

    var theDialog = null ;
    var theIsCreate = false ;
    var theThis = this ;
    var theParentId = null ;


    function initDialog() {
        if (theDialog == null) {
            theDialog = new msh.widget.VocValueEditDialog(theVocKey + "Id", theVocName, theThis);
            theDialog.buildDialog();
        }
    }
    this.insertNewValue = function(aParentId) {
        theParentId = aParentId ;
        initDialog();
        theIsCreate = true;
        theDialog.showInsert();
    }

    this.changeValue = function(aId, aName) {
        initDialog();
        theIsCreate = false;
        theDialog.showEdit(aId, aName);
    }

    this.onSave = function(aIdValue, aNameValue) {
        VocEditService.createVocValue(theVocKey, aIdValue, aNameValue, theParentId, {
            callback: function(aId) {
                theDialog.hide() ;
                theCallbacks.valueChanged(aId, aNameValue) ;
            },
            errorHandler:function(message) {
                alert("Ошибка: " + message);
            },
            warningHandler:function(message) {
                alert("Предупреждение: " + message);
            }

        });
    }

}

msh.widget.VocValueEditDialog = function(theDialogId, theTitle, theController) {

    var theDialog ;
    var theRootPane ;
    var theIdField = null ;
    var theNameField = null ;
    var theSaveButton = null ;
    var theNameH3 = null ;

    this.hide = function() {
        theDialog.hide() ;
    }

    this.showInsert = function() {
        theDialog.show();
        theNameH3.innerHTML = "Создание нового значения" ;
        theNameField.focus();
        theIdField.value = "";
        theNameField.value = "";
        theSaveButton.value = "Создать новое значение";
    }

    this.showEdit = function(aId, aName) {
        theDialog.show();
        theNameH3.innerHTML = "Изменение" ;
        theIdField.value = aId;
        //theIdField.disabled = "true" ;
        theNameField.value = aName
        theNameField.focus();
        theSaveButton.value = "Сохранить изменения";
    }

    function onPressButton() {
        theController.onSave(theIdField.value, theNameField.value);
    }

    function hideDialog() {
        theDialog.hide() ;
    }
    this.buildDialog = function() {
        var div = document.createElement("div") ;
        div.id = theDialogId;
        div.className = "dialog";

        var h2 = document.createElement("h2") ;
        h2.appendChild(document.createTextNode(theTitle));

        var rootPane = document.createElement("div") ;
        rootPane.className = "rootPane";

        theNameH3 = document.createElement("h3") ;


        var form = document.createElement("form") ;
        var table = document.createElement("table") ;
        var tr = document.createElement("tr") ;
        var td = document.createElement("td") ;
        td.className = 'label';
        td.appendChild(document.createTextNode("Идентификатор"));
        tr.appendChild(td);
        td = document.createElement("td");
        var input = document.createElement("input") ;
        input.type = 'text';
        theIdField = input;
        td.appendChild(input);
        tr.appendChild(td);

        // table.appendChild(tr); раскоментировать для ввода значения

        tr = document.createElement("tr");
        td = document.createElement("td");
        td.className = 'label';
        td.appendChild(document.createTextNode("Значение:"));
        tr.appendChild(td);
        td = document.createElement("td");
        input = document.createElement("input");
        input.type = 'text';
        theNameField = input;
        td.appendChild(input);
        tr.appendChild(td);
        table.appendChild(tr);

        form.appendChild(table);

        theSaveButton = document.createElement("input");
        theSaveButton.type = 'button';
        theSaveButton.value = 'Сохранить';
        theSaveButton.onclick = onPressButton;

        var cancelButton = document.createElement("input");
        cancelButton.type = 'button';
        cancelButton.value = 'Отменить';
        cancelButton.onclick = hideDialog ;


        form.appendChild(theSaveButton);
        form.appendChild(document.createTextNode("   "));
        form.appendChild(cancelButton);


        div.appendChild(h2);
        div.appendChild(rootPane);

        rootPane.innerHTML = "";
        rootPane.appendChild(theNameH3);
        rootPane.appendChild(form);

        document.body.appendChild(div);
        theDialog = new msh.widget.Dialog(div);
        theRootPane = rootPane;

        h2.onclick = hideDialog;
    }
}

