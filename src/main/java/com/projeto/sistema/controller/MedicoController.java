package com.projeto.sistema.controller;

import com.projeto.sistema.dto.MedicoRequestDTO;
import com.projeto.sistema.dto.MedicoResponseDTO;
import com.projeto.sistema.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @PostMapping
    public ResponseEntity<MedicoResponseDTO> criar(@RequestBody @Valid MedicoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(medicoService.criar(dto));
    }

    @GetMapping
    public ResponseEntity<List<MedicoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(medicoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(medicoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicoResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid MedicoRequestDTO dto) {
        return ResponseEntity.ok(medicoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        medicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{medicoId}/especialidades/{especialidadeId}")
    public ResponseEntity<MedicoResponseDTO> adicionarEspecialidade(
            @PathVariable Long medicoId,
            @PathVariable Long especialidadeId) {
        return ResponseEntity.ok(medicoService.adicionarEspecialidade(medicoId, especialidadeId));
    }

    @DeleteMapping("/{medicoId}/especialidades/{especialidadeId}")
    public ResponseEntity<MedicoResponseDTO> removerEspecialidade(
            @PathVariable Long medicoId,
            @PathVariable Long especialidadeId) {
        return ResponseEntity.ok(medicoService.removerEspecialidade(medicoId, especialidadeId));
    }
}
