# isbusy-backend
Web service API deployed with spring boot

___
## Pre-requisites
1. [Java 1.8](https://www.oracle.com/technetwork/java/javaee/downloads/jdk8-downloads-2133151.html) installed with [JAVA_HOME](https://docs.oracle.com/cd/E19182-01/820-7851/inst_cli_jdk_javahome_t/) system variable properly set up


2. [Maven 3.6.1](https://maven.apache.org/download.cgi) installed with [MAVEN_HOME](https://maven.apache.org/install.html) system variable set up
    + Used download option: **Binary zip archive** 
3. IDE of your choice : __Importing the project will depend on your IDE__ (I am using VS code) 
    + [Eclipse](https://vaadin.com/tutorials/import-maven-project-eclipse)
    + [Netbeans](https://vaadin.com/tutorials/import-maven-project-netbeans)
    + [Working with Java in VS Code](https://code.visualstudio.com/docs/java/java-project#_generate-project-from-maven-archetype)
4. [Postman](https://www.getpostman.com/downloads/) used to test API endpoints
___

## Setting up the project

1. Clone this repository


2. Open in your IDE 


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
