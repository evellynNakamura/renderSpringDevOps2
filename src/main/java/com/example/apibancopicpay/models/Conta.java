package com.example.apibancopicpay.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Random;

@Entity
public class Conta {

    @Id
    @Column(name = "numero_conta")
    private String numConta;

    @NotNull(message = "O saldo não pode ser nulo e nem negativo")
    @Min(value = 0, message = "O saldo não pode ser negativo")
    private double saldo;

    @NotNull(message = "O limite especial")
    @Min(value = 0, message = "O limite não pode ser negativo")
    @Column(name = "limite_especial")
    private double limite;

    @NotNull(message = "O cpf do cliente não pode ser nulo")
    @Size(min = 11, max = 11, message = "O cpf deve conter somente 11 caracteres")
//    @CPF
    private String cliente_cpf;

    public String getNumConta() {
        return this.numConta;
    }

    public void setNumConta(String numConta) {
        this.numConta = numConta;
    }

    public double getSaldo() {
        return this.saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getLimite() {
        return this.limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    public String getCliente_cpf() {
        return this.cliente_cpf;
    }

    public void setCliente_cpf(String cliente_cpf) {
        this.cliente_cpf = cliente_cpf;
    }


    public Conta(double saldo, double limite, String cliente_cpf) {
        Random random = new Random();
        int num1 = random.nextInt(10);
        int num2 = random.nextInt(10);
        int num3 = random.nextInt(10);
        int num4 = random.nextInt(10);
        int digVerif = (num1 + num2 + num3 + num4) % 10;
        this.numConta = Integer.toString(num1) + Integer.toString(num2) + Integer.toString(num3) +
                Integer.toString(num4) + Integer.toString(digVerif);

        this.saldo = saldo;
        this.limite = limite;
        this.cliente_cpf = cliente_cpf;
    }

    public Conta(){
    }

    public String toString() {
        return "\nConta " +
                "\n| Número da conta: " + this.numConta +
                "\n| Saldo: " + this.saldo +
                "\n| Limite: " + this.limite +
                "\n| CPF do cliente: " + this.cliente_cpf;
    }
}
