<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.s}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="StacJournal">Список СЛО </msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    	<tags:stac_journal currentAction="stac_journalByBedFund"/>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:section>
    <msh:sectionTitle>Журнал пациентов в отделение по коечному фонду ${info} ${infoTypePat}. 
    <a  href="stac_groupByBedFundPrint.do?id=${param.id}"  >Печать</a> &nbsp; <a href="stac_groupByBedFundList.do">Выбрать другую дату</a>
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_admission" nativeSql="
    select m.id,p.lastname||' '||p.firstname||' '||coalesce(p.middlename,'') as fio
    ,d.name as depname,ss.code as sscode,p.birthday as birthday
    , m.dateStart as mdateStart
    ,coalesce(m.dateFinish,m.transferDate) as mdateFinish
    ,h.dateStart as hdateStart
    ,h.dateFinish as hdateFinish
    ,	  case 
			when (coalesce(m.dateFinish,m.transferDate,CURRENT_DATE)-m.dateStart)=0 then 1 
			when bf.addCaseDuration='1' then ((coalesce(m.dateFinish,m.transferDate,CURRENT_DATE)-m.dateStart)+1) 
			else (coalesce(m.dateFinish,m.transferDate,CURRENT_DATE)-m.dateStart)
		  end as cnt1
    ,	  case 
			when (coalesce(h.dateFinish,CURRENT_DATE)-h.dateStart)=0 then 1 
			when bf.addCaseDuration='1' then ((coalesce(h.dateFinish,CURRENT_DATE)-h.dateStart)+1) 
			else (coalesce(h.dateFinish,CURRENT_DATE)-h.dateStart)
		  end as cnt2
    ,(select list(vdrt.name||' '||vpd.name||' '||mkb.code) from Diagnosis diag left join vocidc10 mkb on mkb.id=diag.idc10_id left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id where diag.medcase_id=m.id) as diag
    ,vhr.name as vhrname
    from MedCase as m 
    left join medcase as h on h.id=m.parent_id 
    left join VocHospitalizationResult vhr on vhr.id=h.result_id
    left join statisticstub as ss on ss.id=h.statisticstub_id 
    left join bedfund as bf on bf.id=m.bedfund_id 
    left join vocbedsubtype as vbst on vbst.id=bf.bedSubType_id 
    left join vocbedtype as vbt on vbt.id=bf.bedtype_id 
    left join mislpu as d on d.id=m.department_id 
    left join patient as p on p.id=m.patient_id 
    left join VocSocialStatus pvss on pvss.id=p.socialStatus_id
    left join Omc_Oksm ok on p.nationality_id=ok.id 
    where m.DTYPE='DepartmentMedCase' and ${dateType } between to_date('${dateBegin }','dd.mm.yyyy') and to_date('${dateEnd }','dd.mm.yyyy') 
    ${bedFund} ${addStatus} ${add } ${dep} ${servStream}
    order by p.lastname,p.firstname" 
    />
      <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Journal/ReestrByBedFund/NotViewInfoStac">
		    <msh:table selection="multiply" name="journal_admission" action="entitySubclassView-mis_medCase.do" idField="1">
		      <msh:tableColumn columnName="#" property="sn" />
		      <msh:tableColumn columnName="Стат.карта" property="4" />
		      <msh:tableColumn columnName="Отделение" property="3" />
		      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="2" />
		      <msh:tableColumn columnName="Год рождения" property="5" />
		      <msh:tableColumn columnName="Дата пост. (отд)" property="6" />
		      <msh:tableColumn columnName="Дата выписки / перевода (отд)" property="7" />
		      <msh:tableColumn columnName="К.Д по отд" property="10"/>
		      <msh:tableColumn columnName="Дата пост. (стац)" property="8" />
		      <msh:tableColumn columnName="Дата выписки (стац)" property="9" />
		      <msh:tableColumn columnName="К.Д по стац" property="11"/>
		      <msh:tableColumn columnName="Диагноз" property="12"/>
		      <msh:tableColumn columnName="Результат госпитализации" property="13"/>
		      
		     </msh:table>
      </msh:ifNotInRole>
      <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Journal/ReestrByBedFund/NotViewInfoStac">
	    <msh:table selection="multiply" name="journal_admission" action="entitySubclassView-mis_medCase.do" idField="1">
	      <msh:tableColumn columnName="#" property="sn" />
	      <msh:tableColumn columnName="Стат.карта" property="4" />
	      <msh:tableColumn columnName="Отделение" property="3" />
	      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="2" />
	      <msh:tableColumn columnName="Год рождения" property="5" />
	      <msh:tableColumn columnName="Дата пост. (отд)" property="6" />
	      <msh:tableColumn columnName="Дата выписки / перевода (отд)" property="7" />
	      <msh:tableColumn columnName="К.Д по отд" property="10"/>
	      <msh:tableColumn columnName="Диагноз" property="12"/>
	      <msh:tableColumn columnName="Результат госпитализации" property="13"/>
      
          </msh:table>
      
      </msh:ifInRole>
      
    </msh:sectionContent>
    </msh:section>
  </tiles:put>
</tiles:insert>

