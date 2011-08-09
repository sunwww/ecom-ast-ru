package ru.ecom.mis.ejb.service.diet;


import java.util.List;
import ru.ecom.mis.ejb.form.diet.voc.VocFoodStuffForm;


/**
 */
public interface IFoodStuffService {

    List<VocFoodStuffForm> findFoodStuff(String aName) ;
    }   
    

   




