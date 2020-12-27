var inputElement = document.querySelector('input[name=input]')
var game_tags = document.getElementById('game_tags')
var cards = document.querySelectorAll('.card')

const game_title = document.getElementById('game_title')
const game_title_label = document.getElementById('game_title_label')
const create_game_button = document.getElementById('create_game_button')
const field_size = document.getElementById('field_size')
const color = document.getElementById('colorselector')
let tagify_search
let tagify_tags


setUpPage()


// functions
function setUpPage() {
	tagify_search = new Tagify(inputElement, {
		whitelist: tags_list.concat(['3*3', '4*4', '5*5']),
		dropdown: {
			maxItems: 13,
			classname: "tags-look",
        	enabled: 1,            
        	closeOnSelect: false
		}
	}),
    tagify_tags = new Tagify(game_tags)
	create_game_button.addEventListener('click', createGame)
	game_title.addEventListener('input', () => {
		game_title_label.className = 'hidden'
	})
	$('#colorselector').colorselector();

	tagify_search.on('add', search)
	      .on('remove', search)
	      .on('edit', search)
}

function search(e) {
	cards.forEach(room => {
		visible = true
		tagify_search.value.forEach(tag => {
			// eсли игровая команата не включает в себя тэг
			if(!room.textContent.toLowerCase().includes(tag.value.toLowerCase().trim())){
				visible = false
			}
		})
		if(visible) {
			room.classList.remove('hidden')
		} else {
			room.classList.add('hidden')
		}
	})
}

async function createGame() {
	if(game_title.value.trim().length > 0) {
		var tags = ''
		tagify_tags.value.forEach(tag => {
			tags += tag.value + ';'
		})
		tags += ';'
		tags = tags.replace(';;', '')
		var query = `/newGame?title=${game_title.value}&tags=${tags}&size=${field_size.value}&color=${color.value}`
		console.log(query)
		var response = await request(query)
		if(response == 1) {
			document.location.href = '/' + game_title.value
		} else {
			game_title_label.className = 'text-danger'
			game_title_label.textContent = 'room with this title already exists'
		}
	} else {
		game_title_label.className = 'text-danger'
		game_title_label.textContent = 'this field is required'
	}	
}

async function request(url, method = 'GET', data = null){
	try {
		const headers = {}
		let body
		if(data){
			headers['Content-Type'] = 'application/json'
			body = JSON.stringify(data)
		}
		const response = await fetch(url, {
			method,
			headers,
			body
		})
		return await response.json()
	}
	catch(e) {
		console.log(e)
	}
}