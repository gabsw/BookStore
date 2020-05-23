const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const product = urlParams.get('isbn')
const url = 'http://192.168.160.70:8080/api/books/isbn/' +product;

window.onload = function getByIsbn(){
    fetch(url)
        .then((res) => res.json())
        .then((data) => {
                let isbn = `${data.isbn}`;
                let editor = `${data.publisherName}`;
                let category = `${data.category}`;
                let author = `${data.author}`;
                let price = `${data.price}`;
                let book_title  = `${data.title}`;
                let book_description = `${data.description}`;
                document.getElementById('isbn').innerHTML = "ISBN: " + isbn;
                document.getElementById('editor').innerHTML = "Editor: " + editor;
                document.getElementById('category').innerHTML = "Category: " + category;
                document.getElementById('author').innerHTML = "Author: " + author;
                document.getElementById('price').innerHTML = "Price: " + price;
                document.getElementById('book_title').innerHTML = book_title;
                document.getElementById('description').innerHTML = book_description;

            }
        )
}