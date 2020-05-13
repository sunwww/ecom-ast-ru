<%@page import="ru.ecom.mis.web.action.synclpufond.HospitalDirectFondImportFromDirAction"%>
<%@page import="ru.ecom.mis.web.action.synclpufond.HospitalDirectInFondImportAction"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Journals" title="Журнал для отправки данных в фонд"/>
  </tiles:put>
  <tiles:put name="side" type="string">
  </tiles:put>
  <tiles:put name="body" type="string">
  <%
  
	String typeView=ActionUtil.updateParameter("HospitalDirectDataInFond","typeView","1", request) ;
	String typeView1=ActionUtil.updateParameter("HospitalDirectDataInFond","typeView1","1", request) ;
	String typeDate=ActionUtil.updateParameter("HospitalDirectDataInFond","typeDate","1", request) ;
	String typeMode=ActionUtil.updateParameter("HospitalDirectDataInFond","typeMode","1", request) ;
	String typeEmergency=ActionUtil.updateParameter("HospitalDirectDataInFond","typeEmergency","3", request) ;
	String typeLpu=ActionUtil.updateParameter("HospitalDirectDataInFond","typeLpu","3", request) ;
	String typeImport=ActionUtil.updateParameter("HospitalDirectDataInFond","typeImport","1", request) ;
	request.setAttribute("typeLoad", "1") ;
	String typeLoad="1" ;
  %>
  
    <msh:form action="/stac_direct_in_fond.do" defaultField="lpu" disableFormDataConfirm="true" method="GET">
    <msh:panel>
   
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
      <msh:row>
      	<msh:textField property="lpu"/>
        <msh:textField property="numberReestr" label="Реестровый номер" />
      </msh:row>
      <msh:row>
        <msh:textField  property="period" label="Период с" />
         <msh:textField  property="periodTo" label="по" /> 
      </msh:row>
      <msh:row>
      	<td class="label" title="Режим  (typeMode)" colspan="1"><label for="typeModeName" id="typeModeLabel">Режим:</label></td>
        <td onclick="this.childNodes[1].checked='checked';checkMode()" colspan="2">
        	<input type="radio" name="typeMode" value="1"> Формирования Xml
        </td>
	        <td onclick="this.childNodes[1].checked='checked';checkMode()" colspan="2">
	        	<input type="radio" name="typeMode" value="2"> Импорт результата
	        </td>
	   </msh:row>
      <msh:row>
      	<td></td>
	        <td onclick="this.childNodes[1].checked='checked';checkMode()" colspan="2">
	        	<input type="radio" name="typeMode" value="3"> Работа с данными
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';checkMode()" colspan="2">
	        	<input type="radio" name="typeMode" value="4"> Поиск по номеру
	        </td>

      </msh:row>
      </msh:panel>
      <msh:panel styleId="updateData">
             <msh:row>
           <td colspan="11">
            <input type="submit" value="Обновить соответствия по направлениям" />
          </td>
          
      </msh:row>
            </msh:panel>
      <msh:panel styleId="exportXml">
      <msh:row styleId="rowLoad">
      	<td class="label" title="Тип xml  (typeLoad)" colspan="1"><label for="typeLoadName" id="typeLoadLabel">Сохранять в папку ДЛЯ VIPNET:</label></td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="1">
        	<input type="radio" name="typeLoad" value="1"> не сохранять (еще нужно просмотреть)
        </td>
      	            </msh:row>
      <msh:row>
        <td></td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeLoad" value="2"> сохранять для отправки
        </td>
	   
      </msh:row>
      <msh:row>
        <td class="label" title="Тип xml  (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Тип xml:</label></td>
       </msh:row>
      <msh:row>
        <td></td>
        <td colspan="10"><b>Отправляется после оформления направления в поликлинике</b></td>
        </msh:row>
      <msh:row>
        <td></td>
          <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="1"> (N1) направления на госп.
        </td>
        
       </msh:row>
      <msh:row>
        <td></td>
        <td colspan="10"><br><b>Отправляется при поступлении в стационар</b></td>
	      </msh:row>
      <msh:row>
        <td></td>
            <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeView" value="2"> (N2) для плановых госпитализаций (по направлению из поликлиник)
	        </td>
		            </msh:row>
      <msh:row>
        <td></td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeView" value="3"> (N3) для экстр. госпитализаций
	        </td>
         </msh:row>
      <msh:row>
        <td></td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="4">
        	<input type="radio" name="typeView" value="4">  (N1) формирование направлений для план. госп. без направлений из тек. ЛПУ и (N2) для план. госп. из других ЛПУ
        </td>
       </msh:row>
      <msh:row>
        <td></td>
        <td colspan="10"><br><b>Формируется для направлений, по которым не осуществлена госпитализация (аннулированных направлений из поликлиники)</b></td>
          </msh:row>
      <msh:row>
        <td></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="5"> (N4) аннулирование направ. на госп.
        </td>
         </msh:row>
      <msh:row>
        <td></td>
        <td colspan="10"><br><b>Отправляется после выписки пациентов из стационара</b></td>
       </msh:row>
      <msh:row>
        <td></td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeView" value="6"> (N2) В РАЗРАБОТКЕ!!!! переводы внутри ЛПУ (отправлять файлы надо по одному строго по очередности!!!!)
	        </td>
       	            </msh:row>
      <msh:row>
        <td></td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeView" value="7"> (N5) выбывшие из стац.
	        </td>
       	            </msh:row>
      <msh:row>
        <td></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="8">  (N1) для переводов в другие стационары
        </td>
        
       </msh:row>
      <msh:row>
        <td></td>
        <td colspan="10"><br><b>Выгрузка свободных мест по ЛПУ</b></td>
       </msh:row>
      <msh:row>
        <td></td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeView" value="9"> (N6) наличие своб. мест
	        </td>
       </msh:row>
      <msh:row>
        <td></td>
        <td colspan="10"><br><b>Выгрузка нескольких файлов</b></td>
       </msh:row>
       <msh:row>
        <td></td>
        
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeView" value="10">  N2 + N3 выгружаются при поступлание для плановых и экстренных госпитализаций
        </td>
       	            </msh:row>
      <msh:row>
        <td></td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeView" value="11">  N4 + N5 выгружаются при выписке пациентов и анулирование направлений
        </td>
       </msh:row>
      <msh:row>
        <td></td>
        <td><br><b>Для выгрузки данных с портала фонда</b></td>
       </msh:row>
      <msh:row>
        <td></td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="4">
        	<input type="radio" name="typeView" value="12"> (N0) для выгрузки N1, неоформленных направлений (в ожидании очереди на госпитализацию)
 	            </msh:row>
      <msh:row>
        <td></td>
        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="4">
	        	<input type="radio" name="typeView" value="13"> (N0) для выгрузки N2, госпитализированных пациентов (еще не выписанных)
	        </td>
	            </msh:row>
      <msh:row>
        <td></td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="4">
	        	<input type="radio" name="typeView" value="14"> (N0) для выгрузки N5, выписанных пациентов
	        </td>
	            </msh:row>
      <msh:row>
        <td></td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="4">
	        	<input type="radio" name="typeView" value="15"> (N0) для выгрузки N8, ВСЕ направления по ЛПУ (выписанные, аннулированные, госпитализированные, неоформленные) (не для загрузки!!!!!!!)
	        </td>
       </msh:row>
       <msh:row>
       	<msh:hidden property="filename"/>
       	<msh:hidden property="filenameError"/>
       	<td colspan="4">
       		Файл <span id='aView'></span>
       	</td>
       </msh:row>
      <msh:row>
           <td colspan="11">
            <input type="submit" value="Сформировать файлы" />
          </td>
      </msh:row>
          </msh:panel>
          <msh:panel styleId="workData">
      <msh:row>
        <td class="label" title="Искать по дате (typeDate)" colspan="1"><label for="typeDateName" id="typeDateLabel">Искать по дате:</label></td>
        <td class='tdradio' onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeDate" value="1"> поступления  
        </td>
        <td class='tdradio' onclick="this.childNodes[1].checked='checked';" colspan="3">
        	<input type="radio" name="typeDate" value="2">  выписки
        </td>
      </msh:row>      
     <msh:row>
        <td class="label" title="Поиск по показаниям поступления (typeEmergency)" colspan="1"><label for="typeEmergencyName" id="typeEmergencyLabel">Показания:</label></td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeEmergency" value="1">  экстренные
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeEmergency" value="2" >  плановые
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeEmergency" value="3">  все
        </td>
      </msh:row>
           <msh:row>
        <td class="label" title="Тип ЛПУ (typeLpu)" colspan="1"><label for="typeLpuName" id="typeLpuLabel">Тип ЛПУ:</label></td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeLpu" value="1">  направленных из текущего ЛПУ
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeLpu" value="2" >  направленных из другого ЛПУ
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeLpu" value="3">  без учета ЛПУ
        </td>
      </msh:row>                
           
           
           <msh:row>
            <td class="label" title="Список  (typeView1)" colspan="1"><label for="typeView1Name" 
               id="typeView1Label">Список:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="4">
	        	<input type="radio" name="typeView1" value="1"> госпитализации без прикрепленных направлений из поликлиники 
	        </td>
	   </msh:row>
	   <msh:row>
	        <td></td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="4">
	        	<input type="radio" name="typeView1" value="2">  направления, по которым не определены госпитализации 
	        </td>
	   </msh:row>
	   <msh:row>
	        <td></td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="4">
	        	<input type="radio" name="typeView1" value="3">  неопред. госп. и направления (отображаются 2 таблицы)
	        </td>
	   </msh:row>
	   <msh:row>
	        <td></td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="4">
	        	<input type="radio" name="typeView1" value="4">  отделения, по которым не проставлен диагноз (ошибка при формировании таблицы (N2) поступивших в стационар)
	        </td>
       	</msh:row>
       	<msh:row>
	        <td></td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="8">
	        	<input type="radio" name="typeView1" value="5">  СНИЛС врача для генерации направлений по плановым госпитализациям без направлений по тек. ЛПУ  
	        </td>
	   </msh:row>
       	<msh:row>
	        <td></td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="8">
	        	<input type="radio" name="typeView1" value="6">  коечный фонд (для формирования табл. свободные койки (N6))
	        </td>
	   </msh:row>
             <msh:row>
           <td colspan="11">
            <input type="submit" value="Отобразить данные" />
          </td>
      </msh:row>
          </msh:panel>
          
          <msh:panel styleId="findByNumber">
             <msh:row>
             	<msh:textField property="numberDirect" size="100" />
             </msh:row>
             <msh:row>
           <td colspan="11">
            <input type="submit" value="Отобразить данные" />
          </td>
      </msh:row>
          </msh:panel>
          
    </msh:form>
    
       <msh:form action="stac_direct_in_fond_import.do"  defaultField="isClear" fileTransferSupports="true">
			            <msh:hidden property="saveType"/>
			 			<msh:panel styleId="importXml">
			 			      <msh:row>
        <td class="label" title="Обновить соответствия по направлениям и госпитализациям (typeImport)" colspan="2"><label for="typeImportName" id="typeImportLabel">Обновление соответствий по напр. и госп.:</label></td>
        <td class='tdradio' onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeImport" value="1"> обновить  
        </td>
        <td class='tdradio' onclick="this.childNodes[1].checked='checked';" colspan="3">
        	<input type="radio" name="typeImport" value="2">  не обновлять
        </td>
      </msh:row>   
	<msh:row>
      <msh:autoComplete property="importFormat" vocName="hospitalDataFondFormats" fieldColSpan="50" size="50"/>
      </msh:row>
			                <msh:row>
			                    <td>Файл *.xml (dbf)</td>
			                    <td colspan="15">
			                        <input type="file" name="file" id="file" size="50" value="" onchange="">
			                        <input type="button" name="run_import" value="Импорт"  onclick="this.form.submit()" />
			                        
			                        <input type="button" id="run_import_dir" name="run_import_dir" value="Импорт из папки"  onclick="this.form.action='stac_direct_in_fond_import_dir.do';this.form.submit()" />
			                    </td>
			                </msh:row>
			                
			                	       <msh:row>
       	<td colspan="4">
       		Файл <span id='aViewImportError'></span>
       	</td>
       </msh:row>

			                	
			            </msh:panel>
			        </msh:form>
	<tags:hosp_263 name="Diag"></tags:hosp_263>
      <% 
      String dir = HospitalDirectFondImportFromDirAction.getOutFolderBy263(request) ;
      if (dir==null) { %>
      <script type="text/javascript">
      $('rowLoad').style.display="none" ;
      </script>
      <% } %>
      <% 
      String dirIn = HospitalDirectFondImportFromDirAction.getInFolderBy263(request) ;
      if (dirIn==null) { %>
      <script type="text/javascript">
      $('rowLoad').style.display="none" ;
      </script>
      <% } %>
      
