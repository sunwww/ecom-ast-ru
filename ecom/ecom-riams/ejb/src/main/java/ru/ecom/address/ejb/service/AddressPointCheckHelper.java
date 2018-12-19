package ru.ecom.address.ejb.service;

import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 *
 */
public class AddressPointCheckHelper {
    private static final Logger LOG = Logger.getLogger(AddressPointCheckHelper.class);

    public AddressPointCheck parsePoint(String aStr) {
    	StringBuilder houseNumber = new StringBuilder();
        StringBuilder houseBuilding = new StringBuilder();
        StringBuilder onlyChars = new StringBuilder();
        boolean isBuilding = false ;
        for (char c : aStr.toCharArray()) {
        	if (isBuilding) {
        		houseBuilding.append(c) ;
        	} else {
            if(Character.isUpperCase(c)) {
                if(houseBuilding.length()==0) {
                    houseBuilding.append(c) ;
                } else {
                    throw new IllegalArgumentException("Корпус дома обозначается только одной заглавной буквой") ;
                }
            }else if (c=='к') {
            	isBuilding=true ;
            } else {
                houseNumber.append(c) ;
                if(!Character.isDigit(c)) {
                    onlyChars.append(c) ;
                }
            }
        }
      }

        if(onlyChars.length()>0) {
        	
            String o = onlyChars.toString() ;
            LOG.info("----"+o) ;
            o = checkOnlyOneOrNo(o, "влд") ;
            o = checkOnlyOneOrNo(o, "стр") ;
            o = checkOnlyOneOrNo(o, "к") ;
            o = checkOnlyOneOrNo(o, "/") ;
            if(o.length()>0) {
                throw new IllegalArgumentException("Не допускается ввод "+o) ;
            }
        }

        return houseBuilding.length()>0
                ? new AddressPointCheck(houseNumber.toString(), houseBuilding.toString())
                : new AddressPointCheck(houseNumber.toString()) ;
    }

    private String checkOnlyOneOrNo(String aStr, String aAllowed) {
        if(aStr.indexOf(aAllowed)!=aStr.lastIndexOf(aAllowed)) {
                throw new IllegalArgumentException("'"+aAllowed+"' должно встречаться только один раз") ;
        }
        return aStr.replace(aAllowed,"") ;
    }

    public List<AddressPointCheck> parsePoints(String aStr) {
        StringTokenizer st = new StringTokenizer(aStr, " ,;.\n\r");
        LinkedList<AddressPointCheck> ret = new LinkedList<>();
        TreeSet<String> occurs = new TreeSet<>();
        while(st.hasMoreTokens()) {
            String token = st.nextToken() ;
            try {
            	if(token.indexOf('[')>=0) {
	                List<AddressPointCheck> diapasone = parseDiapasoneFlat(token) ;
	                for (AddressPointCheck addressPoint : diapasone) {
	                    checkOccurs(occurs, addressPoint);
	                    ret.add(addressPoint) ;
	                }
                } else if(token.startsWith("Н(") || token.startsWith("Ч(") || token.contains("-")) {
                    List<AddressPointCheck> diapasone = parseDiapasone(token) ;
                    for (AddressPointCheck addressPoint : diapasone) {
                        checkOccurs(occurs, addressPoint);
                        ret.add(addressPoint) ;
                    }
                } else {
                    AddressPointCheck p = parsePoint(token) ;
                    checkOccurs(occurs, p);

                    ret.add(p) ;
                }
            } catch (Exception e) {
            	LOG.error(e);
                throw new IllegalArgumentException(token+" - "+e.getMessage(),e) ;
            }
        }
//        for (AddressPointCheck point : ret) {
//        }
        return ret ;
    }

    /**
     * Строит индекс дом_корпус_квартира
     * Проверяет на наличие
     * Добавляет
     * @param occurs
     * @param p
     */
    private void checkOccurs(TreeSet<String> occurs, AddressPointCheck p) {
        // проверка на дублирование
        StringBuilder key = new StringBuilder(p.getNumber());
        key.append(".") ;
        if(p.getBuilding()!=null) {
            key.append(p.getBuilding()) ;
        }
        key.append(".") ;
        if(p.getFlat()!=null) {
            if(occurs.contains(key.toString())) {
                throw new IllegalArgumentException("Дом "+key+" поделен по квартирам.") ;
            } else {
            	key.append(p.getFlat()) ;
            }
        }
        if(occurs.contains(key.toString())) {
            throw new IllegalArgumentException("Адрес '"+key+"' уже встречается") ;
        } else {
            occurs.add(key.toString()) ;
        }
    }

