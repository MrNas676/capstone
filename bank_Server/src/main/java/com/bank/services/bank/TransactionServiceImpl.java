package com.bank.services.bank;

import com.bank.dto.TransactionDto;
import com.bank.entity.Transaction;
import com.bank.entity.User;
import com.bank.repo.TransactionRepo;
import com.bank.repo.UserRepo;
import com.bank.responce.GeneralResponse;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {


    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TransactionRepo transactionRepo;

    @Override
    public GeneralResponse postTransaction(TransactionDto transactionDto) throws IOException {
        GeneralResponse response = new GeneralResponse();
        Optional<User> optionalUser = userRepo.findById(transactionDto.getUserId());
        if (optionalUser.isPresent()) {
            Transaction transaction = new Transaction();
            transaction.setFromAccount(transactionDto.getFromAccount());
            transaction.setToAccount(transactionDto.getToAccount());
            transaction.setDate(new Date());
            transaction.setAmount(transactionDto.getAmount());
            transaction.setReason(transactionDto.getReason());
            transaction.setType(transactionDto.getType());
            transaction.setUser(optionalUser.get());
            transactionRepo.save(transaction);
            response.setData(transaction);
            response.setMessage("Transaction posted Successfully");
            response.setStatus(HttpStatus.CREATED);
        } else {
            response.setStatus(HttpStatus.NOT_ACCEPTABLE);
            response.setMessage("User Not Found");
        }
        return response;
    }


    private Object transactionReceipt(Transaction transaction) throws IOException {
        HttpServletResponse response = null;
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
        String headerKey = "Content-Disposition";
        String headerVal = "attachment; filename=transaction receipt" + dateFormat.format(new Date()) + ".pdf";
        response.setHeader(headerKey, headerVal);
        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdfDocument;
        pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);
        try {
            document.add(new Paragraph("Transaction").setBold().setPaddingLeft(200f));
            Table table = new Table(new float[]{20f, 50f, 30F});
            table.setWidthPercent(100).setPadding(0).setFontSize(9);
            Cell cell1 = new Cell(1, 3);
            cell1.setTextAlignment(TextAlignment.CENTER);
            cell1.add("Transaction Receipt").setBold();
            table.addCell(cell1);
            table.addCell(new Cell().add("ID").setBold());
            table.addCell(new Cell().add("From Account").setBold());
            table.addCell(new Cell().add("Too Account").setBold());
            table.addCell(new Cell().add("Amount").setBold());
            table.addCell(new Cell().add("Type").setBold());
            table.addCell(new Cell().add("Date").setBold());
            table.addCell(new Cell().add("Reason").setBold());
            table.addCell(new Cell().add(transaction.getId().toString()));
            table.addCell(new Cell().add(transaction.getFromAccount()));
            table.addCell(new Cell().add(transaction.getToAccount()));
            table.addCell(new Cell().add(transaction.getAmount().toString()));
            table.addCell(new Cell().add(transaction.getType()));
            table.addCell(new Cell().add(transaction.getDate().toString()));
            table.addCell(new Cell().add(transaction.getReason()));
            document.add(table);
            document.close();
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }


}
