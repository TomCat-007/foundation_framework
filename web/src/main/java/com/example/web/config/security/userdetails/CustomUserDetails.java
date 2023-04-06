package com.example.web.config.security.userdetails;

import com.example.web.pojo.*;
import common.config.security.authority.DefaultGrantedAuthority;
import common.config.typeHandler.AccountAware;
import common.config.typeHandler.IdentityAware;
import common.config.typeHandler.OrganAware;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * @author zhanghuiyuan
 * @description 类注释
 * @date 2023/3/3 13:33
 */
public class CustomUserDetails implements UserDetails, IdentityAware, OrganAware, AccountAware {

    private Byte platform;

    private Organ organ;

    private Identity identity;

    private Account currentAccount;

    private List<Account> accounts;

    private Set<DefaultGrantedAuthority> currentAuthorities;

    private Boolean enabled = false;

    public CustomUserDetails() {
        this.platform = 1;
        this.organ = new Organ();
        this.identity = new Identity();
        this.currentAccount = new Account();
        this.accounts = new ArrayList<>();
        this.currentAuthorities = new HashSet<>();

    }

    public CustomUserDetails(Byte platform, Organ organ, Identity identity, Account currentAccount, List<Account> accounts, Set<DefaultGrantedAuthority> currentAuthorities) {
        this.platform = platform;
        this.accounts = new ArrayList<>();
        this.currentAuthorities = new HashSet<>();
        this.organ = organ;
        this.identity = identity;
        this.enabled = identity.getEnabled();
        this.currentAccount = currentAccount;
        this.accounts.addAll(accounts);
        this.currentAuthorities.addAll(currentAuthorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return currentAuthorities;
    }

    @Override
    public String getPassword() {
        return identity.getPassword();
    }

    @Override
    public String getUsername() {
        return identity.getIdentityCode();
    }

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
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public Long currentIdentityId() {
        return identity.getId();
    }

    @Override
    public Long currentAccountId() {
        return currentAccount.getId();
    }

    public Identity getIdentity() {
        return identity;
    }

    public Organ getTenant() {
        return organ;
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    @Override
    public Long currentOrganId() {
        return organ.getId();
    }

    public Byte getPlatform() {
        return platform;
    }
}
