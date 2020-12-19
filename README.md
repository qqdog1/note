# AWS 撞牆筆記

----------
## 原定練習計畫
利用lambda + dynamoDB 建一個linebot  
主要是為了體驗lambda

### 原定架構
line messaging api <-> api-gateway <-> lambda <-> dynamoDB

### 實作練習
[linebot + lambda 實作簡單echo service](https://github.com/qqdog1/aws-note/wiki/Linebot-&-AWS-lambda-&-Java)  
lambda call another lambda  
lambda + dynamoDB  

### 測試結果
在line上發送訊息後會應極慢，多數情況根本沒echo(截圖and log)  

----------
## 修改後練習計畫  
依上述方式建立完的linebot會掉訊息  
可能是因為建立linebot service的instance的時間太長?  
或是跟lambda的執行生命週期有關  
決定先把linebot service移到ec2上  
但為了練習lambda所以字串加工這段留在lambda  

### 修改後架構
line messaging api <-> ec2 <-> lambda <-> dynamoDB  

### 實作練習  
在ec2上建立linebot server  
ec2 call lambda  

### 遭遇問題  
ec2上的web server沒有憑證  
line要設定webhook一定要接https  

### 解決方式  
ec2上架nginx server申請憑證掛上去  
or  
load balance + ec2 + ACM  
但不管上述哪個方法都要申請一個domain  
新解法還沒試 open ssl + nginx  

最後workaround直接用api-gateway接ec2  
但會出現linebot解訊息掉header  
解法直接用pure spring boot + linebot sdk  
自己解訊息不管header硬做  

### 其他想試的  
github -> aws codeDeploy -> ec2  
github -> aws codeDeploy -> lambda  
