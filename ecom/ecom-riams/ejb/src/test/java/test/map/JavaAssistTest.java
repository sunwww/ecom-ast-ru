package test.map;

import org.objectweb.asm.*;
import ru.nuzmsh.commons.formpersistence.annotation.EntityForm;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;

public class JavaAssistTest {

	private static void trace(Object aStr) {
		System.out.println("   "+aStr);
	}

	public static void main(String[] args) throws ClassNotFoundException {
		Thread.currentThread().setContextClassLoader(new MapClassLoader(Thread.currentThread().getContextClassLoader())) ;
		Class cl = Thread.currentThread().getContextClassLoader().loadClass("ru.ecom.ejb.services.entityform.MapEntityForm");
		trace("ann = "+cl.getAnnotation(EntityForm.class));
	}

	static class MapClassLoader extends ClassLoader {
		private final ClassLoader theLoader ;
		public MapClassLoader(ClassLoader aLoader) {
			theLoader =aLoader ;
		}
		
		
		@Override
		public synchronized void clearAssertionStatus() {
			theLoader.clearAssertionStatus();
		}


		@Override
		public URL getResource(String name) {
			return theLoader.getResource(name);
		}


		@Override
		public InputStream getResourceAsStream(String name) {
			return theLoader.getResourceAsStream(name);
		}


		@Override
		public Enumeration<URL> getResources(String name) throws IOException {
			return theLoader.getResources(name);
		}


		@Override
		public synchronized void setClassAssertionStatus(String className, boolean enabled) {
			theLoader.setClassAssertionStatus(className, enabled);
		}


		@Override
		public synchronized void setDefaultAssertionStatus(boolean enabled) {
			theLoader.setDefaultAssertionStatus(enabled);
		}


		@Override
		public synchronized void setPackageAssertionStatus(String packageName, boolean enabled) {
			theLoader.setPackageAssertionStatus(packageName, enabled);
		}


		@Override
		public Class<?> loadClass(String name) throws ClassNotFoundException {
			System.out.println("loading "+name);
			if(name.equals("MapEntityForm")) {
				try {
					ClassReader reader = new ClassReader(
							"MapEntityForm");
					ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
					MapClassVisitor visitor = new MapClassVisitor(writer);
					reader.accept(visitor, ClassReader.SKIP_DEBUG);
					byte[] buf = writer.toByteArray();
					return defineClass(
							"MapEntityForm", buf,
							0, buf.length);
				} catch (Exception e) {
					throw new IllegalStateException(e);
				}
			} else {
				return theLoader.loadClass(name);
			}

		}
	}

	static class MapClassVisitor extends ClassAdapter implements Opcodes {

		public MapClassVisitor(ClassVisitor cv) {
			super(cv);
		}

		
		@Override
		public void visitEnd() {
			visitAnnotation("Lru/nuzmsh/commons/formpersistence/annotation/EntityForm;", true) ;
			trace("end");
		}


		
		@Override
		public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
			trace("desc = "+desc);
			return super.visitAnnotation(desc, visible);
		}


		@Override
		public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
			trace("name = " + name);
			super.visit(version, access, name, signature, superName, interfaces);
		}

		

	}
}
