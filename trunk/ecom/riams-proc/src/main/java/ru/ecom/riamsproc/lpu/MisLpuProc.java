package ru.ecom.riamsproc.lpu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Logger;

import ru.ecom.riamsproc.util.ConnectionUtil;

public class MisLpuProc {

	private static Logger LOG = Logger.getAnonymousLogger();

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
	public static Long findMainMisLpuByOmcCode(String aCode, Map<String, Long> aHash)
			throws SQLException {
		Long ret = aHash != null ? aHash.get(aCode) : null;
		if (ret == null) {
			Connection con = ConnectionUtil.getConnection();
			PreparedStatement stmt = con
					.prepareStatement("select id from MisLpu where parent_id is null "
							+ " and omcCode = ?");
			try {
				stmt.setString(1, aCode);
				ResultSet rs = stmt.executeQuery();
				try {
					if (rs.next()) {
						ret = rs.getLong(1);
						if (rs.next()) {
							LOG.warning("Больше одного ЛПУ с кодом " + aCode);
							ret = null ;
						} else {
							// помещаем в кэш
							if(aHash!=null)	aHash.put(aCode, ret);
						}
					} else {
						LOG.warning("Нет MisLpu с кодом ОМС " + aCode);
					}
				} finally {
					rs.close();
				}
			} finally {
				stmt.close();
			}
		}
		return ret;
	}

	/**
	 * Поиск главного ЛПУ по коду ОМС
	 * 
	 * @param aCode
	 *            код по ОМС
	 * @param aHash
	 *            кеширование
	 * @return ид MisLpu, null если не найден
	 * @throws SQLException
	 */
	public static Long findMainMisLpuByOmcCode(String aCode) throws SQLException {
		return findMainMisLpuByOmcCode(aCode, null);
	}

}
