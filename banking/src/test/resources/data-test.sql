insert into customer (customer_id, customer_name, customer_surname) values (1, 'Alexandra', 'Panait');
insert into customer (customer_id, customer_name, customer_surname) values (2, 'Pieter-Jan', 'Beelen');
insert into customer (customer_id, customer_name, customer_surname) values (3, 'Eddy', 'Vandervaeren');

insert into account (account_id, iban, account_currency, account_no, account_type, balance, created_date, expiration_date, initial_credit, is_active, customer_id)
values (1, 'BE11BBRUEUR45364232', 'EUR', 'AP90469', 'Current', 1000, current_date - 5, current_date + 1068, 0, true, 1);
insert into account (account_id, iban, account_currency, account_no, account_type, balance, created_date, expiration_date, initial_credit, is_active, customer_id)
values (2, 'BE11BBRURON45364234', 'RON', 'AP90469', 'Current', 0, current_date - 6, current_date + 1068, 0, true, 1);

insert into account (account_id, iban, account_currency, account_no, account_type, balance, created_date, expiration_date, initial_credit, is_active, customer_id)
values (3, 'BE11BBRUEUR90048085', 'EUR', 'PB57499', 'Current', 0, current_date - 10, current_date + 1068, 0, true, 2);

insert into account_transaction (transaction_id, amount, transaction_date, transaction_type, account_id) values (1, 2000, current_date - 4, 'Deposit', 1);
insert into account_transaction (transaction_id, amount, transaction_date, transaction_type, account_id) values (2, -1000, current_date - 3, 'Withdrawal', 1);
insert into account_transaction (transaction_id, amount, transaction_date, transaction_type, account_id) values (3, -1000, current_date - 2, 'Withdrawal', 1);
insert into account_transaction (transaction_id, amount, transaction_date, transaction_type, account_id) values (4, 5000, current_date - 1, 'Deposit', 1);

insert into account_transaction (transaction_id, amount, transaction_date, transaction_type, account_id) values (5, 1000, current_date - 6, 'Deposit', 3);
insert into account_transaction (transaction_id, amount, transaction_date, transaction_type, account_id) values (6, -320, current_date - 5, 'Withdrawal', 3);
insert into account_transaction (transaction_id, amount, transaction_date, transaction_type, account_id) values (7, -10, current_date - 4, 'Withdrawal', 3);
insert into account_transaction (transaction_id, amount, transaction_date, transaction_type, account_id) values (8, -200, current_date - 2, 'Withdrawal', 3);