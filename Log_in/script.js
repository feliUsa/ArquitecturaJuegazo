/**
 *
 */

function sign_up() {
	var inputs = document.querySelectorAll('.input_form_sign');
	document.querySelectorAll('.ul_tabs > li')[0].className = "";
	document.querySelectorAll('.ul_tabs > li')[1].className = "active";

	for (var i = 0; i < inputs.length; i++) {
		if (i == 2) {

		} else {
			document.querySelectorAll('.input_form_sign')[i].className = "input_form_sign d_block";
		}
	}

	setTimeout(function () {
		for (var d = 0; d < inputs.length; d++) {
			document.querySelectorAll('.input_form_sign')[d].className = "input_form_sign d_block active_inp";
		}


	}, 100);
	document.querySelector('.link_forgot_pass').style.opacity = "0";
	document.querySelector('.link_forgot_pass').style.top = "-5px";
	document.querySelector('.btn_sign').innerHTML = "SIGN UP";
	setTimeout(function () {

		document.querySelector('.terms_and_cons').style.opacity = "1";
		document.querySelector('.terms_and_cons').style.top = "5px";

	}, 500);
	setTimeout(function () {
		document.querySelector('.link_forgot_pass').className = "link_forgot_pass d_none";
		document.querySelector('.terms_and_cons').className = "terms_and_cons d_block";
	}, 450);

}



function sign_in() {
	var inputs = document.querySelectorAll('.input_form_sign');
	document.querySelectorAll('.ul_tabs > li')[0].className = "active";
	document.querySelectorAll('.ul_tabs > li')[1].className = "";

	for (var i = 0; i < inputs.length; i++) {
		switch (i) {
			case 1:
				console.log(inputs[i].name);
				break;
			case 2:
				console.log(inputs[i].name);
			default:
				document.querySelectorAll('.input_form_sign')[i].className = "input_form_sign d_block";
		}
	}

	setTimeout(function () {
		for (var d = 0; d < inputs.length; d++) {
			switch (d) {
				case 1:
					console.log(inputs[d].name);
					break;
				case 2:
					console.log(inputs[d].name);

				default:
					document.querySelectorAll('.input_form_sign')[d].className = "input_form_sign d_block";
					document.querySelectorAll('.input_form_sign')[2].className = "input_form_sign d_block active_inp";

			}
		}
	}, 100);

	document.querySelector('.terms_and_cons').style.opacity = "0";
	document.querySelector('.terms_and_cons').style.top = "-5px";

	setTimeout(function () {
		document.querySelector('.terms_and_cons').className = "terms_and_cons d_none";
		document.querySelector('.link_forgot_pass').className = "link_forgot_pass d_block";

	}, 500);

	setTimeout(function () {

		document.querySelector('.link_forgot_pass').style.opacity = "1";
		document.querySelector('.link_forgot_pass').style.top = "5px";


		for (var d = 0; d < inputs.length; d++) {

			switch (d) {
				case 1:
					console.log(inputs[d].name);
					break;
				case 2:
					console.log(inputs[d].name);

					break;
				default:
					document.querySelectorAll('.input_form_sign')[d].className = "input_form_sign";
			}
		}
	}, 1500);
	document.querySelector('.btn_sign').innerHTML = "SIGN IN";
}


window.onload = function () {
	document.querySelector('.cont_centrar').className = "cont_centrar cent_active";

}

function handleValidInput() {
	var username = $('input[name="username_us"]').val();
	var email = $('input[name="email_us"]').val();
	var password = $('input[name="pass_us"]').val();

	if (username !== "" && email !== "" && password !== "") {
		var userData = {
			"username": username,
			"password": password,
			"email": email,
			"roleRequest": {
				"roleListName": [
					"USER"
				]
			}
		};

		console.log("JSON enviado:", JSON.stringify(userData));

		$.ajax({
			url: 'http://localhost:8080/auth/register',
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(userData),
			success: function (data) {
				console.log('Registro exitoso:', data);
				if (data.jwt) {
					setCookie("jwt", data.jwt, 7); // Guarda el JWT en una cookie por 7 días
					window.location.href = 'http://127.0.0.1:5500/front_menu/'; // Redirige al usuario
				}
			},
			error: function (xhr, status, error) {
				console.error('Error al registrar:', error);
			}
		});
	} else {
		handleInvalidInput();
	}
}

function handleInvalidInput(){
	var username = $('input[name="username_us"]').val();
	var password = $('input[name="pass_us"]').val();

	var userData = {
		"username": username,
		"password": password
	}

	console.log("JSON enviado:", JSON.stringify(userData));
	$.ajax({
        url: 'http://localhost:8080/auth/log-in',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(userData),
        success: function (data) {
            console.log('logeo exitoso:', data);
            if (data.jwt) {
                setCookie("jwt", data.jwt, 7); // Guarda el JWT en una cookie por 7 días
				window.location.href = 'http://127.0.0.1:5500/front_menu/'; // Redirige al usuario
            }
        },
        error: function (xhr, status, error) {
            console.error('Error al logear:', error);
        }
    });
}

function setCookie(name, value, days) {
    var expires = "";
    if (days) {
        var date = new Date();
        date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
        expires = "; expires=" + date.toUTCString();
    }
    document.cookie = name + "=" + (value || "") + expires + "; path=/";
}

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
    if (jwt) {
        window.location.href = 'http://127.0.0.1:5500/front_menu/'; // Redirige a la página de inicio de sesión si no hay JWT
    } else {
        console.log("JWT presente: ", jwt);
        // Aquí puedes agregar más lógica para validar el JWT si es necesario
    }
}

window.onload = checkJwtCookie;






