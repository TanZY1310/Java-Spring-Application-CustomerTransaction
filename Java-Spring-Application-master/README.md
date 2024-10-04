# Java-Spring-Application

A simple web application using Java Spring services that allows user to read in data and store in database using Spring Batch and perform Pagination and 
Search Filtering on the item stored in database

Running the application for the first time

Create a MySQL table - using MySQL Workbench
a. Run query from schema.sql 
b. Run query from run.sql

Insert data from txt to database http://localhost:8080/batch/run (Execute link in browser or postman application)

To list out all the data from the database http://localhost:8080/batch/customers

To list out customer based on pagination and search filtering 
a. Search based on customer ID (Current ID available are 222 and 333) 
  http://localhost:8080/batch/customers/searchid?custID=222/333

b. Search based on description (Have to follow exactly as stored in database eg: FUND TRANSFER) 
  http://localhost:8080/batch/customers/searchdesc?description=BILL PAYMENT/FUND TRANSFER/ATM WITHDRWAL

Can also filter by page number and the number of items listed 
eg: http://localhost:8080/batch/customers/searchid?custID=222&page=2&size=10 (will list out 10 items with custID-222 on page 2)
