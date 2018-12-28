package ru.ecom.ejb.services.entityform.map;

import org.apache.log4j.Logger;
import org.objectweb.asm.*;
import ru.ecom.ejb.services.entityform.map.model.MapFormInfo;
import ru.ecom.ejb.services.entityform.map.model.forclass.ParentAnnotation;
import ru.ecom.ejb.services.entityform.map.model.forclass.WebTrailAnnotation;
import ru.ecom.ejb.services.util.ClassLoaderHelper;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.PropertyUtil;

import java.lang.reflect.Method;

public class MapClassVisitor extends ClassAdapter implements Opcodes {

	private static final String L_PERSIST = "Lru/nuzmsh/commons/formpersistence/annotation/Persist;";
	private static final String L_WEB_TRAIL = "Lru/ecom/ejb/services/entityform/WebTrail;";
	private static final String L_ENTITY_FORM_PERSISTANCE = "Lru/nuzmsh/ejb/formpersistence/annotation/EntityFormPersistance;";
	private static final String L_PARENT = "Lru/nuzmsh/commons/formpersistence/annotation/Parent;";
	private static final String L_ENTITY_FORM_SECURITY_PREFIX = "Lru/nuzmsh/commons/formpersistence/annotation/EntityFormSecurityPrefix;";
	private static final String L_COMMENT = "Lru/nuzmsh/commons/formpersistence/annotation/Comment;" ;

	private static final Logger LOG = Logger.getLogger(MapClassVisitor.class);

	private static final boolean CAN_DEBUG = LOG.isDebugEnabled();
	
	
	public MapClassVisitor(ClassVisitor cv, MapFormInfo aInfo, String aFormClassName, boolean aTomcatMode) {
		super(cv);
		if (aInfo == null) {
			throw new IllegalArgumentException("Нет описания формы aInfo");
		}
		theFormClassName = aFormClassName ;
		theForm = aInfo;
		theTomcatMode = aTomcatMode ;
	}

	@Override
	public void visitEnd() {
		visitAnnotation(L_COMMENT,true).visit("value", theForm.getComment());
		
		visitAnnotation(
				L_ENTITY_FORM_SECURITY_PREFIX,
				true).visit("value", theForm.getSecurityPrefix());
		
		// persist
		if (theForm.getEntityFormPersistance() != null) {
			String clazz = theForm.getEntityFormPersistance().getClazz();
			String lclass = getLclass(clazz);
			visitAnnotation(
					L_ENTITY_FORM_PERSISTANCE,
					true).visit("clazz", Type.getType(lclass));
			addProperties(clazz);
		}
		
		// webTrail
		if(theForm.getWebTrail()!=null) {
			WebTrailAnnotation webTrail = theForm.getWebTrail();
			AnnotationVisitor av0 = visitAnnotation(L_WEB_TRAIL, true);
			av0.visit("comment", webTrail.getComment());
			AnnotationVisitor av1 = av0.visitArray("nameProperties");
			for(String prop : webTrail.getNameProperties()) {
				av1.visit(null, prop);
			}
			av1.visitEnd();
			av0.visit("view", webTrail.getView());
			if(webTrail.getList()!=null) {
				av0.visit("list", webTrail.getList());
			}
			av0.visitEnd();
		}
		
		// parent
		if(theForm.getParent()!=null) {
			ParentAnnotation parent = theForm.getParent() ;
			AnnotationVisitor an = visitAnnotation(L_PARENT,true);
			an.visit("property", parent.getProperty());
			if(parent.getParentForm()!=null) {
				if(parent.getParentForm().startsWith("$$map$$")) {
					an.visit("parentMapForm", parent.getParentForm()) ;
				} else {
					an.visit("parentForm", Type.getType(getLclass(parent.getParentForm()))) ;
				}
			}
		}

		if (CAN_DEBUG)
			LOG.debug("visitEnd: end.");
	}

	private static String getLclass(String aClassname) {
		String ret = "L" + aClassname.replace('.', '/') + ";";
		if (CAN_DEBUG)
			LOG.debug("getLclass: " + aClassname + " = " + ret);

		return ret;
	}

