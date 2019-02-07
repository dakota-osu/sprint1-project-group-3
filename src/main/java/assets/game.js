
var isSetup = true;
var placedShips = 0;
var game;
var errors = 0;
var shipType = [];

var vertical;

document.getElementById("errorButton").addEventListener("click", errorButton);
document.getElementById("error-ok").addEventListener("click", closeError);

function errorButton() {
    errors++;
    showError("You've pressed me " + errors + " times. Keep it up!");
}

function showError(errorText) {
    document.getElementById("errorText").innerHTML = errorText;
    document.getElementById("error").style.visibility = "visible";
}

function closeError() {
    document.getElementById("error").style.visibility = "hidden";
}

function makeGrid(table, isPlayer) {
    for (i=0; i<10; i++) {
        let row = document.createElement('tr');
        for (j=0; j<10; j++) {
            let column = document.createElement('td');
            column.addEventListener("click", cellClick);
            row.appendChild(column);
        }
        table.appendChild(row);
    }
}

function markHits(board, elementId, surrenderText) {
    board.attacks.forEach((attack) => {
        let className;
        if (attack.result === "MISS"){
            className = "miss";
         }
        else if (attack.result === "HIT"){
                className = "hit";
        }

        else if (attack.result === "SUNK") {
            console.log(attack.ship.shipType);

           document.getElementById(elementId + "-" + attack.ship.shipType.toLowerCase()).classList.add("crossed-out");                                  //if sunken, cross out ship name

            className = "sink";
            attack.ship.occupiedSquares.forEach((square) => {                                                                                           //if ship sunk, grab all occupied squares of ship
                document.getElementById(elementId).rows[square.row-1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add(className);  //set all ship elements to sink class name
            });
        }
        else if (attack.result === "SURRENDER") {                                   //if you win, display surrender text, then reload the game
            alert(surrenderText);
            location.reload();
        }
        document.getElementById(elementId).rows[attack.location.row-1].cells[attack.location.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add(className);

         if (elementId === "opponent"){                                         //display last result
            document.getElementById("results-text").innerHTML = attack.result;
        }
    });

}

function redrawGrid() {
    Array.from(document.getElementById("opponent").childNodes).forEach((row) => row.remove());
    Array.from(document.getElementById("player").childNodes).forEach((row) => row.remove());
    makeGrid(document.getElementById("opponent"), false);
    makeGrid(document.getElementById("player"), true);
    if (game === undefined) {
        return;
    }

    game.playersBoard.ships.forEach((ship) => ship.occupiedSquares.forEach((square) => {
        document.getElementById("player").rows[square.row-1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add("occupied");
    }));

    markHits(game.opponentsBoard, "opponent", "You won the game");
    markHits(game.playersBoard, "player", "You lost the game");
}

var oldListener;
function registerCellListener(f) {
    let el = document.getElementById("player");
    for (i=0; i<10; i++) {
        for (j=0; j<10; j++) {
            let cell = el.rows[i].cells[j];
            cell.removeEventListener("mouseover", oldListener);
            cell.removeEventListener("mouseout", oldListener);
            cell.addEventListener("mouseover", f);
            cell.addEventListener("mouseout", f);
        }
    }
    oldListener = f;
}

function cellClick() {
    let row = this.parentNode.rowIndex + 1;
    let col = String.fromCharCode(this.cellIndex + 65);
    if (isSetup) {
        // vertical = document.getElementById("is_vertical").checked;
        sendXhr("POST", "/place", {game: game, shipType: shipType[0], x: row, y: col, isVertical: vertical}, function(data) {
            game = data;
            redrawGrid();
            updateShipList();
            placedShips++;
//            isSetup = false;
//            shipType = "BADSHIP";
            if (placedShips == 3) {
                isSetup = false;
                registerCellListener((e) => {});
            }
        });
    } else {

            sendXhr("POST", "/attack", {game: game, x: row, y: col}, function(data) {
                game = data;
                redrawGrid();
            })

    }
}

function updateShipList() {
    shipType.shift();
    if(shipType.length === 2) {
        registerCellListener(place(3))
        console.log("asdf");
    } else if(shipType.length === 1) {
        registerCellListener(place(2))
    }
}

function sendXhr(method, url, data, handler) {
    var req = new XMLHttpRequest();
    req.addEventListener("load", function(event) {
        if (req.status != 200) {
            if (placedShips == 3) {
                showError("Cannot target that spot");
                return;
            } else {
                showError("Out of bounds");
                return;
            }
        }
        handler(JSON.parse(req.responseText));
    });
    req.open(method, url);
    req.setRequestHeader("Content-Type", "application/json");
    req.send(JSON.stringify(data));
}

function place(size) {
    return function() {
        let row = this.parentNode.rowIndex;
        let col = this.cellIndex;
        vertical = document.getElementById("is_vertical").checked;
        let table = document.getElementById("player");
        for (let i=0; i<size; i++) {
            let cell;
            if(vertical) {
                let tableRow = table.rows[row+i];
                if (tableRow === undefined) {
                    // ship is over the edge; let the back end deal with it
                    break;
                }
                cell = tableRow.cells[col];
            } else {
                cell = table.rows[row].cells[col+i];
            }
            if (cell === undefined) {
                // ship is over the edge; let the back end deal with it
                break;
            }
            cell.classList.toggle("placed");
        }
    }
}

function initGame() {
    makeGrid(document.getElementById("opponent"), false);
    makeGrid(document.getElementById("player"), true);


    shipType = ["BATTLESHIP", "DESTROYER", "MINESWEEPER"];
    registerCellListener(place(4));

    // document.getElementById("place_minesweeper").addEventListener("click", function(e) {
    //     shipType = "MINESWEEPER";
    //    registerCellListener(place(2));
    // });
    // document.getElementById("place_destroyer").addEventListener("click", function(e) {
    //     shipType = "DESTROYER";
    //    registerCellListener(place(3));
    // });
    // document.getElementById("place_battleship").addEventListener("click", function(e) {
    //     shipType = "BATTLESHIP";
    //    registerCellListener(place(4));
    // });


    sendXhr("GET", "/game", {}, function(data) {
        game = data;
    });
};