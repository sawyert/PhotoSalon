package uk.co.drumcoder.photosalon.organisation;

import java.util.List;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface TenantDao {
	@SqlQuery("SELECT id, name, slug FROM Tenants")
	@RegisterBeanMapper(Tenant.class)
	List<Tenant> findAll();

	@SqlQuery("SELECT id, name, slug FROM Tenants WHERE id=:id")
	@RegisterBeanMapper(Tenant.class)
	Tenant findById(@Bind("id") long id);

	@SqlUpdate("INSERT INTO Tenants (name, slug) VALUES (:name, :slug)")
	void insert(@BindBean Tenant tenant);
}
