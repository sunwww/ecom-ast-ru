<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <msh:form action="entitySaveGoView-mis_lpu.do" defaultField="omcCodeName" guid="17f720e4-3690-4ae5-961b-6d69348757b6">
      <msh:hidden property="id" guid="df46ed12-bbf3-4f90-9046-dcf1b595541c" />
      <msh:hidden property="saveType" guid="12fee02b-f848-4039-b3f6-35cbf5f3a629" />
      <msh:hidden property="address" guid="8f29b46c-9ec3-4dd9-883c-5d9f4efd13a4" />
      <input type="hidden" id="flatNumber" name="flatNumber" />
      <msh:hidden property="houseBuilding" guid="cb67289f-0555-4cf8-95ba-b3c34ea8e017" />
      <msh:hidden property="houseNumber" guid="fe91ab4d-1971-496c-8169-ee59ec4aebd5" />
      <msh:hidden property="parent" guid="289afe10-7420-4b97-b514-d2403eeb73a1" />
      <msh:panel guid="eb62e08a-d34a-4af0-9f13-d23197a33fef">
        <msh:ifInRole roles="/Policy/Mis/MisLpu/EditParent" guid="96b64dbe-1907-48e8-b2e2-e39a570c185a">
          <msh:ifFormTypeIsNotView formName="mis_lpuForm" guid="78609968-ca83-4a72-86a9-1ae6cb7fdaaa">
            <msh:ifFormTypeAreViewOrEdit formName="mis_lpuForm" guid="12393d17-3068-40c2-8dc7-9b9ee4252715">
              <msh:row guid="a7a62505-2bfe-41b6-a54f-217b970dc0c3">
                <msh:autoComplete property="parent" vocName="lpu" label="Родительское ЛПУ" viewAction="entityEdit-mis_lpu.do" fieldColSpan="3" guid="67d2a4af-71bc-4a19-8844-4a59b97fabda" horizontalFill="true" />
              </msh:row>
            </msh:ifFormTypeAreViewOrEdit>
          </msh:ifFormTypeIsNotView>
        </msh:ifInRole>
        <msh:row guid="e3c328fa-adbc-4a2d-bcd7-ac189b052f41">
          <msh:textField property="omcCode" label="Код ФОНДА" fieldColSpan="3" horizontalFill="true" guid="947bb0fe-365f-4147-b6cb-0ed0b07c43b2" />
        </msh:row>
        <msh:row>
        	<msh:checkBox property="lpuLevel" label="Уровень оказания мед. помощи"/>
        </msh:row>

        <msh:row guid="2df6f0d2-a60d-44a5-b64b-3aeb3f298d04">
          <msh:textField property="name" label="Название" horizontalFill="true" fieldColSpan="3" size="50" guid="f1d2a4ec-81ad-48bb-afe9-8983e4017245" />
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
        <msh:row guid="69052d-41b4-9362-7d1609e26759">
          <msh:autoComplete showId="false" vocName="vocLpuFunction" hideLabel="false" property="lpuFunction" viewOnlyField="false" label="Функция ЛПУ" guid="15c7ac5f-10c9-4c1e-2f775fc" horizontalFill="true" />
          <msh:checkBox property="isNoOmc" label="Не входит в ОМС?"/>
        </msh:row>
        <msh:row guid="69052595-06cd-41b4-9362-7d1609e26759">
          <msh:autoComplete showId="false" vocName="vocPigeonHole" property="pigeonHole" label="Приемник" horizontalFill="true" fieldColSpan="1" />
        </msh:row>
        <msh:row guid="2398008e-85f4-4ed7-83f6-f2a186533ac5">
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
        <msh:row guid="0985a3e5-06a4-4245-b652-03e5fc74acf6">
          <msh:textField property="inn" label="ИНН" guid="01e13e55-f966-4eb7-815b-bcaf82617a05" />
          <msh:textField property="ogrn" label="ОГРН" guid="ecaa5fcc-7f49-4f9b-aeac-b9823dd54671" />
        </msh:row>
        <msh:row guid="b8fd6a0c-916d-4065-84e0-25ca4a02a2fd">
          <td colspan="1" title="Адрес (addressField)" class="label">
            <label id="addressFieldLabel" for="addressField">Адрес:</label>
          </td>
          <td colspan="5" class="addressField">
            <input title="АдресNoneField" class=" horizontalFill" id="addressField" name="addressField" size="10" value="Адрес... " type="text" />
          </td>
        </msh:row>
        <msh:row guid="620de35f-acd9-4719-b84e-7c4092dd8a27">
          <msh:textField property="phone" label="Контактный телефон" horizontalFill="true" guid="278a27cf-a55d-4c35-accb-2b5a070ef202" />
          <msh:textField property="email" label="Эл. почта" horizontalFill="true" size="10" guid="bcc76cb8-ae50-4785-b150-d3e621fb8b61" />
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
            <msh:checkBox property="isOphthalmic" label="Офтальмологическое отделение"/>
            <msh:checkBox property="isReportKMP" label="Учитывать в отчёте по экспертизе КР"/>
        </msh:row>
        <msh:row>
            <msh:checkBox property="isMobilePolyclinic" label="Мобильная поликлиника"/>
        	<msh:checkBox property="isArchive" label="Не используется (в архиве)"/>
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
        <msh:submitCancelButtonsRow colSpan="2" guid="fe0172d0-16e6-490d-9bf2-ab6ac96e7402" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="mis_lpuForm" guid="3ed53692-c652-4089-a7c6-b50f0867b3e9">
    <table width="100%">
    	<tr>
    		<td width="15%" valign="top" style="padding-right: 1em">
			      <msh:section title="Список подразделений" guid="c061fcb6-70a4-4739-b110-0a6edad9e250">
			        <ecom:webQuery name="subdivisions" nativeSql="select ml.id,ml.name as mlname
			         ,vlf.name as vlfname 
			        from mislpu ml
			        left join VocLpuFunction vlf on vlf.id=ml.lpuFunction_id
			        where ml.parent_id=${param.id} order by ml.name"/>
			        <msh:table hideTitle="true" name="subdivisions" action="entityParentView-mis_lpu.do" idField="1" guid="a8d00e09-8ef8-420d-856a-1e3b25ab6cdf">
			          <msh:tableColumn columnName="" property="1" guid="6c739d0e-36d8-4c34-b42e-11fa745cd2b7" />
			          <msh:tableColumn columnName="" property="2" guid="6c739d0e-36d8-4c34-b42e-11fa745cd2b7" />
			          <msh:tableColumn columnName="" property="3" guid="6c739d0e-36d8-4c34-b42e-11fa745cd2b7" />
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
			      <msh:section guid="c061fcb6-70a4-4739-b110-0a6edad9e250">
			      <msh:sectionTitle>Коечный фонд 
			      <msh:link action="/entityParentPrepareCreate-mis_bedFund.do?id=${param.id}" roles="/Policy/Mis/BedFund/Create">Добавить</msh:link> 
			      <msh:link action="/entityParentList-mis_bedFund.do?id=${param.id}" roles="/Policy/Mis/BedFund/View">К списку</msh:link> 
			      </msh:sectionTitle>
			      <msh:sectionContent>
			        <ecom:parentEntityListAll formName="mis_bedFundForm" attribute="bedFund" guid="637be19d-ec64-498a-876a-7afcbc882af0" />
			        <msh:table hideTitle="false" name="bedFund" action="entityParentView-mis_bedFund.do" idField="id" guid="a8d00e09-8ef8-420d-856a-1e3b25ab6cdf">
			          <msh:tableColumn columnName="Профиль" property="bedTypeName" guid="6c739d0e-36d8-4c34-b42e-11fa745cd2b7" />
			          <msh:tableColumn columnName="Тип " property="bedSubTypeName" guid="6c739d0e-36d8-4c34-b42e-11fa745cd2b7" />
			          <msh:tableColumn columnName="Поток обсл. " property="serviceStreamName" guid="6c739d0e-36d8-4c34-b42e-11fa745cd2b7" />
			          <msh:tableColumn columnName="Для детей" property="forChild" guid="6c739d0e-36d8-4c34-b42e-11fa745cd2b7" />
			          <msh:tableColumn columnName="Реабил." property="isRehab" guid="6c739d0e-36d8-4c34-b42e-11fa745cd2b7" />
			          <msh:tableColumn columnName="Питание не вкл." property="noFood" guid="6c739d0e-36d8-4c34-b42e-11fa745cd2b7" />
			          <msh:tableColumn columnName=" " property="reductionTypeName" guid="6c739d0e-36d8-4c34-b42e-11fa745cd2b7" />
			          <msh:tableColumn columnName="Дата нач." property="dateStart" guid="6c739d0e-36d8-4c34-b42e-11fa745cd2b7" />
			          <msh:tableColumn columnName="Дата окон." property="dateFinish" guid="6c739d0e-36d8-4c34-b42e-11fa745cd2b7" />
			          <msh:tableColumn columnName="Кол-во" property="amount" guid="6c739d0e-36d8-4c34-b42e-11fa745cd2b7" />
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
            <msh:ifInRole roles="/Policy/Mis/LpuArea/View" guid="f5cfaca8-9a26-4a1e-8ca9-819aeaa15ca3">
              <msh:section title="Список участков ЛПУ" guid="8250a7a7-0eda-45ce-86d1-16e43572a813">
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
                <msh:table name="areaList" action="entityParentView-mis_lpuArea.do" idField="1" guid="0c4751af-fab8-4406-9aaa-41551c505a4a">
                  <msh:tableColumn columnName="Номер участка" property="2" guid="26f55aee-878d-4e4f-acc2-58cd16049353" />
                  <msh:tableColumn columnName="Тип" property="3" guid="d6ed2f51-5ddf-4ea9-88f1-060418754d12" />
                  <msh:tableColumn columnName="Участковый" property="4" guid="d6ed2f51-5ddf-4ea9-88f1-060418754d12" />
                </msh:table>
              </msh:section>
              <msh:section title="Список участков подразделений ЛПУ" guid="8250a7a7-0eda-45ce-86d1-16e43572a813">
              <ecom:webQuery name="areaList" nativeSql="select la.id  
              	,la.number as number
				,vat.name as vatName
				,lpu.name as lpuName
				from mislpu lpu
				left join lpuarea la on la.lpu_id=lpu.id
				left join vocareatype vat on vat.id=la.type_id
				where lpu.parent_id=${param.id} and la.id is not null "/>
                <ecom:parentEntityListAll formName="mis_lpuAreaForm" attribute="areas" guid="8deb695e-26d6-41fa-aade-93bbf6e11870" />
                <msh:table name="areaList" action="entityParentView-mis_lpuArea.do" idField="1" guid="0c4751af-fab8-4406-9aaa-41551c505a4a">
                  <msh:tableColumn columnName="Номер участка" property="2" guid="26f55aee-878d-4e4f-acc2-58cd16049353" />
                  <msh:tableColumn columnName="Тип" property="3" guid="d6ed2f51-5ddf-4ea9-88f1-060418754d12" />
                  <msh:tableColumn columnName="Подразделение" property="4" guid="d6ed2f51-5ddf-4ea9-88f1-060418754d12" />
                </msh:table>
              </msh:section>
            </msh:ifInRole>
          </td>
          
          
          
          <td valign="top">
            <!-- Сохранить -->
            <msh:section title="Прикрепленное население" guid="2096d29b-b1b1-435d-858d-19ddfd3d7713">
              <ecom:webQuery name="byLpu" nativeSql="select lpu_id, count(*) from Patient where lpu_id=${param.id} group by lpu_id" guid="e4795ccc-65e9-46c5-aecd-a68309166497" />
              <msh:table name="byLpu" idField="1" action="mis_patients.do?lpu=" guid="e43a5287-998a-4ecc-a9e0-801d431c3cf8">
                <msh:tableColumn property="2" columnName="Количество прикрепленного населения" guid="b504dd75-a636-4223-8d85-2d5934e3a384" />
              </msh:table>
              <h3>По категориям льготников</h3>
              <ecom:webQuery name="byCategory" nativeSql="select lpu_id, VocPrivilegeCategory.id, VocPrivilegeCategory.name, count(*)     from Privilege   inner join Patient on Privilege.person_id=Patient.id    left outer join VocPrivilegeCategory on Privilege.category_id=VocPrivilegeCategory.id   where Patient.lpu_id=${param.id} group by lpu_id, VocPrivilegeCategory.id, VocPrivilegeCategory.name   order by VocPrivilegeCategory.name" guid="0719bd89-f68c-4308-b145-32c1e2537754" />
              <msh:table name="byCategory" idField="1" action="mis_patients.do?lpu=" guid="64fe103e-a067-4f73-8d20-311130e51616">
                <msh:tableColumn property="3" columnName="Категория льготы" guid="be3c45ba-8df0-4294-b3d7-55313ae6d021" />
                <msh:tableColumn property="4" columnName="Количество" guid="39e8a0f5-963a-43c5-9c71-89e319f862fa" />
              </msh:table>
              <h3>По коду льготы</h3>
              <ecom:webQuery name="byCategory" nativeSql="select lpu_id, VocPrivilegeCode.code, VocPrivilegeCode.name, count(*)     from Privilege   inner join Patient on Privilege.person_id=Patient.id    left outer join VocPrivilegeCode on Privilege.privilegeCode_id=VocPrivilegeCode.id   where Patient.lpu_id=${param.id} group by lpu_id, VocPrivilegeCode.code, VocPrivilegeCode.name   order by VocPrivilegeCode.code" guid="9a765ba8-acb4-4556-b369-98a55d081e2e" />
              <msh:table name="byCategory" idField="1" action="mis_patients.do?lpu=" guid="db74d16a-390f-4fbe-bf1c-1f17571a93c3">
                <msh:tableColumn property="2" columnName="Код" guid="e421c750-7b49-4667-bb47-ddc9f64e4d2c" />
                <msh:tableColumn property="3" columnName="Наименование" guid="368720fb-081d-4c7f-af77-7e1dfd3d5dd9" />
                <msh:tableColumn property="4" columnName="Количество" guid="f8d47f7d-6533-4f67-9039-faf3ad90d089" />
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
    <msh:sideMenu title="ЛПУ" guid="a47c6a37-6628-49fe-bd62-c1932970fb4d">
      <msh:sideLink key="ALT+1" params="" action="/entityParentList-mis_lpu.do?id=-1" name="Список ЛПУ" guid="f28bce52-965e-4f65-963e-9dbc6ce5a1ec" />
      <msh:ifFormTypeIsView formName="mis_lpuForm" guid="256e898b-1c16-4351-88de-147fdb7fc60d">
        <msh:sideLink key="ALT+2" roles="/Policy/Mis/MisLpu/Edit" params="id" action="/entityEdit-mis_lpu" name="Изменить" guid="280fe367-a3f0-4d0c-9dd5-10cf9f2174d0" />
      </msh:ifFormTypeIsView>
      <msh:ifFormTypeAreViewOrEdit formName="mis_lpuForm" guid="de889210-1aba-4447-96ab-a729de7a2c8a">
        <msh:sideLink key="ALT+DEL" params="id" roles="/Policy/Mis/MisLpu/Delete" action="/entityParentDeleteGoParentView-mis_lpu" name="Удалить" confirm="Удалить ЛПУ?" guid="c97223fe-5825-4425-81b7-e7120882fbc2" />
      </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>
    <msh:ifFormTypeIsView formName="mis_lpuForm" guid="441c731d-212d-45e8-9964-dde5c8db0a4b">
      <msh:sideMenu title="Добавить" guid="f692ef30-e3cb-4cb7-9f0f-1e0a38e4b08d">
        <msh:sideLink key="ALT+3" params="id" roles="/Policy/Mis/MisLpu/Create" action="/entityParentPrepareCreate-mis_lpu" name="Подразделение" title="Добавить Подразделение" guid="357a6e14-b4af-4108-9aa1-698f07316f11" />
        <msh:sideLink key="ALT+4" params="id" roles="/Policy/Mis/LpuArea/Create" action="/entityParentPrepareCreate-mis_lpuArea" name="Участок" title="Добавить Участок" guid="88e2d2e6-2561-4753-800f-5b6a46dfef72" />
        <msh:sideLink roles="/Policy/Mis/Worker/WorkCalendar/Pattern/Specialist/Create" key="ALT+4" params="id" action="/entityParentPrepareCreate-cal_patternBySpec" name="Шаблон календаря" title="Добавить шаблон календаря" guid="2f18fed4-7259-479a-97df-ff073fc4569d" />
        <msh:sideLink roles="/Policy/Mis/Worker/WorkCalendar/Pattern/Day/Create" key="ALT+5" params="id" action="/entityParentPrepareCreate-cal_dayPattern" name="Шаблон дня календаря" title="Добавить шаблон дня календаря" guid="2f18fed4-7259-479a-97df-ff073fc4569d" />
      </msh:sideMenu>
      <msh:sideMenu title="Показать" guid="d07f7449-b875-45c6-8007-0867170d328d">
      <msh:sideLink roles="/Policy/Mis/Patient/ViewInfoArea" action="/js-mis_lpuArea-print_lpu_by_address.do?id=${param.id}" name="Печать по адресам пациентов для выборов"/>
        <msh:sideLink params="id" roles="/Policy/Mis/MedService/View" action="/js-mis_lpu-journal_medservice" title="Список услуг по отделению" name="Услуги по отделению" />
        <msh:sideLink key="ALT+5" params="id" roles="/Policy/Mis/Licence/View" action="/entityParentList-mis_licence" title="Список лицензий" name="Лицензии" guid="c454da7d-9684-4d70-ad84-d830308e5bd4" />
        <msh:sideLink key="ALT+6" params="id" action="/js-mis_worker-all" title="Список сотрудников" name="Сотрудников" guid="92fa6023-206c-40c6-974f-155ccc7689e1" roles="/Policy/Mis/Worker/Worker/View" />
        <msh:sideLink roles="/Policy/Mis/Diet/View" params="id" action="/entityParentList-diet_diet" name="Диеты" title="Показать диеты" guid="163ca91b-6130-4bf4-b6e6-f4266a33c60f" />
        <msh:sideLink key="ALT+7" params="id" action="/entityParentList-work_staff" name="Штатное расписание" title="Штатное расписание" guid="7cef38f4-b980-4a43-b9f3-245e46c0f86b" roles="/Policy/Mis/Worker/Staff/View" />
        <msh:sideLink key="ALT+8" params="id" action="/entityParentList-mis_medicalEquipment" title="Список мед. оборудования" name="Медицинское оборудование" guid="4d5beb13-c643-4b90-adce-26c88b21a56f" roles="/Policy/Mis/Equipment/Equipment/View" />
        <msh:sideLink key="ALT+9" params="id" action="/entityParentList-work_groupWorkFunction" title="Список групповых рабочих функций" name="Групповые рабочие функции" guid="07b9e245-deed-4b36-94a7-187a623533d0" roles="/Policy/Mis/Worker/WorkFunction/View" />
        <msh:sideLink params="id" action="/entityParentList-mis_bedFund" title="Коечный фонд" name="Коечный фонд" guid="0745-deed-4b36-94a7-187a3d0" roles="/Policy/Mis/BedFund/View" />
        <msh:sideLink params="id" action="/entityParentList-mis_mortalityReportDate" name="Сведения о смертности" title="Показать сведения о смертности" guid="2db116bf-5d40-4eea-913e-6787399ecc73" roles="/Policy/Mis/Report/Mortality/View" />
        <msh:sideLink roles="/Policy/Mis/Report/Birth/View" params="id" action="/entityParentList-mis_birthReportDate" name="Сведения по рождаемости" title="Показать сведения по рождаемости" guid="27fe8bc3-ae8d-4e8b-88f2-d23a337f614b" />
        <msh:sideLink roles="/Policy/Mis/Worker/WorkFunction/View" key="ALT+7" params="id" action="/js-mis_worker-pattern" name="Шаблоны расписания сотрудников" title="Перейти к установке шаблонов календарей по специалистам" />
        <msh:sideLink roles="/Policy/Mis/Equipment/Equipment/View" params="id" action="/js-mis_lpu-showStandard" name="Оснащение по стандарту" title="Шаблоны по стандарту" />
        <msh:sideLink roles="/Policy/Mis/Equipment/KkmEquipment/View" params="id" action="/entityParentList-mis_kkmequipment" name="Кассовые аппараты" title="Кассовые аппараты" />
        <msh:sideLink roles="/Policy/Mis/ColorIdentityEdit" params="id" action="/entityParentList-mis_colorIdentity" name="Цвета браслетов" title="Цвета браслетов" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Lpu" beginForm="mis_lpuForm" guid="66679190-c7ff-4509-9a19-bd9a724138ac" />
  </tiles:put>
  <tiles:put name="javascript" type="string">
    <script type="text/javascript">
      <msh:ifFormTypeIsView formName="mis_lpuForm" guid="370d9f1c-106f-4a91-910a-4b9fb359963b">$('buttonShowAddress').style.display = 'none';</msh:ifFormTypeIsView>
      $('addressFlatNumber1').style.display = 'none';
        $('addressFlatNumber1Label').style.display = 'none';
      $('addressZipcode').style.display = 'none';
        $('addressZipcodeLabel').style.display = 'none';        
      <msh:ifFormTypeIsNotView formName="mis_lpuForm" guid="9e7faddc-b501-419e-bcbb-73dcfa7e8477">function onOmcCodeNameBlur() {
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

