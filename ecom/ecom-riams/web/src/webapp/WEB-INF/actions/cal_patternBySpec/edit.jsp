<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Шаблон календаря
    	  -->
    <msh:form action="/entityParentSaveGoView-cal_patternBySpec.do" defaultField="name">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="lpu"/>
			<msh:panel>
				<msh:row>
					<msh:textField property="name" label="Название" size="100" fieldColSpan="5" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete property="workFunction" vocName="workFunctionByDirect" size="100" fieldColSpan="5" horizontalFill="true"/>
				</msh:row>
				<msh:row>
					<msh:autoComplete size="100" property="calendarType" label="Тип календаря" vocName="vocWorkCalendarType" horizontalFill="true"  fieldColSpan="5"/>
				</msh:row>
				<msh:ifFormTypeIsCreate formName="cal_patternBySpecForm">
					<msh:row>
						
								<th colspan="10" align="center">АЛГОРИТМЫ КАЛЕНДАРЯ:</th>
						
					</msh:row>
					<msh:row>
						<msh:checkBox property="isProfday" label="профдень"/>
						<msh:checkBox property="isDays" label="за определенный период"/>
					</msh:row>
					<msh:row>
						<msh:checkBox property="isWeekDays" label="по дням недели"/>
						<msh:checkBox property="isWeek" label="рабочая неделя"/>
					</msh:row>
					<msh:row styleId="isProfdayRow1">
						<msh:separator label="Проф.день" colSpan="6"/>
					</msh:row>
					<msh:row styleId="isProfdayRow2"> 
				 	<th class="label" title="Тип профдня (typeWeekAlgorithm)" colspan="1" align="right"><label for="typeWeekAlgorithmName" id="typeDateLabel">Тип профдня:</label></th>
					<td align="left" colspan="5">
						<table>
						 <tr>
					        <td class='tdradio' onclick="this.childNodes[1].checked='checked';viewProf()" colspan="2">
					        	<input type="radio" name="typeProfAlgorithm" value="1"> в зависимости от №недели в месяце  
					        </td>
					        <td class='tdradio' onclick="this.childNodes[1].checked='checked';viewProf()" colspan="3">
					        	<input type="radio" name="typeProfAlgorithm" value="2">  в зависимости от числа
					        </td>
					        
					        
						 </tr>
						</table>
					</td>
					</msh:row>
					<msh:row styleId="isProfdayRow3">
						<msh:autoComplete size="40" property="prophDayAlgorithmForm.monthOrder" label="№недели" vocName="vocWeekMonthOrder" horizontalFill="true" fieldColSpan="5"/>
					</msh:row>
					<msh:row styleId="isProfdayRow4">
						<msh:autoComplete viewAction="entityParentView-cal_dayPattern.do" shortViewAction="entityShortView-cal_dayPattern.do" size="40" property="prophDayAlgorithmForm.weekDay" label="День недели" vocName="vocWeekDay" horizontalFill="true"  fieldColSpan="5"/>
					</msh:row>
					<msh:row styleId="isProfdayRow5">
						<msh:textField property="prophDayAlgorithmForm.monthDay" label="Число"/>
					</msh:row>
				<msh:row styleId="isDaysRow1">
					<msh:separator label="Алгоритм за определенный период" colSpan="6"/>
				</msh:row>
				<msh:row styleId="isDaysRow2">
					<msh:textField property="datesAlgorithmForm.dateFrom" label="Начиная с даты"/>
					<msh:textField property="datesAlgorithmForm.dateTo" label="Заканчивая датой"/>
				</msh:row>
				<msh:row styleId="isDaysRow3">
					<msh:autoComplete
					 viewAction="entityParentView-cal_dayPattern.do" shortViewAction="entityShortView-cal_dayPattern.do" 
					 fieldColSpan="3" parentId="cal_patternBySpecForm.lpu" property="datesAlgorithmForm.dayPattern" label="Шаблон дня" vocName="workCalendarDayPatternByLpu" horizontalFill="true" />
				</msh:row>
				<msh:row styleId="isWeekDaysRow1">
					<msh:separator label="Алгоритм по дням недели" colSpan="6"/>
				</msh:row>
				<msh:row styleId="isWeekDaysRow2">
				 	<td class="label" title="Тип разбивки (typeWeekAlgorithm)" colspan="1"><label for="typeWeekAlgorithmName" id="typeDateLabel">Тип разбивки:</label></td>
					<td align="left" colspan="5">
						<table>
						 <tr>
					        <td class='tdradio' onclick="this.childNodes[1].checked='checked';viewWeekDays()" colspan="2">
					        	<input type="radio" name="typeWeekDaysAlgorithm" value="1"> без разбивки  
					        </td>
					        <td class='tdradio' onclick="this.childNodes[1].checked='checked';viewWeekDays()" colspan="3">
					        	<input type="radio" name="typeWeekDaysAlgorithm" value="2">  по четным/нечетным
					        </td>
					        <td class='tdradio' onclick="this.childNodes[1].checked='checked';viewWeekDays()" colspan="3">
					        	<input type="radio" name="typeWeekDaysAlgorithm" value="3">  по 3
					        </td>
						 </tr>
						</table>
					</td>
				</msh:row>
				<msh:row styleId="isWeekDaysRow3">
				 	<td class="label" title="Разбивать по (typeDayAlgorithm)" colspan="1"><label for="typeWeekAlgorithmName" id="typeDateLabel">Разбивать по:</label></td>
					<td align="left" colspan="5">
						<table>
						 <tr>
					        <td class='tdradio' onclick="this.childNodes[1].checked='checked';viewWeekDays()" colspan="2">
					        	<input type="radio" name="typeByWeekDaysAlgorithm" value="1"> дням  
					        </td>
					        <td class='tdradio' onclick="this.childNodes[1].checked='checked';viewWeekDays()" colspan="3">
					        	<input type="radio" name="typeByWeekDaysAlgorithm" value="2">  неделям
					        </td>
					        <td class='tdradio' onclick="this.childNodes[1].checked='checked';viewWeekDays()" colspan="3">
					        	<input type="radio" name="typeByWeekDaysAlgorithm" value="3">  месяцам
					        </td>
						 </tr>
						</table>
					</td>
				</msh:row>
				
				<msh:row styleId="isWeekDaysRow4">
					<td></td>
					<th class="weekDaysAlgorithm0">1 неделя</th>
					<th class="weekDaysAlgorithm1">2 неделя</th>
					<th class="weekDaysAlgorithm2">3 неделя</th>
				</msh:row>
				<msh:row styleId="isWeekDaysRow5">
					<msh:autoComplete viewAction="entityParentView-cal_dayPattern.do" shortViewAction="entityShortView-cal_dayPattern.do"  size="30" parentId="cal_patternBySpecForm.lpu" property="weekDaysAlgorithmForm0.monday" label="Понедельник" vocName="workCalendarDayPatternByLpu" horizontalFill="true" fieldColSpan="1" />
					<msh:autoComplete viewAction="entityParentView-cal_dayPattern.do" shortViewAction="entityShortView-cal_dayPattern.do" hideLabel="true" size="30" parentId="cal_patternBySpecForm.lpu" property="weekDaysAlgorithmForm1.monday" label="Понедельник" vocName="workCalendarDayPatternByLpu" horizontalFill="true"  fieldColSpan="1" />
					<msh:autoComplete viewAction="entityParentView-cal_dayPattern.do" shortViewAction="entityShortView-cal_dayPattern.do" hideLabel="true"  size="30" parentId="cal_patternBySpecForm.lpu" property="weekDaysAlgorithmForm2.monday" label="Понедельник" vocName="workCalendarDayPatternByLpu" horizontalFill="true" fieldColSpan="1"/>
				</msh:row>
				<msh:row styleId="isWeekDaysRow6">
					<msh:autoComplete viewAction="entityParentView-cal_dayPattern.do" shortViewAction="entityShortView-cal_dayPattern.do" size="30" parentId="cal_patternBySpecForm.lpu" property="weekDaysAlgorithmForm0.tuesday" label="Вторник" vocName="workCalendarDayPatternByLpu" horizontalFill="true" fieldColSpan="1"/>
					<msh:autoComplete viewAction="entityParentView-cal_dayPattern.do" shortViewAction="entityShortView-cal_dayPattern.do" size="30" parentId="cal_patternBySpecForm.lpu" property="weekDaysAlgorithmForm1.tuesday" label="Вторник" vocName="workCalendarDayPatternByLpu" horizontalFill="true" fieldColSpan="1" hideLabel="true"/>
					<msh:autoComplete viewAction="entityParentView-cal_dayPattern.do" shortViewAction="entityShortView-cal_dayPattern.do" size="30" parentId="cal_patternBySpecForm.lpu" property="weekDaysAlgorithmForm2.tuesday" label="Вторник" vocName="workCalendarDayPatternByLpu" horizontalFill="true" fieldColSpan="1" hideLabel="true"/>
				</msh:row>
				<msh:row styleId="isWeekDaysRow7">
					<msh:autoComplete viewAction="entityParentView-cal_dayPattern.do" shortViewAction="entityShortView-cal_dayPattern.do" size="30" parentId="cal_patternBySpecForm.lpu" property="weekDaysAlgorithmForm0.wednesday" label="Среда" vocName="workCalendarDayPatternByLpu" horizontalFill="true" fieldColSpan="1"/>
					<msh:autoComplete viewAction="entityParentView-cal_dayPattern.do" shortViewAction="entityShortView-cal_dayPattern.do" size="30" parentId="cal_patternBySpecForm.lpu" property="weekDaysAlgorithmForm1.wednesday" label="Среда" vocName="workCalendarDayPatternByLpu" horizontalFill="true" fieldColSpan="1" hideLabel="true"/>
					<msh:autoComplete viewAction="entityParentView-cal_dayPattern.do" shortViewAction="entityShortView-cal_dayPattern.do" size="30" parentId="cal_patternBySpecForm.lpu" property="weekDaysAlgorithmForm2.wednesday" label="Среда" vocName="workCalendarDayPatternByLpu" horizontalFill="true" fieldColSpan="1" hideLabel="true"/>
				</msh:row>
				<msh:row styleId="isWeekDaysRow8">
					<msh:autoComplete viewAction="entityParentView-cal_dayPattern.do" shortViewAction="entityShortView-cal_dayPattern.do" size="30" parentId="cal_patternBySpecForm.lpu" property="weekDaysAlgorithmForm0.thursday" label="Четверг" vocName="workCalendarDayPatternByLpu" horizontalFill="true" fieldColSpan="1" />
					<msh:autoComplete viewAction="entityParentView-cal_dayPattern.do" shortViewAction="entityShortView-cal_dayPattern.do" size="30" parentId="cal_patternBySpecForm.lpu" property="weekDaysAlgorithmForm1.thursday" label="Четверг" vocName="workCalendarDayPatternByLpu" horizontalFill="true" fieldColSpan="1"  hideLabel="true"/>
					<msh:autoComplete viewAction="entityParentView-cal_dayPattern.do" shortViewAction="entityShortView-cal_dayPattern.do" size="30" parentId="cal_patternBySpecForm.lpu" property="weekDaysAlgorithmForm2.thursday" label="Четверг" vocName="workCalendarDayPatternByLpu" horizontalFill="true" fieldColSpan="1"  hideLabel="true"/>
				</msh:row>
				<msh:row styleId="isWeekDaysRow9">
					<msh:autoComplete viewAction="entityParentView-cal_dayPattern.do" shortViewAction="entityShortView-cal_dayPattern.do" size="30" parentId="cal_patternBySpecForm.lpu" property="weekDaysAlgorithmForm0.friday" label="Пятница" vocName="workCalendarDayPatternByLpu" horizontalFill="true" fieldColSpan="1"/>
					<msh:autoComplete viewAction="entityParentView-cal_dayPattern.do" shortViewAction="entityShortView-cal_dayPattern.do" size="30" parentId="cal_patternBySpecForm.lpu" property="weekDaysAlgorithmForm1.friday" label="Пятница" vocName="workCalendarDayPatternByLpu" horizontalFill="true" fieldColSpan="1" hideLabel="true"/>
					<msh:autoComplete viewAction="entityParentView-cal_dayPattern.do" shortViewAction="entityShortView-cal_dayPattern.do" size="30" parentId="cal_patternBySpecForm.lpu" property="weekDaysAlgorithmForm2.friday" label="Пятница" vocName="workCalendarDayPatternByLpu" horizontalFill="true" fieldColSpan="1" hideLabel="true"/>
				</msh:row>
				<msh:row styleId="isWeekDaysRow10">
					<msh:autoComplete viewAction="entityParentView-cal_dayPattern.do" shortViewAction="entityShortView-cal_dayPattern.do" size="30" parentId="cal_patternBySpecForm.lpu" property="weekDaysAlgorithmForm0.saturday" label="Суббота" vocName="workCalendarDayPatternByLpu" horizontalFill="true" fieldColSpan="1"/>
					<msh:autoComplete viewAction="entityParentView-cal_dayPattern.do" shortViewAction="entityShortView-cal_dayPattern.do" size="30" parentId="cal_patternBySpecForm.lpu" property="weekDaysAlgorithmForm1.saturday" label="Суббота" vocName="workCalendarDayPatternByLpu" horizontalFill="true" fieldColSpan="1" hideLabel="true"/>
					<msh:autoComplete viewAction="entityParentView-cal_dayPattern.do" shortViewAction="entityShortView-cal_dayPattern.do" size="30" parentId="cal_patternBySpecForm.lpu" property="weekDaysAlgorithmForm2.saturday" label="Суббота" vocName="workCalendarDayPatternByLpu" horizontalFill="true" fieldColSpan="1" hideLabel="true"/>
				</msh:row>
				<msh:row styleId="isWeekDaysRow11">
					<msh:autoComplete viewAction="entityParentView-cal_dayPattern.do" shortViewAction="entityShortView-cal_dayPattern.do" size="30" parentId="cal_patternBySpecForm.lpu" property="weekDaysAlgorithmForm0.sunday" label="Воскресенье" vocName="workCalendarDayPatternByLpu" horizontalFill="true" fieldColSpan="1"/>
					<msh:autoComplete viewAction="entityParentView-cal_dayPattern.do" shortViewAction="entityShortView-cal_dayPattern.do" size="30" parentId="cal_patternBySpecForm.lpu" property="weekDaysAlgorithmForm1.sunday" label="Воскресенье" vocName="workCalendarDayPatternByLpu" horizontalFill="true" fieldColSpan="1" hideLabel="true"/>
					<msh:autoComplete viewAction="entityParentView-cal_dayPattern.do" shortViewAction="entityShortView-cal_dayPattern.do" size="30" parentId="cal_patternBySpecForm.lpu" property="weekDaysAlgorithmForm2.sunday" label="Воскресенье" vocName="workCalendarDayPatternByLpu" horizontalFill="true" fieldColSpan="1" hideLabel="true"/>
				</msh:row>
				
				<msh:row styleId="isWeekRow1">
					<msh:separator label="Алгоритм раб.недели" colSpan="6"/>
				</msh:row>
				
				<msh:row styleId="isWeekRow2">
				 	<td class="label" title="Тип разбивки (typeWeekAlgorithm)" colspan="1"><label for="typeWeekAlgorithmName" id="typeDateLabel">Тип разбивки:</label></td>
					<td align="left" colspan="5">
						<table>
						 <tr>
					        <td class='tdradio' onclick="this.childNodes[1].checked='checked';viewWeek();" colspan="2">
					        	<input type="radio" name="typeWeekAlgorithm" value="1"> без разбивки  
					        </td>
					        <td class='tdradio' onclick="this.childNodes[1].checked='checked';viewWeek();" colspan="3">
					        	<input type="radio" name="typeWeekAlgorithm" value="2">  по четным/нечетным
					        </td>
					        <td class='tdradio' onclick="this.childNodes[1].checked='checked';viewWeek();" colspan="3">
					        	<input type="radio" name="typeWeekAlgorithm" value="3">  по 3
					        </td>
						 </tr>
						</table>
					</td>
				</msh:row>
				<msh:row styleId="isWeekRow3">
				 	<td class="label" title="Разбивать по (typeDayAlgorithm)" colspan="1"><label for="typeWeekAlgorithmName" id="typeDateLabel">Разбивать по:</label></td>
					<td align="left" colspan="5">
						<table>
						 <tr>
					        <td class='tdradio' onclick="this.childNodes[1].checked='checked';viewWeek()" colspan="2">
					        	<input type="radio" name="typeByWeekAlgorithm" value="1"> дням  
					        </td>
					        <td class='tdradio' onclick="this.childNodes[1].checked='checked';viewWeek()" colspan="3">
					        	<input type="radio" name="typeByWeekAlgorithm" value="2">  неделям
					        </td>
					        <td class='tdradio' onclick="this.childNodes[1].checked='checked';viewWeek()" colspan="3">
					        	<input type="radio" name="typeByWeekAlgorithm" value="3">  месяцам
					        </td>
						 </tr>
						</table>
					</td>
				</msh:row>
				<msh:row styleId="isWeekRow4">
					<td></td>
					<th class="weekAlgorithm0">1 неделя</th>
					<th class="weekAlgorithm1">2 неделя</th>
					<th class="weekAlgorithm2">3 неделя</th>
				</msh:row>
				<msh:row styleId="isWeekRow5">
					<msh:autoComplete property="weekAlgorithmForm0.workWeek" label="Рабочая недели" vocName="vocWorkWeek" horizontalFill="true"/>
					<msh:autoComplete property="weekAlgorithmForm1.workWeek" label="Рабочая недели" vocName="vocWorkWeek" horizontalFill="true" hideLabel="true"/>
					<msh:autoComplete property="weekAlgorithmForm2.workWeek" label="Рабочая недели" vocName="vocWorkWeek" horizontalFill="true" hideLabel="true"/>
				</msh:row>
				<msh:row styleId="isWeekRow6">
					<msh:autoComplete viewAction="entityParentView-cal_dayPattern.do" shortViewAction="entityShortView-cal_dayPattern.do" size="30" parentId="cal_patternBySpecForm.lpu" property="weekAlgorithmForm0.dayPattern" label="Шаблон дня" vocName="workCalendarDayPatternByLpu" horizontalFill="true"/>
					<msh:autoComplete viewAction="entityParentView-cal_dayPattern.do" shortViewAction="entityShortView-cal_dayPattern.do" size="30" parentId="cal_patternBySpecForm.lpu" property="weekAlgorithmForm1.dayPattern" label="Шаблон дня" vocName="workCalendarDayPatternByLpu" horizontalFill="true" hideLabel="true"/>
					<msh:autoComplete viewAction="entityParentView-cal_dayPattern.do" shortViewAction="entityShortView-cal_dayPattern.do" size="30" parentId="cal_patternBySpecForm.lpu" property="weekAlgorithmForm2.dayPattern" label="Шаблон дня" vocName="workCalendarDayPatternByLpu" horizontalFill="true" hideLabel="true"/>
				<msh:hidden property="weekAlgorithmForm0.calendarParity"></msh:hidden>
				<msh:hidden property="weekDaysAlgorithmForm0.calendarParity"></msh:hidden>
				<msh:hidden property="weekAlgorithmForm0.parity"></msh:hidden>
				<msh:hidden property="weekDaysAlgorithmForm0.parity"></msh:hidden>
				<msh:hidden property="profType"></msh:hidden>
				</msh:row>
				
				
				</msh:ifFormTypeIsCreate>
			<msh:submitCancelButtonsRow colSpan="2" />
			</msh:panel>
    	</msh:form>
		<msh:ifFormTypeIsView formName="cal_patternBySpecForm">
		<table>
		<tr><td valign="top"  style="padding-right: 1em">
			<msh:section title="Алгоритм по проф.дню" createRoles="/Policy/Mis/Worker/WorkCalendar/Pattern/Algorithm/Create" viewRoles="/Policy/Mis/Worker/WorkCalendar/Pattern/Algorithm/View"
				createUrl="entityParentPrepareCreate-cal_prophDayAlgorithm.do?id=${param.id}" listUrl="entityParentList-cal_prophDayAlgorithm.do?id=${param.id}">
				<msh:sectionContent>
					<ecom:webQuery  name="algProf" nativeSql="
					 select wca.id,wca.monthDay,vwd.name as vwdname,vwmo.name as vwmoname from WorkCalendarAlgorithm wca
					 left join VocWeekMonthOrder vwmo on vwmo.id=wca.monthOrder_id
					 left join VocWeekDay vwd on vwd.id=wca.weekDay_id
					where wca.dtype='WorkCalendarProphDayAlgorithm' and wca.pattern_id='${param.id}'
					" />
					<msh:table  name="algProf"
						editUrl="entityParentEdit-cal_prophDayAlgorithm.do" 
						viewUrl="entityShortView-cal_prophDayAlgorithm.do"
						deleteUrl="entityParentDeleteGoSubclassView-cal_prophDayAlgorithm.do" 
						 action="entitySubclassView-cal_algorithm.do" idField="1">
						<msh:tableColumn property="sn" columnName="#"/>
						<msh:tableColumn property="2" columnName="День месяца"/>
						<msh:tableColumn property="3" columnName="День недели"/>
						<msh:tableColumn property="4" columnName="Порядок недели в месяце"/>
					</msh:table>
				</msh:sectionContent>
			</msh:section>
			</td><td valign="top">
			<msh:section title="Алгоритм по датам"  createRoles="/Policy/Mis/Worker/WorkCalendar/Pattern/Algorithm/Create" viewRoles="/Policy/Mis/Worker/WorkCalendar/Pattern/Algorithm/View"
				createUrl="entityParentPrepareCreate-cal_datesAlgorithm.do?id=${param.id}" listUrl="entityParentList-cal_datesAlgorithm.do?id=${param.id}">
				<msh:sectionContent>
					<ecom:webQuery name="algDates" nativeSql="
					 select wca.id,wca.dateFrom,wca.dateTo,wcdp.name
					 from WorkCalendarAlgorithm wca 
					 left join workCalendarDayPattern wcdp on wcdp.id=wca.dayPattern_id
					 where wca.dtype='WorkCalendarDatesAlgorithm' and pattern_id='${param.id}'
					"/>
					<msh:table 
						editUrl="entityParentEdit-cal_datesAlgorithm.do" 
						viewUrl="entityShortView-cal_datesAlgorithm.do"
						deleteUrl="entityParentDeleteGoSubclassView-cal_datesAlgorithm.do" 
						name="algDates" action="entitySubclassView-cal_algorithm.do" idField="1">
						<msh:tableColumn property="sn" columnName="#"/>
						<msh:tableColumn property="2" columnName="С какого"/>
						<msh:tableColumn property="3" columnName="По какой день"/>
						<msh:tableColumn property="4" columnName="Шаблон дня"/>
					</msh:table>
				</msh:sectionContent>
			</msh:section>
			</td></tr><tr><td valign="top"  style="padding-right: 1em">
			<msh:section title="Алгоритм по дням недели"  createRoles="/Policy/Mis/Worker/WorkCalendar/Pattern/Algorithm/Create" viewRoles="/Policy/Mis/Worker/WorkCalendar/Pattern/Algorithm/View"
				createUrl="entityParentPrepareCreate-cal_weekDaysAlgorithm.do?id=${param.id}" listUrl="entityParentPrepareCreate-cal_weekDaysAlgorithm.do?id=${param.id}"
			>
				<msh:sectionContent>
					<ecom:webQuery name="algWeekDays" nativeSql="
					 select wca.id,vwcp.name as vwcpname,vdp.name as vspname
					 ,vwd1.name as vwd1name
					 ,vwd2.name as vwd2name,vwd3.name as vwd3name,vwd4.name as vwd4name
					 ,vwd5.name as vwd5name,vwd6.name as vwd6name,vwd7.name as vwd7name
					 
					 from WorkCalendarAlgorithm wca 
					 left join VocWorkCalendarParity vwcp on vwcp.id=wca.calendarParity_id
					 left join VocDayParity vdp on vdp.id=wca.parity_id
					 
					 left join workCalendarDayPattern vwd1 on vwd1.id=wca.monday_id
					 left join workCalendarDayPattern vwd2 on vwd2.id=wca.tuesday_id
					 left join workCalendarDayPattern vwd3 on vwd3.id=wca.wednesday_id
					 left join workCalendarDayPattern vwd4 on vwd4.id=wca.thursday_id
					 left join workCalendarDayPattern vwd5 on vwd5.id=wca.friday_id
					 left join workCalendarDayPattern vwd6 on vwd6.id=wca.saturday_id
					 left join workCalendarDayPattern vwd7 on vwd7.id=wca.sunday_id
					 
					 where wca.dtype='WorkCalendarWeekDaysAlgorithm' and wca.pattern_id='${param.id}'
					"/>
					<msh:table name="algWeekDays" 
						editUrl="entityParentEdit-cal_weekDaysAlgorithm.do" 
						viewUrl="entityShortView-cal_weekDaysAlgorithm.do"
						deleteUrl="entityParentDeleteGoSubclassView-cal_weekDaysAlgorithm.do" 
						action="entitySubclassView-cal_algorithm.do" idField="1">
						<msh:tableColumn property="sn" columnName="#"/>
						<msh:tableColumn property="2" columnName="Тип четности"/>
						<msh:tableColumn property="3" columnName="Четность"/>
						<msh:tableColumn property="4" columnName="Понедельник"/>
						<msh:tableColumn property="5" columnName="Вторник"/>
						<msh:tableColumn property="6" columnName="Среда"/>
						<msh:tableColumn property="7" columnName="Четверг"/>
						<msh:tableColumn property="8" columnName="Пятница"/>
						<msh:tableColumn property="9" columnName="Суббота"/>
						<msh:tableColumn property="10" columnName="Воскресение"/>
					</msh:table>
				</msh:sectionContent>
				
			</msh:section>
			</td><td valign="top">
			<msh:section title="Алгоритм по неделям"  createRoles="/Policy/Mis/Worker/WorkCalendar/Pattern/Algorithm/Create" viewRoles="/Policy/Mis/Worker/WorkCalendar/Pattern/Algorithm/View"
				createUrl="entityParentPrepareCreate-cal_weekAlgorithm.do?id=${param.id}" listUrl="entityParentList-cal_weekAlgorithm.do?id=${param.id}"
			>
				<msh:sectionContent>
					<ecom:webQuery name="algWeeks" nativeSql="
					select wca.id,vww.name,wcdp.name as wcdpname
					,vwcp.name as vwcpname,vdp.name as vdpname
					from WorkCalendarAlgorithm wca 
					left join VocWorkWeek vww on vww.id=wca.workWeek_id
					left join VocWorkCalendarParity vwcp on vwcp.id=wca.calendarParity_id
					left join VocDayParity vdp on vdp.id=wca.parity_id
					left join workCalendarDayPattern wcdp on wcdp.id=dayPattern_id
					 where dtype='WorkCalendarWeekAlgorithm' and pattern_id='${param.id}'
					"/>
					<msh:table
						editUrl="entityParentEdit-cal_weekAlgorithm.do" 
						viewUrl="entityShortView-cal_weekAlgorithm.do"
						deleteUrl="entityParentDeleteGoSubclassView-cal_weekAlgorithm.do" 
						 name="algWeeks" action="entitySubclassView-cal_algorithm.do" idField="1">
						<msh:tableColumn property="sn" columnName="#"/>
						<msh:tableColumn property="2" columnName="Рабочиая неделя"/>
						<msh:tableColumn property="3" columnName="Тип четности"/>
						<msh:tableColumn property="4" columnName="Четность"/>
						<msh:tableColumn property="5" columnName="Шаблон дня"/>
					</msh:table>
				</msh:sectionContent>
			</msh:section>
			</td>
			</tr>
			</table>
			
			<msh:section title="Рабочие календари"><msh:sectionContent>
                    <ecom:webQuery name="altes" nativeSql="
                     select jpc.id,jpc.workCalendar_id,vwf.name,coalesce(wp.lastname||' '||wp.firstname||' '||wp.middlename,wf.groupName) as fio
                     from JournalPatternCalendar jpc
                    left join WorkCalendar wc on jpc.workCalendar_id=wc.id
                    left join WorkFunction wf on wf.id=wc.workFunction_id
                    left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
                    left join Worker w on w.id=wf.worker_id
                    left join Patient wp on wp.id=w.person_id
                     where jpc.pattern_id='${param.id}'
                    "/>
                    <msh:table
                        editUrl="entityParentEdit-work_journalPatternCalendar.do"
                        viewUrl="entityShortView-work_journalPatternCalendar.do"
                        deleteUrl="entityParentDeleteGoParentView-work_journalPatternCalendar.do"
                        name="altes" action="entityView-work_journalPatternCalendar.do" idField="1">
                        <msh:tableColumn property="sn" columnName="#"/>
                        <msh:tableColumn property="2" columnName="Календарь"/>
                        <msh:tableColumn property="3" columnName="Раб.функция"/>
                        <msh:tableColumn property="4" columnName="ФИО спец. или наимен. группы"/>
                     </msh:table>
                </msh:sectionContent>
