package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"postgres","jdbc"})
public class PostgresJdbcMealServiceTest extends AbstractMealServiceTest {
}
