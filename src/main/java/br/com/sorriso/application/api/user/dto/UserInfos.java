package br.com.sorriso.application.api.user.dto;

import java.util.List;
import java.util.UUID;

import br.com.sorriso.domain.privilege.Privilege;

public record UserInfos(UUID id,String username, String name, String role, List<Privilege> privileges, String token) {
    
}
