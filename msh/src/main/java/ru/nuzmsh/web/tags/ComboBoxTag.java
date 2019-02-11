package ru.nuzmsh.web.tags;


/**
 * @jsp.tag name="comboBox"
 *          display-name="Combo box Field"
 *          body-content="empty"
 *          description="ComboBox field JSP tag."
 */
public class ComboBoxTag extends AutoCompleteTag {

    /*
    protected Element getFieldElement2() throws JspException {
        select selectField = new select();
        selectField.setName(getProperty()) ;
        selectField.setID(getProperty()) ;
        try {
            selectField.setTitle(getAnnotationLabel() +" : "+getVocName()) ;
        } catch (NoSuchMethodException e) {
            selectField.setTitle(e+"") ;
            selectField.setStyle("background-color: red");

        }

        //selectField.set

        try {
            Collection options = VocHelperDelegate.locateVocHelper().findByAll(pageContext, getVocName()) ;
//            options.add(new VocValue("1", "Россия")) ;
//            options.add(new VocValue("2", "Англия")) ;
//            options.add(new VocValue("2", "Казахстан")) ;
            String id = getFormattedValue() ;
            boolean findedSelected = false ;
            for (Iterator iterator = options.iterator(); iterator.hasNext();) {
                VocValue value = (VocValue) iterator.next();
                option o = new option(value.getName(), value.getId()).addElement(value.getName()) ;
                if(id!=null && id.equals(value.getId())) {
                    o.setSelected(true) ;
                    findedSelected = true ;
                    o.setClass("selected") ;
                }
                selectField.addElement(o) ;
            }
            if(!findedSelected) {
                option nullOption = new option("&nbsp;", "NULL").addElement("&nbsp;") ;
                nullOption.setSelected(true) ;
                nullOption.setClass("selected") ;
                selectField.addElement(nullOption) ;
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (VocHelperLocateException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return selectField ;

    }

*/



}
