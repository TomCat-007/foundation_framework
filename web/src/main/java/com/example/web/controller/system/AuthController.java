package com.example.web.controller.system;

import common.api.request.DefaultLoginRequest;
import common.api.response.DefaultLoginResponse;
import common.api.response.ImageCodeResponse;
import common.config.api.base.BaseResponse;
import common.config.api.base.Rest;
import common.constant.SecurityConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangguiyuan
 * @description
 * @date 2023/3/3 13:33
 */
@Slf4j
@RequestMapping
@RestController
@RequiredArgsConstructor
@Api(value = "认证相关", tags = {"认证相关"}, consumes = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    private final RedissonClient redissonClient;

    @ApiOperation(value = "表单登录", produces = "POST")
    @RequestMapping(value = SecurityConstants.LOGIN_PROCESSING_URL, method = RequestMethod.POST)
    public Rest<DefaultLoginResponse> fakeLogin(@RequestBody DefaultLoginRequest request) {
        throw new IllegalArgumentException("Add Spring Security to handle authentication");
    }

    @GetMapping(SecurityConstants.AUTH_LOGOUT)
    @ApiOperation(value = "注销", notes = "注销", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> fakeLogout() {
        throw new IllegalArgumentException("Add Spring Security to handle authentication");
    }

    @GetMapping(SecurityConstants.URL_IMAGE_CAPTCHA)
    @ApiOperation(value = "图片验证码", notes = "图片验证码", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<ImageCodeResponse> imageCaptcha() {
        throw new IllegalArgumentException("Add Spring Security to handle image captcha");
    }

//
//    @GetMapping("/account/menu/tree")
//    @ApiOperation(value = "登录菜单树", notes = "登录菜单树", produces = MediaType.APPLICATION_JSON_VALUE)
//    public Rest<MenuTreeResponse> menuTree() {
//        List<Element> elements = elementService.buildElementTreeByAccountId(ContextHolder.currentAccountId());
//        List<ElementVo> parents = elements.stream()
//                .map(elementConverter::convert2ElementVo)
//                .collect(Collectors.toList());
//        MenuTreeResponse response = new MenuTreeResponse()
//                .setElements(parents);
//        return Rest.success(response);
//    }
//
//    @GetMapping("/account/info")
//    @ApiOperation(value = "账户信息", notes = "账户信息", produces = MediaType.APPLICATION_JSON_VALUE)
//    public Rest<AccountInfoResponse> info() {
//        Long accountId = ContextHolder.currentAccountId();
//        Long organId = ContextHolder.currentOrganId();
//
//        AccountInfoResponse response = new AccountInfoResponse();
//        response.setAccountId(accountId);
//        response.setOrganId(organId);
//        response.setName(accountService.selectByPrimaryKey(accountId).getName());
//        response.setOrganName(organService.selectByPrimaryKey(organId).getName());
//
//        return Rest.success(response);
//    }
//
//    @PostMapping("/account/pwd/modify")
//    @ApiOperation(value = "修改密码", notes = "修改密码", produces = MediaType.APPLICATION_JSON_VALUE)
//    public Rest<BaseResponse> resetPassword(@Validated @RequestBody PasswordModifyRequest request) {
//        if (!request.getReenterPassword().equals(request.getPassword())) {
//            throw ServiceExceptionUtil.fail(SysManageErrorCodeEnum.REENTER_PASSWORD_NOT_MATCH);
//        }
//
//        identityService.modifyPassword(ContextHolder.currentIdentityId(), request.getOriginalPassword(), request.getPassword());
//        return Rest.success();
//    }


}
