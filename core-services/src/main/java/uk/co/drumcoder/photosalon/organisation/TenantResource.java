package uk.co.drumcoder.photosalon.organisation;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;

import io.swagger.annotations.Api;
import uk.co.drumcoder.photosalon.PhotoSalonConfiguration;

@Path("/tenants")
@Api(value = "tenants")
@Produces(MediaType.APPLICATION_JSON)
public class TenantResource {

	private final PhotoSalonConfiguration configuration;
	private final TenantDao tenantDao;

	public TenantResource(PhotoSalonConfiguration configuration, TenantDao tenantDao) {
		this.configuration = configuration;
		this.tenantDao = tenantDao;
	}

	@POST
	public void createTenant(Tenant tenant) {
		this.tenantDao.insert(tenant);
	}

	@GET
	@Timed
	public List<Tenant> listAll() throws SQLException {
		return this.tenantDao.findAll();
	}

	@GET
	@Path("{id}")
	public Tenant singleTenant(@PathParam("id") String id) {
		final int idAsInt = Integer.parseInt(id);
		return this.tenantDao.findById(idAsInt);
	}
}
