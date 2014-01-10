<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ attribute name="name" description="название" required="true"%>
<%@ attribute name="roles" required="true" description="Роли" %>
<%@ attribute name="title" required="true" description="Заголовок" %>
<%@ attribute name="confirm" required="false" description="Сообщение" %>
<%@ attribute name="medCase" required="true" description="СМО" %>

<msh:sideLink roles="${roles}" name="${title}" action=" javascript:show${name}Documents('.do') " 
	 />
<style type="text/css">
    #${name}DocumentsPrint {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}DocumentsPrint' class='dialog'>
    <h2>Выбор документов для печати</h2>
    <div class='rootPane'>
		<form>
		    <msh:panel>
		    	<msh:row>
		    		<msh:separator label="Согласия" colSpan="2"/>
		    	</msh:row>
	            <msh:row>
		        	<msh:checkBox property="${name}Consent1" label="с обшим планом лечения и обследования"/>
		        </msh:row>
	            <msh:row>
		        	<msh:checkBox property="${name}Consent2" label="на медицинское (оперативное) вмешательство"/>
		        </msh:row>
	            <msh:row>
		        	<msh:checkBox property="${name}Consent3" label="на переливание крови"/>
		        </msh:row>
	            <msh:row>
		        	<msh:checkBox property="${name}Consent4" label="на установку импланта"/>
		        </msh:row>
<%-- 	            <msh:row>
		        	<msh:checkBox property="${name}Consent5" label="на обработку перс. данных"/>
		        </msh:row>--%>
		    	<msh:row>
		    		<msh:separator label="Направления" colSpan="2"/>
		    	</msh:row>
	            <msh:row>
		        	<msh:checkBox property="${name}Direct1" label="Анкета пациента для МРТ ислледования"/>
		        </msh:row>
	            <msh:row>
		    		<msh:separator label="Отказы" colSpan="2"/>
		    	</msh:row>
	            <msh:row>
		        	<msh:checkBox property="${name}Rejection1" label="от мед. вмешательства (продолжения лечения)"/>
		        </msh:row>
		        <msh:row>
		            <msh:checkBox property="${name}Rejection2" label="от оперативного вмешательства"/>
		        </msh:row>
		    </msh:panel>
		        <msh:row>
		            <td colspan="6">
		                <input type="button" name="${name}DocumentsOk" id='${name}DocumentsOk' value='OK' onclick='save${name}Documents()'/>
		                <input type="button" value='Отменить' onclick='cancel${name}Documents()'/>
		            </td>
		        </msh:row>
		</form>
		
		</div>
</div>
<script type="text/javascript">
     var theIs${name}DocumentsPrintInitialized = false ;
     var the${name}DocumentsPrint = new msh.widget.Dialog($('${name}DocumentsPrint')) ;

     // Показать
     function show${name}Documents() {
         // устанавливается инициализация для диалогового окна
         if (!theIs${name}DocumentsPrintInitialized) {
         	init${name}Documents() ;

         } 
         
         the${name}DocumentsPrint.show() ;
		$("${name}Consent1").focus() ;
		$("${name}Consent1").select() ;
     }

     // Отмена
     function cancel${name}Documents() {
         the${name}DocumentsPrint.hide() ;
     }

     // Сохранение данных
     function save${name}Documents() {
		window.location = 'print-stac_documents.do?s=HospitalPrintService&m=printConsentBySlo&id=${medCase}&consent1='
				+($('${name}Consent1').checked?1:0)
				+"&consent2="+($('${name}Consent2').checked?1:0)
				+"&consent3="+($('${name}Consent3').checked?1:0)
				+"&consent4="+($('${name}Consent4').checked?1:0)
				+"&direct1="+($('${name}Direct1').checked?1:0)
				+"&rejection1="+($('${name}Rejection1').checked?1:0)
				+"&rejection2="+($('${name}Rejection2').checked?1:0);
		cancel${name}Documents() ;
     }
     
     // инициализация диалогового окна выбора шаблона протокола
     function init${name}Documents() {
             theIs${name}DocumentsPrintInitialized = true ;
             
             
     }
</script>
