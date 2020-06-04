let url = "/api/";


function getCurrentUser() {
    return localStorage.getItem('token');
}

function clearUser() {
   localStorage.clear();
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

            if (body["attributes"].length !== 0) {
                console.log(body["attributes"].name);
                localStorage.removeItem('PubName');
                localStorage.setItem('PubName',JSON.stringify(body["attributes"].name));
            }
        } );
}

   

function setName(name) {
    document.getElementById('name').innerHTML = name;
}

function getPubName() {
    return JSON.parse(localStorage.getItem('PubName'));
}


function getUserName() {
    return JSON.parse(localStorage.getItem('userName'));
}
