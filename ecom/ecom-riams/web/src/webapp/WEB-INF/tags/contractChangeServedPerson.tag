<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="contract" required="true" description="ИД договора" %>

<style type="text/css">
    #${name}ContractPersonDialog {
        visibility: hidden;
        display: none;
        position: absolute;
    }

</style>

<div id='${name}ContractPersonDialog' class='dialog'>
    <h2>Изменение обслуживаемой персоны по договору</h2>
    <div class='rootPane'>

        <form action="javascript:save${name}ContractPerson()">
            <msh:panel styleId="panel${name}">
                <msh:row>
                    <msh:autoComplete label="Обслуживаемая персона" property="new${name}ServedPerson"
                                      vocName="contractPerson" size="100"/>
                </msh:row>

            </msh:panel>

            <msh:row>
                <td colspan="6">
                    <input type="button" value='Изменить обслуживаему персону' id="${name}Ok"
                           onclick="javascript:save${name}ContractPerson()"/>
                    <input type="button" value='Отменить' id="${name}Cancel"
                           onclick='javascript:cancel${name}ContractPerson()'/>
                </td>
            </msh:row>
        </form>

    </div>
</div>

<script type='text/javascript' src='./dwr/interface/ContractService.js'></script>
<script type="text/javascript">
    var the${name}ContractPersonDialog = new msh.widget.Dialog($('${name}ContractPersonDialog'));

    // Показать
    function show${name}ContractPerson() {
        // устанавливается инициализация для диалогового окна
        the${name}ContractPersonDialog.show();

    }

    // Отмена
    function cancel${name}ContractPerson() {
        the${name}ContractPersonDialog.hide();
    }

    // Сохранение данных
    function save${name}ContractPerson() {
        if (!confirm("Вы уверены что хотите изменить обслуживаемую персону?")) {
            return;
        }
        var personId = +$('new${name}ServedPerson').value;
        if (personId > 0) {
            ContractService.changeServedPerson(${contract}, personId, {
                callback: function () {
                    alert('Обслуживаемая персона успешно изменена!');
                    window.location.reload();
                }
            });
        } else {
            alert('Выберите персону');
        }
    }

</script>