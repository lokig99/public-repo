package GameLogic

import scala.annotation.tailrec

object Saper {

  var Instance = new Saper(10)

  def createNewBoard(boardSize: Int) {

    require(boardSize > 0)
    Instance = new Saper(boardSize)
  }

  def getBoardSizeX = Instance.BOARD_SIZE_X
  def getBoardSizeY = Instance.BOARD_SIZE_Y
  def prepareBoard = Instance.prepare_board
  def flagField(x: Int, y: Int) = Instance.flag_field(x, y)
  def revealField(x: Int, y: Int) = Instance.reveal_field(x, y)

  def isVisited(x: Int, y: Int) = Instance.is_visited(x, y)
  def isRevealed(x: Int, y: Int) = Instance.is_revealed(x, y)
  def isFlagged(x: Int, y: Int) = Instance.is_flagged(x, y)
  def isBomb(x: Int, y: Int) = Instance.is_bomb(x, y)
  def getValue(x: Int, y: Int) = Instance.get_value(x, y)
  def isGameOver = Instance.gameOver
  def isGameWon = Instance.is_game_won()
}

class Saper(boardSize: Int) extends SaperCaller {

  val BOARD_SIZE_RATIO = 16 / 9f //16:9 = 1.78   4:3 = 1.33   1:1 = 1   2:1 = 2
  val BOARD_SIZE_X = boardSize
  val BOARD_SIZE_Y = (BOARD_SIZE_RATIO * BOARD_SIZE_X).intValue()
  val FIELDS_QUANTITY = BOARD_SIZE_X * BOARD_SIZE_Y
  val BOMB_QUANTITY = { val tmp = (FIELDS_QUANTITY * 0.15).intValue(); if (tmp > 0) tmp else 1 }

  var board = Array.ofDim[(Int, Boolean, Boolean, Boolean, Boolean)](BOARD_SIZE_X, BOARD_SIZE_Y)
  //(bombsAround; isBomb; isFlagged, isRevealed, isVisisted)
  var bombCords: List[(Int, Int)] = Nil
  var listeners = Array.ofDim[SaperListener](BOARD_SIZE_X, BOARD_SIZE_Y);
  var listOfAdjacentFields: List[(Int, Int)] = Nil
  var gameOver = false

  reset_board

  private def reset_board = {

    for (i <- 0 until BOARD_SIZE_X) {
      for (j <- 0 until BOARD_SIZE_Y)
        board(i)(j) = (0, false, false, false, false)
    }
  }

  private def prepare_board = {

    gameOver = false
    reset_board
    place_bombs
    generate_fields(bombCords)
  }

  private def place_bombs = {

    var bombsLeft = BOMB_QUANTITY

    while (bombsLeft > 0) {

      var cordX = (Math.random() * BOARD_SIZE_X).intValue()
      var cordY = (Math.random() * BOARD_SIZE_Y).intValue()

      if (!is_bomb(cordX, cordY)) {

        board(cordX)(cordY) = (0, true, false, false, false)
        bombCords = (cordX, cordY) :: bombCords
        bombsLeft -= 1
      }
    }
  }

  @tailrec
  private def generate_fields(xs: List[(Int, Int)]): Unit = {

    xs match {

      case Nil => return
      case h :: t => {

        //bomb cords
        val X = h._1
        val Y = h._2

        call_surrounding_fields(X, Y, increment_value)
        generate_fields(t)
      }
    }
  }

/********************************* GETTERS  **************************************/

  val is_visited = (x: Int, y: Int) => board(x)(y)._5
  val is_revealed = (x: Int, y: Int) => board(x)(y)._4
  val is_flagged = (x: Int, y: Int) => board(x)(y)._3
  val is_bomb = (x: Int, y: Int) => board(x)(y)._2
  val get_value = (x: Int, y: Int) => board(x)(y)._1
  val is_game_won = () => (count_revealed_fields == FIELDS_QUANTITY - BOMB_QUANTITY && count_flagged_bombs == BOMB_QUANTITY)

/********************************** ACTION METHODS *******************************/

  private def flag_field(x: Int, y: Int) = {

    if (!is_revealed(x, y))
      if (is_flagged(x, y))
        board(x)(y) = (get_value(x, y), is_bomb(x, y), false, false, is_visited(x, y))

      else board(x)(y) = (get_value(x, y), is_bomb(x, y), true, false, is_visited(x, y))
  }

  private def reveal_field(x: Int, y: Int): Boolean = {

    is_revealed(x, y) match {

      case true => false
      case _ => if (is_flagged(x, y)) false else if (is_bomb(x, y)) { detonate_bombs; true } else {

        board(x)(y) = (get_value(x, y), is_bomb(x, y), is_flagged(x, y), true, is_visited(x, y))
        if (get_value(x, y) == 0) reveal_adjacent_fields(x, y)
        true
      }
    }
  }

