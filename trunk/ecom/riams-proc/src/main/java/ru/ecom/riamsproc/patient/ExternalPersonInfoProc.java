package ru.ecom.riamsproc.patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import ru.ecom.riamsproc.util.ConnectionUtil;

public class ExternalPersonInfoProc {

	private static Logger LOG = Logger.getLogger("ExternalPersonInfoProc") ;

	/**
	 * Поиск главного ЛПУ по коду ОМС
	 * 
	 * @param aCode
	 *            код по ОМС
	 * @param aHash
	 *            кеширование
	 * @return ид MisLpu
	 * @throws SQLException
	 */
	public static String findAttachedLpuOmcCode(String aCompanyCode, String aSeries, String aNumber)
			throws SQLException {
		String ret = null ;
			Connection con = ConnectionUtil.getConnection();
			PreparedStatement stmt = con
					.prepareStatement(
							"  select bindingLpu from ExternalPersonInfo " 
							+ " where insurancecompany    = ? "
							+ "   and policyseries     = ? "
							+ "   and policyNumber  = ? ");
			try {
				stmt.setString(1, aCompanyCode);
				stmt.setString(2, aSeries);
				stmt.setString(3, aNumber);
				ResultSet rs = stmt.executeQuery();
				try {
					if (rs.next()) {
						ret = rs.getString(1);
						if (rs.next()) {
							LOG.warning("Больше одного полиса ["+aCompanyCode+" "+aSeries+" "+aNumber+"]");
						}
					} else {
						LOG.warning("Нет полиса ["+aCompanyCode+" "+aSeries+" "+aNumber+"]");
					}
				} finally {
					rs.close();
				}
			} finally {
				stmt.close();
			}
		return ret;
	}
	
}
