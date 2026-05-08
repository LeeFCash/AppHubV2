let randomNumber = Math.floor(Math.random() * 100) + 1;
let attempts = 0;
let maxAttempts = 5;
let gameOver = false;

function checkGuess() {
    if (gameOver) return;

    const input = document.getElementById("guessInput");
    const message = document.getElementById("message");
    const tries = document.getElementById("tries");

    let guess = Number(input.value);

    if (!guess) {
        message.innerText = "Enter a valid number!";
        return;
    }

    attempts++;

    if (guess === randomNumber) {
        message.innerText = " You win!";
        gameOver = true;
    } else if (attempts >= maxAttempts) {
        message.innerText = ` You lost! Number was ${randomNumber}`;
        gameOver = true;
    } else if (guess > randomNumber) {
        message.innerText = "Too high!";
    } else {
        message.innerText = "Too low!";
    }

    tries.innerText = `Attempts: ${attempts} / ${maxAttempts}`;
    input.value = "";
}

function resetGame() {
    randomNumber = Math.floor(Math.random() * 100) + 1;
    attempts = 0;
    gameOver = false;

    document.getElementById("message").innerText = "";
    document.getElementById("tries").innerText = "";
    document.getElementById("guessInput").value = "";
}
