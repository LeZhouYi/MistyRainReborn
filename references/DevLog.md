# 开发日志记录

## 一、项目构建
- MinecraftForge官网中下载对应Minecraft版本的Forge的MDK文件，将下载好的zip格式文件解压内容至项目目录下
- 命令行执行gradlew genEclipseRuns，显示"BUILD SUCCESS"则成功；显示失败则需要重试，考虑网络问题导致失败
- 修改.gitignore，忽略规则中，下文会覆盖上文声明的忽略规则

## 二、文件结构组织
- common: 存储通用的方法和类(与Config有关)
  - core: 存储模组的核心方法和类(与Config无关)
- command: 存储指令
  - argument: 存储指令相关的参数判断
    - MRArgSeason: 季节相关
  - MRCmdTime: mrtime <set|info> [<season|month|solarterm>] [<"spring"|"jan"|"spring_begin">]
  - MRCmdHandler: 指令注册

## 三、核心功能
- 时间(time):
  - 季节(MRSeason):Enum=>四季，简单的枚举
  - 月份(MRMonth):Enum=>月份，简单的枚举
  - 节气(MRSolarTerm):Enum=>节气，简单的枚举
  - 时点(MRTimeDot):Class=>记录当前时间点
    - 记录当前的时间点，并在每次访问时更新，若时差产生时点变化，则执行相应属性的更新
    - 不设置线程安全，短时间产生的变化不大

## 四、功能实现
- 命令(Command):
    - 注册命令: 参考mcforge doc事件相关
    - 命令功能实现: 参考net.minecraft.command.impl.TimeCommand
      - 获取命令参数: 参考net.minecraft.command.arguments.TimeArgument