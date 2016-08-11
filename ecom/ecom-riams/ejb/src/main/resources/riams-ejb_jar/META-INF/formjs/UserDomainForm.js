function onCreate(aForm, aEntity, aCtx) {
	if (aEntity.getCode()!=null&&aEntity.getCode()!='') {
		checkCode(aEntity, aCtx, '','userDomain');
	} else {
		createAutoCode (aEntity, aCtx, '','userDomain');
	}
	
}

function onSave(aForm, aEntity, aCtx) {
	if (aEntity.getCode()!=null&&aEntity.getCode()!='') {
		checkCode(aEntity, aCtx, '','userDomain');
	} else {
		createAutoCode (aEntity, aCtx, '','userDomain');
	}

}
function checkCode(aEntity, aCtx, className, tableName) {
	var addSql = "";
	if (className!=null&&className!='') {
		addSql = " and dtype='"+className+"'";
	}
	var cnt = aCtx.manager.createNativeQuery("select count(*) from "+tableName+" where code='"+aEntity.getCode()+"' and id!="+aEntity.getId()+""+addSql).getSingleResult();
	if (+cnt>0) throw "Уже существует запись с таким кодом. Чтобы проставить код автоматически, очистите поле";
}

function createAutoCode (aEntity, aCtx, className, tableName) {
	if (aEntity.getCode()==null||aEntity.getCode()=='') {
		var addSql = "";
		if (className!=null&&className!='') {
			addSql = "and dtype='"+className+"'";
		}
		 
		var i=0;
		while (true) {
			var code = "code"+(i>0?i:"")+"_"+aEntity.getId();
			var list = aCtx.manager.createNativeQuery ("select count(*) from "+tableName+" where code='"+code+"'"+addSql).getSingleResult();
			if (+list>0) {
				i++;
			} else {
				aEntity.setCode(code);
				break;
			}
			
		}
	}
}