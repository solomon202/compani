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

package com.compani.test.pagination.unit.argument;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.compani.pagination.argument.PaginationArgumentResolver;

@DisplayName("Pagination argument resolver error handling")
public class TestPaginationArgumentResolverError {

    private final HandlerMethodArgumentResolver resolver = new PaginationArgumentResolver();

    public TestPaginationArgumentResolverError() {
        super();
    }

    @Test
    @DisplayName("Throws an exception when receiving a text for page")
    public void testResolve_StringPage_Exception() throws Exception {
        final MethodParameter       parameter;
        final ModelAndViewContainer mavContainer;
        final NativeWebRequest      webRequest;
        final WebDataBinderFactory  binderFactory;
        final Executable            executable;

        parameter = Mockito.mock(MethodParameter.class);
        mavContainer = Mockito.mock(ModelAndViewContainer.class);
        webRequest = Mockito.mock(NativeWebRequest.class);
        binderFactory = Mockito.mock(WebDataBinderFactory.class);

        Mockito.when(webRequest.getParameter("page"))
            .thenReturn("abc");
        Mockito.when(webRequest.getParameter("size"))
            .thenReturn("10");

        executable = () -> resolver.resolveArgument(parameter, mavContainer, webRequest, binderFactory);

        Assertions.assertThrows(NumberFormatException.class, executable);
    }

    @Test
    @DisplayName("Throws an exception when receiving a text for size")
    public void testResolve_StringSize_Exception() throws Exception {
        final MethodParameter       parameter;
        final ModelAndViewContainer mavContainer;
        final NativeWebRequest      webRequest;
        final WebDataBinderFactory  binderFactory;
        final Executable            executable;

        parameter = Mockito.mock(MethodParameter.class);
        mavContainer = Mockito.mock(ModelAndViewContainer.class);
        webRequest = Mockito.mock(NativeWebRequest.class);
        binderFactory = Mockito.mock(WebDataBinderFactory.class);

        Mockito.when(webRequest.getParameter("page"))
            .thenReturn("1");
        Mockito.when(webRequest.getParameter("size"))
            .thenReturn("abc");

        executable = () -> resolver.resolveArgument(parameter, mavContainer, webRequest, binderFactory);

        Assertions.assertThrows(NumberFormatException.class, executable);
    }

}
