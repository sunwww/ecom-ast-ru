package test.map;

import java.util.*;
import org.objectweb.asm.*;

public class MapEntityFormDump implements Opcodes {

	public static byte[] dump() throws Exception {

		ClassWriter cw = new ClassWriter(0);
		FieldVisitor fv;
		MethodVisitor mv;
		AnnotationVisitor av0;

		cw.visit(V1_5, ACC_PUBLIC + ACC_SUPER,
				"ru/ecom/ejb/services/entityform/MapEntityForm", null,
				"ru/nuzmsh/forms/validator/MapForm",
				new String[] { "ru/ecom/ejb/services/entityform/IEntityForm" });

		cw.visitSource("MapEntityForm.java", null);

		{
			mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(7, l0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKESPECIAL,
					"ru/nuzmsh/forms/validator/MapForm", "<init>", "()V");
			mv.visitInsn(RETURN);
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLocalVariable("this",
					"Lru/ecom/ejb/services/entityform/MapEntityForm;", null,
					l0, l1, 0);
			mv.visitMaxs(1, 1);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "getUniqueName",
					"()Ljava/lang/String;", null, null);
			{
				av0 = mv
						.visitAnnotation(
								"Lru/nuzmsh/commons/formpersistence/annotation/Persist;",
								true);
				av0.visitEnd();
			}
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(13, l0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitLdcInsn("uniqueName");
			mv.visitMethodInsn(INVOKEVIRTUAL,
					"ru/ecom/ejb/services/entityform/MapEntityForm",
					"getValue", "(Ljava/lang/String;)Ljava/lang/Object;");
			mv.visitTypeInsn(CHECKCAST, "java/lang/String");
			mv.visitInsn(ARETURN);
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLocalVariable("this",
					"Lru/ecom/ejb/services/entityform/MapEntityForm;", null,
					l0, l1, 0);
			mv.visitMaxs(2, 1);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "setUniqueName",
					"(Ljava/lang/String;)V", null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(17, l0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitLdcInsn("uniqueName");
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEVIRTUAL,
					"ru/ecom/ejb/services/entityform/MapEntityForm",
					"setValue", "(Ljava/lang/String;Ljava/lang/Object;)V");
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLineNumber(18, l1);
			mv.visitInsn(RETURN);
			Label l2 = new Label();
			mv.visitLabel(l2);
			mv.visitLocalVariable("this",
					"Lru/ecom/ejb/services/entityform/MapEntityForm;", null,
					l0, l2, 0);
			mv.visitLocalVariable("aUniqueName", "Ljava/lang/String;", null,
					l0, l2, 1);
			mv.visitMaxs(3, 2);
			mv.visitEnd();
		}
		cw.visitEnd();

		return cw.toByteArray();
	}
}
