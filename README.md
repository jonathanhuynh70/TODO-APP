# TODO-APP

A simple Todo app that supports CRUD operations with a user-friendly interface.

Users are able to create, read, update and delete task in this ToDo app, with creation and updating of tasks being handled with a react form.

This project consists of two applications: one is a Spring Boot Rest API called todo_app and another is a React and Typescript application called
todo_frontend.

<b>Prerequisites</b>

<hr>

- Latest JDK and Maven
- npm
- mysql

<b>Set up</b>

<hr></hr>

Clone the repository:

<pre>git clone https://github.com/jonathanhuynh70/TODO-APP.git</pre>

Navigate to the newly created folder:

<pre>cd TODO-APP</pre>

<b>Frontend</b>

Install latest version of npm

Navigate to react-frontend subfolder:

<pre>cd todo_frontend</pre>

Install the modules

<pre>npm i</pre>

Start the application on local host:

<pre>npm run dev</pre>

Navigate to:

http://localhost:5173

<hr></hr>
<b>Backend</b>

Install latest JDK
Install latest Maven

Navigate to spring-backend subfolder:

<pre>cd todo_app</pre>

Install all of the dependencies and package the project
<pre>mvn clean package</pre>

then run the project, running on localhost:8080
<pre>mvn spring-boot:run</pre>

<hr></hr>
<b>Design Decisions:</b>



<b>Frontend</b>


- Use of DTOs:
    - Data Transfer Objects are a useful of representing returned objects from the backend and help the developers understand the objects that are being sent. Its acts as a standard mapping between the frontend and backend, acting as a schema for developers to use when working on api calls. Makes working with api responses easier as well since there is a standardised object the data can be turned into.

- Use of state with the Modal window for task creation:
    - The Modal window is governed by the setModalOpen in ToDoList. I thought ToDoList.tsx was an appropriate spot for this modal window because it would be editing and manipulating values of childern of ToDoList. It would not make sense for the modal window to be rendered in the childern of the ToDoList; ToDoItem, either because there would be the need for a state variable in every child. - The state is manipulated by passing through the setter for modalOpen through to each ToDoItem.



<b>Backend</b>



- Modular design leaves door open for further features to be added:
    - Classes are not highly coupled and treated as a black-box, any development on any class in the repository will not result in much refactoring elsewhere. The classes work independently of each other as long as the return type is the same.

<hr></hr>
<b>Trade-offs and improvements:</b>


- Use CORS properly:
    - CORS, Cross-Origin Resource Sharing, is a browser security feature that explicitly grants permission to access resources from its api interface. 
    - We at the moment do not implement CORS correctly, because in the TaskController class we ar9 just allowing every host to access and retrive the resources from the springboot backend. We would greatly increase the security of our application if we were to configure CORS correctly.  

- Data validation for the task inputs and API calls:
    - An improvement that can be made is the use of data validation for the inputs in the modal window in the frontend. We are currently just checking for null values, but we should also be checking for other things such as malicious code, if we were to allow dates we would need to check for the formatting, if we were checking.
    - Alongside this, API calls can be made where a faulty object is given in the request body, and this can create task objects that are missing titles and descriptions. Should be throwing an error instead. 

- Using Integers as ID instead of other formats:
    - Given the small nature of this project, I thought using Integers would suffice. - Pros: Its fast to index and number, and takes up little memory - Con: - It has a limited range and this matters more for larger applications - in distributed databases, it is hard to enforce uniqueness, only works well in a single-serve application like this.
 


Other options for IDs were:

- Strings: were deemed to not be worth it due ot the storage size and unnecessary nature of having embedded information in the ids.
    Pros:
        - URL friendly
        - can embed information into the ID
    Cons:
        - Large storage
        - slower comparisons
        - still some collision risk
- UUIDs: were deemed unnecessary and takes up too much storage
    Pros:
        - Globally unique
    Cons:
        - Storage size
        - Slower Performance

    IF this application began to experience more database collisions and overflowing ids, then we would switch over to string ids.

    IF this application advanced or grew in magnitude to the point where the we could use a distributed system, then we would port over to using UUIDs.
