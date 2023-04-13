package com.example.web.config.security.provisioning;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.example.web.config.security.userdetails.CustomUserDetails;
import com.example.web.pojo.*;
import com.example.web.service.*;
import common.config.security.authority.DefaultGrantedAuthority;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * An {@link AuthenticationProvider} implementation that retrieves user details from a
 * {@link UserDetailsService}.
 *
 * @author zhangguiyuan
 * @description 类注释
 * @date 2023/3/3 13:33
 */
@Slf4j
@Primary
@Service
@RequiredArgsConstructor
@Component
public class SecurityUserDetailsManager implements UserDetailsManager {

    @Autowired
    private OrganService organService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ElementService elementService;

    @Autowired
    private AccountRoleUnionService accountRoleUnionService;

    @Autowired
    private RoleElementUnionService roleElementUnionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StrUtil.isBlank(username)) {
            throw new UsernameNotFoundException("身份不存在");
        }
        Identity identity = identityService.selectOneByProperty(Identity::getUsername, username);
        if (identity == null) {
            throw new UsernameNotFoundException("身份不存在");
        }
        List<Account> accounts = accountService.selectByProperty(Account::getIdentityId, identity.getId());
        if (CollUtil.isEmpty(accounts)) {
            throw new UsernameNotFoundException("身份账号关联不存在");
        }

        Account currentAccount = accounts.get(0);

        List<AccountRoleUnion> accountRoleUnions = accountRoleUnionService.selectByProperty(AccountRoleUnion::getAccountId, currentAccount.getId());
        List<Long> roleIds = null;
        Set<DefaultGrantedAuthority> authorityPatterns = new HashSet<>();
        if (CollUtil.isNotEmpty(accountRoleUnions)
                && CollUtil.isNotEmpty(roleIds = accountRoleUnions.stream().map(AccountRoleUnion::getRoleId).collect(Collectors.toList()))) {

            List<RoleElementUnion> roleElements = roleElementUnionService.selectInByProperty(RoleElementUnion::getRoleId, roleIds);
            List<Long> elementIds = roleElements.stream().map(RoleElementUnion::getElementId).collect(Collectors.toList());
            if (CollUtil.isNotEmpty(elementIds)) {
                List<Element> elements = elementService.selectByIdList(elementIds);
                List<Long> resourceIds = elements.stream().map(Element::getResourceId).filter(resourceId -> !resourceId.equals(0L)).collect(Collectors.toList());

                if (CollUtil.isNotEmpty(resourceIds)) {
                    List<Resource> resources = resourceService.selectByIdList(resourceIds);
                    authorityPatterns = resources.stream().map(resource -> new DefaultGrantedAuthority(resource.getSign(), resource.getPath())).collect(Collectors.toSet());
                }
            }
        }

        return new CustomUserDetails(currentAccount.getPlatform(), organService.selectByPrimaryKey(identity.getOrganId()), identity, currentAccount, accounts, authorityPatterns);
    }

    @Override
    public void createUser(UserDetails user) {

    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return false;
    }


}
