package ru.javawebinar.topjava.repository;

import org.hibernate.SessionFactory;

import javax.annotation.Resource;

public class JdbcUtil implements CacheUtil {

    @Override
    public void clear2ndLevelHibernateCache() { }

}
