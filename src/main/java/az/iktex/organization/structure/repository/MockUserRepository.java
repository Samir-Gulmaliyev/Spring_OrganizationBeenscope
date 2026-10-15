package az.iktex.organization.structure.repository;

import az.iktex.organization.structure.dto.request.CreateUserRequest;
import az.iktex.organization.structure.dto.response.UserInfoResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

// 27. MockUserRepository yaz — həmişə adı Mock User olan 2 user qaytarsın. Tətbiqi işə sal və xətanı qeyd et.
@Repository
public class MockUserRepository implements UserRepository{
    @Override
    public UserInfoResponse getUserById(Long id) {
        return new UserInfoResponse(id, "Mock User", "mock" + id + "@example.com", true, LocalDateTime.now());
    }

    @Override
    public List<UserInfoResponse> getAllUsers() {
        return List.of(
                new UserInfoResponse(101L, "Mock User", "mock1@example.com", true, LocalDateTime.now()),
                new UserInfoResponse(102L, "Mock User", "mock2@example.com", true, LocalDateTime.now())
        );
    }

    @Override
    public Long createUser(CreateUserRequest createUserRequest) {
        return 999L; // Mock ID
    }
}
