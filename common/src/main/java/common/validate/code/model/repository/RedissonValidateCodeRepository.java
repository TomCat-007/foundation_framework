package common.validate.code.model.repository;

import cn.hutool.core.util.StrUtil;
import common.validate.code.ValidateCodeRepository;
import common.validate.code.exception.ValidateCodeException;
import common.validate.code.model.ValidateCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * @author zhangguiyuan
 * @description 校验码缓存仓库，提供缓存能力
 * @date 2023/4/13 10:59
 */
@Slf4j
@RequiredArgsConstructor
public class RedissonValidateCodeRepository implements ValidateCodeRepository {

    private String keyPrefix = "VALIDATE:CODE";

    private final RedissonClient redissonClient;

    @Override
    public void save(ValidateCode validateCode, int expireIn) {
        RBucket<ValidateCode> bucket = getBucket(validateCode.getUid());
        bucket.set(validateCode, expireIn, TimeUnit.SECONDS);
    }

    @Override
    public ValidateCode get(String uid) {
        return getBucket(uid).get();
    }

    @Override
    public ValidateCode getAndRemove(String uid) {
        return getBucket(uid).getAndDelete();
    }

    @Override
    public void remove(String uid) {
        getBucket(uid).delete();
    }

    private RBucket<ValidateCode> getBucket(String uid) {
        if (StrUtil.isBlank(uid)) {
            throw new ValidateCodeException("验证码的id不能为空");
        }
        String key = getKeyPrefix() + ":" + uid;
        return redissonClient.getBucket(key);
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }
}
