package ru.ecom.ejb.services.quickquery;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.hibernate.hql.classic.WhereParser;
import org.jboss.annotation.security.SecurityDomain;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import ru.ecom.ejb.services.quickquery.config.ConfigQuery;
import ru.ecom.ejb.services.quickquery.config.ConfigQuickQuery;
import ru.ecom.ejb.services.quickquery.config.ConfigWhereClause;
import ru.ecom.ejb.services.quickquery.response.QuickQuery;
import ru.ecom.ejb.services.quickquery.response.QuickQueryResponse;
import ru.ecom.ejb.services.quickquery.response.QuickQueryRow;
import ru.ecom.ejb.services.util.ApplicationDataSourceHelper;
import ru.ecom.ejb.services.util.ClassLoaderHelper;
import ru.ecom.ejb.services.util.JBossConfigUtil;

@Stateless
@Remote(IQuickQueryService.class)
@SecurityDomain("other")
public class QuickQueryServiceBean implements IQuickQueryService {
	
	private final static Logger LOG = Logger
			.getLogger(QuickQueryServiceBean.class);
	private final static boolean CAN_DEBUG = LOG.isDebugEnabled();
	
	public Map<String,String> listQuickQueries() {
		
		TreeMap<String,String> map = new TreeMap<String, String>() ;
		try {
			File dir = JBossConfigUtil.getDataFile("quickquery/") ;
			for(File file : dir.listFiles()) {
				String name=file.getName().replace(".xml", "");
				ConfigQuickQuery conf = getConfig(name);
				map.put(name, conf.getName()) ;
			}
		} catch (Exception e) {
			throw new RuntimeException(e) ;
		}
		
		return map ;
	}
	public QuickQueryResponse executeQuery(String aKey) {
		QuickQueryResponse ret = new QuickQueryResponse() ;
		try {
			ConfigQuickQuery config = getConfig(aKey);
			DataSource dataSource = ApplicationDataSourceHelper.getInstance().findDataSource() ;
			ret.setName(config.getName());
			for(ConfigQuery configQuery : config.getQueries()) {
				Connection con = dataSource.getConnection() ;
				try {
					QuickQuery query = new QuickQuery() ;
					query.setName(configQuery.getName()) ;
					executeQuery(con, query,configQuery,config) ;
					ret.getQueries().add(query) ;
				} finally {
					con.close() ;
				}
			}
		} catch (Exception e) {
			LOG.error("ОШИБКА :"+e.getMessage(),e) ;
			throw new RuntimeException(e);
		}
		return ret ;
	}
	
	private String applyWhereClause(ConfigQuickQuery aConfig, String aQuery) {
		String ret = aQuery ;
		ConfigWhereClause cwc = aConfig.getWhereClauseConfig() ;
		if(cwc.getWhereClause()!=null) {
			QuickQueryContext context = new QuickQueryContext(theManager, theContext) ;
			ret = ret.replace("${where."+cwc.getKey()+"}", cwc.getWhereClause().createWhereClause(context)) ;
		}
		LOG.info("query ="+ret) ;
		return ret ;
		
	}
	private void executeQuery(Connection aCon, QuickQuery aQuery, ConfigQuery aConfigQuery, ConfigQuickQuery aConfig) throws SQLException {
		Statement stmt = aCon.createStatement() ;
		
		ResultSet rs = stmt.executeQuery(applyWhereClause(aConfig, aConfigQuery.getSql())) ;
		ResultSetMetaData meta = rs.getMetaData() ;
		while(rs.next()) {
			QuickQueryRow row = new QuickQueryRow() ;
			for(int i=0; i<meta.getColumnCount(); i++) {
				row.getCells().add(rs.getObject(i+1));
			}
			aQuery.getRows().add(row);
		}
	}

	private ConfigQuickQuery getConfig(String aKey) throws JDOMException, IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		File file = JBossConfigUtil.getDataFile("quickquery/"+aKey.replace(".","")+".xml") ;
		Document doc = new SAXBuilder().build(file);
		Element root = doc.getRootElement() ;
		ConfigQuickQuery ret = new ConfigQuickQuery() ;
		ret.setName(root.getAttributeValue("name")) ;
		List<Element> queryElms = root.getChild("queries").getChildren("query") ;
		
		Element whereElm = root.getChild("where");
		if(whereElm!=null) {
			ret.getWhereClauseConfig().setKey(whereElm.getAttributeValue("id"));
			ret.getWhereClauseConfig().setWhereClause(
					(IWhereClause)ClassLoaderHelper.getInstance()
					.loadClass(whereElm.getAttributeValue("class"))
					.newInstance()
			);
		}
		for(Element elm : queryElms) {
			ConfigQuery query = new ConfigQuery() ;
			query.setName(elm.getAttributeValue("name")) ;
			query.setSql(elm.getChildText("sql")) ;
			ret.getQueries().add(query);
		}
		return ret ;
	}
	
	private @PersistenceContext EntityManager theManager ;  
	private @Resource SessionContext theContext;
}
