package uk.co.drumcoder.photosalon;

import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.jdbi.v3.core.Jdbi;

import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import uk.co.drumcoder.photosalon.organisation.TenantDao;
import uk.co.drumcoder.photosalon.organisation.TenantResource;
import uk.co.drumcoder.photosalon.security.AppAuthorizer;
import uk.co.drumcoder.photosalon.security.AppBasicAuthenticator;
import uk.co.drumcoder.photosalon.security.User;

public class PhotoSalonApplication extends Application<PhotoSalonConfiguration> {
	public static void main(String[] args) throws Exception {
		new PhotoSalonApplication().run(args);
	}

	@Override
	public void initialize(Bootstrap<PhotoSalonConfiguration> bootstrap) {
		bootstrap.addBundle(new MigrationsBundle<PhotoSalonConfiguration>() {
			@Override
			public DataSourceFactory getDataSourceFactory(PhotoSalonConfiguration configuration) {
				return configuration.getDataSourceFactory();
			}
		});

		bootstrap.addBundle(new SwaggerBundle<PhotoSalonConfiguration>() {
			@Override
			protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(PhotoSalonConfiguration configuration) {
				return configuration.swaggerBundleConfiguration;
			}
		});
	}

	@Override
	public void run(PhotoSalonConfiguration configuration, Environment environment) throws Exception {
		// health checks
		final PhotoSalonHealthCheck healthCheck = new PhotoSalonHealthCheck();
		environment.healthChecks().register("global", healthCheck);

		// database connection
		final JdbiFactory factory = new JdbiFactory();
		final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "postgresql");
		final TenantDao tenantDao = jdbi.onDemand(TenantDao.class);

		// Add resources
		environment.jersey().register(new TenantResource(tenantDao));

		// Security
		environment.jersey()
				.register(new AuthDynamicFeature(
						new BasicCredentialAuthFilter.Builder<User>().setAuthenticator(new AppBasicAuthenticator())
								.setAuthorizer(new AppAuthorizer()).setRealm("BASIC-AUTH-REALM").buildAuthFilter()));
		environment.jersey().register(RolesAllowedDynamicFeature.class);
		environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
	}
}
