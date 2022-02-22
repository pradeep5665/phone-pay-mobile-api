package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.CommunicationViewDto;

public class CommunicationViewMapper implements RowMapper<CommunicationViewDto> {

	@Override
	public CommunicationViewDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		CommunicationViewDto com = new CommunicationViewDto();
		com.setId(rs.getLong("ID"));
		com.setMessageId(rs.getLong("messageId"));
		com.setUserId(rs.getLong("userId"));
		Timestamp dateViewed = rs.getTimestamp("DATE_VIEWED");
		com.setDateViewed(dateViewed != null ? dateViewed.toLocalDateTime() : null);
        return com; 
	}

}
