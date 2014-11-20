package org.orcsun.sunspace.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.orcsun.sunspace.dao.ItemDAO;
import org.orcsun.sunspace.object.Item;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class ItemDaoImpl extends SunJdbcDaoSupport implements ItemDAO {

	@Override
	public long add(Item item) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(getJdbcTemplate());
		jdbcInsert.withTableName("ITEM").usingGeneratedKeyColumns(
				new String[] { "ITEMID" });
		Map parameters = new HashMap();
		parameters.put("ITEM", item.getItem());
		parameters.put("NOTE", item.getNote());
		parameters.put("DOTIME", item.getDotime());
		parameters.put("PHASEID", item.getPhaseid());
		parameters.put("CASEID",item.getCaseid());
		parameters.put("OWNER",item.getOwner());
		parameters.put("STATUS",item.getStatus());
		
		Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(
				parameters));
		return key.intValue();
	}

	@Override
	public List<Item> list(int pid, long caseid) {
		String sql ="SELECT * FROM ITEM WHERE PHASEID="+pid+" AND CASEID="+caseid;
		try{
			return this.getJdbcTemplate().query(sql, new ItemMapper());
		}catch(Exception e){
			return null;
		}
	}
	
	private static final class ItemMapper implements RowMapper<Item>{

		@Override
		public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
			Item item = new Item();
			item.setItemid(rs.getLong("ITEMID"));
			item.setItem(rs.getString("ITEM"));
			item.setNote(rs.getString("NOTE"));
			item.setDotime(rs.getTimestamp("DOTIME"));
			item.setCaseid(rs.getLong("CASEID"));
			item.setPhaseid(rs.getInt("PHASEID"));
			item.setOwner(rs.getLong("OWNER"));
			item.setStatus(rs.getInt("STATUS"));
			return item;
		}
		
	}

	@Override
	public int changeStatus(long itemid, int status) {
		String sql = "UPDATE ITEM SET STATUS="+status+" WHERE ITEMID="+itemid;
		return this.getJdbcTemplate().update(sql);
	}

}