	@Override
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		if (CAN_DEBUG) LOG.debug("visitAnnotation: desc = " + desc);
		AnnotationVisitor ann ;
		ann = super.visitAnnotation(desc, visible);
		return ann;
	}

	private void addProperties(String aClassname) {
		try {
			Class entityClass = ClassLoaderHelper.getInstance().loadClass(aClassname);
			for(Method method : entityClass.getMethods()) {
				if(isGetterMethod(method)) {
					String property = PropertyUtil.getPropertyName(method);
					StrutsMapTransform transform = theManager.getTransform(method);
					if(transform!=null) {
						addGetter(method, property, transform);
						addSetter(method, property, transform);
					}
				}
			}
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("Ошибка загрузки класса: "+aClassname+" : "+e.getMessage(),e);
		}
	}
	
	private void addGetter(Method aGetter, String aProperty, StrutsMapTransform aTransform) {
		
		String methodName = aGetter.getName() ;
		MethodVisitor mv = visitMethod(Opcodes.ACC_PUBLIC, methodName,
				"()L"+aTransform.getReturnType()+";", null, null);
		
		AnnotationVisitor av0;
		{
			av0 = mv
					.visitAnnotation(
							L_PERSIST,
							true);
			av0.visitEnd();
		}
		
		if(aGetter.isAnnotationPresent(Comment.class)) {
			Comment comment = aGetter.getAnnotation(Comment.class);
			av0 = mv.visitAnnotation(L_COMMENT, true);
			av0.visit("value", comment.value());
			av0.visitEnd();		
			mv.visitCode();
		}
		if( aTransform.getAnnotaions()!=null) {
			for(String str : aTransform.getAnnotaions()) {
				av0 = mv.visitAnnotation(str, true);
				av0.visitEnd();		
				mv.visitCode();
			}
		}
		Label l0 = new Label();
		mv.visitLabel(l0);
		mv.visitLineNumber(13, l0);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitLdcInsn(aProperty);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
				theFormClassName,
				// "ru/ecom/ejb/services/entityform/MapEntityForm",
				"getValue", "(Ljava/lang/String;)Ljava/lang/Object;");
		mv.visitTypeInsn(Opcodes.CHECKCAST, aTransform.getCastType());
		mv.visitInsn(Opcodes.ARETURN);
		Label l1 = new Label();
		mv.visitLabel(l1);
		mv.visitLocalVariable("this",
				"L"+theFormClassName+";", null,
				//"Lru/ecom/ejb/services/entityform/MapEntityForm;", null,
				l0, l1, 0);
		mv.visitMaxs(2, 1);
		mv.visitEnd();		
	}

	private void addSetter(Method aGetter, String aProperty, StrutsMapTransform aTransform) {
		String methodName = PropertyUtil.getSetterMethodName(aGetter.getName());
		
		MethodVisitor mv = visitMethod(Opcodes.ACC_PUBLIC, methodName,
				"(L"+aTransform.getReturnType()+";)V", null, null);
		mv.visitCode();
		Label l0 = new Label();
		mv.visitLabel(l0);
		mv.visitLineNumber(17, l0);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitLdcInsn(aProperty);
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
				theFormClassName, //"ru/ecom/ejb/services/entityform/MapEntityForm",
				"setValue", "(Ljava/lang/String;Ljava/lang/Object;)V");
		Label l1 = new Label();
		mv.visitLabel(l1);
		mv.visitLineNumber(18, l1);
		mv.visitInsn(Opcodes.RETURN);
		Label l2 = new Label();
		mv.visitLabel(l2);
		mv.visitLocalVariable("this",
				"L"+theFormClassName+";", null,
				//"Lru/ecom/ejb/services/entityform/MapEntityForm;", null,
				l0, l2, 0);
		mv.visitLocalVariable("aParamater", "Ljava/lang/String;", null,
				l0, l2, 1);
		mv.visitMaxs(3, 2);
		mv.visitEnd();		
	}	
	private boolean isGetterMethod(Method aMethod) {
		String name = aMethod.getName() ;
		return (name.startsWith("get") || name.startsWith("is"))
			&& aMethod.getParameterTypes().length==0
			&& !aMethod.getReturnType().equals(Class.class) ;
	}

	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		if (CAN_DEBUG)
			LOG.debug("visit: name = " + name);
		if (CAN_DEBUG)
			LOG.debug("visit: signature = " + signature);

		super.visit(version, access, theFormClassName, signature, superName, interfaces);
	}

	private final MapFormInfo theForm;
	private final StrutsMapTransformManager theManager = StrutsMapTransformManager.getInstance();
	private final String theFormClassName ;
	private final boolean theTomcatMode ;
}
