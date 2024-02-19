const validateData = require("./validateData");

test ("Returns false if data is invalid", () => {
    const name = 'Garrett';
    const customer = 'JimmyJohns';
    const description = 'First install of the deck';
    expect(validateData(name, customer, description)).toBe(true);
});