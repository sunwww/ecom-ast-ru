<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@  attribute name="currentAction" required="true" description="Текущее меню" %>
<tags:style_currentMenu currentAction="${currentAction }"/>
	<msh:sideMenu></msh:sideMenu>
        <msh:sideMenu guid="c8896942-33fb-4800-a00b-61d7750e8100" title="Экспертная карта" >
    	<msh:sideLink action="/js-expert_card-list.do" name="Список экспертных карт" key="ALT+9" roles="/Policy/Mis/MedCase/QualityEstimationCard/View" title="Список экспертных карт" styleId="expert_card"/>
    </msh:sideMenu>
    <msh:sideMenu title="Дополнительно" guid="41e922d8-a2f8-4b4a-be9a-20d7a8abb4f4">
    	<msh:sideLink roles="/Policy/Mis/Journal/QualityEstimationCard/CardIncompletely/View" styleId="expert_cardIncompletely" action="/js-expert_card-listIncompletely.do" name="Список экспертных карт неполностью заполненных"/>
    </msh:sideMenu>
