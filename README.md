+ Frontend:
  + 前端处理:
    + Main - 程序入口
    + Frontend - 处理用户界面以及业务逻辑
    + Http - 发送和接受Http请求
    + Helper - 从Configuation获取信息,并且提供http接口给console,并发送http请求
    + Console - 处理控制台\[多线程\]
    + Log - 保存日志,目前只需要当发送http请求时写入日志(由Http调用)
    + Configuration - 加载常量并读取本地用户配置? \[文件读写\]
    + UI - 图形界面?

+ Backend:
  + 后端处理
    + Main - 程序入口
    + Urls - 监听get/post/put/delete请求
    + Views - (被Urls调用,)处理监听事项
    + Models - 用户身份模块\[继承\] \(这里的所有用户方法都应该直接调用control\)
    + Control - 提供读/写数据库接口

+ 前后端通信规则:
  + 后端只需要处理get/post请求
  + 后端必须相应键值status
  + 前后端通信时所有value都使用URLEncoder.encode, URLDecoder.decode进行转换.

+ 模型(与数据库交接):
  + 超类: User, 数据库保存信息: username(作为唯一标识)/密码
    + 管理员: Admin, 数据库保存信息: null
      + 权限管理: 普通用户的所有权限(不允许创建房间和拥有房间)+删除某房间+删除某房间的成员
    + 普通用户: Participant, 数据库保存信息: *姓名*/*年龄*/邮箱/号码/最大创建房间数
      + 权限管理: 创建房间/加入或退出某房间(如果作为房主,则需要先转让房主权限再退出,或解散)/获取进入的房间的所有成员名
    + 身份冲突: 不允许.即不允许一个User同时充当Admin和Participant
  + 类: Room, 数据库保存信息: roomId(作为唯一标识)/创建时间/人数/房主username/参与者participants' usernames[]/房间聊天记录



开发环境: Ubuntu
```
java -version
>>>
openjdk version "1.8.0_222"
OpenJDK Runtime Environment (build 1.8.0_222-8u222-b10-1ubuntu1~16.04.1-b10)
OpenJDK 64-Bit Server VM (build 25.222-b10, mixed mode)
```
```
javac -version
>>>
javac 1.8.0_222
```

编译运行
```
make build
```
运行前端
```
make runfrontend
```
运行后端
```
make runbackend
```