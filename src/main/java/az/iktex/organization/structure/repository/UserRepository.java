package az.iktex.organization.structure.repository;

import az.iktex.organization.structure.dto.request.CreateUserRequest;
import az.iktex.organization.structure.dto.response.UserInfoResponse;
import az.iktex.organization.structure.testders.component.RequestContext;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public interface UserRepository {

    UserInfoResponse getUserById(Long id);
    List<UserInfoResponse> getAllUsers();
    Long createUser(CreateUserRequest createUserRequest);
    default void init(){};
    default void destroyUser(){};
}
