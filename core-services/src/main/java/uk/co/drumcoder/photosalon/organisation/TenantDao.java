package uk.co.drumcoder.photosalon.organisation;

import java.util.List;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface TenantDao {
	@SqlUpdate("CREATE TABLE Tenants (id int primary key, name varchar(100), slug varchar(25))")
	void createTenantsTable();

	@SqlQuery("SELECT id, name, slug FROM Tenants")
	@RegisterBeanMapper(TenantMapper.class)
	List<Tenant> findAll();

	@SqlQuery("SELECT id, name, slug FROM Tenants WHERE id=:id")
	@RegisterBeanMapper(TenantMapper.class)
	Tenant findById(@Bind("id") int id);

	@SqlUpdate("INSERT INTO Tenants (id, name, slug) VALUES (:id, :name, :slug")
	void insert(@BindBean Tenant tenant);
}
