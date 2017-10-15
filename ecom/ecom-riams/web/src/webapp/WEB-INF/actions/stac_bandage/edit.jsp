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
        <msh:form action="/entityParentSaveGoSubclassView-stac_bandage.do" defaultField="" guid="137f576d-2283-4edd-9978-74290e04b873" editRoles="/Policy/Mis/MedCase/Stac/Ssl/Bandage/Edit" createRoles="/Policy/Mis/MedCase/Stac/Ssl/Bandage/Create">
            <msh:panel guid="80209fa0-fbd4-45d0-be90-26ca4219af2e" colsWidth="15px,250px,15px">
                <msh:hidden property="id" />
                <msh:hidden property="patient" />
                <msh:hidden property="saveType" />
                <msh:hidden property="medCase" />
                <msh:hidden property="lpu" />
                <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/ShortEnter">
                    <msh:row guid="132b1-2e6b-425a-a14e-1c330959">
                        <msh:textField property="numberInJournal" label="Номер протокола"  labelColSpan="1" fieldColSpan="3"/>
                    </msh:row>
                    <msh:separator label="Сведения о перевязке" colSpan="5"  />
                </msh:ifNotInRole>
                <msh:row guid="f7540b-4474-46c6-b162-828">
                    <msh:textField property="startDate" label="Начало дата" guid="e8636a99-31e6-4c99-a6f5-825da2a35caf" />
                    <msh:textField property="startTime" label="время" guid="b5bc7756-2fa4-496b-8a35-f54f44be9732" />
                </msh:row>
                <msh:row guid="f7b4a40b-4474-46c6-b162-80be1590e1a8">
                    <msh:textField property="endDate" label="Окончание дата" guid="e8599-31e6-4c99-a6f5-885caf" />
                    <msh:textField property="endTime" label="время" guid="496b-8a35-f89732" />
                </msh:row>
                <msh:row guid="a03a1e02-5a44-4403-bb71-fb8e5afcec43">
                    <msh:autoComplete property="department" label="Отделение" guid="cfc50051-15f6-4b6f-a382-9c5387482c60" fieldColSpan="3" horizontalFill="true" vocName="vocDepartmet" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="serviceStream" label="Поток обслуживания" fieldColSpan="3" horizontalFill="true" vocName="vocServiceStream" />
                </msh:row>

                <msh:row guid="1221-2e6b-425a-a14e-1c02959">
                    <msh:autoComplete property="medService" label="Услуга" size="60" fieldColSpan="3" horizontalFill="true" vocName="medServiceForBandage" />
                </msh:row>


                <msh:hidden property="nurse"/>
                <msh:hidden property="aspect"/>


                <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/ShortEnter">
                    <msh:row guid="ca8a7727-42ac-4c64-8e52-23d4f84dfe43">
                        <msh:textArea rows="6" hideLabel="false" property="text" viewOnlyField="false" guid="e-5833-4bc3-80df-52fdd237fce9" fieldColSpan="3" label="Протокол" />
                    </msh:row>
                    <msh:row>
                        <msh:ifFormTypeIsNotView formName="stac_bandageForm" guid="1c1ec646-5-b9d5-177a7324aa7f">
                            <td colspan="4" align="center">
                                <input type="button" value="Шаблон" onClick="showTheTextTempTemplateProtocol()" />
                                <input type="button" id="changeSizeEpicrisisButton" value="Увеличить" onclick="changeSizeEpicrisis()">
                            </td>
                        </msh:ifFormTypeIsNotView>
                    </msh:row>
                </msh:ifNotInRole>
                <msh:row guid="203625bc-d215-4f48-8ee3-44f48785755d">
                    <msh:autoComplete horizontalFill="true" property="aspect" label="Показания" vocName="vocHospitalAspect" />
                </msh:row>

                <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/ShortEnter">
                    <msh:row>
                        <msh:autoComplete label="Хирург" property="surgeon" horizontalFill="true" fieldColSpan="3" vocName="workFunctionIsSurgical" />
                    </msh:row>
                    <msh:row guid="12721-2e6b-425a-a14e-1c0298959">
                        <msh:autoComplete property="nurse" label="Медсестра" guid="e282-9d6f-4c39-a6a1-30g2f14f" fieldColSpan="3" horizontalFill="true" vocName="workFunctionIsInstrumentNurse" />
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

                <msh:separator label="Общие сведения" colSpan="5" guid="a7a51c30-4065-4ab8-ac94-335b4ade6f66" />
                <msh:row>
                    <msh:label property="createDate" label="Дата создания"/>
                    <msh:label property="createUsername" label="пользователь" guid="2258d5ca-cde5-46e9-a1cc-3ffc278353fe" />
                </msh:row>
                <msh:row>
                    <msh:label property="editDate" label="Дата редак."/>
                    <msh:label property="editUsername" label="пользователь" guid="2258d5ca-cde5-46e9-a1cc-3ffc278353fe" />
                </msh:row>
                <msh:row>
                    <msh:label property="printDate" label="Дата печати"/>
                    <msh:hidden property="printTime"/>
                    <msh:label property="printUsername" label="пользователь"/>
                </msh:row>
                <msh:submitCancelButtonsRow guid="submitCancel" colSpan="3" />
            </msh:panel>
        </msh:form>
        <msh:ifFormTypeIsNotView formName="stac_bandageForm" guid="6ea7dcbb-d32c-4230-b6b0-a662dcc9f568">
            <tags:templateProtocol property="text" name="TheTextTemp"
                                   idSmo="stac_bandageForm.medCase" version="Visit" voc="protocolVisitByPatient"
            />
        </msh:ifFormTypeIsNotView>
        <msh:ifFormTypeIsView formName="stac_bandageForm" guid="e71c21cc-2a77-4d16-9ee0-ba293d19a42b">
            <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/Anesthesia/View" guid="9a06820c-3f3b-4744-880d-06aa1745888d">
                <ecom:parentEntityListAll attribute="anesthesies" formName="stac_anesthesiaForm" guid="af9854a8-f5a7-45b6-859b-ecbaec1ef3b9" />
                <msh:tableNotEmpty name="anesthesies" guid="cc93a3d4-7e22-4b2f-a16c-46b56c963753">
                    <msh:section title="Анестезия" guid="8ac85b8f-45f3-439b-979d-f7ce88a54dbd">
                        <msh:table name="anesthesies" action="entityParentView-stac_anesthesia.do" idField="id" guid="d89b3a7c-2689-48fb-be75-3da899826749">
                            <msh:tableColumn columnName="Анестезиолог" property="anesthesistInfo" guid="8e832f90-6905-44cf-952e-76495689c35b" />
                            <msh:tableColumn columnName="Длительность (мин)" property="duration" guid="8e832f90-6905-44cf-952e-76495689c35b" />
                            <msh:tableColumn columnName="Метод" property="methodInfo" guid="8e832f90-6905-44cf-952e-76495689c35b" />
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
            <msh:ifFormTypeIsView formName="stac_bandageForm" guid="c7cae1b4-31ca-4b76-ab51-7f75b52d11b6">
                <msh:sideMenu title="Перевязка" guid="edd9bfa6-e6e7-4998-b4c2-08754057b0aa">
                    <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-stac_bandage" name="Изменить" roles="/Policy/Mis/MedCase/Stac/Ssl/Bandage/Edit" guid="5a1450f5-7629-4458-b5a5-e5566af6a914" />
                    <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-stac_bandage" name="Удалить" roles="/Policy/Mis/MedCase/Stac/Ssl/Bandage/Delete" guid="7767f5b6-c131-47f4-b8a0-2604050c450f" />
                    <msh:sideLink key="ALT+3" params="id" action="/entityParentList-stac_bandage.do?id=${medcase}" name="⇧Cписок перевязок" roles="/Policy/Mis/MedCase/Stac/Ssl/Bandage/View" guid="a604af1f-cdbe-447b-f6ef8209698f" title="Перейти к списку перевязок" />
                </msh:sideMenu>
            </msh:ifFormTypeIsView>
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