<script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js">/**/</script>
      <script type="text/javascript">
      checkFieldUpdate('typeLpu','${typeLpu}',1,0) ;
      checkFieldUpdate('typeEmergency','${typeEmergency}',1,0) ;
      checkFieldUpdate('typeView1','${typeView1}',1,0) ;
      checkFieldUpdate('typeView','${typeView}',1,0) ;
      checkFieldUpdate('typeDate','${typeDate}',1,0) ;
      checkFieldUpdate('typeMode','${typeMode}',1,0) ;
      checkFieldUpdate('typeImport','${typeImport}',1,1) ;
      checkFieldUpdate('typeLoad','${typeLoad}',1,0) ;
      
      
      function showHDF(aId) {
    	  getDefinition("entityView-stac_dataFond.do?short=Short&id="+aId, null); 
      }
      function checkFieldUpdate(aField,aValue,aDefaultValue,aFrm) {
    	   	eval('var chk =  document.forms[aFrm].'+aField) ;
    	   	var aMax=chk.length ;
    	   	if ((+aValue)==0 || (+aValue)>(+aMax)) {
    	   		chk[+aDefaultValue-1].checked='checked' ;
    	   	} else {
    	   		chk[+aValue-1].checked='checked' ;
    	   	}
    	   }
     
  $('aView').innerHTML=$('filename').value ;
  $('aViewImportError').innerHTML=$('filenameError').value ;
  
   function checkMode() {
	   var chk =  document.forms[0].typeMode ;
	   if (chk[0].checked) {
		   showTable("exportXml", true ) ; 
		   showTable("importXml", false ) ; 
		   showTable("workData", false ) ; 
		   showTable("updateData", false ) ; 
		   showTable("findByNumber", false ) ; 
		   
	   } else if (chk[1].checked) {
		   showTable("exportXml", false ) ; 
		   showTable("importXml", true ) ; 
		   showTable("workData", false ) ; 
		   showTable("updateData", false ) ; 
		   showTable("findByNumber", false ) ; 
	   } else if (chk[2].checked){
		   showTable("exportXml", false ) ; 
		   showTable("importXml", false ) ; 
		   showTable("workData", true ) ; 
		   showTable("updateData", false ) ; 
		   showTable("findByNumber", false ) ; 
	   } else if (chk[3].checked){
		   showTable("exportXml", false ) ; 
		   showTable("importXml", false ) ; 
		   showTable("workData", false ) ; 
		   showTable("updateData", false ) ; 
		   showTable("findByNumber", true ) ; 
	   } else {
		   showTable("exportXml", false ) ; 
		   showTable("importXml", false ) ; 
		   showTable("workData", false ) ; 
		   showTable("updateData", true ) ; 
		   showTable("findByNumber", false ) ; 
	   }
   }
   function showTable(aTableId, aCanShow ) {
		try {
			$(aTableId).style.display = aCanShow ? 'table' : 'none' ;
		} catch (e) {
			try{
			$(aTableId).style.display = aCanShow ? 'block' : 'none' ;
			}catch(e) {}
		}	
	}
	checkMode() ;
	function setSnilsDoctor(aIdBedFund,aSnils) {
		var idBed = aIdBedFund.split("#") ;
		if (+idBed[0]>0) {} else {
			alert("Не заведен СЛО по госпитализации!!!");
			return "" ;
		}
		var snils = idBed.length>1 ? idBed[1]:aSnils!=null?aSnils:""; 
		snils = prompt("Введите СНИЛС врача:",snils!=null?snils:"");
		if (snils!=null && snils.length==14) {
			HospitalMedCaseService.updateTable('BedFund','id',idBed[0],'snilsDoctorDirect263',snils,'',{
	  			callback: function(aResult) {
	  				window.location.reload() ;
	  			}
	  		}) ;
		} else {
			if (confirm("Вы хотите ввести СНИЛС врача еще раз?")) {
				setSnilsDoctor(aIdBedFund,snils) ;
			}
		}
	}
	function setAmountBed(aIdBedFund,aCnt) {
		var cnt = aCnt!=null?aCnt:""; 
		cnt = prompt("Введите кол-во:",cnt);
		if (cnt!=null && cnt.length>0) {
			HospitalMedCaseService.updateTable('BedFund','id',aIdBedFund,'amount',cnt,'',{
	  			callback: function(aResult) {
	  				window.location.reload() ;
	  			}
	  		}) ;
		} else {
			if (confirm("Вы хотите ввести кол-во еще раз?")) {
				setAmountBed(aIdBedFund,aCnt) ;
			}
		}
	}
	function setDeniedByHDF(aHDFid,aFrm,aDenied) {
		var val =null;
		if (aFrm!=null) {
			var frm=document.forms[aFrm] ;
			val = getCheckedRadio(frm,aDenied) ;
		}
		HospitalMedCaseService.updateTable('HospitalDataFond','id',aHDFid,'deniedHospital',val,'',{
  			callback: function(aResult) {
  				window.location.reload() ;
  			}
  		}) ;
	}
	function setHospByHDF(aHDFid,aSls) {
		HospitalMedCaseService.updateTable('HospitalDataFond','id',aHDFid,'hospitalMedCase_id',aSls,'',{
  			callback: function(aResult) {
  				setDeniedByHDF(aHDFid,null,null) ;
  			}
  		}) ;
	}
	function deleteTableHDFEmpty(aMode) {
		HospitalMedCaseService.deleteTableHDFEmpty(aMode,{
  			callback: function(aResult) {
  				window.location.reload() ;
  			}
  		}) ;
	}
	function deleteHDF(aNapr) {
		HospitalMedCaseService.deleteHDF(aNapr,{
  			callback: function(aResult) {
  				window.location.reload() ;
  			}
  		}) ;
	}
    </script>
	<% 
	String period = request.getParameter("period") ;
	String periodTo = request.getParameter("periodTo") ;
	String numberReestr = request.getParameter("numberReestr") ;
	if (periodTo==null ||periodTo.equals("")) periodTo=period ;
	request.setAttribute("periodTo", periodTo) ;
	boolean isCkeck = true ;
	if (period==null ||period.equals("")) isCkeck=false ;
	if (typeLpu!=null && typeLpu.equals("1")) {
		request.setAttribute("lpuSql", " and hdf.directlpucode='"+numberReestr+"'");
		request.setAttribute("lpuSlsSql", " and lpu.codef='"+numberReestr+"'");
	} else if (typeLpu!=null && typeLpu.equals("2")) {
		request.setAttribute("lpuSql", " and hdf.directlpucode!='"+numberReestr+"'");
		request.setAttribute("lpuSlsSql", " and lpu.codef!='"+numberReestr+"'");
	}
	if (typeEmergency!=null && typeEmergency.equals("1")) {
		request.setAttribute("emergencySql", " and (hdf.formHelp='1' or hdf.formHelp='2')");
		request.setAttribute("emergencySlsSql", " and sls.emergency='1'");
	} else if (typeEmergency!=null && typeEmergency.equals("2")) {
		request.setAttribute("emergencySql", " and (hdf.formHelp='3')");
		request.setAttribute("emergencySlsSql", " and (sls.emergency is null or sls.emergency='0')");
	}
	if (typeMode!=null && typeMode.equals("1")) {
		if (isCkeck && request.getAttribute("listError")!=null){%>
	<msh:table name="listError" action="javascript:void(0)" idField="1">
			<msh:tableColumn property="sn" columnName="#"/>
			<msh:tableColumn property="1" columnName="Дата напр."/>
			<msh:tableColumn property="2" columnName="Форма помощи."/>
			<msh:tableColumn property="3" columnName="Код ЛПУ напр."/>
			<msh:tableColumn property="4" columnName="Код ЛПУ куда напр."/>
			<msh:tableColumn property="5" columnName="Вид полиса"/>
			<msh:tableColumn property="6" columnName="Серия полиса"/>
			<msh:tableColumn property="7" columnName="Номер полиса"/>
			<msh:tableColumn property="8" columnName="СМО"/>
			<msh:tableColumn property="9" columnName="ОГРН"/>
			<msh:tableColumn property="10" columnName="ОКАТО"/>
			<msh:tableColumn property="11" columnName="Фамилия"/>
			<msh:tableColumn property="12" columnName="Имя"/>
			<msh:tableColumn property="13" columnName="Отчество"/>
			<msh:tableColumn property="14" columnName="Пол"/>
			<msh:tableColumn property="15" columnName="Дата рождения"/>
			<msh:tableColumn property="16" columnName="Телефон"/>
			<msh:tableColumn property="17" columnName="Диагноз"/>
			<msh:tableColumn property="18" columnName="Профиль коек"/>
			<msh:tableColumn property="19" columnName="СНИЛС врача"/>
	</msh:table>
	
	<% }
		if (isCkeck && request.getAttribute("listError1")!=null){%>
	<msh:table name="listError1" action="javascript:void(0)" idField="1">
			<msh:tableColumn property="sn" columnName="#"/>
			<msh:tableColumn property="1" columnName="Дата напр."/>
			<msh:tableColumn property="2" columnName="Форма помощи."/>
			<msh:tableColumn property="3" columnName="Код ЛПУ напр."/>
			<msh:tableColumn property="4" columnName="Код ЛПУ куда напр."/>
			<msh:tableColumn property="5" columnName="Вид полиса"/>
			<msh:tableColumn property="6" columnName="Серия полиса"/>
			<msh:tableColumn property="7" columnName="Номер полиса"/>
			<msh:tableColumn property="8" columnName="СМО"/>
			<msh:tableColumn property="9" columnName="ОГРН"/>
			<msh:tableColumn property="10" columnName="ОКАТО"/>
			<msh:tableColumn property="11" columnName="Фамилия"/>
			<msh:tableColumn property="12" columnName="Имя"/>
			<msh:tableColumn property="13" columnName="Отчество"/>
			<msh:tableColumn property="14" columnName="Пол"/>
			<msh:tableColumn property="15" columnName="Дата рождения"/>
			<msh:tableColumn property="16" columnName="Телефон"/>
			<msh:tableColumn property="17" columnName="Диагноз"/>
			<msh:tableColumn property="18" columnName="Профиль коек"/>
			<msh:tableColumn property="19" columnName="СНИЛС врача"/>
	</msh:table>
	
	<% }
	} else if (typeMode!=null && typeMode.equals("4") && isCkeck
			&&request.getParameter("numberDirect")!=null&&!request.getParameter("numberDirect").equals("")) {
		String[] sss = request.getParameter("numberDirect").trim().toUpperCase().split(" ") ;
			request.setAttribute("lastname",sss[0]) ;
 			request.setAttribute("firstname",sss.length>1?sss[1]:"") ;
 			request.setAttribute("middlename",sss.length>2?sss[2]:"") ;

	%>

НАПРАВЛЕНИЕ по номеру:${param.numberDirect}
<ecom:webQuery name="table1" nativeSql="
select hdf.id
,'<'||'span '||case when hdf.deniedHospital is not null then 'style=''background:#01DF74''' else '' end|| '>'
||hdf.numberfond||'<'||'/'||'span'||'>' as f2numberfond
,hdf.statcard as f3
,'<'||'span '||case when hdf.deniedHospital is not null then 'style=''background:#01DF74''' else '' end|| '>'
||hdf.lastname||' '||hdf.firstname||' '||coalesce(hdf.middlename,'')||'<'||'/'||'span'||'>' as f3io

,hdf.birthday,hdf.formHelp
,hdf.profile,hdf.directdate,hdf.hospdate,hdf.hospDischargeDate,hdf.snils as f10snils
,hdf.phone,hdf.diagnosis
,hdf.orderlpucode
,hdf.directlpucode

,hdf.deniedHospital
,hdf.id||''','''||coalesce(''||hdf.deniedHospital,'') as idden
,case when hdf.hospitalmedcase_id is null then hdf.id||''','''||to_char(coalesce(hdf.prehospdate,hdf.hospdate),'dd.mm.yyyy')||''','''||hdf.lastname||''','''||hdf.firstname||''','''||coalesce(hdf.middlename,'')
||''','''||to_char(hdf.birthday,'dd.mm.yyyy')||''',''1'','''||coalesce(hdf.deniedHospital,0) else null end as idforsls

from HospitalDataFond hdf
where (hdf.numberfond='${param.numberDirect}' or upper(hdf.lastname) like '${lastname}%' and upper(hdf.firstname) like '${firstname}%' and upper(hdf.middlename) like '${middlename}%')
and (hdf.directDate between to_date('${param.period}','dd.mm.yyyy') and to_date('${periodTo}','dd.mm.yyyy')
or hdf.hospDate between to_date('${param.period}','dd.mm.yyyy') and to_date('${periodTo}','dd.mm.yyyy')
or hdf.hospDischargeDate between to_date('${param.period}','dd.mm.yyyy') and to_date('${periodTo}','dd.mm.yyyy')
)
order by hdf.lastname,hdf.firstname,hdf.middlename,hdf.id
"/>
<msh:table name="table1" action=" javascript:void(0)" idField="1">
<msh:tableButton property="1" buttonFunction="showHDF" buttonName="Просмотр информации о направление" buttonShortName="Инф."/>
<msh:tableButton property="1" buttonFunction="deleteHDF" buttonName="Удалить направление" buttonShortName="Удалить"/>
<msh:tableButton property="18" hideIfEmpty="true" buttonFunction="showDiag263sls" buttonName="Установить соответствие с неопределенной госпитализацией" buttonShortName="СЛС"/>
<msh:tableColumn property="sn" columnName="#"/>
<msh:tableColumn property="2" columnName="<a href='javascript:alert(\"lpu\")'>Напр. ЛПУ</a>"/>
<msh:tableColumn property="3" columnName="№ИБ"/>
<msh:tableColumn property="4" columnName="<a href='javascript:alert(\"pat\")'>ФИО пациента</a>"/>
<msh:tableColumn property="5" columnName="Дата рождения"/>
<msh:tableColumn property="6" columnName="Показания"/>
<msh:tableColumn property="7" columnName="<a href='javascript:alert(\"prof\")'>Профиль коек</a>"/>
<msh:tableColumn property="8" columnName="Дата напр."/>
<msh:tableColumn property="9" columnName="Дата госп."/>
<msh:tableColumn property="9" columnName="Дата выписки"/>
<msh:tableColumn property="13" columnName="Диагноз"/>
<msh:tableColumn property="14" columnName="ЛПУ напр"/>
<msh:tableColumn property="15" columnName="ЛПУ приним"/>
</msh:table>

<%
	} else if (typeMode!=null && typeMode.equals("3") && isCkeck) {
	
	%>
	<%     if (typeView1!=null && (typeView1.equals("1") || typeView1.equals("2")||typeView1.equals("3"))) {
		
		%>
			<table>
	<tr>
		
		<%if (typeView1!=null && (typeView1.equals("1") || typeView1.equals("3"))) {
			out.println("<th>БЕЗ НАПРАВЛЕНИЙ ГОСПИТАЛИЗАЦИИ</th>");
		%>
		
		<ecom:webQuery name="table1" nativeSql="select sls.id as slsid
,oml.name as omlname		
,ss.code as sscode,pat.lastname||' '||pat.firstname||' '||coalesce(pat.middlename,'') as patmiddlename
,pat.birthday as birthday
, case when sls.emergency='1' then 'экстренно' else 'планово' end as emergency
,sls.dateStart as slsdatestart,sls.dateFinish as slsdatefinish
,ml.name as mlname
,vbt.name as vbtname
,vss.name as vssname
,(select list(mkb.code) from diagnosis diag 
left join VocIdc10 mkb on mkb.id=diag.idc10_id 
left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationtype_id
where diag.medcase_id=slo.id and vpd.code='1' and vdrt.code = '4')
,bf.id||''','''||coalesce(bf.snilsDoctorDirect263,'') as bfid
,bf.snilsDoctorDirect263
,sls.id||''','''||to_char(sls.dateStart,'dd.mm.yyyy')||''','''||pat.lastname||''','''||pat.firstname||''','''||coalesce(pat.middlename,'')
	||''','''||to_char(pat.birthday,'dd.mm.yyyy')||''',''1'',''1' as snarp
from medcase sls 
left join MisLpu lpu on lpu.id=sls.lpu_id
left join MedCase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase'
left join BedFund bf on bf.id=slo.bedFund_id
left join MisLpu ml on ml.id=slo.department_id
left join VocBedType vbt on vbt.id = bf.bedType_id
left join VocBedSubType vbst on vbst.id=bf.bedSubType_id
left join HospitalDataFond hdf on hdf.hospitalMEdCase_id=sls.id
left join StatisticStub ss on ss.id=sls.statisticStub_id
left join Patient pat on pat.id=sls.patient_id
left join VocServiceStream vss on vss.id=sls.serviceStream_id
left join MisLpu oml on oml.id=sls.orderLpu_id
where sls.dtype='HospitalMedCase' and sls.datestart between to_date('${param.period}','dd.mm.yyyy') and to_date('${periodTo}','dd.mm.yyyy')
and sls.deniedHospitalizating_id is null
and (slo.id is null or slo.prevMedCase_id is null) 
and vss.code in ('OBLIGATORYINSURANCE')
and hdf.hospitalmedcase_id is null

${lpuSlsSql} ${emergencySlsSql}
		"/>
		<% }
		if (typeView1!=null && (typeView1.equals("2")||typeView1.equals("3"))) {
			out.print("<th>НАПРАВЛЕНИЯ БЕЗ ГОСПИТАЛИЗАЦИЙ</th>") ;
		%>
		<ecom:webQuery name="table2" nativeSql="
	select hdf.id
	,'<'||'span '||case when hdf.deniedHospital is not null then 'style=''background:#01DF74''' else '' end|| '>' 
	||hdf.numberfond||'<'||'/'||'span'||'>' as f2numberfond
	,'<'||'span '||case when hdf.deniedHospital is not null then 'style=''background:#01DF74''' else '' end|| '>' 
	||hdf.lastname||' '||hdf.firstname||' '||coalesce(hdf.middlename,'')||'<'||'/'||'span'||'>' as f3io
	
	,hdf.birthday,hdf.formHelp
,hdf.profile,hdf.prehospdate,hdf.hospdate,hdf.directdate,hdf.snils as f10snils
,hdf.phone,hdf.diagnosis
,hdf.orderlpucode
,hdf.directlpucode
,hdf.statcard as f15
,hdf.deniedHospital
,hdf.id||''','''||coalesce(''||hdf.deniedHospital,'') as idden
,hdf.id||''','''||to_char(coalesce(hdf.prehospdate,hdf.hospdate),'dd.mm.yyyy')||''','''||hdf.lastname||''','''||hdf.firstname||''','''||coalesce(hdf.middlename,'')
	||''','''||to_char(hdf.birthday,'dd.mm.yyyy')||''',''1'','''||coalesce(hdf.deniedHospital,0) as idforsls

from HospitalDataFond hdf
where hdf.hospitalMedCase_id is null and (case when  hdf.isTable4 ='1' then '1' when hdf.IsTable4='1' then '1' else null end) is null
${lpuSql} ${emergencySql}
and coalesce(hdf.prehospdate,hdf.hospdate) between to_date('${param.period}','dd.mm.yyyy') and to_date('${periodTo}','dd.mm.yyyy')
order by hdf.lastname,hdf.firstname,hdf.middlename,hdf.id
	"/>
	<%} %>
	
	</tr> 
	<tr>
	<%if (typeView1!=null && (typeView1.equals("1") || typeView1.equals("3"))) {
			
		%>
		<td valign="top">
		<msh:table name="table1" action=" javascript:void(0)" idField="1">
			<msh:tableButton property="15" buttonFunction="showDiag263napr" buttonName="Установить соответствие с неопределенными направлениями" buttonShortName="Направление"/>
			<msh:tableButton property="13" buttonFunction="setSnilsDoctor" buttonName="Установить СНИЛС врача" buttonShortName="Уст. СНИЛС"/>
			<msh:tableColumn property="14" columnName="<a href='javascript:alert(\"snils\")'>СНИЛС врача для генерации напр. для план. СЛС</a>"/>
			<msh:tableColumn property="sn" columnName="#"/>
			<msh:tableColumn property="2" columnName="<a href='javascript:alert(\"lpu\")'>Напр. ЛПУ</a>"/>
			<msh:tableColumn property="3" columnName="№ИБ"/>
			<msh:tableColumn property="4" columnName="<a href='javascript:alert(\"pat\")'>ФИО пациента</a>"/>
			<msh:tableColumn property="5" columnName="Дата рождения"/>
			<msh:tableColumn property="6" columnName="Показания"/>
			<msh:tableColumn property="7" columnName="Дата госп."/>
			<msh:tableColumn property="8" columnName="Дата выписки"/>
			<msh:tableColumn property="9" columnName="Отделение"/>
			<msh:tableColumn property="10" columnName="<a href='javascript:alert(\"prof\")'>Профиль коек</a>"/>
			<msh:tableColumn property="11" columnName="Поток обслуживания"/>
			<msh:tableColumn property="12" columnName="Диагноз"/>
		</msh:table>
	</td>
	<% }
		if (typeView1!=null && (typeView1.equals("2")||typeView1.equals("3"))) {
		%>
	<td valign="top">
	<msh:section>
		<msh:sectionTitle>
			<a href='javascript:void(0)' onclick="deleteTableHDFEmpty(1)">Удалить аннулированные направления</a>
			<a href='javascript:void(0)' onclick="deleteTableHDFEmpty(2)">Удалить направленные (не госп. и не выписанные)</a>
			<a href='javascript:void(0)' onclick="deleteTableHDFEmpty(3)">Удалить госпитализированных (еще не выписанным)</a>
		</msh:sectionTitle>
		<msh:table name="table2" action=" javascript:void(0)" idField="1">
			<msh:tableButton property="1" buttonFunction="showHDF" buttonName="Просмотр информации о направление" buttonShortName="Инф."/>
			<msh:tableButton property="18" buttonFunction="showDiag263sls" buttonName="Установить соответствие с неопределенной госпитализацией" buttonShortName="СЛС"/>
			<msh:tableButton property="17" buttonFunction="showDiag263denied" buttonName="Установить отказ" buttonShortName="Отказ"/>
			<msh:tableButton property="1" buttonFunction="deleteHDF" buttonName="Удалить направление" buttonShortName="Удалить"/>
			<msh:tableColumn property="16" columnName="Код отказа"/>
			<msh:tableColumn property="sn" columnName="#"/>
			<msh:tableColumn property="2" columnName="№напр. фонда"/>
			<msh:tableColumn property="3" columnName="ФИО"/>
			<msh:tableColumn property="4" columnName="Дата рождения"/>
			<msh:tableColumn property="5" columnName="Показания"/>
			<msh:tableColumn property="6" columnName="Профиль"/>
			<msh:tableColumn property="7" columnName="Дата пред."/>
			<msh:tableColumn property="8" columnName="Дата госп."/>
			<msh:tableColumn property="9" columnName="Дата направления"/>
			<msh:tableColumn property="10" columnName="СНИЛС врача"/>
			<msh:tableColumn property="11" columnName="Телефон пациента"/>
			<msh:tableColumn property="12" columnName="Диагноз"/>
			<msh:tableColumn property="13" columnName="Напр.ЛПУ"/>
			<msh:tableColumn property="14" columnName="Куда напр.ЛПУ"/>
			
		</msh:table>
		</msh:section>	
		</td>
		<%} %>
		</tr>
		</table>
		<%       }  else  if (typeView1!=null && typeView1.equals("4")) {%>
		<ecom:webQuery name="table1" nativeSql="select sls.id as slsid
,oml.name as omlname		
,ss.code as sscode,pat.lastname as patlastname,pat.firstname as patfirstname
,coalesce(pat.middlename,'') as patmiddlename
, case when sls.emergency='1' then 'экстренно' else 'планово' end as emergency
,sls.dateStart as slsdatestart,sls.dateFinish as slsdatefinish
,ml.name as mlname
,vbt.name as vbtname
,vss.name as vssname
,(select list(mkb.code) from diagnosis diag 
left join VocIdc10 mkb on mkb.id=diag.idc10_id 
left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationtype_id
where diag.medcase_id=slo.id and vpd.code='1' and vdrt.code = '4')
,vbst.name as vbstname
,sls.id||''','''||coalesce(slo.id,0) ss 
from medcase sls 
left join MisLpu lpu on lpu.id=sls.lpu_id
left join MedCase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase'
left join diagnosis d on slo.id = d.medcase_id
left join vocdiagnosisregistrationtype vdrt on vdrt.id=d.registrationtype_id
left join vocprioritydiagnosis vpd on vpd.id=d.priority_id
left join BedFund bf on bf.id=slo.bedFund_id
left join MisLpu ml on ml.id=slo.department_id
left join VocBedType vbt on vbt.id = bf.bedType_id
left join VocBedSubType vbst on vbst.id=bf.bedSubType_id
left join HospitalDataFond hdf on hdf.hospitalMEdCase_id=sls.id
left join StatisticStub ss on ss.id=sls.statisticStub_id
left join Patient pat on pat.id=sls.patient_id
left join VocServiceStream vss on vss.id=sls.serviceStream_id
left join MisLpu oml on oml.id=sls.orderLpu_id
where sls.dtype='HospitalMedCase' and sls.datestart between to_date('${param.period}','dd.mm.yyyy') and to_date('${periodTo}','dd.mm.yyyy')
and sls.deniedHospitalizating_id is null
and (slo.id is null or slo.prevMedCase_id is null) and (vbst.id is null or vbst.code='1')
and vss.code in ('OBLIGATORYINSURANCE','OTHER')

and (case when  hdf.isTable3 ='1' then '1' when hdf.IsTable1='1' then '1' else null end) is not null
and (case when  hdf.isTable2 ='1' then '1' else null end) is null
${lpuSlsSql} ${emergencySqlSql}
group by sls.id,slo.id,oml.name ,ss.code ,pat.lastname ,pat.firstname 
,pat.middlename , sls.emergency,sls.dateStart ,sls.dateFinish
,ml.name ,vbt.name ,vss.name ,vbst.name
having count(case when (vdrt.code='3' or vdrt.code='4') and (vpd.code='1') and d.idc10_id is not null then 1 else null end)=0
		"/>
		<msh:table name="table1" action="javascript:void" idField="1">
			<msh:tableButton property="14" buttonFunction="alert" buttonName="Открыть в новой вкладке на редактирование" buttonShortName="СЛО"/>
			<msh:tableColumn property="sn" columnName="#"/>
			<msh:tableColumn property="2" columnName="№ИБ"/>
			<msh:tableColumn property="3" columnName="Фамилия"/>
			<msh:tableColumn property="4" columnName="Имя"/>
			<msh:tableColumn property="5" columnName="Отчество"/>
			<msh:tableColumn property="6" columnName="Показания"/>
			<msh:tableColumn property="7" columnName="Дата госп."/>
			<msh:tableColumn property="8" columnName="Дата выписки"/>
			<msh:tableColumn property="9" columnName="Отделение"/>
			<msh:tableColumn property="10" columnName="Профиль коек"/>
			<msh:tableColumn property="11" columnName="Поток обслуживания"/>
			<msh:tableColumn property="12" columnName="Диагноз"/>
		</msh:table>	
		<%       }  else  if (typeView1!=null && typeView1.equals("5")) {%>
		<ecom:webQuery name="table1" nativeSql="
select bf.id||''','''||coalesce(bf.snilsDoctorDirect263,'') as id
,bf.snilsDoctorDirect263
,list(distinct oml.name) as omlname		
,ml.name as mlname
,vbt.name as vbtname,count(sls.id) 
from medcase sls 
left join MisLpu lpu on lpu.id=sls.lpu_id
left join MedCase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase'
left join BedFund bf on bf.id=slo.bedFund_id
left join MisLpu ml on ml.id=slo.department_id
left join VocBedType vbt on vbt.id = bf.bedType_id
left join VocBedSubType vbst on vbst.id=bf.bedSubType_id
left join HospitalDataFond hdf on hdf.hospitalMEdCase_id=sls.id
left join StatisticStub ss on ss.id=sls.statisticStub_id
left join Patient pat on pat.id=sls.patient_id
left join VocServiceStream vss on vss.id=sls.serviceStream_id
left join MisLpu oml on oml.id=sls.orderLpu_id
where sls.dtype='HospitalMedCase' and sls.datestart between to_date('${param.period}','dd.mm.yyyy') and to_date('${periodTo}','dd.mm.yyyy')
and sls.deniedHospitalizating_id is null
and (slo.id is null or slo.prevMedCase_id is null) and (vbst.id is null or vbst.code='1')
and vss.code in ('OBLIGATORYINSURANCE','OTHER')

and (case when  hdf.isTable3 ='1' then '1' when hdf.IsTable1='1' then '1' else null end) is null
${lpuSlsSql} ${emergencySlsSql}
group by bf.id,bf.snilsDoctorDirect263,ml.name,vbt.name
		"/>
		<msh:table name="table1" action="javascript:void" idField="1">
			<msh:tableButton property="1" buttonFunction="setSnilsDoctor" buttonName="Установить СНИЛС врача" buttonShortName="Уст. СНИЛС"/>
			<msh:tableColumn property="sn" columnName="#"/>
			<msh:tableColumn property="2" columnName="СНИЛС врача"/>
			<msh:tableColumn property="3" columnName="Направившие ЛПУ"/>
			<msh:tableColumn property="4" columnName="Отделение"/>
			<msh:tableColumn property="5" columnName="Профиль"/>
			<msh:tableColumn property="6" columnName="Кол-во случаев"/>
		</msh:table>	
		<%       }  else  if (typeView1!=null && typeView1.equals("6")) {%>
		<ecom:webQuery name="table1" nativeSql="
select 
bf.id as bfid
,lpu.name as lpuname
, vbt.codef as vbtcodef
, vbt.name  as vbtname
,  vbst.name as usl_ok 
, case when bf.forChild='1' then 'Да' else 'Нет' end as det 
, bf.amount as bfamount
,vss.name as vssname
,bf.id||''','''||coalesce(bf.amount,'0') as id
,lpuP.name as lpupname
  from BedFund bf 
   left join VocBedType vbt on vbt.id=bf.bedType_id 
   left join VocBedSubType vbst on vbst.id=bf.bedSubType_id 
   left join VocServiceStream vss on vss.id=bf.serviceStream_id
   left join MisLpu lpu on lpu.id=bf.lpu_id 
   left join MisLpu lpuP on lpuP.id=lpu.parent_id 
   
  where bf.dateStart <=to_date('${param.period}','dd.mm.yyyy') 
  and coalesce(bf.dateFinish,to_date('${periodTo}','dd.mm.yyyy')) >=to_date('${periodTo}','dd.mm.yyyy')
  and vss.code in ('OBLIGATORYINSURANCE') 
  group by bf.id ,lpu.name , vbt.codef , vbt.name  ,  vbst.name , bf.forChild, bf.amount, vss.name,lpuP.name
  order by vbt.name,lpu.name,lpuP.name
		"/>
		<msh:table name="table1" editUrl="entityParentEdit-mis_bedFund.do" action="entityParentView-mis_bedFund.do" idField="1">
			
			<msh:tableColumn property="sn" columnName="#"/>
			<msh:tableColumn property="8" columnName="Поток обслуживания"/>
			<msh:tableColumn property="10" columnName="ЛПУ"/>
			<msh:tableColumn property="2" columnName="Отделение"/>
			<msh:tableColumn property="3" columnName="Код профиля"/>
			<msh:tableColumn property="4" columnName="Профиль"/>
			<msh:tableColumn property="5" columnName="Тип коек"/>
			<msh:tableColumn property="6" columnName="Детские койки"/>
			<msh:tableButton property="9" buttonFunction="setAmountBed" buttonName="Установить кол-во коек" buttonShortName="Уст. кол-во"/>
			<msh:tableColumn property="7" columnName="Кол-во"/>
		</msh:table>	
		<%       } %>
	<% } %>
  </tiles:put>
</tiles:insert>