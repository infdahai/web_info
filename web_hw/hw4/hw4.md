algorithm:

input :  adjacency matrix A of n*m and number of iterations h.

​		name_1 is a string array about 微博名字. name_2 is a string 	

​		array about user_name.

output:
​            authority and hub score vectors  x and y respectively 





HITS_algorithms(A,h,name_1,name_2):

​	create x[],y[]

//	x[] is a  m-dimension vector, y is a n-dimension vector

​	initial(x)

​	initial(y)

//then 	x=(1,1,...,1)$\in R^{m}$    y=(1,1,...,1)$\in R^{n}$

​	create C,D

//	C is m\*m dimension matrix, D is n\*n  dimension matrix.

​	$C = A^{T}A$

​	$D=AA^{T}$



​	while h--

​		for i = 1..m do

​			for j = 1..m do

​				x[i] += C[i][j]*x[j]$

​		for i =1..n do

​			for j =1..n do

​				y[i] +=D [ i ][ j ]*y[j]

​	map <int,string> weibo(y,name_1),users(x,name_2)

​	sort(x[1..m])

​	sort(y[1..n])

​	for i = 1.. m do 

​		print users[i].second

​	for j=1 .. n do

​		print weibo[j].second



​	return x,y	



