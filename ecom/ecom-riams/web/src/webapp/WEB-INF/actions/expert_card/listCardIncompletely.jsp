<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Expert">Список экспертных карт не полностью заполненных</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <tags:expert_menu currentAction="expert_cardIncompletely"/>
  </tiles:put>
  <tiles:put name="body" type="string">
  	<msh:ifInRole roles="/Policy/Mis/Journal/QualityEstimationCard/CardIncompletely/Expert">
	  	<msh:section title="Нет данных об оценках эксперта по экспертных картам">
  			<ecom:webQuery name="Expert" nativeSql="select 'Expert:'||d.id as id,d.name as dname,count(*) as cnt from QualityEstimationCard card
left join QualityEstimation qe on qe.card_id=card.id and qe.expertType='Expert'
left join mislpu d on d.id=card.department_id
where qe.id is null
group by card.department_id,d.id,d.name"/>
  			<msh:table name="Expert" action="js-expert_card-listIncompletelyByDepartment.do" idField="1">
  				<msh:tableColumn property="sn" columnName="#"/>
  				<msh:tableColumn property="2" columnName="Отделение"/>
  				<msh:tableColumn property="3" columnName="Кол-во карт"/>
  			</msh:table>
	  	</msh:section>
  	</msh:ifInRole>
  	<msh:ifInRole roles="/Policy/Mis/Journal/QualityEstimationCard/CardIncompletely/BranchManager">
  		<msh:section title="Нет данных об оценках заведующего по экспертных картам">
  		  	<ecom:webQuery name="BranchManager" nativeSql="select 'BranchManager:'||d.id as id,d.name as dname ,count(*) as cnt from QualityEstimationCard card
left join QualityEstimation qe on qe.card_id=card.id and qe.expertType='BranchManager'
left join mislpu d on d.id=card.department_id
where qe.id is null
group by card.department_id,d.id,d.name"/>
  			<msh:table name="BranchManager" action="js-expert_card-listIncompletelyByDepartment.do" idField="1">
  				<msh:tableColumn property="sn" columnName="#"/>
  				<msh:tableColumn property="2" columnName="Отделение"/>
  				<msh:tableColumn property="3" columnName="Кол-во карт"/>
  			</msh:table>
  		</msh:section>
  	</msh:ifInRole>
  	<msh:ifInRole roles="/Policy/Mis/Journal/QualityEstimationCard/CardIncompletely/Coeur">
  		<msh:section title="Нет данных об оценках КЭРа по экспертных картам">
  		  	<ecom:webQuery name="Coeur" nativeSql="select 'Coeur:'||d.id as did,d.name as dname,count(*) as cnt from QualityEstimationCard card
left join QualityEstimation qe on qe.card_id=card.id and qe.expertType='Coeur'
left join mislpu d on d.id=card.department_id
where qe.id is null
group by card.department_id,d.id,d.name"/>
  			<msh:table name="Coeur" action="js-expert_card-listIncompletelyByDepartment.do" idField="1">
  				<msh:tableColumn property="sn" columnName="#"/>
  				<msh:tableColumn property="2" columnName="Отделение"/>
  				<msh:tableColumn property="3" columnName="Кол-во карт"/>
   			</msh:table>
  		</msh:section>
  	</msh:ifInRole>
  </tiles:put>
</tiles:insert>