let url = 'http://192.168.160.70:8080/api';

function highest() {
    fetch(url + '/books/available?sort=price,desc')
        .then(res => res.json())
        .then((data) => {
            let output = '';
            data["content"].forEach(function (book) {
                output += `
                <li class="list-group-item">
                    <!-- Custom content-->
                    <div class="media align-items-lg-center flex-column flex-lg-row p-3">
                        <div class="media-body order-2 order-lg-1">
                            <h5 class="mt-0 font-weight-bold mb-2">${book["title"]}</h5>

                            <ul class="list-group list-group-flush">
                                <li class="list-group-item" >Author: ${book.author} </li>
                                <li class="list-group-item">Category: ${book.category} </li>
                                <li class="list-group-item">Publisher: ${book.publisherName}</li>
                                <li class="list-group-item">Price: <strong>${book.price}</strong></li>
                                <li class="list-group-item"><strong>Available:</strong> ${book.quantity}</li>
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
    fetch(url + '/books/available?sort=price,desc')
        .then(res => res.json())
        .then((data) => {
            let output = '';
            data["content"].forEach(function (book) {
                output += `
                <li class="list-group-item">
                    <!-- Custom content-->
                    <div class="media align-items-lg-center flex-column flex-lg-row p-3">
                        <div class="media-body order-2 order-lg-1">
                            <h5 class="mt-0 font-weight-bold mb-2">${book["title"]}</h5>

                            <ul class="list-group list-group-flush">
                                <li class="list-group-item" >Author: ${book.author} </li>
                                <li class="list-group-item">Category: ${book.category} </li>
                                <li class="list-group-item">Publisher: ${book.publisherName}</li>
                                <li class="list-group-item">Price: <strong>${book.price}</strong></li>
                                <li class="list-group-item"><strong>Available:</strong> ${book.quantity}</li>
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
    fetch(url + '/books/available?sort=price,asc')
        .then(res => res.json())
        .then((data) => {
            let output = '';
            data["content"].forEach(function (book) {
                output += `
                <li class="list-group-item">
                    <!-- Custom content-->
                    <div class="media align-items-lg-center flex-column flex-lg-row p-3">
                        <div class="media-body order-2 order-lg-1">
                            <h5 class="mt-0 font-weight-bold mb-2">${book["title"]}</h5>

                            <ul class="list-group list-group-flush">
                                <li class="list-group-item" >Author: ${book.author} </li>
                                <li class="list-group-item">Category: ${book.category} </li>
                                <li class="list-group-item">Publisher: ${book.publisherName}</li>
                                <li class="list-group-item">Price: <strong>${book.price}</strong></li>
                                <li class="list-group-item"><strong>Available:</strong> ${book.quantity}</li>
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
    fetch(url + '/books/available?sort=price,asc')
        .then(res => res.json())
        .then((data) => {
            let output = '';
            data["content"].forEach(function (book) {
                output += `
                <li class="list-group-item">
                    <!-- Custom content-->
                    <div class="media align-items-lg-center flex-column flex-lg-row p-3">
                        <div class="media-body order-2 order-lg-1">
                            <h5 class="mt-0 font-weight-bold mb-2">${book["title"]}</h5>

                            <ul class="list-group list-group-flush">
                                <li class="list-group-item" >Author: ${book.author} </li>
                                <li class="list-group-item">Category: ${book.category} </li>
                                <li class="list-group-item">Publisher: ${book.publisherName}</li>
                                <li class="list-group-item">Price: <strong>${book.price}</strong></li>
                                <li class="list-group-item"><strong>Available:</strong> ${book.quantity}</li>
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
    fetch(url+'/books/search?' + add + book_title)
        .then(res => res.json())
        .then((data) => {
            let output = '';
            if ( data["content"].length == 0) { output = '<h1 class="mt-0 font-weight-bold mb-2" align="center">Nothing Found !</h1>';}
            else {
                data["content"].forEach(function (book) {
                        output += `
                <li class="list-group-item">
                    <!-- Custom content-->
                    <div class="media align-items-lg-center flex-column flex-lg-row p-3">
                        <div class="media-body order-2 order-lg-1">
                            <h5 class="mt-0 font-weight-bold mb-2">${book["title"]}</h5>

                            <ul class="list-group list-group-flush">
                                <li class="list-group-item" >Author: ${book.author} </li>
                                <li class="list-group-item">Category: ${book.category} </li>
                                <li class="list-group-item">Publisher: ${book.publisherName}</li>
                                <li class="list-group-item">Price: <strong>${book.price}</strong></li>
                                <li class="list-group-item"><strong>Available:</strong> ${book.quantity}</li>
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
    fetch(url+'/books/search?' + add + book_title)
        .then(res => res.json())
        .then((data) => {
            let output = '';
            if ( data["content"].length == 0) { output = '<h1 class="mt-0 font-weight-bold mb-2" align="center">Nothing Found !</h1>';}
            else {
                data["content"].forEach(function (book) {
                    output += `
                <li class="list-group-item">
                    <!-- Custom content-->
                    <div class="media align-items-lg-center flex-column flex-lg-row p-3">
                        <div class="media-body order-2 order-lg-1">
                            <h5 class="mt-0 font-weight-bold mb-2">${book["title"]}</h5>

                            <ul class="list-group list-group-flush">
                                <li class="list-group-item" >Author: ${book.author} </li>
                                <li class="list-group-item">Category: ${book.category} </li>
                                <li class="list-group-item">Publisher: ${book.publisherName}</li>
                                <li class="list-group-item">Price: <strong>${book.price}</strong></li>
                                <li class="list-group-item"><strong>Available:</strong> ${book.quantity}</li>
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
            }
            document.getElementById('output').innerHTML = output;
        })
        .catch((error) => {
            console.log(error)
        })
}

function getBooksNoLogin() {
    fetch(url + '/books/available')
        .then(res => res.json())
        .then((data) => {
            let output = '';

            data["content"].forEach(function (book) {
                output += `
                <li class="list-group-item">
                    <!-- Custom content-->
                    <div class="media align-items-lg-center flex-column flex-lg-row p-3">
                        <div class="media-body order-2 order-lg-1">
                            <h5 class="mt-0 font-weight-bold mb-2">${book["title"]}</h5>

                            <ul class="list-group list-group-flush">
                                <li class="list-group-item" >Author: ${book.author} </li>
                                <li class="list-group-item">Category: ${book.category} </li>
                                <li class="list-group-item">Publisher: ${book.publisherName}</li>
                                <li class="list-group-item">Price: <strong>${book.price}</strong></li>
                                <li class="list-group-item"><strong>Available:</strong> ${book.quantity}</li>
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
    fetch(url + '/books/available')
        .then(res => res.json())
        .then((data) => {
            let output = '';
            data["content"].forEach(function (book) {
                output += `
                <li class="list-group-item">
                    <!-- Custom content-->
                    <div class="media align-items-lg-center flex-column flex-lg-row p-3">
                        <div class="media-body order-2 order-lg-1">
                            <h5 class="mt-0 font-weight-bold mb-2">${book["title"]}</h5>

                            <ul class="list-group list-group-flush">
                                <li class="list-group-item" >Author: ${book.author} </li>
                                <li class="list-group-item">Category: ${book.category} </li>
                                <li class="list-group-item">Publisher: ${book.publisherName}</li>
                                <li class="list-group-item">Price: <strong>${book.price}</strong></li>
                                <li class="list-group-item"><strong>Available:</strong> ${book.quantity}</li>
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


function myFunction() {
    alert("Item Added To Cart!");
}