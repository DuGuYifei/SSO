package lsea.service;

import lsea.dto.CreateLogDto;
import lsea.entity.Log;
import lsea.entity.User;
import lsea.errors.GenericForbiddenError;
import lsea.repository.LogRepository;
import lsea.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

/**
 * Service layer for all business actions regarding Log entity.
 */
@Service
public class LogService {

    private final LogRepository logRepository;

    private final UserRepository userRepository;

    public LogService(LogRepository logRepository, UserRepository userRepository) {

        this.logRepository = logRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Log createOne(CreateLogDto dto, String token) throws GenericForbiddenError {

        UUID userId = User.verifyToken(token);

        Optional<User> user = userRepository.findById(userId);

        if (!user.isPresent()) {
            throw new GenericForbiddenError("User not found");
        }

        Log log = Log.create(dto, user.get());
        return logRepository.save(log);
    }

}
