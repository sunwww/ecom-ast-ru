<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ attribute name="nextField" required="false" description="" %>
<%@ attribute name="fieldRayon" required="false" description="" %>

<style type="text/css">
    #addressDialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }

</style>

<div id='addressDialog' class='dialog'>
    <h2>Ввод адреса</h2>
    <div class='rootPane'>


<form>
    <msh:panel>
        <msh:row>
            <msh:comboBox size='40' horizontalFill="true" property='address_1' vocName="Address-1" label='Страна:' fieldColSpan="7"/>
        </msh:row>
        <msh:row>
            <msh:comboBox horizontalFill="true" property='address_2' vocName="Address-2" label='Регион:' fieldColSpan="7"/>
        </msh:row>
        <msh:row>
            <msh:comboBox horizontalFill="true" property='address_3' vocName="Address-3" label='Район области:' fieldColSpan="7"/>
        </msh:row>
        <msh:row>
            <msh:comboBox horizontalFill="true" property='address_4' vocName="Address-4" label='Город:' fieldColSpan="7"/>
        </msh:row>

        <msh:row>
            <msh:comboBox horizontalFill="true" property='address_5' vocName="Address-5" label='Населенный пункт:' fieldColSpan="7"/>
        </msh:row>
        <msh:row>
            <msh:comboBox horizontalFill="true" property='address_6' vocName="Address-6" label='Улица:' fieldColSpan="7"/>
        </msh:row>



        <msh:row>
            <msh:textField property='addressZipcode' label='Индекс:' size='6'/>
            <msh:textField property='addressHouseNumber' label='Дом:' size='5'/>
            <msh:textField property='addressHouseBuilding' label='Корпус:' size='5'/>
            <msh:textField property='addressFlatNumber1' label='Квартира:' size='5'/>
        </msh:row>
    </msh:panel>
        <msh:row>
            <td colspan="6">
            	
                <input type="button" name="buttonSaveAddressOk" id='buttonSaveAddressOk' value='OK' 
                onclick='return saveAddress()' autocomplete="off"/>
                <input type="button" value='Отменить' onclick='cancelAddress()'  autocomplete="off"/>
            </td>
        </msh:row>
</form>

</div>
</div>

