<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>


<style type="text/css">
    #${name}EquipmentChangeTypeDialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
    #${name}Job {
	    text-transform: uppercase;
    }
</style>

<div id='${name}EquipmentChangeTypeDialog' class='dialog'>
    <h2>Выбор типа оборудования</h2>
    <div class='rootPane'>
    
<form action="javascript:">
    <msh:panel>
    	
        <msh:row>
        	<msh:autoComplete label="Тип оборудования:" property="${name}changeType" vocName="vocEquipmentTypeByLpu" parentId="${depId}" size="50"   horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        
    </msh:panel>
        <msh:row>
            <td colspan="6">
                <input type="button" value='Сохранить'  onclick="javascript:save${name}changeType(0)"/>
                <input type="button" value='Сохранить и обновить'  onclick="javascript:save${name}changeType(1)"/>
                <input type="button" value='Отменить' onclick='cancel${name}changeType()'/>
            </td>
        </msh:row>
</form>

</div>
</div>
<script type="text/javascript" src="./dwr/interface/EquipmentService.js"></script>
<script type="text/javascript"><!--
     var theIs${name}EquipmentChangeTypeDialogInitialized = false ;
     var the${name}EquipTypeId = null;
     var the${name}EquipmentChangeTypeDialog = new msh.widget.Dialog($('${name}EquipmentChangeTypeDialog')) ;
     // Показать
     function show${name}DuplicateDocument(aId,aa) {
    	 EquipmentService.getEquipmentType(aId, {
    		 callback: function (a) {
    			 if (a!='') {
    				 a = a.split("@");
    				 $('${name}changeType').value=a[0];
    				 $('${name}changeTypeName').value=a[1];
    			 }
    		 }
    	 });
    	 the${name}EquipTypeId = aId;
         // устанавливается инициализация для диалогового окна
         
         the${name}EquipmentChangeTypeDialog.show() ;
         $("${name}Number").focus() ;

     }

     // Отмена
     function cancel${name}changeType() {
         the${name}EquipmentChangeTypeDialog.hide() ;
     }

     // Сохранение данных
     function save${name}changeType(update) {
    	 if (the${name}EquipTypeId==null) {
    		 alert ("Что-то пошло не так");
    		 return;
    	 }
     	if ($('${name}changeType').value=="") {
     		alert("Поле тип является обязательным") ;
     		$("${name}changeTypeName").focus() ;
     	} else {
     		EquipmentService.setEquipmentType(the${name}EquipTypeId, $('${name}changeType').value, {
	                   callback: function(aString) {
	                	   if (update==1) {
	                		   document.location.reload();  
	                	   }
	                	   
	                    }
	                }
	         ) ;
     		cancel${name}changeType();
     		the${name}EquipTypeId=null;
     		
         }
     }
</script>