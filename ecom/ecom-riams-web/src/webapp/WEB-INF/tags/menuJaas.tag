<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>

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
#side ul li a#deleteDischarge {
	color: red ;
	background-color: silver;
}

</style>
	<msh:sideMenu></msh:sideMenu>
        <msh:sideMenu title="Безопасность">
        	<msh:sideLink styleId="users" action="/entityList-secuser.do" roles="/Policy/Jaas/SecUser/View" name="Пользователи"/>
        	<msh:sideLink styleId="roles" action="/entityList-secrole.do" roles="/Policy/Jaas/SecRole/View" name="Роли"/>
        	<msh:sideLink styleId="policies" action="/entityParentList-secpolicy.do?id=1" roles="/Policy/Jaas/SecPolicy/View" name="Политики"/>
        	<msh:sideLink action="/serviceExport.do" roles="/Policy/Jaas/Activation" name="Активация" title="Активация"/>
        </msh:sideMenu>
