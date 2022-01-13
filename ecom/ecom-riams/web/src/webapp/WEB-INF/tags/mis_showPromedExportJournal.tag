<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="medcaseId" required="true" description="Поле Id СМО" %>

<style type="text/css">
    #${name}ByPromedHistory {
        visibility: hidden;
        display: none;
        position: absolute;
    }
</style>

<div id='${name}ByPromedHistory' class='dialog'>
    <h2>Журнал выгрузок в промед</h2>
    <div class='rootPane'>
        <h3>Найденные пакеты экспорта</h3>
        <form action="javascript:void(0)" id="frmFondHistory" name="frmFondHistory">
            <table border="1" id='${name}ByPromedHistoryText'></table>

            <msh:row>
                <td colspan="8" align="center">
                    <input type="button" value='Закрыть окно' onclick='javascript:cancel${name}ByPromedHistory()'/>
                </td>
            </msh:row>
        </form>

    </div>
</div>


<script type="text/javascript">
    var theIs${name}ByPromedHistoryInitialized = false;
    var the${name}ByPromedHistory = new msh.widget.Dialog($('${name}ByPromedHistory'));

    // Показать

    function show${name}ByPromedHistory(data) {
        init${name}ByPromedHistory();
        // устанавливается инициализация для диалогового окна
        var rows = "<tr><th>GUID</th><th>Послан</th><th>Выгрузки</th></tr>";
        for (var i =0; i<data.length; i++) {
            var el = data[i]; //PromedRequest
            var row = "<tr><td>"+
                el.guid+"</td><td>"+el.createDate+"</td>";
            for (var j = 0; j<el.journals.length;j++) {
                var jo = el.journals[j];
                row+="<td><p>Отправлен: "+jo.requestDate+"</p><p>Ответ получен: "+jo.responseDate+"</p><p>Статус: "+jo.status
                +"</p>"+(jo.errorText?"<p style='color: red'>Ошибка: "+jo.errorText+"</p>":"")+"<td>"
            }
            
            row+="</tr>";
            rows+=row;
        }
        if (rows != null && rows != '') {
            the${name}ByPromedHistory.hide();

            $('${name}ByPromedHistoryText').innerHTML = rows;
            the${name}ByPromedHistory.show();
        } else {
            $('${name}ByPromedHistoryText').innerHTML = "---";
        }

        the${name}ByPromedHistory.show();

    }


    // Отмена
    function cancel${name}ByPromedHistory() {
        the${name}ByPromedHistory.hide();
        msh.effect.FadeEffect.pushFadeAll();
    }


    // инициализация диалогового окна
    function init${name}ByPromedHistory() {
        theIs${name}ByPromedHistoryInitialized = true;
    }
</script>