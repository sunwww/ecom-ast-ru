<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="entitySaveGoView-mis_lpu.do" defaultField="omcCodeName">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="address" />
      <input type="hidden" id="flatNumber" name="flatNumber" />
      <msh:hidden property="houseBuilding" />
      <msh:hidden property="houseNumber" />
      <msh:hidden property="parent" />
      <msh:panel>
        <msh:ifInRole roles="/Policy/Mis/MisLpu/EditParent">
          <msh:ifFormTypeIsNotView formName="mis_lpuForm">
            <msh:ifFormTypeAreViewOrEdit formName="mis_lpuForm">
              <msh:row>
                <msh:autoComplete property="parent" vocName="lpu" label="Родительское ЛПУ" viewAction="entityEdit-mis_lpu.do" fieldColSpan="3" horizontalFill="true" />
              </msh:row>
            </msh:ifFormTypeAreViewOrEdit>
          </msh:ifFormTypeIsNotView>
        </msh:ifInRole>
        <msh:row>
          <msh:textField property="omcCode" label="Код ФОНДА" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
        	<msh:checkBox property="lpuLevel" label="Уровень оказания мед. помощи"/>
        </msh:row>

        <msh:row>
          <msh:textField property="name" label="Название" horizontalFill="true" fieldColSpan="3" size="50" />
        </msh:row>
         <msh:row>
        	<msh:textField property="shortName" label="Корот. наименование" fieldColSpan="1" horizontalFill="true"/>
        	<msh:textField property="prefixForLN" label="Префикс для шаблонов ЛНТ" fieldColSpan="1" horizontalFill="true"/>
        </msh:row>
               <msh:row>
          <msh:textField property="printName" label="Название для печати" horizontalFill="true" fieldColSpan="3" size="50" />
        </msh:row>
        <msh:row>
          <msh:textField property="printAddress" label="Адрес для печати" horizontalFill="true" fieldColSpan="3" size="50" />
        </msh:row>
        <msh:row>
          <msh:autoComplete showId="false" vocName="vocLpuFunction" hideLabel="false" property="lpuFunction" viewOnlyField="false" label="Функция ЛПУ" horizontalFill="true" />
          <msh:checkBox property="isNoOmc" label="Не входит в ОМС?"/>
        </msh:row>
        <msh:row>
          <msh:autoComplete showId="false" vocName="vocPigeonHole" property="pigeonHole" label="Приемник" horizontalFill="true" fieldColSpan="1" />
        </msh:row>
        <msh:row>
          <msh:textField property="codeDepartment"/>
        </msh:row>
        <msh:row>
          <msh:autoComplete property="director" label="Начальник" horizontalFill="true" fieldColSpan="3" vocName="workFunctionByDirect" parentId="mis_lpuForm.id"/>
        </msh:row>
        <msh:row>
          <msh:autoComplete property="accessEnterOperation" label="Доступ на создание опреаций" horizontalFill="true" fieldColSpan="3" vocName="vocLpuAccessEnterOperation" parentId="mis_lpuForm.id"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="registrationInterval" label="Интервал регистр." horizontalFill="true"/>
        	<msh:checkBox property="isIntakeBioMaterial" label="В отд. осущ. забор биомат."/>
        </msh:row>
        <msh:row>
        	<msh:textField property="codef" label='№реестра' horizontalFill="true" />
        	<msh:textField property="socCode" label="Код в ФСС"/>
        </msh:row>
        <msh:row>
          <msh:textField property="inn" label="ИНН" />
          <msh:textField property="ogrn" label="ОГРН" />
        </msh:row>
        <msh:row>
          <td colspan="1" title="Адрес (addressField)" class="label">
            <label id="addressFieldLabel" for="addressField">Адрес:</label>
          </td>
          <td colspan="5" class="addressField">
            <input title="АдресNoneField" class=" horizontalFill" id="addressField" name="addressField" size="10" value="Адрес... " type="text" />
          </td>
        </msh:row>
        <msh:row>
          <msh:textField property="phone" label="Контактный телефон" horizontalFill="true" />
          <msh:textField property="email" label="Эл. почта" horizontalFill="true" size="10" />
        </msh:row>
        <msh:row>
        	<msh:checkBox property="isNoViewRemoteUser"/>
       		<msh:autoComplete property="copyingEquipmentDefault" vocName="copyingEquipment" horizontalFill="true" label="Принтер"/>
        </msh:row>
        <msh:row>
        <msh:autoComplete property="medicalStandard" vocName="vocMedStandard" label="Стандарт оказания мед. помощи" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        <msh:autoComplete property="emergencyCabinet" vocName="funcMedServiceRoom" label="Экстренный кабинет" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        <msh:autoComplete property="kiliProfile" vocName="vocKiliProfile" label="Профиль КИЛИ" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:checkBox property="isNewBornDep" label="Отд. новорожденных"/>
        	<msh:checkBox property="isMaternityWard" label="Родильное отделение"/>
            <msh:checkBox property="isPatologyPregnant" label="Отделение патологии беременности"/>
        </msh:row>
          <msh:row>
              <msh:checkBox property="isOphthalmic" label="Офтальмологическое отделение"/>
              <msh:checkBox property="isForCovid" label="Инфекционное"/>
          </msh:row>
        <msh:row>
            <msh:checkBox property="isMobilePolyclinic" label="Мобильная поликлиника"/>
        	<msh:checkBox property="isArchive" label="Не используется (в архиве)"/>
            <msh:checkBox property="isReportKMP" label="Учитывать в отчёте по экспертизе КР"/>
        </msh:row>
      <msh:row>
          <msh:checkBox property="isCreateCardiacScreening" label="Создают кардиоскрининги новорожд."/>
      </msh:row>
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
        <msh:submitCancelButtonsRow colSpan="2" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="mis_lpuForm">
    <table width="100%">
    	<tr>
    		<td width="15%" valign="top" style="padding-right: 1em">
			      <msh:section title="Список подразделений">
			        <ecom:webQuery name="subdivisions" nativeSql="select ml.id,ml.name as mlname
			         ,vlf.name as vlfname 
			        from mislpu ml
			        left join VocLpuFunction vlf on vlf.id=ml.lpuFunction_id
			        where ml.parent_id=${param.id} order by ml.name"/>
			        <msh:table hideTitle="true" name="subdivisions" action="entityParentView-mis_lpu.do" idField="1">
			          <msh:tableColumn columnName="" property="1" />
			          <msh:tableColumn columnName="" property="2" />
			          <msh:tableColumn columnName="" property="3" />
			        </msh:table>
			      </msh:section>
    		</td>
