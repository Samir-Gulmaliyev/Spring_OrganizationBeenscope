package az.iktex.organization.structure.testders.component;

import az.iktex.organization.structure.dto.response.UserInfoResponse;
import az.iktex.organization.structure.repository.UserRepository;
import az.iktex.organization.structure.service.UserInfoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals; // assertEquals üçün import
import static org.mockito.Mockito.when;

public class UserInfoServiceTest {
    void testGetUserInfo(){
        /*// 1. Mock repository yaradırıq
        UserRepository mockRepository = Mockito.mock(UserRepository.class);
        UserInfoResponse mockUser = new UserInfoResponse(1L, "Test User", "test@example.com", true, LocalDateTime.now());
        when(mockRepository.getUserById(1L)).thenReturn(mockUser);
        // 2. Service-i əllə `new` edərək mock repository-ni konstruktora ötürürük
        UserInfoService userInfoService = new UserInfoService(mockRepository);
        // 3. Metodu yoxlayırıq
        UserInfoResponse result = userInfoService.getUserById(1L);
        assertEquals("Test User", result.getFullName());*/
    }
}
