package com.example.apibancopicpay.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

@Entity
public class Cliente {

    @Id
    @NotNull(message = "O nome não pode ser nulo")
//    @CPF // verifica se possui 11 dígitos e se ele existe
    private String cpf;

    @NotNull(message = "O nome não pode ser nulo")
    @Size(min = 3, message = "O nome deve ter pelo menos 3 caracteres")
    private String nome;

    @NotNull(message = "O email não pode ser nulo")
    @Email //verifica se tem o @ e o .com
    private String email;

    @NotNull(message = "O telefone não pode ser nulo")
    @Size(min = 11, max = 15, message = "O telefone deve ter no mínimo 11 caracteres e no máximo 15")
    private String telefone;

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Cliente(String cpf, String nome, String email, String telefone) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    public Cliente(){}

    public String toString() {
        return "\nCliente " +
                "\n| cpf: " + this.cpf +
                "\n| Nome: " + this.nome +
                "\n| E-mail: " + this.email +
                "\n| Telefone: " + this.telefone;
    }

}
