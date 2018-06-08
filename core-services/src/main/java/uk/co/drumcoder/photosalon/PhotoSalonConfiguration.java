package uk.co.drumcoder.photosalon;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

public class PhotoSalonConfiguration extends Configuration {

	@Valid
	@NotNull
	@JsonProperty
	private final DataSourceFactory database = new DataSourceFactory();

	public DataSourceFactory getDataSourceFactory() {
		return this.database;
	}
}
