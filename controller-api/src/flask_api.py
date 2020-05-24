import time

try:
    from adafruit_motor import stepper
    from adafruit_motorkit import MotorKit
except NotImplementedError:
    from motor_kit_simulator import MotorKit, stepper

from flask import Flask, request


app = Flask(__name__)


@app.route('/')
@app.route('/instructions')
def index():
    return """Follow instructions in the README"""


@app.route('/testdrive')
def testdrive():
    args = request.args
    direction = getattr(stepper, args.get('direction', 'FORWARD'))
    style = getattr(stepper, args.get('style', 'SINGLE'))
    sleep = float(args.get('sleep', '0.01'))
    steps = int(args.get('steps', 100))

    kit = MotorKit()

    for i in range(steps):
        kit.stepper1.onestep(direction=direction, style=style)
        time.sleep(sleep)
    kit.stepper1.release()
    return 'Done!', 200


if __name__ == "__main__":
    testdrive()
