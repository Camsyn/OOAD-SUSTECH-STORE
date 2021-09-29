package top.camsyn.store.auth.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import top.camsyn.store.commons.entity.Account;

import java.util.Collection;


public class AuthUser extends User {

    public AuthUser(Account dbUser, Collection<? extends GrantedAuthority> authorities) {
        super(dbUser.getSid().toString(), dbUser.getPassword(), authorities);
    }

    public AuthUser(Account dbUser, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(dbUser.getSid().toString(), dbUser.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
}
