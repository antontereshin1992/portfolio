var TETRIS = {};

var sizeX = 7;
var sizeY = 12;
var timerInterval = 800;

const CELL = 30;

const GRID_COLOR = "WHITE";
const BACKGROUND_COLOR = "BLACK";
const CELL_COLORS = [
    "YELLOW",
    "LIME",
    "AQUA",
    "PINK",
    "RED",
    "WHITE",
    "BLUE"];

function runTetris() {
    TETRIS.restart();
}

function stopTetris() {
    TETRIS.stop();
}

var preparingTetris = function () {
    var jqueryCanvas = $("#img");
    var jqueryScore = $(".score");
    var jqueryResult = $("#result-container");
    var canvas = jqueryCanvas.get(0);
    var ctx = canvas.getContext("2d");

    var score = 0;
    var fallingItem = {};
    var timer = new Timer();
    var grid = [sizeX];

    /**
     * Initializing
     */
    function init() {
        timer.interval = timerInterval;
        timer.runWithStart = true;
        printScore(score = 0);
        jqueryResult.hide();
        canvas.removeEventListener();
        canvas.width = sizeX * CELL;
        canvas.height = sizeY * CELL;
        ctx.fillRect(0, 0, canvas.width, canvas.height);
        for (var x = 0; x < sizeX; x++) {
            grid[x] = [sizeY];
            for (var y = 0; y < sizeY; y++) {
                grid[x][y] = -1;
            }
        }
        bindElements();
        drawGrid(canvas.width, canvas.height, GRID_COLOR);
        jqueryCanvas.focus();
    }

    /**
     * Binds elements
     */
    function bindElements() {
        canvas.addEventListener("keydown", doMove, true);
        jqueryResult.click(function () {
            jqueryResult.hide();
        });
    }

    /**
     * Catches KeyEvent and controls the event of moves
     * @param e KeyBoard event object
     */
    function doMove(e) {
        if (!timer.isActive()) return;
        switch (e.keyCode) {
            case 37:
                fallingItem.pos.moveLeft();
                break;
            case 39:
                fallingItem.pos.moveRight();
                break;
            case 40:
                fallingItem.pos.moveBottom();
                break;
            default:
                return;
        }
        e.keyCode = 0;
    }

    /**
     * Draws grid using selected color
     * @param w - width
     * @param h = height
     * @param color
     */
    function drawGrid(w, h, color) {
        ctx.strokeStyle = color;
        ctx.moveTo(0, 0);
        var x = 0;
        while (x < w) {
            ctx.lineTo(x, h);
            x += CELL;
            ctx.moveTo(x, 0);
        }
        var y = 0;
        while (y < h) {
            ctx.lineTo(w, y);
            y += CELL;
            ctx.moveTo(0, y);
        }
        ctx.stroke();
    }

    function cellIsEmpty(cell) {
        return cell < 0;
    }

    function getColor(index) {
        return index < 0 ? BACKGROUND_COLOR : CELL_COLORS[index];
    }

    fallingItem.pos = {
        x: -1,
        y: -1,
        /**
         * Move the item to left border
         */
        moveLeft: function () {
            if ((this.x > 0) && (cellIsEmpty(grid[this.x - 1][this.y]))) {
                clearCell(this.x, this.y);
                drawCell(--this.x, this.y, fallingItem.color);
            }
        },
        /**
         * Move the item to right border
         */
        moveRight: function () {
            if ((this.x >= 0) && (this.x + 1 < sizeX) && (cellIsEmpty(grid[this.x + 1][this.y]))) {
                clearCell(this.x, this.y);
                drawCell(++this.x, this.y, fallingItem.color);
            }
        },
        /**
         * Accelerates falling item
         */
        moveBottom: function () {
            if ((this.y < sizeY - 1) && (cellIsEmpty(grid[this.x][this.y + 1]))) {
                clearCell(this.x, this.y);
                drawCell(this.x, ++this.y, fallingItem.color);
            } else {
                falling();
            }
        }
    };
    fallingItem.color = -1;
    fallingItem.index = -1;

    /**
     * Fill cell in target position in selected color
     * @param X - position
     * @param Y - position
     * @param colorIndex - Index of selected color in array of {@link CELL_COLORS}
     */
    function drawCell(X, Y, colorIndex) {
        ctx.fillStyle = getColor(colorIndex);
        ctx.fillRect(X * CELL + 1, Y * CELL + 1, CELL - 2, CELL - 2);
        grid[X][Y] = colorIndex;
    }

    /**
     * Fill cell in target position in {@link BACKGROUND_COLOR} color
     * @param X - position
     * @param Y - position
     */
    function clearCell(X, Y) {
        drawCell(X, Y, -1);
    }

    /**
     * Generate the new item with random color and random select position of the last row that contains empty cells
     * @returns {boolean} - If the item is generated will return true
     */
    function generateItem() {
        fallingItem.index++;
        var pos_x = -1;
        var rowEmptyCells = [];
        for (var x = 0; x < sizeX; x++) {
            if (cellIsEmpty(grid[x][0])) {
                rowEmptyCells.push(x);
            }
        }
        if (rowEmptyCells.length > 0) {
            pos_x = rowEmptyCells[getRandomInt(0, rowEmptyCells.length - 1)];
        }
        fallingItem.pos.x = pos_x;
        fallingItem.pos.y = -1;
        fallingItem.color = getRandomInt(0, CELL_COLORS.length - 1);
        return pos_x >= 0;
    }

    /**
     * find line to clear
     * @param cell_x
     * @param cell_y
     * @returns {number} Count of cleared cells
     */
    function clearAxisX(cell_x, cell_y) {
        var count_L = 0;
        var count_R = 0;
        var x = cell_x;
        while (x < sizeX - 1) {
            if (grid[++x][cell_y] === fallingItem.color) {
                count_R++;
            } else break;
        }
        x = cell_x;
        while (x > 0) {
            if (grid[--x][cell_y] === fallingItem.color) {
                count_L++;
            } else break;
        }
        var count = count_L + count_R;
        if (count >= 2) {
            while (count_R > 0) {
                clearCell(cell_x + count_R--, cell_y);
            }
            while (count_L > 0) {
                clearCell(cell_x - count_L--, cell_y);
            }
            return count;
        }
        return 0;
    }

    /**
     * find line to clear
     * @param cell_x
     * @param cell_y
     * @returns {number} Count of cleared cells
     */
    function clearAxisY(cell_x, cell_y) {
        var count_T = 0;
        var count_B = 0;
        var y = cell_y;
        while (y < sizeY - 1) {
            if (grid[cell_x][++y] === fallingItem.color) {
                count_B++;
            } else break;
        }
        y = cell_y;
        while (y > 0) {
            if (grid[cell_x][--y] === fallingItem.color) {
                count_T++;
            } else break;
        }
        var count = count_T + count_B;
        if (count >= 2) {
            while (count_B > 0) {
                clearCell(cell_x, cell_y + count_B--);
            }
            while (count_T > 0) {
                clearCell(cell_x, cell_y - count_T--);
            }
            return count;
        }
        return 0;
    }

    /**
     * If is allow - moving item to bottom
     * else check row to clean
     */
    function falling() {
        var pos = fallingItem.pos;
        var countCleared = 0;
        if (pos.y < sizeY - 1) {
            if (cellIsEmpty(grid[pos.x][pos.y + 1])) {
                clearCell(pos.x, pos.y);
                drawCell(pos.x, ++pos.y, fallingItem.color);
                return;
            }
            countCleared += clearAxisY(pos.x, pos.y);
        }
        countCleared += clearAxisX(pos.x, pos.y);
        if (countCleared > 0) {
            clearCell(pos.x, pos.y);
            score += countCleared * Math.round(score / 1000 + 1) + 1;
            printScore(score);
        }
        stopDrop();
        dropItem();
    }

    /**
     * Prints out the score
     * @param value
     */
    function printScore(value) {
        jqueryScore.text(value);
    }

    /**
     * Generates and throws the new item
     */
    function dropItem() {
        if (!generateItem()) {
            printResult();
            return;
        }
        timer.start(falling);
    };

    /**
     * Stops falling of the item
     */
    function stopDrop() {
        timer.stop();
    }

    /**
     * Stops tetris
     */
    TETRIS.stop = function () {
        timer.stop();
        init();
    };

    /**
     * Restarts the Tetris game
     */
    TETRIS.restart = function () {
        this.stop();
        dropItem();
    };

    /**
     * Shows result message
     */
    function printResult() {
        var el = jqueryResult.get(0);
        jqueryResult.show();
        jqueryResult.css("left", (canvas.offsetWidth / 2 + canvas.offsetLeft - el.offsetWidth / 2) + "px");
        jqueryResult.css("top", (canvas.offsetHeight / 2 + canvas.offsetTop - el.offsetHeight / 2) + "px");
    }

    init();
};

$(document).ready(preparingTetris);


