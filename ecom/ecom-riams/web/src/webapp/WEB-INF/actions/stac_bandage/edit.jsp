<%@ page import="ru.ecom.mis.ejb.form.medcase.hospital.BandageForm" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
    <tiles:put name="style" type="string">
        <style type="text/css">
            .protocols {
                left:0px;  width:60em;
                top:0px;  height:55em;
                overflow: auto;
            }
            .text {
                width:100%;
            }
        </style>

    </tiles:put>
    <tiles:put name="body" type="string">
        <!--
        -Перевязка
        -->
        <%
            BandageForm frm = (BandageForm)request.getAttribute("stac_bandageForm") ;
            request.setAttribute("medcase", frm.getMedCase()) ;

        %>
        <msh:form action="/entityParentSaveGoSubclassView-stac_bandage.do" defaultField="" editRoles="/Policy/Mis/MedCase/Stac/Ssl/Bandage/Edit" createRoles="/Policy/Mis/MedCase/Stac/Ssl/Bandage/Create">
            <msh:panel colsWidth="15px,250px,15px">
                <msh:hidden property="id" />
                <msh:hidden property="patient" />
                <msh:hidden property="saveType" />
                <msh:hidden property="medCase" />
                <msh:hidden property="lpu" />
                <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/ShortEnter">
                    <msh:row>
                        <msh:textField property="numberInJournal" label="Номер протокола"  labelColSpan="1" fieldColSpan="3"/>
                    </msh:row>
                    <msh:separator label="Сведения о перевязке" colSpan="5"  />
                </msh:ifNotInRole>
                <msh:row>
                    <msh:textField property="startDate" label="Начало дата" />
                    <msh:textField property="startTime" label="время" />
                </msh:row>
                <msh:row>
                    <msh:textField property="endDate" label="Окончание дата" />
                    <msh:textField property="endTime" label="время" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="department" label="Отделение" fieldColSpan="3" horizontalFill="true" vocName="vocDepartmet" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="serviceStream" label="Поток обслуживания" fieldColSpan="3" horizontalFill="true" vocName="vocServiceStream" />
                </msh:row>

                <msh:row>
                    <msh:autoComplete property="medService" label="Услуга" size="60" fieldColSpan="3" horizontalFill="true" vocName="medServiceForBandage" />
                </msh:row>


                <msh:hidden property="nurse"/>
                <msh:hidden property="aspect"/>


                <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/ShortEnter">
                    <msh:row>
                        <msh:textArea rows="6" hideLabel="false" property="text" viewOnlyField="false" fieldColSpan="3" label="Протокол" />
                    </msh:row>
                    <msh:row>
                        <msh:ifFormTypeIsNotView formName="stac_bandageForm">
                            <td colspan="4" align="center">
                                <input type="button" value="Шаблон" onClick="showTheTextTempTemplateProtocol()" />
                                <input type="button" id="changeSizeEpicrisisButton" value="Увеличить" onclick="changeSizeEpicrisis()">
                            </td>
                        </msh:ifFormTypeIsNotView>
                    </msh:row>
                </msh:ifNotInRole>
                <msh:row>
                    <msh:autoComplete horizontalFill="true" property="aspect" label="Показания" vocName="vocHospitalAspect" />
                </msh:row>

                <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/ShortEnter">
                    <msh:row>
                        <msh:autoComplete label="Хирург" property="surgeon" horizontalFill="true" fieldColSpan="3" vocName="workFunctionIsSurgical" />
                    </msh:row>
                    <msh:row>
                        <msh:autoComplete property="nurse" label="Медсестра" fieldColSpan="3" horizontalFill="true" vocName="workFunctionIsInstrumentNurse" />
                    </msh:row>
                </msh:ifNotInRole>

                <msh:ifFormTypeIsCreate formName="stac_bandageForm">
                    <msh:separator label="Анестезия" colSpan="5"/>
                    <msh:row>
                        <msh:autoComplete property="isAnesthesia" label="Анестезия проводилась?" horizontalFill="true" vocName="vocYesNo" fieldColSpan="3" />
                    </msh:row>
                    <msh:row>
                        <msh:autoComplete property="anesthesia" label="Метод" horizontalFill="true" vocName="vocAnesthesiaMethod" fieldColSpan="3" />
                    </msh:row>
                    <msh:row>
                        <msh:autoComplete property="anesthesiaService" label="Услуга" horizontalFill="true" vocName="medServiceAnesthesia" fieldColSpan="3" />
                    </msh:row>
                    <msh:row>
                        <msh:autoComplete property="anesthesiaType" label="Вид" horizontalFill="true" vocName="vocAnesthesia" fieldColSpan="3" />
                    </msh:row>
                    <msh:row>
                        <msh:textField property="anesthesiaDuration" label="Длительность (мин)"  fieldColSpan="3" />
                    </msh:row>
                    <msh:row >
                        <msh:autoComplete property="anaesthetist" label="Анестезиолог" vocName="workFunction" fieldColSpan="3" horizontalFill="true" />
                    </msh:row>
                </msh:ifFormTypeIsCreate>

                <msh:separator label="Общие сведения" colSpan="5" />
                <msh:row>
                    <msh:label property="createDate" label="Дата создания"/>
                    <msh:label property="createUsername" label="пользователь" />
                </msh:row>
                <msh:row>
                    <msh:label property="editDate" label="Дата редак."/>
                    <msh:label property="editUsername" label="пользователь" />
                </msh:row>
                <msh:row>
                    <msh:label property="printDate" label="Дата печати"/>
                    <msh:hidden property="printTime"/>
                    <msh:label property="printUsername" label="пользователь"/>
                </msh:row>
                <msh:submitCancelButtonsRow colSpan="3" />
            </msh:panel>
        </msh:form>
        <msh:ifFormTypeIsNotView formName="stac_bandageForm">
            <tags:templateProtocol property="text" name="TheTextTemp"
                                   idSmo="stac_bandageForm.medCase" version="Visit" voc="protocolVisitByPatient"
            />
        </msh:ifFormTypeIsNotView>
        <msh:ifFormTypeIsView formName="stac_bandageForm">
            <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/Anesthesia/View">
                <ecom:parentEntityListAll attribute="anesthesies" formName="stac_anesthesiaForm" />
                <msh:tableNotEmpty name="anesthesies">
                    <msh:section title="Анестезия">
                        <msh:table name="anesthesies" action="entityParentView-stac_anesthesia.do" idField="id">
                            <msh:tableColumn columnName="Анестезиолог" property="anesthesistInfo" />
                            <msh:tableColumn columnName="Длительность (мин)" property="duration" />
                            <msh:tableColumn columnName="Метод" property="methodInfo" />
                        </msh:table>
                    </msh:section>
                </msh:tableNotEmpty>
            </msh:ifInRole>
        </msh:ifFormTypeIsView>
        <msh:ifFormTypeIsNotView formName="stac_bandageForm">
            <tags:mis_double name='MedService' title='Данная перевязка оказана:' cmdAdd="document.forms[0].submitButton.disabled = false "/>
            <tags:service_change name="ServiceChange" autoCompliteServiceFind="medService"></tags:service_change>
        </msh:ifFormTypeIsNotView>
        <tiles:put name="side" type="string">
            <msh:ifFormTypeIsView formName="stac_bandageForm">
                <msh:sideMenu title="Перевязка">
                    <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-stac_bandage" name="Изменить" roles="/Policy/Mis/MedCase/Stac/Ssl/Bandage/Edit" />
                    <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-stac_bandage" name="Удалить" roles="/Policy/Mis/MedCase/Stac/Ssl/Bandage/Delete" />
                    <msh:sideLink key="ALT+3" params="id" action="/entityParentList-stac_bandage.do?id=${medcase}" name="⇧Cписок перевязок" roles="/Policy/Mis/MedCase/Stac/Ssl/Bandage/View" title="Перейти к списку перевязок" />
                </msh:sideMenu>
            </msh:ifFormTypeIsView>
        </tiles:put>
        <tiles:put name="title" type="string">
            <ecom:titleTrail mainMenu="StacJournal" beginForm="stac_bandageForm" />
        </tiles:put>
        <script type="text/javascript">
            var isChangeSizeEpicrisis=1 ;
            function changeSizeEpicrisis() {
                if (isChangeSizeEpicrisis==1) {
                    Element.addClassName($('text'), "protocols") ;
                    if ($('changeSizeEpicrisisButton')) $('changeSizeEpicrisisButton').value='Уменьшить' ;
                    isChangeSizeEpicrisis=0 ;
                } else {
                    Element.removeClassName($('text'), "protocols") ;
                    if ($('changeSizeEpicrisisButton')) $('changeSizeEpicrisisButton').value='Увеличить' ;
                    isChangeSizeEpicrisis=1;
                }
            }
            eventutil.addEventListener($('text'), "dblclick",
                function() {
                    changeSizeEpicrisis() ;
                }) ;
        </script>
    </tiles:put>
</tiles:insert>