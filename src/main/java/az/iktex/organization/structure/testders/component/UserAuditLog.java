package az.iktex.organization.structure.testders.component;

import jakarta.annotation.PreDestroy;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

// 9-ci sual: component paketində UserAuditLog class-ı yarat: auditId (UUID), createdAt (LocalDateTime), action
//(String, setter ilə).
@Component
@Scope("prototype")
@Getter
public class UserAuditLog {
    private String auditId = UUID.randomUUID().toString();
    private LocalDateTime createdAt = LocalDateTime.now();
    @Setter
    private String action;

    public UserAuditLog(){
        //10. Konstruktorda UserAuditLog yaradildi: + auditId log-u yaz.
        System.out.println("UserAuditLog yaradildi:" + auditId);
    }

    //13. UserAuditLog-a @PreDestroy əlavə et və tətbiqi bağla. Log çıxdımı? Niyə? Şərh kimi yaz.
    @PreDestroy
    public void auditLogDestroy(){
        System.out.println("UserAuditLog məhv edilir: " + auditId);
    }
}
