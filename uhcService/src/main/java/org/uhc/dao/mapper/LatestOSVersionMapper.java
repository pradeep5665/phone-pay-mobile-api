package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.LatestVersionDto;

public class LatestOSVersionMapper implements RowMapper<LatestVersionDto> {

	@Override
	public LatestVersionDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		LatestVersionDto latestVersionDto = new LatestVersionDto();
	       
		latestVersionDto.setVersionID(rs.getLong("versionID"));
		latestVersionDto.setOsName(rs.getNString("OS"));
		String latestAppVersion = rs.getInt("MAJOR_VERSION")+"."+ rs.getInt("MINOR_VERSION")+"."+rs.getInt("PATCH_VERSION");
		latestVersionDto.setLatestAppVersion(latestAppVersion);
		Date productionDate = rs.getDate("PRODUCTION_DATE");
		latestVersionDto.setProductionDate(productionDate != null ? productionDate : null);
		return latestVersionDto; 
	}

}
