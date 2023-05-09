package ru.clevertec.nms.utils.cache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Aspect that intercepts calls to the products DAO and implements work with the cache
 */
@Aspect
@Component
public class CacheAspect {

//    private final Cache<Product> cache;
//
//    public CacheAspect(@Autowired CacheFactory factory) {
//        cache = factory.getCacheImplementation();
//    }
//
//    /**
//     * Intercepts calls getProductById and look in the cache, if there is no data then get the object
//     * from the DAO, save it in the cache and return
//     *
//     * @param jp exposes the proceed(..) method in order to support around advice in @AJ aspects
//     * @param id product ID to search
//     * @return product with specified id
//     * @throws Throwable if the invoked proceed throws anything
//     */
//    @Around(value = "execution(* ru.clevertec.task.dao.impl.ProductDAOImpl.getProductById(..)) " +
//            "&& args(id)", argNames = "jp, id")
//    public Product getProduct(ProceedingJoinPoint jp, int id) throws Throwable {
//        Product product = cache.get(id);
//        if (product == null) {
//            product = (Product) jp.proceed(new Object[]{id});
//            cache.put(product.getId(), product);
//        }
//        return product;
//    }
//
//    /**
//     * Save product in DAO and then save in the cache
//     *
//     * @param product product to add
//     * @param id      generated ID for new product
//     */
//    @AfterReturning(pointcut = "execution(* ru.clevertec.task.dao.impl.ProductDAOImpl.addNewProduct(..)) " +
//            "&& args(product)", returning = "id", argNames = "product, id")
//    public void addProduct(Product product, int id) {
//        product.setId(id);
//        cache.put(id, product);
//    }
//
//    /**
//     * Update product fields with specified ID and then update product in the cache
//     *
//     * @param id      product ID to update
//     * @param product product with new values for update
//     */
//    @AfterReturning(pointcut = "execution(* ru.clevertec.task.dao.impl.ProductDAOImpl.updateProduct(..)) " +
//            "&& args(id, product)", argNames = "id, product")
//    public void updateProduct(int id, Product product) {
//        product.setId(id);
//        cache.put(id, product);
//    }
//
//    /**
//     * Delete product with specified ID in DAO and in the cache
//     *
//     * @param id product ID to delete
//     */
//    @AfterReturning(pointcut = "execution(* ru.clevertec.task.dao.impl.ProductDAOImpl.deleteProductById(..)) " +
//            "&& args(id)", argNames = "id")
//    public void deleteProduct(int id) {
//        cache.delete(id);
//    }
}
