<%@ page import="ru.ecom.web.util.ActionUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
  <%
      String typeStatus = ActionUtil.updateParameter("ClaimList","typeStatus","1", request) ;
      %>
  <tiles:put name="style" type="string">
  	<style type="text/css">

  	span.discharge {
  		list-style:none outside none;
  		background-color: #33cc66;
  	}
  	span.current {
  		list-style:none outside none;
  		
  	}
  	

  	</style>
  </tiles:put>
  <tiles:put name="body" type="string">
   <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Planning/View">
    <msh:form action="/stac_planning_hospitalizations.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <msh:panel>
    	<msh:row>
    		<msh:autoComplete property="department" vocName="vocLpuHospOtdAll" fieldColSpan="3" horizontalFill="true" size="200"/>
    	</msh:row>
    	<msh:row>
    		<msh:autoComplete property="roomType" vocName="vocRoomType" label="Уровень палаты"/>
    		<msh:autoComplete property="countBed" vocName="vocCountBedInHospitalRoom" label="Вместимость"/>
    	</msh:row>
    	<msh:row>
    		<msh:separator label="ПЕРИОД" colSpan="4"/>
    	</msh:row>
    	<msh:row>
    		<msh:textField property="dateBegin" label="Дата начала"/>
    		<msh:textField property="dateEnd" label="Окончания"/>
    	</msh:row>
        <msh:row>
            <td class="label" title="Статус заявки (typeStatus)" colspan="1"><label for="typeStatusName" id="typeStatusLabel">Статус:</label></td>
            <td><label>
                <input type="radio" name="typeStatus" value="1">  Все</label>
            </td>
            <td colspan="2"><label>
                <input type="radio" name="typeStatus" value="2">  Не госпитализированные</label>
            </td>
            <td colspan="2"><label>
                <input type="radio" name="typeStatus" value="3">  Только госпитализированные</label>
            </td>
        </msh:row>
    	<msh:row>
    		<td colspan="4">
    			<input type="submit" onclick="find()" value="Поиск"/>
    			<msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Planning/Create">
    			<input type="button" onclick="newP()" value="Добавить" >
    			</msh:ifInRole>
    		</td>
    	</msh:row>
    </msh:panel>
    </msh:form>
       <div id="dPicker"></div>
       <div id="dPickerData"></div>
    <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js">/**/</script>

  	
    <script type="text/javascript">
        checkFieldUpdate('typeStatus','${param.typeStatus}','1');

    //    makeCalc();


        function makeCalc() {
            var dt = new Date();
            var allDates ={};
            HospitalMedCaseService.getPreHospByMonth(dt.getFullYear(),dt.getMonth(), {
                callback:function(inf) {
                  //  console.log(inf);
                    allDates = JSON.parse(inf);
                    jQuery('#dPicker').datepicker({
                        regional:"ru"
                        ,dateFormat:"dd.mm.yy"
                        ,onSelect: function(date, obj){
                            console.log('date='+date+"</>");
                            jQuery("#dPickerData").load("js-mis_hospitalBed-listByDate.do?startDate="+date+"&finishDate="+date);
                        }
                        ,onChangeMonthYear: function(y,m,cal) {
                            console.log("changed month");
                         //   makeCalc()
                        }
                        ,beforeShowDay:function (day) {
                            return [true,"dayNumber dateAmount:"+day.getDate()];

                        }

                    });
       /*             jQuery('.dayNumber').each(function(a,b){
                        var am = jQuery(b).attr('class').trim().split(" ")[1].split(":")[0];
                       //console.log(jQuery(b).children(':first').html());

                        jQuery(b).children(':first').append(am);
                    });*/
                }
            });
        }

        /**примечание на день */
        function setDateComment(aDate) {

        }

        function checkFieldUpdate(aField,aValue,aDefaultValue) {
            if (jQuery(":radio[name="+aField+"][value='"+aValue+"']").val()!=undefined) {
                jQuery(":radio[name="+aField+"][value='"+aValue+"']").prop('checked',true);
            } else {
                jQuery(":radio[name="+aField+"][value='"+aDefaultValue+"']").prop('checked',true);
            }
        }
        function createHosp(id) {
            id = id.split("#");
            window.document.location='entityParentPrepareCreate-stac_sslAdmission.do?id='+id[1]+'&preHosp='+id[0];
        }
    	function find() {
    		
    	}
    	function newP() {
    		window.location = 'entityPrepareCreate-stac_planHospital.do?department='+$('department').value+"&roomType="+$('roomType').value+"&countBed="+$('countBed').value+"&dateEnd="+$('dateEnd').value+"&dateBegin="+$('dateBegin').value+"&tmp="+Math.random() ;
      	}
    	function editPlanning(aWp) {
    		window.location = 'entityEdit-stac_planHospital.do?id='+aWp+"&tmp="+Math.random() ;
    	}
    	function viewSlo(aSlo) {
    		getDefinition('entityShortView-stac_slo.do?id='+aSlo+"&tmp="+Math.random()) ;
    	}
    	
    	/*if (+$('department').value<1) {
    		HospitalMedCaseService.getDefaultDepartmentByUser ({
     			callback: function(aResult) {
     				var res = aResult.split('#') ;
     				if (+res[0]!==0) {
     					$('department').value = res[0] ; 
     					$('departmentName').value = res[1] ; 
     				}
     			}
     		}) ;      		
    	} */
    </script>
    </msh:ifInRole>
        <%
    
    String date = request.getParameter("dateBegin") ;
    String dateEnd = request.getParameter("dateEnd") ;

    if (dateEnd==null || dateEnd.equals("")) dateEnd=date ;
    request.setAttribute("dateBegin", date) ;
    request.setAttribute("dateEnd", dateEnd) ;
    
    String department = request.getParameter("department") ;
    boolean isgoing = false ;
	if (department!=null && !department.equals("") && !department.equals("0")) {
		request.setAttribute("department", " and wp.lpu_id='"+department+"'") ; 
		request.setAttribute("departmentPlanSql", " and wchb.department_id='"+department+"'") ; 
		request.setAttribute("department1", " slo.department_id='"+department+"' and") ;
		isgoing=true ;
	}
    if (date!=null && !date.equals("") && isgoing) {
    	
    	String roomType = request.getParameter("roomType") ;
    	if (roomType!=null && !roomType.equals("") && !roomType.equals("0")) {
    		request.setAttribute("roomType", " and wp.roomType_id='"+roomType+"'") ;
    	}
    	String countBed = request.getParameter("countBed") ;
    	if (countBed!=null && !countBed.equals("") && !countBed.equals("0")) {
    		request.setAttribute("countBed", " and wp.countBed_id='"+countBed+"'") ;
    	}
	}
    String statusSql ="";
    if ("2".equals(typeStatus)) {
        statusSql = " and wchb.medcase_id is null"; //не госпитализированные
    } else if ("3".equals(typeStatus)) {
        statusSql = " and wchb.medcase_id is not null";
    }
    request.setAttribute("statusSql",statusSql);

        %>
      <a href="javascript:void(0)" onclick="window.location.href='fillbedsreport.do'">Распределение пациентов по палатам</a>
    <msh:section title="Список направлений на госпитализацию">
    <ecom:webQuery name="stac_planHospital"
    nativeSql="select wchb.id,ml.name as mlname,p.id,p.lastname||' '||p.firstname||' '||p.middlename as fio,p.birthday
