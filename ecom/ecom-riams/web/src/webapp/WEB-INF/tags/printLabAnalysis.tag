<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>


<style type="text/css">
    #CloseDisDocument {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>
<script type="text/javascript" src="./dwr/interface/ContractService.js">/**/</script>
<div id='${name}CloseDisDocumentDialog' class='dialog'>
    <h2>Лабораторные анализы</h2>
    <div class='rootPane'>

        <form action="javascript:void(0) ;" id="formId">
            <table width="100%" cellspacing="0" cellpadding="4" id="table1" border="1">
                <tr>
                    <th align="center" width="450">Услуга</th>
                    <th align="center" width="150">Печатать?<input type="checkbox" id="allChb"/></th>
                </tr>
            </table>
            <table width="100%" cellspacing="10" cellpadding="10" id="table2">
            </table>
        </form>
    </div>
</div>

<script type="text/javascript">
    var ID;
    var theIs${name}CloseDisDocumentDialogInitialized = false ;
    var the${name}CloseDisDocumentDialog = new msh.widget.Dialog($('${name}CloseDisDocumentDialog')) ;
    // Показать

    function show${name}CloseDocument(id) {
        ID=id;
        showLabAnalysis();
        theTableArrow = null ;
    }
    function showLabAnalysis() {
        var table2 = document.getElementById('table2');
        table2.innerHTML="<tr><td></td></tr><tr><td align=\"center\"><input type=\"button\" value=\'Печать\' id=\"${name}Print\" onclick=\'javascript:print${name}CloseDocument()\'/></td><td align=\"center\"><input type=\"button\" value='Закрыть' id=\"${name}Cancel\" onclick='javascript:cancel${name}CloseDocument()'/></td></tr><tr><td></td></tr>";
        ContractService.getLabAnalysisExtra(
            ID, {
                callback: function(res) {
                    //alert(res);
                    if (res!="##") {
                        var all = res.split('!') ;
                        var table = document.getElementById('table1');
                        table.innerHTML="<tr><th align=\"center\" width=\"850\">Услуга</th><th align=\"center\" width=\"150\">Печатать?  <input type=\"checkbox\" id=\"allChb${name}\" onclick=\"javascript:checkAllChanged${name}CloseDocument()\"/></th></tr>";
                        for (var i=0; i<all.length-1; i++) {
                            var result=all[i].split('#');
                            var tr = document.createElement('tr');
                            var td1 = document.createElement('td');
                            var td2 = document.createElement('td');
                            td1.innerHTML = result[0];td2.innerHTML = "<input type=\"checkbox\" id="+ result[1]+" name="+result[0]+">";
                            td1.align = "center"; td2.align = "center";
                            tr.appendChild(td1);tr.appendChild(td2);
                            table.appendChild(tr);
                        }
                        the${name}CloseDisDocumentDialog.show() ;
                    }
                    else alert("Дополнительных лабораторных анализов для печати не найдено!");
                }
            }
        );
    }
    // Отмена
    function cancel${name}CloseDocument() {
        the${name}CloseDisDocumentDialog.hide() ;
    }
    // Печать
    function print${name}CloseDocument() {
        var error="";
        var mas = document.getElementsByTagName("input");
        var masCheckBox=[];
        var masFileNames=[];
        for (var i=0; i<mas.length; i++) {
            if (mas[i].getAttribute("type")=="checkbox" && document.getElementsByTagName("input")[i].form.getAttribute("name")==null
                && mas[i].checked && mas[i].getAttribute("id")!="allChb${name}")
                masCheckBox.push(mas[i].getAttribute("id"));
        }
        if (masCheckBox.length==0) alert("Не выбрано ни одной услуги для печати!");
        else {
            ContractService.getAllUserTemplateDocForPrintByService(
                masCheckBox, {
                    callback: function (res) {
                        if (res!="") {
                            var all = res.split('!') ;
                            for (var i=0; i<all.length-1; i++) {
                                var result=all[i].split('#');
                                if (result[1]=="*") error+="Нет шаблона для услуги: " +document.getElementById(result[0]).getAttribute("name") + "!\n";
                                else masFileNames.push(result[1]);
                            }
                            if (error!="") alert(error);
                            for (var j=0; j<masFileNames.length; j++)
                                window.open('print-'+masFileNames[j]+'.do?s=CertificatePersonPrintService&m=printLabAnalysisTemplateExtra&id='+ID);
                        }
                    }
                }
            );
            the${name}CloseDisDocumentDialog.hide() ;
        }
    }
    // Прочекать
    function checkAllChanged${name}CloseDocument() {
        var mas = document.getElementsByTagName("input");
        for (var i=0; i<mas.length; i++) {
            if (mas[i].getAttribute("type")=="checkbox" && document.getElementsByTagName("input")[i].form.getAttribute("name")==null)
                mas[i].checked=document.getElementById("allChb${name}").checked;
        }
    }
</script>