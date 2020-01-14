# isbusy-backend
Web service API deployed with spring boot

___
## Pre-requisites
1.  [Java 1.8](https://www.oracle.com/technetwork/java/javaee/downloads/jdk8-downloads-2133151.html) installed with [JAVA_HOME](https://docs.oracle.com/cd/E19182-01/820-7851/inst_cli_jdk_javahome_t/) system variable properly set up

2. [Maven 3.6.1](https://maven.apache.org/download.cgi) installed with [MAVEN_HOME](https://maven.apache.org/install.html) system variable set up
    + Used download option: **Binary zip archive** 
3. IDE of your choice : __Importing the project will depend on your IDE__ (I am using VS code) 
    + [Eclipse](https://vaadin.com/tutorials/import-maven-project-eclipse)
    + [Netbeans](https://vaadin.com/tutorials/import-maven-project-netbeans)
    + [Working with Java in VS Code](https://code.visualstudio.com/docs/java/java-project#_generate-project-from-maven-archetype)
4. [Postman](https://www.getpostman.com/downloads/) used to test API endpoints
___

## Setting up the project

1.  Clone this repository


2.  Open in your IDE 


3. Using a terminal, **cd** into the project directory and type in : **`mvn install`** 

    + This will install all project dependencies (Including Spring Boot, Spring MVC, Hibernate, MySQL driver, etc..)


4. Once the project set up, create a new MySQL database and name it **isBusy**

    + You can do this programmatically by opening **src/main/resources/application.properties** and changing **spring.jpa.hibernate.ddl-auto** value to **create** (After having changed the credentials required to connect to the database)

    + Once the database created, you should change the value back to **none** (Otherwise, you will risk losing data every time you start the application)


5. Change database credentials in **src/main/resources/application.properties** to your credentials

6. Once the project is imported, type **`mvn spring-boot:run`** into your terminal

7. Application should start on port 8080



___
## Git Workflow
1. Set up this repository as [remote](https://articles.assembla.com/using-git/how-to-add-a-new-remote-to-your-git-repo) (Waiting to migrate to **BitBucket**)
2. Before creating a new feature, **create a new branch** (choose a meaningful name for your branches)
```git
git branch <branch_name>
```
3. Checkout to the new branch and start developing
```git
git checkout <branch_name>
```
4. Once you have a working prototype, stage changes and commit them with a meaningful message
```git
git add .
git commit -m <message>
```
5. If you are going to merge or push to another branch, make sure to pull before pushing to get latest repository updates
```git 
git pull 
git push origin <branch_name>
```
Further reading: [Git Workflow](https://www.atlassian.com/git/tutorials/comparing-workflows)


## Endpoints

### Users
* **[GET]  /users :** List all users
* **[POST] /users/register :** To add a new user
* **[GET]  /users/me :** Shows the current user's details
* **[POST] /users/delete :** Deletes the current user
* **[POST] /users/update :** Updates the current user
* **[GET]  /users/{id} :** To get a certain user

### Emplacements - Places

* **[POST] /emplacements/add :** For **Users** to suggest a new location
* **[GET] /emplacements/categorie/{categorieId}/{radius}/{position} :** Get some places based on the categorical criteria, the position and the radius of search
* **[GET] /emplacements/near/{position} :** Gives all places which are close to the position given
* **[GET] /emplacements/pending :** For **Admins** to see user's propositions of places which are not found by the API
* **[GET] /emplacements/query/{name}/{radius}/{position} :** Get some places based on the name criteria
* **[GET] /emplacements/{id} :** Gives all information about a certain place
* **[POST] /emplacements/{id}/approve :** For **Admins** to approve a certain place suggested by a user
* **[POST] /emplacements/{id}/ignore :** For **Admins** to ignore a certain place suggested by a user
* **[GET] /emplacements/{id}/evaluations :** Gives all evaluations (or statistics) about a certain place
* **[GET] /emplacements/{id}/{jour} :** Gives all evaluations (or statistics) about a certain place based on a certain week day
* **[POST] /emplacements/{id}/evaluer :** To add a new evaluation to a certain place (is it full, empty or closed)
* **[GET] /favories :** Get current user's all favorite places 
* **[POST] /favories/add/{id} :** Makes a certain place as favorite place to the current user 
* **[POST] /favories/delete/{id} :** Removes a certain place from the current user's list of favorite places 
* **[GET] /admin/stats :** Gives some statistics to the **Admins** about places and evaluations 

### Categories

* **[GET] /categories :** Lists all available categories
* **[POST] /categories/add :** To add a new category
* **[GET] /categories/id/{id} :** Get all information about a certain category by ID
* **[GET] /categories/name/{name} :** Get all information about a certain category by **name** of the category
* **[PATCH] /categories/{id}/update :** Updates a certain information about the selected category


### Feedbacks
* **[GET] /reclamations :** Lists all feedbacks reported by users
* **[POST] /reclamations/add :** Add a new feedback

