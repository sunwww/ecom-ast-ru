<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<%@ attribute name="name" required="true" description="Название поля Address" %>
<%@ attribute name="zipcode" required="true" description="" %>
<%@ attribute name="flatNumber" required="true" description="" %>
<%@ attribute name="houseNumber" required="true" description="" %>
<%@ attribute name="houseBuilding" required="true" description="" %>
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
            <msh:comboBox size='40' horizontalFill="true" property='${name}address_1' vocName="Address-1" label='Страна:' fieldColSpan="7"/>
        </msh:row>
        <msh:row>
            <msh:comboBox horizontalFill="true" property='${name}address_2' vocName="Address-2" label='Регион:' fieldColSpan="7" />
        </msh:row>
        <msh:row>
            <msh:comboBox horizontalFill="true" property='${name}address_3' vocName="Address-3" label='Район области:' fieldColSpan="7" />
        </msh:row>
        <msh:row>
            <msh:comboBox horizontalFill="true" property='${name}address_4' vocName="Address-4" label='Город:' fieldColSpan="7" />
        </msh:row>

        <msh:row>
            <msh:comboBox horizontalFill="true" property='${name}address_5' vocName="Address-5" label='Населенный пункт:' fieldColSpan="7" />
        </msh:row>
        <msh:row>
            <msh:comboBox horizontalFill="true" property='${name}address_6' vocName="Address-6" label='Улица:' fieldColSpan="7" />
        </msh:row>



        <msh:row>
            <msh:textField property='${name}addressZipcode' label='Индекс:' size='6'/>
            <msh:textField property='${name}addressHouseNumber' label='Дом:' size='5'/>
            <msh:textField property='${name}addressHouseBuilding' label='Корпус:' size='5'/>
            <msh:textField property='${name}addressFlatNumber' label='Квартира:' size='5'/>
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
    
    function ${name}updateZipcode() {
	   	 	AddressService.getZipcode($('${name}address_5').value
	   	 			,$('${name}address_6').value, {
	            callback: function(aString) {
	                $('${name}addressZipcode').value = aString ;
	             }
	       	} ) ;
    }
    function show${name}Address() {
        if(!theIs${name}AddressDialogInitialized) {
            the${name}Fields = new Array(6) ;
            the${name}Fields[0] = ${name}address_1Autocomplete ;
            the${name}Fields[1] = ${name}address_2Autocomplete ;
            the${name}Fields[2] = ${name}address_3Autocomplete ;
            the${name}Fields[3] = ${name}address_4Autocomplete ;
            the${name}Fields[4] = ${name}address_5Autocomplete ;
            the${name}Fields[5] = ${name}address_6Autocomplete ;

            for(var i=1; i<the${name}Fields.length; i++) {
                the${name}Fields[i].setParent(the${name}Fields[i-1]) ;
            }
            
            ${name}address_6Autocomplete.addOnChangeCallback(function() {
             	 ${name}updateZipcode() ;
             }) ;
            ${name}address_5Autocomplete.addOnChangeCallback(function() {
             	 ${name}updateZipcode() ;
             }) ;
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
            
            $('${name}addressHouseNumber').value = $('${houseNumber}').value ;
            $('${name}addressHouseBuilding').value = $('${houseBuilding}').value ;
            $('${name}addressFlatNumber').value = $('${flatNumber}').value ;
            if ('${zipcode}'!='') $('${name}addressZipcode').value = $('${zipcode}').value ;
            theIs${name}AddressDialogInitialized = true ;
        }
        the${name}AddressDialog.show() ;
        $('${name}address_6Name').focus() ;
        $('${name}address_6Name').select() ;
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

    function update${name}ProvincialArea() {
        return;
  /*      if(true) return ;
        AddressService.getFondRegionForAddress($('address').value, {
            callback: function(aRegionId) {
                if(aRegionId!=null && aRegionId!="") {
                    provincialAreaPkAutocomplete.setVocId(aRegionId) ;
                    $('provincialAreaPkName').disabled = true ;
                } else {
                    $('provincialAreaPkName').disabled = false ;
                }
            }
        } ) ; */
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
        $('${houseNumber}').value = $('${name}addressHouseNumber').value ;
        $('${houseBuilding}').value = $('${name}addressHouseBuilding').value ;
        $('${flatNumber}').value = $('${name}addressFlatNumber').value ;
        if ('${zipcode}'!='') $('${zipcode}').value = $('${name}addressZipcode').value ;
        AddressService.getAddressString(addressPk, $('${name}addressHouseNumber').value, $('${name}addressHouseBuilding').value, $('${name}addressFlatNumber').value
        , $('${name}addressZipcode').value
        , {
            callback: function(aString) {
                $('${name}addressPar').innerHTML = aString ;
            }
        } ) ;
        the${name}AddressDialog.hide() ;
        $('${name}').value = addressPk ;
        //updateProvincialArea() ;
        $('buttonShow${name}Address').focus() ;
       }

    function init${name}Address() {
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
		var zipcode = '${zipcode}' ;
		if (zipcode!='') zipcode=$(zipcode).value ;
        AddressService.getAddressString($('${name}').value
                , $('${houseNumber}').value, $('${houseBuilding}').value, $('${flatNumber}').value,zipcode, {
            callback: function(aString) {
                $('${name}addressPar').innerHTML = aString ;
            }
        } ) ;
        eventutil.addEnterSupport('${name}addressFlatNumber', '${name}buttonAddressOk') ;
    }
    init${name}Address() ;

</script>

    <msh:ifFormTypeIsView formName="${form}">
      <script type="text/javascript">
      		$('buttonShow${name}Address').style.display = 'none';
		</script>
    </msh:ifFormTypeIsView>