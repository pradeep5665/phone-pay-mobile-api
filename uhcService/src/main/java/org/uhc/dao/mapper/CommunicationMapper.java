package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.CommunicationDto;

public class CommunicationMapper implements RowMapper<CommunicationDto> {

	@Override
	public CommunicationDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		CommunicationDto com = new CommunicationDto();
	       
		com.setMessageID(rs.getLong("messageId"));
	        com.setComMessage(rs.getString("MESSAGE"));
	        
	        Date startDate = rs.getDate("DISPLAY_START");
	        com.setDisplayStart(startDate != null ? startDate : null);
	        
	        Date endDate = rs.getDate("DISPLAY_END");
	        com.setDisplayEnd(endDate != null ? endDate : null);
	        
	        com.setDisplayOnce(rs.getInt("DISPLAY_ONCE"));
	        com.setPopUp(rs.getInt("POP_UP"));
	        com.setPriority(rs.getInt("PRIORITY"));
	        return com; 
	}

}
