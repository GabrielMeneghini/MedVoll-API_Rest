package med.voll.api.controller;

import med.voll.api.domain.consulta.AgendaDeConsultas;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.DadosCancelamentoConsulta;
import med.voll.api.domain.consulta.DadosDetalhamentoConsulta;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> jtDadosAgendamentoConsulta;

    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> jtDadosDetalhamentoConsulta;

    @Autowired
    private JacksonTester<DadosCancelamentoConsulta> jtDadosCancelamentoConsulta;

    @MockitoBean
    private AgendaDeConsultas mockAgendaDeConsultas;

    @Test
    @DisplayName("Deveria devolver código http 400 quando informações estão inválidas")
    @WithMockUser
    void agendarCenario1() throws Exception {
        var response = mockMvc.perform(post("/consultas"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver código http 200 quando informações estão válidas")
    @WithMockUser
    void agendarCenario2() throws Exception {
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var dadosDetalhamentoConsulta = new DadosDetalhamentoConsulta(null, 17l, 19l, proximaSegundaAs10);
        when(mockAgendaDeConsultas.agendar(any())).thenReturn(dadosDetalhamentoConsulta);

        var response = mockMvc
                .perform(
                        post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jtDadosAgendamentoConsulta
                                .write(new DadosAgendamentoConsulta(proximaSegundaAs10, 17l, 19l, null))
                                .getJson()
                        )
                )
                .andReturn().getResponse();

        var jsonEsperado = jtDadosDetalhamentoConsulta
                .write(dadosDetalhamentoConsulta)
                .getJson();
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

//    @Test
//    @DisplayName("Deveria devolver código http 400 quando id da Consulta não existe")
//    @WithMockUser
//    void cancelarCenario1() throws Exception {
//
//        var response = mockMvc
//                .perform(
//                        delete("/consultas")
//                )
//                .andReturn()
//                .getResponse();
//
//        assertThat(response.getStatus()).isEqualTo(400);
//    }

}