</msh:section>
			
		</msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  		<script type="text/javascript">
		
		function goViewFunctions() {
			window.location.href = "js-mis_worker-pattern.do?id="+$('lpu').value  ;
		}
		</script>
  <msh:ifFormTypeIsCreate formName="cal_patternBySpecForm">
  	<script type="text/javascript">
  	checkFieldUpdate('typeProfAlgorithm','${param.typeProfAlgorithm}',1) ;
  	checkFieldUpdate('typeByWeekAlgorithm','${param.typeByWeekAlgorithm}',1) ;
  	checkFieldUpdate('typeWeekAlgorithm','${param.typeWeekAlgorithm}',1) ;
  	checkFieldUpdate('typeWeekDaysAlgorithm','${param.typeWeekDaysAlgorithm}',1) ;
  	checkFieldUpdate('typeByWeekDaysAlgorithm','${param.typeByWeekDaysAlgorithm}',1) ;
  	function checkFieldUpdate(aField,aValue,aDefaultValue) {
  	   	eval('var chk =  document.forms[0].'+aField) ;
  	   	var aMax=chk.length ;
  	   	//alert(aField+" "+aValue+" "+aMax+" "+chk) ;
  	   	if ((+aValue)==0 || (+aValue)>(+aMax)) {
  	   		chk[+aDefaultValue-1].checked='checked' ;
  	   	} else {
  	   		chk[+aValue-1].checked='checked' ;
  	   	}
  	  }
  	function showRow(aRowId, aCanShow ) {
		//alert(aRowId) ;
		try {
			//alert( aCanShow ? 'table-row' : 'none') ;
			$(aRowId).style.display = aCanShow ? 'table-row' : 'none' ;
		} catch (e) {
			// for IE
			//alert(aCanShow ? 'block' : 'none') ;
			try{
			$(aRowId).style.display = aCanShow ? 'block' : 'none' ;
			}catch(e) {}
		}	
	}
  	function showCell(aCellClassname,aCanShow,aValue) {
  		try {
			if (aValue!=null) document.getElementsByClassName(aCellClassname)[0].innerHTML = aValue ; 
			//alert( aCanShow ? 'table-row' : 'none') ;
			document.getElementsByClassName(aCellClassname)[0].style.display = aCanShow ? 'table-cell' : 'none' ;
		} catch (e) {
			// for IE
			//alert(aCanShow ? 'block' : 'none') ;
			try{
				document.getElementsByClassName(aCellClassname)[0].style.display = aCanShow ? 'block' : 'none' ;
			}catch(e) {}
		}
  	}
  	function viewProf() {
  		var check = getCheckedRadio(document.forms[0],"typeProfAlgorithm");
  		$('profType').value=check ;
  		if (+check==1) {
  			showRowList('isProfdayRow',3,4,true) ;
  			showRowList('isProfdayRow',5,5,false) ;
  		} else if (+check==2){
  			showRowList('isProfdayRow',3,4,false) ;
  			showRowList('isProfdayRow',5,5,true) ;
  		} 
  	}
  	function viewWeekDays() {
  		var propertyList=["monday","tuesday","wednesday","thursday","friday","saturday","sunday"] ;
  		viewCellByPatiry("typeWeekDaysAlgorithm","typeByWeekDaysAlgorithm","weekDaysAlgorithm",propertyList,"weekDaysAlgorithmForm","isWeekDaysRow3")
  	}
  	function showTitle(aType,aTypeBy,aClass) {
  		if (aType==2) {
  			showCell(aClass+"0",true,aTypeBy==1?"четный день":(aTypeBy==2?"четная неделя":"четный месяц"));
  			showCell(aClass+"1",true,aTypeBy==1?"нечетный день":(aTypeBy==2?"нечетная неделя":"нечетный месяц"));
  			showCell(aClass+"2",false,"");
  		} else if (aType==3) {
  			showCell(aClass+"0",true,aTypeBy==1?"1 день":(aTypeBy==2?"1 неделя":"1 месяц"));
  			showCell(aClass+"1",true,aTypeBy==1?"2 день":(aTypeBy==2?"2 неделя":"2 месяц"));
  			showCell(aClass+"2",true,aTypeBy==1?"3 день":(aTypeBy==2?"3 неделя":"3 месяц"));
  		} else {
  			showCell(aClass+"0",false,"");
			showCell(aClass+"1",false,"");
			showCell(aClass+"2",false,"");
  			
  		}
  	}
  	function viewWeek() {
  		var propertyList=["workWeek","dayPattern"] ;
  		viewCellByPatiry("typeWeekAlgorithm","typeByWeekAlgorithm","weekAlgorithm",propertyList,"weekAlgorithmForm","isWeekRow3")
  	}
  	function viewCellByPatiry(aTypeField,aTypeByField,aTitleField,aPropertyList,aForm,aTitleRow) {
  		var check = getCheckedRadio(document.forms[0],aTypeField);
  		var check1 = getCheckedRadio(document.forms[0],aTypeByField);
  		$(aForm+"0"+".parity").value=check ;
  		$(aForm+"0"+".calendarParity").value=check1 ;
  		showTitle(check,check1,aTitleField);
  		if (check==2) {
  			showRow(aTitleRow,true);
  			showCellList(aForm+"1",aPropertyList,true) ;
  			showCellList(aForm+"2",aPropertyList,false) ;
  		} else if (check==3) {
  			showRow(aTitleRow,true);
  			showCellList(aForm+"1",aPropertyList,true) ;
  			showCellList(aForm+"2",aPropertyList,true) ;  			
  		} else {
  			showRow(aTitleRow,false);
  			showCellList(aForm+"1",aPropertyList,false) ;
  			showCellList(aForm+"2",aPropertyList,false) ;  			
  		}
  	}
  	function onDays() {
  		if ($('isDays').checked) {
  			showRowList('isDaysRow',1,3,true) ;
  		} else{
  			showRowList('isDaysRow',1,3,false) ;
  		}
  	}
  	function onProfday() {
  		if ($('isProfday').checked) {
  			showRowList('isProfdayRow',1,2,true) ;
			viewProf() ;
		} else{
			showRowList('isProfdayRow',1,5,false) ;
  		}
  	}
  	function onWeek() {
  		if ($('isWeek').checked) {
  			showRowList('isWeekRow',1,6,true) ;
  			viewWeek() ;
  		} else {
  			showRowList('isWeekRow',1,6,false) ;
  			
  		}
  	}
  	function onWeekDays() {
  		if ($('isWeekDays').checked) {
  			showRowList('isWeekDaysRow',1,11,true) ;
  			viewWeekDays() ;
  		} else {
  			showRowList('isWeekDaysRow',1,11,false) ;
  		}
  	}
  	function showCellList(aForm,aPropertyList,aValue) {
  		for (var i=0;i<aPropertyList.length;i++) {
  			showCell(aForm+"."+aPropertyList[i],aValue) ;
  		}
  	}
  	function showRowList(aIdRow,aMin,aMax,aValue) {
  		for (var i=aMin;i<=aMax;i++) {
  			showRow(aIdRow+i,aValue) ;
  		}
  	}
  	onProfday() ;
  	onDays() ;
  	onWeekDays() ;
  	onWeek() ;
  	eventutil.addEventListener($('isDays'), 'click', onDays) ;
  	eventutil.addEventListener($('isProfday'), 'click', onProfday) ;
  	eventutil.addEventListener($('isWeek'), 'click', onWeek) ;
  	eventutil.addEventListener($('isWeekDays'), 'click', onWeekDays) ;
    
  	</script>
  	</msh:ifFormTypeIsCreate>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Lpu" beginForm="cal_patternBySpecForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
   <msh:ifFormTypeIsView formName="cal_patternBySpecForm">
		<msh:ifFormTypeAreViewOrEdit formName="cal_patternBySpecForm">
			<msh:sideMenu>
				<msh:sideLink key="ALT+2" params="id" action="/entityEdit-cal_patternBySpec" name="Изменить" title="Изменить" roles="/Policy/Mis/Worker/WorkCalendar/Pattern/Specialist/Edit"/>
				<msh:sideLink key="ALT+DEL" params="id" action="/entityParentDeleteGoParentView-cal_patternBySpec" name="Удалить" title="Удалить" roles="/Policy/Mis/Worker/WorkCalendar/Pattern/Specialist/Delete"/>
			</msh:sideMenu>
			<msh:sideMenu title="Добавить" >
				<msh:sideLink key="ALT+4" params="id" action="/entityParentPrepareCreate-cal_prophDayAlgorithm" name="алгоритм проф.дня" title="Добавить алгоритм шаблона рабочего календаря по проф.дню" roles="/Policy/Mis/Worker/WorkCalendar/Pattern/Algorithm"/>
				<msh:sideLink key="ALT+3" params="id" action="/entityParentPrepareCreate-cal_datesAlgorithm" name="алгоритм по датам" title="Добавить алгоритм шаблона рабочего календаря по датам" roles="/Policy/Mis/Worker/WorkCalendar/Pattern/Algorithm"/>
				<msh:sideLink key="ALT+5" params="id" action="/entityParentPrepareCreate-cal_weekAlgorithm" name="алгоритм по неделям" title="Добавить алгоритм шаблона рабочего календаря по неделям" roles="/Policy/Mis/Worker/WorkCalendar/Pattern/Algorithm"/>
				<msh:sideLink key="ALT+6" params="id" action="/entityParentPrepareCreate-cal_weekDaysAlgorithm" name="алгоритм по дням недели" title="Добавить алгоритм шаблона рабочего календаря по дням недели" roles="/Policy/Mis/Worker/WorkCalendar/Pattern/Algorithm"/>
			</msh:sideMenu>
						<msh:sideMenu title="Показать">
       <msh:sideLink roles="/Policy/Mis/Worker/WorkFunction/View" key="ALT+7" params="id" action="/javascript:goViewFunctions('.do')" name="Шаблоны расписания сотрудников" title="Перейти к установке шаблонов календарей по специалистам" />				
			</msh:sideMenu>
		</msh:ifFormTypeAreViewOrEdit>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>