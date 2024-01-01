// Function to calculate age based on date of birth
function calculateAge(dob) {
    const today = new Date();
    const birthDate = new Date(dob);
    let age = today.getFullYear() - birthDate.getFullYear();
    const monthDiff = today.getMonth() - birthDate.getMonth();

    if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
        age--;
    }
    return age;
}

// Function to validate email format
function validateEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

// Function to validate the form
function validateForm() {
    const form = document.querySelector('.register-form');
    const dobInput = form.elements['dob'];
    const emailInput = form.elements['email'];

    dobInput.addEventListener('change', function () {
        const age = calculateAge(this.value);
        if (age < 18) {
            alert('You must be at least 18 years old to register.');
            this.value = ''; // Clear the date input
        }
    });

    form.addEventListener('submit', function (event) {
        if (!validateEmail(emailInput.value)) {
            alert('Please enter a valid email address.');
            event.preventDefault(); // Prevent form submission
        }
    });
}

// Function to validate password
function validatePassword() {
    var password = document.querySelector('input[name="password"]');
    var confirmPassword = document.querySelector('input[name="confirmPassword"]');
    var submitButton = document.querySelector('input[type="submit"]');

    function validate() {
        if (password.value !== confirmPassword.value) {
            confirmPassword.setCustomValidity("Passwords don't match");
        } else {
            confirmPassword.setCustomValidity('');
        }
    }

    password.addEventListener('change', validate);
    confirmPassword.addEventListener('keyup', validate);
    confirmPassword.addEventListener('change', validate);

    submitButton.addEventListener('click', function(event) {
        if (password.value !== confirmPassword.value) {
            event.preventDefault();
        }
    });
}


function initialize() {
    validateForm();
    validatePassword();
}

window.onload = function() {
    initialize();
};