<script type="text/javascript" src='./dwr/interface/AddressService.js' ></script>
<script type="text/javascript">


    FormField = function(theId, theAutocomplete, theInputField) {


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

    var theAddressDialog = new msh.widget.Dialog($('addressDialog')) ;
    var theFields ;
    var theIsAddressDialogInitialized = false ;
    
    function reloadAddress() {
    	theIsAddressDialogInitialized=false ;
		$('addressFieldRow').innerHTML='<input title="АдресNoneField" class=" horizontalFill" id="addressField" name="addressField" size="10" value="Адрес... " type="text" />' ;
		initAddress() ;    	
    }
    function showAddress() {
        if(!theIsAddressDialogInitialized) {
            theFields = new Array(6) ;
            theFields[0] = address_1Autocomplete ;
            theFields[1] = address_2Autocomplete ;
            theFields[2] = address_3Autocomplete ;
            theFields[3] = address_4Autocomplete ;
            theFields[4] = address_5Autocomplete ;
            theFields[5] = address_6Autocomplete ;

            for(var i=1; i<theFields.length; i++) {
                theFields[i].setParent(theFields[i-1]) ;
            }

            var addressPkValue = $('address').value ;
            if(addressPkValue=="" || addressPkValue==null || addressPkValue==0) {
                var cookieValue = getCookie("defaultAddress") ;
                if(cookieValue!=null || cookieValue!="") {
                    addressPkValue = cookieValue ;
                }
            }
            for(var i=0; i<theFields.length; i++) {
                setIdForLevel(i+1, addressPkValue) ;
            }

            $('addressHouseNumber').value = $('houseNumber').value ;
            $('addressHouseBuilding').value = $('houseBuilding').value ;
            $('addressFlatNumber1').value = $('flatNumber').value ;
            if ($('zipcode')) $('addressZipcode').value = $('zipcode').value ;
            //addressProvincialAreaPkAutocomplete.setVocId($('provincialAreaPk')) ;
            eventutil.addEnterSupport('buttonSaveAddressOk', '${nextField}') ;
            address_6Autocomplete.addOnChangeCallback(function() {
             	 updateZipcode() ;
             }) ;
            //address_5Autocomplete.addOnChangeCallback(function() {
            // 	 updateZipcode() ;
             //}) ;
             
            theIsAddressDialogInitialized = true ;
        }
        theAddressDialog.show() ;
        $('address_6Name').focus() ;
        $('address_6Name').select() ;
//        var ar = createArray() ;
//        for(var i=0; i<ar.length; i++) {
//            ar[i].loadValue() ;
//        }
//        addressCountryPkAutocomplete.requestFocus() ;
    }

    function updateZipcode() {
	   	 	AddressService.getZipcode($('address_5').value
	   	 			,$('address_6').value, {
	            callback: function(aString) {
	                $('addressZipcode').value = aString ;
	                
	             }
	       	} ) ;
    }
    function setIdForLevel(aLevel, aAddressPk) {
        AddressService.getIdForLevel(aLevel, aAddressPk, {
            callback: function(aId) {
                theFields[aLevel-1].setVocId(aId) ;
            }
        } ) ;

    }
    function cancelAddress() {
        theAddressDialog.hide() ;
    }

    function updateProvincialArea() {
        if(true) return ;
        AddressService.getFondRegionForAddress($('address').value, {
            callback: function(aRegionId) {
                if(aRegionId!=null && aRegionId!="") {
                    provincialAreaPkAutocomplete.setVocId(aRegionId) ;
                    $('provincialAreaPkName').disabled = true ;
                } else {
                    $('provincialAreaPkName').disabled = false ;
                }
            }
        } ) ;
    }

    function saveAddress(aEvent,aKeyPress) {
    	//alert(4);
        var addressPk = "" ;
        var defaultAddress = "" ;
        var sb = "" ;
        for(var i=0; i<theFields.length; i++) {
            if(theFields[i].getVocId()!="") {
                sb = sb + ", "+theFields[i].getVocName();
                addressPk = theFields[i].getVocId() ;
                if(i<5) {
                    defaultAddress = theFields[i].getVocId() ;
                }
            }
        }

        setCookie("defaultAddress",defaultAddress) ;
        $('houseNumber').value = $('addressHouseNumber').value ;
        $('houseBuilding').value = $('addressHouseBuilding').value ;
        $('flatNumber').value = $('addressFlatNumber1').value ;
        if ($('zipcode')) $('zipcode').value = $('addressZipcode').value ;
//        $('provincialAreaPk').value = addressProvincialAreaPkAutocomplete.getVocId() ;

        AddressService.getAddressString(addressPk, $('addressHouseNumber').value, $('addressHouseBuilding').value
        		, $('addressFlatNumber1').value,$('addressZipcode').value, {
            callback: function(aString) {
                $('addressPar').innerHTML = aString ;
                //alert('${fieldRayon}') ;
                if ('${fieldRayon}'!='') {
                	AddressService.getAddressRayon(addressPk, $('addressHouseNumber').value
                			,{callback: function(aResult) {
                				//alert(aResult) ;
                				if (aResult!='#' && aResult!='') {
                					var res = aResult.split("#")
                    				$('${fieldRayon}').value= res[0];
                    				$('${fieldRayon}Name').value= res[1];
                    				$('${nextField}').focus() ;
                    				$('${nextField}').select() ;
                				}
                			}}) ;
                	//${nextField}
                
                }
            }
        }) ;


//        var dlg = $('addressDialog') ;
//        dlg.style.visibility = 'hidden' ;
//        dlg.style.display = 'none' ;
        theAddressDialog.hide() ;


        $('address').value = addressPk ;

        updateProvincialArea() ;


        if(onAddressSave) {
            onAddressSave() ;
        }

        $('buttonShowAddress').focus() ;
        
    }




    function initAddress() {

        var address = document.createElement("p") ;
        var inputLabel = "Ввести" ;
        var val = $('addressField').value ;
//        alert("'"+val+"'")
        if(val!=null && val.length>0) {
            var finded = false ;
            for(var i=0; i<val.length; i++) {
                if(val.charAt(i)!=" ") {
                    finded = true ;
                }
            }
            inputLabel = "Изменить" ;
        }

        $('addressField').parentNode.innerHTML = "<span id='addressPar'>"+
                                                 "Получение адреса...<a href='javascript:reloadAddress()'>Обновить</a>"
                +"</span><input id='buttonShowAddress' type='button' value='"+inputLabel+"' onclick='showAddress()' />" ;
                
        var zipcode ="";
        if ($('zipcode')) zipcode=$('zipcode').value ;

        AddressService.getAddressString($('address').value
                , $('houseNumber').value, $('houseBuilding').value, $('flatNumber').value, zipcode,{
            callback: function(aString) {
                $('addressPar').innerHTML = aString ;
            }
        } ) ;
        if ($('addressFlatNumber1').style.display == 'none') {
        	eventutil.addEnterSupport('addressHouseBuilding', 'buttonSaveAddressOk') ;
        } else {
        	eventutil.addEnterSupport('addressFlatNumber1', 'buttonSaveAddressOk') ;
        }
        //eventutil.addEnterSupport('buttonSaveAddressOk', '${nextField}') ;
        

    }

    initAddress() ;
    //window.onload =    updateProvincialArea ;
	updateProvincialArea() ;

</script>


