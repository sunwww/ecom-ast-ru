package ru.ecom.riamsproc.patient;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Logger;

import ru.ecom.riamsproc.lpu.MisLpuProc;
import ru.ecom.riamsproc.util.ConnectionUtil;

public class MedPolicyProc {
	private static Logger LOG = Logger.getAnonymousLogger();

	public static int updateAttachedLpuFromEpi() throws SQLException {
		Connection con = ConnectionUtil.getConnection() ;
		Statement stmt = con.createStatement() ;
		try {
			HashMap<String, Long> misLpuHash = new HashMap<String, Long>() ; 
			int count = 0 ; 
			ResultSet rs = stmt.executeQuery(
					"  select MedPolicy.id, reg_ic.omcCode, series, polNumber from MedPolicy "
					+ " inner join reg_ic on MedPolicy.company_id=reg_ic.id"
					+ " where dtype='MedPolicyOmc'"
					+"    and attachedLpu_id is null "
					+"    and company_id is not null "
					//+" limit 1000"
			);
			try {
				while(rs.next()) {
					long id = rs.getLong(1);
					String companyCode = rs.getString(2);
					String series = rs.getString(3);
					String number = rs.getString(4);
					String lpuCode = ExternalPersonInfoProc.findAttachedLpuOmcCode(companyCode, series, number) ;
					if(lpuCode!=null) {
						Long misLpu = MisLpuProc.findMainMisLpuByOmcCode(lpuCode, misLpuHash);
						if(misLpu!=null) {
							count++ ;
							if(count % 1000 == 0 ) {
								LOG.finest("Must updated3: "+count);
							}
						}
					}
				}
				return count ;
			} finally {
				rs.close() ;
			}
		} finally {
			stmt.close();
		}
	}
}
