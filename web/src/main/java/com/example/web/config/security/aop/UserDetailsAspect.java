//package com.example.web.config.security.aop;
//
//import cn.hutool.core.collection.CollUtil;
//import cn.hutool.core.lang.Assert;
//import cn.hutool.core.util.StrUtil;
//import com.example.web.config.security.userdetails.CustomUserDetails;
//import com.example.web.pojo.*;
//import com.example.web.service.*;
//import common.config.security.authority.DefaultGrantedAuthority;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.redisson.api.RBucket;
//import org.redisson.api.RLock;
//import org.redisson.api.RedissonClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.lang.reflect.Method;
//import java.util.*;
//import java.util.concurrent.TimeUnit;
//import java.util.stream.Collectors;
//
///**
// * @author zhanghuiyuan
// * @description 类注释
// * @date 2023/3/3 13:33
// */
//@Aspect
//@Component
//@Slf4j
//public class UserDetailsAspect {
//
//    @Pointcut("@annotation(com.example.web.config.security.aop.UserDetails)")
//    private void userDetailsAspect() {
//    }
//
//    @Resource
//    private RedissonClient redissonClient;
//
//    @Autowired
//    private OrganService organService;
//
//    @Autowired
//    private IdentityService identityService;
//
//    @Autowired
//    private AccountService accountService;
//
//    @Autowired
//    private ResourceService resourceService;
//
//    @Autowired
//    private ElementService elementService;
//
//    @Autowired
//    private AccountRoleUnionService accountRoleUnionService;
//
//    @Autowired
//    private RoleElementUnionService roleElementUnionService;
//
//    private static final String LOCK_RDS_KEY_FAKE_LOGIN = "LOCK:FAKE:LOGIN:{}";
//    private static final String RDS_KEY_FAKE_LOGIN = "FAKE:LOGIN:{}";
//
//    @Around("userDetailsAspect()")
//    public Object userDetails(ProceedingJoinPoint joinPoint) throws Throwable {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && !"anonymousUser".equalsIgnoreCase(authentication.getPrincipal().toString())) {
//            return joinPoint.proceed();
//        }
//
//        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//
//        Method method = methodSignature.getMethod();
//        UserDetails annotation = method.getAnnotation(UserDetails.class);
//
//        Assert.notNull(annotation);
//        Assert.notBlank(annotation.value());
//
//        String value = annotation.value();
//
//        RBucket<CustomUserDetails> bucket = redissonClient.getBucket(StrUtil.format(RDS_KEY_FAKE_LOGIN, value));
//
//        CustomUserDetails userDetail = bucket.isExists() ? bucket.get() : fakeLogin(value);
//
//        Assert.notNull(userDetail, "身份信息不存在, 请添加{}的身份信息或修改身份");
//
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetail, authentication, authentication == null ? Collections.emptyList() : authentication.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//
//        return joinPoint.proceed();
//    }
//
//    public CustomUserDetails fakeLogin(String value) {
//        CustomUserDetails userDetail = null;
//        RLock lock = redissonClient.getLock(StrUtil.format(LOCK_RDS_KEY_FAKE_LOGIN, value));
//        lock.lock(10, TimeUnit.SECONDS);
//
//        RBucket<CustomUserDetails> bucket = redissonClient.getBucket(StrUtil.format(RDS_KEY_FAKE_LOGIN, value));
//
//        if (bucket.isExists()) {
//            userDetail = bucket.get();
//            lock.unlock();
//            return userDetail;
//        }
//
//        if (StrUtil.isBlank(value)) {
//            throw new UsernameNotFoundException("身份不存在");
//        }
//        Identity identity = identityService.selectOneByProperty(Identity::getUsername, value);
//        if (identity == null) {
//            throw new UsernameNotFoundException("身份不存在");
//        }
//        Organ organ = organService.selectByPrimaryKey(identity.getOrganId());
//        List<Account> accounts = accountService.selectByProperty(Account::getIdentityId, identity.getId());
//
//        Account currentAccount = accounts.get(0);
//
//        List<AccountRoleUnion> accountRoleUnions = accountRoleUnionService.selectByProperty(AccountRoleUnion::getAccountId, currentAccount.getId());
//        List<Long> roleIds = null;
//        Set<DefaultGrantedAuthority> authorityPatterns = new HashSet<>();
//        if (CollUtil.isNotEmpty(accountRoleUnions)
//                && CollUtil.isNotEmpty(roleIds = accountRoleUnions.stream().map(AccountRoleUnion::getRoleId).collect(Collectors.toList()))) {
//
//            List<RoleElementUnion> roleElements = roleElementUnionService.selectInByProperty(RoleElementUnion::getRoleId, roleIds);
//            List<Long> elementIds = roleElements.stream().map(RoleElementUnion::getElementId).collect(Collectors.toList());
//            if (CollUtil.isNotEmpty(elementIds)) {
//                List<Element> elements = elementService.selectByIdList(elementIds);
//                List<Long> resourceIds = elements.stream().map(Element::getResourceId).filter(resourceId -> !resourceId.equals(0L)).collect(Collectors.toList());
//
//                if (CollUtil.isNotEmpty(resourceIds)) {
//                    List<com.example.web.pojo.Resource> resources = resourceService.selectByIdList(resourceIds);
//                    authorityPatterns = resources.stream().map(resource -> new DefaultGrantedAuthority(resource.getSign(), resource.getPath())).collect(Collectors.toSet());
//                }
//            }
//        }
//
//        userDetail = new CustomUserDetails(currentAccount.getPlatform(), organ, identity, currentAccount, accounts, authorityPatterns);
//        bucket.set(userDetail, 1L, TimeUnit.HOURS);
//        lock.unlock();
//        return userDetail;
//    }
//
//}
