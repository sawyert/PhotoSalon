package uk.co.drumcoder.photosalon.tests;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import uk.co.drumcoder.photosalon.PhotoSalonConfiguration;
import uk.co.drumcoder.photosalon.organisation.Tenant;
import uk.co.drumcoder.photosalon.organisation.TenantDao;
import uk.co.drumcoder.photosalon.organisation.TenantResource;

public class TenantTests {

	@Test
	public void testListTenants() throws SQLException {
		// Arrange
		final TenantDao tenantDao = Mockito.mock(TenantDao.class);
		final List<Tenant> testTenants = Arrays.asList( //
				new Tenant(1, "Ilkley Camera Club", "icc"), //
				new Tenant(2, "Yorkshire Salon", "yorkshire-salon") //
		);
		Mockito.when(tenantDao.findAll()).thenReturn(testTenants);

		final PhotoSalonConfiguration photoSalonConfiguration = new PhotoSalonConfiguration();
		final TenantResource tenantResource = new TenantResource(photoSalonConfiguration, tenantDao);

		// Act
		final List<Tenant> tenants = tenantResource.listAll();

		// Assert
		Assert.assertEquals(2, tenants.size());
		final Tenant firstTenant = tenants.get(0);
		Assert.assertEquals(1, firstTenant.getId());
		Assert.assertEquals("Ilkley Camera Club", firstTenant.getName());
		Assert.assertEquals("icc", firstTenant.getSlug());
	}

	@Test
	public void testSelectTenantById() throws SQLException {
		// Arrange
		final TenantDao tenantDao = Mockito.mock(TenantDao.class);
		final Tenant testTenant = new Tenant(1, "Ilkley Camera Club", "icc");
		Mockito.when(tenantDao.findById(1)).thenReturn(testTenant);

		final PhotoSalonConfiguration photoSalonConfiguration = new PhotoSalonConfiguration();
		final TenantResource tenantResource = new TenantResource(photoSalonConfiguration, tenantDao);

		// Act
		final Tenant returnedTenant = tenantResource.singleTenant("1");

		// Assert
		Assert.assertEquals(1, returnedTenant.getId());
		Assert.assertEquals("Ilkley Camera Club", returnedTenant.getName());
		Assert.assertEquals("icc", returnedTenant.getSlug());
	}
}
