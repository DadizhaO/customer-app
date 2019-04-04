package springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import springboot.model.Customer;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:test.properties")
public class CustomerControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private DataSource dataSource;

    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setup() {
        Resource initSchema = new ClassPathResource("script\\schema.sql");
        Resource data = new ClassPathResource("script\\data.sql");
        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema, data);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void testGetAllCustomers() throws Exception {
        String mvcResult = mockMvc.perform(get("/customer")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();

        List customers = mapper.readValue(mvcResult, mapper.getTypeFactory().constructCollectionType(List.class, Customer.class));
        assertEquals(2, customers.size());
    }


    @Test
    public void testGetCustomerByIdExist() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/customer/{id}", "1111")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        Customer customer = mapper.readValue(mvcResult.getResponse().getContentAsString(), Customer.class);
        assertNotNull(customer);
    }

    @Test
    public void testGetCustomerByIdNotExist() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/customer/{id}", "-11")).andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(0, mvcResult.getResponse().getContentLength());
    }

    @Test
    public void testAddCustomer() throws Exception {
        String json = mapper.writeValueAsString(new Customer(BigDecimal.valueOf(3333)));
        mockMvc.perform(post("/customer").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        mockMvc.perform(delete("/customer/{id}", "1111")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        mockMvc.perform(put("/customer/{id}", "1111").param("creditLimit", "777"))
                .andExpect(status().isOk())
                .andReturn();
    }

}
