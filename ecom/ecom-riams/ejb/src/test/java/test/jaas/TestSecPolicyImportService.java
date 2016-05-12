package test.jaas;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import ru.ecom.jaas.ejb.service.ISecPolicyImportService;
import ru.nuzmsh.util.filesystem.FileSystemIterate;
import ru.nuzmsh.util.filesystem.IFileListener;
import ru.nuzmsh.util.filesystem.SourcesFileFilter;

public class TestSecPolicyImportService {

	private static final String POLICY_KEY = "\"/Policy/";

	public TestSecPolicyImportService() throws NamingException, IOException {
		// service = (ISecPolicyImportService) new
		// InitialContext().lookup("jaas-app/SecPolicyImportServiceBean/remote");

		// prop.loadFromXML(new FileInputStream("translate.xml"));
	}

	public void importDynamicPolicy() throws NamingException {
		System.out.println("dddd");
		ISecPolicyImportService service = (ISecPolicyImportService) new InitialContext()
				.lookup("jaas-app/SecPolicyImportServiceBean/remote");
		Map<String, String> map = new HashMap<String, String>();
		map.put("100", "Привет");
		service.importPolicy("/Policy/Mis/MisLpuDynamic/100/Create", map);
		service.importPolicy("/Policy/Mis/MisLpuDynamic/100/Delete", map);
		
	}

	public static void main(String[] args) throws NamingException, IOException {
		// new
		// TestSecPolicyImportService().importFromSourceDir("C:\\project\\ecom")
		// ;
		new File("policiesList.txt").delete();
		TestSecPolicyImportService test = new TestSecPolicyImportService();
		// test.importDynamicPolicy();
		// test.importFromSourceDir(args[0]) ;

		// test.importFromSourceDir("D:\\install\\1219\\ecom-mis") ;

		test.importFromSourceDir("/home/esinev/workspace");
		System.out.println("DONE.");
		// test.importFromSourceDir("/home/esinev/workspace/java/stacejb") ;
		// test.importFromSourceDir("/home/esinev/workspace/java/stacweb") ;
	}

	public void importFromSourceDir(String aSourceDir) {
		FileSystemIterate iterate = new FileSystemIterate();
		iterate.iterate(new File(aSourceDir), new ProcessFile(),
				new SourcesFileFilter());
	}

	private void importLine(String aLine) throws IOException {
		int firstPos = 0;
		while ((firstPos = aLine.indexOf(POLICY_KEY, firstPos)) > 0) {
			int lastPos = aLine.indexOf("\"", firstPos + 1);
			if(lastPos<0) {
				break ;
			}
				String str = aLine.substring(firstPos + 1, lastPos);
				if (aLine.contains("@EntityFormSecurityPrefix")) {
					importPolicy(str + "/View");
					importPolicy(str + "/Edit");
					importPolicy(str + "/Create");
					importPolicy(str + "/Delete");
				} else {
					importPolicy(str);
				}
				firstPos = lastPos;
		}

	}

	private void importPolicy(String str) throws IOException {
		if (!str.startsWith("/Policy/Stac/VocStac")) {
			if (!theImported.contains(str)) {
				System.out.println("Importing " + str + " ...");
				// service.importPolicy(str) ;

				write(str);
				theImported.add(str);

			} else {
				System.err.println("Skipping imported " + str);

			}
		} else {
			System.err.println("Skipping voc " + str);
		}
	}

	private void write(String aStr) throws IOException {
		System.out.println("    asdfasdf " + aStr);
		FileWriter out = new FileWriter("policiesList.txt", true);
		try {
			out.write(aStr);
			out.write(",\\\n");
		} finally {
			out.close();
		}
	}

	class ProcessFile implements IFileListener {

		public void processFile(File aFile) {

			if (aFile.getName().equals("EntityFormSecurityPrefix"))
				return;
			if (aFile.getName().endsWith(".class"))
				return;

			try {
				LineNumberReader in = new LineNumberReader(
						new FileReader(aFile));

				String line = null;
				while ((line = in.readLine()) != null) {
					// if(aFile.getName().endsWith("startPriem.jsp")) {
					// System.out.println("'"+line+"'");
					// }
					line = line.replace('\'', '"');
					if (line.indexOf(POLICY_KEY) >= 0) {
						// System.err.println("pol "+line);
						importLine(line);
					}
				}
				in.close();
			} catch (Exception e) {
				System.err.println(aFile + ":" + e.getMessage());
				throw new RuntimeException(aFile + ":" + e.getMessage(), e);
			}
			// System.out.println("aFile = " + aFile);
		}

	}

	// Properties prop = new Properties();
	// ISecPolicyImportService service ;
	TreeSet<String> theImported = new TreeSet<String>();
}
