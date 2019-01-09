# web_info



~~now ,just include lab_1 ----->  search engine~~


  We will finish search-engine lab before ddl(~~2019.1.5~~ it's updated to 2019.1.10 now).  But it's still a tough project.
  
  Our Team:
          nlh && zta    
  
  **Let's try our best!!**

## 一些必要的介绍:

 1. nlp的使用:
 
    仅仅用来提取地点，时间。对于topics，十分头铁地用正则怒干(可想而知，能有几个生出来，情况也是惨不忍睹)
 
 2. 爬虫:
    
    目前大概把基本要素dbworld上都爬到本地。   之后会使用定时更新的方法。
    
 3. 前端:
    
    太不懂jsp了啊.
    
 4. 文件预处理：
    
    丧心病狂地用正则乱搞一通，只为得到看起来像点样的 md_[\\d+].txt 文件
    
 5. Lucene操作:
    
    这部分，把存入data\\目录下文件读取，存入Lucene中。 搜索则是提取相应内容。 可以使用lucene内置排序.

## some existing problems:
  
 1. auto-update:
    
    这里是使用Time来管理，实现固定时间循环访问函数。  
    但对于Lucene索引文件，由于涉及到write_lock的问题，尚未解决。 对此，打算纸糊过去。  
    
 2. extract topics:
    
    用的是正则(匹配较少)，但实际上用nlpCore更好。这方面可以参考正则的策略， 结合nlp parser(property: noun)以及 text 分析一下就行。
   
 ## the final:
 
  不管怎么说，完结撒花吧:tada:(我和我的队友都**心力交瘁** :pray: )
    
  本实验参考cjp，zevin等代码，非常感谢他们
 
