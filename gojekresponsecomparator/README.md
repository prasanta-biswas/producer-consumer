## response-comparator
response-comparator is a simple java library that accepts two file paths as argument. These file should
contain multiple http/https apis seperated by new line. The library compares the response of apis from first file to
the responses of the apis of second file.
                                                                                         .
```
File 1 : line 1: https://reqres.in/api/users/3 & File 2 : line 1: https://reqres.in/api/users/1,
File 1 : line 2: https://reqres.in/api/users/2 & File 2 : line 2: https://reqres.in/api/users/2, 
....and so on
```  
  
##### Approaches taken in consideration
1. First approach was pretty simple. It accept two file paths and runs a loop until there are no records in the small file. The loop reads 
the files line by line and checks if their response matches or not. This approach is easy and fulfills the criterion.
Now if the files are huge in size and contains millions of records, the loop might block the user for a very long time. So it needed an efficient way of reading the file and processing it. 
So it needed some rethinking.

2. Second approach implements the well known producer consumer logic. As we need an efficient way of reading the files and then process it for comparison. What if we can do this read and process operation independently.
Multi-thread comes into picture. The solution creates one producer thread responsible for reading the large files line by line and put them in a blocking queue, multiple consumer threads takes the values from the blocking queue and process them for comparison.
The blocking queue has fixed size of 100 and the number of consumer threads is 10. So a producer will be producing in the queue while the consumers will be consuming from the queue independently.
When the queue is full, producer wait until a free space is available and then produce. Similarly, when queue is empty, consumers will wait until the queue has at least one element.

So the second approach seems very promising in processing large files in efficient manner that takes less memory and time.

##### See it for real
Get inside the project root folder
`cd /path/to/gojekresponsecomparator`

Run the following maven command to build your project

`mvn clean package`

Once build process finishes, run the following command with two file paths as arguments

`java -cp target/response-comparator-*.jar gojek.responsecomparator.Main /path/to/file1 /path/to/file2`  # replace * with your jar version 

##### Sample output
```
https://reqres.in/api/users/3 not equals https://reqres.in/api/users/2
https://reqres.in/api/users/1 equals https://reqres.in/api/users/1
https://reqres.in/api/users?page=2 equals https://reqres.in/api/users?page=2
https://reqres.in/api/users?page=3 not equals https://reqres.in/api/users?page=1

------------------------------------
Comparison finished
------------------------------------
```
