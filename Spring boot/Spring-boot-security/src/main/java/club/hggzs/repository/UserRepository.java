package club.hggzs.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import club.hggzs.entity.Users;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {
}
