package org.orcsun.sunspace.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.orcsun.sunspace.dao.TagDAO;
import org.orcsun.sunspace.object.Tag;
import org.springframework.jdbc.core.RowMapper;

public class TagDaoImpl extends  SunJdbcDaoSupport implements TagDAO {

	@Override
	public int addTags(String strTags) {
		String[] tags = strTags.split(" ");
		String sql = "";
		for(int i=0;i<tags.length;i++){
			sql = "SELECT COUNT(*) FROM TAG WHERE TAG='" + tags[i] +"'";
			int cnt = this.getJdbcTemplate().queryForInt(sql);
			if(cnt > 0){
				sql = "UPDATE TAG SET COUNT=COUNT+1 WHERE TAG='"+tags[i]+"'";
				this.getJdbcTemplate().update(sql);
			}else{
				sql ="INSERT INTO TAG (TAG,COUNT,DESCRIPTION) VALUES(?,?,?)";
				Object[] args = new Object[] {tags[i],1,""};
				getJdbcTemplate().update(sql, args);
			}
		}
		return 1;
	}
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
