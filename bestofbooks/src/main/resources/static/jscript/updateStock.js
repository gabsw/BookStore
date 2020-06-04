values = function toUpdate() {
    return JSON.parse(localStorage.getItem('updateBook'));
};

function updateBooks() {
    let map = new Map();
    map.set(document.getElementById('isbn').value, document.getElementById('quantity').value);
    console.log(conversion(map));
    makeAPIcall(conversion(map));
}

function makeAPIcall(mapas) {
    fetch(url + 'publisher/'+getPubName()+'/stock' , {
        method: 'put',
        headers: {
            'Content-Type': 'application/json',
            'x-auth-token' : getCurrentUser()
        },
        body:  mapas
    })
        .then(res => res.json().then(json => ({
            headers: res.headers,
            status: res.status,
            json
        }))
            .then(({ headers, status, json }) => {
                if (JSON.stringify(json).includes("Quantity cannot not be less than 1")) {
                    alert("Quantity cannot not be less than 1")
                }
                else if (JSON.stringify(json).includes("must match")) {
                    alert("ISBN does not exists in this context");
                }
                else{
                    alert("Updated")
                }

            }))

}

/**
 * @return {string}
 */
conversion = function StringForJson(mapa) {
    return  '{' + '\"isbn\"' + '\:"' + Array.from(mapa.keys())[0] + '\",' + '\"quantity\"' + '\:' + mapa.get(Array.from(mapa.keys())[0]) + '}';
};

