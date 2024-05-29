// Función para obtener el valor de una cookie por su nombre
function getCookieValue(cookieName) {
    const cookies = document.cookie.split(';');
    for (let i = 0; i < cookies.length; i++) {
        const cookie = cookies[i].trim();
        if (cookie.startsWith(cookieName + '=')) {
            return cookie.substring(cookieName.length + 1);
        }
    }
    return null;
}

// Obtener el elemento del DOM donde se mostrará el nombre de usuario actual
const currentUsernameInput = document.getElementById('currentUsername');

// Obtener el elemento del DOM donde se mostrará el email actual
const currentEmailInput = document.getElementById('currentEmail');

// URL del endpoint
const endpointUrl = 'http://localhost:8080/users/me';

// Obtener el token JWT de la cookie
const jwtToken = getCookieValue('jwt');

// Configurar la cabecera de autorización con el token JWT
const headers = {
    'Authorization': `Bearer ${jwtToken}`,
    'Content-Type': 'application/json'
};

// Realizar la petición GET utilizando fetch
fetch(endpointUrl, {
    method: 'GET',
    headers: headers
})
.then(response => {
    if (!response.ok) {
        throw new Error('Error en la petición.');
    }
    return response.json();
})
.then(data => {
    // Manejar los datos obtenidos
    console.log('Datos del usuario:', data);
    // Mostrar los datos del usuario en los campos de entrada de texto
    currentUsernameInput.value = data.username;
    currentEmailInput.value = data.email;
})
.catch(error => {
    console.error('Error:', error);
});