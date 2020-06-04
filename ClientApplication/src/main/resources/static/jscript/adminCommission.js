function getCommissions() {
    fetch(  url+ 'admin/commissions/', {
        method: 'get',
        headers: {
            'x-auth-token': getCurrentUser()
        }
    })

        .then(res => res.json())
        .then((data) => {
            let count =0 ;
            let output = '';
            data["content"].forEach(function (commission) {
                count ++;
                output += `
                <li class="list-group-item">
                    <!-- Custom content-->
                    <div class="media align-items-lg-center flex-column flex-lg-row p-3">
                        <div class="media-body order-2 order-lg-1">
                            <h5 class="mt-0 font-weight-bold mb-2" id="commission_title_`+count + `">Commission #${commission["id"]}</h5>

                            <ul class="list-group list-group-flush">
                            <li class="list-group-item" id="commission_amount_`+count + `">Amount: ${commission["amount"]}  &euro;</li>
                            <li class="list-group-item" id="commission_order_`+count + `">Order ID: ${commission.orderId} </li>
                            </ul>
                        </div>
                    </div>
                 </li>

                    `;

            });
            document.getElementById('output').innerHTML = output;
        })
        .catch((error) => {
            console.log(error)
        })
}
function getCommissionsTotal() {
    fetch(url + 'admin/commissions/total',{
        method: 'get',
            headers: {
            'x-auth-token': getCurrentUser()
        }
    })
        .then(res => res.json())
        .then((data) => {
            document.getElementById('finalPrice').innerHTML = '<strong>Commission Total:</strong> '+  data +' &euro;';
        })
        .catch((error) => {
            console.log(error)
        })
}



function lowestAmount() {
    fetch( url+ 'admin/commissions/?sort=amount,asc', {
        method: 'get',
        headers: {
            'mode' : 'no-cors',
            'x-auth-token': getCurrentUser()
        }
    })
        .then(res => res.json())
        .then((data) => {
            let count =0 ;
            let output = '';
            data["content"].forEach(function (commission) {
                count ++;
                output += `
                <li class="list-group-item">
                    <!-- Custom content-->
                    <div class="media align-items-lg-center flex-column flex-lg-row p-3">
                        <div class="media-body order-2 order-lg-1">
                            <h5 class="mt-0 font-weight-bold mb-2" id="commission_title_`+count + `">Commission #${commission["id"]}</h5>

                            <ul class="list-group list-group-flush">
                            <li class="list-group-item" id="commission_amount_`+count + `">Amount: ${commission["amount"]}  &euro;</li>
                            <li class="list-group-item" id="commission_order_`+count + `">Order ID: ${commission.orderId} </li>
                            </ul>
                        </div>
                    </div>
                 </li>

                    `;

            });
            document.getElementById('output').innerHTML = output;
        })
        .catch((error) => {
            console.log(error)
        })
}

function highestAmount() {
    fetch( url+ 'admin/commissions/?sort=amount,desc', {
        method: 'get',
        headers: {
            'mode' : 'no-cors',
            'x-auth-token': getCurrentUser()
        }
    })
        .then(res => res.json())
        .then((data) => {
            let count =0 ;
            let output = '';
            data["content"].forEach(function (commission) {
                count ++;
                output += `
                <li class="list-group-item">
                    <!-- Custom content-->
                    <div class="media align-items-lg-center flex-column flex-lg-row p-3">
                        <div class="media-body order-2 order-lg-1">
                            <h5 class="mt-0 font-weight-bold mb-2" id="commission_title_`+count + `">Commission #${commission["id"]}</h5>

                            <ul class="list-group list-group-flush">
                            <li class="list-group-item" id="commission_amount_`+count + `">Amount: ${commission["amount"]}  &euro;</li>
                            <li class="list-group-item" id="commission_order_`+count + `">Order ID: ${commission.orderId} </li>
                            </ul>
                        </div>
                    </div>
                 </li>

                    `;

            });
            document.getElementById('output').innerHTML = output;
        })
        .catch((error) => {
            console.log(error)
        })
}

