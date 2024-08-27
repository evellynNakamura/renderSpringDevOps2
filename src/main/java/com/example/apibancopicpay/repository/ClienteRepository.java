package com.example.apibancopicpay.repository;

import com.example.apibancopicpay.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, String> {

    List<Cliente> findByNomeContainsIgnoreCase(String nome);

    List<Cliente> findByEmailEndsWithIgnoreCase(String valor);

//    Cliente findFirstByByEmailEndsWithIgnoreCase(String valor);
//
//
//    void deleteByEmailEndingWithIgnoreCase(Cliente cliente);

}
