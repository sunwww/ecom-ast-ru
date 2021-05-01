package ru.ecom.ejb.services.index;

import org.apache.log4j.Logger;
import org.jboss.annotation.ejb.Service;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.util.ApplicationDataSourceHelper;
import ru.ecom.ejb.services.util.EntityHelper;
import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.StringUtil;

import javax.ejb.Local;
import javax.naming.NamingException;
import javax.persistence.*;
import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.HashSet;

/**
 * Работа с индексами
 */
@Service
@Local(IIndexService.class)
public class IndexServiceBean implements IIndexService, IIndexServiceManagement {
	private static final Logger LOG = Logger.getLogger(IndexServiceBean.class);

	private HashSet<String> getIndexNames(Connection aCon) throws SQLException {
		HashSet<String> ret = new HashSet<>();
		DatabaseMetaData db = aCon.getMetaData();
		ResultSet rs = db.getTables(null, null, null, new String[] { "TABLE" });
		try {

			while (rs.next()) {
				String tableName = rs.getString("TABLE_NAME");
				// LOG.info("table "+tableName);

				ResultSet irs = db.getIndexInfo(null, null, tableName, true,
						true);
				try {
					while (irs.next()) {
						String indexName = irs.getString("INDEX_NAME");
						ret.add(indexName.toLowerCase());
					}
				} finally {
					irs.close();
				}

				irs = db.getIndexInfo(null, null, tableName, false, true);
				try {
					while (irs.next()) {
						String indexName = irs.getString("INDEX_NAME");
						ret.add(indexName.toLowerCase());
					}
				} finally {
					irs.close();
				}

			}
		} finally {
			rs.close();
		}
		return ret;
	}

	public IndexServiceBean() throws NamingException, SQLException,
			NoSuchMethodException {

		DataSource dataSource = findDataSource();
		Connection con = dataSource.getConnection();
		try {
			LOG.info("Getting indexes info ...");
			HashSet<String> indexes = getIndexNames(con);
			Statement stmt = con.createStatement();
			try {
				for (Class entityClass : theEntityHelper.listAllEntities()) {
					createIndexes(entityClass, stmt, indexes);
				}
			} finally {
				stmt.close();
			}
		} finally {
			con.close();
		}
	}

	private DataSource findDataSource() throws NamingException {
		return ApplicationDataSourceHelper.getInstance().findDataSource();
	}

	@Override
	public void destroy() {
	}

	private void createIndexes(Class aEntitClass, AIndexes aIndexes,
			Statement aStatement, HashSet<String> aIndexsNames)
			throws NoSuchMethodException {
		AIndexes indexesAnnotation = aIndexes;
		if (indexesAnnotation != null) {
			String tableName = entityHelper.getTableName(aEntitClass);
			AIndex[] indexes = indexesAnnotation.value();
			for (AIndex index : indexes) {
				if (!index.table().equals("")) tableName = index.table() ; 
				createIndex(aEntitClass, tableName, index, aStatement,
						aIndexsNames);
			}
		}

		// Class superClass = aEntitClass.getSuperclass() ;
		// if(superClass!=null) {
		// createIndexes(superClass, aStatement) ;
		// }

	}

	private void createIndexes(Class aEntitClass, Statement aStatement,
			HashSet<String> indexes) throws NoSuchMethodException {
		if (aEntitClass.isAnnotationPresent(MappedSuperclass.class)) {
			LOG
					.info("Класс  "
							+ aEntitClass
							+ " имеет аннотацию MappedSuperclass. Значит не сохраняется в базу напрямую.");
			return;
		}

		Class clazz = aEntitClass;
		Class tableClass = aEntitClass;
		while (clazz != null) {
			if (clazz.getAnnotation(MappedSuperclass.class) == null) {
				tableClass = clazz;
			}
			createIndexes(tableClass, (AIndexes) clazz
					.getAnnotation(AIndexes.class), aStatement, indexes);

			clazz = clazz.getSuperclass();
		}
	}

