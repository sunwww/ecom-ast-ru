<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="preg_neonatalNewBornForm">
      <msh:sideMenu title="Новорожденный">
        <msh:sideLink key="ALT+2" params="id" action="/entityEdit-preg_neonatalNewBorn" name="Изменить" roles="/Policy/Mis/NewBorn/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-preg_neonatalNewBorn" name="Удалить" roles="/Policy/Mis/NewBorn/Delete" />
      </msh:sideMenu>
      <msh:sideMenu title="Показать">
    <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/View" name="Отделения по новорожденному" 
    	params="id"  action='/entityParentList-stac_newBornSlo'  key='Alt+6' title='Лечение в отделениях по новорожденному' 
    	styleId="stac_newBornSlo"
    	/>      
      </msh:sideMenu>
      <msh:sideMenu title="Печать">
        <msh:sideLink params="id" action="/print-newbornhistory.do?s=PrintNewBornHistoryService&amp;m=printNewBornHistory" name="Историю развития новорожденного" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Новорожденный
    	  -->
    <msh:form action="/entityParentSaveGoView-preg_neonatalNewBorn.do" defaultField="lastname">
      <msh:hidden property="id" />
      <msh:hidden property="medCase" />
      <msh:hidden property="childBirth" />
      <msh:hidden property="saveType" />
      <msh:hidden property="patient" />
        
      <msh:panel colsWidth="7%,7%,4%">
      <msh:separator label="Роды" colSpan="4"/>
         <msh:row>
          	<msh:autoComplete property="child" vocName="vocNewBorn" label="Ребенок" horizontalFill="true" fieldColSpan="3"/>
          </msh:row>
        <msh:ifFormTypeIsView formName="preg_neonatalNewBornForm">
          <msh:row>
            <msh:autoComplete property="patient" vocName="patient" label="Персона ребенка" fieldColSpan="3" horizontalFill="true" viewOnlyField="true" viewAction="entityView-mis_patient.do" />
          </msh:row>
        </msh:ifFormTypeIsView>
        <msh:row>
          <msh:autoComplete property="sex" vocName="vocSex" label="Пол" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="birthDate" label="Дата рождения" />
          <msh:textField property="birthTime" label="Время" />
        </msh:row>
        <msh:row>
          <msh:textField property="birthWeight" label="Вес при рождении (гр.)" />
          <msh:textField property="birthHeight" label="Рост при рождении (см)" />
        </msh:row>
        <msh:row>
          <msh:textField property="headCircle" label="Окружность головы" />
          <msh:textField property="shouldersCircle" label="Окружность плеч" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="liveBorn" label="Родился" vocName="vocLiveBorn" fieldColSpan="1" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="partBodyBorn" label="Родился" vocName="vocPartBodyBorn" fieldColSpan="1" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="maturity" vocName="vocNewBornMaturity" label="Зрелость" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:textField property="birthState" label="Состояние при рождении" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:textField property="setPart" label="Вставленная часть тела" />
          <msh:textField property="setView" label="Вид вставки" />
        </msh:row>
        <msh:row>
        <msh:autoComplete property="department" vocName="vocNewBornAllDeps" label="Переведен в отделение" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:separator label="Пуповина" colSpan="4" />
        </msh:row>
        <msh:row>
          <msh:textField property="cordLength" label="Длина пуповины (см)" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="entanglement" vocName="vocBirthEntanglement" label="Обвитие" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="entanglementMultiplicity" vocName="vocBirthEntanglementMultiplicity" label="Кол-во обвитий" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="whereEntanglement" vocName="vocBirthWhereEntanglement" label="Где" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
          <msh:autoComplete property="cordAttaching" horizontalFill="true" vocName="vocNewBornCordAttaching" label="Прикрепление пуповины" />
          <msh:textField property="hemorrhageVolume" label="Кровопотеря (мл)" />
        </msh:row>
        
        <msh:row>
          <msh:separator label="Дополнительно" colSpan="4" />
        </msh:row>
        <msh:row>
          <msh:textField property="oesophagusPermeability" label="Проходимость пищевода" horizontalFill="true" />
          <msh:checkBox property="anus" label="Наличие ануса" />
        </msh:row>
        <msh:ifFormTypeIsNotView formName="preg_neonatalNewBornForm">
          <msh:row>
            <td class="label" title="Поиск по промежутку  (diabetIdentity)" colspan="1"><label for="diabetIdentityName" id="tdiabetIdentityLabel">Диабет у матери:</label></td>
            <td onclick="this.childNodes[1].checked='checked'; checkdiabetIdentity();" colspan="1" style="background-color:#ffffa0">
              <input type="radio" name="diabetIdentityRad" value="1" > Нет
            </td>
            <td onclick="this.childNodes[1].checked='checked'; checkdiabetIdentity();" colspan="3" style="background-color:#ffffa0">
              <input type="radio" name="diabetIdentityRad" value="2"> Да
            </td>
            <msh:autoComplete property="diabetIdentity" fieldColSpan="3" horizontalFill="true" label="" vocName="vocIdentityPatientNewBorn"/>
          </msh:row>
        </msh:ifFormTypeIsNotView>
        <msh:ifFormTypeIsView formName="preg_neonatalNewBornForm">
          <msh:row>
            <msh:autoComplete property="diabetIdentity" fieldColSpan="1" horizontalFill="true" label="Диабет у матери" vocName="vocIdentityPatientNewBorn" viewOnlyField="true"/>
          </msh:row>
        </msh:ifFormTypeIsView>
        <msh:row>
          <msh:textField property="malformations" label="Пороки развития" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="childDeliverer" label="Кто принимал ребенка" vocName="workFunction" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="createDate" label="Дата создания" viewOnlyField="true" />
          <msh:textField property="createTime" label="Время" viewOnlyField="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="username" label="Пользователь" viewOnlyField="true" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="3"  functionSubmit="if (checkSaveIdentity()) document.forms[0].submit();" />
      </msh:panel>
       <msh:ifFormTypeIsView formName="preg_neonatalNewBornForm">
    <msh:section title="Госпитализация">
    <ecom:webQuery name="list" nativeSql="select sls.id as f1slsid
  ,to_char(sls.dateStart,'dd.mm.yyyy') as f2dateStart
  ,to_char(sls.dateFinish,'dd.mm.yyyy') as f3dateFinish 
  ,vdh.id as f4vhdid,sls.username as f5slsusername
  ,case when sls.emergency='1' then 'да' else null end as f6emergency 
  ,coalesce(ss.code,'')||case when vdh.id is not null then ' '||vdh.name else '' end as f7stacard 
  ,ml.name as f8entdep,mlLast.name as f9mlLastdep 
  ,case when vdh.id is not null then null when (coalesce(sls.dateFinish,CURRENT_DATE)-sls.dateStart)=0 then 1 when vht.code='DAYTIMEHOSP' then ((coalesce(sls.dateFinish,CURRENT_DATE)-sls.dateStart)+1) else (coalesce(sls.dateFinish,CURRENT_DATE)-sls.dateStart) end as f10countDays 
  ,list(distinct vpd.name||' '||mkb.code) as f11diagDisch 
  ,list(distinct vpd1.name||' '||mkb1.code) as f12diagClinic
  ,case when vdh.id is not null then 'color: red ;' 
  when UPPER(sls.DTYPE) ='EXTHOSPITALMEDCASE' then 'color: blue ;'
  else '' end
    as f13style 
  from MedCase sls 
  left join newborn nb on nb.patient_id=sls.patient_id
  left join VocHospType vht on vht.id=sls.hospType_id 
  left join VocDeniedHospitalizating vdh on vdh.id=sls.deniedHospitalizating_id 
  left join MedCase sloLast on sloLast.parent_id=sls.id and sloLast.dtype='DepartmentMedCase' 
  left join StatisticStub ss on ss.id=sls.statisticStub_id 
  left join MisLpu mlLast on mlLast.id=sloLast.department_id 
  left join MisLpu ml on ml.id=sls.department_id	
  left join Diagnosis diag on sls.id=diag.medCase_id	
  left join VocIdc10 mkb on mkb.id=diag.idc10_id	
  left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id and vdrt.code='3'	
  left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id	
  left join Diagnosis diag1 on sloLast.id=diag1.medCase_id	
  left join VocIdc10 mkb1 on mkb1.id=diag1.idc10_id	
  left join VocDiagnosisRegistrationType vdrt1 on vdrt1.id=diag1.registrationType_id and vdrt1.code='4'	
  left join VocPriorityDiagnosis vpd1 on vpd1.id=diag1.priority_id where nb.id=${param.id} 
  and UPPER(sls.DTYPE) IN ('HOSPITALMEDCASE','EXTHOSPITALMEDCASE') 
  and  (sloLast.id is null or sloLast.transferDate is null) 
   group by sls.id,sls.dtype,sls.dateStart,sls.dateFinish,vdh.id ,sls.username ,sls.emergency, ss.code,vdh.id,vdh.name ,ml.name,mlLast.name,vht.code 
   order by sls.dateStart
  "/>
    <msh:table name="list" action="entitySubclassView-mis_medCase.do" 
    viewUrl="entitySubclassView-mis_medCase.do?short=Short"
    idField="1" styleRow="13">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Стат.карта" property="7" />
      <msh:tableColumn columnName="Дата поступления" property="2" />
      <msh:tableColumn columnName="Дата выписки" property="3" />
      <msh:tableColumn columnName="Кол-во дней" property="10" />
      <msh:tableColumn columnName="Экстр.?" property="6" />
      <msh:tableColumn columnName="Отделение пост." property="8" />
      <msh:tableColumn columnName="Отделение выписки (текущее)" property="9" />
      <msh:tableColumn columnName="Диагноз клин. посл. отд." property="12" />
      <msh:tableColumn columnName="Диагноз выписной" property="11" />
      <msh:tableColumn columnName="Кем открыт" property="5" />
    </msh:table>
    </msh:section>
    </msh:ifFormTypeIsView>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="preg_neonatalNewBornForm" />
  </tiles:put>
  <script type="text/javascript" src="./dwr/interface/PregnancyService.js" >/**/</script>
  <tiles:put name="javascript" type="string">
  <script type="text/javascript">
      //проверка диабета у матери
      function checkdiabetIdentity() {
          if (document.getElementsByName("diabetIdentityRad") && document.getElementsByName("diabetIdentityRad")[1]) {
              if (document.getElementsByName("diabetIdentityRad")[1].checked) {
                  $('diabetIdentity').removeAttribute('hidden');
                  $('diabetIdentityName').removeAttribute('hidden');
                  $('diabetIdentity').value='';
                  $('diabetIdentityName').value='';
                  $('diabetIdentityName').className = "autocomplete horizontalFill required";
                  setAutoBracelet();
              }
              else {

                  $('diabetIdentity').setAttribute('hidden', true);
                  $('diabetIdentityName').setAttribute('hidden', true);
                  $('diabetIdentity').value='';
                  $('diabetIdentityName').value='';
                  $('diabetIdentityName').className = 'autocomplete horizontalFill';
              }
          }
      }
      //если есть единственное значение для браслета новорожд., то выбрать его автоматически
      function setAutoBracelet() {
          PregnancyService.getAutoBracelet({
              callback: function (aResult) {
                  if (aResult != null && aResult != '{}') {
                      var res = JSON.parse(aResult);
                      $('diabetIdentity').value=res.id;
                      $('diabetIdentityName').value=res.name;
                  }
              }
          });
      }
      //проверка перед сохранением
      function checkSaveIdentity() {
          if (!(document.getElementsByName("diabetIdentityRad") && document.getElementsByName("diabetIdentityRad")[1] && document.getElementsByName("diabetIdentityRad")[1].checked && $('diabetIdentity').value!=''
              || document.getElementsByName("diabetIdentityRad") && document.getElementsByName("diabetIdentityRad")[0] && document.getElementsByName("diabetIdentityRad")[0].checked && $('diabetIdentity').value=='')) {
              alert('Заполните правильно диабет матери!');
              $('submitButton').disabled = false;
              return false;
          }
          else return true;
      }
      <msh:ifFormTypeIsNotView formName="preg_neonatalNewBornForm">
      checkdiabetIdentity();
      </msh:ifFormTypeIsNotView>
  </script>
  </tiles:put>
</tiles:insert>

