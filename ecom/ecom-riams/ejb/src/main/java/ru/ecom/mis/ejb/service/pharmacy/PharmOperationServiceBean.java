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
@Remote(IPharmOperationService.class)
public class PharmOperationServiceBean implements IPharmOperationService {

   public void setFunctionReserve(EntityManager aManager){
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

    public void setFunctionEndPrescription(){

       String sql = "Create OR REPLACE FUNCTION pharmEndPrescription(PresId integer, username text) RETURNS integer AS '\n" +
               "DECLARE\n" +
               "isclose boolean;\n" +
               "DateIn date;\n" +
               "amountOp real;\n" +
               "drugId integer;\n" +
               "actualId integer;\n" +
               "actualAmount real;\n" +
               "vocDrugId integer;\n" +
               "StorageId integer;\n" +
               "NextDrugId integer;\n" +
               "workfunct integer;\n" +
               "BEGIN\n" +
               "select case when canceldate is not null n true else false end from prescription into isclose where id = $1;\n" +
               "IF isclose=false n\n" +
               "select amount,pharmdrug_id from pharmoperation into amountOp,drugId where prescription = $1;\n" +
               "update prescription set canceldate=current_date, canceltime=current_time, cancelusername =$2 where id =$1;\n" +
               "select id,amount,drug_id,pharmstorage_id from pharmdrug into actualId,actualAmount,vocDrugId,StorageId where drug_id =(select drug_id from pharmdrug where id = drugId) and nextstate_id is null;\n" +
               "insert into pharmdrug (dateincome,amount,drug_id,pharmstorage_id,laststate_id)values (DateIn,(amountOp+actualAmount),vocDrugId,StorageId, actualId) returning id into NextDrugId;\n" +
               "update pharmdrug set nextstate_id = NextDrugId where id= actualId;\n" +
               "select wf.id from secuser into workfunct su left join workfunction wf on wf.secuser_id = su.id where su.login = $2;\n" +
               "insert into pharmoperation(dtype,dateoperation,timeoperation,amount,pharmdrug_id,prescription_id,workfunction_id) values (''PharmEndPrescriptOperation'',current_date,current_time,actualAmount,NextDrugId,$1,workfunct);\n" +
               "return 0;\n" +
               "ELSE return 1;\n" +
               "END IF;\n" +
               "END;\n" +
               "' LANGUAGE plpgsql;";
        manager.createNativeQuery(sql).executeUpdate();
    }

    @EJB
    ILocalEntityFormService entityFormService;
    @PersistenceContext
    EntityManager manager;
    @Resource
    SessionContext context;
}
