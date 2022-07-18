# GithubRepos

- An App shows GitHub repositories.

- I used `Jetpack compose` for building the UI component and the  `android Paging 3` for the pagination 

- The `main` branch contains the implementation of fetching data from a single data source Network. `ReposDataSource` defines the source of data and how to retrieve data from `GitHubRepoApi`, it has a single function `load`  returns the loaded data and information about previous, next page, or error in case of failure.

- the `feature/offline-caching` branch continhs the caching implementation, with Room database which supports returning `PagingSource`. `ReposRemoteMediator` is a mediator class that loads data from the network and saves it to the database, database here is the single source of truth to the data flow.

- `GithubRepository` constructs the `Pager` object, passing configration and functions that creates the datasources. 

- This is a sample app, so I built it with a simple UI and low testing coverage!. 




- Things I would to improve the app, and  make production ready  
   
     - Improve the UI 
     - Implementing a CI pipeline 
     - Adding static code analysis tool
     - optimize app modularization 
     - Increase test coverage, add more UI and local tests 
     - Measring app perfromance with Macrobenchmark 
     - Create baseline profile to optimize app performance espicially with jetpack compose. 
     
     
 # Libraries 
 - [Hilt] (https://developer.android.com/training/dependency-injection/hilt-android)
 - [JetPack Compose] (https://developer.android.com/jetpack/compose) 
 - [Android Paging] (https://developer.android.com/topic/libraries/architecture/paging/v3-overview)
 - [Mockito] (https://github.com/mockito/mockito-kotlin)
 - [Truth] (https://github.com/google/truth)
 
     
     
     
     
     
     
