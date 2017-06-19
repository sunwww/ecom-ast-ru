<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="roles" required="true" description="Роли" %>

<msh:ifInRole roles="${roles}">

<style type="text/css">
    #${name}PrescriptionReport {
        visibility: hidden ;
        display: none ;
        position: absolute;
    }
</style>

<div id='${name}PrescriptionReportDialog' class='dialog'>

<div id='${name}PrescriptionReportDataDiv'>

</div>

 <input id="cancel" value="Закрыть" onclick="cancel${name}PrescriptionReport()" type="button">
 </div>
<script type="text/javascript" src="./dwr/interface/PrescriptionService.js"></script>
<script type="text/javascript">
//var str;
var the${name}PrescriptionReportDialog = new msh.widget.Dialog($('${name}PrescriptionReportDialog')) ;

// Показать
function show${name}PrescriptionReport(id) {

  // var Div = document.querySelector('#${name}PrescriptionReportDialog');
  // var div2 = $('${name}PrescriptionReportDataDiv');
   
   /*var div2 = document.createElement('div');
   div2.id="tempdiv";*/
 
	getInfo(id);
	   
  /* if(create==0) {
       document.getElementById("cancel").style.display = "none";
   } else {
       document.getElementById("cancel").style.display ="";
   }*/
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

            var splits = aResult.split('|');
            var html = "<table border='1'>";
            if (splits.length>1) {
                var arr = ['Создание назначения: ','Забор биоматериала: ','Передача в лабораторию: ','Выполнение анализа: ','Подтверждение анализа: ','Информация о браке: '];
                for (var i=0;i<splits.length;i++) {
                    html+="<tr><td>"+arr[i]+"</td><td>"+splits[i]+"</td></tr>";
				}
			} else {
                html += "<tr><td colspan='2'>Информация не найдена</td></tr>";
			}
			$('${name}PrescriptionReportDataDiv').innerHTML = html;
            the${name}PrescriptionReportDialog.show() ;
		}
	});
}

</script>
</msh:ifInRole>