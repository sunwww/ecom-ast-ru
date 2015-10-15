<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="parentID" required="true" description="ИД родителя" %>
<%@ attribute name="title" required="true" description="Заголовок" %>

<style type="text/css">
    #${name}DiaryDefectDialog {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}DiaryDefectDialog' class='dialog'>
    <h2></h2>
    <div class='rootPane'>
    <h3>${title}</h3>
<form action="javascript:void(0)" name="frm${name}Defects">
    <table id = 'table${name}Defects'>
	</table>
	<table>
	<msh:row>
	<msh:textArea property="${name}Comment" label="Комментарий" rows="2" />
	</msh:row>
	<msh:row>
	<msh:textArea property="${name}Record" label="Протокол" rows="7" />
	</msh:row>
	</table>
	<button onclick="save${name}DiaryDefect()">Отправить врачу сообщение</button>
	<button onclick="cancel${name}DiaryDefect()">отмена</button>
</form>

</div>
</div>

<script type="text/javascript"><!--
     var theIs${name}DiaryId=0 ;
     var theIs${name}DiaryDefectDialogInitialized = false ;
     var the${name}DiaryDefectDialog = new msh.widget.Dialog($('${name}DiaryDefectDialog')) ;
     // Показать 
     function show${name}DiaryDefect(aId) {
    	 //alert(aId) ;
    	 if (theIs${name}DiaryId!=aId){
    		 theIs${name}DiaryId=aId;
    		 theIs${name}DiaryDefectDialogInitialized = false ;
    	 }
    	 //alert(theIs${name}DiaryId) ;
    	 // устанавливается инициализация для диалогового окна
    	 //theIs${name}DiaryDefectId=aId ;
         if (!theIs${name}DiaryDefectDialogInitialized) {
         	init${name}DiaryDefectDialog() ;
         	the${name}DiaryDefectDialog.show() ;
         	
          } else {
        	    the${name}DiaryDefectDialog.show() ;
          }
         
         

     }
     
     // Отмена 
     function cancel${name}DiaryDefect() {
    	
        the${name}DiaryDefectDialog.hide() ;
     }
     
	function save${name}DiaryDefect() {
		var defID=getCheckedRadio(document.forms['frm${name}Defects'],'${name}rdDefectDiary') ;
		if (+defID==0) {
			alert('Выберите причину для редакции протокола!!!') ;
		} else {
		HospitalMedCaseService.setDiaryDefect(theIs${name}DiaryId,defID
				,$('${name}Comment').value,$('${name}Record').value
				,{
			callback: function(aResult) {
				window.document.location.reload();
			}
		});
		the${name}DiaryDefectDialog.hide() ;
		}
	}
     // инициализация диалогового окна 
     function init${name}DiaryDefectDialog() {
	 
		 HospitalMedCaseService.getDiaryDefects(theIs${name}DiaryId, {
					 callback: function(aDefects) {
						 
						 
						 if (aDefects!=null && aDefects!="") {
							 var defects = aDefects.split("#");
							 var tbody = document.getElementById('table${name}Defects');
							for (var i=0;i<defects.length;i++) {
								var row = document.createElement("TR");
								var param = defects[i].split(":");
								var tID = param[0];
								var tName = param[1];
								var radio = "<td id='td${name}Defects"+i+"'  onclick='this.childNodes[0].checked=\"checked\";'>";
								radio +="<input type='radio' id = '${name}rdDefectDiary' name='${name}rdDefectDiary' value='"+tID+"' onclick=''>"+tName+"</td>";
								tbody.appendChild(row);
								row.innerHTML=radio;
							}
							
						 }
						 //alert(theIs${name}DiaryId) ;
						 HospitalMedCaseService.getDiaryText(theIs${name}DiaryId
									,{
							 
								callback: function(aResult1) {
									$('${name}Record').value=aResult1;
								}
							});
					 }
				 }
				);
			 
		 
		 theIs${name}DiaryDefectDialogInitialized=true;
     }
     function edit${name}TextDiary(aId) {
    	 
     }
</script>