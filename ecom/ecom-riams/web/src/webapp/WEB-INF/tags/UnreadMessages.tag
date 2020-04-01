<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>


<style type="text/css">
    #CloseDocument {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>
<script type="text/javascript" src="./dwr/interface/VocService.js">/**/</script>
<div id='${name}CloseDocumentDialog' class='dialog'>
    <h2 id = '${name}Title'></h2>
    <div class='rootPane'>
        <form action="javascript:void(0) ;" id="formId">
            <table width="100%" cellspacing="0" cellpadding="4" id="table${name}" border="1">
            </table>
            <table width="100%" cellspacing="10" cellpadding="10" id="table2${name}">
            </table>
        </form>
    </div>
</div>

<script type="text/javascript">
    var the${name}CloseDocumentDialog = new msh.widget.Dialog($('${name}CloseDocumentDialog')) ;
    var the${name}MaxCnt = 20;
    function show${name}() {
        var table2 = document.getElementById('table2${name}');
        jQuery('#${name}Title').text('Новые сообщения (последние ' + the${name}MaxCnt + ')');
        table2.innerHTML="<tr><td></td></tr><tr><td align=\"center\"><input type=\"button\" value=\'Закрыть\' id=\"${name}Cancel\" onclick=\'javascript:the${name}CloseDocumentDialog.hide() ;\'/></td></tr><tr><td></td></tr>";
        VocService.getUnreadMessages("",the${name}MaxCnt,true,{
                callback: function(aResult) {
                    if (aResult!=null && aResult!='[]' && JSON.parse(aResult).params.length>0) {
                        var res = JSON.parse(aResult).params;
                        var table = document.getElementById('table${name}');
                        table.innerHTML="<tr><th align=\"center\" width=\"150\">Заголовок</th><th align=\"center\" width=\"150\">Сообщение</th><th align=\"center\" width=\"150\">Прочитано?</th></tr>";
                        for (var i=0; i<res.length; i++) {
                            var tr = document.createElement('tr');
                            tr.id="row${name}"+res[i].id;
                            var td1 = document.createElement('td');
                            var td2 = document.createElement('td');
                            var td3 = document.createElement('td');
                            td1.innerHTML = res[i].messageTitle;
                            var textStr = res[i].messageText;
                            td2.id = "row2${name}"+res[i].id;
                            if (textStr.length<500)
                                td2.innerHTML = textStr;
                            else {
                                var htmlTd='<a onclick="jQuery(\'#slow${name}'+res[i].id+
                                    '\').slideToggle(\'slow${name}'+res[i].id+'\');" href="javascript://">'+textStr.substring(0,20)+'...'
                                    +'</a><div id="slow${name}'+
                                    res[i].id+'">'+textStr+'</div>';
                                td2.innerHTML = htmlTd;
                            }
                            td3.innerHTML = "<input type=\"button\" value='Прочитано' id=\"read${name}Cancel\" onclick=\'javascript:read${name}("+res[i].id+")\'/>";
                            td1.setAttribute("align","center");
                            td2.setAttribute("align","center");
                            td2.style = "min-width:700px;max-width:700px ";
                            td3.setAttribute("align","center");
                            tr.appendChild(td1);tr.appendChild(td2);tr.appendChild(td3);
                            table.appendChild(tr);
                            jQuery('#slow${name}'+res[i].id).slideToggle('#slow${name}'+res[i].id);
                        }
                        the${name}CloseDocumentDialog.show() ;
                    }
                    else showToastMessage('Пока нет непрочитанных сообщений!',null,true);
                }
            }
        );
    }

    //отметить сообщение прочтанным
    function read${name}(aMsgId) {
        if (jQuery('#slowUnreadMessages' + aMsgId).is(':visible') || jQuery('#row2${name}'+aMsgId).text().length<500) {
        VocService.checkEmergencyMessages(aMsgId,'',{
                callback: function() {
                    deleteRow("row${name}"+aMsgId);
                    showToastMessage('Отмечено прочитанным',null,true,false,1000);
                    var val=jQuery('unreadMsg').text()-1;
                    jQuery('unreadMsg').text(val);
                    if (+jQuery('unreadMsg').text()==0)
                        the${name}CloseDocumentDialog.hide() ;
                }
            }
        );
        }
        else
            showToastMessage('Разверните и прочитайте сообщение. Только после этого можно отметить его прочитанным.',null,true);
    }

    //удаление строки из таблицы
    function deleteRow(rowid) {
        var row = document.getElementById(rowid);
        var table = row.parentNode;
        while (table && table.tagName != 'TABLE')
            table = table.parentNode;
        if (!table)
            return;
        table.deleteRow(row.rowIndex);
        if (table.rows.length==1) {  //если, помимо заголовка, нет сообщений
            the${name}CloseDocumentDialog.hide() ;
            show${name}();
        }
    }
</script>