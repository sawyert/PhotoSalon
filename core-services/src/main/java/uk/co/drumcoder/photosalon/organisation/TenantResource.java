package uk.co.drumcoder.photosalon.organisation;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.DELETE;
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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.BasicAuthDefinition;
import io.swagger.annotations.SecurityDefinition;
import io.swagger.annotations.SwaggerDefinition;

@Path("/tenants")
@Api(value = "tenants")
@SwaggerDefinition(securityDefinition = @SecurityDefinition(basicAuthDefinitions = {
		@BasicAuthDefinition(key = "basicAuth", description = "TSalon") }))
public class TenantResource {

	private final TenantDao tenantDao;

	public TenantResource(TenantDao tenantDao) {
		this.tenantDao = tenantDao;
	}

	@POST
	@RolesAllowed({ "ADMIN" })
	@ApiOperation(value = "Create a new tenant", notes = "slug must be unique", authorizations = {
			@Authorization(value = "basicAuth") })
	@ApiResponses(value = { //
			@ApiResponse(code = 201, message = "New tenant was created"), //
			@ApiResponse(code = 409, message = "Duplicate slug provided"), //
			@ApiResponse(code = 401, message = "Forbidden: Requires Login"), //
			@ApiResponse(code = 403, message = "No permission to access resource") //
	})
	public Response createTenant(Tenant tenant) throws URISyntaxException {
		final String location = "/tenants/" + tenant.getSlug();

		try {
			this.tenantDao.insert(tenant);
		} catch (final UnableToExecuteStatementException ex) {
			return Response.status(409).header("Location", location).build();
		}

		return Response.created(new URI(location)).build();
	}

	@DELETE
	@RolesAllowed({ "ADMIN" })
	@Path("{slug}")
	@ApiOperation(value = "Delete a tenant by slug", notes = "", authorizations = {
			@Authorization(value = "basicAuth") })
	@ApiResponses(value = { //
			@ApiResponse(code = 202, message = "Deleted successfully"), //
			@ApiResponse(code = 401, message = "Forbidden: Requires Login"), //
			@ApiResponse(code = 403, message = "No permission to access resource"), //
			@ApiResponse(code = 404, message = "Not found") //
	})
	public Response deleteTenant(@PathParam("slug") String slug) {
		final Tenant tenantToDelete = this.tenantDao.findBySlug(slug);
		if (tenantToDelete == null) {
			return Response.status(404).build();
		}

		this.tenantDao.deleteTenant(slug);
		return Response.status(202).build();
	}

	@GET
	@RolesAllowed({ "ADMIN" })
	@Timed
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "List all tenants", notes = "", response = Tenant.class, responseContainer = "List", authorizations = {
			@Authorization(value = "basicAuth") })
	@ApiResponses(value = { //
			@ApiResponse(code = 200, message = "Successful"), //
			@ApiResponse(code = 401, message = "Forbidden: Requires Login"), //
			@ApiResponse(code = 403, message = "No permission to access resource") //
	})
	public List<Tenant> listAll() throws SQLException {
		return this.tenantDao.findAll();
	}

	@GET
	@Path("{slug}")
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get a single tenant by slug", notes = "", response = Tenant.class, authorizations = {
			@Authorization(value = "basicAuth") })
	@ApiResponses(value = { //
			@ApiResponse(code = 200, message = "Successful"), //
			@ApiResponse(code = 401, message = "Forbidden: Requires Login"), //
			@ApiResponse(code = 403, message = "No permission to access resource"), //
			@ApiResponse(code = 404, message = "Not found") //
	})
	public Response singleTenant(@PathParam("slug") String slug) {
		final Tenant tenantToReturn = this.tenantDao.findBySlug(slug);
		if (tenantToReturn != null) {
			return Response.ok().entity(tenantToReturn).build();
		}
		return Response.status(404).build();
	}

}
