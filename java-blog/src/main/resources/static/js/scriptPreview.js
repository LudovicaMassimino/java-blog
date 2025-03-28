// script per il modal article preview
window.onload = function () {
    const previewButton = document.getElementById('previewButton');
    console.log(previewButton);
    if (previewButton) {
        previewButton.addEventListener('click', () => {
            // Recupera i valori dai campi del form
            const titleValue = document.getElementById('title').value;
            const bodyValue = document.getElementById('body').value;
            const categorySelect = document.getElementById('category');
            const categoryText = categorySelect.options[categorySelect.selectedIndex].text;

            // Imposta i valori nel modal
            document.getElementById('previewTitle').innerText = titleValue || 'Untitled';
            document.getElementById('previewBody').innerText = bodyValue || 'No content provided.';
            document.getElementById('previewCategory').innerText = categoryText || 'No category selected';

            // Imposta la data corrente formattata come dd/MM/yyyy
            const now = new Date();
            const day = String(now.getDate()).padStart(2, '0');
            const month = String(now.getMonth() + 1).padStart(2, '0');
            const year = now.getFullYear();
            document.getElementById('previewDate').innerText = `${day}/${month}/${year}`;

            // Gestione dell'anteprima dell'immagine
            const imageInput = document.getElementById('imageFile');
            const previewImage = document.getElementById('previewImage');

            if (imageInput.files && imageInput.files[0]) {
                const reader = new FileReader();
                reader.onload = (e) => {
                    previewImage.src = e.target.result;
                };
                reader.readAsDataURL(imageInput.files[0]);
            } else {
                previewImage.src = ''; // oppure imposta un'immagine di default
            }
        });
    }
};