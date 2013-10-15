package wtp;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.OMText;
import org.apache.axiom.soap.SOAPBody;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.context.OperationContext;
import org.apache.axis2.wsdl.WSDLConstants;

import util.FileUtil;
import util.StringUtil;
import exp.ExpIterator;

public class MainServices {
	public String getExpIteratorSqlFileToXmlFile(String aSqlFileName, String aXmlFileName){
		ExpIterator.sqlFileToXmlFile(aSqlFileName, aXmlFileName);
		return "";
	}
	public String getExpIteratorXmlFile(String aIterationType
			, String aXmlFileName, String aDateStartE, String aDateEndE
			, String aLpu, int aCasesFromSPO, int aCasesFromSLS
			, int aAllLpu){
		String ret = "";
		boolean casesFromSPO = StringUtil.isNotEmpty(aCasesFromSPO);
		boolean casesFromSLS = StringUtil.isNotEmpty(aCasesFromSLS);
		boolean allLpu = StringUtil.isNotEmpty(aAllLpu);
		aXmlFileName = FileUtil.getTempFileName(aXmlFileName);
		ExpIterator.toXmlFile(aIterationType, aXmlFileName, aDateStartE, aDateEndE, aLpu, casesFromSPO, casesFromSLS, allLpu);
/*		if(FileUtil.exists(aXmlFileName, true)){
				SystemUtil.delay(10000);
				//ret = "1";
				try {
					ret=getFileMTOM(aXmlFileName);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}*/
		return ret;
		
	}
	public String getFile(String aFileName) {
		FileDataSource dataSource = new FileDataSource(aFileName);
		DataHandler dataHandler = new DataHandler(dataSource);
		MessageContext inMessageContext = MessageContext.getCurrentMessageContext();
		OperationContext operationContext = inMessageContext.getOperationContext();
		MessageContext outMessageContext = null;
		String fileCID = "";
		try {
			outMessageContext = operationContext.getMessageContext(WSDLConstants.MESSAGE_LABEL_OUT_VALUE);
			//outMessageContext.setDoingSwA(true);
			outMessageContext.setDoingMTOM(true);
			fileCID = outMessageContext.addAttachment(dataHandler);
			//OMFactory factory = OMAbstractFactory.getOMFactory();
			//OMNamespace omNs = factory.createOMNamespace("http://service.sample/xsd", "swa");
			//OMElement fileElement = factory.createOMElement("file", omNs); 
			//fileElement.addAttribute("href",fileCID, omNs);

			
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			FileUtil.writeErrorFile(e,"getFile.txt");
		}
		return fileCID;
	}
	public String getFileMTOM(String aFileName) throws Exception {	
		
		FileDataSource dataSource = new FileDataSource(aFileName);
		DataHandler dataHandler = new DataHandler(dataSource);
		MessageContext inMessageContext = MessageContext.getCurrentMessageContext();
		OperationContext operationContext = inMessageContext.getOperationContext();
		MessageContext outMessageContext = null;
		//String fileCID = "";
		try {
			outMessageContext = operationContext.getMessageContext(WSDLConstants.MESSAGE_LABEL_OUT_VALUE);
			outMessageContext.setDoingMTOM(true);
			
			SOAPEnvelope envelop = outMessageContext.getEnvelope();
			SOAPBody body = envelop.getBody();
			
			OMFactory factory = OMAbstractFactory.getOMFactory();
	        OMNamespace ns = factory.createOMNamespace("http://mtomservice", "ns");       
	        //OMElement rootresponse = factory.createOMElement("getFileMTOMResponse", ns1);
	        
	        OMElement file = factory.createOMElement("file", ns);  
	        OMText data = factory.createOMText(dataHandler, true);    
  	        file.addChild(data);
	        
	        body.addChild(file);  
			
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			FileUtil.writeErrorFile(e,"getFileMTOM.txt");
		}		
		
      
        return "1";   
    }
}
