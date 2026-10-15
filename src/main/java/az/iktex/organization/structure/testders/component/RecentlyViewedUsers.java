package az.iktex.organization.structure.testders.component;

import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.LinkedList;
import java.util.List;

//20. RecentlyViewedUsers yarat: son 5 ID-ni saxlasın, təkrar olmasın, ən yenisi əvvəldə.
@Component
@SessionScope
public class RecentlyViewedUsers {
    private final LinkedList<Long> recentIds = new LinkedList<>();
    private static final int MAX_SIZE = 5;

    public void addUserId(Long userId) {
        // 1. Əgər bu ID artıq siyahıdadırsa, əvvəlkini silirik (təkrar olmasın) recentIds.remove(userId);
        recentIds.remove(userId);

        // 2. Yeni ID-ni siyahının ƏVVƏLİNƏ əlavə edirik (ən yenisi əvvəldə olur)
        recentIds.addFirst(userId);

        // 3. Əgər ölçü 5-i keçərsə, ən sonda olanı (ən köhnəni) silirik
        if (recentIds.size() > MAX_SIZE) {
            recentIds.removeLast();
        }

        System.out.println("Son baxılanlar: " + recentIds);
    }

    public List<Long> getRecentIds() {
        return new LinkedList<>(recentIds); // Kapsullaşdırma (encapsulation) üçün surətini qaytarırıq
    }

    //23. Session müddətini 1 dəqiqəyə endir və @PreDestroy log-unun çıxdığını yoxla.
    @PreDestroy
    public void onDestroy() {
        System.out.println("Session scope-a aid RecentlyViewedUsers bean-i məhv edilir (Destroy olundu)!");
    }
}
