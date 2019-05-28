/**
* Поиск
*/
function findListTemplate(aContext, aId, aCnt, aNext, aSearch) {
	var list ;
	var ret = new java.util.ArrayList() ;
	//if (aCnt==0) aCnt=10 ;
	var sqlAdd = ""+aSearch!=="" ? " upper(text) like upper('%"+aSearch+"%')" : "";
	if (aNext < 0) {
		if (aCnt<0) aCnt = aCnt*(-1) ;
		if (aId==null || aId == "" || aId==0) {
			list =  aContext.manager.createQuery("from TemplateProtocol "+(sqlAdd!==""? "where "+sqlAdd : "")+" order by id desc").setMaxResults(aCnt).getResultList() ;
		} else {
			list =  aContext.manager.createQuery("from TemplateProtocol where id < "+aId+(sqlAdd!==""? "and "+sqlAdd:"")+" order by id desc").setMaxResults(aCnt).getResultList() ;
			if (list.size()<aCnt) {
				return findListTemplate(aContext,"",aCnt,1) ;
			}
			
		}
		
		for (var i=list.size()-1 ; i>=0 ; i--) {
			var temp = list.get(i) ;
			ret.add(protocolMap(temp)) ;
		}
	} else {
		if (aId==null || aId == "" || aId==0) {
			list =  aContext.manager.createQuery("from TemplateProtocol "+(sqlAdd!==""? "where "+sqlAdd : "")+" order by id asc").setMaxResults(aCnt).getResultList() ;
		} else {
			list =  aContext.manager.createQuery("from TemplateProtocol where id > "+aId+(sqlAdd!==""? "and "+sqlAdd:"")+" order by id asc").setMaxResults(aCnt).getResultList() ;
			if (list.size()<aCnt) {
				return findListTemplate(aContext,"",aCnt,-1) ;
			}
		}
		for (var i=0 ; i< list.size() ; i++) {
			var temp = list.get(i) ;
			ret.add(protocolMap(temp)) ;
		}
	}
	return ret ;
}
/**
* Поиск
*/
function findListTemplateByUser(aContext, aId, aCnt, aNext) {
	var list ;
	var username=aContext.sessionContext.callerPrincipal.name ;
	var ret = new java.util.ArrayList() ;
	//if (aCnt==0) aCnt=10 ;
	if (aNext < 0) {
		if (aCnt<0) aCnt = aCnt*(-1) ;
		if (aId==null || aId == "" || aId==0) {
			list =  aContext.manager.createQuery("from TemplateProtocol where username='"+username+"' order by id desc").setMaxResults(aCnt).getResultList() ;
		} else {
			list =  aContext.manager.createQuery("from TemplateProtocol where username='"+username+"' and id < "+aId+" order by id desc").setMaxResults(aCnt).getResultList() ;
			if (list.size()<aCnt) {
				return findListTemplate(aContext,"",aCnt,1) ;
			}
			
		}
		
		for (var i=list.size()-1 ; i>=0 ; i--) {
			var temp = list.get(i) ;
			ret.add(protocolMap(temp)) ;
			
		}
	} else {
		if (aId==null || aId == "" || aId==0) {
			list =  aContext.manager.createQuery("from TemplateProtocol where  username='"+username+"' order by id asc").setMaxResults(aCnt).getResultList() ;
		} else {
			list =  aContext.manager.createQuery("from TemplateProtocol where username='"+username+"' and id > "+aId+" order by id asc").setMaxResults(aCnt).getResultList() ;
			if (list.size()<aCnt) {
				return findListTemplate(aContext,"",aCnt,-1) ;
			}
		}
		for (var i=0 ; i< list.size() ; i++) {
			var temp = list.get(i) ;
			ret.add(protocolMap(temp)) ;
		}
	}
	return ret ;
}
function findListCalendarTemplate(aContext, aId, aCnt, aNext) {
	var list ;
	var ret = new java.util.ArrayList() ;
	//if (aCnt==0) aCnt=10 ;
	if (aNext < 0) {
		if (aCnt<0) aCnt = aCnt*(-1) ;
		if (aId==null || aId == "" || aId==0) {
			list =  aContext.manager.createQuery("from WorkCalendarPattern order by id desc").setMaxResults(aCnt).getResultList() ;
		} else {
			list =  aContext.manager.createQuery("from WorkCalendarPattern where id < "+aId+" order by id desc").setMaxResults(aCnt).getResultList() ;
			if (list.size()<aCnt) {
				return findListTemplate(aContext,"",aCnt,1) ;
			}
			
		}
		
		for (var i=list.size()-1 ; i>=0 ; i--) {
			var temp = list.get(i) ;
			ret.add(calendarPatternMap(temp)) ;
			
		}
	} else {
		if (aId==null || aId == "" || aId==0) {
			list =  aContext.manager.createQuery("from WorkCalendarPattern order by id asc").setMaxResults(aCnt).getResultList() ;
		} else {
			list =  aContext.manager.createQuery("from WorkCalendarPattern where id > "+aId+" order by id asc").setMaxResults(aCnt).getResultList() ;
			if (list.size()<aCnt) {
				return findListTemplate(aContext,"",aCnt,-1) ;
			}
		}
		for (var i=0 ; i< list.size() ; i++) {
			var temp = list.get(i) ;
			ret.add(calendarPatternMap(temp)) ;
		}
	}
	return ret ;
}
function findListPrescriptTemplateByUser(aContext, aId, aCnt, aNext) {
	var list ;
	var ret = new java.util.ArrayList() ;
	var username=aContext.sessionContext.callerPrincipal.name ;
	
	//if (aCnt==0) aCnt=10 ;
	if (aNext < 0) {
		if (aCnt<0) aCnt = aCnt*(-1) ;
		if (aId==null || aId == "" || aId==0) {
			list =  aContext.manager.createQuery("from AbstractPrescriptionList where DTYPE='PrescriptListTemplate' and createUsername='"+username+"' order by id desc").setMaxResults(aCnt).getResultList() ;
		} else {
			list =  aContext.manager.createQuery("from AbstractPrescriptionList where DTYPE='PrescriptListTemplate' and id < "+aId+" and createUsername='"+username+"' order by id desc").setMaxResults(aCnt).getResultList() ;
			if (list.size()<aCnt) {
				return findListPrescriptTemplate(aContext,"",aCnt,1) ; 
			}
			
		}
		
		for (var i=list.size()-1 ; i>=0 ; i--) {
			var temp = list.get(i) ;
			ret.add(prescriptMap(temp)) ;
			
		}
	} else {
		if (aId==null || aId == "" || aId==0) {
			list =  aContext.manager.createQuery("from AbstractPrescriptionList where DTYPE='PrescriptListTemplate'  and createUsername='"+username+"' order by id asc").setMaxResults(aCnt).getResultList() ;
		} else {
			list =  aContext.manager.createQuery("from AbstractPrescriptionList where DTYPE='PrescriptListTemplate'  and createUsername='"+username+"' and id > "+aId+" order by id asc").setMaxResults(aCnt).getResultList() ;
			if (list.size()<aCnt) {
				return findListPrescriptTemplate(aContext,"",aCnt,-1) ;
			}
		}
		for (var i=0 ; i< list.size() ; i++) {
			var temp = list.get(i) ;
			ret.add(prescriptMap(temp)) ;
		}
	}
	return ret ;
}
function findListPrescriptTemplate(aContext, aId, aCnt, aNext) {
	var list ;
	var ret = new java.util.ArrayList() ;
	//if (aCnt==0) aCnt=10 ;
	if (aNext < 0) {
		if (aCnt<0) aCnt = aCnt*(-1) ;
		if (aId==null || aId == "" || aId==0) {
			list =  aContext.manager.createQuery("from AbstractPrescriptionList where DTYPE='PrescriptListTemplate' order by id desc").setMaxResults(aCnt).getResultList() ;
		} else {
			list =  aContext.manager.createQuery("from AbstractPrescriptionList where DTYPE='PrescriptListTemplate' and id < "+aId+" order by id desc").setMaxResults(aCnt).getResultList() ;
			if (list.size()<aCnt) {
				return findListPrescriptTemplate(aContext,"",aCnt,1) ; 
			}
			
		}
		
		for (var i=list.size()-1 ; i>=0 ; i--) {
			var temp = list.get(i) ;
			ret.add(prescriptMap(temp)) ;
			
		}
	} else {
		if (aId==null || aId == "" || aId==0) {
			list =  aContext.manager.createQuery("from AbstractPrescriptionList where DTYPE='PrescriptListTemplate' order by id asc").setMaxResults(aCnt).getResultList() ;
		} else {
			list =  aContext.manager.createQuery("from AbstractPrescriptionList where DTYPE='PrescriptListTemplate' and id > "+aId+" order by id asc").setMaxResults(aCnt).getResultList() ;
			if (list.size()<aCnt) {
				return findListPrescriptTemplate(aContext,"",aCnt,-1) ;
			}
		}
		for (var i=0 ; i< list.size() ; i++) {
			var temp = list.get(i) ;
			ret.add(prescriptMap(temp)) ;
		}
	}
	return ret ;
}

