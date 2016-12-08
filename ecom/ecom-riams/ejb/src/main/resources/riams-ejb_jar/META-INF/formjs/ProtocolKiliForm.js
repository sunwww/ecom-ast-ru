/**
 * Перед редактированием
 */
function onPreSave(aForm, aEntity, aContext) {

var dayLimit = aContext.manager.createNativeQuery("select keyvalue from softconfig  where key='KILI_EDIT_CNTDAYS'").getResultList();
if (dayLimit.size()>0)	{
	
	dayLimit = +dayLimit.get(0);
	if (dayLimit>0){		
		try {
			var format = new java.text.SimpleDateFormat("dd.MM.yyyy") ;
			var date = format.parse(aForm.protocolDate);
			var check = Packages.ru.ecom.mis.ejb.form.medcase.hospital.interceptors.SecPolicy.isDateLessThenHour(date,((dayLimit+1)*24));
			if (!check) {
				throw "Прошло больше "+dayLimit+" дней с даты протокола, редактирование невозможно";
			}
		} catch ( e) {
				throw ""+e;		
		}
	}
	}
}

/**
 * Перед созданием
 */

function onPreCreate(aForm, aCtx) {
	var list = aCtx.manager.createNativeQuery("select protocolnumber, id from ProtocolKili where deathcase_id="+ aForm.deathCase).getResultList() ;
	
	if (list.size()>0) {
		var error = "";
		error += " Протокол уже существует. <a href='entityParentView-mis_protocolKili.do?id=" + list.get(0)[1] +"'>Протокол №" + list.get(0)[0] +"</a><br/>" ;
		throw error;
		}
	
}

/**
 * Перед удалением
 */
function onPreDelete(aId, aCtx) {
	
}
/**
 * Перед сохранением
 */
function onSave(aForm, aEntity, aCtx) {
	var date = new java.util.Date() ;
	aEntity.setEditDate(new java.sql.Date(date.getTime())) ;
	aEntity.setEditTime(new java.sql.Time (date.getTime())) ;
	aEntity.setEditUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	saveData(aForm, aEntity, aCtx);
}
/**
 * Перед созданием
 */
function onCreate(aForm, aEntity, aCtx) {
	var username = aCtx.getSessionContext().getCallerPrincipal().toString();
	var date = new java.sql.Date(new java.util.Date().getTime());
	var time = new java.sql.Time (date.getTime());
	aEntity.setCreateDate(date) ;
	aEntity.setCreateTime(time) ;
	aEntity.setCreateUsername(username) ;
	saveData(aForm, aEntity, aCtx);
}

function saveData(aForm, aEntity, aCtx) {
	/**
	 * Переменная defects принимает значение defectSaveList
	 */
	var defects = aForm.getDefectSaveList().split("##");
	/**
	 * Парсинг 
	 */
	
	var temp = aForm.getDefectSaveList().split("##");
	if (defects.length > 0){
		for (var i=0;i<defects.length;i++) {
			var separator = defects[i].split("@@") ;
			var defId = +separator[0];
			var def = null;
			if (defId == null || defId==0) {
				 def = new Packages.ru.ecom.mis.ejb.domain.medcase.kili.ProtocolKiliDefect();
			} else {
				def = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.kili.ProtocolKiliDefect,new java.lang.Long(defId));
			}
			var vocDefect = aCtx.manager.find(Packages.ru.ecom.mis.ejb.domain.medcase.voc.VocKiliDefect, new java.lang.Long(separator[1])) ;
			def.setProtocol(aEntity);
			def.setDefect(vocDefect);
			def.setIsDefectFound (separator[2]=='1'?true:false);
			def.setDefectText(separator[2]=='1'?separator[3]:'');
			aCtx.manager.persist(def);
		}
	}	
}