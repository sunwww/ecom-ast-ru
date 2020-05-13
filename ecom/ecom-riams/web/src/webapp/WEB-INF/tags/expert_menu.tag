<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@  attribute name="currentAction" required="true" description="Текущее меню" %>
<tags:style_currentMenu currentAction="${currentAction }"/>
	<msh:sideMenu></msh:sideMenu>
        <msh:sideMenu title="Экспертная карта" >
    	<msh:sideLink action="/js-expert_card-list.do" name="Список экспертных карт" key="ALT+9" roles="/Policy/Mis/MedCase/QualityEstimationCard/View" title="Список экспертных карт" styleId="expert_card"/>
    </msh:sideMenu>
    <msh:sideMenu title="Дополнительно">
    	<msh:sideLink roles="/Policy/Mis/Journal/QualityEstimationCard/CardIncompletely/View" styleId="expert_cardIncompletely" action="/js-expert_card-listIncompletely.do" name="Список экспертных карт неполностью заполненных"/>
    </msh:sideMenu>
