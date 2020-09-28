<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="ru.ecom.web.login.LoginInfo"%>
<%@page import="ru.nuzmsh.web.tags.helper.RolesHelper"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Voc" title="Кодификатор" />
  </tiles:put>
  <tiles:put name="side" type="string" />
  <tiles:put name="body" type="string">
  
          <%
        	String login = LoginInfo.find(request.getSession(true)).getUsername() ;
        	request.setAttribute("login", login) ;
        %>
        <ecom:webQuery name="infoByLogin"
        maxResult="1" nativeSql="
        select wf.id,w.lpu_id,case when wf.isAdministrator='1' then '1' else null end as isAdmin
        from SecUser su
        left join WorkFunction wf on su.id=wf.secUSer_id
        left join Worker w on wf.worker_id=w.id
        where su.login='${login}'
        "
        />
        <%
        	boolean isViewAllDepartment=RolesHelper.checkRoles("/Policy/Mis/MedCase/Stac/Journal/ShowInfoAllDepartments",request) ;
	    	List list= (List)request.getAttribute("infoByLogin");
	    	WebQueryResult wqr = list.size()>0?(WebQueryResult)list.get(0):null ;
        	String department = request.getParameter("department") ;
        	String curator = request.getParameter("curator") ;
        	String workFunc = wqr!=null?""+wqr.get1():"0" ;
        	boolean isBossDepartment=(wqr!=null&&wqr.get3()!=null)?true:false ;
 
        	int type=0 ;
        	if (isViewAllDepartment) {
        		type=2 ;
       		} else if (isViewAllDepartment) {
       			type=1 ;
       		} else if (wqr!=null) {
       			if (isBossDepartment) {
       				type=2 ;
       				department=""+wqr.get2() ;
       			} else {
       				type=3 ;
       				curator=workFunc ;
       			}
       		}
        	if (department!=null &&!department.equals("")&&!department.equals("0")) {
           		request.setAttribute("departmentInfoSql", "case when vdpc.department_id='"+department+"' then ml.name || case when vdpc.isLowerCase='1' then ' (НР)' else '' end else null end") ;
        	} else {
        		request.setAttribute("departmentInfoSql", "ml.name || case when vdpc.isLowerCase='1' then ' (НР)' else '' end") ;
        	}
       		request.setAttribute("department", department) ;
       		request.setAttribute("curator", curator) ;        	
       	%>
  	<ecom:webQuery name="list" nativeSql="select vdp.id as vdpid,vdpg.name as vdpgname,vdp.name as vdpname
  	,vdp.dimension as vdpdimension
  	, list(distinct ${departmentInfoSql}) as list
  	from VocDocumentParameter vdp
left join VocDocumentParameterGroup vdpg on vdp.parameterGroup_id=vdpg.id
left join VocDocumentParameterConfig vdpc on vdpc.documentParameter_id=vdp.id
left join MisLpu ml on ml.id=vdpc.department_id
group by vdp.id, vdpg.name, vdp.name, vdp.dimension 
order by vdpg.name,vdp.id"/>
    <msh:table selection="multy"  name="list" action="entityView-voc_documentParameter.do" idField="1">
	<msh:tableNotEmpty name="list">
		  	<msh:toolbar >
			                	<tbody>
			                		<msh:toolbar>
				                		<tr>
			                					<msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Journal/ShowInfoAllDepartments">
			                					<msh:autoComplete size="100" property="lpu" horizontalFill="true" label="Отделение" vocName="lpu"/>
			                					<td>
			                					<input type='button' value='Просмотр' onclick="javascript:viewDepartment()" />
			                					</td>
			                					</msh:ifInRole>
			                					<msh:checkBox property="isLowerCase" label="Отображать в нижнем регистре"/>
				                		</tr>
				                		<tr>
			                									    
			                					            			<th class='linkButtons' colspan="6">
			                					

			                					<input type='button' value='Установить' onclick="javascript:updateDataFromConfig()" />
			                					<input type='button' value='Снять' onclick="javascript:remomeDataFromConfig()" />
			                					
			                				</th>
				                		</tr>
			                		</msh:toolbar>
			                	</tbody>
		  	</msh:toolbar>
  	</msh:tableNotEmpty>
      <msh:tableColumn columnName="Название группы" property="2" />
      <msh:tableColumn property="3" columnName="Название параметра" />
      <msh:tableColumn columnName="Размерность" property="4"/>
      <msh:tableColumn columnName="Привязка к отделениям" property="5"/>
    </msh:table>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink params="" action="/entityPrepareCreate-voc_documentParameter" name="Создать" title="Создать" roles="/Policy/Mis/MedService/Create" />
    </msh:sideMenu>
    <tags:voc_menu currentAction="diary_param_list"/>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js">/**/</script>
  	<script type="text/javascript">
	if ($('isLowerCase')) {
		$('isLowerCase').checked=true ;
	}
  		function updateDataFromConfig() {
            var ids = theTableArrow.getInsertedIdsAsParams("","list") ;
            if (ids) {
            	var department = +'${department}' ;
            	if ($('lpu')) {
            		if (+$('lpu').value>0) {
            			department = +$('lpu').value ;
            		}
            	}
            	var isLowerCase = '0' ;
            	if ($('isLowerCase')) {
            		if ($('isLowerCase').checked) {
            			isLowerCase = '1' ;
            		}
            	}
            	if (department>0) {
            		HospitalMedCaseService.updateDataFromParameterConfig(department,isLowerCase,ids, {
	                    callback: function(aResult) {

	                        document.location.reload() ;
	                      }
	                     }
	                    );

            	} else {
            		alert('Неопределено отделение!!!') ;
            	}
            } else {
                alert("Нет выделенных параметров");
            }
  		}
  		function remomeDataFromConfig() {
            var ids = theTableArrow.getInsertedIdsAsParams("","list") ;
            if (ids) {
            	var department = +'${department}' ;
            	if ($('lpu')) {
            		if (+$('lpu').value>0) {
            			department = +$('lpu').value ;
            		}
            	}
            	
            	if (department>0) {
            		HospitalMedCaseService.removeDataFromParameterConfig(department,ids, {
	                    callback: function(aResult) {
	                    	document.location.reload() ;
	                      }
	                     }
	                    );

            	} else {
            		alert('Неопределено отделение!!!') ;
            	}
            } else {
                alert("Нет выделенных параметров");
            }
  		}
  		function viewDepartment() {
        	var department = +'${department}' ;
        	if ($('lpu')) {
        		if (+$('lpu').value>0) {
        			department = +$('lpu').value ;
        		}
        	}
  			goToPage('entityList-voc_documentParameter.do?department='+department,'0') ;
  		}
  	</script>
  </tiles:put>
</tiles:insert>