function findListDish(aContext, aId, aCnt, aNext) {
	var list ;
	var ret = new java.util.ArrayList() ;
	//if (aCnt==0) aCnt=10 ;
	if (aNext < 0) {
		if (aCnt<0) aCnt = aCnt*(-1) ;
		if (aId==null || aId == "" || aId==0) {
			list =  aContext.manager.createQuery("from Dish order by id desc").setMaxResults(aCnt).getResultList() ;
		} else {
			list =  aContext.manager.createQuery("from Dish where id < "+aId+" order by id desc").setMaxResults(aCnt).getResultList() ;
			if (list.size()<aCnt) {
				return findListDish(aContext,"",aCnt,1) ; 
			}
			
		}
		
		for (var i=list.size()-1 ; i>=0 ; i--) {
			var temp = list.get(i) ;
			ret.add(dishMap(temp)) ;
			
		}
	} else {
		if (aId==null || aId == "" || aId==0) {
			list =  aContext.manager.createQuery("from Dish order by id asc").setMaxResults(aCnt).getResultList() ;
		} else {
			list =  aContext.manager.createQuery("from Dish where id > "+aId+" order by id asc").setMaxResults(aCnt).getResultList() ;
			if (list.size()<aCnt) {
				return findListDish(aContext,"",aCnt,-1) ;
			}
		}
		for (var i=0 ; i< list.size() ; i++) {
			var temp = list.get(i) ;
			ret.add(dishMap(temp)) ;
		}
	}
	return ret ;
}


