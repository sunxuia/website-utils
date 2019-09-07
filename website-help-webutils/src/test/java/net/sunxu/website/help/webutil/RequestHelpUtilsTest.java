package net.sunxu.website.help.webutil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RequestHelpUtilsTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MockableService mockableService;

    @Test
    public void testGetIPAddress() throws Exception {
        Mockito.when(mockableService.execute())
                .thenAnswer(invocation -> RequestHelpUtils.getIpAddress(RequestHelpUtils.getRequest()));
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string("127.0.0.1"));
    }

}
