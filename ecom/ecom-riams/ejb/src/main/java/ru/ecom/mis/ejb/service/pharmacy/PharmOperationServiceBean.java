package ru.ecom.mis.ejb.service.pharmacy;

import ru.ecom.ejb.services.entityform.ILocalEntityFormService;

import javax.annotation.EJB;
import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by rkurbanov on 18.09.2017.
 */
@Stateless
@Remote(IPharmService.class)
public class PharmOperationServiceBean implements IPharmService {

   public static void setFunctionReserve(EntityManager aManager){
       String sql = "Create OR REPLACE FUNCTION pharmReseve (PharmDrugid integer,count real,drugPrescription integer,workfunc integer) RETURNS integer AS '\n" +
               "DECLARE\n" +
               "Date date;\n" +
               "Amount real;\n" +
               "StoreId bigint;\n" +
               "DrugId bigint;\n" +
               "nextId bigint;\n" +
               "BEGIN\n" +
               "select pd.dateincome, pd.amount, pd.pharmstorage_id,pd.drug_id from pharmdrug pd into Date,Amount,StoreId,DrugId where pd.id= $1;\n" +
               "Amount := Amount - $2;\n" +
               "insert into pharmoperation(dtype,dateoperation,timeoperation,amount,pharmdrug_id,prescription,workfunction_id)\n" +
               "values (''PharmReserveOperation'',current_date,current_time,$2,$1,$3,$4);\n" +
               "insert into pharmdrug (dateincome,amount,drug_id,pharmstorage_id,laststate_id)values (Date,Amount,DrugId,StoreId,$1) returning id into nextId;\n" +
               "update pharmdrug set nextstate_id = nextId where id = $1;\n" +
               "return $1;\n" +
               "END;\n" +
               "' LANGUAGE plpgsql;";
       aManager.createNativeQuery(sql).executeUpdate();
   }

    @EJB
    ILocalEntityFormService theEntityFormService;
    @PersistenceContext
    EntityManager theManager;
    @Resource
    SessionContext theContext;
}
