// Base URL for the API
const BASE_URL = "http://localhost:8080/batch/customers";

// Pagination variables
//let currentPage = 0;
//let totalPages = 1;  // Initialize total pages as 1 to start

//Function to display all customer transactions
async function displayAllCustomer(page) {
    const resultsDiv = document.getElementById('results');
    resultsDiv.innerHTML = ''; // Clear previous results
    try {
        const response = await fetch(`${BASE_URL}`);
        const data = await response.json();

        if (response.ok) {
            totalPages = data.totalPages; // Update total pages
            currentPage = data.currentPage;
            updatePagination();
            displayResults(data.content);
        } else {
            resultsDiv.innerHTML = `<p class="error-message">Error: ${data.message || 'An error occurred'}</p>`;
        }
    } catch (error) {
        resultsDiv.innerHTML = `<p class="error-message">Error: ${error.message}</p>`;
    }
}

// Function to search by customer ID
async function searchById(page) {
    const custID = document.getElementById('searchId').value;
    const resultsDiv = document.getElementById('results');
    resultsDiv.innerHTML = ''; // Clear previous results
    try {
        const response = await fetch(`${BASE_URL}/searchid?custID=${custID}`);
        const data = await response.json();

        if (response.ok) {
            totalPages = data.totalPages; // Update total pages
            currentPage = data.currentPage;
            updatePagination();
            displayResults(data.customers);
        } else {
            resultsDiv.innerHTML = `<p class="error-message">Error: ${data.message || 'An error occurred'}</p>`;
        }
    } catch (error) {
        resultsDiv.innerHTML = `<p class="error-message">Error: ${error.message}</p>`;
    }
}

// Function to search by description
async function searchByDescription(page) {
    const description = document.getElementById('searchDescription').value;
    const resultsDiv = document.getElementById('results');
    resultsDiv.innerHTML = ''; // Clear previous results
    try {
        const response = await fetch(`${BASE_URL}/searchdesc?description=${description}`);
        const data = await response.json();

        if (response.ok) {
            totalPages = data.totalPages; // Update total pages
            currentPage = data.currentPage;
            updatePagination();
            displayResults(data.customers);
        } else {
            resultsDiv.innerHTML = `<p class="error-message">Error: ${data.message || 'An error occurred'}</p>`;
        }
    } catch (error) {
        resultsDiv.innerHTML = `<p class="error-message">Error: ${error.message}</p>`;
    }
}

// Function to display results in a table format
function displayResults(customers) {
    const resultsDiv = document.getElementById('results');

    if (customers.length === 0) {
        resultsDiv.innerHTML = '<p>No customers found.</p>';
        return;
    }

    let table = '<table>';
    table += '<tr><th>Transaction ID</th><th>Customer ID</th><th>Account Number</th><th>Description</th><th>Amount</th><th>Date</th><th>Time</th></tr>';
    customers.forEach(customer => {
        table += `<tr>
                    <td>${customer.trx_id}</td>
                    <td>${customer.custID}</td>
                    <td>${customer.acc_number}</td>
                    <td>${customer.description}</td>
                    <td>${customer.trx_amount}</td>
                    <td>${customer.trx_date}</td>
                    <td>${customer.trx_time}</td>
                  </tr>`;
    });
    table += '</table>';
    resultsDiv.innerHTML = table;
}

// Update pagination controls based on the current page
function updatePagination() {
    document.getElementById('currentPage').innerText = `Page ${currentPage + 1}`;
    document.getElementById('prevButton').disabled = currentPage === 0;
    document.getElementById('nextButton').disabled = currentPage >= totalPages - 1;
}

// Go to the previous page
function prevPage() {
    if (currentPage > 0) {
        const searchId = document.getElementById('searchId').value;
        const searchDescription = document.getElementById('searchDescription').value;
        if (searchId) {
            searchById(currentPage - 1);
        } else if (searchDescription) {
            searchByDescription(currentPage - 1);
        } else {
            displayAllCustomer(currentPage - 1);
        }
    }
}

// Go to the next page
function nextPage() {
    if (currentPage < totalPages - 1) {
        const searchId = document.getElementById('searchId').value;
        const searchDescription = document.getElementById('searchDescription').value;
        if (searchId) {
            searchById(currentPage + 1);
        } else if (searchDescription) {
            searchByDescription(currentPage + 1);
        } else {
            displayAllCustomer(currentPage + 1);
        }
    }
}
