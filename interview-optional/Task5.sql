-- The following SQL queries have been written and locally tested taking MySQL as RDBMS

-- a) Get the total number of transactions in 2013 of a specific category (‘15’) and user (‘356789’).

SELECT *
FROM transaction
INNER JOIN account
ON account.id = transaction.account_id
WHERE posted_date >= '2013-01-01' AND posted_date < '2014-01-01' AND
category_id = 15 AND
account.user_id = 356789;

-- b) Get transactions of a user filtering by account and current month. (user_id = 356789)

SELECT *
FROM transaction
WHERE account_id = (SELECT id FROM account WHERE user_id = 356789 LIMIT 1) AND
MONTH(posted_date) = MONTH(CURRENT_DATE());

-- c) Get income categories of a user for the current month. A category is considered INCOME when the amount of the transaction is positive. (user_id = 356789)

SELECT DISTINCT category.id, category.name
FROM category
INNER JOIN transaction 
ON transaction.category_id = category.id
INNER JOIN account
ON account.id = transaction.account_id
WHERE MONTH(posted_date) = MONTH(CURRENT_DATE()) AND
account.user_id = 356789 AND
amount > 0;
