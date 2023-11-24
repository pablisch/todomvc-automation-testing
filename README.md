# TodoMVC Automation

This is a pair project to automate testing of the [TodoMVC website](https://todomvc.com/), which in this instance focusses on the [React examples page](https://todomvc.com/examples/react/#/).

## Test content explanation for coaches

Manuela and I worked using the page object model from the outset.

We started with the highest priority e2e test and worked through all e2e test cases before moving on to the highest priority non-2e2 tests.

Towards the end we stopped prioritising the test list and started prioritising what we had the most to learn from:
* We implemented an interface.
* We prioritised the challenge of local storage over test priority.
* We actively chose to tackle local storage using both Selenium methods and JSExecutor.
  
As a result of implementing the interface towards the end:
* There are duplicate files for Page and PageWithInterface.
* All subsequest tests appear only in the PageWithInterface page and tests.

Due to time at the end, there are a few loose ends:
* One legacy test left in the Test file that does not use the POM.
* None of the post interface




## Tests list base for this exercise

Or test list can be found [here](https://docs.google.com/document/d/1jlVdQ3DSBzPtOBCJIIHp-r9520iDQW0PgPY68v53K_g/edit?usp=sharing).

![Test list](images/Screenshot%202023-11-24%20at%2015.57.57.png)

