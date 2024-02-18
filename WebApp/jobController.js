const express = require('express');
const app = express();
const cors = require('cors');
const dotenv = require('dotenv');
dotenv.config();

const dbService = require('./dbService');

class JobController {
    constructor() {
        this.db = dbService.getDbServiceInstance();
    }
    insertJob(name, customer, description) {
        return this.db.insertNewName(name, customer, description);
    }
    getAllJobs() {
        return this.db.getAllData();
    }
    updateJob(id, name){
        return this.db.updateNameById(id, name);
    }
    deleteJob(id) {
        return this.db.deleteRowById(id);
    }
    searchJobByName(name) {
        return this.db.searchByName(name);
    }
    reportByCustomer(customer) {
        return this.db.reportByCustomer(customer);
    }
}

const jobController = new JobController();

app.use(cors());
app.use(express.json());
app.use(express.urlencoded({extended : false}));

//New OOP Create
app.post('/insert', (request, response) => {
    const { name, customer, description } = request.body;
    jobController.insertJob(name, customer, description)
    .then(data => response.json({ data }))
    .catch(err => console.log(err));
});

//New OOP Read
app.get('/getAll', (request, response) => {
    jobController.getAllJobs()
    .then(data => response.json({ data }))
    .catch(err => console.log(err));
});

//New OOP Update
app.patch('/update', (request, response) => {
    const { id, name } = request.body;
    jobController.updateJob(id, name)
    .then (success => response.json({ success }))
    .catch(err => console.log(err));
});

//New OOP Delete
app.delete('/delete/:id', (request, response) => {
    const { id } = request.params;
    jobController.deleteJob(id)
    .then(success => response.json({ success }))
    .catch(err => console.log(err));
});

//New OOP Search
app.get('/search/:name', (request, response) => {
    const { name } = request.params;
    jobController.searchJobByName(name)
    .then(data => response.json({ data }))
    .catch(err => console.log(err));
});

//New OOP Report
app.get('/report/:customer', (request, response) => {
    const { customer } = request.params;
    jobController.reportByCustomer(customer)
    .then(data => response.json({ data }))
    .catch(err => console.log(err));
});

app.listen(process.env.PORT, () => console.log('App is running'));

//module.exports = reportByCustomer;
