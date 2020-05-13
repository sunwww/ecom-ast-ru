<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_medCaseForm" mainMenu="StacJournal" title="Список всех хир.операций в ССЛ" />
  </tiles:put>
  <tiles:put name="side" type="string" />
  <tiles:put name="body" type="string">
      <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/HitechMedCase/View">
          <ecom:webQuery name="hitechCases" nameFldSql="hitechCases_sql" nativeSql="select himc.id
          ,to_char(himc.ticketDate,'dd.mm.yyyy') as f2_ticketDate
          ,to_char(himc.planHospDate,'dd.mm.yyyy') as f3_planHospDate
          ,vkhc.code ||' '||vkhc.name as f4_kind
          ,vmhc.code ||' '||vmhc.name as f5_method
          ,himc.stantAmount as f6_stantAmount
          from HitechMedicalCase himc
          left join vocKindHighCare vkhc on vkhc.id=himc.kind_id
          left join vocMethodHighCare vmhc on vmhc.id=himc.method_id
          where  
           himc.medCase_id=${param.id}
          order by himc.ticketDate
          "/>
	    <msh:section title="Случаи ВМП " createUrl="entityParentPrepareCreate-stac_vmpCase.do?id=${param.id}"
	    createRoles="/Policy/Mis/MedCase/Stac/Ssl/HitechMedCase/Create">
	    	<msh:table viewUrl="entityShortView-stac_vmpCase.do"
	    	editUrl="entityParentEdit-stac_vmpCase.do"  
	    	name="hitechCases" action="entityParentView-stac_vmpCase.do" idField="1">
	    		<msh:tableColumn columnName="#" property="sn"/>
	    		<msh:tableColumn columnName="Дата направления" property="2"/>
	    		<msh:tableColumn columnName="Дата предварительной госпитализации" property="3"/>
	    		<msh:tableColumn columnName="Вид" property="4"/>
	    		<msh:tableColumn columnName="Метод" property="5"/>
                <msh:tableColumn columnName="Количество стентов" property="6"/>
	    	</msh:table>
	    </msh:section>
    </msh:ifInRole>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink roles="" params="id" action="/entityParentPrepareCreate-stac_vmpCase" name="Случай ВМП" title="Добавить случай ВМП" />
    </msh:sideMenu>
    
  </tiles:put>
</tiles:insert>

