package ru.ecom.mis.ejb.domain.medstat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.medstat.voc.MedStatPeriod;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Строка данных отчета Медстат
 * @author azviagin
 * provinceCode+areaCode=MedStatArea.code
 * substr(formCode,1,3)+tableCode+stringCode=MedStatString.code
 */

@Comment("Строка данных отчета Медстат")
@Entity
@Table(name="medstat",schema="SQLUser")
@AIndexes({
	@AIndex(unique = false, properties={"provinceCode"})
	,@AIndex(unique = false, properties={"formCode"})
	,@AIndex(unique = false, properties={"tableCode"})
	,@AIndex(unique = false, properties={"stringCode"})
	,@AIndex(unique = false, properties={"statPeriod"})
	,@AIndex(name="kodif", unique = false, properties={"provinceCode","areaCode"})
	,@AIndex(name="identify", unique = true, properties={"provinceCode","areaCode","formCode","tableCode","stringCode","statPeriod"})
})
public class MedStatDataString extends BaseEntity{

	/** Код области */
	@Comment("Код области")
	@Column(name="a1")
	public String getProvinceCode() {
		return theProvinceCode;
	}

	public void setProvinceCode(String aProvinceCode) {
		theProvinceCode = aProvinceCode;
	}

	/** Код области */
	private String theProvinceCode;
	
	/** Код района */
	@Comment("Код района")
	@Column(name="a2")
	public String getAreaCode() {
		return theAreaCode;
	}

	public void setAreaCode(String aAreaCode) {
		theAreaCode = aAreaCode;
	}

	/** Код района */
	private String theAreaCode;
	
	/** Код формы */
	@Comment("Код формы")
	@Column(name="a4")
	public String getFormCode() {
		return theFormCode;
	}

	public void setFormCode(String aFormCode) {
		theFormCode = aFormCode;
	}

	/** Код формы */
	private String theFormCode;
	
	/** Код таблицы */
	@Comment("Код таблицы")
	@Column(name="a5")
	public String getTableCode() {
		return theTableCode;
	}

	public void setTableCode(String aTableCode) {
		theTableCode = aTableCode;
	}

	/** Код таблицы */
	private String theTableCode;
	
	/** Код строки */
	@Comment("Код строки")
	@Column(name="a6")
	public String getStringCode() {
		return theStringCode;
	}

	public void setStringCode(String aStringCode) {
		theStringCode = aStringCode;
	}

	/** Код строки */
	private String theStringCode;
	
	/** Колонка 1 */
	@Comment("Колонка 1")
	@Column(name="a81")
	public Double getColumn1() {
		return theColumn1;
	}

	public void setColumn1(Double aColumn1) {
		theColumn1 = aColumn1;
	}

	/** Колонка 1 */
	private Double theColumn1;
	
	/** Колонка 2 */
	@Comment("Колонка 2")
	@Column(name="a82")
	public Double getColumn2() {
		return theColumn2;
	}

	public void setColumn2(Double aColumn2) {
		theColumn2 = aColumn2;
	}

	/** Колонка 2 */
	private Double theColumn2;
	
	/** Колонка 3 */
	@Comment("Колонка 3")
	@Column(name="a83")
	public Double getColumn3() {
		return theColumn3;
	}

	public void setColumn3(Double aColumn3) {
		theColumn3 = aColumn3;
	}

	/** Колонка 3 */
	private Double theColumn3;
	
	/** Колонка 4 */
	@Comment("Колонка 4")
	@Column(name="a84")
	public Double getColumn4() {
		return theColumn4;
	}

	public void setColumn4(Double aColumn4) {
		theColumn4 = aColumn4;
	}

	/** Колонка 4 */
	private Double theColumn4;
	
	/** Колонка 5 */
	@Comment("Колонка 5")
	@Column(name="a85")
	public Double getColumn5() {
		return theColumn5;
	}

	public void setColumn5(Double aColumn5) {
		theColumn5 = aColumn5;
	}

	/** Колонка 5 */
	private Double theColumn5;
	
	/** Колонка 6 */
	@Comment("Колонка 6")
	@Column(name="a86")
	public Double getColumn6() {
		return theColumn6;
	}

