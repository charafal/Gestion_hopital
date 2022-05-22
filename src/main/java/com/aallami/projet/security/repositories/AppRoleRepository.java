package com.aallami.projet.security.repositories;

import com.aallami.projet.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole, Long> {
    AppRole findByRoleName(String rolename);
}
