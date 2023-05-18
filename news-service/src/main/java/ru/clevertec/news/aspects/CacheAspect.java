package ru.clevertec.news.aspects;

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
import ru.clevertec.exceptions.exceptions.FieldException;
import ru.clevertec.exceptions.models.ErrorCode;
import ru.clevertec.news.utils.cache.CacheFacade;

import java.lang.reflect.Field;

import static ru.clevertec.news.utils.constants.MessageConstants.CANNOT_GET_FIELD_VALUE;
import static ru.clevertec.news.utils.constants.MessageConstants.FIELD_NOT_PRESENT;

/**
 * Aspect that intercepts calls to the services and implements work with the cache
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
     * Intercepts get calls and look in cache, if there is no data then get object
     * from service, save it in cache and return
     *
     * @param jp Exposes the proceed(..) method in order to support around advice in @AJ aspects
     * @param cacheable annotation that trigger operation with cache
     * @param id ID to search
     * @return Item with specified id
     * @throws Throwable  if the invoked proceed throws anything
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
     * Save or update item and then save it in cache
     * @param cachePut annotation that trigger operation with cache
     * @param value returned value after operation executing
     */
    @AfterReturning(value = "@annotation(cachePut)", returning = "value")
    public void putInCache(CachePut cachePut, Object value) {
        String cacheName = cachePut.value()[CACHE_NAME_INDEX];
        long id = getIdFromObject(value);
        cacheFacade.put(cacheName, id, value);
    }

    /**
     * Delete item with specified ID in service and in cache
     * @param cacheEvict annotation that trigger operation with cache
     * @param id item ID to delete
     */
    @AfterReturning("@annotation(cacheEvict) && args(id,..)")
    public void deleteFromCache(CacheEvict cacheEvict, long id) {
        String cacheName = cacheEvict.value()[CACHE_NAME_INDEX];
        cacheFacade.delete(cacheName, id);
    }

    /**
     * Get ID from object with help of reflection
     * @param value object for getting ID
     * @return value ID
     */
    private Long getIdFromObject(Object value) {
        try {
            Field idField = value.getClass().getDeclaredField(ID_FIELD);
            idField.setAccessible(true);
            return (Long) idField.get(value);
        } catch (NoSuchFieldException e) {
            String message = ID_FIELD + FIELD_NOT_PRESENT + value.getClass().getSimpleName();
            throw new FieldException(message, ErrorCode.FIELD_REFLECT_EXCEPTION);
        } catch (IllegalAccessException e) {
            String message = ID_FIELD + CANNOT_GET_FIELD_VALUE + value.getClass().getSimpleName();
            throw new FieldException(message, ErrorCode.FIELD_REFLECT_EXCEPTION);
        }
    }
}