	public void setColumn6(Double aColumn6) {
		theColumn6 = aColumn6;
	}

	/** Колонка 6 */
	private Double theColumn6;
	
	/** Колонка 7 */
	@Comment("Колонка 7")
	@Column(name="a87")
	public Double getColumn7() {
		return theColumn7;
	}

	public void setColumn7(Double aColumn7) {
		theColumn7 = aColumn7;
	}

	/** Колонка 7 */
	private Double theColumn7;
	
	/** Колонка 8 */
	@Comment("Колонка 8")
	@Column(name="a88")
	public Double getColumn8() {
		return theColumn8;
	}

	public void setColumn8(Double aColumn8) {
		theColumn8 = aColumn8;
	}

	/** Колонка 8 */
	private Double theColumn8;
	
	/** Колонка 9 */
	@Comment("Колонка 9")
	@Column(name="a89")
	public Double getColumn9() {
		return theColumn9;
	}

	public void setColumn9(Double aColumn9) {
		theColumn9 = aColumn9;
	}

	/** Колонка 9 */
	private Double theColumn9;
	
	/** Колонка 10 */
	@Comment("Колонка 10")
	@Column(name="a810")
	public Double getColumn10() {
		return theColumn10;
	}

	public void setColumn10(Double aColumn10) {
		theColumn10 = aColumn10;
	}

	/** Колонка 10 */
	private Double theColumn10;
	
	/** Колонка 11 */
	@Comment("Колонка 11")
	@Column(name="a811")
	public Double getColumn11() {
		return theColumn11;
	}

	public void setColumn11(Double aColumn11) {
		theColumn11 = aColumn11;
	}

	/** Колонка 11 */
	private Double theColumn11;
	
	/** Колонка 12 */
	@Comment("Колонка 12")
	@Column(name="a812")
	public Double getColumn12() {
		return theColumn12;
	}

	public void setColumn12(Double aColumn12) {
		theColumn12 = aColumn12;
	}

	/** Колонка 12 */
	private Double theColumn12;
	
	/** Колонка 13 */
	@Comment("Колонка 13")
	@Column(name="a813")
	public Double getColumn13() {
		return theColumn13;
	}

	public void setColumn13(Double aColumn13) {
		theColumn13 = aColumn13;
	}

	/** Колонка 13 */
	private Double theColumn13;
	
	/** Колонка 14 */
	@Comment("Колонка 14")
	@Column(name="a814")
	public Double getColumn14() {
		return theColumn14;
	}

	public void setColumn14(Double aColumn14) {
		theColumn14 = aColumn14;
	}

	/** Колонка 14 */
	private Double theColumn14;
	
	/** Колонка 15 */
	@Comment("Колонка 15")
	@Column(name="a815")
	public Double getColumn15() {
		return theColumn15;
	}

	public void setColumn15(Double aColumn15) {
		theColumn15 = aColumn15;
	}

	/** Колонка 15 */
	private Double theColumn15;
	
	/** Колонка 16 */
	@Comment("Колонка 16")
	@Column(name="a816")
	public Double getColumn16() {
		return theColumn16;
	}

	public void setColumn16(Double aColumn16) {
		theColumn16 = aColumn16;
	}

	/** Колонка 16 */
	private Double theColumn16;
	
	/** Колонка 17 */
	@Comment("Колонка 17")
	@Column(name="a817")
	public Double getColumn17() {
		return theColumn17;
	}

	public void setColumn17(Double aColumn17) {
		theColumn17 = aColumn17;
	}

	/** Колонка 17 */
	private Double theColumn17;
	
	/** Колонка 18 */
	@Comment("Колонка 18")
	@Column(name="a818")
	public Double getColumn18() {
		return theColumn18;
	}

	public void setColumn18(Double aColumn18) {
		theColumn18 = aColumn18;
	}

	/** Колонка 18 */
	private Double theColumn18;
	
	/** Колонка 19 */
	@Comment("Колонка 19")
	@Column(name="a819")
	public Double getColumn19() {
		return theColumn19;
	}

	public void setColumn19(Double aColumn19) {
		theColumn19 = aColumn19;
	}

	/** Колонка 19 */
	private Double theColumn19;
	
