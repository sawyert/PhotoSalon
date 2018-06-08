package uk.co.drumcoder.photosalon;

import com.codahale.metrics.health.HealthCheck;

public class PhotoSalonHealthCheck extends HealthCheck {

	@Override
	protected Result check() throws Exception {
		return Result.healthy();
	}

}
