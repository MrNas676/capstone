package com.bank.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TransactionDto {

    private Long id;

    private String fromAccount;

    private String toAccount;

    private Date date;

    private Long amount;

    private String reason;

    private String type;

    private Long userId;

}