  private def visit_field(x: Int, y: Int): Unit =
    board(x)(y) = (get_value(x, y), is_bomb(x, y), is_flagged(x, y), is_revealed(x, y), true)

  private def detonate_bombs: Unit = {

    gameOver = true

    for (cords <- bombCords) {
      board(cords._1)(cords._2) = (get_value(cords._1, cords._2), is_bomb(cords._1, cords._2), false, true, false)
      callField(cords._1, cords._2, SaperCaller.REVEAL_FIELD)
    }
  }

  private def count_revealed_fields: Int = {

    var res = 0

    for (i <- 0 until BOARD_SIZE_X)
      for (j <- 0 until BOARD_SIZE_Y)
        if (is_revealed(i, j)) res += 1
    res
  }

  private def count_flagged_bombs: Int = bombCords.foldLeft(0)((res, cord) => res + (if (is_flagged(cord._1, cord._2)) 1 else 0))

  private def reveal_adjacent_fields(x: Int, y: Int) = {

    listOfAdjacentFields = Nil

    generate_list_of_adjacent_fields(x, y)

    for (fieldCords <- listOfAdjacentFields) {

      reveal_field(fieldCords._1, fieldCords._2)
      callField(fieldCords._1, fieldCords._2, SaperCaller.REVEAL_FIELD)
    }
  }

  private def generate_list_of_adjacent_fields(x: Int, y: Int): Unit = {

    if (!(listOfAdjacentFields contains (x, y))) listOfAdjacentFields = (x, y) :: listOfAdjacentFields

    visit_field(x, y)

    if (get_value(x, y) == 0)
      call_surrounding_fields(x, y, generate_list_of_adjacent_fields, is_visited)
  }

  private def call_surrounding_fields[A](x: Int, y: Int, fun: (Int, Int) => A): Unit = call_surrounding_fields(x, y, fun, (_, _) => false)

  private def call_surrounding_fields[A](x: Int, y: Int, fun: (Int, Int) => A, predicate: (Int, Int) => Boolean): Unit = {

    //cordinates of adjacent fields
    val RIGHT_Y = y + 1
    val LEFT_Y = y - 1
    val TOP_X = x - 1
    val BOTTOM_X = x + 1

    //RIGTH
    if (validate_cords(x, RIGHT_Y) && !predicate(x, RIGHT_Y))
      fun(x, RIGHT_Y)

    //LEFT
    if (validate_cords(x, LEFT_Y) && !predicate(x, LEFT_Y))
      fun(x, LEFT_Y)

    //TOP
    if (validate_cords(TOP_X, y) && !predicate(TOP_X, y))
      fun(TOP_X, y)

    //BOTTOM
    if (validate_cords(BOTTOM_X, y) && !predicate(BOTTOM_X, y))
      fun(BOTTOM_X, y)

    //DIAGONAL TOP-RIGTH
    if (validate_cords(TOP_X, RIGHT_Y) && !predicate(TOP_X, RIGHT_Y))
      fun(TOP_X, RIGHT_Y)

    //DIAGONAL TOP-LEFT
    if (validate_cords(TOP_X, LEFT_Y) && !predicate(TOP_X, LEFT_Y))
      fun(TOP_X, LEFT_Y)

    //DIAGONAL BOTTOM-RIGTH
    if (validate_cords(BOTTOM_X, RIGHT_Y) && !predicate(BOTTOM_X, RIGHT_Y))
      fun(BOTTOM_X, RIGHT_Y)

    //DIAGONAL BOTTOM-LEFT
    if (validate_cords(BOTTOM_X, LEFT_Y) && !predicate(BOTTOM_X, LEFT_Y))
      fun(BOTTOM_X, LEFT_Y)
  }

  private def validate_cords(x: Int, y: Int) = (((x >= 0) && (x < BOARD_SIZE_X)) && ((y >= 0) && (y < BOARD_SIZE_Y)))

  private def increment_value(x: Int, y: Int) =
    board(x)(y) = (get_value(x, y) + 1, is_bomb(x, y), is_flagged(x, y), is_revealed(x, y), is_visited(x, y))

/********************************** OBSERVER METHODS *******************************/

  def callAllFields(ActionCode: Int): Unit = {

    for (i <- 0 until this.listeners.length)
      for (j <- 0 until this.listeners(0).length)

        listeners(i)(j).saperUpdateReceived(ActionCode)
  }

  def callField(x: Int, y: Int, ActionCode: Int): Unit = listeners(x)(y).saperUpdateReceived(ActionCode)

  def addSaperListener(listener: SaperListener): Unit = this.listeners(listener.getCordX)(listener.getCordY) = listener
}