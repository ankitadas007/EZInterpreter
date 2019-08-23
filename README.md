# EZ Programming Language

Although the EZ language only has five keywords, it allows users to declare and call functions. 
The five keywords in the EZ language are as follows:

* proc <<identifier> identifier> declares a procedure named identifier
* call <<identifier> identifier> calls procedure identifier
* stop ends execution of current procedure
* echo <<identifier> text> displays lowercase text and the values of uppercase variables until of the end of the line followed by a new line
* copy <<identifier> var> <in‐expr> evaluates an infix expression consisting of uppercase variables, single digit values, addition signs, and multiplication signs and places the result in variable var. 

There are some restrictions on the function calls that are allowed in the EZ language.

* A function may call only functions that have previously been declared.
* The program begins executing the function named main. This must be the last function
declared. The program terminates when a stop command is encountered in the main
function.
* Variables are only allowed to be uppercase letters. Thus, no more than 26 variables can be
used in a program. All should be initialized to 0.
* All variables are global.
* The echo command can only print lower case text. If an upper case character is in the echo
command, the command should treat it as a variable and display its value.
* As in most languages, when one function calls another, execution is temporarily suspended in
the first function until the called function terminates.
