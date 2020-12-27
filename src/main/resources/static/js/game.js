var field = []
const move_index = document.getElementById('move_index')
const modal_window = document.getElementById('modal_window')
const ok_button = document.getElementById('ok_button')
const NOUGHT = -1
const CROSS = 1
let stompClient
var connected = false

for(var i = 0; i < field_size; i++) {
	field.push(document.getElementById('row' + i).querySelectorAll('div'))
	field[i].forEach(el => el.addEventListener('click', () => {
		stompClient.send("/app/chat", {}, JSON.stringify({
			'roomId': room_id,
			'x': el.getAttribute('x'),
			'y': el.getAttribute('y'),
			'move': player_role
		}))
	}))
}

connect()

async function connect() {
    var socket = new SockJS('/cross-nought-game');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        connected = true;
        console.log('Connected: ' + frame);
        stompClient.subscribe('/room/' + room_id + '/queue/messages', function (data) {
            var message = JSON.parse(data.body)
            if(message.move == NOUGHT) {
            	field[message.y][message.x].classList.add('type-1')
            	move_index.innerHTML = '<i class="fas fa-times cross"></i>'
            } else if(message.move == CROSS) {
            	field[message.y][message.x].classList.add('type1')
            	move_index.innerHTML = '<i class="far fa-circle nought"></i>'
            } else {
            	showWinner(message)
            }
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    connected = false;
    console.log("Disconnected");
    connect()
}

function showWinner(message) {
    if(message == 0) {
        modal_window.querySelector('.modal-body').innerHTML = "Tie!"
    } else {
        modal_window.querySelector('.modal-body').innerHTML = message == player_role ? "You win!" : "You loosed :c"
    }
    console.log(message)
    $('#modal_window').modal({
        dackdrop: true,
        show: true
    })
    ok_button.addEventListener('click', () => {
        document.location.href = '/'
    })
}