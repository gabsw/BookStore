//let url = 'http://localhost:8080/api/';
let url = 'http://localhost:8080/api/';


function getCurrentUser() {
    return localStorage.getItem('token');
}

function clearUser() {
   localStorage.clear()
}

function setCurrentToken(token) {
    localStorage.setItem('token',token);
}

function getUserInfo() {
    fetch(url + 'session/user-info', {
        method: 'get',
        headers: {
            'x-auth-token': getCurrentUser()
        }
    })
        .then((resp) => resp.json())
        .then((body) => {
            setName(body.username);
            localStorage.setItem('userName',JSON.stringify(body.username));
        } );
};

function setName(name) {
    document.getElementById('name').innerHTML = name;
}

function getUserName() {
    return JSON.parse(localStorage.getItem('userName'));
}
