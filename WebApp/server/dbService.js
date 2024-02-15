const mysql = require('mysql');
const dotenv = require('dotenv');
let instance = null;
dotenv.config();

const connection = mysql.createConnection({
    host: process.env.HOST,
    user: process.env.USER,
    password: process.env.PASSWORD,
    database: process.env.DATABASE,
    port: process.env.DB_PORT
});

connection.connect((err) => {
    if (err) {
        console.log(err.message);

    }
    //console.log('db ' + connection.state);
});

class DbService {
    static getDbServiceInstance(){
        return instance ? instance : new DbService();
    }
    async getAllData() {
        try{
            const response = await new Promise((resolve, reject) => {
                //const query = "SELECT * FROM jobs WHERE id = ?"
                const query = "SELECT * FROM jobs";
                connection.query(query, (err, results) => {
                    if (err) reject(new Error(err.message));
                    resolve(results);
                })
            });
            return response;
        } catch (error) {
            console.log(error);
        }
    }

    async insertNewName(name, customer, description) {
        try {
            const dateAdded = new Date();
            //const customer = customer;
            //if (name === "" || customer === "" || description === "") {
            //    alert("Please enter a job name");
            //}
            //else{
            const insertId = await new Promise((resolve, reject)=> {
                const query = "INSERT INTO jobs (name, date_added, customer, description) VALUES (?,?,?,?);";
                connection.query(query, [name, dateAdded, customer, description], (err, result) => {
                    if (err) reject(new Error(err.message));
                    resolve(result.insertId);
                })
            });
            //}
            return {
                id: insertId,
                name: name,
                dateAdded: dateAdded,
                //customer: customer,
                //description: description
            }

        } catch (error) {
            console.log(error);
        }
    }
    async deleteRowById(id) {
        try {
            id = parseInt(id, 10);
            const response = await new Promise((resolve, reject)=> {
                //const query = "SELECT * FROM jobs WHERE id = ?"
                const query = "DELETE FROM jobs WHERE id = ?";

                connection.query(query, [id], (err, result) => {
                    if (err) reject(new Error(err.message));
                    resolve(result.affectedRows);
                })
            });

            return response === 1 ? true : false;
        } catch (error) {
            console.log(error);
            return false;
        }
    }

    async updateNameById(id, name){
        try {
            id = parseInt(id, 10);
            const response = await new Promise((resolve, reject) => {
                const query = "UPDATE jobs SET name = ? WHERE id = ?";

                connection.query(query, [name, id], (err, result) => {
                    if (err) reject(new Error(err.message));
                    resolve(result.affectedRows);
                })
            });

            return response === 1 ? true : false;
        } catch (error) {
            console.log(error);
            return false;
        }
    }

    async searchByName(name) {
        try{
            const response = await new Promise((resolve, reject) => {
                //const query = "SELECT * FROM jobs WHERE id = ?"
                const query = "SELECT * FROM jobs WHERE name = ?";
                connection.query(query, [name], (err, results) => {
                    if (err) reject(new Error(err.message));
                    resolve(results);
                })
            });

            return response;
        } catch (error) {
            console.log(error);
        }
    }

    async reportByCustomer(customer) {
        try{
            const response = await new Promise((resolve, reject) => {
                const query = "SELECT * FROM jobs WHERE customer = ?";
                connection.query(query, [customer], (err, results) => {
                    if (err) reject(new Error(err.message));
                    resolve(results);
                    console.log(results);
                })
                
            });

            return response;
        } catch (error) {
            console.log(error);
        }
    }
}




module.exports = DbService;