    /**
     * Построение диапазона квартир
     * @return
     */
    private List<AddressPointCheck> parseDiapasoneFlat(String aStr) {
    	if(aStr.indexOf('-')<0) throw new IllegalArgumentException("Не введен символ '-' для указания диапазона квартир") ;
    	if(aStr.indexOf(']')<0) throw new IllegalArgumentException("Не введен символ ']' для указания конца диапазона квартир") ;
    	if(aStr.indexOf('[')==0) throw new IllegalArgumentException("Нет номера дома при указании диапазона квартир") ;
        LinkedList<AddressPointCheck> ret = new LinkedList<>();
    	// получение дома и корпуса
    	String houseAndBuildingPart = aStr.substring(0, aStr.indexOf('[')) ;
    	AddressPointCheck houseAndBuildingPointCheck = parsePoint(houseAndBuildingPart);
    	String flatPart = aStr.substring(aStr.indexOf('[')) ;
    	Integer from ;
    	Integer to ;
    	StringTokenizer st = new StringTokenizer( flatPart, "[ -]") ;
    	if(st.hasMoreTokens()) {
    		from = Integer.parseInt(st.nextToken()) ;
    		if(st.hasMoreTokens()) {
    			to = Integer.parseInt(st.nextToken()) ;
                if(to-from>1000) throw new IllegalArgumentException("Слишком большой диапазон квартир. Максимум - 1000") ;
                if(to<from) throw new IllegalArgumentException("Правая часть диапазона квартир не должна быть меньше левой") ;
                if(to.equals(from)) throw new IllegalArgumentException("Правая часть диапазона квартир не должна быть равна левой") ;
    			for(int i=from; i<=to; i++) {
    				ret.add(new AddressPointCheck(
    						houseAndBuildingPointCheck.getNumber()
    						, houseAndBuildingPointCheck.getBuilding().length()==0?null:houseAndBuildingPointCheck.getBuilding()
    						, String.valueOf(i)
    						)) ;
    			}
    		} else {
    			throw new IllegalArgumentException("Нет правой части диапазона квартир") ;
    		}
    	} else {
            throw new IllegalArgumentException("Нет левой части диапазона квартир") ;
    	}
    	return ret;
    }
    
    private List<AddressPointCheck> parseDiapasone(String aStr) {
        StringTokenizer st = new StringTokenizer(aStr, "ЧН()- ");
        LinkedList<AddressPointCheck> ret = new LinkedList<>();
        Integer from ;
        Integer to ;
        if(st.hasMoreTokens()) {
            from = Integer.parseInt(st.nextToken()) ;
            if(st.hasMoreTokens()) {
                to = Integer.parseInt(st.nextToken()) ;
                if(to-from>1000) throw new IllegalArgumentException("Слишком большой диапазон домов. Максимум - 1000") ;
                if(to<from) throw new IllegalArgumentException("Правая часть диапазона не должна быть меньше левой") ;
                if(to.equals(from)) throw new IllegalArgumentException("Правая часть диапазона не должна быть равна левой") ;
                for(int i=from; i<=to; i++) {

                    boolean canAdd = true ;
                    if(aStr.toUpperCase().startsWith("Ч") && i%2==1) {
                        canAdd  = false ;
                    } else if(aStr.toUpperCase().startsWith("Н") && i%2==0) {
                        canAdd = false ;
                    }
                    if(canAdd) ret.add(new AddressPointCheck(String.valueOf(i)));
                }
            } else {
                throw new IllegalArgumentException("Нет правой части диапазона") ;
            }
        } else {
            throw new IllegalArgumentException("Нет левой части диапазона") ;
        }
        return ret ;
    }
    
    public static void main(String[] args) {
    	AddressPointCheckHelper helper = new AddressPointCheckHelper() ;
    	List<AddressPointCheck> list = helper.parsePoints("12/12, 69Б[10-13]") ;
    	LOG.info(list);
    }
     
}
