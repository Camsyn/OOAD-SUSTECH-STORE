package top.camsyn.store.auth.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import top.camsyn.store.commons.entity.auth.Account;

import java.util.Collection;


@Getter
public class AuthUser extends User {
    private String email;

    public AuthUser(Account dbUser, Collection<? extends GrantedAuthority> authorities) {
        super(dbUser.getSid().toString(), dbUser.getPassword(), authorities);
        email = dbUser.getEmail();
    }

    public AuthUser(Account dbUser, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(dbUser.getSid().toString(), dbUser.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        email = dbUser.getEmail();
    }
}