as birthday,mkb.code,wchb.diagnosis
 ,wchb.dateFrom,mc.dateStart,mc.dateFinish,list(mkbF.code),wchb.phone
 ,wchb.createDate as wchbcreatedate
 ,list(vwf.name ||' '||wPat.lastname) as f14_creator
 ,list(case when wchb.medcase_id is not null then 'background-color:green' when wf.isAdministrator='1' then 'background-color:#add8e6' else '' end) as f15_styleRow
 ,case when wchb.medcase_id is null then wchb.id||'#'||p.id else null end as f16_createHospIds
from WorkCalendarHospitalBed wchb
left join Patient p on p.id=wchb.patient_id
left join MedCase mc on mc.id=wchb.medcase_id
left join VocIdc10 mkb on mkb.id=wchb.idc10_id
left join MisLpu ml on ml.id=wchb.department_id
left join Diagnosis diag on diag.medcase_id=mc.id
left join VocIdc10 mkbF on mkbF.id=diag.idc10_id
left join workfunction wf on wf.id=wchb.workfunction_id
left join worker w on w.id=wf.worker_id
left join patient wpat on wpat.id=w.person_id
left join vocworkfunction vwf on vwf.id=wf.workfunction_id
where wchb.dateFrom between to_date('${dateBegin}','dd.mm.yyyy')
	 and to_date('${dateEnd}','dd.mm.yyyy')
 ${departmentPlanSql} ${statusSql}
group by wchb.id,wchb.createDate,ml.name,p.id,p.lastname,p.firstname,p.middlename,p.birthday
,mkb.code,wchb.diagnosis,wchb.dateFrom,mc.dateStart,mc.dateFinish,wchb.phone
order by wchb.dateFrom,p.lastname,p.firstname,p.middlename
    "
    />
    <msh:table printToExcelButton="Сохранить в excel" name="stac_planHospital" action="entityParentView-stac_planHospital.do"
    idField="1" styleRow="15" >
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Дата пред.госпитализации" property="8"/>
            <msh:tableColumn columnName="Направлен в отделение" property="2"/>
            <msh:tableColumn columnName="Телефон пациента" property="12"/>
            <msh:tableColumn columnName="ФИО пациента" property="4"/>
            <msh:tableColumn columnName="Дата рождения" property="5"/>
            <msh:tableColumn columnName="Код МКБ" property="6"/>
            <msh:tableColumn columnName="Диагноз" property="7"/>
            <msh:tableColumn columnName="Кто создал" property="14"/>
            <msh:tableColumn columnName="Дата создания" property="13"/>
        <msh:tableButton property="16" buttonShortName="ГОСП" buttonFunction="createHosp" hideIfEmpty="true" />

    </msh:table>
    </msh:section>
    <div id="divViewBed">
    </div>
    
  </tiles:put>
  <tiles:put name="side" type="string">
  	<tags:stac_journal currentAction="stac_planning_hospitalizations"/>
  </tiles:put>

  <tiles:put name="title" type="string">
  </tiles:put>

</tiles:insert>