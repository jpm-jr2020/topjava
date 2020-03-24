package ru.javawebinar.topjava.repository;

import org.hibernate.SessionFactory;

import javax.annotation.Resource;
import javax.validation.*;
import java.util.Set;

public class JdbcUtil implements CacheUtil {
    private static Validator validator;

    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Resource(name="sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public void clear2ndLevelHibernateCache() {

        sessionFactory.getCache().evictAllRegions();
    }

    public static <T> void validate(T entity) {
        Set<ConstraintViolation<T>> violations = validator.validate(entity);
        if (!violations.isEmpty()) throw new ConstraintViolationException(violations);
    }
}
