let url = 'http://192.168.160.70:8080/api/';

function getCurrentUser() {
    return localStorage.getItem('token');
}

function clearUser() {
   localStorage.clear()
}

function setCurrentToken(token) {
    localStorage.setItem('token',token);
}