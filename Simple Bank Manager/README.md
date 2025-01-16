# Simple Bank Manager
The Simple Bank Manager app helps user manage bank account and pay bills. 

## Features
- User can log into the bank account. (Default username and password given by Hyperskill are 'Lara' and '1234')
- After successfully logging in, user can choose one of the actions in the main menu: 
View Balance, Transfer Funds, Calculate Exchange, or Pay Bills
- User can view current balance in the account.
- User can transfer funds to a checking account (account number starting with 'ca' followed by numbers) or
  savings account (account number starting with 'sa' followed by numbers.)
- User can use exchange calculator to convert certain amount from a currency to another (options are USD, GBP, and EUR.)
- User can pay bill (gas, electricity, or water) by entering code (GAS, ELEC, and WTR.) This can only be done
if user has enough balance in the account.

- **TODO:**
This project was completed as a part of Hyperskill coursework. Feature that can be added in the future includes encrypting user account password.

## Screenshots
#### Login:
<kbd><img src="https://github.com/user-attachments/assets/feb5c190-3c78-4ede-ad8a-e8ee6f0f35fe" width="200"></kbd>
#### User Menu:
<kbd><img src="https://github.com/user-attachments/assets/751d8fed-c18a-434b-94b6-b5be7811b457" width="200"></kbd>
#### View Balance:
<kbd><img src="https://github.com/user-attachments/assets/5e1f4179-b1f4-4003-ab59-249643957940" width="200"></kbd>
#### Transfer Funds:
<kbd><img src="https://github.com/user-attachments/assets/7bd69824-0819-4ce7-b68d-3713ec9d455d" width="200"></kbd>
<kbd><img src="https://github.com/user-attachments/assets/d519199e-41a7-4b27-b189-a74c2ba10b85" width="200"></kbd>
<kbd><img src="https://github.com/user-attachments/assets/7705a734-73c9-4cf0-8393-024cc4423c6d" width="200"></kbd>
#### Currency Exchange:
<kbd><img src="https://github.com/user-attachments/assets/510aeb52-de76-4cb4-a803-03eca97d9794" width="200"></kbd>
#### Pay Bills:
<kbd><img src="https://github.com/user-attachments/assets/d44b0b11-ed0a-40bd-815a-1a272ec3c08d" width="200"></kbd>
<kbd><img src="https://github.com/user-attachments/assets/f44edcb7-a16f-4c18-a92d-f24efa7f11b8" width="200"></kbd>
<kbd><img src="https://github.com/user-attachments/assets/038e8cb8-f636-4a05-b7ec-d1f58f77d42f" width="200"></kbd>
<kbd><img src="https://github.com/user-attachments/assets/c9abfa54-8b90-4240-b37b-9bbf949a1452" width="200"></kbd>


## Directory and Files
```
Simple Bank Manager/Simple Bank Manager/task/src/main/java/org/hyperskill/simplebankmanager

> MainActivity.kt
> LoginFragment.kt
> LoginViewModel.kt
> UserMenuFragment.kt
> ViewBalanceFragment.kt
> CurrentBalanceSharedViewModel.kt
> TransferFundsFragment.kt
> CalculateExchangeFragment.kt
> CurrencyExchangeSharedViewModel.kt
> PayBillsFragment.kt
> BillInfoSharedViewModel.kt
> CorrectBillCodeDialogFragment.kt
> IncorrectBillCodeDialogFragment.kt
> InsufficientFundDialogFragment.kt

```

#### For more information about the project, please visit: [Hyperskill Simple Bank Manager Project](https://hyperskill.org/projects/333)

