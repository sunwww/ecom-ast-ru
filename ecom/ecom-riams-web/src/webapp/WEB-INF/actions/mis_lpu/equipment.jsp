<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

<tiles:put name='title' type='string'>
	<ecom:titleTrail beginForm="mis_lpuForm" mainMenu="Lpu" title="Список исп. оборудования" guid="e51b1bad-82ba-4906-9829-7d9148b1174a" />
        </tiles:put>

    <tiles:put name='side' type='string'>
    <msh:sideLink key="ALT+1" params="id" action="/entityView-mis_lpu" name="⇧ К ЛПУ"/>
    <msh:sideLink key="ALT+1" params="id" action="/entityParentList-mis_medicalEquipment" name="  К списку оборудования"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
  <table><tr>
  <td title='Мед. оборудование' class='label' colspan='1' size='10'><label id='equipLabel' for='equipName'>Мед. оборудование:</label></td>
	<td colspan='2' class='equip'><div><input size='1' name='equip' value=''
		id='equip' type='hidden'><input autocomplete='off' title='equip' name='equipName' value='' id='equipName' size='40' class='autocomplete horizontalFill' type='text'>
		<div style='visibility: hidden; display: none;' id='equipDiv'></div></div></td>
		</tr>
		<tr><td>
  <input type="button" name="subm" onclick="createEquipment($('equip').value);" value="Добавить" tabindex="4">
  </td></tr>
  </table>
   <table id="equipTable">
   
        <tbody id="addequipElements">
        </tbody>
    </table>
    
  </tiles:put>
   <tiles:put type="string" name="javascript">
	    <script type="text/javascript" src="./dwr/interface/LpuService.js"></script>
    <script type="text/javascript">
    function createAutocomplete (){
    	var listEquip = new msh_autocomplete.Autocomplete() ;
    	 listEquip.setUrl('simpleVocAutocomplete/allMedicalEquipment') ;
    	 listEquip.setIdFieldId('equip');
    	 listEquip.setNameFieldId('equipName') ;
    	 listEquip.setDivId('equipDiv') ;
    	 listEquip.build() ;
    }	
    
    onload=function () {
    	createAutocomplete();	
    	LpuService.getOtherEquipmentByLpu(${param.id},{
    			callback: function(aResult) {
					if (aResult!=null&&aResult!='') {
						var arr = aResult.split("@");
						for (var i=0; i<arr.length;i++) {
							addRow(arr[i]);
						}
					}
				}
    		});
    		
		}
    	function createEquipment(aId) {
    		LpuService.createOtherEquipment(${param.id}, aId);
    		addRow(aId+":"+$('equipName').value);
    	}
    	function removeEquipment(aId) {
    		if (confirm('Удалить оборудование из отделения?')) {
    			LpuService.removeOtherEquipment(${param.id}, aId);
    		}
    	}
    	
    	function addRow(arrRow) {
    		var aRow = arrRow.split(":");
    		var tbody = document.getElementById('addequipElements');
    	    var row = document.createElement("TR");
    		tbody.appendChild(row);
    	    var td1 = document.createElement("TD");
    	    var td2 = document.createElement("TD");
    	    row.appendChild(td1);
   		 	row.appendChild(td2);
   		 	td1.innerHTML="<span>"+aRow[1]+"</span>";
   			td2.innerHTML = "<input type='button' name='subm' onclick='javascript:removeEquipment("+aRow[0]+");var node=this.parentNode.parentNode;node.parentNode.removeChild(node);' value='Удалить' />";
    	}
    	</script>
    </tiles:put>
</tiles:insert>