package uk.co.drumcoder.photosalon.security;

import java.security.Principal;
import java.util.Set;

public class User implements Principal {
	private final String name;

	private final Set<String> roles;

	public User(String name) {
		this.name = name;
		this.roles = null;
	}

	public User(String name, Set<String> roles) {
		this.name = name;
		this.roles = roles;
	}

	public int getId() {
		return (int) (Math.random() * 100);
	}

	@Override
	public String getName() {
		return this.name;
	}

	public Set<String> getRoles() {
		return this.roles;
	}
}
