<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
    <tiles:put name='body' type='string'>
        <msh:form action="entityParentSaveGoView-pregProtocol.do" defaultField="dateRegistration"
                  fileTransferSupports="true">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:hidden property="username" />
            <msh:hidden property="date" />
            <msh:hidden property="time" />
            <msh:hidden property="editUsername" />
            <msh:hidden property="editDate" />
            <msh:hidden property="editTime" />
            <msh:hidden property="specialist" />
            <msh:hidden property="templateProtocol" />
            <msh:hidden property="params" />
            <msh:hidden property="medCase" />
            <msh:panel>
                <msh:row>
                    <msh:textField label="Дата" property="dateRegistration" fieldColSpan="1"/>
                    <msh:textField label="Время" property="timeRegistration" fieldColSpan="1"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="type" fieldColSpan="3"
                                      label="Тип протокола" horizontalFill="true"
                                      vocName="vocTypeProtocolMaternity"  />
                </msh:row>
                <msh:row>
                    <msh:ifFormTypeIsNotView formName="pregProtocolForm">
                        <td colspan="3" align="center">
                        <input type="button" name="btnEditProt2" id="btnEditProt2"
                               value="Редактировать параметры" onClick="showTemplateForm($('templateProtocol').value);" />
                        <div name="btnEditProt1" id="btnEditProt1"></div>
                        </td>
                        <td  colspan="3" align="center">
                            <input type="button" value="Шаблон" onClick="showtmpTemplateProtocol()"/>
                        </td>
                    </msh:ifFormTypeIsNotView>
                </msh:row>
                <msh:row>
                    <msh:textArea property="record" label="Текст:" size="100" rows="25"
                                  fieldColSpan="8"/>

                </msh:row>
                <msh:ifFormTypeIsView formName="pregProtocolForm">

                    <msh:row>
                        <msh:textField property="date" label="Дата создания"
                                       viewOnlyField="true" />
                        <msh:textField property="time" label="Время" viewOnlyField="true" />
                        <msh:textField property="username" label="Пользователь"
                                       viewOnlyField="true" />
                    </msh:row>
                    <msh:row>
                        <msh:label property="editDate" label="Дата редак." />
                        <msh:label property="editTime" label="Время редак." />
                        <msh:label property="editUsername" label="Пользователь" />
                    </msh:row>
                    <msh:row>
                        <msh:textField property="printDate" label="Дата печати"
                                       viewOnlyField="true" />
                        <msh:textField property="printTime" label="Время"
                                       viewOnlyField="true" />
                    </msh:row>
                </msh:ifFormTypeIsView>
                <msh:row>
                    <msh:submitCancelButtonsRow colSpan="3" />
                </msh:row>
            </msh:panel>
        </msh:form>
        <tags:templateProtocol  property="record" name="tmp" voc="protocolVisitByPatient"/>
    </tiles:put>
    <tiles:put name='side' type='string'>
        <msh:ifFormTypeIsView formName="pregProtocolForm">
            <tags:stac_selectPrinter name="Select"
                                     roles="/Policy/Config/SelectPrinter" />
        <msh:sideMenu>
                <tags:mis_protocolTemplateDocumentList name="Print" />
                <msh:sideLink roles="/Policy/Mis/MedCase/Protocol/Edit" key="ALT+2"
                              params="id" action="/entityParentEdit-pregProtocol"
                              name="Редактировать" />

                <msh:sideLink roles="/Policy/Mis/MedCase/Protocol/Delete"
                              key='ALT+DEL' params="id"
                              action="/entityParentDeleteGoParentView-pregProtocol"
                              name="Удалить" confirm="Вы действительно хотите удалить?" />
        </msh:sideMenu>
        <msh:sideMenu title="Печать">
            <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/PrintProtocol"
                          name="Печать дневника"
                          action='/javascript:showPrintProtocolTemplate()'
                          title='Печать дневника' />
        </msh:sideMenu>
        </msh:ifFormTypeIsView>
</tiles:put>
    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Patient" beginForm="pregProtocolForm"/>
    </tiles:put>
    <tiles:put name='javascript' type='string'>
        <script type="text/javascript" src="./dwr/interface/PatientService.js">/**/</script>
        <msh:ifFormTypeIsCreate formName="pregProtocolForm">
            <script type="text/javascript">
                typeAutocomplete.addOnChangeCallback(function() {
                    if ($('type').value) {
                        PatientService.getNameTypeProtocol('preg'+$('type').value,{
                            callback: function (res) {
                                if (res != null && res != '[]') {
                                    var Result = JSON.parse(res);
                                    if (typeof(Result.tmpl) !=='undefined') $('templateProtocol').value = Result.tmpl;
                                    if ($('templateProtocol').value!='0')
                                        showTemplateForm($('templateProtocol').value);
                                    else
                                        showToastMessage('Не найден шаблон ' + $('type').value + ' для роддома!',null,true);
                                }
                                else
                                    showToastMessage('Не найден шаблон ' + $('type').value + ' для роддома!',null,true);
                            }
                        });
                    }
                });
            </script>
        </msh:ifFormTypeIsCreate>
        <msh:ifFormTypeAreViewOrEdit formName="pregProtocolForm">
            <msh:ifFormTypeIsNotView formName="pregProtocolForm">
                <script type="text/javascript">
                        showTemplateForm($('templateProtocol').value);
                </script>
            </msh:ifFormTypeIsNotView>
        </msh:ifFormTypeAreViewOrEdit>
        <msh:ifFormTypeIsView formName="pregProtocolForm">
        <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js">/**/</script>
        <script type="text/javascript">
            function printProtocol() {
                HospitalMedCaseService.getPrefixByProtocol(${param.id},
                    {
                        callback: function(prefix) {
                            if (prefix==null) prefix="" ;
                            initSelectPrinter("print-"+'preg'+$('type').value+prefix+".do?m=printPregProtocol&s=HospitalPrintService&id=${param.id}",1);

                        }
                    }
                )
            }
        </script>
        </msh:ifFormTypeIsView>
    </tiles:put>
</tiles:insert>