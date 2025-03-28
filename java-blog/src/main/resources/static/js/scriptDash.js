// Script per mostrare il modal se il post è stato creato
document.addEventListener("DOMContentLoaded", function () {
    const createPostButton = document.querySelector('button[type="submit"][name="createPost"]');
    const postCreatedModal = document.getElementById('postCreatedModal');

    // Mostra il modal solo quando il post è stato creato (aggiungi logica per controllare se il post è creato)
    if (createPostButton && postCreatedModal) {
        createPostButton.addEventListener('click', function () {
            // Mostra il modal dopo il clic sul pulsante
            const myModal = new bootstrap.Modal(postCreatedModal);
            myModal.show();
        });
    }
});
// script per l'input-cat 
document.getElementById('categoryForm').addEventListener('submit', function (e) {
    const input = this.querySelector('.input-cat');
    const clientError = document.getElementById('clientError');
    const serverError = document.getElementById('serverError');

    if (!input.value.trim()) {
        e.preventDefault();
        clientError.style.display = 'block';
        // Nascondi l'errore server, se presente
        if (serverError) {
            serverError.style.display = 'none';
        }
    } else {
        clientError.style.display = 'none';
    }
});

