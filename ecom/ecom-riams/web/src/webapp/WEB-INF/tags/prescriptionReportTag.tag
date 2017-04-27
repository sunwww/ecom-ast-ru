<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="title" required="true" description="Заголовок" %>
<%@ attribute name="roles" required="true" description="Роли" %>
<%@ attribute name="field" required="true" description="Название поля" %>

<msh:ifInRole roles="${roles}">
<msh:sideLink name="${title}" action=" javascript:show${name}PrescriptionReport('.do') " /> 

<style type="text/css">
    #${name}PrescriptionReport {
        visibility: hidden ;
        display: none ;
        position: absolute;
    }
</style>

<div id='${name}PrescriptionReportDialog' class='dialog'>

<div id='tempDiv'>

</div>
 <!-- <msh:section title='Результат поиска'>
            	<ecom:webQuery name="listPres" nativeSql="select * from patient where id=${param.id} limit 10"/>
            	 <msh:table name="listPres" action="javascript:void(0)" idField="1">
            	 <msh:tableColumn columnName="Дата забора" property="2"/>
            	 <msh:tableColumn columnName="Кто проводил забор" property="3"/>
            	 <msh:tableColumn columnName="Дата передачи" property="4"/>
            	 <msh:tableColumn columnName="Кто передал" property="5"/>
            	 <msh:tableColumn columnName="Дата отмены" property="6"/>
            	 <msh:tableColumn columnName="Причина отмены" property="7"/>
            	 <msh:tableColumn columnName="Кто отменил" property="8"/>
            	 </msh:table>
            </msh:section>
            -->
 <input id="cancel" value="Закрыть" onclick="cancel${name}PrescriptionReport()" type="button">
 </div>
<script type="text/javascript" src="./dwr/interface/PrescriptionService.js"></script>
<script type="text/javascript">
var str;
var theIs${name}PrescriptionReportDialogInitialized = false ;
var the${name}PrescriptionReportDialog = new msh.widget.Dialog($('${name}PrescriptionReportDialog')) ;

// инициализация диалогового окна
function init${name}PrescriptionReportDialog() {
	theIs${name}PrescriptionReportDialogInitialized = true ;
}


// Показать
function show${name}PrescriptionReport(id,create) {
   if (!theIs${name}PrescriptionReportDialogInitialized) {
       init${name}PrescriptionReportDialog() ;
   }
   the${name}PrescriptionReportDialog.show() ;
   
   var Div = document.querySelector('#${name}PrescriptionReportDialog');
   var Div2 = document.querySelector('#tempDiv');
   
   /*var div2 = document.createElement('div');
   div2.id="tempdiv";*/
 
   getInfo(id);
   var splits = str.split('|')
   Div2.innerHTML = "<table border='1'>"+
		"<tr>"+
		"<th>Дата забора</th>"+
		"<th>Кто проводил забор</th>"+
		"<th>Дата передачи</th>"+
		"<th>Кто передал</th>"+
		"<th>Дата отмены</th>"+
		"<th>Причина отмены</th>"+
		"<th>Кто отменил</th>"+
		"</tr><tr>"+
	   "<td>"+splits[0]+"</td>"+
	   "<td>"+splits[1]+"</td>"+
	   "<td>"+splits[2]+"</td>"+
	   "<td>"+splits[3]+"</td>"+
	   "<td>"+splits[4]+"</td>"+
	   "<td>"+splits[5]+"</td>"+
	   "<td>"+splits[6]+"</td>"+
	   "</tr></tr></table>";
	   
   if(create==0)
   	document.getElementById("cancel").style.display = "none";
   
   if(create!=0)
   	document.getElementById("cancel").style.display ="";

}

// Отмена
function cancel${name}PrescriptionReport() {
	/* var div1 = document.querySelector('#${name}PrescriptionReportDialog');
	 var div2 = document.querySelector('#tempdiv');
	 div.removeChild(div2);*/
    the${name}PrescriptionReportDialog.hide() ;
    
}


function getInfo(aPrescriptionId) {

	PrescriptionService.getInfoAboutPrescription(aPrescriptionId, {
		callback : function(aResult) {
			str= aResult;
		}
	});
}

</script>
</msh:ifInRole>