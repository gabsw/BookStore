from flask import Flask, jsonify

app = Flask(__name__)


@app.route('/', methods=['GET'])
def index():
    greeting = {'hello': 'world'}
    return jsonify(greeting)


if __name__ == '__main__':
    app.run(host='0.0.0.0')
