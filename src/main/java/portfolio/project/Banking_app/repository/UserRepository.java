package portfolio.project.Banking_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import portfolio.project.Banking_app.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
