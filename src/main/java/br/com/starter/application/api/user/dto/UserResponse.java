package br.com.starter.application.api.user.dto;

import java.util.List;
import java.util.UUID;

import br.com.starter.domain.privilege.Privilege;
import br.com.starter.domain.profile.Profile;
import br.com.starter.domain.user.User;

public record UserResponse(UUID id, String username, Profile profile, String role, List<String> privileges) {
        public static UserResponse fromUser(User user) {
        return new UserResponse(
            user.getId(),
            user.getAuth().getUsername(),
            user.getProfile(),
            user.getRole().getName(),
            user.getPrivileges().stream()
                .map(Privilege::getName)
                .toList()
        );
    }
}
