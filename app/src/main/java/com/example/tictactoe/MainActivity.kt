package com.example.tictactoe

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe.R

class MainActivity : AppCompatActivity() {

    private lateinit var tvStatus: TextView
    private lateinit var btnPlayAgain: Button
    private lateinit var cells: List<Button>

    private val board: Array<Char?> = arrayOfNulls(9)
    private var currentPlayer: Char = 'X'
    private var gameOver: Boolean = false

    private val winLines = arrayOf(
        intArrayOf(0, 1, 2),
        intArrayOf(3, 4, 5),
        intArrayOf(6, 7, 8),
        intArrayOf(0, 3, 6),
        intArrayOf(1, 4, 7),
        intArrayOf(2, 5, 8),
        intArrayOf(0, 4, 8),
        intArrayOf(2, 4, 6)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvStatus = findViewById(R.id.tvStatus)
        btnPlayAgain = findViewById(R.id.btnPlayAgain)

        cells = listOf(
            findViewById(R.id.btn0),
            findViewById(R.id.btn1),
            findViewById(R.id.btn2),
            findViewById(R.id.btn3),
            findViewById(R.id.btn4),
            findViewById(R.id.btn5),
            findViewById(R.id.btn6),
            findViewById(R.id.btn7),
            findViewById(R.id.btn8),
        )

        cells.forEachIndexed { index, button ->
            button.setOnClickListener { onCellClicked(index) }
        }

        btnPlayAgain.setOnClickListener { resetGame() }

        resetGame()
    }

    private fun onCellClicked(index: Int) {
        if (gameOver) return
        if (board[index] != null) return

        board[index] = currentPlayer
        cells[index].text = currentPlayer.toString()
        cells[index].isEnabled = false

        val winner = checkWinner()
        when {
            winner != null -> {
                gameOver = true
                tvStatus.text = "Player $winner wins!"
                endGameUI()
            }
            isDraw() -> {
                gameOver = true
                tvStatus.text = "It's a draw!"
                endGameUI()
            }
            else -> {
                currentPlayer = if (currentPlayer == 'X') 'O' else 'X'
                tvStatus.text = "Player $currentPlayer's turn"
            }
        }
    }

    private fun checkWinner(): Char? {
        for (line in winLines) {
            val a = board[line[0]]
            val b = board[line[1]]
            val c = board[line[2]]
            if (a != null && a == b && b == c) return a
        }
        return null
    }

    private fun isDraw(): Boolean =
        board.all { it != null } && checkWinner() == null

    private fun endGameUI() {
        cells.forEach { it.isEnabled = false }
        btnPlayAgain.isEnabled = true
        btnPlayAgain.alpha = 1.0f
    }

    private fun resetGame() {
        for (i in board.indices) board[i] = null
        currentPlayer = 'X'
        gameOver = false

        tvStatus.text = "Player X's turn"

        cells.forEach {
            it.text = ""
            it.isEnabled = true
        }

        btnPlayAgain.isEnabled = false
        btnPlayAgain.alpha = 0.5f
    }
}
