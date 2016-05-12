<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form action="/entityParentSaveGoView-cal_dayPattern.do" defaultField="name" >
			<msh:hidden property="id" />
			<msh:hidden property="saveType" />
			<msh:hidden property="lpu" />
			<msh:panel>

				<msh:row>
					<msh:autoComplete property="workBusy" label="Тип занятости" vocName="vocWorkBusy" horizontalFill="true"  fieldColSpan="3"/>
				</msh:row>
				
				<msh:ifFormTypeIsCreate formName="cal_dayPatternForm">
					<msh:row>
						<msh:textField property="timeIntervalForm.timeFrom" label="Начиная с времени"/>
						<msh:textField property="timeIntervalForm.timeTo" label="Заканчивая временем"/>
					</msh:row>
					<msh:row>
						<msh:textField property="timeIntervalForm.visitTime" label="Ср. время (мин)"/>
						<msh:textField property="timeIntervalForm.countVisits" label="Кол-во визитов"/>
					</msh:row>
					<msh:row>
						<td colspan="5">указывается либо среднее время, либо кол-во </td>
					</msh:row>
				</msh:ifFormTypeIsCreate>
				<msh:row>
					<msh:textField property="name" label="Название" horizontalFill="true" fieldColSpan="3" size="100"/>
				</msh:row>
				<msh:row>
					<msh:textArea property="timeIntervalForm.listTimes" fieldColSpan="4" hideLabel="true"
						horizontalFill="true" rows="4" />
				</msh:row>
				<msh:submitCancelButtonsRow colSpan="2" />
			</msh:panel>
		</msh:form>
		<msh:ifFormTypeIsView formName="cal_dayPatternForm">
			<msh:section title="Шаблоны интервалов времен">
			<ecom:webQuery name="timePatterns" nativeSql="
			select wctp.id,vwb.name as vwbname, wctp.timeFrom,wctp.timeTo,wctp.visitTime,wctp.countVisits
			from WorkCalendarTimePattern wctp
			left join VocWorkBusy vwb on vwb.id=wctp.workBusy_id
			where wctp.dayPattern_id = ${param.id} and wctp.dtype='WorkCalendarTimeInterval'
			" />
				<msh:table  name="timePatterns" action="entitySubclassView-cal_timePattern.do" idField="1">
					<msh:tableColumn columnName="##" property="sn"/>
					<msh:tableColumn columnName="Занятость" property="2"/>
					<msh:tableColumn columnName="Интервал с" property="3"/>
					<msh:tableColumn columnName="по" property="4"/>
					<msh:tableColumn columnName="Время визита" property="5"/>
					<msh:tableColumn columnName="Кол-во визитов" property="6"/>
				</msh:table>
			</msh:section>
			<msh:section title="Шаблоны сгенерированных времен">
			<ecom:webQuery name="timePatterns" nativeSql="
			select wctp.id,vwb.name as vwbname, wctp.calendarTime,vsrt.name as vsrtname
			from WorkCalendarTimePattern wctp
			left join VocWorkBusy vwb on vwb.id=wctp.workBusy_id
			left join VocServiceReserveType vsrt on vsrt.id=wctp.reserveType_id
			where wctp.dayPattern_id = ${param.id} and wctp.dtype='WorkCalendarTimeExample'
			order by wctp.calendarTime
			" />
				<msh:table deleteUrl="entityParentDeleteGoSubclassView-cal_timeExample.do" editUrl="entityParentEdit-cal_timeExample.do" name="timePatterns" action="entitySubclassEdit-cal_timePattern.do" idField="1">
					<msh:tableColumn columnName="##" property="sn"/>
					<msh:tableColumn columnName="Время приема" property="3"/>
					<msh:tableColumn columnName="Резерв" property="4"/>
				</msh:table>
			</msh:section>
			<msh:section title="В каких шаблонах используется">
            <ecom:webQuery name="patterns" nativeSql="
            select wcp.id,wcp.name
from workcalendarpattern wcp
left join WorkCalendarAlgorithm wca on wca.pattern_id=wcp.id

