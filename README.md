# Hibernate N+1 Problem Demo

This project demonstrates the **N+1 problem** in Hibernate and how to resolve it using `JOIN FETCH` or `@EntityGraph`.

---

## Console Output

### ❌ With N+1 Problem

```bash
2025-04-19T11:57:51.009+05:30  INFO 17416 --- [nPlusOne] [nio-8080-exec-1] com.satish.service.CustomerService     : getCustomersWithNPlusOneProblem() service starts : with N+1 Problem
Hibernate:
    select
        c1_0.id,
        c1_0.name
    from
        customer c1_0
Hibernate:
    select
        a1_0.customer_id,
        a1_0.id,
        a1_0.city,
        a1_0.zip_code
    from
        address a1_0
    where
        a1_0.customer_id=?
Hibernate:
    select
        a1_0.customer_id,
        a1_0.id,
        a1_0.city,
        a1_0.zip_code
    from
        address a1_0
    where
        a1_0.customer_id=?
```
### ✅ Without N+1 Problem

```bash
2025-04-19T12:00:22.416+05:30  INFO 35496 --- [nPlusOne] [nio-8080-exec-1] com.satish.service.CustomerService       : ==========================================================================
2025-04-19T12:00:22.416+05:30  INFO 35496 --- [nPlusOne] [nio-8080-exec-1] com.satish.service.CustomerService       : getCustomersWithoutNPlusOneProblem() service starts : without N+1 Problem
Hibernate:
    select
        c1_0.id,
        a1_0.customer_id,
        a1_0.id,
        a1_0.city,
        a1_0.zip_code,
        c1_0.name
    from
        customer c1_0
    left join
        address a1_0
            on c1_0.id=a1_0.customer_id
```


```bash
2025-04-19T12:01:04.388+05:30  INFO 35496 --- [nPlusOne] [nio-8080-exec-2] com.satish.service.CustomerService       : ==========================================================================
2025-04-19T12:01:04.388+05:30  INFO 35496 --- [nPlusOne] [nio-8080-exec-2] com.satish.service.CustomerService       : fetchCustomersWithAddresses() service starts : without N+1 Problem
Hibernate:
    select
        c1_0.id,
        a1_0.customer_id,
        a1_0.id,
        a1_0.city,
        a1_0.zip_code,
        c1_0.name
    from
        customer c1_0
    left join
        address a1_0
            on c1_0.id=a1_0.customer_id
```


### Explanation

```bash
Initially THREE queries are executed ONE is for Parent Data(Customer) and remaining TWO queries for Child Data(Address).
But when I used @EntityGraph and JOIN FETCH query then only ONE query is get executed which fetch the data of Parent as well as of Child.
```


