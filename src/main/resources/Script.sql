select product_name from orders
left join customers c on c.id = orders.customers_id
where c.name = :name;