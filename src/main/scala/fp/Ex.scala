package fp

/*
    One of the teams used task board to organize their work in project aimed to launch
    a simple video streaming service. The task board was divided into columns: To Do,
    In Progress, To Verify, Done. Subsequent system features were placed on cards on
    the task board and then moved between that columns, as work progressed.

    File task_history.csv stores all events that happened every day on the task board. Each event consists of:
    - date of event in format YYYY-MM-DD,
    - name of the task, which is also a task identifier,
    - type of event; there are three possibilities: task creation, task movement to another column, task removal from board,
    - additional data depending on type of event:
        - for task creation event: difficulty level, (integer from 1 to 5, optional)
        - for task movement event: target column,
        - for task removal event: no additional data.

    All events in the file are sorted chronologically. The project started on 2017-01-01 and finished on 2017-01-18.

    Your role is to restore task board state on 2017-01-15. The entire code should be placed in this file.

    For fun: Try to implement every used function as single expression function i.e. 'fun someName(...) = ...'
    Limitation doesn't apply to lambdas. Lambdas can contain multiple lines.

    Follow the steps. Good luck.
 */

import java.time.LocalDate

import scala.io.Source

object Ex {

  /*
  STEP 1:

  Fill Column enum according to task board description above.
   */
  sealed trait Column

  /*
  STEP 2:

  Each line in the file contains single event. Before you try to implement file parsing,
  create all event types as classes extending TaskEvent. We'll be using LocalDate
  to store date.

  Hints:

  - data classes are rather discouraged in this step (inheritance).
   */
  sealed trait TaskEvent {
    def date: LocalDate
    def taskName: String
  }

  /*
  In the CSV file, task creation event may not have task's difficulty level.
  Take the default difficulty level as defined below.
   */
  val DEFAULT_DIFFICULTY = 3

  /*
  STEP 3:

  Implement parseFile(). The function takes a path to CSV file with task history (src/main/resources/task_history.csv)
  and returns list of parsed events.

  Hints:
  - use Files.readAllLines() to read the file,
  - do not use sophisticated solution for parsing csv, just split lines with String.split(),
  - 'when' expression can be handy,
  - in case of an error, throw IllegalArgumentException with meaningful message.
   */
  def parseFile(path: String): List[TaskEvent] = {
    def mapToEvent(line: String): TaskEvent = ???

    Source
      .fromResource(path)
      .getLines()
      .map(mapToEvent)
      .toList
  }

  /*
  STEP 4:

  Now, implement class that represents state of a task board. The task board should be immutable,
  what implies that all methods modifying its state should return new task board with that state.

  Hints:
  - due to immutability and no inheritance, data class can be quite useful,
  - it is very likely you will need Task class for internal task representation; feel free
      to create one, also as immutable type,
  - use previously defined Column enum to represent columns.
   */

  class TaskBoard() {

    /*
    STEP 5:

    Write apply() function which returns modified state of the task board based on an event.

    Hints:
    - once again, 'when' can be good choice,
    - remember to apply default difficulty level,
    - in case of an error, throw IllegalArgumentException with meaningful message.
     */
    def apply(event: TaskEvent): TaskBoard = ???

    /*
    STEP 6:

    Implement print() function. Print function should display current state of the task board on console.
    The display format is this:

    TO DO:
    - <task name> (<difficulty level>)
    IN PROGRESS:
    - <task name> (<difficulty level>)
    TO VERIFY:
    - <task name> (<difficulty level>)
    DONE:
    - <task name> (<difficulty level>)

    for example:

    TO DO:
    - Add account management (2)
    - Implement video upload (5)
    IN PROGRESS:
    TO VERIFY:
    DONE:

    In each column, tasks should be sorted by name.

    Hints:
    - use string templates,
    - you can iterate over enum values and replace _ to whitespace to have column names.
     */
    def print(): Unit = ???

  }

  /*
  STEP 6:

  Finally, implement main function. A target date should be specified as a program argument.
  Remember to filter out all events that happened after that date. They won't be needed.
  The final console output should look like this:

  TO DO:
  IN PROGRESS:
  - Create video search (3)
  TO VERIFY:
  - Add playlist management (3)
  - Implement video comments (4)
  DONE:

  which is a correct solution of this exercise.
   */
  def main(args: Array[String]): Unit = {

    val events = parseFile("task_history.csv")

    def aggr(board: TaskBoard, event: TaskEvent): TaskBoard = ???
    val board = events
      .filter(_.date.isBefore(LocalDate.parse("2017-01-16")))
      .foldLeft(new TaskBoard())(aggr)

    board.print()
  }
}
