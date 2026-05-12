package com.projeto.sistema.dto;

import com.projeto.sistema.model.Especialidade;

import java.util.List;
import java.util.stream.Collectors;

public class EspecialidadeResponseDTO {

    private Long id;
    private String nome;
    private String descricao;
    private List<MedicoResumoDTO> medicos;

    public static EspecialidadeResponseDTO fromEntity(Especialidade especialidade) {
        EspecialidadeResponseDTO dto = new EspecialidadeResponseDTO();
        dto.id = especialidade.getId();
        dto.nome = especialidade.getNome();
        dto.descricao = especialidade.getDescricao();
        dto.medicos = especialidade.getMedicos().stream()
                .map(m -> new MedicoResumoDTO(m.getId(), m.getNome(), m.getCrm()))
                .collect(Collectors.toList());
        return dto;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public List<MedicoResumoDTO> getMedicos() { return medicos; }

    public static class MedicoResumoDTO {
        private Long id;
        private String nome;
        private String crm;

        public MedicoResumoDTO(Long id, String nome, String crm) {
            this.id = id;
            this.nome = nome;
            this.crm = crm;
        }

        public Long getId() { return id; }
        public String getNome() { return nome; }
        public String getCrm() { return crm; }
    }
}
