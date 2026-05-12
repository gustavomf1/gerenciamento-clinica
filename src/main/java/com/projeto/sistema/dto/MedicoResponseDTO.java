package com.projeto.sistema.dto;

import com.projeto.sistema.model.Medico;

import java.util.List;
import java.util.stream.Collectors;

public class MedicoResponseDTO {

    private Long id;
    private String nome;
    private String crm;
    private String email;
    private String telefone;
    private List<EspecialidadeResumoDTO> especialidades;

    public static MedicoResponseDTO fromEntity(Medico medico) {
        MedicoResponseDTO dto = new MedicoResponseDTO();
        dto.id = medico.getId();
        dto.nome = medico.getNome();
        dto.crm = medico.getCrm();
        dto.email = medico.getEmail();
        dto.telefone = medico.getTelefone();
        dto.especialidades = medico.getEspecialidades().stream()
                .map(e -> new EspecialidadeResumoDTO(e.getId(), e.getNome()))
                .collect(Collectors.toList());
        return dto;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getCrm() { return crm; }
    public String getEmail() { return email; }
    public String getTelefone() { return telefone; }
    public List<EspecialidadeResumoDTO> getEspecialidades() { return especialidades; }

    public static class EspecialidadeResumoDTO {
        private Long id;
        private String nome;

        public EspecialidadeResumoDTO(Long id, String nome) {
            this.id = id;
            this.nome = nome;
        }

        public Long getId() { return id; }
        public String getNome() { return nome; }
    }
}
