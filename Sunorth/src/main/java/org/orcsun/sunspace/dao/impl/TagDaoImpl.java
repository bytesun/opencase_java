package org.orcsun.sunspace.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.orcsun.sunspace.dao.TagDAO;
import org.orcsun.sunspace.object.Tag;
import org.springframework.jdbc.core.RowMapper;

public class TagDaoImpl extends  SunJdbcDaoSupport implements TagDAO {

	@Override
	public List<Tag> listTag(int count) {
		String sql = "SELECT * FROM TAG ORDER BY COUNT DESC LIMIT "+count;
		return this.getJdbcTemplate().query(sql, new TagMapper());
	}
	
	private static final class TagMapper implements RowMapper<Tag>{

		@Override
		public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
			Tag t = new Tag();
			t.setTag(rs.getString("TAG"));
			t.setCount(rs.getInt("COUNT"));
			t.setDesc(rs.getString("DESCRIPTION"));
			return t;
		}
		
	}

}
