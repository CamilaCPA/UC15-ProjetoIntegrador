/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senac.financa.controller;

import com.senac.financa.model.Transaction;
import com.senac.financa.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("transactions", transactionService.findAll());
        model.addAttribute("balance", transactionService.calculateBalance());
        return "index";
    }

    @PostMapping("/add")
    public String addTransaction(@RequestParam String description, @RequestParam Double amount, @RequestParam String type) {
        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);
        transaction.setType(type);
        transactionService.save(transaction);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editTransaction(@PathVariable Long id, Model model) {
        Transaction transaction = transactionService.findById(id);
        if (transaction == null) {
            return "redirect:/"; // Redireciona se a transação não for encontrada
        }
        model.addAttribute("transaction", transaction);
        return "edit";
    }

    @PostMapping("/update/{id}")
    public String updateTransaction(@PathVariable Long id, @RequestParam String description, @RequestParam Double amount, @RequestParam String type) {
        Transaction transaction = transactionService.findById(id);
        if (transaction == null) {
            return "redirect:/"; // Redireciona se a transação não for encontrada
        }
        transaction.setDescription(description);
        transaction.setAmount(amount);
        transaction.setType(type);
        transactionService.save(transaction);
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String deleteTransaction(@PathVariable Long id) {
        transactionService.deleteById(id);
        return "redirect:/";
    }
}
