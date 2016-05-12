/**
* @author vtsybulin
*/
function onPreCreate(aForm, aCtx) {
	var date = new java.util.Date();
	aForm.setCreateDate(Packages.ru.nuzmsh.util.format.DateFormat.formatToDate(date)) ;
	aForm.setCreateTime(new java.sql.Time (date.getTime())) ;
	aForm.setUsername(aCtx.getSessionContext().getCallerPrincipal().toString()) ;
	if (+aForm.getWorkfunction()>0){
	}  
	else {		
		var wf = aCtx.serviceInvoke("WorkerService", "findLogginedWorkFunction") ;
		aForm.setWorkfunction(wf.getId()) ;
	}	
}

function onCreate (aForm, aEntity, aCtx) {
	
}

function onPreSave(aForm,aEntity , aCtx) {
	
}

