const { validate } = require('./index.js');

describe('Validate function', () => {
    document.forms = {
        name: { value: ''}
    };

    expect(validate()).toBe(false);
    expect(window.alert).toHaveBeenCalledWith('Please enter a job name');
});

test('Should return true if the name field is not empty', () => {
    document.forms = {
        name: { value: 'Test job' }

    };
    expect(validate()).toBe(true);
    expect(window.alert).not.toHaveBeenCalledWith();
});
