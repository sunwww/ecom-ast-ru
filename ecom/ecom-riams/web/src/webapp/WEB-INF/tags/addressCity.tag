<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<%@ attribute name="name" required="true" description="Название поля Address" %>
<%@ attribute name="addressField" required="true" description="" %>
<%@ attribute name="form" required="true" description="" %>




<style type="text/css">
    #${name}addressDialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }

</style>

<div id='${name}addressDialog' class='dialog'>
    <h2>Ввод адреса</h2>
    <div class='rootPane'>


<form>
    <msh:panel>
        <msh:row>
            <msh:comboBox size='40' horizontalFill="true" property='${name}address_1' vocName="Address-1" label='Страна:' fieldColSpan="5"/>
        </msh:row>
        <msh:row>
            <msh:comboBox horizontalFill="true" property='${name}address_2' vocName="Address-2" label='Регион:' fieldColSpan="5" />
        </msh:row>
        <msh:row>
            <msh:comboBox horizontalFill="true" property='${name}address_3' vocName="Address-3" label='Район области:' fieldColSpan="5" />
        </msh:row>
        <msh:row>
            <msh:comboBox horizontalFill="true" property='${name}address_4' vocName="Address-4" label='Город:' fieldColSpan="5" />
        </msh:row>

        <msh:row>
            <msh:comboBox horizontalFill="true" property='${name}address_5' vocName="Address-5" label='Населенный пункт:' fieldColSpan="5" />
        </msh:row>



    </msh:panel>
        <msh:row>
            <td colspan="6">
                <input type="button" id='${name}buttonAddressOk' value='OK' onclick='save${name}Address()'/>
                <input type="button" value='Отменить' onclick='cancel${name}Address()'/>
            </td>
        </msh:row>
</form>

</div>
</div>

<script src='./dwr/interface/AddressService.js' type="text/javascript"></script>
<script type="text/javascript">


    ${name}FormField = function(theId, theAutocomplete, theInputField) {


        this.loadValue = function () {
            if(theAutocomplete) {
                theAutocomplete.setVocId($(theId).value) ;
            } else {
                $(theInputField).value = $(theId).value ;
            }
        }

        this.saveValue = function (aStr) {
            var ret = aStr!=null ? aStr +", " : "" ;
            var id ;
            var name ;
            if(theAutocomplete) {
                name = theAutocomplete.getVocName() ;
                id = theAutocomplete.getVocId() ;
            } else {
                name = $(theInputField).value ;
                id = $(theInputField).value ;
            }
            ret = ret + name ;
            $(theId).value = id ;
            return ret ;
        }
    }
    

    var the${name}AddressDialog = new msh.widget.Dialog($('${name}addressDialog')) ;
    var the${name}Fields ;
    var theIs${name}AddressDialogInitialized = false ;

    function show${name}Address() {
        if(!theIs${name}AddressDialogInitialized) {
        
            the${name}Fields = new Array(5) ;
            the${name}Fields[0] = ${name}address_1Autocomplete ;
            the${name}Fields[1] = ${name}address_2Autocomplete ;
            the${name}Fields[2] = ${name}address_3Autocomplete ;
            the${name}Fields[3] = ${name}address_4Autocomplete ;
            the${name}Fields[4] = ${name}address_5Autocomplete ;
            //the${name}Fields[5] = ${name}address_6Autocomplete ;

            for(var i=1; i<the${name}Fields.length; i++) {
                the${name}Fields[i].setParent(the${name}Fields[i-1]) ;
            }

            var addressPkValue = $('${name}').value ;
            if(addressPkValue=="" || addressPkValue==null || addressPkValue==0) {
                var cookieValue = getCookie("defaultAddress") ;
                if(cookieValue!=null || cookieValue!="") {
                    addressPkValue = cookieValue ;
                }
            }
            for(var i=0; i<the${name}Fields.length; i++) {
                set${name}IdForLevel(i+1, addressPkValue) ;
            }
            
            //addressProvincialAreaPkAutocomplete.setVocId($('provincialAreaPk')) ;


            theIs${name}AddressDialogInitialized = true ;
        }
        the${name}AddressDialog.show() ;
        $('${name}address_5Name').focus() ;
        $('${name}address_5Name').select() ;
//        var ar = createArray() ;
//        for(var i=0; i<ar.length; i++) {
//            ar[i].loadValue() ;
//        }
//        addressCountryPkAutocomplete.requestFocus() ;
    }

    function set${name}IdForLevel(aLevel, aAddressPk) {
        AddressService.getIdForLevel(aLevel, aAddressPk, {
            callback: function(aId) {
                the${name}Fields[aLevel-1].setVocId(aId) ;
            }
        } ) ;

    }
    function cancel${name}Address() {
        the${name}AddressDialog.hide() ;
    }

    function save${name}Address() {
        var addressPk = "" ;
        var defaultAddress = "" ;
        var sb = "" ;
        for(var i=0; i<the${name}Fields.length; i++) {
            if(the${name}Fields[i].getVocId()!="") {
                sb = sb + ", "+the${name}Fields[i].getVocName();
                addressPk = the${name}Fields[i].getVocId() ;
                if(i<5) {
                    defaultAddress = the${name}Fields[i].getVocId() ;
                }
            }
        }

        setCookie("defaultAddress",defaultAddress) ;
        //$('${houseNumber}').value = $('${name}addressHouseNumber').value ;
        //$('${houseBuilding}').value = $('${name}addressHouseBuilding').value ;
        //$('${flatNumber}').value = $('${name}addressFlatNumber').value ;
//        $('provincialAreaPk').value = addressProvincialAreaPkAutocomplete.getVocId() ;
        AddressService.getAddressString(addressPk, '', '', '','', {
            callback: function(aString) {
                $('${name}addressPar').innerHTML = aString ;
            }
        } ) ;


        the${name}AddressDialog.hide() ;
        $('${name}').value = addressPk ;
        $('buttonShow${name}Address').focus() ;
    }



    function init${name}Address() {
        var address = document.createElement("p") ;
        var inputLabel = "Ввести" ;
        var val = $('${addressField}').value ;
        if(val!=null && val.length>0) {
            var finded = false ;
            for(var i=0; i<val.length; i++) {
                if(val.charAt(i)!=" ") {
                    finded = true ;
                }
            }
            inputLabel = "Изменить" ;
        }

        $('${addressField}').parentNode.innerHTML = "<span id='${name}addressPar'>"+
                                                 "Получение адреса..."
                +"</span><input id='buttonShow${name}Address' type='button' value='"+inputLabel+"' onclick='show${name}Address()' />" ;
        AddressService.getAddressString($('${name}').value
                , '', '', '','', {
            callback: function(aString) {
                $('${name}addressPar').innerHTML = aString ;
            }
        } ) ;


        //eventutil.addEnterSupport('${name}addressFlatNumber', '${name}buttonAddressOk')

    }

    init${name}Address() ;
    //window.onload =    updateProvincialArea ;
	
	//updateProvincialArea() ;

</script>

    <msh:ifFormTypeIsView formName="${form}">
      <script type="text/javascript">
      		$('buttonShow${name}Address').style.display = 'none';
		</script>
    </msh:ifFormTypeIsView>


