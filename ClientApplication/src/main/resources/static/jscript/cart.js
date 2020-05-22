values = function fin() {
    return JSON.parse(localStorage.getItem('storage'));
};

/**
 * @return {string}
 */
jsontrying = function StringForJson() {
    let output = '[';
    let virgCount = 0;
    let maxVirg = result().size -1 ;
    for (let i = 0; i < result().size; i++) {
        output += '{' + '\"isbn\"' + '\:"' + Array.from(result().keys())[i] +'\",' + '\"quantity\"' + '\:' + result().get(Array.from(result().keys())[i]) + '}'
        if(virgCount < maxVirg) {
            output += ',';
            virgCount ++;
        }
    }
    output += ']';
    return output;
};

result = function createOrder () {
    const map = new Map();
    let count = 1;
    for (let i= 0 ; i < values().length; i++) {
        if(!map.has(values()[i])) {
            map.set(values()[i], 1);
        } else {
            count++ ;
            map.set(values()[i],count);}
    }
    return map;
};

