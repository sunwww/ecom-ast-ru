package org.tempuri.WS_MES_SERVER.wsdl;

public class WS_MES_SERVERSoapPortProxy implements org.tempuri.WS_MES_SERVER.wsdl.WS_MES_SERVERSoapPort {
  private String _endpoint = null;
  private org.tempuri.WS_MES_SERVER.wsdl.WS_MES_SERVERSoapPort wS_MES_SERVERSoapPort = null;
  
  public WS_MES_SERVERSoapPortProxy() {
    _initWS_MES_SERVERSoapPortProxy();
  }
  
  public WS_MES_SERVERSoapPortProxy(String endpoint) {
    _endpoint = endpoint;
    _initWS_MES_SERVERSoapPortProxy();
  }
  
  private void _initWS_MES_SERVERSoapPortProxy() {
    try {
      wS_MES_SERVERSoapPort = (new org.tempuri.WS_MES_SERVER.wsdl.WSLocator()).getWS_MES_SERVERSoapPort();
      if (wS_MES_SERVERSoapPort != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)wS_MES_SERVERSoapPort)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)wS_MES_SERVERSoapPort)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (wS_MES_SERVERSoapPort != null)
      ((javax.xml.rpc.Stub)wS_MES_SERVERSoapPort)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.tempuri.WS_MES_SERVER.wsdl.WS_MES_SERVERSoapPort getWS_MES_SERVERSoapPort() {
    if (wS_MES_SERVERSoapPort == null)
      _initWS_MES_SERVERSoapPortProxy();
    return wS_MES_SERVERSoapPort;
  }
  
  public java.lang.Object get_RZ_from_POLIS(java.lang.String l_s_polis, java.lang.String l_n_polis, java.lang.String l_comentsl) throws java.rmi.RemoteException{
    if (wS_MES_SERVERSoapPort == null)
      _initWS_MES_SERVERSoapPortProxy();
    return wS_MES_SERVERSoapPort.get_RZ_from_POLIS(l_s_polis, l_n_polis, l_comentsl);
  }
  
  public java.lang.Object get_RZ_from_SN_SCAO(java.lang.String l_SocialNumber) throws java.rmi.RemoteException{
    if (wS_MES_SERVERSoapPort == null)
      _initWS_MES_SERVERSoapPortProxy();
    return wS_MES_SERVERSoapPort.get_RZ_from_SN_SCAO(l_SocialNumber);
  }
  
  public java.lang.Object get_SN_SCAO_from_RZ(java.lang.String l_rz) throws java.rmi.RemoteException{
    if (wS_MES_SERVERSoapPort == null)
      _initWS_MES_SERVERSoapPortProxy();
    return wS_MES_SERVERSoapPort.get_SN_SCAO_from_RZ(l_rz);
  }
  
  public java.lang.Object get_DOCS_from_RZ(java.lang.String l_rz, java.lang.String l_comentsl) throws java.rmi.RemoteException{
    if (wS_MES_SERVERSoapPort == null)
      _initWS_MES_SERVERSoapPortProxy();
    return wS_MES_SERVERSoapPort.get_DOCS_from_RZ(l_rz, l_comentsl);
  }
  
  public java.lang.Object get_ADRES_from_RZ(java.lang.String l_rz, java.lang.String l_comentsl) throws java.rmi.RemoteException{
    if (wS_MES_SERVERSoapPort == null)
      _initWS_MES_SERVERSoapPortProxy();
    return wS_MES_SERVERSoapPort.get_ADRES_from_RZ(l_rz, l_comentsl);
  }
  
  public java.lang.Object get_RZ_from_SS(java.lang.String l_ss, java.lang.String l_comentsl) throws java.rmi.RemoteException{
    if (wS_MES_SERVERSoapPort == null)
      _initWS_MES_SERVERSoapPortProxy();
    return wS_MES_SERVERSoapPort.get_RZ_from_SS(l_ss, l_comentsl);
  }
  
  public java.lang.Object get_RZ_from_DOCS(java.lang.String l_doc_t, java.lang.String l_doc_s, java.lang.String l_doc_n, java.lang.String l_comentsl) throws java.rmi.RemoteException{
    if (wS_MES_SERVERSoapPort == null)
      _initWS_MES_SERVERSoapPortProxy();
    return wS_MES_SERVERSoapPort.get_RZ_from_DOCS(l_doc_t, l_doc_s, l_doc_n, l_comentsl);
  }
  
  public java.lang.Object get_POLIS_from_RZ2(java.lang.String l_rz, java.lang.String l_comentsl) throws java.rmi.RemoteException{
    if (wS_MES_SERVERSoapPort == null)
      _initWS_MES_SERVERSoapPortProxy();
    return wS_MES_SERVERSoapPort.get_POLIS_from_RZ2(l_rz, l_comentsl);
  }
  
  public java.lang.Object get_POLIS_from_RZ(java.lang.String l_rz, java.lang.String l_comentsl) throws java.rmi.RemoteException{
    if (wS_MES_SERVERSoapPort == null)
      _initWS_MES_SERVERSoapPortProxy();
    return wS_MES_SERVERSoapPort.get_POLIS_from_RZ(l_rz, l_comentsl);
  }
  
  public java.lang.Object get_FIODR_from_RZ(java.lang.String l_rz, java.lang.String l_comentsl) throws java.rmi.RemoteException{
    if (wS_MES_SERVERSoapPort == null)
      _initWS_MES_SERVERSoapPortProxy();
    return wS_MES_SERVERSoapPort.get_FIODR_from_RZ(l_rz, l_comentsl);
  }
  
  public java.lang.Object get_RZ_from_FIODR(java.lang.String l_f, java.lang.String l_i, java.lang.String l_o, java.lang.String l_dr, java.lang.String l_comentsl) throws java.rmi.RemoteException{
    if (wS_MES_SERVERSoapPort == null)
      _initWS_MES_SERVERSoapPortProxy();
    return wS_MES_SERVERSoapPort.get_RZ_from_FIODR(l_f, l_i, l_o, l_dr, l_comentsl);
  }
  
  
}