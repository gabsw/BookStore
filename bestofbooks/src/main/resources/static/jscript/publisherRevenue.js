function getRevenues(name) {
    fetch(url+ 'publisher/'+ name+ '/revenue',{
        method: 'get',
        headers: {
            'x-auth-token': getCurrentUser()
        }
    })
        .then(res => res.json())
        .then((data) => {
            let count =0 ;
            let output = '';
            data["content"].forEach(function (revenue) {
                count ++;
                output += `
                <li class="list-group-item">
                    <!-- Custom content-->
                    <div class="media align-items-lg-center flex-column flex-lg-row p-3">
                        <div class="media-body order-2 order-lg-1">
                            <h5 class="mt-0 font-weight-bold mb-2" id="revenue_title_`+count + `">Revenue #${revenue["id"]}</h5>

                            <ul class="list-group list-group-flush">
                            <li class="list-group-item" id="revenue_amount_`+count + `">Amount: ${revenue["amount"]}  &euro;</li>
                            <li class="list-group-item" id="revenue_order_`+count + `">Order ID: ${revenue.orderId} </li>
                            <li class="list-group-item" id="revenue_isbn_`+count + `">ISBN : ${revenue.isbn} </li>

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
function getRevenuesTotal(name) {
    fetch(url + 'publisher/'+ name +'/revenue/total',{
        method: 'get',
        headers: {
            'x-auth-token': getCurrentUser()
        }
    })

        .then(res => res.json())
        .then((data) => {
            document.getElementById('finalPrice').innerHTML = '<strong>Total: </strong> '+  data +' &euro;';
        })
        .catch((error) => {
            console.log(error)
        })
}

