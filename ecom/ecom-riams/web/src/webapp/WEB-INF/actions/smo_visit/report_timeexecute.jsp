<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom"%>


<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<% String typeDate=ActionUtil.updateParameter("BloodReport","typeDate","1", request); %>
		<msh:form action="/report_timeexecute.do"
				  defaultField="hello">
			<msh:panel>
				<msh:row>
					<msh:row>
						<msh:autoComplete vocName="vocWorkFunctionShort"
										  property="building" label="Отделение" fieldColSpan="4" size="60" />
					</msh:row>
					<msh:row>
						<td class="label" title="Отображать" colspan="1">
							<label for="typeDateName" id="typeDateLabel">По:</label>
						</td>
						<td onclick="this.childNodes[1].checked='checked';">
							<input type="radio" name="typeDate" value="1"> Дате записи
						</td>
						<td onclick="this.childNodes[1].checked='checked';">
							<input type="radio" name="typeDate" value="2"> Дате приема
						</td>
					</msh:row>
					<msh:row>
						<msh:textField property="beginDate" label="c" />
						<msh:textField property="endDate" label="по" />
					</msh:row>
					<td><input type="submit" value="Найти" /></td>
				</msh:row>
			</msh:panel>
		</msh:form>


		<%

			String SQL = "";
			String dep = request.getParameter("building");

			if(dep!=null && !dep.equals("")){
				SQL+=" and vwf.id="+dep;
				request.setAttribute("SQL", SQL);
			}

			String beginDate = request.getParameter("beginDate") ;
			if (beginDate!=null && !beginDate.equals("")) {
				String finishDate = request.getParameter("endDate") ;
				if (finishDate==null || finishDate.equals("")) {
					finishDate=beginDate ;
				}
				request.setAttribute("dateStart", beginDate);
				request.setAttribute("dateFinish", finishDate) ;


				String SQLDate="";
				if(typeDate.equals("1")){
					SQLDate = " and coalesce(wct.createdateprerecord,m.createdate) ";
				}else{
					SQLDate = " and m.datestart ";
				}
				request.setAttribute("SQLDate", SQLDate);
		%>

		<ecom:webQuery isReportBase="true" name = "elnList" nativeSql="select
		vwf.name,
		p.lastname||' '||p.firstname||' '||p.middlename as fio,
		to_char((coalesce(wct.createdateprerecord,m.createdate)),'dd.MM.yyyy')||' '||to_char((coalesce(wct.createtimeprerecord,m.createtime)),'HH24:MI:ss') as whenrecord,
		to_char((wcd.calendardate),'dd.MM.yyyy')||' '||wct.timefrom as onRec,
		CAST(CAST(wcd.calendardate + wct.timefrom AS timestamp)-CAST(coalesce(wct.createdateprerecord,m.createdate)+coalesce(wct.createtimeprerecord,m.createtime) AS timestamp) as text) as executeTime,
		to_char((m.datestart),'dd.MM.yyyy')||' '||m.timeexecute as exect,
		CAST(CAST(m.datestart+m.timeexecute AS timestamp) -CAST(wcd.calendardate + wct.timefrom AS timestamp) as text) as executeTime2
		from workcalendartime  wct
		left join workcalendarday wcd on wcd.id = wct.workcalendarday_id
		left join medcase m on m.id = wct.medcase_id
		left join patient p on p.id = m.patient_id
		left join workcalendar wc on wc.id = wcd.workcalendar_id
		left join workfunction wf on wf.id = wc.workfunction_id
		left join vocworkfunction vwf on vwf.id = wf.workfunction_id
		where
		m.id is not null
		${SQLDate} between to_date('${dateStart}','dd.MM.yyyy') and to_date('${dateFinish}','dd.MM.yyyy')
		${SQL}" />
		* - разница между датой, когда был записан и на какую дату записан пациент; <br>
		** - разница между датой записи и датой исполнения;
		<msh:section>
			<msh:sectionContent>
				<msh:table name="elnList" action="" idField="1">
					<msh:tableColumn columnName="Рабочая функция" property="1" />
					<msh:tableColumn columnName="ФИО пациента" property="2" />
					<msh:tableColumn columnName="Дата записи" property="3" />
					<msh:tableColumn columnName="На какую дату записан" property="4"/>
					<msh:tableColumn columnName="Время ожидания *" property="5"/>
					<msh:tableColumn columnName="Дата приема" property="6"/>
					<msh:tableColumn columnName="Время ожидания **" property="7" />
				</msh:table>
			</msh:sectionContent>
		</msh:section>
		<%} else { %>
		<p>Укажите период!</p>
		<%}%>

	</tiles:put>

	<tiles:put name="javascript" type="string">
		<script type="text/javascript">
            checkFieldUpdate('typeDate','${typeDate}',1) ;

            function checkFieldUpdate(aField,aValue,aDefault) {

                eval('var chk =  document.forms[0].'+aField) ;
                var max = chk.length ;
                if ((+aValue)>max) {
                    chk[+aDefault-1].checked='checked' ;
                } else {
                    chk[+aValue-1].checked='checked' ;
                }
            }

		</script>
	</tiles:put>
</tiles:insert>