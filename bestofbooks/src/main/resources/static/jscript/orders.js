function find() {
    fetch(url + 'buyer/' + getUserName()+'/orders')
        .then(res => res.json())
        .then((data) => {
            let output = '';
            data.forEach(function (order) {
                output += `
            <li class="list-group-item">
                    <!-- Custom content-->
                    <div class="media align-items-lg-center flex-column flex-lg-row p-3">
                        <div class="media-body order-2 order-lg-1">
                            <h5 class="mt-0 font-weight-bold mb-2">Order ${order.id}</h5>

                            <ul class="list-group list-group-flush">
                                <li class="list-group-item" >Shipping Address:  ${order.address}  </li>
                                <li class="list-group-item">Total: ${order.finalPrice} $ </li>
                                <li class="list-group-item">Ref: ${order.paymentReference}  </li>

                            </ul>
                            <div class="btn-group" style="width:100%">
                              <a class="btn btn-outline-warning" role="button"  href="OrderDetails.html?id=${order.id}">Details/Invoice</a>
                            </div>
                        </div>
                    </div>

                    <!-- End -->
                </li>
                         `;

            })
            document.getElementById('output').innerHTML = output;
        })
        .catch((error) => {
            console.log(error)
        })
}