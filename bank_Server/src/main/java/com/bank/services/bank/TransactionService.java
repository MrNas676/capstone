package com.bank.services.bank;


import com.bank.dto.TransactionDto;
import com.bank.responce.GeneralResponse;

import java.io.IOException;

public interface TransactionService {
    GeneralResponse postTransaction(TransactionDto transactionDto) throws IOException;
}

