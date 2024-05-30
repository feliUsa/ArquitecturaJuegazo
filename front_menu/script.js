function getCookie(name) {
    var nameEQ = name + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) === ' ') c = c.substring(1, c.length);
        if (c.indexOf(nameEQ) === 0) return c.substring(nameEQ.length, c.length);
    }
    return null;
}

function checkJwtCookie() {
    var jwt = getCookie("jwt");
    if (!jwt) {
        window.location.href = 'http://127.0.0.1:5500/Log_in/'; // Redirige a la página de inicio de sesión si no hay JWT
    } else {
        console.log("JWT presente: ", jwt);
        // Aquí puedes agregar más lógica para validar el JWT si es necesario
    }
}

function logout() {
    deleteCookie("jwt"); // Borra la cookie jwt
    window.location.href = 'http://127.0.0.1:5500/Log_in/'; // Redirige a la página de inicio de sesión
}

function deleteCookie(name) {
    document.cookie = name + '=; Max-Age=-99999999; path=/';
}

function config(){
    window.location.href = 'http://127.0.0.1:5500/configuracion/';
}

function search(){
    window.location.href = 'http://127.0.0.1:5500/mesa/';
}

window.onload = checkJwtCookie;