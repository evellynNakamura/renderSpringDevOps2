package com.example.apibancopicpay.services;

import com.example.apibancopicpay.models.Conta;
import com.example.apibancopicpay.repository.ContaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContaService {

    private final ContaRepository contaRepository;

    public ContaService(ContaRepository contaRepository){
        this.contaRepository = contaRepository;
    }

    // Buscando todas as contas
    public List<Conta> buscarTodasContas(){
        return contaRepository.findAll();
    }

    // Buscando a conta pelo id (numConta)
    public Conta buscarContaPorNum(String numConta){
        return contaRepository.findById(numConta).orElseThrow(() ->
                new RuntimeException("Conta não encontrada"));
    }

    //
}
