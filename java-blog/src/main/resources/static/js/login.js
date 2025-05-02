// Toggle mostra/nascondi password

document.addEventListener('DOMContentLoaded', function () {
    // Toggle visibilità della password nel form di login
    const togglePasswordLogin = document.getElementById('togglePasswordLogin');
    const passwordFieldLogin = document.getElementById('password');
  
    if (togglePasswordLogin && passwordFieldLogin) {
      togglePasswordLogin.addEventListener('click', function (e) {
        e.stopPropagation(); // Impedisce che l'evento si propaghi al dropdown
        const type = passwordFieldLogin.getAttribute('type') === 'password' ? 'text' : 'password';
        passwordFieldLogin.setAttribute('type', type);
  
        // Cambia l'icona
        this.classList.toggle('bi-eye');
        this.classList.toggle('bi-eye-slash');
      });
    }
  });