	/** Колонка 20 */
	@Comment("Колонка 20")
	@Column(name="a820")
	public Double getColumn20() {
		return theColumn20;
	}

	public void setColumn20(Double aColumn20) {
		theColumn20 = aColumn20;
	}

	/** Колонка 20 */
	private Double theColumn20;
	
	/** Колонка 21 */
	@Comment("Колонка 21")
	@Column(name="a821")
	public Double getColumn21() {
		return theColumn21;
	}

	public void setColumn21(Double aColumn21) {
		theColumn21 = aColumn21;
	}

	/** Колонка 21 */
	private Double theColumn21;
	
	/** Колонка 22 */
	@Comment("Колонка 22")
	@Column(name="a822")
	public Double getColumn22() {
		return theColumn22;
	}

	public void setColumn22(Double aColumn22) {
		theColumn22 = aColumn22;
	}

	/** Колонка 22 */
	private Double theColumn22;
	
	/** Колонка 23 */
	@Comment("Колонка 23")
	@Column(name="a823")
	public Double getColumn23() {
		return theColumn23;
	}

	public void setColumn23(Double aColumn23) {
		theColumn23 = aColumn23;
	}

	/** Колонка 23 */
	private Double theColumn23;
	
	/** Колонка 24 */
	@Comment("Колонка 24")
	@Column(name="a824")
	public Double getColumn24() {
		return theColumn24;
	}

	public void setColumn24(Double aColumn24) {
		theColumn24 = aColumn24;
	}

	/** Колонка 24 */
	private Double theColumn24;
	
	/** Колонка 25 */
	@Comment("Колонка 25")
	@Column(name="a825")
	public Double getColumn25() {
		return theColumn25;
	}

	public void setColumn25(Double aColumn25) {
		theColumn25 = aColumn25;
	}

	/** Колонка 25 */
	private Double theColumn25;
	
	/** Колонка 26 */
	@Comment("Колонка 26")
	@Column(name="a826")
	public Double getColumn26() {
		return theColumn26;
	}

	public void setColumn26(Double aColumn26) {
		theColumn26 = aColumn26;
	}

	/** Колонка 26 */
	private Double theColumn26;
	
	/** Колонка 27 */
	@Comment("Колонка 27")
	@Column(name="a827")
	public Double getColumn27() {
		return theColumn27;
	}

	public void setColumn27(Double aColumn27) {
		theColumn27 = aColumn27;
	}

	/** Колонка 27 */
	private Double theColumn27;
	
	/** Колонка 28 */
	@Comment("Колонка 28")
	@Column(name="a828")
	public Double getColumn28() {
		return theColumn28;
	}

	public void setColumn28(Double aColumn28) {
		theColumn28 = aColumn28;
	}

	/** Колонка 28 */
	private Double theColumn28;
	
	/** Комментарии */
	@Comment("Комментарии")
	@Column(name="srt")
	public String getComments() {
		return theComments;
	}

	public void setComments(String aComments) {
		theComments = aComments;
	}

	/** Комментарии */
	private String theComments;
	
	/** Служебное поле 1 */
	@Comment("Служебное поле 1")
	@Column(name="n1")
	public Double getAuxiliary1() {
		return theAuxiliary1;
	}

	public void setAuxiliary1(Double aAuxiliary1) {
		theAuxiliary1 = aAuxiliary1;
	}

	/** Служебное поле 1 */
	private Double theAuxiliary1;
	
	/** Служебное поле 2 */
	@Comment("Служебное поле 2")
	@Column(name="n2")
	public Double getAuxiliary2() {
		return theAuxiliary2;
	}

	public void setAuxiliary2(Double aAuxiliary2) {
		theAuxiliary2 = aAuxiliary2;
	}

	/** Служебное поле 2 */
	private Double theAuxiliary2;
	
	/** Статистический период */
	@Comment("Статистический период")
	@OneToOne
	@JoinColumn(name="stat_period_id")
	public MedStatPeriod getStatPeriod() {
		return theStatPeriod;
	}

	public void setStatPeriod(MedStatPeriod aStatPeriod) {
		theStatPeriod = aStatPeriod;
	}

	/** Статистический период */
	private MedStatPeriod theStatPeriod;
	

}
