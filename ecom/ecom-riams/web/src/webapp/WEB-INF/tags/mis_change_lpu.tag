<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="roles" required="true" description="Роли" %>
<%@ attribute name="title" required="true" description="Заголовок" %>
<%@ attribute name="service" required="true" description="Сервис" %>

<msh:ifInRole roles="${roles}">
    <msh:sideLink name="${title}" action=" javascript:show${name}ChangeLpu('.do') "
    />

    <style type="text/css">
        #${name}ChangeLpuDialog {
            visibility: hidden;
            display: none;
            position: absolute;
        }
    </style>

    <div id='${name}ChangeLpuDialog' class='dialog'>
        <h2>Изменить ЛПУ направителя у СМО</h2>
        <div class='rootPane'>

            <form action="javascript:">
                <msh:panel>
                    <msh:row>
                        <msh:autoComplete property="${name}ChangeLpu" label="ЛПУ" size="100" vocName="mainLpu"/>
                    </msh:row>
                </msh:panel>
                <msh:row>
                    <td colspan="6">
                        <input type="button" value='OK' onclick="javascript:save${name}ChangeLpu()"/>
                        <input type="button" value='Отменить' onclick='javascript:cancel${name}ChangeLpu()'/>
                    </td>
                </msh:row>
            </form>

        </div>
    </div>

    <script type="text/javascript">
        var theIs${name}ChangeLpuDialogInitialized = false;
        var the${name}ChangeLpuDialog = new msh.widget.Dialog($('${name}ChangeLpuDialog'));

        // Показать
        function show${name}ChangeLpu() {
            // устанавливается инициализация для диалогового окна
            if (!theIs${name}ChangeLpuDialogInitialized) {
                init${name}ChangeLpuDialog();
            }
            the${name}ChangeLpuDialog.show();
            $("${name}ChangeLpuName").focus();

        }

        // Отмена
        function cancel${name}ChangeLpu() {
            the${name}ChangeLpuDialog.hide();
            msh.effect.FadeEffect.pushFadeAll();
        }

        // Сохранение данных
        function save${name}ChangeLpu() {
            if (+$('${name}ChangeLpu').value == 0) {
                alert("Поле ЛПУ является обязательным");
                $("${name}ChangeLpuName").focus();
            } else {
                ${service}.
                changeLpuBySmo(
                    '${param.id}', $('${name}ChangeLpu').value, {
                        callback: function (aString) {
                            alert(aString);
                            window.location.reload();
                        }
                    }
                );
            }
        }

        // инициализация диалогового окна
        function init${name}ChangeLpuDialog() {
            theIs${name}ChangeLpuDialogInitialized = true;
        }
    </script>
</msh:ifInRole>