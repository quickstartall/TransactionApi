package com.raquibul.bank.payment.rest.repository;

import com.raquibul.bank.payment.rest.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}