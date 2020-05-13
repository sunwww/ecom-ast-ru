<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="stac_screeningCardiacFirstForm">
      <msh:sideMenu title="Скрининг новорождённых">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-stac_screeningCardiacFirst" name="Изменить" roles="/Policy/Mis/Pregnancy/CardiacScreening/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-stac_screeningCardiacFirst" name="Удалить" roles="/Policy/Mis/Pregnancy/CardiacScreening/Delete" />
        <msh:sideLink key="ALT+N" action="/javascript:window.location.href='entityParentPrepareCreate-stac_screeningCardiacSecond.do?id='+$('medCase').value;" name="Кардио-скрининг нов. (II этап)" roles="/Policy/Mis/Pregnancy/CardiacScreening/Create" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
</tiles:put>
  <tiles:put name="body" type="string">
      <msh:form action="/entityParentSaveGoView-stac_screeningCardiacFirst.do" defaultField="skin">
        <msh:hidden property="id" />
        <msh:hidden property="medCase" />
        <msh:hidden property="saveType" />
        <msh:panel>
        <msh:row>
          <msh:autoComplete property="skin" label="Кожные покровы" vocName="vocScreeningSkin"  size="30"/>
        </msh:row>
        <msh:separator label="Пульсация артерий конечностей:" colSpan="6"/>
          <msh:row>
            <msh:autoComplete property="rightHandAP" label="Правая рука (лучевая/локтевая артерии)" vocName="vocScreeningArterialPulsation" size="30"/>
          </msh:row>
          <msh:row>
            <msh:autoComplete property="legAP" label="Нога (дорзальная артерия стопы/бедренная артерия)" vocName="vocScreeningArterialPulsation"  size="30"/>
          </msh:row>
          <msh:separator label="Пульсоксиметрия на конечностях одновременная:" colSpan="6"/>
        <msh:row>
          <msh:textField property="rightHandPulse" label="правая рука"/>
          <msh:textField property="legPulse" label="нога"/>
        </msh:row>
          <msh:separator label="Характеристика дыхания:" colSpan="6"/>
        <msh:row>
          <msh:textField property="breathingRate" label="частота в мин."/>
        </msh:row>
        <msh:row>
          <msh:checkBox property="retractionIntercostalGaps" label="втяжение межрёберных промежутков"/>
          <msh:checkBox property="noseWingsMovement" label="движение крыльев носа"/>
          <msh:checkBox property="noisyBreathing" label="шумное дыхание"/>
          <msh:checkBox property="wheezing" label="хрипы"/>
        </msh:row>
        <msh:row>
          <br>
          <msh:autoComplete property="CNS" label="Характеристика ЦНС" vocName="vocScreeningCNS"  size="30"/>
        </msh:row>
          <msh:separator label="Характеристика сердечной деятельности:" colSpan="6"/>
        <msh:row>
          <msh:textField property="heartRate" label="частота в мин."/>
        </msh:row>
        <msh:row>
          <br>
          <msh:autoComplete property="cardiacActivity" label="Характеристика СД" vocName="vocScreeningCardiacActivity"  size="30"/>
        </msh:row>
        <msh:row>
          <msh:checkBox property="noisePresence" label="наличие шума"/>
        </msh:row>
        <br>
        <msh:row>
          <msh:textArea property="extraInfo" label="Дополнительные сведения: подозрения на синдромальную патологию, множественные пороки развития"
                        horizontalFill="true"/>
        </msh:row>
        <msh:ifFormTypeAreViewOrEdit formName="stac_screeningCardiacFirstForm">
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
          <msh:submitCancelButtonsRow colSpan="5" />
        </msh:panel>
  </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="stac_screeningCardiacFirstForm" />
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
    </script>
  </tiles:put>
</tiles:insert>