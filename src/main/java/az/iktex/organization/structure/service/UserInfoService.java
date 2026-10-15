package az.iktex.organization.structure.service;

import az.iktex.organization.structure.dto.request.CreateUserRequest;
import az.iktex.organization.structure.dto.response.UserInfoResponse;
import az.iktex.organization.structure.repository.UserRepository;
import az.iktex.organization.structure.testders.component.NotificationService;
import az.iktex.organization.structure.testders.component.RecentlyViewedUsers;
import az.iktex.organization.structure.testders.component.RequestContext;
import az.iktex.organization.structure.testders.component.UserAuditLog;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserInfoService {
    private final UserRepository userRepository;
    private final ObjectProvider<UserAuditLog> userAuditLogProvider;
    // Optional olaraq təyin edirik
    private NotificationService notificationService;

    //15. RequestContext-i üç qatın hamısına inject et: controller, service, repository.
    private final RequestContext requestContext;
    private final RecentlyViewedUsers recentlyViewedUsers;

    public UserInfoService(@Qualifier("mockUserRepository") UserRepository userRepository, ObjectProvider<UserAuditLog> userAuditLogProvider, RequestContext requestContext, RecentlyViewedUsers recentlyViewedUsers) {
        this.userRepository = userRepository;
        this.userAuditLogProvider = userAuditLogProvider;
        this.requestContext = requestContext;
        this.recentlyViewedUsers = recentlyViewedUsers;
        // 6-cı sual: Service konstruktoruna hashCode logu
        System.out.println("UserInfoService yaradildi: " + this.hashCode());
    }

    public UserInfoResponse getUserById(Long id) {
        //16. Hər qatın getUserById metodunda log yaz: [Controller] requestId=..., [Service] ..., [Repository]
        System.out.println("[Service] requestId=" + requestContext.getRequestId());
        return userRepository.getUserById(id);
    }

    public List<UserInfoResponse> getAllUsers() {
        return userRepository.getAllUsers();
    }

    // 7-ci sual: POST /create ilə user yarat, qaytarılan id Long ilə geri dönür.
    public Long createUser(CreateUserRequest createUserRequest) {
        // Hər .getObject() çağırışı YENİ bir UserAuditLog obyekti yaradır!
        UserAuditLog userAuditLog = userAuditLogProvider.getObject();
        userAuditLog.setAction("CREATE_USER");
        System.out.println("User yaradildi, Action: " + userAuditLog.getAction());
        //31. ConsoleNotificationService əlavə et, createUser-də Xoş gəldin log-unu gör.
        if (notificationService!=null){
            notificationService.sendWelcome(createUserRequest.getEmail());
        }
        return userRepository.createUser(createUserRequest);
    }

    //22. GET /api/v1/user-info/recent endpoint-i yaz — ID-ləri yox, tam user məlumatını qaytarsın. Service-də yeni metod lazım olacaq.
    public List<UserInfoResponse> getRecentUsers() {
        List<Long> recentIds = recentlyViewedUsers.getRecentIds();
        List<UserInfoResponse> recentUsers = new ArrayList<>();
        for (Long id : recentIds) {
            try {
                UserInfoResponse user = userRepository.getUserById(id);
                recentUsers.add(user);
            } catch (Exception e) {

            }
        }
        return recentUsers;
    }
    //30. NotificationService interface-i yarat (sendWelcome(String email)) və UserInfoService-ə optional inject et. Implementasiyasız işə sal — xəta olmamalıdır.
    @Autowired(required = false)
    public void setNotificationService(NotificationService notificationService){
        this.notificationService = notificationService;
    }
}
