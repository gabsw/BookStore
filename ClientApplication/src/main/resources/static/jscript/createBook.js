function createNewBook () {
    let body_text =  '[' + '{' +
        '\"isbn\"' + '\:"' + document.getElementById('create_ISBN').value +'\",' +
        '\"title\"' + '\:"' + document.getElementById('create_Title').value +'\",' +
        '\"author\"' + '\:"' + document.getElementById('create_Author').value +'\",' +
        '\"description\"' + '\:"' + document.getElementById('create_Description').value +'\",' +
        '\"price\"' + ':' + document.getElementById('create_Price').value +',' +
        '\"quantity\"' + ':' + document.getElementById('create_Quantity').value +',' +
        '\"category\"' + '\:"' + document.getElementById('create_Category').value +'\"'
        + '}' + ']' ;

    fetch(url + 'publisher/'+getPubName()+'/stock' , {
        method: 'post',
        headers: {
            'Content-Type': 'application/json',
            'x-auth-token' : getCurrentUser()
        },
        body:  body_text
    })
        .then(function (res) {
            return res.json();
        })
        .then(function (data) {
            alert("Book Added to Stock!")
        })

}