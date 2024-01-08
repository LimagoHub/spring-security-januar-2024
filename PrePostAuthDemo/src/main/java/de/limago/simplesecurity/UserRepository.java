package de.limago.simplesecurity;


import de.limago.simplesecurity.security.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {

}
