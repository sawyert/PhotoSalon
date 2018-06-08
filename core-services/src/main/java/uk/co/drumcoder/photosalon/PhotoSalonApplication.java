package uk.co.drumcoder.photosalon;

import org.jdbi.v3.core.Jdbi;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.jdbi3.bundles.JdbiExceptionsBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import uk.co.drumcoder.photosalon.organisation.TenantDao;
import uk.co.drumcoder.photosalon.organisation.TenantResource;

public class PhotoSalonApplication extends Application<PhotoSalonConfiguration> {
	public static void main(String[] args) throws Exception {
		new PhotoSalonApplication().run(args);
	}

	@Override
	public void initialize(Bootstrap<PhotoSalonConfiguration> bootstrap) {
		bootstrap.addBundle(new JdbiExceptionsBundle());

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
		final PhotoSalonHealthCheck healthCheck = new PhotoSalonHealthCheck();
		environment.healthChecks().register("global", healthCheck);

		// database connection
		final JdbiFactory factory = new JdbiFactory();
		final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "postgresql");
		final TenantDao tenantDao = jdbi.onDemand(TenantDao.class);

		// Add resources
		environment.jersey().register(new TenantResource(configuration, tenantDao));
	}
}
