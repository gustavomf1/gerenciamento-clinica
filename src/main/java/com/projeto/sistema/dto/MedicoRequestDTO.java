package com.projeto.sistema.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class MedicoRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "CRM é obrigatório")
    @Size(max = 20, message = "CRM deve ter no máximo 20 caracteres")
    private String crm;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;
    
    @Size(max = 11, message = "Telefone deve ter no máximo 11 dígitos")
    private String telefone;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCrm() { return crm; }
    public void setCrm(String crm) { this.crm = crm; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
}
