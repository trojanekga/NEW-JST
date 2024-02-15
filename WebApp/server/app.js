const express = require('express');
const app = express();
const cors = require('cors');
const dotenv = require('dotenv');
dotenv.config();

const dbService = require('./dbService');

class Job {
    constructor(name, dateAdded, customer) {
        this.name = name;
        this.dateAdded = dateAdded;
        this.customer = customer;
    }
}

class SubJob extends Job {
    constructor(name, dateAdded, customer, subJob){
        super(name, dateAdded, customer);
        this.subJob = subJob;
        this.dateAdded = dateAdded -5;
    }
}


app.use(cors());
app.use(express.json());
app.use(express.urlencoded({extended : false}));

// Create
app.post('/insert', (request, response) => {
    const { name } = request.body;
    const { customer } = request.body;
    const { description } = request.body;
    const db = dbService.getDbServiceInstance();
    const result = db.insertNewName(name, customer, description);

    result
    .then(data => response.json({ data: data}))
    .catch(err => console.log(err));
});

// Read
app.get('/getAll', (request, response) => {
    const db = dbService.getDbServiceInstance();

    const result = db.getAllData();

    result
    .then(data => response.json({data : data}))
    .catch(err => console.log(err));
    
})

// Update
app.patch('/update', (request, response) => {
    const { id, name } = request.body;
    const db = dbService.getDbServiceInstance();

    const result = db.updateNameById(id, name);

    result
    .then(data => response.json({success : data}))
    .catch(err => console.log(err));

});



// Delete
app.delete('/delete/:id', (request, response) => {
    const { id } = request.params;
    const db = dbService.getDbServiceInstance();

    const result = db.deleteRowById(id);

    result
    .then(data => response.json({success : data}))
    .catch(err => console.log(err));
})

//Search
app.get('/search/:name', (request, response) => {
    const { name } = request.params;
    const db = dbService.getDbServiceInstance();
    const result = db.searchByName(name);

    result
    .then(data => response.json({data : data}))
    .catch(err => console.log(err));

})

//Report
app.get('/report/:customer', (request, response) => {
    const { customer } = request.params;
    const db = dbService.getDbServiceInstance();
    const result = db.reportByCustomer(customer);

    result
    .then(data => response.json({data : data}))
    .catch(err => console.log(err));
})


app.listen(process.env.PORT, () => console.log('App is running'));
