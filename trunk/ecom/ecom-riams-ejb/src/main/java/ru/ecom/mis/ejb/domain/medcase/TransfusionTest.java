package ru.ecom.mis.ejb.domain.medcase;

import javax.persistence.OneToOne;

import ru.ecom.mis.ejb.domain.medcase.voc.VocTransfusionTestMethod;
import ru.ecom.mis.ejb.domain.medcase.voc.VocTransfusionTestResult;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

public class TransfusionTest {
	/** Результат пробы */
	@Comment("Результат пробы")
	@OneToOne
	public VocTransfusionTestResult getResult() {return theResult;}
	public void setResult(VocTransfusionTestResult aResult) {theResult = aResult;}

	/** Метод */
	@Comment("Метод")
	@OneToOne
	public VocTransfusionTestMethod getMethod() {return theMethod;}
	public void setMethod(VocTransfusionTestMethod aMethod) {theMethod = aMethod;}

	/** Метод */
	private VocTransfusionTestMethod theMethod;
	/** Результат пробы */
	private VocTransfusionTestResult theResult;
}
