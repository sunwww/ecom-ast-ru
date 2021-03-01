<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%@ attribute name="name" required="true" description="Название" %>
<%@ attribute name="listId" required="true" description="ИД заполнения" %>

<div id='${name}CancerDialog' class='dialog'>
    <h2>Данные для онкослучая</h2>
    <div class='rootPane'>

        <form action="javascript:void(0) ;" id="formId">
            <table width="100%" cellspacing="0" cellpadding="4">
                <msh:row>
                    <msh:autoComplete property="${name}Occasion" size="100" label="Повод" horizontalFill="true"
                                      fieldColSpan="1" vocName="vocOncologyReasonTreatCode"/>
                </msh:row><msh:row>
                <msh:autoComplete property="${name}DirectionType" size="100" label="Вид направления"
                                  horizontalFill="true" fieldColSpan="1" vocName="vocOncologyTypeDirectionCode"/>
            </msh:row>
                <msh:row><msh:autoComplete property="${name}DirectionSurveyMethod" size="100" label="метод направления"
                                           horizontalFill="true" fieldColSpan="1"
                                           vocName="vocOncologyMethodDiagTreatCode"/>
                </msh:row><msh:row>
                <msh:autoComplete property="${name}ConsiliumResult" label="Результат консилиума" size="100"
                                  horizontalFill="true" fieldColSpan="1" vocName="vocOncologyConsiliumCode"/>
            </msh:row>

                <tr>
                    <td><input type="button" value='Присвоить' id="${name}Save"
                               onclick='javascript:save${name}CancerNumber()'/></td>
                    <td><input type="button" value='Закрыть' id="${name}Cancel"
                               onclick='javascript:cancel${name}CancerNumber()'/></td>
                </tr>
            </table>
        </form>

    </div>
</div>

<script type="text/javascript">

    var theIs${name}CancerDialogInitialized = false;
    var the${name}CancerDialog = new msh.widget.Dialog($('${name}CancerDialog'));

    // Показать
    function show${name}CancerDialog() {
        the${name}CancerDialog.show();

    }

    function cancel${name}CancerNumber() {
        the${name}CancerDialog.hide();
    }

    function save${name}CancerNumber() {
        var occasion = jQuery('#${name}Occasion').val();
        if (occasion) {
            var defectCode = prompt("Введите доп. код дефекта", "327");
            var js = {};
            js.occasion = occasion;
            js.directionType = jQuery('#${name}DirectionType').val();
            js.directionSurveyMethod = jQuery('#${name}DirectionSurveyMethod').val();
            js.consiliumResult = jQuery('#${name}ConsiliumResult').val();
            js.consiliumResult = jQuery('#${name}ConsiliumResult').val();

            Expert2Service.makeOncologyCase(
                +${listId}, JSON.stringify(js), defectCode, {
                    callback: function () {
                        alert("Создано!");
                        cancel${name}CancerNumber();
                    }
                }
            )
        }

    }
</script>