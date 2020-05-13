<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>


<%@ attribute name="name" required="true" description="" %>
<%@ attribute name="flatNumber" required="true" description="" %>
<%@ attribute name="houseNumber" required="true" description="" %>
<%@ attribute name="houseBuilding" required="true" description="" %>

<%@ attribute name="addressField" required="true" description="" %>

<%@ attribute name="territory" required="true" description="" %>
<%@ attribute name="region" required="true" description="" %>
<%@ attribute name="typeSettlement" required="true" description="" %>
<%@ attribute name="settlement" required="true" description="" %>
<%@ attribute name="typeStreet" required="true" description="" %>
<%@ attribute name="street" required="true" description="" %>
<%@ attribute name="zipcode" required="true" description="" %>

<%@ attribute name="form" required="true" description="" %>

<style type="text/css">
    #${name}addressNonresidentDialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }

</style>

<div id='${name}addressNonresidentDialog' class='dialog'>
    <h2>Ввод иногороднего адреса (по месту регистрации)</h2>
    <div class='rootPane'>


<form>
    <msh:panel colsWidth="1%,1%,1%,1%,1%,1%,1%,1%">
        <msh:row>
            <msh:comboBox size='40' horizontalFill="true" property='${name}addressNonresidentTerritory' vocName="omcKodTer" label='Территория регистрации' fieldColSpan="7"/>
        </msh:row>
        <msh:row>
            <msh:textField horizontalFill="true" fieldColSpan="7" label="Район проживания" property="${name}addressNonresidentRegion" />
        </msh:row>
        <msh:row>
            <msh:comboBox horizontalFill="true" property='${name}addressNonresidentTypeSettlement' vocName="omcQnp" label='Вид населенного пункта' fieldColSpan="7" />
        </msh:row>
        <msh:row>
            <msh:textField horizontalFill="true" fieldColSpan="7" label="Населенный пункт" property="${name}addressNonresidentSettlement" />
        </msh:row>
        <msh:row>
            <msh:comboBox horizontalFill="true" property='${name}addressNonresidentTypeStreet' vocName="omcStreetT" label='Тип улицы' fieldColSpan="7" />
        </msh:row>
        <msh:row>
            <msh:textField horizontalFill="true" fieldColSpan="7" label="Наименование улицы" property="${name}addressNonresidentStreet" />
        </msh:row>





        <msh:row>
            <msh:textField property='${name}addressNonresidentZipCode' label='Индекс:' size='6'/>
            <msh:textField property='${name}addressNonresidentHouseNumber' label='Дом:' size='5'/>
            <msh:textField property='${name}addressNonresidentHouseBuilding' label='Корпус:' size='5'/>
            <msh:textField property='${name}addressNonresidentFlatNumber' label='Квартира:' size='5'/>
        </msh:row>
    </msh:panel>
        <msh:row>
            <td colspan="6">
                <input type="button" id='${name}buttonAddressOk' value='OK' onclick='save${name}addressNonresident()'/>
                <input type="button" value='Отменить' onclick='cancel${name}addressNonresident()'/>
            </td>
        </msh:row>
</form>

</div>
</div>


