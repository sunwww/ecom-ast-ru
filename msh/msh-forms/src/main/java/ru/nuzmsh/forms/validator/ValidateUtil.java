package ru.nuzmsh.forms.validator;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import ru.nuzmsh.forms.validator.transforms.DoIntegerString;
import ru.nuzmsh.forms.validator.transforms.DoIntegerStringTransform;
import ru.nuzmsh.forms.validator.transforms.DoUpperCase;
import ru.nuzmsh.forms.validator.transforms.DoUpperCaseTransform;
import ru.nuzmsh.forms.validator.transforms.DoTrimString;
import ru.nuzmsh.forms.validator.transforms.DoTrimStringTransform;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.transforms.DoDateStringTransform;
import ru.nuzmsh.forms.validator.transforms.DoTimeStringTransform;
import ru.nuzmsh.forms.validator.transforms.DoInputNonLat;
import ru.nuzmsh.forms.validator.transforms.DoInputNonLatTransform;
import ru.nuzmsh.forms.validator.validators.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ESinev
 *         Date: 02.11.2005
 *         Time: 12:21:24
 */
public class ValidateUtil {

    private static final HashMap<Class, IValidator> VALIDATORS = new HashMap<Class, IValidator>();
    private static final HashMap<Class, ITransform> TRANSFORMS = new HashMap<Class, ITransform>();

    static {
        VALIDATORS.put(DateString.class, new DateStringValidator());
        VALIDATORS.put(Required.class, new RequiredValidator());
        VALIDATORS.put(MinLength.class, new MinLengthValidator());
        VALIDATORS.put(Min.class, new MinValidator());
        VALIDATORS.put(TimeString.class, new TimeStringValidator());
        VALIDATORS.put(SnilsString.class, new SnilsStringValidator());
        VALIDATORS.put(VInputNonLat.class, new VInputNonLatValidator());
        VALIDATORS.put(MinDate.class, new MinDateValidator());
        VALIDATORS.put(MaxLength.class, new MaxLengthValidator());
        VALIDATORS.put(Mkb.class, new MkbValidator());
        VALIDATORS.put(VInputFIOByMaskOmc.class, new VInputFIOByMaskOmcValidator());
        VALIDATORS.put(IntegerString.class, new IntegerStringValidator());
        VALIDATORS.put(EmailString.class, new EmailStringValidator());
        VALIDATORS.put(PhoneString.class, new PhoneStringValidator());
        VALIDATORS.put(MaxDateCurrent.class, new MaxDateCurrentValidator());

        TRANSFORMS.put(DoTrimString.class, new DoTrimStringTransform());
        TRANSFORMS.put(DoUpperCase.class, new DoUpperCaseTransform());
        TRANSFORMS.put(DoIntegerString.class, new DoIntegerStringTransform());
        TRANSFORMS.put(DoDateString.class, new DoDateStringTransform());
        TRANSFORMS.put(DoTimeString.class, new DoTimeStringTransform());
        TRANSFORMS.put(DoInputNonLat.class, new DoInputNonLatTransform());
    }

    public static ActionErrors validate(final Object aObject, HttpServletRequest aRequest) {

        ActionErrors errors = new ActionErrors();
        Class clazz = aObject.getClass();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                transform(annotation, method, aObject);
            }
            for (Annotation annotation : annotations) {
                validate(annotation, method, aObject, errors, aRequest);
            }
        }
        return errors;
    }

    private static void validate(Annotation aAnnotation, Method aMethod, Object aObject, ActionErrors aErrors, HttpServletRequest aRequest) {
    	
        IValidator validator = VALIDATORS.get(aAnnotation.annotationType());
        if (validator != null) {
            try {
                Object value = aMethod.invoke(aObject);
                validator.validate(value, aAnnotation,aRequest);
            } catch (IllegalAccessException e) {
                addError(aErrors, aMethod, e.getMessage());
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                addError(aErrors, aMethod, e.getMessage());
                e.printStackTrace();
            } catch (ValidateException e) {
                addError(aErrors, aMethod, e.getMessage());
            }
        }
    }

    private static void transform(Annotation aAnnotation, Method aMethod, Object aObject) {
        ITransform transform = TRANSFORMS.get(aAnnotation.annotationType());
        if (transform != null) {
            try {
                Object ret = transform.transform(aMethod.invoke(aObject));
                if (ret != null) {
                    BeanUtils.setProperty(aObject, getPropertyName(aMethod), ret);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }
    }

    private static void addError(ActionErrors aErrors, Method aMethod, String aMessage) {
        aErrors.add(getPropertyName(aMethod), new ActionMessage(aMessage));
    }

    public static String getPropertyName(Method aMethod) {
        String propertyName = aMethod.getName();
        if (propertyName.startsWith("get")) {
            propertyName = propertyName.substring(3);
            propertyName = String.valueOf(propertyName.charAt(0)).toLowerCase()
                    + propertyName.substring(1);
        }
        return propertyName;
    }

}
