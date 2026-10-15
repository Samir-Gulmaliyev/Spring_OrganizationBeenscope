package az.iktex.organization.structure.repository;

import az.iktex.organization.structure.dto.request.CreateUserRequest;
import az.iktex.organization.structure.dto.response.UserInfoResponse;
import az.iktex.organization.structure.testders.component.RequestContext;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
//28. Xətanı birinci yolla həll et: InMemoryUserRepository default olsun.
//@Primary
public class InMemoryUserRepository implements UserRepository {

    // 1 və 2-ci sual: Thread-safe storage və AtomicLong sayğac
    public Map<Long, UserInfoResponse> storage = new ConcurrentHashMap<>();
    public AtomicLong counter = new AtomicLong(0);

    //15. RequestContext-i üç qatın hamısına inject et: controller, service, repository.
    private final RequestContext requestContext;

    public InMemoryUserRepository(RequestContext requestContext) {
        this.requestContext = requestContext;
        System.out.println("InMemoryUserRepository yaradildi: " + this.hashCode());
    }

    // 3-cü sual: Tətbiq açılanda ilkin yüklənmə və log
    @PostConstruct
    @Override
    public void init() {
        // Burada avtomatik 3 ədəd user yaradıb storage-ə əlavə edirik.
        long id1 = counter.incrementAndGet();
        storage.put(id1, new UserInfoResponse(id1, "User1", "user1@gmail.com", true, LocalDateTime.now()));
        long id2 = counter.incrementAndGet();
        storage.put(id2, new UserInfoResponse(id2, "User2", "user2@gmail.com", true, LocalDateTime.now()));
        long id3 = counter.incrementAndGet();
        storage.put(id3, new UserInfoResponse(id3, "User3", "user3@gmail.com", false, LocalDateTime.now()));
        System.out.println("UserRepository hazirdir, 3 user yuklendi");
    }

    // 4-cü sual: Tətbiq bağlananda storage-in təmizlənməsi
    @Override
    public void destroyUser() {
        storage.clear();
        System.out.println("Storage təmizləndi, UserRepository bağlandı, tətbiq bağlanır.");
    }

    // 5-ci sual: User tapılmasa NoSuchElementException atılması
    @Override
    public UserInfoResponse getUserById(Long id) {
        UserInfoResponse user = null;
        if (id != null) {
            user = storage.get(id); // storage-də axtarırıq
            if (user == null) {
                throw new NoSuchElementException("User tapılmadı: ID = " + id);
            }
        }
        //16. Hər qatın getUserById metodunda log yaz: [Controller] requestId=..., [Service] ..., [Repository]
        System.out.println("[Repository] requestId=" + requestContext.getRequestId());
        return user;

        /* Müəllimin yazdığı kod
        return new UserInfoResponse(
                id,
                "test_istifadeci",
                "user" + id + "@example.com",
                true,
                LocalDateTime.now()
        );*/
    }

    @Override
    public List<UserInfoResponse> getAllUsers() {
        return storage.values().stream().toList();
        /*
        Müəllimin yazdığı kod
        return List.of(getUserById(1L), getUserById(2L), getUserById(3L));
         */
    }

    // 7-ci sual: POST /create ilə user yarat, qaytarılan id Long ilə geri dönür.
    @Override
    public Long createUser(CreateUserRequest createUserRequest) {
        UserInfoResponse user = new UserInfoResponse();
        user.setId(counter.incrementAndGet());
        user.setFullName(createUserRequest.getFullName());
        user.setEmail(createUserRequest.getEmail());
        user.setCreatedAt(LocalDateTime.now());
        storage.put(user.getId(), new UserInfoResponse(user.getId(), user.getFullName(), user.getEmail(), true, user.getCreatedAt()));
        return user.getId();
    }
}
