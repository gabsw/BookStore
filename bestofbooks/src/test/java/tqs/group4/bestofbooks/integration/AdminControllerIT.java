package tqs.group4.bestofbooks.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqs.group4.bestofbooks.BestofbooksApplication;
import tqs.group4.bestofbooks.mocks.CommissionMocks;
import tqs.group4.bestofbooks.model.Commission;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tqs.group4.bestofbooks.utils.Json.toJson;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = BestofbooksApplication.class)
@AutoConfigureMockMvc
@Transactional
public class AdminControllerIT {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private EntityManager entityManager;


    @AfterEach
    public void after() {
        entityManager.remove(CommissionMocks.commission1);
        entityManager.remove(CommissionMocks.commission2);
        entityManager.flush();
    }

    @Test
    void givenCommissions_thenGetCommissionsTotal() throws Exception {
        entityManager.persist(CommissionMocks.commission1);
        entityManager.persist(CommissionMocks.commission2);

        entityManager.flush();
        Double allCommissions = CommissionMocks.commission1.getAmount() +  CommissionMocks.commission2.getAmount();
        String url = "/api/admin/commissions/total";

        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status()
                .isOk())
                .andExpect(content().json(toJson(allCommissions)));

    }

    @Test
    void givenNoCommissions_thenGetZeroAsTotal() throws Exception {
        entityManager.persist(CommissionMocks.commission1);
        entityManager.persist(CommissionMocks.commission2);

        entityManager.flush();
        Double allCommissions = 0.0;
        String url = "/api/admin/commissions/total";
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status()
                .isOk())
                .andExpect(content().json(toJson(allCommissions)));
    }
}
