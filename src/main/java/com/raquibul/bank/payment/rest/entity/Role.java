package com.raquibul.bank.payment.rest.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class Role {
	@Id
	@GeneratedValue
	private Long id;
	private String name;

	@OneToMany(targetEntity = User.class, mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<User> users;

	public Role() {
	}

	public Role(Long id, @NotNull String name) {
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
