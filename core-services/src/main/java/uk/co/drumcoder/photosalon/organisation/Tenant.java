package uk.co.drumcoder.photosalon.organisation;

public class Tenant {
	private long id;
	private String name;
	private String slug;

	public Tenant() {

	}

	public Tenant(long id, String name, String slug) {
		this.id = id;
		this.name = name;
		this.slug = slug;
	}

	public long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getSlug() {
		return this.slug;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}
}
