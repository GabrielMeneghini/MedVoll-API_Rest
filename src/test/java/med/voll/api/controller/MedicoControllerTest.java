package med.voll.api.controller;

import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.medico.*;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class MedicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<DadosCadastroMedico> jtDadosCadastroMedico;

    @Autowired
    private JacksonTester<DadosDetalhamentoMedico> jtDadosDetalhamentoMedico;

    @MockitoBean
    private MedicoRepository mockMedicoRepository;

    @Test
    @DisplayName("Deveria devolver código http 400 quando informações estão inválidas")
    @WithMockUser
    void cadastrarCenario1() throws Exception {
        var response = mockMvc.perform(post("/medicos"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver código http 201 quando informações estão válidas")
    @WithMockUser
    void cadastrarCenario2() throws Exception {
        var medico = new Medico(gerarDadosCadastroMedico());
        when(mockMedicoRepository.save(any())).thenReturn(medico);

        var response = mockMvc
                .perform(post("/medicos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jtDadosCadastroMedico.write(gerarDadosCadastroMedico()).getJson()))
                .andReturn()
                .getResponse();

        var jsonEsperado = jtDadosDetalhamentoMedico.write(new DadosDetalhamentoMedico(medico)).getJson();
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    public DadosCadastroMedico gerarDadosCadastroMedico() {
        return new DadosCadastroMedico(
                "MedicoTeste",
                "medicoTeste@vollmed.com",
                "00911111111",
                "000000",
                Especialidade.CARDIOLOGIA,
                gerarDadosEndereco()
        );
    }

    public DadosEndereco gerarDadosEndereco() {
        return new DadosEndereco(
                "Rua Teste",
                "Bairro Teste",
                "00000000",
                "Cidade Teste",
                "UF",
                "00",
                "Complemento Teste");
    }

}