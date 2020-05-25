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