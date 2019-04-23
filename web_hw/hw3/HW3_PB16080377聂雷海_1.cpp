

/*
    description:
        index.txt includes  .txt document's ID( contains English words)  and its relative path .
        for example.

                1	./1.txt
	
                2	./2.txt

                3	./3.txt

                4	./4.txt

*/


#include<iostream>
#include<fstream>
#include<cstdio>
#include<string.h>

#include<string>


#include<map>
#include<vector>
#include<algorithm>

using namespace std;
map< string,vector<int> > Index_Table;

int n   ;
void init()
{
    Index_Table.clear();
}

int main()
{
    freopen("index.txt","r",stdin);
    freopen("result.txt","w",stdout);
    //output file.

    init();

    int id;
    string file_path;
    while(cin>>id>>file_path)
    {
        ifstream fin(file_path.c_str());
        string s;
        while(fin>>s)
        {
            s.erase(std::remove(s.begin(),s.end(),','),s.end());
            s.erase(std::remove(s.begin(),s.end(),'.'),s.end());
            // clean the '.'  or '.'  in English words.
            if(s.empty()) continue;
            Index_Table[s].push_back(id);
            // put Document ID.
        }
        n++ ;
        // recode the number of documentation
    }
    map<string,vector<int> >::iterator map_pointer;
    map_pointer = Index_Table.begin();
    while(map_pointer!=Index_Table.end())
    {
        string tmp = map_pointer->first;
        cout<<tmp<<"    ";
        cout<<Index_Table[tmp].size()<<"        ";
        //  in every line,   the first is   the string , then output the number of words appearing . 
        //Finally, input every document  that the word appears.
        vector<int>::iterator i_t;
        for(int i = 1;i<=n;i++)
            {
               for(i_t=Index_Table[tmp].begin();i_t!=Index_Table[tmp].end(); i_t++)
                    if(*i_t==i)
                        {
                            cout<< i<<"  ";
                            break;
                        }

            }
        cout<<endl;
        map_pointer++;

    }
    fclose(stdin);
    fclose(stdout);
    return 0;

}







