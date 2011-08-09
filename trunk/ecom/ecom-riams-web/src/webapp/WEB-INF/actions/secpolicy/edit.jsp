<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >



    <tiles:put name='body' type='string' >
        <msh:form action="entityParentSaveGoView-secpolicy.do" defaultField="key">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:hidden property="parentSecPolicy" />

            <msh:panel>
            <msh:ifInRole roles="/Policy/Jaas/SecPolicy/EditParent">
                <msh:row>
                	<msh:autoComplete property="parentSecPolicy" vocName="SecPolicy" label="Родитель" horizontalFill="true" fieldColSpan="3"/>
                </msh:row>
            </msh:ifInRole>
                <msh:row>
                    <msh:textField property="key" label="Ключ" fieldColSpan="4" horizontalFill="true"/>
                </msh:row>
                <msh:ifFormTypeIsNotView formName="secpolicyForm">
                    <msh:row>
                        <msh:textField property="name" label="Название" fieldColSpan="4" horizontalFill="true"/>
                    </msh:row>
                </msh:ifFormTypeIsNotView>
                <msh:row>
                    <msh:textArea property="comment" label="Комментарий" fieldColSpan="4" horizontalFill="true"/>
                </msh:row>
                <msh:ifFormTypeIsNotView formName="secpolicyForm">
	                <msh:row>
		                <ecom:oneToManyOneAutocomplete label="Роли" property="roleList" vocName="role" colSpan="4"/>
	                </msh:row>
                </msh:ifFormTypeIsNotView>

                <msh:submitCancelButtonsRow colSpan="4" />
            </msh:panel>
        </msh:form>

        <msh:ifFormTypeIsView formName="secpolicyForm">

            <ecom:parentEntityListAll formName="secpolicyForm" attribute="policies" />
            <msh:tableNotEmpty name="policies">
              <msh:section title="Список вложенных политик">
                <msh:table name="policies" action="entityParentView-secpolicy.do" idField="id">
                    <msh:tableColumn columnName="Ключ" property="key"  guid="dsadad"/>
                    <msh:tableColumn columnName="Название" property="name"  guid="fdsfsf"/>
                    <msh:tableColumn columnName="Комментарий" property="comment"  guid="fdsgggs"/>
                </msh:table>
              </msh:section>
            </msh:tableNotEmpty>

        	<msh:ifInRole roles="/Policy/Jaas/SecRole/View">
	        	<ecom:webQuery name="roles" nativeSql="select sr.id,sr.name,sr.comment,sr.key from SecRole_SecPolicy as rp left join SecRole as sr on sr.id=rp.secRole_id where rp.secPolicies_id='${param.id}'"/>
	        	<msh:tableNotEmpty name="roles">
	        		<msh:section title="Список ролей, в которых есть данная политика">
	        		<msh:table name="roles" action="roleView.do" idField="1">
	        			<msh:tableColumn columnName="№" property="sn"/>
	        			<msh:tableColumn columnName="Название" property="2"/>
	        			<msh:tableColumn columnName="Ключ" property="4"/>
	        			<msh:tableColumn columnName="Комментарий" property="3"/>
	        		</msh:table>
	        		</msh:section>
	        	</msh:tableNotEmpty>
        	</msh:ifInRole>
            
        </msh:ifFormTypeIsView>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>

            <msh:sideLink key='ALT+N' params="id" action="/entityParentPrepareCreate-secpolicy" name="Создать новую политику" />
	      <msh:sideLink key='ALT+1' action="/javascript:createStandartPolicy('.do')" name="Создать стандартные политики" 
	      	roles="/Policy/Jaas/SecPolicy/Edit"/>
	      	<tags:sec_policyFull roles="/Policy/Jaas/SecPolicy/Edit"
	      	name='addPol' title='Добавить/Перейти к политике' key="ALT+9"/>
            <%--<msh:sideLink key="ALT+1" params="id" action="/entityParentListRedirect-secrole" name="⇧ Список политик" />--%>
            <msh:sideLink key='ALT+2' params="id" action="/entityParentEdit-secpolicy" name="Изменить" 
            	roles="/Policy/Jaas/SecPolicy/Edit"/>
            <msh:sideLink params="id" action="/entityParentDelete-secpolicy" key="ALT+DEL" name="Удалить" confirm="Удалить политику?"
            	roles="/Policy/Jaas/SecPolicy/Delete" />

        </msh:sideMenu>
        <msh:sideMenu title="Администрирование">
        	<msh:sideLink action="/servicePolicy-exportEdit.do" roles="/Policy/Jaas/SecPolicy/Export" name="Экспорт списка политик"/>
			<msh:sideLink action="/servicePolicy-importEdit.do" roles="/Policy/Jaas/SecPolicy/Import" name="Импорт списка политик"/>
        </msh:sideMenu>
        <tags:menuJaas currentAction="policies"/>
    </tiles:put>

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Policies" beginForm="secpolicyForm" />
    </tiles:put>

    <tiles:put name="javascript" type="string">
	    <script type="text/javascript" src="./dwr/interface/RolePoliciesService.js" ></script>
        <script type="text/javascript">
            Element.addClassName($('mainMenuPolicies'), "selected") ;
            
            function createStandartPolicy() {
				if (+"${param.id}"<1 ) {
		     	} else {
		     		
				    RolePoliciesService.standartPolicy(
			     		'${param.id}' , {
			                   callback: function(aString) {
			                       //alert(aString) ;
			                       window.document.location.reload()  ;
			                    }
			                
			         }) ;
		         }            	
            }
            
        </script>
    </tiles:put>

</tiles:insert>