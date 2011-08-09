package test.entity;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.LinkedList;
import java.util.List;

import ru.ecom.ejb.domain.simple.BaseEntity;

public class TestGenericList {

	  private static void print(TypeVariable v) {
	      System.out.println("Type variable");
	      System.out.println("Name: " + v.getName());
	      System.out.println("Declaration: " + 
	                         v.getGenericDeclaration());
	      System.out.println("Bounds:");
	      for (Type t : v.getBounds()) {
	         print(t);
	      }
	   }
	   // Prints information about a wildcard type
	   private static void print(WildcardType wt) {
	      System.out.println("Wildcard type");
	      System.out.println("Lower bounds:");
	      for (Type b : wt.getLowerBounds()) {
	         print(b);
	      }

	      System.out.println("Upper bounds:");
	      for (Type b : wt.getUpperBounds()) {
	         print(b);
	      }
	   }

	   // Prints information about a parameterized type
	   private static void print(ParameterizedType pt) {
	      System.out.println("Parameterized type");
	      System.out.println("Owner: " + pt.getOwnerType());
	      System.out.println("Raw type: " + pt.getRawType());
	      
	      for (Type actualType : pt.getActualTypeArguments()) {
	         print(actualType);
	      }
	   }

	   // Prints information about a generic array type
	   private static void print(GenericArrayType gat) {
	      System.out.println("Generic array type");
	      System.out.println("Type of array: ");
	      print(gat.getGenericComponentType());
	   }

	   /**
	    * Prints information about a type. The nested 
	    * if/else-if chain calls the
	    * appropriate overloaded print method for the 
	    * type. If t is just a Class,
	    * we print it directly.
	    */

	   private static void print(Type t) {
	      if (t instanceof TypeVariable) {
	         print((TypeVariable)t);
	      } else if (t instanceof WildcardType) {
	         print((WildcardType)t);
	      } else if (t instanceof ParameterizedType) {
	         print((ParameterizedType)t);
	      } else if (t instanceof GenericArrayType) {
	         print((GenericArrayType)t);
	      } else {
	         System.out.println(t);
	      }
	   }	
	public static void main(String args[] ) {
		List<BaseEntity> list = new LinkedList<BaseEntity>();
		
		Class clazz = list.getClass() ;
		p("class="+clazz) ;
		//clazz.get
		p("genericSuperclass="+clazz.getGenericSuperclass() ) ;
		print(clazz.getTypeParameters()[0]) ;
		
		for(TypeVariable tv : clazz.getTypeParameters()) {
			p(" gd = "+tv.getGenericDeclaration()) ;
			p(" cl+"+tv.getClass()) ;
			p(" tv "+tv+"[ name= "+tv.getName()+",  gd = "+tv.getGenericDeclaration()+"]") ;
			tv.getBounds() ;
			for(Type t : tv.getBounds()) {
				//ParameterizedType pt = (ParameterizedType) t ;
				System.out.println("  t="+t+" ");
				print(t) ;
			}
		}
		
	}
	
	private static void p(String aStr) {
		System.out.println(aStr);
	}
	
	
}