<msh:ifInRole roles="/Policy/Mis/MisLpu/OperatingRoom/View">
    		<td width="15%" valign="top" style="padding-right: 1em">
    			<msh:section>
    				<msh:sectionTitle>Список операционных. <a href='entityParentPrepareCreate-mis_operatingRoom.do?id=${param.id}'>Добавить</a></msh:sectionTitle>
    				<msh:sectionContent>
    					<ecom:webQuery name="operatingRooms" nativeSql="select wp.id,wp.groupname from WorkFunction wp where wp.lpu_id='${param.id}' and wp.dtype='OperatingRoom'"/>
    					<msh:table  name="operatingRooms" hideTitle="true" action="entityParentView-mis_operatingRoom.do" idField="1">
    						<msh:tableColumn property="2"/>
    					</msh:table>
    				</msh:sectionContent>
    			</msh:section>
    		</td>
  			</msh:ifInRole>
  			    			<msh:ifInRole roles="/Policy/Mis/BedFund/View">
    		<td width="70%" valign="top">
			      <msh:section>
			      <msh:sectionTitle>Коечный фонд 
			      <msh:link action="/entityParentPrepareCreate-mis_bedFund.do?id=${param.id}" roles="/Policy/Mis/BedFund/Create">Добавить</msh:link> 
			      <msh:link action="/entityParentList-mis_bedFund.do?id=${param.id}" roles="/Policy/Mis/BedFund/View">К списку</msh:link> 
			      </msh:sectionTitle>
			      <msh:sectionContent>
			        <ecom:parentEntityListAll formName="mis_bedFundForm" attribute="bedFund" />
			        <msh:table hideTitle="false" name="bedFund" action="entityParentView-mis_bedFund.do" idField="id">
			          <msh:tableColumn columnName="Профиль" property="bedTypeName" />
			          <msh:tableColumn columnName="Тип " property="bedSubTypeName" />
			          <msh:tableColumn columnName="Поток обсл. " property="serviceStreamName" />
			          <msh:tableColumn columnName="Для детей" property="forChild" />
			          <msh:tableColumn columnName="Реабил." property="isRehab" />
			          <msh:tableColumn columnName="Питание не вкл." property="noFood" />
			          <msh:tableColumn columnName=" " property="reductionTypeName" />
			          <msh:tableColumn columnName="Дата нач." property="dateStart" />
			          <msh:tableColumn columnName="Дата окон." property="dateFinish" />
			          <msh:tableColumn columnName="Кол-во" property="amount" />
			        </msh:table>
			        </msh:sectionContent>
			      </msh:section>
    		</td>
			      </msh:ifInRole>
    	</tr>
    </table>
    <table width=100%>
    	<tr>
    		<td  valign="top" style="padding-right: 1em">
			      <msh:section title="Шаблоны календарей" createUrl="entityParentPrepareCreate-cal_patternBySpec.do?id=${param.id}" viewRoles="/Policy/Mis/Worker/WorkCalendar/Pattern/Specialist/View" createRoles="/Policy/Mis/Worker/WorkCalendar/Pattern/Specialist/Create" listUrl="entityParentList-cal_patternBySpec.do?id=${param.id}">
			      	<ecom:webQuery nativeSql="select wcp.id,wcp.name,vwct.name as vwctname 
			      	from WorkCalendarPattern wcp 
			      	left join VocWorkCalendarType vwct on vwct.id=wcp.calendarType_id 
			      	where wcp.lpu_id=${param.id}" 
			      	name="calendarPattern" />
			      	<msh:table viewUrl="entityShortView-cal_patternBySpec.do" deleteUrl="entityParentDeleteGoParentView-cal_patternBySpec.do" editUrl="entityParentEdit-cal_patternBySpec.do" name="calendarPattern" action="entitySubclassView-cal_pattern.do" idField="1">
			      		<msh:tableColumn property="sn" columnName="#"/>
			      		<msh:tableColumn property="2" columnName="Название"/>
			      		<msh:tableColumn property="3" columnName="Тип календаря"/>
			      	</msh:table>
			      </msh:section>
    		</td>
    		<td  valign="top">
		      <msh:section title="Шаблоны дней календаря" createRoles="/Policy/Mis/Worker/WorkCalendar/Pattern/Day/Create" viewRoles="/Policy/Mis/Worker/WorkCalendar/Pattern/Day/View" createUrl="entityParentPrepareCreate-cal_dayPattern.do?id=${param.id}" listUrl="entityParentList-cal_dayPattern.do?id=${param.id}">
		      	<ecom:webQuery nativeSql="select wcdp.id,wcdp.name,vwb.name as vwbname from WorkCalendarDayPattern wcdp 
		      	left join VocWorkBusy vwb on vwb.id=wcdp.workBusy_id 
		      	where wcdp.lpu_id=${param.id}" 
		      	name="calendarDayPattern" />
		      	<msh:table viewUrl="entityShortView-cal_dayPattern.do" deleteUrl="entityParentDeleteGoParentView-cal_dayPattern.do" editUrl="entityParentEdit-cal_dayPattern.do" name="calendarDayPattern" action="entityParentView-cal_dayPattern.do" idField="1">
		      		<msh:tableColumn property="sn" columnName="#"/>
		      		<msh:tableColumn property="2" columnName="Название"/>
		      		<msh:tableColumn property="3" columnName="Тип занятости"/>
		      	</msh:table>
		      </msh:section>
    		</td>
    	</tr>
    </table>
      <table width="100%">
        <tr>
          <td width="50%" valign="top" style="padding-right: 1em">
            <msh:ifInRole roles="/Policy/Mis/LpuArea/View">
              <msh:section title="Список участков ЛПУ">
                 <ecom:webQuery name="areaList" nativeSql="select la.id  
              	,la.number as number
				,vat.name as vatName
				,vwf.name ||' '||wp.lastname
				from lpuarea la
				left join vocareatype vat on vat.id=la.type_id
				left join workfunction wf on wf.id=la.workfunction_id
				left join worker w on w.id=wf.worker_id
				left join patient wp on wp.id=w.person_id
				left join vocworkfunction vwf on vwf.id=wf.workfunction_id
				where la.lpu_id=${param.id} "/>
                <msh:table name="areaList" action="entityParentView-mis_lpuArea.do" idField="1">
                  <msh:tableColumn columnName="Номер участка" property="2" />
                  <msh:tableColumn columnName="Тип" property="3" />
                  <msh:tableColumn columnName="Участковый" property="4" />
                </msh:table>
              </msh:section>
              <msh:section title="Список участков подразделений ЛПУ">
              <ecom:webQuery name="areaList" nativeSql="select la.id  
              	,la.number as number
				,vat.name as vatName
				,lpu.name as lpuName
				from mislpu lpu
				left join lpuarea la on la.lpu_id=lpu.id
				left join vocareatype vat on vat.id=la.type_id
				where lpu.parent_id=${param.id} and la.id is not null "/>
                <ecom:parentEntityListAll formName="mis_lpuAreaForm" attribute="areas" />
                <msh:table name="areaList" action="entityParentView-mis_lpuArea.do" idField="1">
                  <msh:tableColumn columnName="Номер участка" property="2" />
                  <msh:tableColumn columnName="Тип" property="3" />
                  <msh:tableColumn columnName="Подразделение" property="4" />
                </msh:table>
              </msh:section>
            </msh:ifInRole>
          </td>
          
          
          
          <td valign="top">
            <!-- Сохранить -->
            <msh:section title="Прикрепленное население">
              <ecom:webQuery name="byLpu" nativeSql="select lpu_id, count(*) from Patient where lpu_id=${param.id} group by lpu_id" />
              <msh:table name="byLpu" idField="1" action="mis_patients.do?lpu=">
                <msh:tableColumn property="2" columnName="Количество прикрепленного населения" />
              </msh:table>
              <h3>По категориям льготников</h3>
              <ecom:webQuery name="byCategory" nativeSql="select lpu_id, VocPrivilegeCategory.id, VocPrivilegeCategory.name, count(*)     from Privilege   inner join Patient on Privilege.person_id=Patient.id    left outer join VocPrivilegeCategory on Privilege.category_id=VocPrivilegeCategory.id   where Patient.lpu_id=${param.id} group by lpu_id, VocPrivilegeCategory.id, VocPrivilegeCategory.name   order by VocPrivilegeCategory.name" />
              <msh:table name="byCategory" idField="1" action="mis_patients.do?lpu=">
                <msh:tableColumn property="3" columnName="Категория льготы" />
                <msh:tableColumn property="4" columnName="Количество" />
              </msh:table>
              <h3>По коду льготы</h3>
              <ecom:webQuery name="byCategory" nativeSql="select lpu_id, VocPrivilegeCode.code, VocPrivilegeCode.name, count(*)     from Privilege   inner join Patient on Privilege.person_id=Patient.id    left outer join VocPrivilegeCode on Privilege.privilegeCode_id=VocPrivilegeCode.id   where Patient.lpu_id=${param.id} group by lpu_id, VocPrivilegeCode.code, VocPrivilegeCode.name   order by VocPrivilegeCode.code" />
              <msh:table name="byCategory" idField="1" action="mis_patients.do?lpu=">
                <msh:tableColumn property="2" columnName="Код" />
                <msh:tableColumn property="3" columnName="Наименование" />
                <msh:tableColumn property="4" columnName="Количество" />
              </msh:table>
            </msh:section>
          </td>
        </tr>
        <tr>
        <msh:ifInRole roles="/Policy/Mis/MisLpu/OperatingRoom/View">
          <td width="15%" valign="top" colspan="3" style="padding-right: 1em">
    			<msh:section>
    				<msh:sectionTitle>Правила установки диагноза в отделении. <a href='entityParentPrepareCreate-mis_lpuDiagnosisRule.do?id=${param.id}'>Добавить</a></msh:sectionTitle>
    				<msh:sectionContent>
    					<ecom:webQuery name="diagnosisRule" nativeSql="
    					select ldr.id, list (cng.name) from lpudiagnosisrule ldr
						left join lpucontractnosologygroup lcng on lcng.lpudiagnosisrule=ldr.id
						left join contractnosologygroup cng on cng.id=lcng.nosologygroup
    					where ldr.department='${param.id}' group by ldr.id"/>
    					<msh:table  name="diagnosisRule" hideTitle="true" action="entityParentView-mis_lpuDiagnosisRule.do" idField="1">
    						<msh:tableColumn property="1"/>
    						<msh:tableColumn property="2"/>
    						
    					</msh:table>
    				</msh:sectionContent>
    			</msh:section>
    		</td>
  			</msh:ifInRole>
  			       
          <td width="15%" valign="top" colspan="3" style="padding-right: 1em">
    			<msh:section>
    				<msh:sectionTitle>В ЛПУ входят след. подразделения. <a href='entityParentPrepareCreate-mis_lpuGroup.do?id=${param.id}'>Добавить</a></msh:sectionTitle>
    				<msh:sectionContent>
    					<ecom:webQuery name="lpuGroups" nativeSql="
    					select lg.id, ml.name from lpuGroup lg
						left join mislpu ml on ml.id=lg.lpu
						where lg.groupLpu='${param.id}' order by ml.name"/>
    					<msh:table  name="lpuGroups" hideTitle="true" action="entityParentView-mis_lpuGroup.do" idField="1">
    						<msh:tableColumn property="1"/>
    						<msh:tableColumn property="2"/>
    						
    					</msh:table>
    				</msh:sectionContent>
    			</msh:section>
    		</td>
  			
  			
        </tr>
      </table>
        <msh:section>
        <msh:sectionTitle>Реквизиты для печати по ЛПУ <a href='entityParentPrepareCreate-mis_lpuRequisite.do?id=${param.id}'>Добавить</a></msh:sectionTitle>
        <msh:sectionContent>
        <ecom:webQuery name="requisites" nativeSql="select id, name, code, value
        from MisLpuRequisite where lpu_id=${param.id}" />
        <msh:table name="requisites" action="entityView-mis_lpuRequisite.do" idField="1">
            <msh:tableColumn columnName="Название" property="2" />
            <msh:tableColumn columnName="Код" property="3" />
            <msh:tableColumn columnName="Значение" property="4" />
        </msh:table>
        </msh:sectionContent></msh:section>
    </msh:ifFormTypeIsView>
    <tags:addressTag />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="ЛПУ">
      <msh:sideLink key="ALT+1" params="" action="/entityParentList-mis_lpu.do?id=-1" name="Список ЛПУ" />
      <msh:ifFormTypeIsView formName="mis_lpuForm">
        <msh:sideLink key="ALT+2" roles="/Policy/Mis/MisLpu/Edit" params="id" action="/entityEdit-mis_lpu" name="Изменить" />
      </msh:ifFormTypeIsView>
      <msh:ifFormTypeAreViewOrEdit formName="mis_lpuForm">
        <msh:sideLink key="ALT+DEL" params="id" roles="/Policy/Mis/MisLpu/Delete" action="/entityParentDeleteGoParentView-mis_lpu" name="Удалить" confirm="Удалить ЛПУ?" />
      </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>
    <msh:ifFormTypeIsView formName="mis_lpuForm">
      <msh:sideMenu title="Добавить">
        <msh:sideLink key="ALT+3" params="id" roles="/Policy/Mis/MisLpu/Create" action="/entityParentPrepareCreate-mis_lpu" name="Подразделение" title="Добавить Подразделение" />
        <msh:sideLink key="ALT+4" params="id" roles="/Policy/Mis/LpuArea/Create" action="/entityParentPrepareCreate-mis_lpuArea" name="Участок" title="Добавить Участок" />
        <msh:sideLink roles="/Policy/Mis/Worker/WorkCalendar/Pattern/Specialist/Create" key="ALT+4" params="id" action="/entityParentPrepareCreate-cal_patternBySpec" name="Шаблон календаря" title="Добавить шаблон календаря" />
        <msh:sideLink roles="/Policy/Mis/Worker/WorkCalendar/Pattern/Day/Create" key="ALT+5" params="id" action="/entityParentPrepareCreate-cal_dayPattern" name="Шаблон дня календаря" title="Добавить шаблон дня календаря" />
      </msh:sideMenu>
      <msh:sideMenu title="Показать">
      <msh:sideLink roles="/Policy/Mis/Patient/ViewInfoArea" action="/js-mis_lpuArea-print_lpu_by_address.do?id=${param.id}" name="Печать по адресам пациентов для выборов"/>
        <msh:sideLink params="id" roles="/Policy/Mis/MedService/View" action="/js-mis_lpu-journal_medservice" title="Список услуг по отделению" name="Услуги по отделению" />
        <msh:sideLink key="ALT+5" params="id" roles="/Policy/Mis/Licence/View" action="/entityParentList-mis_licence" title="Список лицензий" name="Лицензии" />
        <msh:sideLink key="ALT+6" params="id" action="/js-mis_worker-all" title="Список сотрудников" name="Сотрудников" roles="/Policy/Mis/Worker/Worker/View" />
        <msh:sideLink roles="/Policy/Mis/Diet/View" params="id" action="/entityParentList-diet_diet" name="Диеты" title="Показать диеты" />
        <msh:sideLink key="ALT+7" params="id" action="/entityParentList-work_staff" name="Штатное расписание" title="Штатное расписание" roles="/Policy/Mis/Worker/Staff/View" />
        <msh:sideLink key="ALT+8" params="id" action="/entityParentList-mis_medicalEquipment" title="Список мед. оборудования" name="Медицинское оборудование" roles="/Policy/Mis/Equipment/Equipment/View" />
        <msh:sideLink key="ALT+9" params="id" action="/entityParentList-work_groupWorkFunction" title="Список групповых рабочих функций" name="Групповые рабочие функции" roles="/Policy/Mis/Worker/WorkFunction/View" />
        <msh:sideLink params="id" action="/entityParentList-mis_bedFund" title="Коечный фонд" name="Коечный фонд" roles="/Policy/Mis/BedFund/View" />
        <msh:sideLink params="id" action="/entityParentList-mis_mortalityReportDate" name="Сведения о смертности" title="Показать сведения о смертности" roles="/Policy/Mis/Report/Mortality/View" />
        <msh:sideLink roles="/Policy/Mis/Report/Birth/View" params="id" action="/entityParentList-mis_birthReportDate" name="Сведения по рождаемости" title="Показать сведения по рождаемости" />
        <msh:sideLink roles="/Policy/Mis/Worker/WorkFunction/View" key="ALT+7" params="id" action="/js-mis_worker-pattern" name="Шаблоны расписания сотрудников" title="Перейти к установке шаблонов календарей по специалистам" />
        <msh:sideLink roles="/Policy/Mis/Equipment/Equipment/View" params="id" action="/js-mis_lpu-showStandard" name="Оснащение по стандарту" title="Шаблоны по стандарту" />
        <msh:sideLink roles="/Policy/Mis/Equipment/KkmEquipment/View" params="id" action="/entityParentList-mis_kkmequipment" name="Кассовые аппараты" title="Кассовые аппараты" />
        <msh:sideLink roles="/Policy/Mis/ColorIdentityEdit" params="id" action="/entityParentList-mis_colorIdentity" name="Цвета браслетов" title="Цвета браслетов" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Lpu" beginForm="mis_lpuForm" />
  </tiles:put>
  <tiles:put name="javascript" type="string">
    <script type="text/javascript">
      <msh:ifFormTypeIsView formName="mis_lpuForm">$('buttonShowAddress').style.display = 'none';</msh:ifFormTypeIsView>
      $('addressFlatNumber1').style.display = 'none';
        $('addressFlatNumber1Label').style.display = 'none';
      $('addressZipcode').style.display = 'none';
        $('addressZipcodeLabel').style.display = 'none';        
      <msh:ifFormTypeIsNotView formName="mis_lpuForm">function onOmcCodeNameBlur() {
            var nameField = $('name') ;
            if (nameField.value == "") nameField.value = $('omcCodeName').value;
        }
        eventutil.addEventListener($('omcCodeName'), "blur", onOmcCodeNameBlur);</msh:ifFormTypeIsNotView>
    </script>
  </tiles:put>
  <tiles:put name="style" type="string">
    <style type="text/css">h3 {
    		margin-top: 1em ;
    		margin-bottom: 0.5em ;
    		color: brown ;
    	}</style>
  </tiles:put>
</tiles:insert>

