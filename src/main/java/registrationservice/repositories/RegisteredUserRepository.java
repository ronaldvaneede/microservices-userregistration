package registrationservice.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import registrationservice.models.RegisteredUser;

public interface RegisteredUserRepository extends MongoRepository<RegisteredUser, String> {

}