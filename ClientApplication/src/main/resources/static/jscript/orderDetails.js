const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const product = urlParams.get('id');

function getById(){

    fetch(url+ 'order/'+product,{
        method: 'get',
        headers: {
            'x-auth-token': getCurrentUser()
        }
    })
        .then((res) => res.json())
        .then((data) => {
            let id = `${data["id"]}`;
            document.getElementById('order_id').innerHTML = "Order #" + id;
            let buyer_name = `${data.buyerName}`;
            document.getElementById('buyer_name').innerHTML = "<strong>" + buyer_name+ "</strong>";
            let buyer_address = `${data.address}`;
            document.getElementById('buyer_address').innerHTML ="Address: " +buyer_address;
            let total = `${data.finalPrice}`;
            document.getElementById('total_price').innerHTML ="<strong>" + total + "  &euro; </strong>";

            let output = '';
            data["bookOrders"].forEach(function (order) {
                output += `
                    <tr>
                    <td class="center">${order.isbn}</td>
                    <td class="left strong">${order["title"]}</td>

                    <td class="right">${order.author}</td>
                    <td class="center">${order.quantity}</td>
                    </tr>
                         `;

            })
            document.getElementById('output').innerHTML = output;
        })
        .catch((error) => {
            console.log(error)
        })
}
