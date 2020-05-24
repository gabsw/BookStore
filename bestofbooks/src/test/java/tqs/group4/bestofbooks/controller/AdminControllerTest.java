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
import tqs.group4.bestofbooks.mocks.BookMocks;
import tqs.group4.bestofbooks.mocks.CommissionMocks;
import tqs.group4.bestofbooks.model.Book;
import tqs.group4.bestofbooks.model.Commission;
import tqs.group4.bestofbooks.service.BookService;
import tqs.group4.bestofbooks.service.CommissionService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tqs.group4.bestofbooks.utils.Json.toJson;

@WebMvcTest(AdminController.class)
class AdminControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CommissionService commissionService;

    @AfterEach
    public void after() {
        reset(commissionService);
    }

    @Test
    void getCommissions() {
    }

    @Test
    void givenCommissions_thenGetCommissionsTotal() throws Exception {
        Double allCommissions = CommissionMocks.commission1.getAmount() +  CommissionMocks.commission2.getAmount();
        String url = "/api/admin/commissions/total";
        given(commissionService.getCommissionsTotal()).willReturn(allCommissions);
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status()
                .isOk())
                .andExpect(content().json(toJson(allCommissions)));
        verify(commissionService, VerificationModeFactory.times(1)).getCommissionsTotal();
    }

    @Test
    void givenNoCommissions_thenGetZeroAsTotal() throws Exception {
        Double allCommissions = 0.0;
        String url = "/api/admin/commissions/total";
        given(commissionService.getCommissionsTotal()).willReturn(allCommissions);
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status()
                .isOk())
                .andExpect(content().json(toJson(allCommissions)));
        verify(commissionService, VerificationModeFactory.times(1)).getCommissionsTotal();
    }

    @Test
    void givenCommissions_thenGetAllCommissions() throws Exception {
        Pageable p = PageRequest.of(0, 20);
        Page<Commission> commissionPage = new PageImpl<>(Lists.newArrayList(CommissionMocks.commission1,
                CommissionMocks.commission2), p, 2);

        given(commissionService.getCommissions(p)).willReturn(commissionPage);

        String url = "/api/admin/commissions/";

        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status()
                .isOk())
           .andExpect(content().json(toJson(commissionPage)));
    }
}