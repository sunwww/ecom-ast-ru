<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

<tiles:put name='title' type='string'>
	<ecom:titleTrail beginForm="mis_lpuForm" mainMenu="Lpu" title="Список исп. оборудования" />
        </tiles:put>

    <tiles:put name='side' type='string'>
    <msh:sideLink key="ALT+1" params="id" action="/entityView-mis_lpu" name="⇧ К ЛПУ"/>
    <msh:sideLink key="ALT+1" params="id" action="/entityParentList-mis_medicalEquipment" name="  К списку оборудования"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
  <table>
  <msh:row>
  <msh:autoComplete property="lpu" vocName="lpu" label="Отделение" size="50"/>
  </msh:row>
  <msh:row>
  <msh:autoComplete property="equipmentType" vocName="vocEquipmentTypeByLpu" label="Тип оборудования" parentAutocomplete="lpu" size="50"/>
  </msh:row>
  <msh:row>
  <msh:autoComplete property="equip" vocName="allMedicalEquipment" label="Мед. оборудование" size="50"/>
  </msh:row>
  
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
    lpuAutocomplete.addOnChangeCallback(function(){checkAutocompletes();});
    equipmentTypeAutocomplete.addOnChangeCallback(function(){createEquipmentAutocomplete();});
 
    function checkAutocompletes() {
    	createEquipmentTypeAutocomplete();
    }
    function createEquipmentAutocomplete (){
    	
    	var url = 'simpleVocAutocomplete/allMedicalEquipment';
    	var parentId="";
    	if ($('lpu').value!=null&&+$('lpu').value>0) {
    		if ($('equipmentType').value!=null&&+$('equipmentType').value>0) {
    			url="simpleVocAutocomplete/medicalEquipmentByLpuAndType";
    			parentId=""+$('lpu').value+'#'+$('equipmentType').value;
    		} else {
    			url="simpleVocAutocomplete/medicalEquipmentByLpu";
    			parentId=""+$('lpu').value;
    		}
    	} else {
    		if ($('equipmentType').value!=null&&+$('equipmentType').value>0) {
    			url="simpleVocAutocomplete/medicalEquipmentByType";
    			parentId=""+$('equipmentType').value;
    		}
    	}
    	if (parentId!="") {
    		equipAutocomplete.setParentId(parentId);
    	}
    	equipAutocomplete.setUrl(url) ;
    	 
    	
    }	
    function createEquipmentTypeAutocomplete() {
    	
    	if ($('lpu').value!=null&&+$('lpu').value>0) {
    		equipmentTypeAutocomplete.setUrl("simpleVocAutocomplete/vocEquipmentTypeByLpu");
    		equipmentTypeAutocomplete.setParentId(""+$('lpu').value);
    		
    	} else {
    		equipmentTypeAutocomplete.setUrl("simpleVocAutocomplete/vocEquipmentType");
    	}
    	createEquipmentAutocomplete ();
    }
    
    onload=function () {
    	 checkAutocompletes() ;	
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