package ru.ecom.expomc.ejb.services.voc.allvalues;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import ru.ecom.ejb.services.voc.helper.ArrayAllValue;
import ru.ecom.expomc.ejb.services.check.checkers.BeanShellCheck;
import ru.ecom.expomc.ejb.services.check.checkers.ChangeIfEmpty;
import ru.ecom.expomc.ejb.services.check.checkers.ChangeLatinToRussian;
import ru.ecom.expomc.ejb.services.check.checkers.ChangeLowerCase;
import ru.ecom.expomc.ejb.services.check.checkers.ChangeOToZero;
import ru.ecom.expomc.ejb.services.check.checkers.ChangeRussianToLatin;
import ru.ecom.expomc.ejb.services.check.checkers.ChangeStringEquals;
import ru.ecom.expomc.ejb.services.check.checkers.ChangeSubstring;
import ru.ecom.expomc.ejb.services.check.checkers.ChangeUpperCase;
import ru.ecom.expomc.ejb.services.check.checkers.ChangeZeroToLatinO;
import ru.ecom.expomc.ejb.services.check.checkers.CheckDates;
import ru.ecom.expomc.ejb.services.check.checkers.CheckInDomain;
import ru.ecom.expomc.ejb.services.check.checkers.CheckMkbDiapazon;
import ru.ecom.expomc.ejb.services.check.checkers.CheckNotInDomain;
import ru.ecom.expomc.ejb.services.check.checkers.RequiredCheck;
import ru.ecom.expomc.ejb.services.check.checkers.registry.ChangeCasePrice;
import ru.ecom.expomc.ejb.services.check.checkers.registry.ChangeFromExternalPatientInfo;
import ru.ecom.expomc.ejb.services.check.checkers.registry.ChangeFromExternalPolicyInfo;
import ru.ecom.expomc.ejb.services.check.checkers.registry.ChangeWorksNameAndNewCode;
import ru.ecom.expomc.ejb.services.check.checkers.registry.CheckCanUpLevelByOsl;
import ru.ecom.expomc.ejb.services.check.checkers.registry.CheckDnevnoyStacionar;
import ru.ecom.expomc.ejb.services.check.checkers.registry.CheckDoubleReestr;
import ru.ecom.expomc.ejb.services.check.checkers.registry.CheckIsChild;
import ru.ecom.expomc.ejb.services.check.checkers.registry.CheckIsPlanovaya;
import ru.ecom.expomc.ejb.services.check.checkers.registry.CheckOnko;
import ru.ecom.expomc.ejb.services.check.checkers.registry.CheckPoliclinic;
import ru.ecom.expomc.ejb.services.check.checkers.registry.CheckProvisor;
import ru.ecom.expomc.ejb.services.check.checkers.registry.CheckProvisorMoreThan4;
import ru.ecom.expomc.ejb.uc.snils.ChangeSnilsChecker;
import ru.ecom.expomc.ejb.uc.snils.ChangeSnilsNormalizeChecker;
import ru.ecom.expomc.ejb.uc.snils.CheckCompanyPolicySeriesAndNumber;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Список допустимыъ проверок
 */
public class AllowedChecksAllValues extends ArrayAllValue {
    public AllowedChecksAllValues() {
        // ДОБАВЛЯТЬ ТОЛЬКО В КОНЕЦ !!!
    	
        add(RequiredCheck.class) ;
        add(CheckInDomain.class) ;
        add(CheckNotInDomain.class) ;
        add(ChangeStringEquals.class) ;
        add(CheckMkbDiapazon.class) ;
        add(CheckDates.class) ;
        add(ChangeUpperCase.class) ;
        add(ChangeLowerCase.class) ;
        add(ChangeRussianToLatin.class) ;
        add(ChangeLatinToRussian.class) ;
        add(ChangeOToZero.class) ;
        add(ChangeZeroToLatinO.class) ;

        add(ChangeCasePrice.class) ;
        add(CheckCanUpLevelByOsl.class) ;
        add(CheckDnevnoyStacionar.class) ;
        add(CheckIsChild.class) ;
        add(CheckIsPlanovaya.class) ;
        add(CheckOnko.class) ;
        add(CheckPoliclinic.class) ;
        add(CheckProvisor.class) ;
        add(CheckProvisorMoreThan4.class) ;
        
        add(BeanShellCheck.class) ;
        add(ChangeFromExternalPatientInfo.class) ;

        add(ChangeIfEmpty.class) ;
        
        add(ChangeFromExternalPolicyInfo.class) ;

        add(ChangeWorksNameAndNewCode.class) ;

        add(ChangeSnilsChecker.class) ;
        
        add(ChangeSnilsNormalizeChecker.class) ;

        add(CheckCompanyPolicySeriesAndNumber.class) ;

        add(ChangeSubstring.class) ;
        
        add(CheckDoubleReestr.class);
        
        // ДОБАВЛЯТЬ ТОЛЬКО В КОНЕЦ !!!
        
        
    }

    public Set<Map.Entry<Long,Class>> getChecksEntries() {
        return theHash.entrySet() ;
    }

    public Collection<Class> getChecksClasses() {
        return theHash.values();
    }

    public Class getCheckClassById(long aCheckId) {
        return theHash.get(aCheckId);
    }

    private void add(Class aClass) {
        theIndex++ ;
        Comment comment = (Comment) aClass.getAnnotation(Comment.class) ;
        StringBuilder sb = new StringBuilder();
        if(comment!=null) {
            sb.append(comment.value()) ;
        }
        sb.append(" (") ;
        sb.append(aClass.getSimpleName()) ;
        sb.append(")") ;
        addValue(String.valueOf(theIndex), sb.toString());
        theHash.put(theIndex, aClass) ;

    }

    private TreeMap<Long, Class> theHash = new TreeMap<Long, Class>();
    private long theIndex = 0 ;
}
