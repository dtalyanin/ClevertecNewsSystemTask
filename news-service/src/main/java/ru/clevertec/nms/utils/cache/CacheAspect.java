package ru.clevertec.nms.utils.cache;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.clevertec.nms.exceptions.FieldException;

import java.lang.reflect.Field;

import static ru.clevertec.nms.utils.constants.MessageConstants.CANNOT_GET_FIELD_VALUE;
import static ru.clevertec.nms.utils.constants.MessageConstants.FIELD_NOT_PRESENT;

/**
 * Aspect that intercepts calls to the products DAO and implements work with the cache
 */
@Aspect
@Component
@RequiredArgsConstructor
@Profile("dev")
public class CacheAspect {

    private static final int CACHE_NAME_INDEX = 0;
    private static final String ID_FIELD = "id";

    private final CacheFacade cacheFacade;

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
    public Object getFromCache(ProceedingJoinPoint jp, Cacheable cacheable, long id) throws Throwable {
        String cacheName = cacheable.value()[CACHE_NAME_INDEX];
        Object object = cacheFacade.get(cacheName, id);
        if (object == null) {
            object = jp.proceed();
            cacheFacade.put(cacheName, id, object);
        }
        return object;
    }

    /**
     * Save product in DAO and then save in the cache
     *
     * @param product product to add
     * @param id      generated ID for new product
     */
    @AfterReturning(value = "@annotation(cachePut)", returning = "value")
    public void putInCache(CachePut cachePut, Object value) {
        String cacheName = cachePut.value()[CACHE_NAME_INDEX];
        long id = getIdFromObject(value);
        cacheFacade.put(cacheName, id, value);
    }

    /**
     * Delete product with specified ID in DAO and in the cache
     *
     * @param id product ID to delete
     */
    @AfterReturning("@annotation(cacheEvict) && args(id,..)")
    public void deleteFromCache(CacheEvict cacheEvict, long id) {
        String cacheName = cacheEvict.value()[CACHE_NAME_INDEX];
        cacheFacade.delete(cacheName, id);
    }

    private Long getIdFromObject(Object value) {
        try {
            Field idField = value.getClass().getDeclaredField(ID_FIELD);
            idField.setAccessible(true);
            return (Long) idField.get(value);
        } catch (NoSuchFieldException e) {
            throw new FieldException(ID_FIELD + FIELD_NOT_PRESENT + value.getClass().getSimpleName());
        } catch (IllegalAccessException e) {
            throw new FieldException(ID_FIELD + CANNOT_GET_FIELD_VALUE + value.getClass().getSimpleName());
        }
    }
}
