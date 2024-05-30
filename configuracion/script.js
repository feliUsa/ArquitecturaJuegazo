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



//fucnion para validar si la cookie existe
function checkJwtCookie() {
    var jwt = getCookieValue("jwt");
    if (!jwt) {
        window.location.href = 'http://127.0.0.1:5500/Log_in/'; // Redirige a la página de inicio de sesión si no hay JWT
    } else {
        console.log("JWT presente: ", jwt);
        // Aquí puedes agregar más lógica para validar el JWT si es necesario
    }
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


    function actualizarDatos() {
        const username = $('input[name="username"]').val().trim();
        const email = $('input[name="email"]').val().trim();
        const password = $('input[name="password"]').val().trim();
    
        // Check if password is provided
        if (!password) {
            alert('Password is required');
            return;
        }
    
        const token = getCookieValue('jwt'); // Assuming this function is defined somewhere to get the cookie value
    
        const payload = {
            username: username || "", // If username is empty, it will be an empty string
            email: email || "", // If email is empty, it will be an empty string
            password: password,
            roleRequest: {
                roleListName: ["USER"]
            }
        };
    
        fetch('http://localhost:8080/users/update', {
            method: 'PUT', // Change to PUT
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(payload)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            if (data.jwt) {
                document.cookie = `jwt=${data.jwt}; path=/`; // Update the jwt cookie with the new token
            }
    
            alert('User updated successfully');
            window.location.reload(); // Reload the page after the alert is closed
            console.log(data); // Just for debugging, remove or comment this line in production
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
            alert('Error updating user');
        });
    }


    function bottomRightButtonFunction(){
        window.location.href = 'http://127.0.0.1:5500/front_menu/'
    }



window.onload = checkJwtCookie;