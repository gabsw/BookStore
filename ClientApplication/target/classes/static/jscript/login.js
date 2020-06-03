function account() {
    let basicAuth = document.getElementById("inputUsername").value + ':'+ document.getElementById("inputPassword").value ;
    let encoded = btoa(basicAuth);
    let authenticationHeader = 'Basic ' + encoded ;
    askUser(authenticationHeader);
}

function askUser(authenticationHeader) {
    fetch(url + 'session/login', {
        method: 'get',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': authenticationHeader
        }
    })
        .then(res => res.json().then(json => ({
            headers: res.headers,
            status: res.status,
            json
        }))
            .then(({ headers, status, json }) => {
                if (status === 200) {
                    alert("Logged in!");
                    getUser(json);
                    setCurrentToken(headers.get('x-auth-token'));
                }
                else if (status === 404) {
                    alert("User not Found!");
                }
                else if (status === 403) {
                    alert("Did not input either Username or Password!");
                }
            }))
}

function getUser(json) {
    console.log('JSON format :' + JSON.stringify(json));
    if (JSON.stringify(json).includes('\"userType\":\"Buyer\"')){
        document.getElementById("signIn").href = "clientIndex.html";
    }
    else if (JSON.stringify(json).includes('\"userType\":\"Admin\"')){
        document.getElementById("signIn").href = "adminIndex.html";
    }
    else if (JSON.stringify(json).includes('\"userType\":\"Publisher\"')){
        document.getElementById("signIn").href = "publisherIndex.html";
    }
    else {
        alert("Not defined!");
    }
}
