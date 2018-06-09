package uk.co.drumcoder.photosalon.organisation;

import java.util.List;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface TenantDao {
	@SqlUpdate("DELETE FROM tenants WHERE slug = :slug")
	void deleteTenant(@Bind("slug") String slug);

	@SqlQuery("SELECT name, slug FROM tenants")
	@RegisterBeanMapper(Tenant.class)
	List<Tenant> findAll();

	@SqlQuery("SELECT name, slug FROM tenants WHERE slug=:slug")
	@RegisterBeanMapper(Tenant.class)
	Tenant findBySlug(@Bind("slug") String slug);

	@SqlUpdate("INSERT INTO tenants (name, slug) VALUES (:name, :slug)")
	void insert(@BindBean Tenant tenant);
}
