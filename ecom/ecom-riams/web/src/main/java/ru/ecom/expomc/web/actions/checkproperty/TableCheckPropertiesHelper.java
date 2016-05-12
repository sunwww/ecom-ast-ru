package ru.ecom.expomc.web.actions.checkproperty;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import ru.ecom.expomc.ejb.services.form.check.ChecksTableForm;
import ru.ecom.expomc.ejb.services.form.check.ChecksTableFormRow;
import ru.ecom.expomc.ejb.services.form.check.ChecksTableFormRowOn;

/**
 */
public class TableCheckPropertiesHelper {

    public static void printForm(ChecksTableForm aForm, JspWriter out) throws IOException {
        out.println("<form name='exp_checksTableForm' method='post' action='exp_checkTablePropertiesSave.do' id='exp_checksTableForm'>") ;
        out.println("<input type='hidden' name='format' value='"+aForm.getFormat()+"'/>") ;
        int index = 0 ;
        for (ChecksTableFormRow row : aForm.getChecksTableFormRows()) {
            index++ ;
            printRow(index, row, out) ;
        }
        out.println("<br><br>") ;
        out.println("<input type='submit' value='Сохранить изменения' />" ) ;
        out.println("</form>") ;
    }

    private static void printRow(int aIndex, ChecksTableFormRow row, JspWriter out) throws IOException {
        out.println("<h3>"+aIndex+" <span class='dbf'>"+row.getFormatFieldName()+ " </span>  "+row.getComment()+" <span class='prop'> "+row.getProperty()+" "+row.getDbfInfo()+"</span> </h3>") ;
        out.println("<table>") ;
        out.println("<tr>") ;
        int index = 0 ;
        boolean odd = false ;
        for (ChecksTableFormRowOn on : row.getOns()) {
            if( (index % 2)==0 ) {
                out.println("</tr><tr>") ;
            }
            out.println("<td>") ;
            printOn(row, on, out) ;
            out.println("</td>") ;
            index++ ;
        }
        out.println("</tr>") ;
        out.println("</table>") ;
    }

    private static void printOn(ChecksTableFormRow row, ChecksTableFormRowOn on, JspWriter out) throws IOException {
        String key = row.getProperty() + "_"+on.getKey() ;
        out.print("<input id='"+key+"' name='"+key+"' type='checkbox' ") ;
        out.print(" onclick=\"onChange('"+key+"')\"") ;
        if(on.getValue()) {
            out.print(" checked='checked' ") ;
            out.print(" class='on' ") ;
        } else {
            out.print(" class='on' ") ;
        }
        out.println("/>") ;
        out.println("<label id='"+key+"Label' for='"+key
                +"' class='"+(on.getValue()?"on":"off")+"' >"+on.getName()+"</label>") ;
    }


}
