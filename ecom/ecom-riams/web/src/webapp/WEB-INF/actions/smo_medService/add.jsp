<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <form action="javascript:void(0)">
      <input type="hidden" name="id" id="id"/>
      <input type="hidden" name="medCase" id="medCase"/>
      <input type="hidden" name="medServices" id="medServices"/>
      <msh:panel>
        <msh:row>
          <msh:autoComplete vocName="vocServiceStream" property="serviceStream" label="Вид оплаты" horizontalFill="true" fieldColSpan="7"/>
        </msh:row>
       <msh:row>
       <msh:separator label="Мед.услуги" colSpan="7"/>
       </msh:row>
        <msh:row>
		   	<msh:autoComplete viewAction="entityView-mis_medService.do" label="Мед. услуги" property="medService" vocName="medService" fieldColSpan="6" horizontalFill="true"/>
          	<td><input type="button" value="..." onclick="showServiceChangeServiceFind(0,'MedService','MedService')"></td>
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="workFunction" property="workFunctionExecute" label="Специалист" fieldColSpan="7"  horizontalFill="true"  />
        </msh:row>
        <msh:row>
	   	<msh:autoComplete label="МКБ" property="idc10" vocName="vocIdc10" size="45"/>
        	<msh:textField property="dateStart" label="Дата" size="10"/>
            <msh:textField property="medServiceAmount" label="Кол-во" size="2" />
        	<td><input type="button" value="+ услугу" onclick="addService()"/></td>
        </msh:row>
        <table id="otherMedServices" border="1px solid">
        	
        </table>
         <input type="button" value="Сохранить" onclick="this.value='Сохранение изменений ...'; this.disabled=true; saveService()" >
        
      </msh:panel>
    </form>
    
    	<tags:service_change name="ServiceChange" autoCompliteServiceFind="medService"></tags:service_change>
    
  </tiles:put>
  <tiles:put name="side" type="string">
  </tiles:put>
  <tiles:put name="title" type="string">
  	<ecom:titleTrail mainMenu="Patient" beginForm="mis_medCaseForm" title="Добавление услуг"/>
  </tiles:put>
  <tiles:put name="javascript" type="string">
    <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js"></script>
    
      	<script type="text/javascript">

      	onload=function(){
      		$('id').value='${param.id}' ;
      		new dateutil.DateField($('dateStart')) ;
      		if (+$('medServiceAmount').value<1) $('medServiceAmount').value="1";
      		HospitalMedCaseService.getServiceStreamAndMkbByMedCase('${param.id}',
     				 {
               callback: function(aResult) {
                   aResult = JSON.parse(aResult)
            	   $('serviceStream').value=aResult.serviceStreamId;
            	   $('serviceStreamName').value=aResult.serviceStreamName;
            	   $('serviceStreamName').disabled=true ;

            	   $('idc10').value=aResult.mkbId ;
            	   $('idc10Name').value=aResult.mkbName ;
            	   if (aResult.medcaseType==='DepartmentMedCase' ||aResult.medcaseType==='HospitalMedCase' ) {
                       medServiceAutocomplete.setUrl('simpleVocAutocomplete/medServiceForStac');
                       medServiceAutocomplete.setParentId(aResult.serviceStreamId);
                   } else {
            	       //TODO разделить на ОМС/платно. скорее всего это не используется.
                       alert('Ошибка переполнения пневмобуфферов! Инициализация не пройдена');
                       //Если где-то используется, мы об этом узнаем
                       medServiceAutocomplete.setUrl('simpleVocAutocomplete/medService');
            	       /*if (aResult.serviceStreamCode=='OBLIGATORY' || aResult.serviceStreamCode=='BUDGET') {

                       } else {

                       }*/
                   }

               
      		
      		HospitalMedCaseService.getServiceByMedCase('${param.id}',
      				 {
                callback: function(aResult) {
                	$('medServices').value=aResult;
                   
               		if ($('medServices').value!=null&&$('medServices').value!='') {
                        var addRowF="";
                        var ind_f=0 ;
                  		for (var i=0;i<theFld.length;i++) {
                  			addRowF+="ar["+(ind_f++)+"],"
                  			if (theFld[i][2]==1) {
                  				addRowF+="ar["+(ind_f++)+"],"
                  			}
                  		}
                		addRowF=addRow.length>0?addRowF.trim().substring(0,addRowF.length-1):"";
                        addRowF="addRow ("+addRowF+");"
                        
                  		var arr = $('medServices').value.split("#@#");
                  		for (var i=0;i<arr.length;i++) {
                  			var ar=arr[i].split("@#@") ;
                              eval(addRowF) ;
                  		}
                  	}
                    
                }
	        	}
      		);
               }});
        }
      	function saveService() {
      		HospitalMedCaseService.saveServiceByMedCase($('id').value
      				,$('medServices').value,{
    			callback: function(aResult) {
    				window.location.href = "entitySubclassView-mis_medCase.do?id=${param.id}" ;
    			}
      		});
			
      	}
        function addService(check) {
            var addRowF="";
      		for (var i=0;i<theFld.length;i++) {
      			var fld_i = theFld[i] ;
  				eval("var "+fld_i[1]+"=$('"+fld_i[5]+"').value;");
      			var fld_i = theFld[i] ;addRowF+=fld_i[1]+","
      			if (fld_i[2]==1) {
      				eval("var "+fld_i[1]+"Name=$('"+fld_i[5]+"Name').value;");
          			addRowF+=fld_i[1]+"Name," ;
      			}
      		}
    		addRowF=addRow.length>0?addRowF.trim().substring(0,addRowF.length-1):"";
            addRowF="addRow ("+addRowF+");"
      		var isCheckReq=true ;
            if (isCheckReq) {
            	var servs = document.getElementById('otherMedServices').childNodes;
                  var l = servs.length;
                  for (var i=1; i<l;i++) {
                	  var isCheckDouble = false;
                	 if (isCheckDouble) {
                 		 if (+check!=1) {
                 			 if (confirm("Такая услуга уже зарегистрирована. Вы хотите ее заменить?")) {
                 			var node=servs[i];node.parentNode.removeChild(node);
                 		 } else {
                  			return;
                  		 } 
                 		} else {return;}
                    }                 
                 }
            		for (var i=0;i<theFld.length;i++) {
            		}
            }
            eval(addRowF) ;
      		for (var i=0;i<theFld.length;i++) {
      			var fld_i = theFld[i] ;
      			if (fld_i[6]==1) {
	  				eval("$('"+fld_i[5]+"').value='';");
	      			if (fld_i[2]==1) {
	      				eval("$('"+fld_i[5]+"Name').value='';");
	      			}
      			}
      		}
         }
        
      	function createOtherMedServices() {
      		var servs = document.getElementById('otherMedServices').childNodes;
      		var str = ""; $('medServices').value='';
      		for (var i=1;i<servs.length;i++) {
      			for (var ii=0;ii<theFld.length;ii++) {
      			str+=servs[i].childNodes[0].childNodes[theFld[ii][3]].value+"@#@";
      			}
      			str=str.length>0?str.trim().substring(0,str.length-3):"";
      			str+="#@#" ;
      		}
      		str=str.length>0?str.trim().substring(0,str.length-3):"";
      		$('medServices').value=str;
      	}
      	// 0. наименование 1. Наим. поля в функции 2. autocomplete-1,textFld-2 ,dateFld-3
      	// 3. Номер node в добавленной услуге 4. Обяз.поля да-1 нет-2 
      	// 5. наим. поля в форме 6. очищать поле в форме при добавление да-1, нет-0 
  		var theFld = [['Услуга','Service',1,1,1,'medService',0],['Специалист','WF',1,3,1,'workFunctionExecute',0]
			,['МКБ','Idc',1,5,1,'idc10',0],['Дата','Date',3,7,1,'dateStart',1],['Кол-во','Amount',2,9,1,'medServiceAmount',0]
  			,['Поток обслуживания','ServiceStream',1,11,1,'serviceStream',0]] ;
      	var fldIndexRow = 0;
      	function addRow (aService,aServiceName,aWF,aWFName,aIdc,aIdcName,aDate,aAmount,aServiceStream,aServiceStreamName) {
      		var table = document.getElementById('otherMedServices');
      		var row = document.createElement('TR');
      		var td = document.createElement('TD');
      		var tdDel = document.createElement('TD');
      		table.appendChild(row);
      		row.appendChild(td);
      		var txt ="" ;
      		fldIndexRow++ ;
      		var js="" ;
      		for (var i=0;i<theFld.length;i++) {
      			var fld_i = theFld[i] ;
      			if (fld_i[2]==1) {
      				txt+=" "+fld_i[0]+": "+eval("a"+fld_i[1]+"Name")+" <input type='hidden' value='"+eval("a"+fld_i[1])+"'>"
      			} else if (fld_i[2]==2) {
      				txt+=" "+fld_i[0]+": <input type='text' value='"+eval("a"+fld_i[1])+"'>"
      			} else if (fld_i[2]==3) {
      				txt+=" "+fld_i[0]+": <input type='text' name='"+fld_i[1]+fldIndexRow+"' id='"+fld_i[1]+fldIndexRow+"' value='"+eval("a"+fld_i[1])+"'>"
      				js += "new dateutil.DateField($('"+fld_i[1]+fldIndexRow+"')) ;"
      			}
      		}
      		td.innerHTML=txt ;
      		row.appendChild(tdDel);
      		eval(js) ;
      		tdDel.innerHTML = "<input type='button' name='subm' onclick='var node=this.parentNode.parentNode;node.parentNode.removeChild(node);createOtherMedServices()' value='- услугу' />";
      		createOtherMedServices();
      	}
</script>

</tiles:put>
</tiles:insert>