<script type="text/javascript">


    
    

    var the${name}addressNonresidentDialog = new msh.widget.Dialog($('${name}addressNonresidentDialog')) ;
    var the${name}Fields ;
    var theIs${name}addressNonresidentDialogInitialized = false ;

    function show${name}addressNonresident() {
        if(!theIs${name}addressNonresidentDialogInitialized) {
            
            $('${name}addressNonresidentHouseNumber').value = $('${houseNumber}').value ;
            $('${name}addressNonresidentHouseBuilding').value = $('${houseBuilding}').value ;
            $('${name}addressNonresidentFlatNumber').value = $('${flatNumber}').value ;
	        if ($('${territory}').value!="" && $('${territory}').value>0) ${name}addressNonresidentTerritoryAutocomplete.setVocId($('${territory}').value)  ;
	        $('${name}addressNonresidentRegion').value = $('${region}').value ;
	        $('${name}addressNonresidentRegion').className = $('${name}addressNonresidentRegion').className+" upperCase" ;
	        if ($('${typeSettlement}').value!="" && $('${typeSettlement}').value>0) ${name}addressNonresidentTypeSettlementAutocomplete.setVocId($('${typeSettlement}').value) ;
	        $('${name}addressNonresidentSettlement').value = $('${settlement}').value ;
	        $('${name}addressNonresidentSettlement').className = $('${name}addressNonresidentSettlement').className + " upperCase" ;
	        if ($('${typeStreet}').value!="" && $('${typeStreet}').value>0) ${name}addressNonresidentTypeStreetAutocomplete.setVocId($('${typeStreet}').value)  ;
	        $('${name}addressNonresidentZipCode').value = $('${zipcode}').value ;
	        $('${name}addressNonresidentStreet').value = $('${street}').value ;
	        $('${name}addressNonresidentStreet').className = $('${name}addressNonresidentSettlement').className + " upperCase" ;
            theIs${name}addressNonresidentDialogInitialized = true ;
        }
        the${name}addressNonresidentDialog.show() ;
        $('${name}addressNonresidentTerritoryName').focus() ;
        $('${name}addressNonresidentTerritoryName').select() ;
    }
    
    function cancel${name}addressNonresident() {
        the${name}addressNonresidentDialog.hide() ;
    }

    function save${name}addressNonresident() {
        $('${houseNumber}').value = $('${name}addressNonresidentHouseNumber').value ;
        $('${houseBuilding}').value = $('${name}addressNonresidentHouseBuilding').value ;
        $('${flatNumber}').value = $('${name}addressNonresidentFlatNumber').value ;
        
        $('${territory}').value = $('${name}addressNonresidentTerritory').value ;
        $('${region}').value = $('${name}addressNonresidentRegion').value.toUpperCase() ;
        $('${typeSettlement}').value = $('${name}addressNonresidentTypeSettlement').value ;
        $('${settlement}').value = $('${name}addressNonresidentSettlement').value.toUpperCase() ;
        $('${typeStreet}').value = $('${name}addressNonresidentTypeStreet').value ;
        $('${street}').value = $('${name}addressNonresidentStreet').value.toUpperCase() ;
        $('${zipcode}').value = $('${name}addressNonresidentZipCode').value ;
        
        

        AddressService.getAddressNonresidentString($('${name}addressNonresidentTerritory').value, $('${name}addressNonresidentRegion').value.toUpperCase()
        	, $('${name}addressNonresidentTypeSettlement').value
        	, $('${name}addressNonresidentSettlement').value.toUpperCase()
        	, $('${name}addressNonresidentTypeStreet').value
        	, $('${name}addressNonresidentStreet').value.toUpperCase()
        	, $('${name}addressNonresidentHouseNumber').value
        	, $('${name}addressNonresidentHouseBuilding').value, $('${name}addressNonresidentFlatNumber').value
        	,$('${name}addressNonresidentZipCode').value , {
            callback: function(aString) {
                $('${name}addressNonresidentPar').innerHTML = aString ;
            }
        } ) ;
        
        the${name}addressNonresidentDialog.hide() ;

        $('buttonShow${name}addressNonresident').focus() ;
    }



    function init${name}addressNonresident() {
    
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

        $('${addressField}').parentNode.innerHTML = "<span id='${name}addressNonresidentPar'>"+
                                                 "Получение адреса..."
                +"</span><input id='buttonShow${name}addressNonresident' type='button' value='"+inputLabel+"' onclick='show${name}addressNonresident()' />" ;

        AddressService.getAddressNonresidentString($('${territory}').value
		        , $('${region}').value
		        , $('${typeSettlement}').value
		        , $('${settlement}').value
		        , $('${typeStreet}').value
		        , $('${street}').value
                , $('${houseNumber}').value, $('${houseBuilding}').value, $('${flatNumber}').value,$('${zipcode}').value, {
            callback: function(aString) {
                $('${name}addressNonresidentPar').innerHTML = aString ;
            }
        } ) ;


        eventutil.addEnterSupport('${name}addressNonresidentFlatNumber', '${name}buttonAddressOk') ;

    }

    init${name}addressNonresident() ;


</script>

    <msh:ifFormTypeIsView formName="${form}">
      <script type="text/javascript">// <![CDATA[//
      		$('buttonShow${name}addressNonresident').style.display = 'none';
		//]]></script>
    </msh:ifFormTypeIsView>


