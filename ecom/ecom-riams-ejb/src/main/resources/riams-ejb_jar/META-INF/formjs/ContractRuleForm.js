
/**
 * Перед созданием
 */
function onPreCreate(aForm, aCtx) {
	check(aForm,aCtx);
}

/**
 * Перед сохранением
 */
function onPreSave(aForm, aEntity, aCtx) {
	check(aForm,aCtx);
}
function onSave(aForm, aEntity, aCtx) {
	save(aForm,aEntity,aCtx);
}

function save(aForm,aEntity,aCtx) {
	if (+aForm.servedPerson<1 &&
			+aForm.person>0 ) {
		var servPerson = Packages.ru.ecom.mis.ejb.domain.contract.ServedPerson() ;
		servPerson.setContract(aEntity.contract) ;
		var person = getObject(aCtx,aForm.person,Packages.ru.ecom.mis.ejb.domain.contract.ContractPerson) ;
		servPerson.setPerson(person) ;
		aCtx.manager.persist(servPerson) ;
		aEntity.setServedPerson(servPerson) ;
		aCtx.manager.persist(aEntity) ;
	}
}
function getObject(aCtx,aId,aClazz) {
	return (aId==null||aId=='0'||aId=='')?null:aCtx.manager.find(aClazz, aId) ;
}
function check(aForm,aCtx) {
	if (+aForm.nosologyGroup<1 &&
			+aForm.medServiceGroup<1 &&
			+aForm.guaranteeGroup<1) {
		throw "Обязательно надо заполнить хотя бы одно условие!!!" ;
	}
	
	
} 
