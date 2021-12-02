# 兔叽的发布与使用说明
-----
##下载:
[点击此处](https://github.com/MikuNyanya/live2dPet_windows/releases/tag/v0.0.1)，直接解压可用
<br/>

###使用说明:
1.配置参数(application.properties)      
2.启动RabbitBotRE.bat        


###配置文件说明(请善用Ctrl+F搜索):
    bot.name.cn
群机器人中文名称，不过不是只能起中文，名字随便起        
如果遇到发出消息是乱码问题，可以把名字转为Unicode        
比如`bot.name.cn=兔叽`，使用的时候是乱码，可写成`bot.name.cn=\u5154\u53fd`

    bot.name.en
群机器人英文名称，也是随便起，并不是只能写英文     

    bot.version
版本号，标识用，非重要参数，可不填       

    bot.account
q号，重要参数必须填写，填写作为群机器人的q号     

    bot.pwd
重要参数必须填写，上面那个q号的密码      

    bot.master
最高权限q号，可不填      
最高权限的账号，可以不受权限限制使用指令        
也可以直接通过指令修改兔叽配置信息       

    bot.admin       
二级权限q号，可不填      
计划是权限在普通用户和最高权限之间，但由于实际没啥需求，这个功能也就没太多关注

    file.path.config
配置文件存放路径，重要参数，已预先配置好，建议不更改     

    file.path.data
资源文件路径，重要参数，已预先配置好，建议不要更改

    proxy.address
代理地址，可不填        
获取P站图片等功能可能直连无法获取，所以需要代理    
如果使用代理，则需要配置该参数     

    proxy.prot
代理端口，同上

    pixiv.cookie
Pixiv的cookie，可不填        
如不填写则只会影响P站相关功能的使用     
比如 `P站日榜` `搜图` `pid指令` `色图` 等       
cookie的获取：
就是很普通的Pixiv的网页cookie，我这里以chrome浏览器为例提供其中一种方法     
1.首先登录`pixiv`       
2.然后随便打开一个作品，并F12打开浏览器开发工具界面      
3.打开选项卡`Network-Fetch/XHR`，这里可以看到网络请求        
4.刷新页面      
5.开发工具界面会刷出一堆网络请求记录，随便点开一个(如果你不理解这些东西，则点开`roots?illust_id`字样开头的那一行)       
在右边的窗口中找到`Headers`选项卡下的`Request Headers`，展开这个，有一行名叫`cookie`的参数，把它后面的值复制下来，填写到 `pixiv.cookie` 中        
注意复制完全，cookie一般都很长的，前后注意不要有空格       
如图：     
![ ](https://github.com/MikuNyanya/RabbitBot_RE/blob/master/data/images/help/pixiv_cookie.png)

    saucenao.key
saucenao搜图网站的key，可不填        
如不填写则只会影响 `搜图` 功能       
saucenao官方提供了搜图接口，所以这里的key是官方下发的，需要去注册个账号开通
saucenao网站传送门，从网站下面有个 `Account` 选项开始        
登录进去以后，在个人信息界面左边菜单，有个`api`，点进去可以获取`api key`
这个界面也是官方提供的api文档，有兴趣可以看看    
如图：
![ ](https://github.com/MikuNyanya/RabbitBot_RE/blob/master/data/images/help/pixiv_cookie.png)







