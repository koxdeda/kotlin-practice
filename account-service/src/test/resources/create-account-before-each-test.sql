delete from t_accounts;
delete from t_currency;

insert into t_accounts(id, balance, client_id) values
(175, 20, 226);

insert into t_currency(id, amount, created_at, currency, updated_at, account_id) values
(186, 20, "11/12/2022 11:24:35", "RUR", "11/12/2022 11:24:35", 175);
