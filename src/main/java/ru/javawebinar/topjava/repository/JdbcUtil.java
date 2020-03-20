package ru.javawebinar.topjava.repository;

import org.hibernate.SessionFactory;

import javax.annotation.Resource;

public class JdbcUtil implements CacheUtil {
    @Resource(name="sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public void clear2ndLevelHibernateCache() {

        sessionFactory.getCache().evictAllRegions();
    }
}
