document.addEventListener('DOMContentLoaded', function(){
    fetch('http://localhost:5000/getAll')
    .then(response => response.json())
    .then(data => loadHTMLTable(data['data']));
    
});

function validate(){
    if (document.forms.name.value.trim() === ""){
        alert("Please enter a job name");
        document.forms.name.focus();
        return false;
    }
    return true;
}

document.querySelector('table tbody').addEventListener('click', function(event) {
    const previouslyHighlightedRow = document.querySelector('.highlighted-row');
    if (previouslyHighlightedRow) {
        previouslyHighlightedRow.classList.remove('highlighted-row');
    }

    if (event.target.className === "delete-row-btn") {
        deleteRowById(event.target.dataset.id);
    }
    if (event.target.className === "edit-row-btn") {
        const row = event.target.closest('tr');
        row.classList.add('highlighted-row');
        handleEditRow(event.target.dataset.id);
    }
}
);

const updateBtn = document.querySelector('#update-row-btn');
const searchBtn = document.querySelector('#search-btn');
const reportBtn = document.querySelector('#report-btn');

//Search button
searchBtn.onclick = function () {
    const searchValue = document.querySelector('#search-input').value;

    fetch('http://localhost:5000/search/' + searchValue)
    .then(response => response.json())
    .then(data => loadHTMLTable(data['data']));
}

//Report button
reportBtn.onclick = function () {
    const reportCustomer = document.querySelector('#report-input').value;
    handleReportRow();

    fetch('http://localhost:5000/report/' + reportCustomer)
    .then(response => response.json())
    .then(data => loadReportTable(data['data']));

}


function deleteRowById(id) {
    fetch('http://localhost:5000/delete/' + id, {
        method: 'DELETE'
    })
    .then(response => response.json())
    .then(data => {
        if (data.success){
            //location.reload();
            refresh();
        }
    });
}

function handleEditRow(id) {
    const updateSection = document.querySelector('#update-row');
    updateSection.hidden = false;
    document.querySelector('#update-name-input').dataset.id = id;
}

updateBtn.onclick = function() {
    const updatedNameInput = document.querySelector('#update-name-input');

    //console.log(updateNameInput);

    fetch('http://localhost:5000/update', {
        method: 'PATCH',
        headers: {
            'Content-type' : 'application/json'
        },
        body: JSON.stringify({
            id: updatedNameInput.dataset.id,
            name: updatedNameInput.value
        })
    })
    .then(response => response.json())
    .then(data => {
        if(data.success) {
            location.reload();
        }
    })
}

const addBtn = document.querySelector('#add-name-btn');

addBtn.onclick = function () {
    const nameInput = document.querySelector('#name-input');
    const name = nameInput.value;
    nameInput.value = "";
    const customerInput = document.querySelector('#customer-input');
    const customer = customerInput.value;
    customerInput.value = "";
    const descriptionInput = document.querySelector("#description-input");
    const description = descriptionInput.value;
    descriptionInput.value = "";
    //Input validation to eliminate missing fields below:
    if (name === "" || customer === "" || description === ""){
        alert("Please complete all fields");
        return false;
    }
    else{
    fetch('http://localhost:5000/insert', {
        headers: {
            'Content-type': 'application/json'

        },
        method: 'POST',
        body: JSON.stringify({name : name, customer : customer, description : description})
    })
    .then(response => response.json())
    .then(data => insertRowIntoTable(data['data']))
    ;
    }
    refresh();
}

function insertRowIntoTable(data) {
    const table = document.querySelector('table tbody');
    const isTableData = table.querySelector('.no-data');

    let tableHtml = "<tr>";

    for (var key in data) {
        if (data.hasOwnProperty(key)) {
            if (key === 'dateAdded') {
                data[key] = new Date(data[key]).toLocaleString();
                data.customer = new String(data.customer).toString();
                data.description = new String(data.description).toString();
            }
            tableHtml += `<td>${data[key]}</td>`;
        }
    }
    tableHtml += `<td>${data.customer}</td>`;
    tableHtml += `<td>${data.description}</td>`;
    tableHtml += `<td><button class="delete-row-btn" data-id=${data.id}>Delete</td>`;
    tableHtml += `<td><button class="edit-row-btn" data-id=${data.id}>Edit</td>`;

    tableHtml += "</tr>";

    if (isTableData) {
        table.innerHTML = tableHtml;

    } else {
        const newRow = table.insertRow();
        newRow.innerHTML = tableHtml;
    }
}

function loadHTMLTable(data){
    const table = document.querySelector('table tbody');
    //console.log(data);
    
    if (data.length === 0){
        table.innerHTML = "<tr><td class='no-data' colspan='5'>No Data</td></tr>";
        return;
    }
    let tableHtml = "";

    data.forEach(function ({id, name, date_added, customer, description}) {
        tableHtml += "<tr>";
        tableHtml += `<td>${id}</td>`;
        tableHtml += `<td>${name}</td>`;
        tableHtml += `<td>${new Date(date_added).toLocaleString()}</td>`;
        tableHtml += `<td>${customer}</td>`;
        tableHtml += `<td>${description}</td>`;
        tableHtml += `<td><button class="delete-row-btn" data-id=${id}>Delete</td>`;
        tableHtml += `<td><button class="edit-row-btn" data-id=${id}>Edit</td>`;
        
        tableHtml += "</tr>";
    });

    table.innerHTML = tableHtml;

    refresh();
}

function loadReportTable(data){
    const reportTable = document.querySelector('table.report-table tbody');

    if (data.length === 0){
        reportTable.innerHTML = "<tr><td class='no-data' colspan='5'>No Data</td></tr>";
        return;
    }
    let reportTableHtml = "";
    //console.log(data);
    data.forEach(function ({id, name, date_added, customer, description}) {
        reportTableHtml += "<tr>";
        reportTableHtml += `<td>${id}</td>`;
        reportTableHtml += `<td>${name}</td>`;
        reportTableHtml += `<td>${new Date(date_added).toLocaleString()}</td>`;
        reportTableHtml += `<td>${customer}</td>`;
        reportTableHtml += `<td>${description}</td>`;
        
        reportTableHtml += "</tr>";
    });

    reportTable.innerHTML = reportTableHtml;
}

function handleReportRow() {
    const reportSection = document.querySelector('#report-row');
    reportSection.hidden = false;
    //document.querySelector('#report-input').dataset.customer = customer;
}

//function refresh(){
 //   fetch('http://localhost:5000/getAll')
 //   .then(response => response.json())
  //  .then(data => loadHTMLTable(data['data']));
  //  return;
//}