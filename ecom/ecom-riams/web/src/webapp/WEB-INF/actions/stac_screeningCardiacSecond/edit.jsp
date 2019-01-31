<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
    <tiles:put name="side" type="string">
        <msh:ifFormTypeIsView formName="stac_screeningCardiacSecondForm" guid="e20545-4285-a21c-3bb9b4569efc">
            <msh:sideMenu guid="9ec15353-1f35-4c18-b99d-e2b63ecc60c9" title="Скрининг новорождённых">
                <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-stac_screeningCardiacSecond" name="Изменить" roles="/Policy/Mis/Pregnancy/CardiacScreening/Edit" guid="5a1450f5-7629-4458-b5a5-e5566af6a914" />
                <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-stac_screeningCardiacSecond" name="Удалить" roles="/Policy/Mis/Pregnancy/CardiacScreening/Delete" guid="7767f5b6-c131-47f4-b8a0-2604050c450f" />
            </msh:sideMenu>
        </msh:ifFormTypeIsView>
    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:form action="/entityParentSaveGoView-stac_screeningCardiacSecond.do" defaultField="skin">
            <msh:hidden property="id" />
            <msh:hidden property="medCase" />
            <msh:hidden property="saveType" />
                <msh:row>
                    <msh:autoComplete property="skin" label="Кожные покровы" vocName="vocScreeningSkin"  size="30"/>
                </msh:row>
                <msh:separator label="Пульсация артерий конечностей:" colSpan="2"/>
                <msh:row>
                    <msh:autoComplete property="rightHandAP" label="Правая рука (лучевая/локтевая артерии)" vocName="vocScreeningArterialPulsation" size="30"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="legAP" label="Нога (дорзальная артерия стопы/бедренная артерия)" vocName="vocScreeningArterialPulsation"  size="30"/>
                </msh:row>
                <msh:separator label="Пульсоксиметрия на конечностях одновременная:" colSpan="2"/>
                <msh:row>
                    <msh:textField property="rightHandPulse" label="правая рука"/>
                    <msh:textField property="legPulse" label="нога"/>
                </msh:row>
                <msh:separator label="Регистрация артериального давления одновременная (мм. рт. ст.):" colSpan="2"/>
                <msh:row>
                    <msh:textField property="rightHandAD" label="правая рука"/>
                    <msh:textField property="legAD" label="нога"/>
                </msh:row>
                <msh:separator label="Характеристика дыхания:" colSpan="2"/>
                <msh:row>
                    <msh:textField property="breathingRate" label="частота в мин."/>
                </msh:row>
                <msh:row>
                    <br>
                    <msh:checkBox property="retractionIntercostalGaps" label="втяжение межрёберных промежутков"/>
                    <msh:checkBox property="noseWingsMovement" label="движение крыльев носа"/>
                    <msh:checkBox property="noisyBreathing" label="шумное дыхание"/>
                    <msh:checkBox property="wheezing" label="хрипы"/>
                </msh:row>
                <msh:row>
                    <br>
                    <msh:autoComplete property="CNS" label="Характеристика ЦНС" vocName="vocScreeningCNS"  size="30"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="apicalImpulseLocal" label="Локализация верхушечного толчка" vocName="vocScreeningApicalImpulseLocalization"  size="30"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="liverEdgeLocal" label="Локализация края печени" vocName="vocScreeningLiverEdgeLocalLocalization"  size="30"/>
                </msh:row>
                <msh:separator label="Характеристика сердечной деятельности:" colSpan="2"/>
                <msh:row>
                    <msh:textField property="heartRate" label="частота в мин."/>
                </msh:row>
                <msh:row>
                    <br>
                    <msh:autoComplete property="cardiacActivity" label="Пульс" vocName="vocScreeningCardiacActivity"  size="30"/>
                </msh:row>
                <msh:row>
                    <msh:checkBox property="noisePresence" label="наличие шума"/>
                </msh:row>
                <msh:row>
                    <br>
                    <msh:autoComplete property="diuresis" label="Характеристика диуреза" vocName="vocScreeningDiuresis"  size="30"/>
                </msh:row>
                <msh:row>
                    <br><label>Электрокардиография (по показаниям)</label><br>
                    <msh:textArea property="ECG" label=""/>
                </msh:row>
                <msh:row>
                    <br><label>Дополнительные сведения: подозрения на синдромальную патологию, множественные пороки развития</label><br>
                    <msh:textArea property="extraInfo" label=""
                                  horizontalFill="true"/>
                </msh:row>
                <msh:ifFormTypeAreViewOrEdit formName="stac_screeningCardiacSecondForm">
                    <msh:row>
                        <msh:separator label="Дополнительная информация" colSpan="4"/>
                    </msh:row>
                    <msh:row>
                        <msh:label property="createDate" label="Дата создания"/>
                        <msh:label property="createTime" label="время"/>
                    </msh:row>
                    <msh:row>
                        <msh:label property="createUsername" label="пользователь"/>
                    </msh:row>
                    <msh:row>
                        <msh:label property="editDate" label="Дата редактирования"/>
                        <msh:label property="editTime" label="время"/>
                    </msh:row>
                    <msh:row>
                        <msh:label property="editUsername" label="пользователь"/>
                    </msh:row>
                </msh:ifFormTypeAreViewOrEdit>
                <msh:submitCancelButtonsRow colSpan="2" />
        </msh:form>
    </tiles:put>
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Patient" beginForm="stac_screeningCardiacSecondForm" guid="fb43e71c-1ba9-4e61-8632-a6f4a72b461c" />
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <script>
            eventutil.addEventListener($('rightHandPulse'), "change",function(){
                $('rightHandPulse').value=parseInt($('rightHandPulse').value);
                if ($('rightHandPulse').value=="NaN") $('rightHandPulse').value="";
            }) ;
            eventutil.addEventListener($('legPulse'), "change",function(){
                $('legPulse').value=parseInt($('legPulse').value);
                if ($('legPulse').value=="NaN") $('legPulse').value="";
            }) ;
            eventutil.addEventListener($('breathingRate'), "change",function(){
                $('breathingRate').value=parseInt($('breathingRate').value);
                if ($('breathingRate').value=="NaN") $('breathingRate').value="";
            }) ;
            eventutil.addEventListener($('heartRate'), "change",function(){
                $('heartRate').value=parseInt($('heartRate').value);
                if ($('heartRate').value=="NaN") $('heartRate').value="";
            }) ;
            eventutil.addEventListener($('rightHandAD'), "change",function(){
                $('rightHandAD').value=parseInt($('rightHandAD').value);
                if ($('rightHandAD').value=="NaN") $('rightHandAD').value="";
            }) ;
            eventutil.addEventListener($('legAD'), "change",function(){
                $('legAD').value=parseInt($('legAD').value);
                if ($('legAD').value=="NaN") $('legAD').value="";
            }) ;
        </script>
    </tiles:put>
</tiles:insert>