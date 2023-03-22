package com.kakao;

import com.kakao.domain.search.repository.KeywordRepository;
import com.kakao.domain.search.service.SearchService;
import com.kakao.exception.ErrorCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SearchApplicationTests extends BaseTest {

    private static final String GET_BLOG_URL = "/v1/search/blog";
    private static final String GET_KEYWORD_URL = "/v1/search/keyword";

    @Autowired
    private SearchService searchService;

    @Autowired
    private KeywordRepository keywordRepository;

    @Test
    void 블로그검색api_검색어는_필수값() throws Exception {
        mockMvc
            .perform(
                get(GET_BLOG_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(content().encoding("UTF-8"))
            .andExpect(status().is(400))
            .andExpect(jsonPath("$.errorMessage").value(ErrorCode.INVALID_ARGUMENT.name()));
    }

    @Test
    void 블로그검색api_검색어의_최대_길이는_100글자_이하() throws Exception {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("query", "야".repeat(101));

        mockMvc
            .perform(
                get(GET_BLOG_URL)
                    .params(paramMap)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(content().encoding("UTF-8"))
            .andExpect(status().is(400))
            .andExpect(jsonPath("$.errorMessage").value(ErrorCode.INVALID_ARGUMENT.name()));
    }

    @Test
    void 블로그검색api_정렬기준의_기본값은_정확도() throws Exception {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("query", "야호");

        mockMvc
            .perform(
                get(GET_BLOG_URL)
                    .params(paramMap)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(content().encoding("UTF-8"))
            .andExpect(status().is(200))
            .andExpect(jsonPath("$.sort").value("accuracy"));
    }

    @Test
    void 블로그검색api_정렬기준의_기본값은_정확도_2() throws Exception {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("query", "야호");
        paramMap.add("sort", "dasd");

        mockMvc
            .perform(
                get(GET_BLOG_URL)
                    .params(paramMap)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(content().encoding("UTF-8"))
            .andExpect(status().is(200))
            .andExpect(jsonPath("$.sort").value("accuracy"));
    }

    @Test
    void 블로그검색api_결과페이지번호의_기본값은_1() throws Exception {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("query", "야호");

        mockMvc
            .perform(
                get(GET_BLOG_URL)
                    .params(paramMap)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(content().encoding("UTF-8"))
            .andExpect(status().is(200))
            .andExpect(jsonPath("$.page").value(1));
    }

    @Test
    void 블로그검색api_결과페이지번호의_최대값은_50() throws Exception {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("query", "야호");
        paramMap.add("page", "52");

        mockMvc
            .perform(
                get(GET_BLOG_URL)
                    .params(paramMap)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(content().encoding("UTF-8"))
            .andExpect(status().is(400))
            .andExpect(jsonPath("$.errorMessage").value(ErrorCode.INVALID_ARGUMENT.name()));
    }

    @Test
    void 블로그검색api_한페이지에_보여질_숫자의_기본값은_10() throws Exception {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("query", "야호");

        mockMvc
            .perform(
                get(GET_BLOG_URL)
                    .params(paramMap)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(content().encoding("UTF-8"))
            .andExpect(status().is(200))
            .andExpect(jsonPath("$.size").value(10));
    }

    @Test
    void 블로그검색api_한페이지에_보여질_숫자의_최대값은_50() throws Exception {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("query", "야호");
        paramMap.add("size", "51");

        mockMvc
            .perform(
                get(GET_BLOG_URL)
                    .params(paramMap)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(content().encoding("UTF-8"))
            .andExpect(status().is(400))
            .andExpect(jsonPath("$.errorMessage").value(ErrorCode.INVALID_ARGUMENT.name()));
    }

    @Test
    void 키워드저장_동시에_1000개_저장() throws Exception {

        int threadCount = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.execute(() -> {
                searchService.saveKeyword("새로운키워드");
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();
        Long hits = keywordRepository.findByKeyword("새로운키워드").orElseThrow().getHits();
        assertThat(hits).isEqualTo(1000L);
    }

    @Test
    void 키워드검색api_최대10개_조회() throws Exception {
        mockMvc
            .perform(
                get(GET_KEYWORD_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(content().encoding("UTF-8"))
            .andExpect(status().is(200))
            .andExpect(jsonPath("$.keywords.length()").value(10));

    }
}
