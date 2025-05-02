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