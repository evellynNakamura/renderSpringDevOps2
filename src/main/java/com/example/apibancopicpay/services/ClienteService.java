package com.example.apibancopicpay.services;

import com.example.apibancopicpay.models.Cliente;
import com.example.apibancopicpay.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    // Buscando todos os clientes
    public List<Cliente> buscarTodosClientes(){
        return clienteRepository.findAll();
    }

    // Buscando cliente pelo id (cpf)
    public Cliente buscarClientePorCPF(String cpf) {
        return clienteRepository.findById(cpf).orElseThrow(() ->
                new RuntimeException("Cliente não encontrado"));
    }

    // Buscando pelo nome, ele pode estar em qualquer lugar da linha
    public List<Cliente> buscarClientePorNome(String nome){
        return clienteRepository.findByNomeContainsIgnoreCase(nome);
    }

    // Buscando pela palavra que está no final do email
    public List<Cliente> buscarPorFinalEmail(String valor){
        return clienteRepository.findByEmailEndsWithIgnoreCase(valor);
    }

    // Salvando o cliente
    @Transactional //Quando você vai fazer alguma alteração no banco
    public Cliente salvarCliente(Cliente cliente){
        return clienteRepository.save(cliente);
        // insere e altera
    }

    // Excluindo cliente (o cliente não é excluido se ele tiver uma conta)
    public Cliente excluirCliente(String cpf){
        Cliente cliente = buscarClientePorCPF(cpf);
        clienteRepository.delete(cliente);
        return cliente;

        //Verificar se o cliente tem uma conta e se ele tiver, ele não pode excluir o cliente
    }

//    public Cliente excluirPorFinalEmail(String valor){
//        Cliente cliente = clienteRepository.findFirstByByEmailEndsWithIgnoreCase(valor);
//        clienteRepository.deleteByEmailEndingWithIgnoreCase(cliente);
//        return cliente;
//    }
}
