<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@  attribute name="currentAction" required="true" description="Текущее меню" %>

<style type="text/css">
a#${currentAction}, #side ul li a#${currentAction}, #side ul li a#${currentAction}  {
    color: black ;
    background-color: rgb(195,217,255) ;
    cursor: text ;
    border: none ;
    text-decoration: none ;
    background-image: url("/skin/images/main/sideSelected.gif");
    background-repeat: no-repeat;
    background-position: center left; ;
	font-weight: bold ;
    -moz-border-radius-topleft: 4px ;
    -moz-border-radius-bottomleft: 4px ;
}
</style>
<msh:sideMenu></msh:sideMenu>
<msh:sideMenu title="Поиск документов нетрудоспособности">
	<msh:sideLink name="По серии и номеру" action="/dis_documents.do" key="ALT+7" 
		title="Поиск документов нетрудоспособности по серии и номеру" roles="/Policy/Mis/Disability/Case/Document/View"
		styleId="find_number"/>
</msh:sideMenu>
    <msh:sideMenu title="Журналы НТ">
      <msh:sideLink key="CTRL+7" params="" action="/dis_documentClose" name="Выданных документов НТ" 
        roles='/Policy/Mis/Disability/Case/Document/View' styleId="closeDNT" />
      <msh:sideLink key="CTRL+8" params="" action="/dis_documentOpen" name="Открытых документов НТ" 
        roles='/Policy/Mis/Disability/Case/Document/View' styleId="openDNT" />
      <msh:sideLink name="Журнал поступивших бланков" action="/entityList-dis_blanks.do"
      	roles="/Policy/Mis/Disability/Blanks/View" styleId="blanksNT"
      />
      <msh:sideLink name="Свод" action="/dis_swod.do"
      	roles="/Policy/Mis/Disability/Case/Document/View" styleId="swodNT"
      />
    </msh:sideMenu>
<msh:sideMenu title="Журналы по КЭР">
      <msh:sideLink name="Направления на ВК" action="/expert_journal_ker.do"
      	roles="/Policy/Mis/MedCase/ClinicExpertCard/JournalByPeriod" styleId="journalKERByPeriod"
      />
      <msh:sideLink name="Открытые направления на ВК" action="/expert_kersopen.do"
      	roles="/Policy/Mis/MedCase/ClinicExpertCard/JournalOpenCase" styleId="journalOpenKER"
      />

</msh:sideMenu>
