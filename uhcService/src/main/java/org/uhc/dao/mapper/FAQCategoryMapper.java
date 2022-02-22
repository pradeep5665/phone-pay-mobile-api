package org.uhc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.uhc.dao.dto.FAQCategoryDto;

public class FAQCategoryMapper implements RowMapper<FAQCategoryDto> {

	@Override
	public FAQCategoryDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		FAQCategoryDto faqCategoryDto = new FAQCategoryDto();
	       
		faqCategoryDto.setId(rs.getLong("ID"));
		faqCategoryDto.setCategotyName(rs.getString("categoryName"));
		return faqCategoryDto; 
	
	}

}
