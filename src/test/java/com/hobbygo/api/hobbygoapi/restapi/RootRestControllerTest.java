package com.hobbygo.api.hobbygoapi.restapi;

import com.hobbygo.api.hobbygoapi.model.entity.Address;
import com.hobbygo.api.hobbygoapi.model.entity.Evento;
import com.hobbygo.api.hobbygoapi.model.entity.Member;
import com.hobbygo.api.hobbygoapi.model.entity.Player;
import com.hobbygo.api.hobbygoapi.restapi.controller.RootRestController;
import com.hobbygo.api.hobbygoapi.restapi.resource.EventoResource;
import com.hobbygo.api.hobbygoapi.restapi.resource.FactoryResource;
import com.hobbygo.api.hobbygoapi.restapi.resource.assembler.EventoResourceAssembler;
import com.hobbygo.api.hobbygoapi.security.EventoSecurityService;
import com.hobbygo.api.hobbygoapi.service.EventoService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.hobbygo.api.hobbygoapi.model.constants.Hobby.PADEL;
import static com.jayway.jsonassert.impl.matcher.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RootRestController.class)
//@Import({EventoResourceAssembler.class})
@WebAppConfiguration
@AutoConfigureRestDocs(outputDir = "build/snippets")
public class RootRestControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventoService eventoService;

    @MockBean
    private FactoryResource factoryResource;

    @MockBean
    private EventoSecurityService eventoSecurityService;

    private List<Evento> eventoList;

    private String userName = "francesc3000";

    private Player player;

    private Principal mockPrincipal;

    @Before
    public void setUp() {

        initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();

        mockPrincipal = Mockito.mock(Principal.class);
        when(mockPrincipal.getName()).thenReturn(userName);

        player = new Player("1","francesc3000@gmail.com",userName,PADEL);
        Evento evento =
                new Evento("1",
                        new Member(player,null),
                        "Evento 1", "www.avatar.es",true,
                        LocalDateTime.now(),LocalDateTime.now().plusDays(3),
                        new Address("Calle del Pepino","","Barcelona",
                                "España","08940",new Float(40),new Float(165)),
                        4,PADEL);

        eventoList = new ArrayList<>();
        eventoList.add(evento);
    }

    @Test
    public void shouldReturnRootMessage() throws Exception {

        when(eventoSecurityService.canModifyEvento(userName, eventoList.get(0).getId()))
                .thenReturn(true);

        EventoResourceAssembler assembler = new EventoResourceAssembler(userName,player,eventoSecurityService);

        when(factoryResource.getEventoResource(userName,eventoList.get(0)))
                .thenReturn(new EventoResource(eventoList.get(0)));
                //.thenReturn(assembler.toResource(evento));
/*
        this.mockMvc.perform(get("/api/v1").accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("/api/v1/users")))
                .andDo(document("home"))
        ;
        */
        this.mockMvc.perform(get("/api/v1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.links",hasSize(3)));
    }

    @Test
    public void shouldReturnEventoOnDistance() throws Exception{
        when(eventoService.getEventosByDistance(0,0,0))
                .thenReturn(eventoList);

        when(factoryResource.getEventoResource(userName,eventoList.get(0)))
                .thenReturn(new EventoResource(eventoList.get(0)));

        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();

        params.add("lon","10");
        params.add("lat","0");
        params.add("dis","0");

        this.mockMvc.perform(get("/api/v1/distance")
                .params(params)
                .principal(mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data",hasSize(0)));
    }
}
