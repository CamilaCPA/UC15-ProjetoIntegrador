/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senac.financa.service;

import com.senac.financa.model.Transaction;
import com.senac.financa.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }

     public double calculateBalance() {
        // Obter todas as transações uma única vez
        List<Transaction> transactions = transactionRepository.findAll();

        // Calcular a soma das receitas
        double income = transactions.stream()
                .filter(t -> "RECEITA".equals(t.getType()))
                .mapToDouble(Transaction::getAmount)
                .sum();

        // Calcular a soma das despesas
        double expense = transactions.stream()
                .filter(t -> "DESPESA".equals(t.getType()))
                .mapToDouble(Transaction::getAmount)
                .sum();

        // Retornar o saldo
        return income - expense;
    }

    public Transaction findById(Long id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        return transaction.orElse(null);
    }

    public void deleteById(Long id) {
        transactionRepository.deleteById(id);
    }
    
}
