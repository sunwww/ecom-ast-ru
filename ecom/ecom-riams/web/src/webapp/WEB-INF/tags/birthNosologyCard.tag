<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>


<style type="text/css">
    #NosCard {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>
<script type="text/javascript" src="./dwr/interface/PregnancyService.js">/**/</script>
<div id='${name}NosCardDialog' class='dialog'>
<h2>Нозологии</h2>
<div class='rootPane'>
    <form action="javascript:void(0) ;" id="formId">
        <table width="100%" cellspacing="0" cellpadding="4" id="table${name}" border="1">
        </table>
    </form>
</div>
</div>

<script type="text/javascript">
    var aSlsID${name};
    var theIs${name}NosCardDialogInitialized = false ;
    var the${name}NosCardDialog = new msh.widget.Dialog($('${name}NosCardDialog')) ;
    var goIfNOtSave${name};
    var disable${name};
    var aPrefix${name};
    var Form${name};
    var codeNum${name};  //codeNum - номер страницы вывода чекбоксов = code в voc
    var saveMasChb${name};  //saveMasChb - массив с сохранённмыи выбранными чекбоксами
    var saveDs${name};  //saveDs - сохранять диагнозы
    // Показать
    function show${name}(id,goIfNOtSave,disable,form,aPrefix,codeNum, saveMasChb, saveDs) {
        aSlsID${name}=id;
        goIfNOtSave${name}=goIfNOtSave;
        disable${name}=disable;
        Form${name}=form;
        aPrefix${name}=aPrefix;
        if (codeNum) codeNum${name}=codeNum;
        else codeNum${name}=1;
        saveMasChb${name} = saveMasChb;
        saveDs${name}=saveDs;
        PregnancyService.getBirthNosologyCard(
            aSlsID${name}, codeNum${name}, {
                callback: function(aResult) {
                    if (aResult!=null && aResult!='[]') {
                        var dsbl=false;
                        var result = JSON.parse(aResult);
                        var table = document.getElementById('table${name}');
                        table.innerHTML="";
                        for (var i=0; i<result.length; i++) {
                            var res=result[i];
                            var tr = document.createElement('tr');
                            var td1 = document.createElement('td');
                            td1.setAttribute("colspan",2);
                            var html = '<label height="100%" width="100%" for="${name}'+res.vocID+'"><input type="checkbox" id="${name}'+res.vocID+'" name="${name}Chb"';
                            if ( disable${name}!=null && (res.disabled || disable${name})) {
                                html+=' disabled';
                                dsbl=true;
                            }
                            if (res.checked) html+=' checked';
                            html+= '>'+res.vocName+'</label>';
                            td1.innerHTML =  html;
                            td1.setAttribute("align","left");
                            tr.id='${name}row'+i;
                            tr.appendChild(td1);
                            table.appendChild(tr);
                        }
                        var tHtml=""
                        tHtml+=" <table width=\"100%\" cellspacing=\"0\" cellpadding=\"4\" id=\"table${name}\" border=\"1\">" +
                            "        <tbody><tr>";
                            if (codeNum${name}==2)
                                tHtml+=    "            <td align=\"center\" ><input type=\"button\" value='Сохранить' id=\"${name}Save\" onclick='save${name}();'/></td>";
                        tHtml+="            <td align=\"center\"><input type=\"button\" value='Закрыть' id=\"${name}Cancel\" onclick='javascript:cancel${name}NosCardDialog()'/></td>";
                            if (codeNum${name}==1)
                                tHtml+="            <td align=\"center\" ><input type=\"button\" value='Далее' id=\"${name}Next\" onclick='next${name}();'/></td>";
                        tHtml+="        </tr></tbody>" +
                            "        </table>";
                        if (dsbl) {
                            jQuery("#${name}Save").prop("disabled", true);
                            if (!disable${name})
                                showToastMessage("Пациент выписан, либо уже есть СЛО в обсервационном отделении (при выписке можно будет уточнить нозологии)! Сейчас доступно только для просмотра.",null,true,false);
                        }
                        /*jQuery('#table${name} > tbody  > tr').each(function(index, tr) {
                            tr.onclick = function() {
                                if (tr.childNodes.length>0 && tr.childNodes[0].childNodes.length>0)
                                    tr.childNodes[0].childNodes[0].click();
                            }
                        });*/
                        table.innerHTML+=tHtml;
                        the${name}NosCardDialog.show() ;
                    }
                    else showToastMessage("Нозологии не найдены!",null,true,true);
                }
            }
        );
    }

    //Сохранить
    function save${name}() {
        var mas = getAllCheckBoxesFromPages${name}();
        if (mas.length>0) {
            PregnancyService.saveBirthNosologyCard(aSlsID${name},mas+'', {
                callback: function (aResult) {
                    showToastMessage(aResult,null,true,false,2000);
                    the${name}NosCardDialog.hide() ;
                    if (saveDs${name})  //сохранить
                        fillСoncomitantDiagnosis(aSlsID${name});
                    if (Form${name}) {
                        //Form${name}.submit();
                        showCreateDiagnoseCriteriaCloseDocument($('clinicalMkb').value,null,null, document.forms[0],aSlsID${name},true);
                    }
                    if (aPrefix${name} || aPrefix${name}=='')
                        saveNext(aPrefix${name});
                }
                }
            );
        }
        else
            showToastMessage("Обязательно должна быть отмечена хотя бы одна нозология!",null,true,false);
    }

    //Получить все чекбоксы + сохранённые с предыдущих страниц
    function getAllCheckBoxesFromPages${name}() {
        var mas = getCheckBoxes${name}();
        for (var i=0; i<saveMasChb${name}.length; i++)
            mas.push(saveMasChb${name}[i]);
        return mas;
    }

    //Получить все отмеченные чекбоксы
    function getCheckBoxes${name}(){
        var mas=[];
        var inputs = document.getElementsByTagName('input');
        for (var i = 0; i < inputs.length; i++) {
            if (inputs[i].type == 'checkbox' && inputs[i].checked==true && inputs[i].name.indexOf('${name}')!=-1) {
                mas.push(inputs[i].id.replace('${name}',''));
            }
        }
        return mas;
    }

    //Закрыть
    function cancel${name}NosCardDialog() {
        the${name}NosCardDialog.hide() ;
        if (goIfNOtSave${name})
            window.location = goIfNOtSave${name};
        if (aPrefix${name} || aPrefix${name}=='')
            $('submitButton').disabled=false ;
    }

    //Далее (на след. страницу)
    function next${name}() {
        the${name}NosCardDialog.hide() ;
        show${name}(aSlsID${name},goIfNOtSave${name},disable${name},Form${name},aPrefix${name},++codeNum${name}, getCheckBoxes${name}(),saveDs${name});
    }

    function showOnlyChecked${name}(id) {
        aSlsID${name}=id;
        PregnancyService.getBirthNosologyCardOnlyChecked(
            aSlsID${name}, {
                callback: function(aResult) {
                    if (aResult!=null && aResult!='[]' && aResult!="0") {
                        var dsbl=false;
                        var result = JSON.parse(aResult);
                        var table = document.getElementById('table${name}');
                        table.innerHTML="";
                        for (var i=0; i<result.length; i++) {
                            var res=result[i];
                            var tr = document.createElement('tr');
                            var td1 = document.createElement('td');
                            td1.setAttribute("colspan",2);
                            var html = '<label height="100%" width="100%" for="${name}'+res.vocID+'"><input type="checkbox" id="${name}'+res.vocID+'" name="${name}Chb"';
                            html+=' disabled';
                            dsbl=true;
                            if (res.checked) html+=' checked';
                            html+= '>'+res.vocName+'</label>';
                            td1.innerHTML =  html;
                            td1.setAttribute("align","left");
                            tr.id='${name}row'+i;
                            tr.appendChild(td1);
                            table.appendChild(tr);
                        }
                        var tHtml=" <table width=\"100%\" cellspacing=\"0\" cellpadding=\"4\" id=\"table${name}\" border=\"1\">" +
                            "        <tbody><tr>" +
                            "            <td align=\"center\"><input type=\"button\" value='Закрыть' id=\"${name}Cancel\" onclick='javascript:cancel${name}NosCardDialog()'/></td>" +
                            "        </tr></tbody>" +
                            "        </table>";
                        table.innerHTML+=tHtml;
                        the${name}NosCardDialog.show() ;
                    }
                    else showToastMessage("Заполненные нозологии не найдены!",null,true,true);
                }
            }
        );
    }
</script>