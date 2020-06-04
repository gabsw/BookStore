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


function getFinalPrice(){
    fetch(url + 'order/estimated-price' , {
        method: 'post',
        headers: {
            'Content-Type': 'application/json',
            'x-auth-token' : getCurrentUser()
        },
        body: jsontrying()})
        .then(res => res.json())
        .then(res => document.getElementById('finalPrice').innerHTML = "Final Price: " + res +" &euro;")
        .catch(error => console.error('Error:', error));
};


function getBy(){
    let output = ``;
    for (let i= 0 ; i < result().size; i++) {

        fetch(url+'books/isbn/' + Array.from(result().keys())[i],{
            method: 'get',
            headers: {
                'x-auth-token': getCurrentUser()
            }
        })
            .then((res) => res.json())
            .then((data) => {
                let count = 0;
                    output += `
                <div class="row">
                        <div class="col-12 text-sm-center col-sm-12 text-md-left col-md-6">
                        <h4 class="product-name" id="product-name_` + count +`">
                                ${data.title}
                       </h4>
                    </div>
                    <div class="col-12 col-sm-12 text-sm-center col-md-6 text-md-right row">
                        <div class="col-6 col-sm-6 col-md-6 text-md-right" id="price" style="padding-top: 5px">
                        <h6><strong>${data.price}  &euro; <span class="text-muted">x</span></strong></h6>
                    </div>
                    <div class="col-3 col-sm-3 col-md-3">
                        <div class="quantity" style="padding-top: 5px">
                        <h6><strong> Qty: `+ result().get(Array.from(result().keys())[i]) +`</strong></h6>
                        </div>
                        </div>
                        
                        </div>
                        </div>
                        <hr>
                        `;
                    document.getElementById('output').innerHTML = output;
                }

            )
    }
}


ref = function generateRef() {
   return  Math.random().toString(36).slice(2)
};

function createOrder() {
    fetch(url + 'order/' , {
        method: 'post',
        headers: {
            'Content-Type': 'application/json',
            'x-auth-token' : getCurrentUser()
        },
        body: JSON.stringify({
            "buyerUsername": getUserName(),
            "address": document.getElementById('address').value,
            "paymentReference": ref(),
            "bookOrders": JSON.parse(jsontrying())
        }) })
        .then(res => res.json().then(json => ({
            headers: res.headers,
            status: res.status,
            json
        }))
            .then(({ headers, status, json }) => {
                if (JSON.stringify(json).includes("does not have enough copies in stock to fulfill order request")) {
                    alert("Not enough books in Stock!")
                }
                else if (JSON.stringify(json).includes("Address cannot be null")) {
                    alert("Address cannot be null or whitespace");
                }
                else if (JSON.stringify(json).includes("bookOrders cannot be empty")) {
                    alert("Cart is Empty");
                }
                else{
                    alert("Thank You for Your Purchase\nPayment Ref: " +ref())
                }

            }))
}

