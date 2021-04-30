package ru.ecom.mis.ejb.service.medcase;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AddressSheetParentPrintForm  implements Serializable {
	/** Документ 1 */
	private AddressSheetPrintForm doc1;
	
	/** Документ 2 */
	private AddressSheetPrintForm doc2;
	
	/** Документ 3 */
	private AddressSheetPrintForm doc3;
	
	/** Документ 4 */
	private AddressSheetPrintForm doc4;
}
