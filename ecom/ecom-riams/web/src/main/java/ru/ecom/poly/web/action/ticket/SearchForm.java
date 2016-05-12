package ru.ecom.poly.web.action.ticket;

import java.text.SimpleDateFormat;
import java.util.*;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoUpperCase;
import ru.nuzmsh.forms.validator.validators.DateString;

/**
 * Created by IntelliJ IDEA.
 * User: morgun
 * Date: 29.01.2007
 * Time: 9:02:40
 * To change this template use File | Settings | File Templates.
 */
public class SearchForm extends BaseValidatorForm {

    /** Специалист **/
	public SearchForm(){
		Date ad = new Date(System.currentTimeMillis());		
		SimpleDateFormat FORMAT_1 = new SimpleDateFormat("dd.MM.yyyy");
        setDateFilter(FORMAT_1.format(ad));
	}

    public Long getDoctor() { return theDoctor; }
    public void setDoctor(Long aDoctor) { theDoctor = aDoctor; }


    /** Номер талона */
    @DoUpperCase
	public String getNumberTicket() {return theNumberTicket;}
	public void setNumberTicket(String aNumberTicket) {theNumberTicket = aNumberTicket;}

    /** @return Дата **/
    @DateString
    @DoDateString
    public String getDateFilter() { return theDateFilter; }
    public void setDateFilter(String aDateFilter) { theDateFilter = aDateFilter; }

    /** Комментарии */
	public String getComment() {return theComment;}
	public void setComment(String aComment) {theComment = aComment;}

	/** Комментарии */
	private String theComment;
    /** Специалист **/
    private Long theDoctor;
	/** Номер талона */
	private String theNumberTicket;
    /** Дата **/
    private String theDateFilter;
}