function findListDeleteJournal(aContext, aId, aCnt, aNext) {
	var list ;
	var ret = new java.util.ArrayList() ;
	//if (aCnt==0) aCnt=10 ;
	if (aNext < 0) {
		if (aCnt<0) aCnt = aCnt*(-1) ;
		if (aId==null || aId == "" || aId==0) {
			list =  aContext.manager.createQuery("from DeleteJournal order by id desc").setMaxResults(aCnt).getResultList() ;
		} else {
			list =  aContext.manager.createQuery("from DeleteJournal where id < "+aId+" order by id desc").setMaxResults(aCnt).getResultList() ;
			if (list.size()<aCnt) {
				return findListDeleteJournal(aContext,"",aCnt,1) ; 
			}
			
		}
		
		for (var i=list.size()-1 ; i>=0 ; i--) {
			var temp = list.get(i) ;
			ret.add(deleteJournalMap(temp)) ;
			
		}
	} else {
		if (aId==null || aId == "" || aId==0) {
			list =  aContext.manager.createQuery("from DeleteJournal order by id asc").setMaxResults(aCnt).getResultList() ;
		} else {
			list =  aContext.manager.createQuery("from DeleteJournal where id > "+aId+" order by id asc").setMaxResults(aCnt).getResultList() ;
			if (list.size()<aCnt) {
				return findListDeleteJournal(aContext,"",aCnt,-1) ;
			}
		}
		for (var i=0 ; i< list.size() ; i++) {
			var temp = list.get(i) ;
			ret.add(deleteJournalMap(temp)) ;
		}
	}
	return ret ;
}

