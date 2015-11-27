/**
 * Created by Anton on 25.11.2015.
 */
function getRandomInt(min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
}

function Timer() {
    this.timerId = null;
    this.interval = 1000;
    this.runWithStart = false;
    this.start = function (func) {
        if (this.runWithStart) {
            func();
        }
        this.timerId = setInterval(func, this.interval);
    };
    this.stop = function () {
        if (this.isActive()) clearInterval(this.timerId);
        this.timerId = null;
    };
    this.isActive = function () {
        return this.timerId !== null;
    };
}