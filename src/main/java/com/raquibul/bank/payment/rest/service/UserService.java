package com.raquibul.bank.payment.rest.service;

import com.raquibul.bank.payment.rest.entity.User;

import java.util.Collection;
import java.util.Optional;

public interface UserService {
	public Collection<User> findAll();
	public Optional<User> findById(Long id);
	public User saveOrUpdate(User t);
	public String deleteById(Long id);
}
