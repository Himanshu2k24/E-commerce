document.addEventListener('DOMContentLoaded', function() {
    // Register form validation
    document.getElementById('registerForm').addEventListener('submit', function(event) {
        event.preventDefault();
        var username = document.getElementById('username');
        var email = document.getElementById('email');
        var password = document.getElementById('password');
        var isValid = true;

        // Username validation
        if (!username.checkValidity()) {
            username.classList.add('is-invalid');
            isValid = false;
        } else {
            username.classList.remove('is-invalid');
        }

        // Email validation
        if (!email.checkValidity()) {
            email.classList.add('is-invalid');
            isValid = false;
        } else {
            email.classList.remove('is-invalid');
        }

        // Password validation
        if (!password.checkValidity() || password.value.length < 8) {
            password.classList.add('is-invalid');
            isValid = false;
        } else {
            password.classList.remove('is-invalid');
        }

        // If everything is valid, submit the form
        if (isValid) {
            this.submit();
        }
    });
});
