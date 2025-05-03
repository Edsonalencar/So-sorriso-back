package br.com.starter.domain.role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findByName(String name);

    boolean existsByName(String name);

    Page<Role> findAll(Pageable pageable);

    @Query(value = "SELECT COUNT(*) > 0 FROM privileges_on_roles WHERE role_id = :roleId AND privilege_id = :privilegeId", nativeQuery = true)
    boolean isPrivilegeAssignedToRole(UUID roleId, UUID privilegeId);

    @Modifying
    @Query(value = "INSERT INTO privileges_on_roles (role_id, privilege_id) VALUES (:roleId, :privilegeId)", nativeQuery = true)
    int addPrivilegeToRole(UUID roleId, UUID privilegeId);
    

    @Modifying
    @Query(value = "DELETE FROM privileges_on_roles WHERE role_id = :roleId AND privilege_id = :privilegeId", nativeQuery = true)
    int removePrivilegeFromRole(UUID roleId, UUID privilegeId);
}

