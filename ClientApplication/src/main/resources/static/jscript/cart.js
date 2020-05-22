

values = function fin() {
    return JSON.parse(localStorage.getItem('mapa'));
};

result = function createOrder () {
    const map = new Map();
    let count = 1;
    for (let i= 0 ; i < values().length; i++) {
        if(!map.has(values()[i])) {
            map.set(values()[i], 1);
        } else { count++ ; map.set(values()[i],count);}
    }
    return map;
};

function strMapToObj(result) {
    let obj = Object.create(null);
    for (let [k,v] of result) {
        obj[k] = v;
    }
    return obj;
}

