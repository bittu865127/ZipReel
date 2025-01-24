# ZipReel

**Problem Statement**
We are developing a movie content management system - ZipReel for a streaming platform, where users can search and access movie information efficiently. The system needs to handle frequent searches and provide fast responses through a multi-level caching mechanism.

As an engineer, your task is to build a feature that allows:

Registration of movies and users in the system
Implementation of a multi-level cache to optimize search operations
Efficient retrieval of movie information based on various parameters
This system aims to provide lightning-fast search results via intelligent caching.


**Explanations**

**What is the multi-level cache?**

L1 Cache: User-specific recent searches (max 5 entries per user)
L2 Cache: Global popular searches (max 20 entries)
Primary Store: All movie data in-memory

**What is a cache hit/miss?**

When searching, system checks caches in order: L1 -> L2 -> Primary Store
Cache hit: Data found in any cache level
Cache miss: Data not found, requiring search in base data

**Requirements**

**System should be able to:**
1. Add movies with following attributes:
    Movie ID (unique)
    Title
    Genre
    Release Year
    Rating
2. Register users with:
    User ID (unique)
    Name
    Preferred Genre
   
3. Implement multi-level caching:
    L1: User-specific cache
          Maximum 5 entries per user
          Implements LRU (Least Recently Used) eviction
    L2: Global popular searches cache
          Maximum 20 entries
          Implements LFU (Least Frequently Used) eviction
        Primary Store: Complete movie database in memory
   
4. Handle search operations:
   
    Search by title
    Search by genre
    Search by release year
    Multiple filter combination
   
**Search Process:**

    Check L1 cache (user-specific)
    If not found, check L2 cache (global popular)
    If not found, search in Primary Store (main database)
    Update caches based on search result
    
**Bonus Requirement:**

    System should be able to view cache level wise cache hits i.e for L1, L2 and Primary Store and should display the total number of searches in the system.

**Commands**

ADD_MOVIE <id> <title> <genre> <year> <rating>
Example: ADD_MOVIE 1 "Inception" "Sci-Fi" 2010 9.5

Output: Movie 'Inception' added successfully

ADD_USER <id> <name> <preferred_genre>
Example: ADD_USER 1 "John" "Action"

Output: User 'John' added successfully

SEARCH <user_id> <search_type> <search_value>
Example: SEARCH 1 GENRE "Action"

Output: [List of movies with cache level indicator] Format: {movie_title} (Found in {cache_level})

SEARCH_MULTI <user_id> <genre> <year> <min_rating>
Example: SEARCH_MULTI 1 "Action" 2020 8.0

Output: [List of filtered movies with cache level indicator]

VIEW_CACHE_STATS <user_id>
Example: VIEW_CACHE_STATS 1

**Output:**

L1 Cache Hits: X
L2 Cache Hits: Y
Primary Store Hits: Z
Total Searches: N

CLEAR_CACHE <cache_level>
Example: CLEAR_CACHE L1

Output: L1 cache cleared successfully

**Guidelines:**
Input can be read from a file or Scanner or coded in a driver method. [No API and No UI]
Output can be written to a file or STDOUT. [No API]
Store all interim/output data in-memory data structures. The usage of databases is not allowed.
Language should be Java only.

**Expectations:**
The code should be demo-able and functionally correct
Proper exception handling for edge cases:
Invalid movie/user IDs
Duplicate entries
Invalid search parameters
Code should be modular and follow OOP principles
Efficient implementation of cache eviction policies
Clear separation of concerns between:
Data storage
Cache management
Search operations
Proper unit tests for critical components are good to have.
What are the ways we can approach this problem. Design Patterns we can use and why?
