package com.projeto.sistema.service;

import com.projeto.sistema.dto.EspecialidadeRequestDTO;
import com.projeto.sistema.dto.EspecialidadeResponseDTO;
import com.projeto.sistema.exception.BusinessException;
import com.projeto.sistema.exception.ResourceNotFoundException;
import com.projeto.sistema.model.Especialidade;
import com.projeto.sistema.repository.EspecialidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EspecialidadeService {

    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    public EspecialidadeResponseDTO criar(EspecialidadeRequestDTO dto) {
        if (especialidadeRepository.existsByNome(dto.getNome())) {
            throw new BusinessException("Já existe uma especialidade com o nome: " + dto.getNome());
        }

        Especialidade especialidade = new Especialidade();
        especialidade.setNome(dto.getNome());
        especialidade.setDescricao(dto.getDescricao());

        return EspecialidadeResponseDTO.fromEntity(especialidadeRepository.save(especialidade));
    }

    public List<EspecialidadeResponseDTO> listarTodas() {
        return especialidadeRepository.findAll().stream()
                .map(EspecialidadeResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public EspecialidadeResponseDTO buscarPorId(Long id) {
        Especialidade especialidade = especialidadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Especialidade não encontrada com id: " + id));
        return EspecialidadeResponseDTO.fromEntity(especialidade);
    }

    public EspecialidadeResponseDTO atualizar(Long id, EspecialidadeRequestDTO dto) {
        Especialidade especialidade = especialidadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Especialidade não encontrada com id: " + id));

        if (!especialidade.getNome().equals(dto.getNome()) && especialidadeRepository.existsByNome(dto.getNome())) {
            throw new BusinessException("Já existe uma especialidade com o nome: " + dto.getNome());
        }

        especialidade.setNome(dto.getNome());
        especialidade.setDescricao(dto.getDescricao());

        return EspecialidadeResponseDTO.fromEntity(especialidadeRepository.save(especialidade));
    }

    public void deletar(Long id) {
        Especialidade especialidade = especialidadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Especialidade não encontrada com id: " + id));

        if (!especialidade.getMedicos().isEmpty()) {
            throw new BusinessException("Não é possível excluir a especialidade pois existem médicos vinculados a ela");
        }

        especialidadeRepository.delete(especialidade);
    }

    public Especialidade buscarEntidadePorId(Long id) {
        return especialidadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Especialidade não encontrada com id: " + id));
    }
}
