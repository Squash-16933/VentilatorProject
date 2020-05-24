import time


class stepper(object):
    FORWARD = "forward"
    BACKWARD = "backward"
    SINGLE = "single"
    DOUBLE = "double"
    INTERLEAVE = "interleave"
    MICROSTEP = "microstep"

    def __init__(self, name='stepper1'):
        self.name = name
        print('Starting...')

    def onestep(self, direction=FORWARD, style=SINGLE):
        print(f'{self.name}: Step {direction}, {style}')

    def release(self):
        print(f'{self.name} released')


class MotorKit(object):

    def __init__(self, stepper1=stepper('stepper1'), stepper2=None):
        self._stepper1 = stepper1
        self._stepper2 = stepper2

    @property
    def stepper1(self):
        return self._stepper1

    @property
    def stepper2(self):
        return self._stepper2


if __name__ == "__main__":
    kit = MotorKit()
    for i in range(100):
        kit.stepper1.onestep(direction=stepper.FORWARD, style=stepper.SINGLE)
        time.sleep(0.01)
    kit.stepper1.release()