	private void createIndex(Class aEntityClass, String aTableName,
			AIndex aIndex, Statement aStatement, HashSet<String> indexes)
			throws NoSuchMethodException {
		if (!(aIndex.properties().length == 1 && aIndex
				.properties()[0].equalsIgnoreCase("dtype"))) {
			createIndex(aEntityClass, aTableName, aIndex, aStatement,
					canCreateDtype(aEntityClass), indexes);
		} else {
			createIndex(aEntityClass, aTableName, aIndex, aStatement, false,
					indexes);
		}
	}

	private boolean canCreateDtype(Class aClass) {
		if (aClass != null) {
			Class superClass = aClass.getSuperclass();
			if (superClass != null
					&& superClass.isAnnotationPresent(Entity.class)) {
				return true;
			} else {
				return canCreateDtype(superClass);
			}
		} else {
			return false;
		}
	}

	private void createIndex(Class aEntityClass, String aTableName,
			AIndex aIndex, Statement aStatement, boolean aAppendDtype,
			HashSet<String> indexes) throws NoSuchMethodException {
		StringBuilder indexName = new StringBuilder(entityHelper
				.getEntityName(aEntityClass));
		if ("".equals(aIndex.name() ) ) {
			for (String property : aIndex.properties()) {
			indexName.append('_');
			indexName.append(property);
			}
		}
		else {
			indexName.append(aIndex.name());
		}
		if (aAppendDtype) {
			indexName.append("_dtype");
		}

		if (indexes.contains(indexName.toString().toLowerCase())) {
			// LOG.info("Index "+indexName+" already exists") ;
			return;
		}

		StringBuilder query = new StringBuilder("create ");
		if (aIndex.unique()) {
			query.append("unique ");
		}
		query.append("index ");
		query.append(indexName);
		
		query.append(" on ").append(aTableName);
		query.append("(");
		boolean isFirstPassed = false;
		for (String property : aIndex.properties()) {
			if (isFirstPassed)
				query.append(", ");
			else
				isFirstPassed = true;
			query.append(getColumnName(aEntityClass, property));
		}
		if (aAppendDtype) {
			query.append(", dtype");
		}
		query.append(")");
		LOG.info(query + " ...");
		try {
			aStatement.executeUpdate(query.toString());
		} catch (Exception e) {
			String m = e.getMessage();
			LOG.error(m+" : "+ query);
		}
	}

	private String getColumnName(Class aEntityClass, String aProperty) {
		if ("dtype".equalsIgnoreCase(aProperty)) {
			return "dtype"; // FIXME временно
		}
		// переобределение аттрибутов
		if (aEntityClass.isAnnotationPresent(AttributeOverride.class)) {
			AttributeOverride over = (AttributeOverride) aEntityClass
					.getAnnotation(AttributeOverride.class);
			if (aProperty.equals(over.name())) {
				if (!StringUtil.isNullOrEmpty(over.column().name())) {
					return over.column().name();
				}
			}
		}

		Method method = PropertyUtil.getGetterMethod(aEntityClass, aProperty); // aEntityClass.getMethod(methodName)
																				// ;
		//zav
		JoinColumn joinColumn = method.getAnnotation(JoinColumn.class);
		if (joinColumn != null && !StringUtil.isNullOrEmpty(joinColumn.name())) {
			return joinColumn.name();
			}
		//zav
		Column column = method.getAnnotation(Column.class);
		if (column != null && !StringUtil.isNullOrEmpty(column.name())) {
			return column.name();
		} else {
			OneToOne oneToOne = method.getAnnotation(OneToOne.class);
			ManyToOne manyToOne = method.getAnnotation(ManyToOne.class);

			if (oneToOne != null || manyToOne != null) {
				// todo id - добавить функцию в EntityHelper getIdPropertyName()
				return  PropertyUtil.getPropertyName(method) + "_"
						+ getColumnName(method.getReturnType(), "id");

			}
		}
		return aProperty;
	}

	private final EntityHelper entityHelper = EntityHelper.getInstance();

}
