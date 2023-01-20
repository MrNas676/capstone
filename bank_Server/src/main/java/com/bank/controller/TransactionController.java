package com.bank.controller;

import com.bank.dto.TransactionDto;
import com.bank.responce.GeneralResponse;
import com.bank.services.bank.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api/bank/")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;


    @PostMapping("transaction")
    public GeneralResponse postTransaction(@RequestBody TransactionDto transactionDto) {
        GeneralResponse response = new GeneralResponse();
        try {
            return transactionService.postTransaction(transactionDto);
        } catch (Exception ex) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage("Sorry Something Wrong Happened.");
            return response;
        }
    }

}
