package com.hobbygo.api.hobbygoapi.restapi;

import com.hobbygo.api.hobbygoapi.model.entity.*;
import com.hobbygo.api.hobbygoapi.restapi.advice.ValidatingUserRepositoryDecorator;
import com.hobbygo.api.hobbygoapi.restapi.controller.UserRestController;
import com.hobbygo.api.hobbygoapi.restapi.resource.FactoryResource;
import com.hobbygo.api.hobbygoapi.service.PlayerService;
import com.hobbygo.api.hobbygoapi.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.util.Locale;

import static com.jayway.jsonassert.impl.matcher.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserRestController.class)
@WebAppConfiguration
//@AutoConfigureRestDocs(outputDir = "build/snippets")
public class UserRestControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private FactoryResource factoryResource;

    @MockBean
    private PlayerService playerService;

    @MockBean
    private ValidatingUserRepositoryDecorator validatingUserRepositoryDecorator;

    private String userName = "francesc3000";

    private Principal mockPrincipal;

    private User user;

    @Before
    public void setUp() {

        initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();

        mockPrincipal = Mockito.mock(Principal.class);
        when(mockPrincipal.getName()).thenReturn(userName);

        String[] role = {"USER"};

        user = new User("francesc3000@gmail.com","Francesc",
                "francesc3000", "contrasena01",
                role ,new Locale("Es"));
    }

    @Test
    public void shouldReturnUserDetails() throws Exception {

        when(validatingUserRepositoryDecorator.findAccountValidated(userName))
                .thenReturn(user);


        this.mockMvc.perform(get("/api/v1/users/getUserRoot")
                .principal(mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)));
    }
}
