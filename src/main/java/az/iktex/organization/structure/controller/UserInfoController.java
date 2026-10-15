package az.iktex.organization.structure.controller;

import az.iktex.organization.structure.dto.request.CreateUserRequest;
import az.iktex.organization.structure.dto.response.UserInfoResponse;
import az.iktex.organization.structure.service.UserInfoService;
import az.iktex.organization.structure.testders.component.RecentlyViewedUsers;
import az.iktex.organization.structure.testders.component.RequestContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-info")
public class UserInfoController {
    private final UserInfoService userInfoService;
    //15. RequestContext-i üç qatın hamısına inject et: controller, service, repository.
    private final RequestContext requestContext;
    private final RecentlyViewedUsers recentlyViewedUsers;

    public UserInfoController(UserInfoService userInfoService, RequestContext requestContext,RecentlyViewedUsers recentlyViewedUsers) {
        this.userInfoService = userInfoService;
        this.requestContext = requestContext;
        this.recentlyViewedUsers=recentlyViewedUsers;
        // 6-cı sual: Controller konstruktoruna hashCode logu
        System.out.println("UserInfoController yaradildi: " + this.hashCode());
    }

    @GetMapping("/{id}")
    public UserInfoResponse getUserById(@PathVariable Long id) {
        //16. Hər qatın getUserById metodunda log yaz: [Controller] requestId=..., [Service] ..., [Repository]
        System.out.println("[Controller] requestId" + requestContext.getRequestId());
        //17. Controller-də cavab qaytarmazdan əvvəl sorğunun neçə millisaniyə çəkdiyini log-a yaz.
        long duration = System.currentTimeMillis() - requestContext.getStartTime();
        System.out.println("[Controller] Sorğu müddəti: " + duration + "ms");
        //21. getUserById hər çağırılanda ID siyahıya əlavə olunsun.
        recentlyViewedUsers.addUserId(id);

        return userInfoService.getUserById(id);
    }

    @GetMapping("/all")
    public List<UserInfoResponse> getAllUsers() {
        return userInfoService.getAllUsers();
    }

    // 7-ci sual: POST /create ilə user yarat, qaytarılan id Long ilə geri dönür.
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Long createUser(@RequestBody CreateUserRequest createUserRequest) {
        return userInfoService.createUser(createUserRequest);
    }

    @GetMapping("/requestContext")
    public void getRequestContext(){
        System.out.println(requestContext.getClass());
    }

    //22. GET /api/v1/user-info/recent endpoint-i yaz — ID-ləri yox, tam user məlumatını qaytarsın. Service-də yeni metod lazım olacaq.
    @GetMapping("/recent")
    public List<UserInfoResponse> getRecentUsers() {
        return userInfoService.getRecentUsers();
    }
}