## lab 1



1.发现dbworld网站上面的链接，每天都会增加，并且还会减少。

​	可能是时效的原因。后续再看看



2.用户登录，注册界面在ppt中99页附近可参考。

3.可考虑用application来实现页面交互。ppt110页附近

4. exception 115页



> pageScope：一个页面范围JSP各个隐式对象详解。
>
> requestScope：一次服务器请求范围
>
> sessionScope：一次会话范围
>
> applicationScope：一个应用服务器范围



5. 考虑lucene和NLP使用
   1. NLP内存占用过大，需要先用网页端尝试。

   2. 首要任务，找出时间，地点，主题

   3. 可增加description信息

      1.  可采用 ```<A HREF=\"(.*?)\">http://.*?</A>```

   4. 删除无关信息
      1.  比如，不是会议(phD申请)
      2.  若是paper投稿推荐.   关键词journal
      3.  call for proposal ,剔除
          1.  实验室，或者投期刊不用管
          2.  研讨会方案应该选用

   5.  可考虑时效性，比如to time截止，就清除信息或标记截止。

   6.  主题出现太少

   7.  call for papers应该可以作为会议的一个判断

   8.  此正则表达式可以 判断是否有web Page

       ```java
          String patternStr_ = "<TBODY>\\n<TR VALIGN=TOP>\\n<TD>.*?</TD>\\n<TD>(.*?)</TD>\\n<TD>.*?</TD>" +
                       "\\n<TD><A HREF=\"(.*?)\".*?</TD>\\n<TD>(.*?)</TD>\\n"
                       + "<TD>(<A.*?HREF=\"(.*?)\".*?web page</A>)?.*?</TD>\\n</TR></TBODY>";
       
       ```



>Lucene搜索过程
>Lucene的索引结构是文档(Document)形式的，下面简单介绍一下Lucene搜索的过程 
>（1）将文档传给分词组件(Tokenizer)，分词组件根据标点符号和停词将文档分成词元(Token)，并将标点符号和停词去掉。
>
>停词是指没有特别意思的词。英语的是指比如a、the等等单词
>
>文章1内容：Tom favorite fruit is apple.
>
>经过分词处理后，变成\[Tom\]\[favorite\]\[fruit\]\[apple\]
>
>（2）再将词元传给语言处理组件(Linguistic Processor)
>
>英语的单词经过语言处理组件处理后，字母变为小写，词元会变成最基本的词根形式，比如likes变成like
>
>经过分词处理后，变成\[tom\]\[favorite\]\[fruit\]\[apple\]
>
>（3） 然后得到的词元传给索引组件(Indexer)，索引组件处理得到索引结构，得到关键字、出现频率、出现位置分别作为词典文件（Term Dictionary）、频率文件（frequencies）和位置文件（positions）保存起来，然后通过二元搜索算法快速查找关键字
>--------------------- 
>https://blog.csdn.net/u014427391/article/details/80006401



1. nlp
> https://nlp.stanford.edu/software/regexner.html



2.开发自己的搜索引擎

​	35页，将大文档切成小的文档（暂不实现）

3.去掉+--- |||

```
+--------------------------------------------------------------------------------+
| 14th Track on Dependable, Adaptive, and Trustworthy Distributed Systems (DADS) |
| of the 34th ACM Symposium on Applied Computing (SAC'19)                        |
+--------------------------------------------------------------------------------+
```

