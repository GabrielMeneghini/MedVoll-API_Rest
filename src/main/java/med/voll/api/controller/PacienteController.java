package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    public void casdastrar(@RequestBody @Valid DadosCadastroPaciente dados) {
        repository.save(new Paciente(dados));
    }
    
    @GetMapping
    public Page<DadosListagemPaciente> listar(@PageableDefault(size=10, sort={"nome"}) Pageable pageable) {
        return repository.findAllByAtivoTrue(pageable).map(p -> new DadosListagemPaciente(p.getId(), p.getNome(),p.getEmail(), p.getCpf()));
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoPaciente dados) {
        Paciente paciente = repository.getReferenceById(dados.id());
        paciente.atualizarInformacoes(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deletar(@PathVariable Long id) {
        repository.getReferenceById(id).excluir();
    }
}
