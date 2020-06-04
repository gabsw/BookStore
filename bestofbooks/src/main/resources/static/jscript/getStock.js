function getStock() {
    fetch(url +'publisher/'+ getPubName()+  '/stock' ,{
        method: 'get',
        headers: {
            'x-auth-token': getCurrentUser()
        }
    })
        .then(res => res.json())
        .then((data) => {
            let output = '';
            let count = 0 ;
            data["content"].forEach(function (book) {
                count ++;
                output += `
              <li class="list-group-item">
                    <!-- Custom content-->
                    <div class="media align-items-lg-center flex-column flex-lg-row p-3">
                        <div class="media-body order-2 order-lg-1">
                          <h5 class="mt-0 font-weight-bold mb-2" id="book_title_`+count + `">${book["title"]}</h5>

                            <ul class="list-group list-group-flush">
                                <li class="list-group-item" id="book_author_`+count + `">Author: ${book.author} </li>
                                <li class="list-group-item" id="book_isbn_`+count + `">ISBN: ${book.isbn} </li>
                                <li class="list-group-item" id="book_category_`+count + `">Category: ${book.category} </li>
                                <li class="list-group-item" id="book_description_`+count + `">Description: ${book["description"]}</li>
                                <li class="list-group-item" id="book_price_`+count + `">Price: <strong>${book.price}&euro</strong></li>
                                <li class="list-group-item" id="book_qt_`+count + `"><strong>Quantity:</strong> ${book.quantity}</li>
                            </ul>

                        </div>
                    </div>
                    <!-- End -->
                </li>
                         `;

            })
            document.getElementById('output').innerHTML = output;
            console.log(JSON.stringify(data));
        })
        .catch((error) => {
            console.log(error)
        })
}

