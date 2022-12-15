package main.VeterinaryClinic.Service.Account;

import main.VeterinaryClinic.Model.Account.AccountUserDetail;
import main.VeterinaryClinic.Model.Account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class AccountUserDetailService extends DefaultOAuth2UserService {
    @Autowired
    private AccountService accountService;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        return new AccountUserDetail(oAuth2User, loadUserByUsername(oAuth2User));
    }

    public Account loadUserByUsername(OAuth2User oAuth2User) throws UsernameNotFoundException {
        String lineName = oAuth2User.getAttribute("displayName");
        String lineId = oAuth2User.getAttribute("userId");
        String linePic = oAuth2User.getAttribute("pictureUrl");
        Account account = accountService.getByLineId(lineId);
        if (account != null) {
            if (account.getLastName() == null|| account.getLastName().isEmpty() || account.getLastName().isBlank()){
                account.setFirstName(lineName);
            }
            account.setLineId(lineId);
            account.setImg_path(linePic);
            accountService.save(account);

            System.out.println("User Already");
            System.out.println(account);
        } else {
            System.out.println("User NOT Already");
            account = accountService.create(lineName, lineId, linePic);
        }
        return account;
    }
}
