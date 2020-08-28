//package top.qiuk.util.redis;
//
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.log4j.spi.LoggerFactory;
//import top.qiuk.constant.ExceptionTypeEnum;
//import top.qiuk.exception.UtilRuntimeException;
//
//import java.util.UUID;
//import java.util.concurrent.TimeUnit;
//
//
///**
// * @author qiukai
// * 操作redis 的组件
// * 所有操作redis的地方，请在该类下进行修改，不要到处乱写，以便管理
// */
//public class RedisUtils {
//
//    private RedisTemplate redisTemplate;
//
//    private String LOCK = "lock:";
//
//    /**
//     * 保存redis 的值
//     *
//     * @param key     redis key
//     * @param value   保存的对象
//     * @param seconds 保存时间，小于等于0 将永久保存
//     */
//    public void set(String key, Object value, int seconds) {
//        redisTemplate.opsForValue().set(key, JSONUtils.json(value), seconds, TimeUnit.SECONDS);
//    }
//
//    /**
//     * 保存redis 的值
//     *
//     * @param key     redis key
//     * @param value   保存的对象
//     * @param seconds 保存时间，小于等于0 将永久保存
//     */
//    public void setSpecial(String key, String value, int seconds) {
//        redisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
//    }
//
//
//    /**
//     * 获取redis 的值
//     *
//     * @param key   redis key
//     * @param clazz 反序列化需要的对象的Class
//     * @param <T>   T结果返回的类型
//     * @return T类型的value
//     */
//    public <T> T get(String key, Class<T> clazz) {
//        String result = (String) redisTemplate.opsForValue().get(key);
//        T obj = null;
//        if (StringUtils.isNotBlank(result)) {
//            obj = JSONUtils.deserialize(result, clazz);
//        }
//        return obj;
//    }
//
//    /**
//     * 获取redis 的值
//     *
//     * @param key redis key
//     * @return T类型的value
//     */
//    public String get(String key) {
//        Object o = redisTemplate.opsForValue().get(key);
//        if (o == null) {
//            return null;
//        }
//        return (String) o;
//    }
//
//    /**
//     * 删除 redis 的值
//     *
//     * @param keys 删除的key
//     */
//    public boolean del(String keys) {
//        return redisTemplate.delete(keys);
//    }
//
//
//    /**
//     * redis 自旋 阻塞锁
//     *
//     * @param lockName           锁的key
//     * @param lockTimeOutSeconds 锁的超时时间
//     * @param retryMillisecond   重新获取锁，重试时间
//     * @param retryMaxTimeSecond 阻塞最大时间
//     * @return 不是null 说明锁定成功，是null 说明锁定失败
//     */
//    @LogTime
//    public String lockSynchronized(String lockName, int lockTimeOutSeconds, int retryMillisecond, int retryMaxTimeSecond) {
//
//        retryMaxTimeSecond = retryMaxTimeSecond * 1000;
//        long l = System.currentTimeMillis();
//        while (true) {
//
//            String lock = this.lock(lockName, lockTimeOutSeconds);
////            log.info("检查锁{}，结果{}",lockName,lock);
//            if (StringUtils.isNotBlank(lock)) {
//                return lock;
//            }
//            long l1 = System.currentTimeMillis();
//            if (l1 - l > (retryMaxTimeSecond)) {
//                return null;
//            }
//
//            try {
//                Thread.sleep(retryMillisecond);
//            } catch (InterruptedException e) {
//                log.error("系统睡眠时间异常：", e);
//            }
//        }
//    }
//
//    /**
//     * 加锁
//     *
//     * @param lockName 锁的key
//     * @param seconds  锁的超时时间  s 秒
//     * @return 锁标识
//     */
//    @Deprecated
//    public String lock(String lockName, int seconds) {
//        return _lock(lockName, seconds);
//    }
//
//    /**
//     *
//     * redis 自由锁，会返回空，可以自由控制什么时候解锁或者获取锁失败后如何处理
//     *
//     * 如果是一般常见，获取锁失败就报错建议
//     *
//     * @param seconds       锁的超时时间  s 秒
//     * @param redisLockType 锁类型，防止多地方使用，锁标志重复
//     * @param param         锁参数
//     * @return 锁标识
//     */
//    public String lock(int seconds, RedisLockType redisLockType, String... param) {
//        return _lock(getLockName(redisLockType, param), seconds);
//    }
//
//    /**
//     *
//     * redis 一般锁，获取不到锁就会报错，建议使用
//     *
//     * @param lockExe       锁执行的逻辑
//     * @param seconds       锁的超时时间  s 秒
//     * @param redisLockType 锁类型，防止多地方使用，锁标志重复
//     * @param param         锁参数
//     */
//    public void lock(LockExe lockExe, int seconds, RedisLockType redisLockType, String... param) {
//        String lock = this.lock(seconds, redisLockType, param);
//
//        if (org.apache.commons.lang3.StringUtils.isBlank(lock)) {
//            throw new UtilRuntimeException(ExceptionTypeEnum.REDIS);
//        }
//
//        try {
//            lockExe.exe();
//        } finally {
//            this.unlock(lock, redisLockType, param);
//        }
//    }
//
//
//    private String _lock(String lockName, int seconds) {
//        if (StringUtils.isBlank(lockName)) {
//            return null;
//        }
//        // 随机生成一个value
//        String identifier = UUID.randomUUID().toString();
//        // 锁名，即key值
//        String lockKey = this.LOCK + lockName;
//        // 超时时间，上锁后超过此时间则自动释放锁
//        // 获取锁的超时时间，超过这个时间则放弃获取锁
//        boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(lockKey, identifier, seconds, TimeUnit.SECONDS);
//        if (aBoolean) {
//            return identifier;
//        }
//        return null;
//    }
//
//    private String getLockName(RedisLockType redisLockType, String... param) {
//        StringBuilder lockName = new StringBuilder(redisLockType.name());
//        for (String s : param) {
//            lockName.append(s);
//        }
//        return lockName.toString();
//    }
//
//
//    /**
//     * 释放锁
//     *
//     * @param lockName   锁的key
//     * @param identifier 释放锁的标识
//     */
//    public boolean unlock(String lockName, String identifier) {
//        return _unlock(lockName, identifier);
//    }
//
//    public boolean _unlock(String lockName, String identifier) {
//        if (StringUtils.isBlank(lockName) || StringUtils.isBlank(identifier)) {
//            return false;
//        }
//        String lockKey = this.LOCK + lockName;
//        String s = this.get(lockKey);
//        if (identifier.equals(s)) {
//            this.del(lockKey);
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * @param identifier    释放锁的标识
//     * @param redisLockType 锁类型，防止多地方使用，锁标志重复
//     * @param param         锁参数
//     */
//    public boolean unlock(String identifier, RedisLockType redisLockType, String... param) {
//        return _unlock(getLockName(redisLockType, param), identifier);
//    }
//
//    /**
//     * 设置超时时间，单位是秒
//     */
//    private void timeout(RedisTemplate jedis, String key, int seconds) {
//        if (seconds <= 0) {
//            return;
//        }
//        boolean result = jedis.expire(key, seconds, TimeUnit.SECONDS);
//        if (result) {
//            return;
//        }
//        throw new RuntimeException("redis 设置锁时间失败，key：" + key + "设置的时间是：" + seconds);
//    }
//
//    public static void main(String[] args) {
//        new RedisUtils().lockSynchronized("asv", 50, 500, 50);
//    }
//}
