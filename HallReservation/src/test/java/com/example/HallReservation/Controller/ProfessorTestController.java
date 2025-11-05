package com.example.HallReservation.Controller;

import com.example.HallReservation.Entity.Professor;
import com.example.HallReservation.Service.ProfessorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@WebMvcTest(ProfessorTestController.class)
public class ProfessorTestController extends AbstractTest{

        @Override
        @BeforeEach
        public void setUp() {
            super.setUp();
        }
    @Test
    public void getProductsList() throws Exception {
        String uri = "/products";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Professor[] professorlist = super.mapFromJson(content, Professor[].class);
        assertTrue(professorlist.length > 0);
    }

}
