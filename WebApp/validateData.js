function validateData (name, customer, description) {
    if (name === "" || customer === "" || description === ""){
        //alert("Please complete all fields");
        return false;
    }
    else if (description.length < 5){
        return false;
    }
    else {
        return true;
    }
};

module.exports = validateData;