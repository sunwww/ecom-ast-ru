package test.ru.ecom.web.tags;

import ru.ecom.web.tags.OneToManyOneAutocompleteTag;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import java.util.Collection;
import java.util.LinkedList;
import java.lang.reflect.InvocationTargetException;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 *
 */
public class TestOneToManyOneAutocompleteTag {

    public static void main(String[] args) throws JSONException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        OneToManyOneAutocompleteTag tag = new OneToManyOneAutocompleteTag();
        LinkedList<ChildForm> childs = new LinkedList<ChildForm>();
        childs.add(new ChildForm(1L,124L,123L)) ;
        childs.add(new ChildForm(2L,124L,1123L)) ;
        childs.add(new ChildForm(12L,124L,1123L)) ;
        childs.add(new ChildForm(56L,124L,1123L)) ;
        ParentForm parent = new ParentForm();
        parent.setChilds(childs);

        String json = tag.getJson(parent, "childs") ;
        System.out.println("json = " + json);

        JSONObject obj = new JSONObject(json);
        JSONArray ar = obj.getJSONArray("childs") ;
        for(int i=0; i<ar.length(); i++) {
            JSONObject child = (JSONObject) ar.get(i) ;
            System.out.println("child.get(\"value\") = " + child.get("value"));

        }
        System.out.println("obj = " + obj);
    }

    static public class ParentForm {

        /** childs */
        public Collection<ChildForm> getChilds() { return theChilds ; }
        public void setChilds(Collection<ChildForm> aChilds) { theChilds = aChilds ; }

        /** childs */
        private Collection<ChildForm> theChilds ;
    }

    static public class ChildForm {
        public ChildForm(long aId, Long aParent, Long aValue) {
            theId = aId ;
            theParent = aParent ;
            theValue = aValue ;
        }

        /**  */
        public long getId() { return theId ; }
        public void setId(long aId) { theId = aId ; }

        /** Ид родителя */
        public Long getParent() { return theParent ; }
        public void setParent(Long aParent) { theParent = aParent ; }

        /** Значение */
        public Long getValue() { return theValue ; }
        public void setValue(Long aValue) { theValue = aValue ; }

        /** Значение */
        private Long theValue ;
        /** Ид родителя */
        private Long theParent ;
        /**  */
        private long theId ;
    }
}
