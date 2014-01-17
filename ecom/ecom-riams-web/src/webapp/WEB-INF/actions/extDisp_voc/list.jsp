<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<msh:title mainMenu="Voc" title="Виды доп. диспансеризации"/>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/ExtDisp/Card/Voc/Create" action="/entityPrepareCreate-extDisp_voc" title="Добавить вид диспансеризации" name="Вид диспансеризации" />
		</msh:sideMenu>
	</tiles:put>
	<tiles:put name='body' type='string' >
	<msh:section createRoles="/Policy/Mis/ExtDisp/Card/Voc/Create" 
		createUrl="entityPrepareCreate-extDisp_voc.do"
		title="Список видов доп.диспансеризации">
		<ecom:webQuery name="list" nativeSql="
		select ved.id, ved.code, ved.name
		from VocExtDisp ved
		"/>
	  	 <msh:tableNotEmpty name="list">
		  	<msh:toolbar >
          		<table>
               	<tbody>
               		<msh:row>
                		<th class='linkButtons' colspan="2">
                			<ul>
                				<li><a href="javascript:void(0)" onclick="javascript:exportVED()">Экспорт</a></li>
              					 <li> Импорт
        					         <msh:form action="vocExtDisp_import.do"  defaultField="isClear" fileTransferSupports="true">
			            <msh:hidden property="saveType"/>
			
			            <msh:panel>
			                <msh:row>
			                	<msh:checkBox property="isClear" label="Удалять существующие настройки по плану?"/>
			                </msh:row>
			                <msh:row>
			                    <td>Файл *.xml</td>
			                    <td colspan="1">
			                        <input type="file" name="file" id="file" size="50" value="" onchange="">
			                        <input type="button" name="run_import" value="Импорт"  onclick="this.form.submit()" />
			                    </td>
			                </msh:row>
			                	<msh:row>
			                	<td colspan="4" align="center">
			                		
			                	</td>
			                	</msh:row>
			                	
			            </msh:panel>
			        </msh:form>
</li>
              				</ul>
              			</th>				                		
               		</msh:row>
               	</tbody>
             		</table>
             		</msh:toolbar>
	  	</msh:tableNotEmpty>
		<msh:table name="list" action="entityView-extDisp_voc.do" idField="1" selection="true">
			<msh:tableColumn columnName="#" property="sn" />
			<msh:tableColumn columnName="ИД" property="1" />
			<msh:tableColumn columnName="Код" property="2" />
			<msh:tableColumn columnName="Наименование" property="3" />
		</msh:table>
	</msh:section>
</tiles:put>
<tiles:put name="javascript" type="string">
<script type="text/javascript">
	function exportVED() {
		var ids = theTableArrow.getInsertedIdsAsParams("id","list") ;
		if (ids) {
			window.open('vocExtDisp_export.do?'+ids,'..','..');
        } else {
             alert("Нет выделенных видов диспансеризации");
        }  			
		
	}
	function exportVED() {
		var ids = theTableArrow.getInsertedIdsAsParams("id","list") ;
		if (ids) {
			window.open('vocExtDisp_export.do?'+ids,'..','..');
        } else {
             alert("Нет выделенных видов диспансеризации");
        }  			
		
	}
</script>
</tiles:put>
</tiles:insert>
