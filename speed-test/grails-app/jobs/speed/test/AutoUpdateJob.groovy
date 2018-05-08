package speed.test

class AutoUpdateJob {
    static triggers = {
        simple repeatInterval: 1000 * 30 // execute job once in 5 seconds
    }

    def execute() {
        print "job run"
    }
}
