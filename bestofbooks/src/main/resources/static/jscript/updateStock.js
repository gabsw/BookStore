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
        .then(function (res) {
            return res.json();
        })

}

/**
 * @return {string}
 */
conversion = function StringForJson(mapa) {
    return  '{' + '\"isbn\"' + '\:"' + Array.from(mapa.keys())[0] + '\",' + '\"quantity\"' + '\:' + mapa.get(Array.from(mapa.keys())[0]) + '}';
};

