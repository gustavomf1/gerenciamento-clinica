package com.projeto.sistema.service;

import com.projeto.sistema.dto.MedicoRequestDTO;
import com.projeto.sistema.dto.MedicoResponseDTO;
import com.projeto.sistema.exception.BusinessException;
import com.projeto.sistema.exception.ResourceNotFoundException;
import com.projeto.sistema.model.Especialidade;
import com.projeto.sistema.model.Medico;
import com.projeto.sistema.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private EspecialidadeService especialidadeService;

    public MedicoResponseDTO criar(MedicoRequestDTO dto) {
        if (medicoRepository.existsByCrm(dto.getCrm())) {
            throw new BusinessException("Já existe um médico com o CRM: " + dto.getCrm());
        }
        if (medicoRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("Já existe um médico com o email: " + dto.getEmail());
        }

        Medico medico = new Medico();
        medico.setNome(dto.getNome());
        medico.setCrm(dto.getCrm());
        medico.setEmail(dto.getEmail());
        medico.setTelefone(dto.getTelefone());

        return MedicoResponseDTO.fromEntity(medicoRepository.save(medico));
    }

    public List<MedicoResponseDTO> listarTodos() {
        return medicoRepository.findAll().stream()
                .map(MedicoResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public MedicoResponseDTO buscarPorId(Long id) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Médico não encontrado com id: " + id));
        return MedicoResponseDTO.fromEntity(medico);
    }

    public MedicoResponseDTO atualizar(Long id, MedicoRequestDTO dto) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Médico não encontrado com id: " + id));

        if (!medico.getCrm().equals(dto.getCrm()) && medicoRepository.existsByCrm(dto.getCrm())) {
            throw new BusinessException("Já existe um médico com o CRM: " + dto.getCrm());
        }
        if (!medico.getEmail().equals(dto.getEmail()) && medicoRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("Já existe um médico com o email: " + dto.getEmail());
        }

        medico.setNome(dto.getNome());
        medico.setCrm(dto.getCrm());
        medico.setEmail(dto.getEmail());
        medico.setTelefone(dto.getTelefone());

        return MedicoResponseDTO.fromEntity(medicoRepository.save(medico));
    }

    public void deletar(Long id) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Médico não encontrado com id: " + id));
        medicoRepository.delete(medico);
    }

    public MedicoResponseDTO adicionarEspecialidade(Long medicoId, Long especialidadeId) {
        Medico medico = medicoRepository.findById(medicoId)
                .orElseThrow(() -> new ResourceNotFoundException("Médico não encontrado com id: " + medicoId));

        Especialidade especialidade = especialidadeService.buscarEntidadePorId(especialidadeId);

        if (medico.getEspecialidades().contains(especialidade)) {
            throw new BusinessException("O médico já possui essa especialidade");
        }

        medico.getEspecialidades().add(especialidade);
        return MedicoResponseDTO.fromEntity(medicoRepository.save(medico));
    }

    public MedicoResponseDTO removerEspecialidade(Long medicoId, Long especialidadeId) {
        Medico medico = medicoRepository.findById(medicoId)
                .orElseThrow(() -> new ResourceNotFoundException("Médico não encontrado com id: " + medicoId));

        Especialidade especialidade = especialidadeService.buscarEntidadePorId(especialidadeId);

        if (!medico.getEspecialidades().contains(especialidade)) {
            throw new BusinessException("O médico não possui essa especialidade");
        }

        medico.getEspecialidades().remove(especialidade);
        return MedicoResponseDTO.fromEntity(medicoRepository.save(medico));
    }
}
