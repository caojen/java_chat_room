# Java 聊天室

+ 本聊天室由三部分构成: 后端, 前端(命令行), 前端(GUI)

## 项目开发环境

### 后端/前端(命令行)
+ 后端/前端(命令行)在Linux和win10混合开发

### 前端(GUI)
+ GUI界面只在win10上通过测试. Linux未进行测试.

### 开发环境
```Bash
;Linux:
uname -a
>>> Linux DESKTOP-N57HQHR 4.4.0-17763-Microsoft #864-Microsoft Thu Nov 07 15:22:00 PST 2019 x86_64 x86_64 x86_64 GNU/Linux
```
```
Java -version
>>> openjdk version "1.8.0_222"
>>> OpenJDK Runtime Environment (build 1.8.0_222-8u222-b10-1ubuntu1~16.04.1-b10)
>>> OpenJDK 64-Bit Server VM (build 25.222-b10, mixed mode)
```
```
javac -version
>>> javac 1.8.0_222
```
## 编译及运行本项目

### 编译准备
+ 请在编译前,或重新编译前,将所有目录下所有class后缀删除
+ Windows:
```Bat
del *.class /s > nul
```

+ Linux:
```Bash
find  . -name  '*.class' -type  f -print -exec  rm -rf  {} \
```

### 编译后端

+ Windows 请使用下面命令:
```Bat
javac -cp sqlite.jar; Backend\Main.java
```

+ Linux 请使用下面命令:
```Bash
javac -cp sqlite.jar: Backend/Main.java
```

### 编译前端(命令行)和前端(GUI)

+ Windows 请使用下面命令:
```Bat
javac Frontend\Main.java
```
+ 或
```Bat
javac FGUI\Main.java
```

+ Linux 只支持命令行模式的前端:
```Bash
javac Frontend/Main.java
```

### 运行后端
+ Windows 请使用下面命令:
```Bat
java -cp sqlite.jar; Backend.Main
```

+ Linux 请使用下面命令:
```Bash
java -cp sqlite.jar: Backend.Main
```

### 运行前端(命令行)和前端(GUI)
+ Windows 请使用下面命令:
```Bat
java Frontend.Main
```
+ 或
```Bat
java FGUI.Main
```

+ Linux 只支持命令行模式的前端:
```Bash
java Frontend.Main
```

## 服务端配置
本项目已经配置在服务端,请在前端运行时输入以下地址:
```
http://39.98.244.149:8888/
```
服务器将保持维护直到2020年4月15日.

## GUI打包
+ GUI已经打包到FGUI.jar

## 前后端通信
+ 如果要使用远程服务端, 请运行程序后输入:
```
http://39.98.244.149:8888/
```
+ 如果要使用本地的服务端, 请编译并运行后端, 在运行前端后输入(默认):
```
http://localhost:8888/
```
+ 如果有已经运行服务端的其他服务器地址, 请在运行前端后按照上述格式输入ip地址+后端访问端口.
+ 注意: 请确保在最后输入一个'/'