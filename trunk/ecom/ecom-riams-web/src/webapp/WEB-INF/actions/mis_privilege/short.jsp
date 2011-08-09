<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true">

     <tiles:put name='body' type='string'>
		<!-- Льгота -->
        <msh:form action="entityParentSaveGoParentView-mis_privilege.do" defaultField="beginDate">
            <msh:hidden property="id"/>
            <msh:hidden property="saveType"/>
            <msh:hidden property="person"/>

             <msh:panel>

                 <msh:row>
                    <msh:textField property="beginDate" label="Дата включения" horizontalFill="false" />
                    <msh:textField property="endDate" label="Дата исключения" horizontalFill="false" />
                </msh:row>
                <msh:row>
                	<msh:checkBox property="active" label="Активность"/>
                </msh:row>
                <msh:row>
                	<msh:autoComplete property="category" label="Категория льготника" horizontalFill="true" fieldColSpan="3" vocName="vocPrivilegeCategory"/>
                </msh:row>
                <msh:row>
                	<msh:autoComplete property='privilegeCode' label='Код льготы' horizontalFill="true" fieldColSpan="3" vocName="vocPrivilegeCode"/>
                </msh:row>
                <msh:row>
                	<msh:autoComplete property='idc10' label='Диагноз' horizontalFill="true" fieldColSpan="3" vocName="vocIdc10"/>
                </msh:row>
                <msh:separator label="Льготный документ" colSpan="4"></msh:separator>

                <msh:row>
                    <msh:textField property="documentNumber" label="Номер" horizontalFill="false" size="20"/>
                    <msh:textField property="documentIssue" label="Дата выдачи"/>
                </msh:row>
                
          </msh:panel>
        </msh:form>

  </tiles:put>
</tiles:insert>