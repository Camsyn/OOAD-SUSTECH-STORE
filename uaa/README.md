###### 1.1 client1 流程



此流程与标准的 oauth2 流程相比，多了两次授权请求，按照正常 oauth2 流程，在第一次请求授权时如果未登录就重定向到登录页，但用前后端分离后，返回了授权接口在前端跳转，此时多了一次授权请求，在登录成功后又再次请求授权接口，这样做的原因是登录成功后，client2 再请求时无法获取到登录成功后的 SSO-SESSION 这个 cookie，从而导致需要再登录，我认为拿不到 cookie 的原因是在不同域名下请求另一个域名的接口是无法取到 cookie 的，所以只能在浏览器上跳转，授权中心根据 isRedirect 这个参数来判断是重定向到登录页还是返回 json 未登录。

![img](https://www.yuque.com/api/filetransfer/images?url=https%3A%2F%2Fupload-images.jianshu.io%2Fupload_images%2F8428991-4d8706948e620011.png&sign=0707212aa879f02867bef452ddbba421954cf1a5fa19a66df4270e236c16f7ac)

###### 1.2 client2 流程

![img](https://www.yuque.com/api/filetransfer/images?url=https%3A%2F%2Fupload-images.jianshu.io%2Fupload_images%2F8428991-093aafe6de7d4a78.png&sign=7cf5d5869afcee705e399fba016306ca73252ab4539c0e24f15a50afaff92c0d)



### 另一张图

![img](https://images2015.cnblogs.com/blog/797930/201612/797930-20161203152650974-276822362.png)