package pl.shonsu.jsonbusage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.shonsu.jsonbusage.module.User;

import java.util.List;

@RepositoryRestResource
interface UserRepository extends JpaRepository<User, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM \"user\" u where u.address->>'street'=:street")
    List<User> findByAddressStreet(@Param("street") String street);

    @Query(nativeQuery = true, value = "SELECT * FROM \"user\" u where u.address @> jsonb_build_object('city',:city)")
    List<User> findByAddressCity(@Param("city") String city);


    // useful query SELECT oprname, oprcode FROM pg_operator WHERE oprname LIKE '%?%'
    @Query(nativeQuery = true, value = "SELECT * FROM \"user\" u where jsonb_exists(u.roles, :role)")
    List<User> findByRole(@Param("role") String role);

    List<User> findUserByRoles(String role);
}
