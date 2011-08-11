CREATE or REPLACE FUNCTION MisLpu_findMainMisLpuByOmcCode(aCode text)
   RETURNS BigInt
        AS 'ru.ecom.riamsproc.lpu.MisLpuProc.findMainMisLpuByOmcCode'
  LANGUAGE java ;

CREATE or REPLACE FUNCTION MedPolicyProc_updateAttachedLpuFromEpi()
   RETURNS Integer
        AS 'ru.ecom.riamsproc.patient.MedPolicyProc.updateAttachedLpuFromEpi'
  LANGUAGE java ;
 