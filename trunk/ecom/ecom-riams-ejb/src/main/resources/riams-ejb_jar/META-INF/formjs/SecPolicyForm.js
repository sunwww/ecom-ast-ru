function onCreate(aForm, aEntity, aContext) {
	saveArray(aEntity, aContext.manager,aForm.getRoleList()) ;
	
}
function onSave(aForm, aEntity, aContext) {
	Packages.ru.ecom.mis.ejb.uc.privilege.form.interceptor.ListPersist.saveArrayJson("SecRole_SecPolicy", aEntity.getId(), aForm.getRoleList(),"secPolicies_id","SecRole_id", aContext.manager) ;
}
function saveArray(aEntity,aManager, aJsonString) {
	var obj = new Packages.org.json.JSONObject(aJsonString) ;
	var ar = obj.getJSONArray("childs");
	var ids = new Packages.java.lang.StringBuilder() ;
	
	
	for (var i = 0; i < ar.length(); i++) {
		var child = ar.get(i);
		var jsId = java.lang.String.valueOf(child.get("value"));
		if (jsId!=null && jsId!="" || jsId=="0") {
			var jsonId=java.lang.Long.valueOf(jsId) ;
			var role = aManager.find(Packages.ru.ecom.jaas.ejb.domain.SecRole,jsonId) ;
			role.getSecPolicies().add(aEntity) ;
		}
	}
}