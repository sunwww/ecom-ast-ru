<%@ tag import="ru.ecom.web.util.ActionUtil" %>
<%@ tag import="ru.ecom.web.login.LoginInfo" %>
<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>


<style type="text/css">
    #ReplaceSymbs {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>
<div id='${name}ReplaceSymbsDialog' class='dialog'>
    <h2>Замените непечатные символы</h2>
    <div class='rootPane'>

        <form action="javascript:void(0) ;" id="formId">
            <div>
                <table width="100%" cellspacing="10" cellpadding="10" id="table${name}" border="0">
                </table>
            </div>
            <table width="100%" cellspacing="10" cellpadding="10">
                <tr><td></td></tr>
                <tr>
                    <td align="center"><input  type="button" value='Ок' style="font-weight:bold" onclick='javascript:${name}Save()'/></td>
                    <td align="center"><input type="button" value='Отмена' onclick='javascript:the${name}ReplaceSymbsDialog.hide();'/></td>
                </tr>
                <tr><td></td></tr>
            </table>
        </form>
    </div>
</div>

<script type="text/javascript">

    var theIs${name}ReplaceSymbsDialogInitialized = false ;
    var the${name}ReplaceSymbsDialog = new msh.widget.Dialog($('${name}ReplaceSymbsDialog')) ;
    var record${name}=$('record').value;
    var table${name} = document.getElementById('table${name}');
    var num${name}=20;  //кол-во символов вокруг позиции
    var masAll${name}=[];
    // Показать
    function show${name}() {
        <%
    String msgError="";
    Object id = request.getParameter("id");
    if (id!=null && !id.equals("0")) {
        String login = LoginInfo.find(request.getSession(true)).getUsername();
        ActionUtil.getValueBySql("select d.id, case when d.username='" + login + "' then '1' else '0' end from diary d where d.id=" + id
                , "d_id", "res", request);
        Object res = request.getAttribute("res");
        if (!res.equals("1"))
            msgError="Только пользователь, создавший этот дневник, может редактировать его перед печатью!";
    }
    else
        msgError="Некорректный id дневника!";
    %>
        if ('<%= msgError %>'!='')
            showToastMessage('<%= msgError %>',null,true,true,3000);
        else {
            masAll${name}=[];
            table${name}.innerHTML="";
            var numOfRow=0;
            for (var i=0; i<record${name}.length; i++) {
                var code=record${name}.charCodeAt(i);
                if (!checkCode(code)) {
                    if (masAll${name}.indexOf(record${name}[i])==-1) { //символ для замены выводится один раз и заменяется везде
                        masAll${name}.push(record${name}[i]);

                        var tr = document.createElement('tr');
                        var tdLeft = document.createElement('td');
                        var tdSymbol = document.createElement('td');
                        var tdRepl = document.createElement('td');
                        var tdRight = document.createElement('td');

                        var pp = getTextAround(i);
                        tdLeft.innerHTML = '...';
                        tdLeft.innerHTML += i>0? record${name}.substring(pp[0],i) : record${name}.substring(pp[0],i);
                        tdRight.innerHTML = (i+1)<record${name}.length? record${name}.substring(i+1,pp[1]) : record${name}.substring(i,pp[1]);
                        tdRight.innerHTML += '...';
                        tdSymbol.innerHTML = '<b>'+record${name}[i]+'</b>';
                        tdRepl.innerHTML = '<input type="text" name="repl${name}" id="repl'+numOfRow+'" size=10px ></input>';
                        tdLeft.align="center";tdRight.align="center";
                        tdSymbol.align="center";tdRepl.align="center";
                        tr.appendChild(tdLeft); tr.appendChild(tdSymbol);
                        tr.appendChild(tdRight); tr.appendChild(tdRepl);
                        table${name}.appendChild(tr);
                        numOfRow++;
                    }
                }
            }
            if (table${name}.rows.length>0)
                the${name}ReplaceSymbsDialog.show() ;
        }
    }

    //Возврат текста вокруг символа
    function getTextAround(pos) {
        var pp=[];
        if (pos==0) {
            pp[0] = 0;
            pp[1] = num${name};
        }
        else if (pos==record${name}.length-1) {
            pp[0] = pos - num${name};
            pp[1] = pos;
        }
        else if (pos >= num${name}) {
            pp[0] = pos - num${name};
            pp[1] = pos + 1 + num${name};
        }
        else if (pos < num${name}) {
            pp[0] = 0;
            pp[1] = pos + 1 + num${name};
        }
        return pp;
    }

    // Замена символов
    function ${name}Save() {
        var rows=table${name}.rows;
        for (var i=0; i<rows.length; i++) {
            var symb = jQuery(rows[i].cells[1]).text();
            var replSymb = jQuery('#repl'+i).val();
            if (replSymb!='') {
                if (checkInput(replSymb))
                    replace${name}(symb, replSymb);
                else {
                    if (replSymb.length==1)
                        showToastMessage('Символ ' + replSymb + ' непечатный. Нужно заменить печатным символом!',null,true,true);
                    else
                        showToastMessage(replSymb + ' содержит непечатные символы. Нужно заменить печатным символом!',null,true,true);
                    return;
                }
            }
            else if (confirm('Вы не заполнили поле. Вы уверены, что хотите просто удалить символ ' + symb + '?'))
                replace${name}(symb, replSymb);
            else return;

        }
        $('record').value=record${name};
        jQuery('.record').html('<b>'+record${name}.replace(new RegExp('\n','g'),'<br>'));  //new lines
        the${name}ReplaceSymbsDialog.hide() ;
        saveRecord${name}();
        goPrint${name}();
    }

    //Проверка кода символа
    /*0-255 ascii цифры, знаки, англ алфавит
    1040-1103 кириллица
    1025,1105 ёЁ
    8470 №*/
    function checkCode(code) {
        return ((code>=0 && code<=255) ||
            (code>=1040 && code<=1103) ||
            code==1025 || code==1105 || code==8470);
    }

    //Замена символов
    function replace${name}(symb, replSymb) {
        record${name}=record${name}.replace(new RegExp(symb,'g'),replSymb);
    }

    //Проверка введённого (мб несколько символов)
    function checkInput(input) {
        for (var i=0; i<input.length; i++) {
            if (!checkCode(input.charCodeAt(i)))
                return false;
        }
        return true;
    }

    //Сохранение в бд
    function saveRecord${name}() {
        if (confirm('Сохранить изменения?')) {
            TemplateProtocolService.saveRecordUnprint($('id').value,record${name},{
                    callback: function() {
                        showToastMessage('Сохранено.',null,true,false,2000);
                    }
                }
            ) ;
        }
    }

    //Печать
    function goPrint${name}() {
        if (confirm("Распечатать дневник?"))
            showPrintProtocolTemplate();
    }
</script>