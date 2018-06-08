package uk.co.drumcoder.photosalon;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class PhotoSalonConfiguration extends Configuration {

	@JsonProperty("swagger")
	public SwaggerBundleConfiguration swaggerBundleConfiguration;

	@Valid
	@NotNull
	@JsonProperty
	private final DataSourceFactory database = new DataSourceFactory();

	public DataSourceFactory getDataSourceFactory() {
		return this.database;
	}
}
