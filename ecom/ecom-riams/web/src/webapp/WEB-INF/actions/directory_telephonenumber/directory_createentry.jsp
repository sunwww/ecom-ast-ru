<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="ru.ecom.web.login.LoginInfo"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom"%>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp"
	flush="true">
	<tiles:put name="body" type="string">
		<msh:panel guid="panel">
		<div class="content">
			<msh:row>
				<msh:row>
					<msh:autoComplete vocName="vocBuildingShort" property="building" label="Корпус" fieldColSpan="4" size="60" />
				</msh:row>
				<msh:row>
					<msh:autoComplete vocName="vocBuildingLevelShort" property="buildingLevel" label="Этаж" fieldColSpan="4" size="60" />
				</msh:row>
				<msh:row>
					<msh:autoComplete vocName="vocDepartmet" property="department" label="Отделение" fieldColSpan="4" size="60" />
				</msh:row>
				<msh:row>
					<msh:autoComplete vocName="vocPersonShort" property="person" label="Персона" fieldColSpan="4" size="60" />
				</msh:row>
				<msh:row>
					<msh:textField property="comment" label="Наименование" horizontalFill="true" />
				</msh:row>
				<msh:row>
					<msh:textField property="internalNumber" label="Внутренний номер" horizontalFill="true" />
				</msh:row>
				<td><input id="test" type="submit" value="Найти" /></td>
			</msh:row>
			<div class="number">
			
			</div>
			</div>
			
			<div class="numbers">
			</div>
		</msh:panel>
	</tiles:put>
	
	<tiles:put name="javascript" type="string">
		<script type="text/javascript">
		
		$('#test').click(function(){
            alert('123');//forwardWithotClick(aLink);
        });
		</script>
	</tiles:put>
</tiles:insert>