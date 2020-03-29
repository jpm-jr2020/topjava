package ru.javawebinar.topjava.web;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

class RootControllerTest extends AbstractControllerTest {

    @Test
    void getUsers() throws Exception {
        mockMvc.perform(get("/users")) // ResultActions.andDo(ResultHandler) - ResultActions
                                                  // ResultActions.andExpect(ResultMatcher) - ResultActions
                                                  // ResultActions.andReturn() - MvcResult
                // ResultHandler.handle(MvcResult)
                // ResultMatcher.match(MvcResult)
                // ResultMatcher.matchAll(ResultMatcher... matchers) - ResultMatcher

                // MvcResult
                // get*()

                // MockMvcResultMatchers
                // view(), model(), forwardedUrl(), ...

                // MockMvcResultHandlers
                // log(), print*()


                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"))
                .andExpect(model().attribute("users", hasSize(2)))
                .andExpect(model().attribute("users", hasItem(
                        allOf(
                                hasProperty("id", is(START_SEQ)),
                                hasProperty("name", is(USER.getName()))
                        )
                )));
    }
}