const cartasJugador = document.querySelectorAll('.cartas-jugador .carta');
const btnCambioCarta = document.querySelector('.cambio-carta-btn');

btnCambioCarta.addEventListener('click', cambiarCarta);

cartasJugador.forEach(carta => {
    carta.addEventListener('dragstart', dragStart);
    carta.addEventListener('dragend', dragEnd);
});

function dragStart() {
    this.classList.add('dragging');
}

function dragEnd() {
    this.classList.remove('dragging');
}

const casillasAreaJuego = document.querySelectorAll('.area-juego > div');

casillasAreaJuego.forEach(casilla => {
    casilla.addEventListener('dragenter', dragEnter);
    casilla.addEventListener('dragleave', dragLeave);
    casilla.addEventListener('drop', drop);
});

function dragEnter(e) {
    e.preventDefault();
    this.classList.add('hovered');
}

function dragLeave() {
    this.classList.remove('hovered');
}

function drop() {
    event.preventDefault();
    const cartaArrastrada = document.querySelector('.dragging');
    cartaArrastrada.classList.remove('dragging');
    this.appendChild(cartaArrastrada);
    this.classList.remove('hovered');
}

function cambiarCarta() {
    const cartaMazo = document.querySelector('.mazo .carta');
    const cartaJugador = document.querySelector('.cartas-jugador .carta');

    if (cartaMazo && cartaJugador) {
        const contenidoCartaJugador = cartaJugador.innerHTML;
        cartaJugador.innerHTML = cartaMazo.innerHTML;
        cartaMazo.innerHTML = contenidoCartaJugador;
    }
}

function back(){
    window.location.href = 'http://127.0.0.1:5500/front_menu/';
}
