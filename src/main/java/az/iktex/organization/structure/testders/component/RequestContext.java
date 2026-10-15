package az.iktex.organization.structure.testders.component;

import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.time.LocalDateTime;
import java.util.UUID;

//14. RequestContext class-ı yarat: requestId (UUID-in ilk 8 simvolu), startTime.
@Component
//18. @RequestScope-u @Scope("request") ilə əvəz et (proxyMode olmadan).Tətbiq açıldımı? Xətanı şərh kimi yaz, sonra düzəlt.
@Scope(value = "request",proxyMode = ScopedProxyMode.TARGET_CLASS)
@Getter
public class RequestContext {
    private String requestId = UUID.randomUUID().toString().substring(0,8);
    private Long startTime = System.currentTimeMillis();

}
