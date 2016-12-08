/**
 * Перед редактированием
 */
function onPreSave(aForm, aEntity, aContext) {
//var day_limit = aContext.manager.createNativeQuery("select keyvalue from softconfig  where key='KILI_EDIT_CNTDAYS'");
/*
var today = new Date();
var dd = today.getDate();
var mm = today.getMonth()+1; //January is 0!
var yyyy = today.getFullYear();

var str = "2013/01/14";
var p = str.split("/");
var date_protocol = new Date(p[0],p[1],p[2]);

var diffDays = parseInt((today - date_protocol) / (1000 * 60 * 60 * 24)); 
if (diffDays>3)
System.out.println("Access denied!");
else
System.out.println("Access granted!");
	var getCurDate = new Date(); 
	var getKiliDate = aCtx.manager.createNativeQuery("select createdate from protocolkili where deathcase_id=" + aForm.deathcase);
	if 
	updateAddress(aForm) ;
	changeData(aForm,aEntity,aContext);
	*/
//throw day_limit;
}

/**
 * Перед созданием
 */

function onPreCreate(aForm, aCtx) {
	//var aId = aForm.getIDProtocol();
	var list = aCtx.manager.createNativeQuery("select protocolnumber from ProtocolKili where deathcase_id="+ aForm.deathCase).getResultList() ;
	var list2 = aCtx.manager.createNativeQuery("select id from ProtocolKili where deathcase_id="+ aForm.deathCase).getResultList() ;
	if (list.size()>0) {
		var error = "";
		error += " Протокол уже существует. <a href='entityParentView-mis_protocolKili.do?id=" + list2.get(0) +"'>Протокол №" + list.get(0) +"</a><br/>" ;
		throw error;//list.size()>0?">0 т.к."+list.get(0):"Пусто";;
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
	//System.out.println();
	//var defects = aForm.getDefectSaveList();
	//throw defects.length;
	/**
	 * Парсинг 
	 */
	
	var temp = aForm.getDefectSaveList().split("##");
	
	//throw(defects[0]) ;
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
			//def.setProfile(aForm);
			def.setProtocol(aEntity);
			def.setDefect(vocDefect);
			def.setIsDefectFound (separator[2]=='1'?true:false);
			def.setDefectText(separator[2]=='1'?separator[3]:'');
			aCtx.manager.persist(def);
		}
	}
	
	
	
	//throw ""+defects.length;
	//var def = new Packages.ru.ecom.mis.ejb.domain.medcase.kili.ProtocolKiliDefect();
	//def.setProtocolKili(aEntity);
	//aCtx.manager.persist(def);
	
	
}