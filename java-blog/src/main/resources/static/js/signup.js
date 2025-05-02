// Script per la registrazione
document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('signUpForm').addEventListener('submit', function (e) {
        e.preventDefault(); // blocca l'invio classico

        const formData = new FormData(this);

        fetch(this.getAttribute('action'), {
            method: 'POST',
            body: formData
        })
            .then(response => {
                if (response.ok) {
                    // supponiamo che il server restituisca 200 ok per successo
                    showResult(true);
                } else {
                    // altrimenti errore
                    showResult(false);
                }
            })
            .catch(error => {
                console.error('Errore di rete:', error);
                showResult(false);
            });
    });

    function showResult(isSuccess) { // Nascondi il form
        document.getElementById('signUpForm').classList.add('d-none');

        // Mostra il container del messaggio
        const messageContainer = document.getElementById('messageContainer');
        messageContainer.classList.remove('d-none');

        // Gestisci GIF e messaggi
        const statusGif = document.getElementById('statusGif');
        const successMessage = document.getElementById('successMessage');
        const errorMessage = document.getElementById('errorMessage');

        if (isSuccess) {
            statusGif.src = '/img/signUpSuccess.gif'; // GIF successo
            successMessage.classList.remove('d-none');
            errorMessage.classList.add('d-none');
        } else {
            statusGif.src = '/img/signUpError.gif'; // GIF errore
            successMessage.classList.add('d-none');
            errorMessage.classList.remove('d-none');
        }
    }

    // -----------------------------
    // Toggle mostra/nascondi password
    // -----------------------------
    const togglePassword = document.getElementById('togglePassword');
    const passwordField = document.getElementById('password1');

    if (togglePassword && passwordField) {
        togglePassword.addEventListener('click', function () {
            console.log('Icon clicked!');
            const type = passwordField.getAttribute('type') === 'password' ? 'text' : 'password';
            passwordField.setAttribute('type', type);

            console.log('Password type is now: ' + passwordField.getAttribute('type')); // Dovrebbe stampare 'text' quando visibile

            // Cambia l'icona
            this.classList.toggle('bi-eye');
            this.classList.toggle('bi-eye-slash');
        });
    }
});