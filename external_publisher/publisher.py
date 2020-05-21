import requests
import time
import base64


publisher_name = 'External Publisher'
username = 'external_publisher'
password = 'pw'
basic_token = username + ':' + password

session = requests.Session()

auth_token = None

auth_header = {'Authorization': 'Basic ' + base64.encodebytes(basic_token)}


def login():
    response = session.post('https://localhost:8080/api/session/login', headers=auth_header)
    if response.status_code != 200:
        raise Exception('bad credentials')


def get_stock():
    current_page = 0

    while True:
        response = session.get(f"https://localhost:8080/api/publisher/{publisher_name}/stock?page={current_page}")
        book_page = response.json()
        is_last_page = book_page['last']

        books = book_page['content']
        for book in books:
            if book['quantity'] == 0:
                update_stock(book['isbn'])

        if is_last_page:
            break
        current_page += 1


def update_stock(isbn):
    new_stock = {'isbn': isbn, 'quantity': 10}
    session.put(f"https://localhost:8080/api/publisher/{publisher_name}/stock", json=new_stock)


if __name__ == '__main__':
    while True:
        login()
        get_stock()
        time.sleep(60 * 60)


