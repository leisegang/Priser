SELECT Customers.CustomerName, Orders.OrderID
FROM Customers
INNER JOIN Orders
ON Customers.CustomerID=Orders.CustomerID
ORDER BY Customers.CustomerName;



SELECT utested.uid, utested.name, utested.description, utested.url, utested.picurl, utested.mapurl, price.price
FROM utested
INNER JOIN price
ON utested.uid=price.uid
GROUP BY utested.uid
ORDER BY utested.uid ASC;


SELECT utested.uid, utested.name, utested.description, utested.url, utested.picurl, utested.mapurl, price.price
FROM utested
INNER JOIN price
ON utested.uid=price.uid
ORDER BY price.price ASC;

SELECT utested.uid, utested.name, utested.description, utested.url, utested.picurl, utested.mapurl, price.price, price.pid
FROM utested
INNER JOIN price
ON utested.uid=price.uid
GROUP BY utested.uid
ORDER BY price.pid DESC;






