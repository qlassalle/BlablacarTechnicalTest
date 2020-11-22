# BlaBlaCar technical test

## How to run

This project is a maven project, so it can be easily imported in your favorite IDE. First run a `mvn install` to 
download dependencies. Test suite can be ran with `mvn test` and you can run the Main method of this project with 
`mvn compile exec:java`. If you want to test this program with your own input file, you can rename the name of the file
 passed to the `LawnController` on line 7 of the `Main` class and add your file in the `src/resources/input` directory,
  next to the `two_mowers.txt` file.

## Approach

I tried to separate as much as possible the core of this program (which is moving mowers in a lawn) with its details (like 
having the input coming from a file). Therefore, I organized my code in two different packages, `core` which would contain 
my domain objects and services, and an `application` package, which would be in charge of reading the file and giving 
instructions. I didn't want the core of my app to know any details of implementation, that's why my `LawnService` only has a method
which accepts a queue of `Instruction` and has not any interest in knowing where those instructions come from. Therefore, 
it's the role of the controller to give proper data to the service. 

I relied on queues to set and perform instructions as order matters in this precise use case. Also, it feels natural to
poll the instruction, execute it, and remove it from our queue of instruction. I thought that it was a nice way of designing 
it.  

I also tried to keep coupling as low as possible. That's why I didn't want to tie a `Mower` to a `Lawn`. In my opinion, a 
`Mower` does not need to know about its `Lawn`, it simply has to change the orientation and move if asked. The `Lawn` 
is therefore in charge of managing the different mowers and ensure that they can perform the instruction they have to do.

### Testing 

Regarding testing, I always started by writing my tests first in order to write the simplest possible app. Having a large 
test suite allowed me to refactor peacefully. I used parameterized tests extensively as my test cases were really close. 
I set the tests in `LawnControllerTest` as `RepeatableTest` to ensure that my multithreading approach was working.

### Multithreading

Concerning the multithreading part, I had different possibilities:
* The easiest one was to set my `moveMower` method as synchronized. This would have work but isn't suitable as it would 
prevent all the other `Mower` to move. We would therefore behave like a single threaded app but with a lot of threads 
waiting, definitely not a good approach.
* Another easy approach was to add a `synchronized` block on the `lawn` of the `LawnService`. This would have worked too
but would not have been efficient neither as it would have prevented all the other threads to access the `lawn`.

The third possibility and the one I chose, was to simply put a lock on the current cell of the mower, and a lock on the
cell the mower has to go. With this approach, we simply lock two cells and do not impact any other mower that can be
working on other cells. Once the move is performed, we simply release the locks.

## Technical choices

The technical choices for this project were pretty straightforward. I used only the libraries that were useful in my
opinion:

* junit: Required for testing
* lombok: Nice to have this lib to avoid writing boilerplate code