function findListPermission(aContext, aId, aCnt, aNext) {
	var list ;
	var ret = new java.util.ArrayList() ;
	
	if (aNext < 0) {
		if (aCnt<0) aCnt = aCnt*(-1) ;
		if (aId==null || aId == "" || aId==0) {
			list =  aContext.manager.createQuery("from Permission where (isDeleted is null or isDeleted='0') order by id asc").setMaxResults(aCnt).getResultList() ;
		} else {
			list =  aContext.manager.createQuery("from Permission where (isDeleted is null or isDeleted='0') and id > "+aId+" order by id asc").setMaxResults(aCnt).getResultList() ;
			if (list.size()<aCnt) {
				return findListPermission(aContext,"",aCnt,1) ; 
			}
		}
		
		for (var i=list.size()-1 ; i>=0 ; i--) {
			var temp = list.get(i) ;
			ret.add(permissionMap(temp)) ;
		}
	} else {
		if (aId==null || aId == "" || aId==0) {
			list =  aContext.manager.createQuery("from Permission where (isDeleted is null or isDeleted='0')order by id desc").setMaxResults(aCnt).getResultList() ;
		} else {
			list =  aContext.manager.createQuery("from Permission where (isDeleted is null or isDeleted='0') and id < "+aId+" order by id desc").setMaxResults(aCnt).getResultList() ;
			if (list.size()<aCnt) {
				return findListPermission(aContext,"",aCnt,-1) ;
			}
		}
		for (var i=0 ; i< list.size() ; i++) {
			var temp = list.get(i) ;
			ret.add(permissionMap(temp)) ;
		}
	}
		 
	return ret ;
}





function protocolMap(aTemp) {
	var map = new java.util.HashMap() ;
	map.put("id", new java.lang.Long(aTemp.id)) ;
	map.put("title", aTemp.title) ;
	map.put("text", aTemp.text) ;
	map.put("date", aTemp.date) ;
	map.put("username", aTemp.username) ;
	map.put("categoriesInfo", aTemp.categoriesInfo) ;
	map.put("categories", aTemp.categories) ;
	return map ;
}

function prescriptMap(aTemp) {
	var map = new java.util.HashMap() ;
	map.put("id", new java.lang.Long(aTemp.id)) ;
	map.put("name", aTemp.name) ;
	map.put("comments", aTemp.comments) ;
	map.put("workFunctionInfo", aTemp.workFunctionInfo) ;
	map.put("ownerInfo", aTemp.ownerInfo) ;
	map.put("date", aTemp.date) ;
	map.put("username", aTemp.username) ;
	map.put("prescriptTypeInfo", aTemp.prescriptTypeInfo) ;
	return map ;
}

function dishMap(aTemp) {
	var map = new java.util.HashMap() ;
	map.put("id", new java.lang.Long(aTemp.id)) ;
	map.put("name", aTemp.name) ;
	map.put("weight", aTemp.weight) ;
	map.put("dietsShortName", aTemp.dietsShortName) ;
	return map ;
}
function deleteJournalMap(aTemp) {
	var map = new java.util.HashMap() ;
	map.put("id", new java.lang.Long(aTemp.id)) ;
	map.put("serialization", aTemp.serialization) ;
	map.put("deleteDate", aTemp.deleteDate) ;
	map.put("deleteTime", aTemp.deleteTime) ;
	map.put("loginName", aTemp.loginName) ;
	map.put("className", aTemp.className) ;
	map.put("comment", aTemp.comment) ;
	map.put("objectId", aTemp.objectId) ;
	
	return map ;
}
function calendarPatternMap(aTemp) {
	var map = new java.util.HashMap() ;
	map.put("id", new java.lang.Long(aTemp.id)) ;
	map.put("info", aTemp.info) ;
	map.put("defaultTimeFrom", aTemp.defaultTimeFrom) ;
	map.put("defaultTimeTo", aTemp.defaultTimeTo) ;
	map.put("defaultTimeInterval", aTemp.defaultTimeInterval) ;
	map.put("noActive", aTemp.noActive) ;
	return map ;
}
function permissionMap(aTemp) {
	var map = new java.util.HashMap() ;
	map.put("id", new java.lang.Long(aTemp.id)) ;
	map.put("dateFrom", aTemp.dateFrom) ;
	map.put("dateTo", aTemp.dateTo) ;
	map.put("objectInfo", aTemp.objectInfo) ;
	map.put("permissionInfo", aTemp.permissionInfo) ;
	map.put("idObject", aTemp.idObject) ;
	map.put("userInfo", aTemp.userInfo) ;
	map.put("createUsername", aTemp.createUsername) ;
	map.put("createDate", aTemp.createDate) ;

	return map ;
}