
function register() {
    let basicAuth = document.getElementById("inputUsername").value + ':'+ document.getElementById("inputPassword").value ;
    let encoded = btoa(basicAuth);
    let authenticationHeader = 'Basic ' + encoded ;
    let body_text =  '{' +
        '\"username\"' + '\:"' + document.getElementById('inputUsername').value +'\",' +
        '\"userType\"' + '\:"' + document.getElementById('example').value +'\",' +
        '\"attributes\"' + ':'+ atributes()
        + '}';

 //   console.log(body_text);
   registerUser(authenticationHeader,body_text);
}


atributes = function changeAttr() {
    let atributes= '' ;
    if (document.getElementById('example').value === 'Admin' || document.getElementById('example').value === 'Buyer' )
    {
        atributes += '{}';
    } else {
        atributes += '{'+'\"name\":'+ '\"'+ document.getElementById('inputName').value + '\",\"tin\":\"' +
            document.getElementById('inputTin').value + '\"' + '}';
    }
    return atributes;
};


function registerUser(authenticationHeader,body_text) {
    fetch(url + 'session/register', {
        method: 'post',
        headers: {
            'Content-Type': 'application/json',
            'mode' : 'no-cors',
            'Authorization': authenticationHeader
        },
        body : body_text
    })
        .then(res => res.json().then(json => ({
                headers: res.headers,
                status: res.status,
                json
            }))
            .then((data) => {
                if (data.status === 201) {
                    alert("Created!");
                } else if (data.status === 400 ) {
                    alert("Could not create, User either exists or missing an input!");
                }
                console.log(data.status)
            }))
}

function checkvalue(val)
{
    if(val==="Publisher") {
        document.getElementById('inputName').style.display='block';
        document.getElementById('inputTin').style.display='block';
    }
    else{
        document.getElementById('inputName').style.display='none';
        document.getElementById('inputTin').style.display='none';
    }
}