where wca.monday_id=${param.id} or wca.tuesday_id=${param.id} or wca.wednesday_id=${param.id} or wca.thursday_id=${param.id} or wca.friday_id=${param.id}
 or wca.saturday_id=${param.id} or wca.sunday_id=${param.id} or wca.dayPattern_id=${param.id}            " />
                <msh:table deleteUrl="entityParentDeleteGoSubclassView-cal_pattern.do" editUrl="entitySubclassEdit-cal_pattern.do" name="patterns"
action="entitySubclassView-cal_pattern.do" idField="1">
                    <msh:tableColumn columnName="#" property="sn"/>
                    <msh:tableColumn columnName="Наименование шаблона" property="2"/>
                </msh:table>
            </msh:section>
			
		</msh:ifFormTypeIsView>
	</tiles:put>
	<tiles:put name="javascript" type="string">
		<msh:ifFormTypeIsCreate formName="cal_dayPatternForm">
			<script type="text/javascript">
			function updateName(){
				
				var add =' ' ;
				if (+$('timeIntervalForm.visitTime').value>0) {
					add = add+"("+$('timeIntervalForm.visitTime').value+" мин.)" ;
				} else if (+$('timeIntervalForm.countVisits').value>0) {
					add = add+"("+$('timeIntervalForm.countVisits').value+" виз.)" ;
				}
				$('name').value=$('timeIntervalForm.timeFrom').value +'-'+$('timeIntervalForm.timeTo').value+add ;
				getListTimes() ;
			}
			function getListTimes() {
				$('timeIntervalForm.listTimes').name = $('timeIntervalForm.timeFrom').value +', '+$('timeIntervalForm.timeTo').value ;
			}
			eventutil.addEventListener($('timeIntervalForm.visitTime'),'blur',function(){updateName();});
			eventutil.addEventListener($('timeIntervalForm.countVisits'),'blur',function(){updateName();});
			eventutil.addEventListener($('timeIntervalForm.timeFrom'),'blur',function(){updateName();});
			eventutil.addEventListener($('timeIntervalForm.timeTo'),'blur',function(){updateName();});
			</script>
		</msh:ifFormTypeIsCreate>
		<script type="text/javascript">
		function goNewDay() {
			window.location.href = "entityParentPrepareCreate-cal_dayPattern.do?id="+$('lpu').value  ;
		}
		function goNewPattern() {
			window.location.href = "entityParentPrepareCreate-cal_patternBySpec.do?id="+$('lpu').value  ;
		}
		function goViewFunctions() {
			window.location.href = "js-mis_worker-pattern.do?id="+$('lpu').value  ;
		}
		</script>
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Patient" beginForm="cal_dayPatternForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:ifFormTypeAreViewOrEdit formName="cal_dayPatternForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-cal_dayPattern" name="Изменить" title="Изменить" roles="/Policy/Mis/Worker/WorkCalendar/Pattern/Day/Edit"/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDelete-cal_dayPattern" name="Удалить" title="Удалить" roles="/Policy/Mis/Worker/WorkCalendar/Pattern/Day/Delete"/>
			</msh:sideMenu>
			<msh:sideMenu title="Добавить" >
				<msh:sideLink key="ALT+3" params="id" action="/entityParentPrepareCreate-cal_timeInterval" name="Интервал времен" title="Добавить шаблон интервалов времен" roles="/Policy/Mis/Worker/WorkCalendar/Pattern/Day/Time/Create"/>
				<msh:sideLink roles="/Policy/Mis/Worker/WorkCalendar/Pattern/Day/Create" 
    	name="Шаблон дня по ЛПУ" 
    	action='.javascript:goNewDay(".do")' title='Добавить шаблон дня'
    	/>
				<msh:sideLink roles="/Policy/Mis/Worker/WorkCalendar/Pattern/Specialist/Create" 
    	name="Шаблон календаря по ЛПУ" 
    	action='.javascript:goNewPattern(".do")' title='Добавить шаблон календаря по ЛПУ'
    	/>
			</msh:sideMenu>
			<msh:sideMenu title="Показать">
       <msh:sideLink roles="/Policy/Mis/Worker/WorkFunction/View" key="ALT+7" params="id" action="/javascript:goViewFunctions('.do')" name="Шаблоны расписания сотрудников" title="Перейти к установке шаблонов календарей по специалистам" />				
			</msh:sideMenu>
		</msh:ifFormTypeAreViewOrEdit>
	</tiles:put>
</tiles:insert>
