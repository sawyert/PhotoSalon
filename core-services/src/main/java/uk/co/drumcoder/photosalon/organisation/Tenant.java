package uk.co.drumcoder.photosalon.organisation;

public class Tenant {
	private String name;
	private String slug;

	public Tenant() {
	}

	public Tenant(String name, String slug) {
		this.name = name;
		this.slug = slug;
	}

	public String getName() {
		return this.name;
	}

	public String getSlug() {
		return this.slug;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}
}
