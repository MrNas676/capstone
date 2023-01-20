import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import html2canvas from 'html2canvas';
import jspdf from 'jspdf';
import jsPDF from 'jspdf';
import { NzButtonSize } from 'ng-zorro-antd/button';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { TransactionService } from '../../service/transaction-service';

@Component({
  selector: 'app-transaction',
  templateUrl: './transaction.component.html',
  styleUrls: ['./transaction.component.scss']
})
export class TransactionComponent implements OnInit {

  FROMACCOUNT: string[] = [
    "Account 1", "Account 2", "Account 3"
  ]

  TOACCOUNT: string[] = [
    "Account 1", "Account 2", "Account 3"
  ]

  TYPE: string[] = [
    "Deposit", "Withdraw", "Transfer"
  ]

  isSpinning = false;
  size: NzButtonSize = 'large';
  validateForm!: FormGroup;
  response: any;

  constructor(private fb: FormBuilder,
    private transactionService: TransactionService,
    private notification: NzNotificationService,
    private router: Router) { }

  ngOnInit(): void {
    this.validateForm = this.fb.group({
      fromAccount: [null, [Validators.required]],
      toAccount: [null, [Validators.required]],
      amount: [null, [Validators.required]],
      reason: [null],
      type: [null, [Validators.required]],
    });
  }
  postTransaction() {
    this.isSpinning = true;
    this.transactionService.postTransaction(this.validateForm.value).subscribe((res) => {
      console.log(res);
      this.response = res.data;
      console.log(this.response);
      this.isSpinning = false;
      if (res.status == "CREATED") {
        this.notification
          .success(
            'SUCCESS',
            `Transaction Done successfully!`,
            { nzDuration: 5000 }
          );
        this.openPDF();
      } else {
        this.notification
          .error(
            'ERROR',
            `${res.message}`,
            { nzDuration: 5000 }
          )
      }
    }, error => {
      console.log("errorr", error);
      if (error.status == 406) {
        this.notification
          .error(
            'ERROR',
            `${error.error}`,
            { nzDuration: 5000 }
          )
      }
      this.isSpinning = false;
    })
  }

  public openPDF(): void {
    let DATA: any = document.getElementById('htmlData');
    html2canvas(DATA).then((canvas) => {
      let fileWidth = 208;
      let fileHeight = (canvas.height * fileWidth) / canvas.width;
      const FILEURI = canvas.toDataURL('image/png');
      let PDF = new jsPDF('p', 'mm', 'a4');
      let position = 0;
      PDF.addImage(FILEURI, 'PNG', 0, position, fileWidth, fileHeight);
      PDF.save('receipt.pdf');
    });
  }

}
