package main.VeterinaryClinic.Model.Account;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

public class AccountUserDetail implements OAuth2User, UserDetails {
    private OAuth2User oAuth2User;
    private Account account;
    public AccountUserDetail(OAuth2User oAuth2User, Account account) {
        this.oAuth2User = oAuth2User;
        this.account = account;
    }

    public OAuth2User getoAuth2User() {return oAuth2User;}
    public Account getAccount() {return account;}

    public String getLineId() {return oAuth2User.getAttribute("userId");}
    public String getPictureUrl() {return oAuth2User.getAttribute("pictureUrl");}

    // OAuth2User, UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = account.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
//            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    // OAuth2User Override
    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public String getName() {return oAuth2User.getAttribute("displayName");}

    // UserDetails Override
    @Override
    public String getPassword() { return null;}
    @Override
    public String getUsername() { return getLineId();}
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}
