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


SELECT c.customer_name, o.customer_id, o.order_id,o.id 
FROM customers c 
INNER JOIN orders o 
    ON o.id = (SELECT id 
    			FROM orders 
    			WHERE customer_id = c.id 
    			ORDER BY id DESC 
    			LIMIT 1)
    			
SELECT u.uid, u.name, p.pid, p.price, p.uid 
FROM utested u 
INNER JOIN price p 
    ON p.pid = (SELECT uid 
    			FROM price 
    			WHERE p.uid = u.uid 
    			ORDER BY uid DESC 
    			LIMIT 1)
    			
    			
SELECT 
	u.uid,
	u.name, 
	MAX(p.pid),
	p.price
FROM 
	utested u
INNER JOIN
	price p
ON
	p.uid = u.uid
ORDER BY 
	u.uid desc
GROUP BY p.uid
LIMIT 20;

SELECT tbl.customer_id, tbl.customer_name, o.order_id,  MaxOrderDate, Completed_orders, Total_Order
FROM [ORDER] o
JOIN
( SELECT c.customer_id, c.customer_name, MAX(o.order_date) AS MaxOrderDate
     ,SUM(CASE WHEN o.status = 'completed' THEN 1 ELSE 0 END) AS Completed_orders
     ,COUNT(order_id) AS Total_Order
   FROM Customer c 
   JOIN [Order] o
     ON c.customer_id = o.customer_id
  WHERE c.customer_id = 1
  GROUP BY c.customer_id,c.customer_name
) tbl
ON o.CUSTOMER_ID = tbl.CUSTOMER_ID
AND o.order_date = tbl.MaxOrderDate;
	


SELECT 
	u.uid,
	u.name, 
	p.pid,
	p.price
FROM 
	utested u
	INNER JOIN
		price p
			ON
				p.uid = u.uid
GROUP BY p.uid
ORDER BY 
	u.name ASC


SELECT tbl.uid, tbl.name, tbl.description, o.pid,  MaxOrderPID
FROM price o
JOIN
( SELECT c.uid, c.name, c.description, MAX(o.pid) AS MaxOrderPID
   FROM utested c 
   JOIN price o
     ON c.uid = o.uid
  GROUP BY c.uid,c.name
) tbl
ON o.uid = tbl.uid
AND o.pid = tbl.MaxOrderPID
	



