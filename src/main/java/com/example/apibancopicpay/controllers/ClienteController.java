package com.example.apibancopicpay.controllers;

import com.example.apibancopicpay.models.Cliente;
import com.example.apibancopicpay.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController //Rest: sinaliza que vai ser um retorno JSON | Controller: sinalizar que é um controler
@RequestMapping("/api/picpay/clientes") //indicando qual é a url padrão do nosso Controller
public class ClienteController {

    private final ClienteService clienteService;

    private final Validator validador;

    public ClienteController(ClienteService clienteService, Validator validador){
        this.clienteService = clienteService;
        this.validador = validador;
    }

    // Buscando todods os cliente
    @GetMapping("/buscar")
    public List<Cliente> listarClientes(){
        return clienteService.buscarTodosClientes();
    }

    // Buscando o cliente pelo cpf
    @GetMapping("/buscarPorCPF/{cpf}")
    public ResponseEntity<String> buscarPorCPF(@PathVariable String cpf){
        try {
            return ResponseEntity.ok(clienteService.buscarClientePorCPF(cpf).toString());
        }catch (RuntimeException re){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado");
        }
    }

    // Buscando o cliente pelo nome, ele pode estar em qualquer parte do nome
    @GetMapping("/buscarPorNome/{nome}")
    public List<Cliente> buscarPorNome(@PathVariable String nome){
        return clienteService.buscarClientePorNome(nome);
    }

    // Buscando os emails pelo final
    @GetMapping("/buscarPorFinalEmail/{valor}")
    public List<Cliente> buscarPorFinalEmail(@PathVariable String valor){
        return clienteService.buscarPorFinalEmail(valor);
    }

    // Inserindo um cliente
    @PostMapping("/inserirCliente")
    public ResponseEntity<String> inserirCliente(@Valid @RequestBody Cliente cliente,
                                                BindingResult resultado){
        if(resultado.hasErrors()){
            Map<String, String> erros = new HashMap<>();
            for (FieldError erro : resultado.getFieldErrors()) {
                // Coloque o nome do campo e a mensagem de erro no mapa
                erros.put(erro.getField(), erro.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros.toString());
        } else{
            clienteService.salvarCliente(cliente);
            return ResponseEntity.ok("Cliente inserido com sucesso");
        }
    }

    // Atualizando um cliente
    @PutMapping("/atualizarCliente/{cpf}")
    public ResponseEntity<String> atualizarCliente(@PathVariable String cpf,
                                                   @Valid @RequestBody Cliente clienteAtualizado,
                                                   BindingResult resultado){
        if(resultado.hasErrors()){
            Map<String, String> erros = new HashMap<>();
            for (FieldError erro : resultado.getFieldErrors()) {
                // Coloque o nome do campo e a mensagem de erro no mapa
                erros.put(erro.getField(), erro.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros.toString());
        } else{
            Cliente clienteExistente = clienteService.buscarClientePorCPF(cpf);

            Cliente cliente = clienteExistente;
            cliente.setNome(clienteAtualizado.getNome());
            cliente.setEmail(clienteAtualizado.getEmail());
            cliente.setTelefone(clienteAtualizado.getTelefone());
            clienteService.salvarCliente(cliente);
            return ResponseEntity.ok("Cliente atualizado com sucesso");
        }
    }

    // Atualizando uma parte do cliente
    @PatchMapping("atualizarParcial/{cpf}")
    public ResponseEntity<String> atualizarProdutoParcial(@PathVariable String cpf,
                                                          @RequestBody Map<String, Object> updates) {
        Cliente clienteExistente = clienteService.buscarClientePorCPF(cpf);

        try {
            Cliente cliente = clienteExistente;

            //Atualiza apenas os campos que foram passados no corpo da requisição
            if (updates.containsKey("nome")) {
                cliente.setNome((String) updates.get("nome"));
            }
            if (updates.containsKey("email")) {
                cliente.setEmail((String) updates.get("email"));
            }
            if (updates.containsKey("telefone")) {
                cliente.setTelefone((String) updates.get("telefone"));
            }

            //Validando os dados
            DataBinder binder =  new DataBinder(cliente); //vincula o DataBinder ao produto
            binder.setValidator(validador); //configura o validador
            binder.validate(); // executa o validado no objeto vinculado
            BindingResult resultado = binder.getBindingResult();
            if (resultado.hasErrors()){
                Map<String, String> erros = new HashMap<>();
                for (FieldError erro : resultado.getFieldErrors()) {
                    // Coloque o nome do campo e a mensagem de erro no mapa
                    erros.put(erro.getField(), erro.getDefaultMessage());
                }
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros.toString());
            }
            Cliente clienteSalvo = clienteService.salvarCliente(cliente);
            return ResponseEntity.ok(String.valueOf(clienteSalvo));

        } catch (RuntimeException re){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
    }

    // Removendo um cliente
    @DeleteMapping("/excluir/{cpf}")
    public ResponseEntity<String> excluirCliente(@PathVariable String cpf) {

        try {
            clienteService.excluirCliente(cpf);
            return ResponseEntity.ok("Cliente excluido com sucesso!");

        }catch (RuntimeException re){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado");
        }
    }

//    // Removendo um cliente pelo final do email
//    @DeleteMapping("/excluirPorFinalEmail/{valor}")
//    public ResponseEntity<String> excluirClientePorFinalEmail(@PathVariable String valor) {
//
//        try {
//            clienteService.excluirPorFinalEmail(valor);
//            return ResponseEntity.ok("Cliente excluido com sucesso!");
//
//        }catch (RuntimeException re){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado");
//        }
//    }
    // testar ele depois
}
