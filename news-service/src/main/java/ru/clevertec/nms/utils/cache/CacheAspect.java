package ru.clevertec.nms.utils.cache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Aspect that intercepts calls to the products DAO and implements work with the cache
 */
@Aspect
@Component
public class CacheAspect {

    private final GeneralCache generalCache = new GeneralCache();

    /**
     * Intercepts calls getProductById and look in the cache, if there is no data then get the object
     * from the DAO, save it in the cache and return
     *
     * @param jp exposes the proceed(..) method in order to support around advice in @AJ aspects
     * @param id product ID to search
     * @return product with specified id
     * @throws Throwable if the invoked proceed throws anything
     */
    @Around("@annotation(cacheable) && args(id)")
    public Object getFromCache(ProceedingJoinPoint jp, long id, Cacheable cacheable) throws Throwable {
        String cacheType = cacheable.value()[0];
        System.out.println("get before " + generalCache);
        Object object = generalCache.get(cacheType, id);
        if (object == null) {
            object = jp.proceed();
            generalCache.put(cacheType, id, object);
        }
        System.out.println("get after " + generalCache);
        return object;
    }

    /**
     * Save product in DAO and then save in the cache
     *
     * @param product product to add
     * @param id      generated ID for new product
     */
    @AfterReturning(value = "@annotation(cachePut)", returning = "value")
    public void putInCache(CachePut cachePut, Object value) throws NoSuchFieldException, IllegalAccessException {
        String cacheType = cachePut.value()[0];
        Field fields = value.getClass().getDeclaredField("id");
        fields.setAccessible(true);
        long id = (long) fields.get(value);
        System.out.println("put before " + generalCache);
        generalCache.put(cacheType, id, value);
        System.out.println("put after " + generalCache);
    }

    /**
     * Delete product with specified ID in DAO and in the cache
     *
     * @param id product ID to delete
     */
    @AfterReturning("@annotation(cacheEvict) && args(id,..)")
    public void putInCache(long id, CacheEvict cacheEvict) throws Throwable {
        String cacheType = cacheEvict.value()[0];
        System.out.println(cacheType);
        System.out.println("delete before " + generalCache);
        generalCache.delete(cacheType, id);
        System.out.println("delete after " + generalCache);
    }
}
