package uk.co.drumcoder.photosalon.organisation;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jdbi.v3.core.statement.UnableToExecuteStatementException;

import com.codahale.metrics.annotation.Timed;

import io.swagger.annotations.Api;

@Path("/tenants")
@Api(value = "tenants")
public class TenantResource {

	private final TenantDao tenantDao;

	public TenantResource(TenantDao tenantDao) {
		this.tenantDao = tenantDao;
	}

	@POST
	public Response createTenant(Tenant tenant) throws URISyntaxException {
		final String location = "/tenants/" + tenant.getSlug();

		try {
			this.tenantDao.insert(tenant);
		} catch (final UnableToExecuteStatementException ex) {
			return Response.status(409).header("Location", location).build();
		}

		return Response.created(new URI(location)).build();
	}

	@GET
	@Timed
	@Produces(MediaType.APPLICATION_JSON)
	public List<Tenant> listAll() throws SQLException {
		return this.tenantDao.findAll();
	}

	@GET
	@Path("{slug}")
	@Produces(MediaType.APPLICATION_JSON)
	public Tenant singleTenant(@PathParam("slug") String slug) {
		return this.tenantDao.findBySlug(slug);
	}
}
