



#include<iostream>
#include<fstream>
#include<cstdio>
#include<string.h>

#include<string>
#include<sstream>

#include<map>
#include<vector>
#include<algorithm>

using namespace std;

map<string ,vector<int> > Index_Table;

void init()
{
    Index_Table.clear();
}
const int n=4 ; 
//the number of document.
static int pointer= 1;
int hash_func( )
{
    return pointer++ ;
}


int main()
{

        int id;
        string s1 ;

    for(int i=1;i<=n;i++)
        {   
           
            id =i;
            stringstream ss ;
            ss<<i;
             s1=ss.str();
            s1=s1+".txt";
            const char* path = s1.c_str();


            ifstream fin(path);
            string s ;
            while(fin >> s)
            {
                s.erase(std::remove(s.begin(),s.end(),','),s.end());
                 s.erase(std::remove(s.begin(),s.end(),'.'),s.end());
                 if(s.empty())  continue;
                 Index_Table[s].push_back(id) ;
 /*                
                if(Index_Table[s].empty())
                {
                   Index_Table[s].push_back(1); 
                }
                else
                {
                    Index_Table[s][0]++;
                    bool ok =false;
                    for(int i = 1 ;i<Index_Table[s].size();i++)
                    {
                        if(id==Index_Table[s][i])
                            {
                                ok = true;
                                break;
                            }
                    }
                    if (!ok)
                        {
                            Index_Table[s].push_back(id);
                        }
    ///  use the "ok"  symbol  to add  the Document ID in sequence.
    ///  And just one time.
    */
                
            }
            fin.close();
            fin.clear();
        
    }
//    freopen("dict.txt","w",stdout);  
    ofstream fout("dict.txt");
    map<string,vector <int> > :: iterator map_pointer;
    map_pointer=Index_Table.begin();
    while(map_pointer != Index_Table.end())
    {
        string tmp = map_pointer->first;
        fout<<tmp<<"    ";
        fout<<hash_func()<<"  ";
        fout<<endl;
        map_pointer ++;
    }
 //       fclose(stdout);
        fout.close();
        fout.clear();

 //       freopen("list.txt","w",stdout);
     fout.open("list.txt");
     map_pointer=Index_Table.begin();
     vector<int> tmp;
     while(map_pointer!=Index_Table.end())
     {
  //      string tmp = map_pointer->first;
         tmp =map_pointer->second;
        fout<<tmp.size()<<"   " ;
        for(int i=1;i<=n;i++)
            {
                    bool ok = false;
                    for(int j=0;j<tmp.size();j++)
                    {
                        if (i==tmp[j])
                            {
                                ok=true;
                                break ;
                            }
                    }
                    if(ok==true)
                    fout<<i<<"  ";
            }

        fout<<endl ;
        map_pointer++;
     }

        fout.close();
        fout.clear();


        return 0;
}