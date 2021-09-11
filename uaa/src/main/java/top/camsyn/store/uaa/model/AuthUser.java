package top.camsyn.store.uaa.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


public class AuthUser extends User {

    public AuthUser(top.camsyn.store.commons.entity.User dbUser, Collection<? extends GrantedAuthority> authorities) {
        super(dbUser.getSid().toString(), dbUser.getPassword(), authorities);
    }

    public AuthUser(top.camsyn.store.commons.entity.User dbUser, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(dbUser.getSid().toString(), dbUser.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
}
