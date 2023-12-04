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

window.onload = function() {
    validatePassword();
};
