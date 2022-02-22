package org.uhc.dao.mapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.FAQDto;

public class FAQMapper implements RowMapper<FAQDto> {

	@Override
	public FAQDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		FAQDto faqDto = new FAQDto();
		//QUESTION_SEQUENCE, QUESTION_TITLE, QUESTION_ANSWER, APPLICATION, ACTIVE
		faqDto.setId(rs.getLong("ID"));
		faqDto.setCategoryID(rs.getLong("CATEGORY_ID"));
		faqDto.setQuestionSequence(rs.getInt("QUESTION_SEQUENCE"));
		faqDto.setQuestionTitle(rs.getString("QUESTION_TITLE"));
		faqDto.setQauestionAnswer(rs.getString("QUESTION_ANSWER"));
		faqDto.setApplication(rs.getString("APPLICATION"));
		faqDto.setActive(rs.getString("ACTIVE"));
        return faqDto; 
	}

}
