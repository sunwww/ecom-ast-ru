<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="style" type="string">
    <style type="text/css">tr.deniedHospitalizating td {
            color: red ;}
        tr.otherLpu td {
            color: blue ;
        }</style>
  </tiles:put>
  <tiles:put name="title" type="string">
  <%
  String sslid = request.getParameter("sslid") ;
  if (sslid!=null) {
	  ActionUtil.getValueBySql("select sls.patient_id,pat.lastname||' '||pat.firstname||' '||pat.middlename||' '||to_char(pat.birthday,'dd.mm.yyyy') as f2 from MedCase sls left join patient pat on pat.id=sls.patient_id where sls.id="+sslid

			  , "patient", "infopat", request) ;
  } else {
	  request.setAttribute("patient", request.getParameter("id")) ;
	  ActionUtil.getValueBySql("select pat.id,pat.lastname||' '||pat.firstname||' '||pat.middlename||' '||to_char(pat.birthday,'dd.mm.yyyy') as f2 from patient pat where pat.id="+request.getParameter("id")
			  ,"patient","infopat",request) ;
	  
  }
  %>
 
    <h1> 
<span style=""><a  title="${infopat} " href="entityView-mis_patient.do?id=${patient}" style="text-decoration: underline;">Персона : ${infopat} </a><a ondblclick="javascript:goToPage(&quot;entityView-mis_patient.do&quot;,&quot;${patient}&quot;)" onclick="getDefinition(&quot;entityShortView-mis_patient.do?id=${patient}&quot;, event); return false ;" href="javascript:void(0)" class="a_instance_message"><img width="16" height="16" title="Просмотр записи" alt="Просмотр записи" src="/skin/images/main/view1.png"></a></span>
 Список всех госпитализаций (случаев лечения в стационаре).
</h1>
  </tiles:put>
  <tiles:put name="side" type="string" />
  <tiles:put name="body" type="string">
  <%
  String sslid = request.getParameter("sslid") ;
  if (sslid!=null) {
	  ActionUtil.getValueBySql("select sls.patient_id,sls.id from MedCase sls where sls.id="+sslid

			  , "patient", "sslid", request) ;
  } else {
	  request.setAttribute("patient", request.getParameter("id")) ;
	  request.setAttribute("sslid", "0") ;
  }
  %>
  <ecom:webQuery name="list" nativeSql="
  
  select sls.id as f1slsid
    ,to_char(sls.dateStart,'dd.mm.yyyy') as f2dateStart
      ,to_char(sls.dateFinish,'dd.mm.yyyy') as f3dateFinish
        ,vdh.id as f4vhdid,sls.username as f5slsusername
          ,case when sls.emergency='1' then 'да' else null end as f6emergency
            ,coalesce(ss.code,'')||case when vdh.id is not null then ' '||vdh.name else '' end as f7stacard
            ,ml.name as f8entdep
            
            ,(select list(mlLast.name) from MedCase sloLast 
            left join MisLpu mlLast on mlLast.id=sloLast.department_id
            where sloLast.parent_id=sls.id
            and (sloLast.dtype)='DEPARTMENTMEDCASE'  and case when sloLast.transferDate is null then 1 else 0 end=1
            ) as slolast
             ,case when vdh.id is not null then null when (coalesce(sls.dateFinish,CURRENT_DATE)-sls.dateStart)=0 then 1
               when vht.code='DAYTIMEHOSP' then ((coalesce(sls.dateFinish,CURRENT_DATE)-sls.dateStart)+1) else (coalesce(sls.dateFinish,CURRENT_DATE)-sls.dateStart) end as f10countDays
                 ,list(distinct vpd.name||' '||mkb.code) as f11diagDisch
                 
                 ,(select list(distinct vpd1.name||' '||mkb1.code) from MedCase sloLast 
                   left join Diagnosis diag1 on sloLast.id=diag1.medCase_id
                     left join VocIdc10 mkb1 on mkb1.id=diag1.idc10_id
                       left join VocDiagnosisRegistrationType vdrt1 on vdrt1.id=diag1.registrationType_id and vdrt1.code='4'
                         left join VocPriorityDiagnosis vpd1 on vpd1.id=diag1.priority_id 
                         where sloLast.parent_id=sls.id
                         and (sloLast.dtype)='DEPARTMENTMEDCASE' and  case when sloLast.transferDate is null then 1 else 0 end=1
                         ) as diaglast
                             ,case when vdh.id is not null then 'color: red ;'
                               when UPPER(sls.DTYPE) ='EXTHOSPITALMEDCASE' then 'color: blue ;'
                                 else '' end||
                                   case when sls.id='${sslid}' then 'background-color: #DDDDDD;border-bottom: 2px solid #666666;border-top: 2px solid #666666;' else '' end
                                       as f13style
                                         from MedCase sls
                                           left join VocHospType vht on vht.id=sls.hospType_id
                                             left join VocDeniedHospitalizating vdh on vdh.id=sls.deniedHospitalizating_id
                                               
                                                 
                                                   left join StatisticStub ss on ss.id=sls.statisticStub_id
                                                   
                                                     left join MisLpu ml on ml.id=sls.department_id
                                                       left join Diagnosis diag on sls.id=diag.medCase_id
                                                         left join VocIdc10 mkb on mkb.id=diag.idc10_id
                                                           left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id and vdrt.code='3'
                                                             left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
                                                             
                                                             
                                                             where sls.patient_id=${patient}
                                                               and UPPER(sls.DTYPE) IN ('HOSPITALMEDCASE','EXTHOSPITALMEDCASE')
                                                               
                                                                  group by sls.id,sls.dtype,sls.dateStart,sls.dateFinish,vdh.id ,sls.username ,sls.emergency, ss.code,vdh.id,vdh.name ,ml.name,vht.code
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
    <msh:tableNotEmpty name="list">
      <div class="h3">
        <h3>Легенда</h3>
      </div>
      <table class="tabview">
        <tr class="deniedHospitalizating">
          <td>―</td>
          <td>Отказ в госпитализации</td>
        </tr>
        <tr class="current">
          <td />
          <td>Текущая госпитализация</td>
        </tr>
        <tr class="otherLpu">
          <td />
          <td>Госпитализация в другом ЛПУ</td>
        </tr>
      </table>
    </msh:tableNotEmpty>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Admission/Create" params="id" action="/entityParentPrepareCreate-stac_sslAdmission" name="Госпитализацию" title="Добавить случай лечения в стационаре" key="ALT+2" />
      <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Admission/Create,/Policy/Mis/MedCase/Stac/Ssl/ShortEnter" params="id" action="/entityParentPrepareCreate-stac_sslAdmissionShort" name="Госпитализацию (короткая)" title="Добавить случай лечения в стационаре" key="ALT+4" />
      <msh:sideLink roles="/Policy/Mis/MedCase/Stac/Ssl/Ext/Create" params="id" action="/entityParentPrepareCreate-stac_extssl" name="Госпитализацию в другом стационаре" title="Зарегистрировать госпитализацию в другом стационаре" key="ALT+3"/>
    </msh:sideMenu>
    <msh:sideMenu title="Перейти">
      <msh:sideLink key="ALT+2" params="id" action="/entityView-mis_patient" name="⇧ Пациент" />
      <msh:sideLink roles="/Policy/XZ" params="id" action="/entityParentListRedirect-pres_prescriptList" name="⇧К списку назначений" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

