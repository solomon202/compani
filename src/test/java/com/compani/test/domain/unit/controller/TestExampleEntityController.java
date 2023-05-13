/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or authors.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.compani.test.domain.unit.controller;

import java.util.ArrayList;
import java.util.Collection;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.compani.domain.controller.ExampleEntityController;
import com.compani.domain.model.DefaultExampleEntity;
import com.compani.domain.model.ExampleEntity;
import com.compani.domain.service.ExampleEntityService;
import com.compani.pagination.argument.PaginationArgumentResolver;
import com.compani.pagination.argument.SortArgumentResolver;
import com.compani.pagination.model.DefaultPageIterable;
import com.compani.pagination.model.PageIterable;
import com.compani.response.controller.ResponseAdvice;
import com.compani.test.config.UrlConfig;

@DisplayName("Example controller")
public final class TestExampleEntityController {

    private MockMvc mockMvc;

    public TestExampleEntityController() {
        super();
    }

    @BeforeEach
    public final void setUpMockContext() {
        mockMvc = MockMvcBuilders.standaloneSetup(getController())
            .setControllerAdvice(ResponseAdvice.class)
            .setCustomArgumentResolvers(new PaginationArgumentResolver(), new SortArgumentResolver())
            .alwaysExpect(MockMvcResultMatchers.status()
                .isOk())
            .alwaysExpect(MockMvcResultMatchers.content()
                .contentType(MediaType.APPLICATION_JSON))
            .build();
    }

    @Test
    @DisplayName("Returns all the entities")
    public final void testGet_ExpectedResults() throws Exception {
        final ResultActions result; // Request result

        result = mockMvc.perform(getGetRequest());

        // The operation was accepted
        result.andExpect(MockMvcResultMatchers.status()
            .isOk());

        // The response model contains the expected attributes
        result.andExpect(MockMvcResultMatchers.jsonPath(".content", Matchers.hasSize(3)));
    }

    /**
     * Returns a controller with mocked dependencies.
     *
     * @return a controller with mocked dependencies
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private final ExampleEntityController getController() {
        final ExampleEntityService               service;
        final Collection<ExampleEntity>          entities;
        final DefaultPageIterable<ExampleEntity> pageIterable;

        service = Mockito.mock(ExampleEntityService.class);

        entities = new ArrayList<>();
        entities.add(new DefaultExampleEntity());
        entities.add(new DefaultExampleEntity());
        entities.add(new DefaultExampleEntity());

        pageIterable = new DefaultPageIterable();
        pageIterable.setContent(entities);

        Mockito.when(service.getAll(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn((PageIterable) pageIterable);

        return new ExampleEntityController(service);
    }

    /**
     * Returns a request builder prepared for reading entities.
     *
     * @return a request builder prepared for reading entities
     */
    private final RequestBuilder getGetRequest() {
        return MockMvcRequestBuilders.get(UrlConfig.ENTITY)
            .contentType(MediaType.APPLICATION_JSON);
    }

}
