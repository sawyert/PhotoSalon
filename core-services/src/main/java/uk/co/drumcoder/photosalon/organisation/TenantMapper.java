package uk.co.drumcoder.photosalon.organisation;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

public class TenantMapper implements RowMapper<Tenant> {

	@Override
	public Tenant map(ResultSet rs, StatementContext ctx) throws SQLException {
		return new Tenant(rs.getString("name"), rs.getString("slug"));
	}

}
