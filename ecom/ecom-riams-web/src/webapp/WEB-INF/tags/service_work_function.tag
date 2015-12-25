<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>



<style type="text/css">
    #${name}ServiceWorkFunctionDialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}ServiceWorkFunctionDialog' class='dialog'>
    <h2>Параметры прикрепления к услуге</h2>
    <div class='rootPane'>
    
<form action="javascript:save${name}ServiceWorkFunction()">
    <msh:panel styleId="panel${name}" >
    	<input type="hidden" name="${name}MedServiceId" id="${name}MedServiceId" />
    	<msh:row>
          <msh:autoComplete size="100" vocName="vocWorkFunction" property="${name}vocWorkFunction" label="Раб. функция" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete size="100" vocName="serviceWorkFunction" property="${name}workFunction" label="Специалист (группа)" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete size="100" vocName="lpu" property="${name}lpu" label="ЛПУ" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete size="100" vocName="vocBedType" property="${name}bedType" label="Профиль коек" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete size="100" vocName="vocBedSubType" property="${name}bedSubType" label="Тип коек" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete size="100" vocName="vocRoomType" property="${name}roomType" label="Уровень палат" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete size="100" property="${name}prescriptType" vocName="vocPrescriptType" fieldColSpan="3" horizontalFill="true" label="Тип назначения"/>
        </msh:row>
        <msh:row>
        	<msh:checkBox property="${name}noActiveByPrescript" fieldColSpan="3" label="Не отображать в назначениях"/>
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
                <input type="button" value='Добавить прикрепление' id="${name}Ok"  onclick="javascript:save${name}ServiceWorkFunction()"/>
                <input type="button" value='Отменить' id="${name}Cancel" onclick='javascript:cancel${name}ServiceWorkFunction()'/>
            </td>
        </msh:row>
        </div>
</form>

</div>
</div>

<script type='text/javascript' src='./dwr/interface/ContractService.js'></script>
<script type="text/javascript"><!--
     var theIs${name}ServiceWorkFunctionDialogInitialized = false ;
     var the${name}ServiceWorkFunctionDialog = new msh.widget.Dialog($('${name}ServiceWorkFunctionDialog')) ;

     // Показать
     function show${name}ServiceWorkFunction(aMedServiceId) {
         // устанавливается инициализация для диалогового окна
         if (!theIs${name}ServiceWorkFunctionDialogInitialized) {
         	init${name}ServiceWorkFunctionDialog() ;
          }
         the${name}ServiceWorkFunctionDialog.show() ;
         $('${name}MedServiceId').value=aMedServiceId ;
         $("${name}vocWorkFunctionName").focus() ;
         $("${name}vocWorkFunctionName").select() ;
         
         eventutil.addEnterSupport("${name}noActiveByPrescript", '${name}Ok') ;

     }

     // Отмена
     function cancel${name}ServiceWorkFunction() {
         the${name}ServiceWorkFunctionDialog.hide() ;
     }

     // Сохранение данных
     function save${name}ServiceWorkFunction() {
     	if ($('${name}vocWorkFunction').value>0 || $('${name}workFunction').value>0 || $('${name}lpu').value>0
     			||$('${name}bedType').value>0 || $('${name}bedSubType').value>0 || $('${name}roomType').value>0
     			||$('${name}prescriptType').value>0
     		)
     		 {
         	ContractService.saveWorkFunctionService($('${name}MedServiceId').value,
         			$('${name}vocWorkFunction').value,$('${name}workFunction').value, $('${name}lpu').value
         			,$('${name}bedType').value, $('${name}bedSubType').value, $('${name}roomType').value
         			,$('${name}prescriptType').value,
           		 $('${name}noActiveByPrescript').checked?'1':'0'
    		     			
    		     		 ,{
    		     		 callback: function(aString) {
    		     			 //window.document.reload() ;
    		     		 }})
    		     		 
             }
     }

     // инициализация диалогового окна
     function init${name}ServiceWorkFunctionDialog() {
     	/*new dateutil.DateField($('${name}Birthday')) ;
     	//new snilsutil.SnilsField($('${name}Snils'));
     	
         $("${name}Fio").className=$("${name}Fio").className+" upperCase " ;
         $("${name}Lastname").className=$("${name}Lastname").className+" required upperCase " ;
         $("${name}Middlename").className=$("${name}Middlename").className+" required upperCase" ;
         $("${name}Firstname").className=$("${name}Firstname").className+" required upperCase" ;     	
         $("${name}Birthday").className=$("${name}Birthday").className+" required" ;     	
         eventutil.addEnterSupport('${name}Birthday', '${name}Ok') ;
         eventutil.addEventListener($('${name}Fio'),'blur',function(){
		  		${name}UpdateFio();
		  	}) ;*/
     	theIs${name}ServiceWorkFunctionDialogInitialized = true ;
     }
</script>