<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>


<%@ attribute name="autoCompliteServiceFind" required="false" description="Дата" %>


<style type="text/css">
    #${name}ServiceFindDialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
    #${name}Lastname {
    	 
    }
</style>

<div id='${name}ServiceFindDialog' class='dialog'>
    <h2 id="${name}Title"></h2>
    <div class='rootPane'>
    
<form action="javascript:save${name}ServiceFind()">
    <msh:panel styleId="panel${name}" >
    	<input type="hidden"  name="${name}Table" id="${name}Table" />
    	<input type="hidden"  name="${name}Table1" id="${name}Table1" />
    	<input type="hidden"  name="${name}ServiceId" id="${name}ServiceId" />
    	<input type="hidden"  name="${name}JavaFunction" id="${name}JavaFunction" />
    	<msh:row>
    		<msh:autoComplete property="${name}PriceList" label="Прейскурант" vocName="priceList" fieldColSpan="3" size="100"/>
    	</msh:row>
    	<msh:row>
    		<msh:textField property="${name}Code" label="Код" fieldColSpan="3" horizontalFill="true"/>
    	</msh:row>
    	<msh:row>
    		<msh:textField property="${name}Name" label="Наименование" fieldColSpan="3" horizontalFill="true"/>
    	</msh:row>
    </msh:panel>
    	<msh:row>
    		<td colspan="6"> 
    			<div id="${name}errorDiv" style="background-color: #FFF8E7;"></div>
    		</td>
    	</msh:row>
        <div id='div${name}Button'>
        <msh:row>
            <td colspan="6">
                <input type="button" value='Поиск' id="${name}Ok"  onclick="javascript:save${name}ServiceFind()"/>
                <input type="button" value='Отменить' id="${name}Cancel" onclick='javascript:cancel${name}ServiceFind()'/>
            </td>
        </msh:row>
        </div>
</form>

</div>
</div>

<script type='text/javascript' src='./dwr/interface/ContractService.js'></script>
<script type="text/javascript"><!--
     var theIs${name}ServiceFindDialogInitialized = false ;
     var the${name}ServiceFindDialog = new msh.widget.Dialog($('${name}ServiceFindDialog')) ;

     // Показать
     function show${name}ServiceFind(aId,aTable,aTable1,aFunction) {
    	 // устанавливается инициализация для диалогового окна
         if (!theIs${name}ServiceFindDialogInitialized) {
         	init${name}ServiceFindDialog() ;
          }
         if (aTable=="PricePosition") {
        	 //javascript:show${name}ServiceFind()
        	 $('${name}Title').innerHTML="Услуги по прейскуранту" ;
             $("${name}PriceListName").focus() ;
             $("${name}PriceListName").select() ;
         } else if (aTable=="VocMedService") {
        	 $('${name}Title').innerHTML="Услуги по ОМС" ;
             $("${name}Code").focus() ;
             $("${name}Code").select() ;
         } else if (aTable=="MedService") {
        	 $('${name}Title').innerHTML="Услуги по внутр. справочник" ;
             $("${name}Code").focus() ;
             $("${name}Code").select() 
         }
         if (aFunction==null || aFunction=="") {
        	 if ('${autoCompliteServiceFind}'!='') {
            	 $('${name}JavaFunction').value="update${name}Autocomplete"
        	 } else {
            	 $('${name}JavaFunction').value="update${name}Service" ;
        	 }
         } else {
        	 $('${name}JavaFunction').value=aFunction ;
         }
         the${name}ServiceFindDialog.show() ;
         $('${name}Table').value=aTable ;
         $('${name}Table1').value=aTable1 ;
         $('${name}ServiceId').value=aId ;
         

     }

     // Отмена
     function cancel${name}ServiceFind() {
         the${name}ServiceFindDialog.hide() ;
     }

     // Сохранение данных
     function save${name}ServiceFind() {
     	if (${name}checkRequered('${name}Code') 
     		|| ${name}checkRequered('${name}Name')
     		)
     		 {
		     	check${name}ServiceFind($('${name}JavaFunction').value,$('${name}PriceList').value,$('${name}Code').value,$('${name}Name').value,$('${name}Table').value,$('${name}Table1').value,$('${name}ServiceId').value) ;
             }
     }
     function edit${name}ServiceFind() {
		     		 		$('panel${name}').style.display="" ;
		     		 		$('div${name}Button').style.display="" ;
		     		 		$('${name}errorDiv').innerHTML=""
     
     }
     function check${name}ServiceFind(aJavaFunction,aPriceList,aCode,aName,aTable,aTable1,aServiceId) {
    	ContractService.findService(
     			aTable,aServiceId,aTable1,aPriceList,aCode,aName
		     			,'javascript:'+aJavaFunction
		     		 ,{
		     		 callback: function(aString) {
		     		 	
		     		 	if (aString==null || aString=="") {
		     		 		alert("<h2>НЕТ ДАННЫХ!!!</h2>") ;
		     		 		edit${name}ServiceFind() ;
		     		 	} else {
		     		 		$('panel${name}').style.display="none" ;
		     		 		$('div${name}Button').style.display="none" ;
		     		 		$('${name}errorDiv').innerHTML = "<h2>СПИСОК УСЛУГ!!!</h2>"
		     		 			+aString
		     		 			+'<br/><i>Выберите услугу</i><br/>'
		     		 			+'<button onclick="edit${name}ServiceFind()">Изменить данные</button>' ;

		     		 		
		     		 		return false ;
		     		 	}
		     		 }
		     		 }) ;
     }
     function update${name}Autocomplete(aTable,aId,aTable1,aId1,aName) {
    	 if ('${autoCompliteServiceFind}'!='') {
     	 	if (+aId>0) {
     	 		$('${autoCompliteServiceFind}').value =  aId;
     			$('${autoCompliteServiceFind}Name').value =aName ;
     			the${name}ServiceFindDialog.hide() ;
     	 	} 
     	 }
     }
     function update${name}Service(aTable,aId,aTable1,aId1) {
    	 ContractService.updateService(
    			 aTable,aId,aTable1,aId1
       		 ,{
		     		 callback: function(aString) {
		     			 alert('Обновлено') ;
		     		 }
      			}) ;
     }
     function ${name}checkRequered(aField,aFieldView) {
     	if ($(aField).value=="" || $(aField).value==0) {
	     	if (aFieldView==null || aFieldView=="") {
		     	$(aField).focus() ;
		     	$(aField).select() ;
	     	} else {
		     	$(aFieldView).focus() ;
		     	$(aFieldView).select() ;
	     	}
     		return false ;
     	} 
     		return true
     	
     }

     // инициализация диалогового окна
     function init${name}ServiceFindDialog() {
         $("${name}Code").className=$("${name}Code").className+" upperCase " ;
         $("${name}Name").className=$("${name}Name").className+" required upperCase " ;
         eventutil.addEnterSupport('${name}Name', '${name}Ok') ;
     	theIs${name}ServiceFindDialogInitialized = true ;
     }
</script>