function onCreate(aForm, aEntity, aCtx) {
	setDefault (aEntity, aCtx);
}

function onSave(aForm, aEntity, aCtx) {
	setDefault (aEntity, aCtx);
}

function setDefault (aEntity, aCtx) {
	if (aEntity.isDefault) {
		if (true==aEntity.isArchive) {
			throw "Архивная доверенность не может быть использована в системе по умолчанию!";
		}
		aCtx.manager.createQuery("update Attorney set isDefault='0' where id!="+aEntity.getId()).executeUpdate();
	}
}