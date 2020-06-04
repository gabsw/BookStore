
function highest() {
    fetch(url + 'books/available?sort=price,desc')
        .then(res => res.json())
        .then((data) => {
            let count = 0;
            let output = '';
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
                                <li class="list-group-item" id="book_category_`+count + `">Category: ${book.category} </li>
                                <li class="list-group-item" id="book_pub_`+count + `">Publisher: ${book.publisherName}</li>
                                <li class="list-group-item" id="book_price_`+count + `">Price: <strong>${book.price}  &euro;</strong></li>
                                <li class="list-group-item" id="book_qt_`+count + `"><strong>Available:</strong> ${book.quantity}</li>
                            </ul>
                            <div class="btn-group" style="width:100%">
                                <a class="btn btn-info" href="details.html?isbn=${book.isbn}" role="button">More Details</a>
                                <a class="btn btn-info"  onclick="myFunction()" role="button">Add To Cart</a>
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

function highestNoLogin() {
    fetch(url + 'books/available?sort=price,desc')
        .then(res => res.json())
        .then((data) => {
            let count = 0;
            let output = '';
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
                                <li class="list-group-item" id="book_category _`+count + `">Category: ${book.category} </li>
                                <li class="list-group-item" id="book_pub_`+count + `">Publisher: ${book.publisherName}</li>
                                <li class="list-group-item" id="book_price `+count + `">Price: <strong>${book.price} &euro;</strong> </li>
                                <li class="list-group-item" id="book_qt_`+count + `"><strong>Available:</strong> ${book.quantity}</li>
                            </ul>
                            <div class="btn-group" style="width:100%">
                              <a class="btn btn-outline-warning" role="button" id="detailsModal" href="login.html">Login to Buy/See More !</a>
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

function lowest() {
    fetch(url + 'books/available?sort=price,asc')
        .then(res => res.json())
        .then((data) => {
            let count =0 ;
            let output = '';
            data["content"].forEach(function (book) {
                count ++ ;
                output += `
                <li class="list-group-item">
                    <!-- Custom content-->
                    <div class="media align-items-lg-center flex-column flex-lg-row p-3">
                        <div class="media-body order-2 order-lg-1">
                          <h5 class="mt-0 font-weight-bold mb-2" id="book_title_`+count + `">${book["title"]}</h5>

                            <ul class="list-group list-group-flush">
                                <li class="list-group-item" id="book_author_`+count + `">Author: ${book.author} </li>
                                <li class="list-group-item" id="book_category _`+count + `">Category: ${book.category} </li>
                                <li class="list-group-item" id="book_pub_`+count + `">Publisher: ${book.publisherName}</li>
                                <li class="list-group-item" id="book_price `+count + `">Price: <strong>${book.price} &euro;</strong> </li>
                                <li class="list-group-item" id="book_qt_`+count + `"><strong>Available:</strong> ${book.quantity}</li>
                            </ul>
                            <div class="btn-group" style="width:100%">
                                <a class="btn btn-info" href="details.html?isbn=${book.isbn}" role="button">More Details</a>
                                <a class="btn btn-info"  onclick="myFunction()" role="button">Add To Cart</a>
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
function lowestNoLogin() {
    fetch(url + 'books/available?sort=price,asc')
        .then(res => res.json())
        .then((data) => {
            let count = 0;
            let output = '';
            data["content"].forEach(function (book) {
                count ++ ;
                output += `
                <li class="list-group-item">
                    <!-- Custom content-->
                    <div class="media align-items-lg-center flex-column flex-lg-row p-3">
                        <div class="media-body order-2 order-lg-1">
                            <h5 class="mt-0 font-weight-bold mb-2" id="book_title_`+count + `">${book["title"]}</h5>

                            <ul class="list-group list-group-flush">
                                <li class="list-group-item" id="book_author_`+count + `">Author: ${book.author} </li>
                                <li class="list-group-item" id="book_category_`+count + `">Category: ${book.category} </li>
                                <li class="list-group-item" id="book_pub_`+count + `">Publisher: ${book.publisherName}</li>
                                <li class="list-group-item" id="book_price_`+count + `">Price: <strong>${book.price} &euro;</strong> &euro;</li>
                                <li class="list-group-item" id="book_qt_`+count + `"><strong>Available:</strong> ${book.quantity}</li>
                            </ul>
                            <div class="btn-group" style="width:100%">
                              <a class="btn btn-outline-warning" role="button" id="detailsModal" href="login.html">Login to Buy/See More !</a>
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
function searchBooks() {
    let book_title = document.getElementById("findBook").value;
    let add;
    if (document.getElementById("book_title").checked){
        add = 'title='
    } else if (document.getElementById("book_author").checked){
        add = 'author='
    } else if (document.getElementById("book_category").checked){
        add = 'category='
    } else { add = 'title='}
    fetch(url+'books/search?' + add + book_title)
        .then(res => res.json())
        .then((data) => {
            let count = 0;
            let output = '';
            if ( data["content"].length === 0) { output = '<h1 class="mt-0 font-weight-bold mb-2" id="undefined" align="center">Nothing Found !</h1>';}
            else {
                data["content"].forEach(function (book) {
                    count ++ ;
                    output += `
                <li class="list-group-item">
                    <!-- Custom content-->
                    <div class="media align-items-lg-center flex-column flex-lg-row p-3">
                        <div class="media-body order-2 order-lg-1">
                          <h5 class="mt-0 font-weight-bold mb-2" id="book_title_`+count + `">${book["title"]}</h5>

                            <ul class="list-group list-group-flush">
                                <li class="list-group-item" id="book_author_`+count + `">Author: ${book.author} </li>
                                <li class="list-group-item" id="book_category _`+count + `">Category: ${book.category} </li>
                                <li class="list-group-item" id="book_pub_`+count + `">Publisher: ${book.publisherName}</li>
                                <li class="list-group-item" id="book_price `+count + `">Price: <strong>${book.price} &euro;</strong> </li>
                                <li class="list-group-item" id="book_qt_`+count + `"><strong>Available:</strong> ${book.quantity}</li>
                            </ul>
                            <div class="btn-group" style="width:100%">
                                <a class="btn btn-info" href="details.html?isbn=${book.isbn}" role="button">More Details</a>
                                <a class="btn btn-info"  onclick="myFunction()" role="button">Add To Cart</a>
                            </div>
                        </div>
                    </div>
                    <!-- End -->
                </li>
                         `;
                    }
                )
            }
            document.getElementById('output').innerHTML = output;
        })
        .catch((error) => {
            console.log(error)
        })
}
function searchBooksNoLogin() {
    let book_title = document.getElementById("findBook").value;
    let add;
    if (document.getElementById("book_title").checked){
        add = 'title='
    } else if (document.getElementById("book_author").checked){
        add = 'author='
    } else if (document.getElementById("book_category").checked){
        add = 'category='
    } else { add = 'title='}
    fetch(url+'books/search?' + add + book_title)
        .then(res => res.json())
        .then((data) => {
            let count = 0;
            let output = '';
            if ( data["content"].length === 0) { output = '<h1 class="mt-0 font-weight-bold mb-2" id="undefined" align="center">Nothing Found !</h1>';}
            else {
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
                                <li class="list-group-item" id="book_category _`+count + `">Category: ${book.category} </li>
                                <li class="list-group-item" id="book_pub_`+count + `">Publisher: ${book.publisherName}</li>
                                <li class="list-group-item" id="book_price `+count + `">Price: <strong>${book.price} &euro;</strong> </li>
                                <li class="list-group-item" id="book_qt_`+count + `"><strong>Available:</strong> ${book.quantity}</li>
                            </ul>
                            <div class="btn-group" style="width:100%">
                                 <a class="btn btn-outline-warning" role="button" id="detailsModal" href="login.html">Login to Buy/See More !</a>
                            </div>
                        </div>
                    </div>
                    <!-- End -->
                </li>
                        ` ;
                })
            }
            document.getElementById('output').innerHTML = output;
        })
        .catch((error) => {
            console.log(error)
        })
}

function getBooksNoLogin() {
        fetch(url + 'books/available',)
            .then(res => res.json())
            .then((data) => {
                let count = 0;
                let output = '';
                data["content"].forEach(function (book) {
                    count++;
                    var book_title = "book_title" + count.toString();
                    output += `
                <li class="list-group-item">
                    <!-- Custom content-->
                    <div class="media align-items-lg-center flex-column flex-lg-row p-3">
                        <div class="media-body order-2 order-lg-1">
                            <h5 class="mt-0 font-weight-bold mb-2" id="book_title_` + count + `">${book["title"]}</h5>

                            <ul class="list-group list-group-flush">
                                <li class="list-group-item" id="book_author_` + count + `">Author: ${book.author} </li>
                                <li class="list-group-item" id="book_category_` + count + `">Category: ${book.category} </li>
                                <li class="list-group-item" id="book_pub_` + count + `">Publisher: ${book.publisherName}</li>
                                <li class="list-group-item" id="book_price_` + count + `">Price: <strong>${book.price} &euro;</strong></li>
                                <li class="list-group-item" id="book_qt_` + count + `"><strong>Available:</strong> ${book.quantity}</li>
                            </ul>
                            <div class="btn-group" style="width:100%">
                               <a class="btn btn-outline-warning" role="button" id="detailsModal" href="login.html">Login to Buy/See More !</a>

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

function getBooks(){
    fetch(url + 'books/available')
        .then(res => res.json())
        .then((data) => {
            let count = 0;
            let output = '';
            data["content"].forEach(function (book) {
                count ++ ;
                output += `
                <li class="list-group-item">
                    <!-- Custom content-->
                    <div class="media align-items-lg-center flex-column flex-lg-row p-3">
                        <div class="media-body order-2 order-lg-1">
                          <h5 class="mt-0 font-weight-bold mb-2" id="book_title_`+count + `">${book["title"]}</h5>

                            <ul class="list-group list-group-flush">
                                <li class="list-group-item" id="book_author_`+count + `">Author: ${book.author} </li>
                                <li class="list-group-item" id="book_category _`+count + `">Category: ${book.category} </li>
                                <li class="list-group-item" id="book_pub_`+count + `">Publisher: ${book.publisherName}</li>
                                <li class="list-group-item" id="book_price `+count + `">Price: <strong>${book.price} &euro;</strong> </li>
                                <li class="list-group-item" id="book_qt_`+count + `"><strong>Available:</strong> ${book.quantity}</li>
                            </ul>
                            <div class="btn-group" style="width:100%">
                                <a class="btn btn-info" href="details.html?isbn=${book.isbn}" role="button">More Details</a>
                                <a class="btn btn-info"  onclick="myFunction(${book.isbn})" role="button">Add To Cart</a>
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


let storeObj= [];
function myFunction(isbn) {
    storeObj.push(isbn);
    console.log("Array " + storeObj);
    localStorage.setItem('storage',JSON.stringify(storeObj));
}






