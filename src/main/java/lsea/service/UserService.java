package lsea.service;

import lsea.dto.CreateUserDto;
import lsea.entity.User;
import lsea.errors.GenericConflictError;
import lsea.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Service layer for all business actions regarding User entity.
 */
@Service
public class UserService {

    /**
     * Repository for user management.
     */
    private UserRepository userRepository;

    /**
     * Constructs a new instance of UserService class with UserRepositoryInterface dependency injection.
     * @param userRepository an instance of UserRepositoryInterface
     */
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Saves a new User to the UserRepository.
     * @param dto a new User instance
     * @throws Exception if a user with the same email address already exists in the UserRepository
     */
    @Transactional
    public void createOne(CreateUserDto dto) throws Exception {
        Optional<User> existingUser = userRepository.findByEmail(dto.getEmail());
        if (existingUser.isPresent()) {
            throw new GenericConflictError("User with email " + dto.getEmail() + " already exists.");
        }
        final User user = User.create(dto);
        userRepository.save(user);
    }

    /**
     * Retrieves a User instance by id.
     * @param id the id of the User instance to be retrieved
     * @return an Optional containing the User instance if it exists, otherwise returns an empty Optional
     */
    public Optional<User> findById(UUID id) {
        return userRepository.findById(id.toString());
    }

}
