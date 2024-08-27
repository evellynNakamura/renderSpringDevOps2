package com.example.apibancopicpay.controllers;

import com.example.apibancopicpay.models.Cliente;
import com.example.apibancopicpay.models.Conta;
import com.example.apibancopicpay.services.ContaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/picpay/contas")
public class ContaController {

    private final ContaService contaService;

    private final Validator validador;

    public ContaController(ContaService contaService, Validator validador){
        this.contaService = contaService;
        this.validador = validador;
    }

    // Buscando todas as contas
    @GetMapping("/buscar")
    public List<Conta> listarContas(){
        return contaService.buscarTodasContas();
    }

    // Buscando o conta pelo número
    @GetMapping("/buscarPorNumConta/{numConta}")
    public ResponseEntity<String> buscarContaPorNum(@PathVariable String numConta){
        try {
            return ResponseEntity.ok(contaService.buscarContaPorNum(numConta).toString());
        }catch (RuntimeException re){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada");
        }
    }


}
