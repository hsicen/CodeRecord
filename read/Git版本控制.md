#### 什么是Git
Git 是 Linux 发明者 Linus 开发的 款新时代的版本控制系统，  用于管理源代码

#### 为什么要管理源码
- 为了防止源代码丢失，本地代码丢失，我的服务器
- 大家同时开发项目相互不影响，需要发版本时可以将分别开发的代码合并在一起
- 代码开发到一半，发现上线的版本有BUG需要紧急修复随着功能越来越多，我们会需要知道哪些地方做了改，是谁做的

#### Git的安装
git是 个版本控制的 具，需要下载才能使 ，当然mac是 带就有啦。下载安装参考下  
- Mac：https://sourceforge.net/projects/git-osx-installer/
- Windows：https://git-for-windows.github.io/
- Linux：apt-get install git

#### 了解Git
工作区  暂存区  版本区三者之间的关系
![](图片暂无)

#### 常用命令
**git init**    
初始化仓库

**git status**  
查看状态    

**git add text1.txt**   
添加text1.txt到暂存区

**git add .**   
添加当前文件夹下所有文件到暂存区    

**git commit -m "提交说明"**    
将暂存区的文件提交到本地版本库  

**git log/reflog**  
查看提价历史    

**git clone 仓库地址**  
从服务器克隆项目到当前文件夹下  

**git branch**    
列出所有分支    

**git branch 分支名**   
创建分支    

**git checkout 分支名**    
切换到指定分支  

**git checkout -b 分支名**    
创建并切换到指定分支    

**git branch -d 分支名**    
删除指定分支    

**git checkout -- 文件名**  
撤销文件的修改，恢复到修改前的状态  

**git merge 分支名**    
合并指定分支到当前分支  

**git tag -a 标签名字 -m "相关说明"**   
新建标签    

**git tag**     
列出当前项目的所有标签  

**git tag -d 标签名**   
删除指定标签    

**git show 标签名**      
查看指定标签    

**git help**    
查看所有帮助    

**git help 指令名(如add)**      
查看指定指令说明    

##### 配置SSH Key
1. 在本机用以下指令生成一个SSH Key  ```ssh-keygen -t rsa -b 4096 -C "yourEmail@example.com"```
2. 在sshkey保存的目录下，复制id_rsa.pub文件中的内容到GitHub的setting -> SSH key中
3. 运行 ```ssh -T git@github.com``` 验证是否成功

##### 在GitHub上创建空项目，并在本地上传文件到空项目中
1. 在GitHub上创建一个项目，并复制其项目地址(用ssh格式的项目地址)
2. 在本地创建好并初始化好的项目文件夹下关联本地项目到远程的GitHub项目   
 ```git remote add origin 刚才复制的新建的GitHub项目地址 ```
3. ```git remote -v``` 查看该项目的远程仓库地址

**git push**    
推送项目到远程仓库  

**git push -u origin master**   
-u 表示指定 origin 为默认主机,向其推送 master，以后可以直接使用git push     

**git push resp branch**    
resp为远程仓库地址，branch为分支    

**git push origin master:master**    
将本地master分支推送到远程仓库origin的master分支上  
第一个master为本地master分支，第二个master为远程master分支  
如果本地分支名与远程分支名一致，那么只写其中一个分支名即可 **git push origin master**

**git push origin net:master**    
将本地net分支推送到远程master分支   

**git push origin   :net**      
删除远程仓库的net分支   

**git pull**    
从服务器更新代码    

**git diff 文件名**    
查看文件差异    

**git reset --hard HEAD^**    
回退到上一个版本（HEAD^^上上版本，HEAD~n往上n个版本）   

**git reset --hard commitId**    
回退到指定id的版本      

**git rm 文件名**       
删除文件    

**git stash**   
保存当前工作环境，清空工作区    

**git stash list**      
查看保存的工作环境      

**git stash apply 编号**    
恢复指定的工作环境（没有删除工作环境）  

**git stash drop 编号**     
删除指定的工作环境

**git stash pop**   
弹出指定工作区并删除    

##### 忽略文件
- 创建.gitignore文件，把要忽略的具体文件名或文件类型或某个文件夹下的文件写在该文件下面
这样可以忽略这些没有加入控制版本的文件
- .gitignore只能忽略那些原来没有被track的文件，如果某些文件已经被纳入了版本管理中，则修改.gitignore是无效的
- 当文件加入控制版本后，想要再忽略该文件有一下方法：
  1. 删除文件在本地和远程仓库：
     首先 ```rm 文件名``` 删除该文件，然后vim编辑.gitignore文件，添加该文件的忽略，最后add commit提交更改至仓库，push更改到远程仓库
  2. 不删除本地文件，只删除远程仓库的：
     首先 ```git rm --cached 文件名```，然后vim编辑.gitignore文件，添加该文件的忽略，最后add commit提交更改至仓库，push更改到远程仓库
  3. 只是本地不提交文件，远程仓库依然存在：```git update-index --assume-unchanged 文件名``` 即可

##### 指令配置别名
```git config --global alias.st status```   用st表示status  
```git config --global alias.unstage 'reset HEAD'```    
```git config --global alias.lg "log --color --graph --pretty=format:'%Cred%h%Creset -%C(yellow)%d%Creset %s %Cgreen(%cr) %C(bold blue)<%an>%Creset' --abbrev-commit"```   配置颜色      

- 配置Git的时候，加上--global是针对当前用户起作用的，如果不加，那只针对当前的仓库起作用
- 每个仓库的Git配置文件都放在.git/config文件中
- 别名就在[alias]后面，要删除别名，直接把对应的行删掉即可
- 而当前用户的Git配置文件放在用户主目录下的一个隐藏文件.gitconfig中
- 	配置别名也可以直接修改这个文件，如果改错了，可以删掉文件重新通过命令配置