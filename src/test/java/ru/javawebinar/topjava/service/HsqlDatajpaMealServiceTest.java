package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"hsqldb","datajpa"})
public class HsqlDatajpaMealServiceTest extends AbstractMealServiceTest {
}
