<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
        	<msh:sideLink action="/messagesOther.do" params="id" name="Фильтры ОМС"/>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string' >
           <ecom:webQuery name='test' 
             hql='select m.id, m.check.name from Message m where importTime=${param.importTime} and m.check.id=${param.id}' />
	           <msh:table name="test" action="exp_messageView.do" idField="1"> 
	           	<msh:tableColumn columnName="№" property="sn"/>
	           	<msh:tableColumn columnName="Вид проверки" property="2"/>
           </msh:table>  
    </tiles:put>


</tiles:insert>