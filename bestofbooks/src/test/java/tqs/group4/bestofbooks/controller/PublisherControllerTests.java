package tqs.group4.bestofbooks.controller;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqs.group4.bestofbooks.dto.RevenueDTO;
import tqs.group4.bestofbooks.exception.UserNotFoundException;
import tqs.group4.bestofbooks.mocks.BookMocks;
import tqs.group4.bestofbooks.mocks.RevenueMocks;
import tqs.group4.bestofbooks.model.Book;
import tqs.group4.bestofbooks.service.RevenueService;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tqs.group4.bestofbooks.utils.Json.toJson;

@WebMvcTest(PublisherController.class)
public class PublisherControllerTests {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private RevenueService revenueService;

    Pageable p = PageRequest.of(0, 20);

    @AfterEach
    public void after() {
        reset(revenueService);
    }

    @Test
    void givenExistentPublisherName_whenGetRevenuesByPublisherName_thenReturnJson() throws Exception {
        String knownPublisher = RevenueMocks.revenue1.getPublisherName();
        String url = "/api/publisher/" + knownPublisher + "/revenue";

        Page<RevenueDTO> revenueDTOPage = new PageImpl<>(Lists.newArrayList(RevenueDTO.fromRevenue(RevenueMocks.revenue1)), p, 1);
        given(revenueService.getRevenuesByPublisher(knownPublisher, p)).willReturn(revenueDTOPage);

        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status()
                .isOk())
           .andExpect(content().json(toJson(revenueDTOPage)));
        verify(revenueService, VerificationModeFactory.times(1)).getRevenuesByPublisher(knownPublisher, p);
    }

    @Test
    void givenUnknownPublisherName_thenThrowHTTPStatusNotFound_forRevenues() throws Exception {
        String unknownPublisher = "none";
        String url = "/api/publisher/" + unknownPublisher + "/revenue";

        given(revenueService.getRevenuesByPublisher(unknownPublisher, p)).willThrow(new UserNotFoundException());

        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    void givenExistentPublisherName_whenGetTotalRevenuesByPublisherName_thenReturnJson() throws Exception {
        String knownPublisher = RevenueMocks.revenue1.getPublisherName();
        String url = "/api/publisher/" + knownPublisher + "/revenue/total";

        given(revenueService.getRevenuesTotalByPublisher(knownPublisher)).willReturn(500.00);

        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status()
                .isOk())
           .andExpect(content().json(toJson(500.00)));
        verify(revenueService, VerificationModeFactory.times(1)).getRevenuesTotalByPublisher(knownPublisher);
    }

    @Test
    void givenUnknownPublisherName_thenThrowHTTPStatusNotFound_forTotalInRevenues() throws Exception {
        String unknownPublisher = "none";
        String url = "/api/publisher/" + unknownPublisher + "/revenue/total";

        given(revenueService.getRevenuesTotalByPublisher(unknownPublisher)).willThrow(new UserNotFoundException());

        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }
}
