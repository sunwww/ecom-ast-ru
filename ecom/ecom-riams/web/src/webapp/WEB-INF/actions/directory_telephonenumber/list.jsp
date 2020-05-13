<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom"%>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp"
	flush="true">
	 
	<tiles:put name="side" type="string">
		<msh:sideMenu>
			<msh:sideLink
				roles="/Policy/Mis/Directory/Department/CreateRecord" key="ALT+N"
				action="/directory_createEntry.do"
				name="Создать" />
		</msh:sideMenu>
	</tiles:put>
	
	<tiles:put name="body" type="string">
		<msh:form action="/directory_search.do"
			defaultField="hello">
			<msh:panel>
				<msh:row>
					<msh:row>
						<msh:autoComplete vocName="vocBuildingShort" property="building"
							label="Корпус" fieldColSpan="4" size="60" />
					</msh:row>
					<msh:row>
						<msh:autoComplete vocName="vocBuildingLevelShort"
							property="buildingLevel" label="Этажа" fieldColSpan="4" size="60" />
					</msh:row>
					<msh:row>
					<msh:autoComplete vocName="vocDepartmet"
							property="department" label="Отделение" fieldColSpan="4" size="60" />
					</msh:row>
					<msh:row>
						<msh:textField property="lastname" label="По параметру *" horizontalFill="true" />
					</msh:row>
					<td><input type="submit" value="Найти" /></td>
					
				</msh:row>
				* - по номеру телефона или наименованию или ФИО<br>
			</msh:panel>
		</msh:form>
		<%
		    String whereSQL = "";
				    String buildingId = request.getParameter("building");
				    String buildingLevelId = request.getParameter("buildingLevel");
				    String FIO = request.getParameter("lastname");
				    String dep = request.getParameter("department");
				    String searchSQL = "";
				    
				    if (buildingId != null && !buildingId.equals("")) {
					searchSQL += " vb.id=" + buildingId;
				    }

				    if (buildingLevelId != null && !buildingLevelId.equals("")) {
					if (!searchSQL.equals(""))
					    searchSQL += " and ";
					searchSQL += " vbl.id=" + buildingLevelId;
				    }
				    
				    if(dep!=null && !dep.equals("")){
					if (!searchSQL.equals("")) searchSQL += " and ";
					searchSQL+=" m.id="+dep;
				    }

				    if (FIO != null && !FIO.equals("")) {
					if (!searchSQL.equals(""))
					    searchSQL += " and ";
					searchSQL += "upper(p.lastname||' '||p.firstname||' '||p.middlename) like '%"
						+ FIO.toUpperCase()
						+ "%' or upper(e.insidenumber) like '%"
						+ FIO.toUpperCase()
						+ "%' or tn.telnumber like '%"
						+ FIO.toUpperCase()
						+ "%' or upper(e.comment) like '%"
						+ FIO.toUpperCase() + "%'";
				    }
				    if (!searchSQL.equals("")) {
					whereSQL = "where " + searchSQL;
				    }

				    request.setAttribute("whereSQL", whereSQL);
		%>
		<input id="getExcel" class="button" name="submit" value="Сохранить в excel" onclick="mshSaveTableToExcelById()" role="button" type="submit">
		
		<div id="myTemp">
			<ecom:webQuery  name="list" nameFldSql="listSQL"
			nativeSql="select  e.id,
					e.insidenumber,
					list(tn.telnumber) as sot,
					list(tn2.telnumber) as gor,
					e.comment as comment,p.lastname||' '||p.firstname||' '||p.middlename as names,vb.name as build,vbl.name as level,m.name as dep
					from entry e
					left join WorkFunction as wf on wf.id= e.person_id
					left join Worker as w on w.id=wf.worker_id
					left join Patient as p on p.id=w.person_id
					left join department d on d.id=e.department_id
					left join mislpu m on m.id=d.department_id
					left join vocbuildinglevel vbl on vbl.id = d.buildinglevel_id
					left join vocbuilding vb on vb.id = d.building_id
					left join telephonenumber tn on tn.entry_id = e.id and tn.typenumber_id = 3
					left join voctypenumber vtn on vtn.id = tn.typenumber_id
					left join telephonenumber tn2 on tn2.entry_id = e.id and tn2.typenumber_id = 2
					left join voctypenumber vtn2 on vtn2.id = tn2.typenumber_id
					${whereSQL}
					group by e.id,names,build,level,dep
					order by build, level,dep"/>
		<msh:table name="list" action="directory_editEntry.do" idField="1">
			<msh:tableColumn columnName="#" property="sn" />
			<msh:tableColumn columnName="Внутренний номер" property="2" />
			<msh:tableColumn columnName="Сотовый" property="3" />
			<msh:tableColumn columnName="Городской" property="4" />
			<msh:tableColumn columnName="Наименование" property="5" />
			<msh:tableColumn columnName="ФИО" property="6" />
			<msh:tableColumn columnName="Корпус" property="7" />
			<msh:tableColumn columnName="Этаж" property="8" />
			<msh:tableColumn columnName="Отделение" property="9" />
		</msh:table>
		</div>
	</tiles:put>
<tiles:put name="javascript" type="string">
		<script type="text/javascript">
		
		function mshPrintTextToExcelTable (html) {
			  window.location.href='data:application/vnd.ms-excel,'+'\uFEFF'+encodeURIComponent(html);
			}
		
		function mshSaveTableToExcelById() {
			  mshPrintTextToExcelTable(document.getElementById("myTemp").outerHTML);
			  //mshPrintTextToExcelTable(document.getElementsByClassName("tabview").outerHTML);
		}//tabview sel tableArrow

			function myAlert() {
				alert(123);
            }
		</script>
	</tiles:put>
</tiles:insert>
