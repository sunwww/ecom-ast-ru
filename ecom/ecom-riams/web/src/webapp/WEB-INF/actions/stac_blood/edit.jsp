<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
  <tiles:put name="side" type="string">
    	<tiles:insert page="/WEB-INF/tiles/stac_sslSide.jsp"/>
  </tiles:put>

  <tiles:put name="body" type="string">
    <!-- 
    	  - <h1>Переливание крови: <span><stac:patientInfo/></span></h1>
    	  -->
    <msh:form action="/entityParentSaveGoView-stac_blood.do" defaultField="transfusionDate">
      <msh:hidden property="id" guid="d10f460a-e434-45a5-90f0-b0a7aed00ec6" />
      <msh:hidden property="medCase" guid="710eb92b-fc3f-4b44-9390-b32df6837280" />
      <msh:hidden property="patient" guid="9d908e88-e051-4d0a-8da6-3f5f4b226493" />
      <msh:hidden property="saveType" guid="bd322f07-c944-4587-a963-a09db2b93caf" />
	    <msh:panel>
	      <msh:row>
	        <msh:textField label="Дата" property="transfusionDate" />
	        <msh:autoComplete label="Отделение" property="department" />
	      </msh:row>
	      <msh:row>
	        <msh:textField label="Пеpелито в дозах" property="transfusionAllDose" labelColSpan="3" />
	      </msh:row>
	      <msh:row>
	        <msh:textField label="Кол-во в литpах" property="litreCount" labelColSpan="3" />
	      </msh:row>
	      <msh:row>
	        <msh:separator colSpan="4" label="Привлеченные доноры из родственников" />
	      </msh:row>
	      <msh:row>
	        <msh:textField label="Кол-во доноpов" property="donorCount" labelColSpan="3" />
	      </msh:row>
	      <msh:row>
	        <msh:textField label="Количество сданной ими крови" property="donorBloodCount" labelColSpan="3" />
	      </msh:row>
	      <msh:row>
	        <msh:separator colSpan="4" label="Перелито в литрах или дозах" />
	      </msh:row>
	      <msh:row>
	        <msh:textField label="Кpовь цельная всего" property="wholeBlood" />
	      </msh:row>
	      <msh:row>
	        <msh:textField label="Кpовь консеpв всего" property="bankedBlood" />
	      </msh:row>
	      <msh:row>
	        <msh:textField label="Эpитpоцитаpная масса всего" property="packedRedBloodCells" />
	      </msh:row>
	      <msh:row>
	        <msh:textField label="Плазма замоpоженая всего" property="frozenPlasma" />
	      </msh:row>
	      <msh:row>
	        <msh:textField label="Нативная плазма всего" property="nativePlasma" />
	      </msh:row>
	      <msh:row>
	        <msh:textField label="Свежезамоpоз.плазма всего" property="freshFrozenPlasma" />
	      </msh:row>
	      <msh:row>
	        <msh:textField label="МОК всего" property="MOK" />
	      </msh:row>
	      <msh:row>
	        <msh:textField label="Антистафилок плазма всего" property="antiStaphylococcalPlasma" />
	      </msh:row>
	      <msh:row>
	        <msh:textField label="Отмытые эpитpоциты всего" property="washCleanErythrocytes" />
	      </msh:row>
	      <msh:row>
	        <msh:textField label="Размоpож отмытые эpитpоциты всего" property="defreezeWashCleanErythrocytes" />
	      </msh:row>
	      <msh:row>
	        <msh:textField label="Сухая плазма всего" property="driedPlasma" />
	      </msh:row>
	      <msh:row>
	        <msh:textField label="Альбумин 10% всего" property="seralbumin" />
	      </msh:row>
	      <msh:row>
	        <msh:textField label="Стандаpт сывоpотка АВО всего" property="ABO" />
	      </msh:row>
	      <msh:row>
	        <msh:textField label="Кpиопpецитат сухой всего" property="cryoprecipitateDried" />
	      </msh:row>
	      <msh:row>
	        <msh:textField label="Унивеpс pеагент антиpезус всего" property="uniAntiRhesusReagent" />
	      </msh:row>
	      <msh:row>
	        <msh:textField label="Кpиопpеципитат замоpож всего" property="cryoprecipitateFrozen" />
	      </msh:row>
	      <msh:row>
	        <msh:textField label="Стандаpт эpитpоциты всего" property="standardErythrocytes" />
	      </msh:row>
	      <msh:row>
	        <msh:textField label="Иммуноглобулин ноpм чел всего" property="immunoglobulinNormal" />
	      </msh:row>
	      <msh:row>
	        <msh:textField label="Концетpат тpомбоц всего" property="concentratePlatelet" />
	      </msh:row>
	      <msh:row>
	        <msh:textField label="Иммуноглобулин 10% всего" property="immunoglobulin10" />
	      </msh:row>
	      <msh:row>
	        <msh:textField label="Антистафилок глобулин всего" property="antiStaphylococcalGlobulin" />
	      </msh:row>
	      <msh:row>
	        <msh:textField label="Иммуноглобулин антистафилок всего" property="immunoglobulinStaphylococcal" />
	      </msh:row>
	      <msh:row>
	        <msh:textField label="Кpовозаменители всего" property="bloodSubstitutes" />
	      </msh:row>
	    </msh:panel>
        <msh:submitCancelButtonsRow colSpan="" guid="6bece8ec-9b93-4faf-b729-851f1447d54f" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string"> 
    <ecom:titleTrail mainMenu="Patient" beginForm="stac_sloForm" guid="638ddd30-b48e-4058-b3ad-866c0c70ee1f" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="stac_bloodForm">
      <msh:sideMenu title="СCО" guid="03e79b31-7afd-4187-b53c-b3539d313ce2">
        <msh:sideLink key="" params="id" action="/entityParentEdit-stac_blood" name="Изменить " title="Изменить информацию о переливании крови" roles="/Policy/Mis/MedCase/Stac/Ssl/Blood/Edit" guid="e7826847-dfe9-45cc-9f2c-cf2637f2d423" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoParentView-stac_blood" name="Удалить" roles="/Policy/Mis/MedCase/Stac/Ssl/Blood/Delete" guid="79e30880-7a1a-4a4b-9def-ab27702f74e3" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

