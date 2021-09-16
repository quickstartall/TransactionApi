package com.raquibul.bank.payment.rest.service;

import com.raquibul.bank.payment.rest.entity.Role;

import java.util.Collection;
import java.util.Optional;


public interface RoleService {
	public Collection<Role> findAll();
	public Optional<Role> findById(Long id);
	public Role saveOrUpdate(Role t);
	public String deleteById(Long id);
}
