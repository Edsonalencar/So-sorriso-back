package br.com.sorriso.infrastructure.config.security;


import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CustomRoleHierarchy implements RoleHierarchy {

    @Override
    public Collection<? extends GrantedAuthority> getReachableGrantedAuthorities(
            Collection<? extends GrantedAuthority> authorities) {

        Set<GrantedAuthority> reachableAuthorities = new HashSet<>(authorities);

        authorities.forEach(authority -> {
            if ("ROLE_ADMIN".equals(authority.getAuthority())) {
                reachableAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                reachableAuthorities.add(new SimpleGrantedAuthority("ROLE_DENTIST"));
                reachableAuthorities.add(new SimpleGrantedAuthority("ROLE_ATTENDANT"));
            } else if ("ROLE_DENTIST".equals(authority.getAuthority())) {
                reachableAuthorities.add(new SimpleGrantedAuthority("ROLE_DENTIST"));
                reachableAuthorities.add(new SimpleGrantedAuthority("ROLE_ATTENDANT"));
            } else if ("ROLE_ATTENDANT".equals(authority.getAuthority())) {
                reachableAuthorities.add(new SimpleGrantedAuthority("ROLE_ATTENDANT"));
            }
        });
        return reachableAuthorities;
    }
}


