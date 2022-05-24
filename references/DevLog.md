# 开发日志记录

## 一、项目构建

- MinecraftForge官网中下载对应Minecraft版本的Forge的MDK文件，将下载好的zip格式文件解压内容至项目目录下
- 命令行执行gradlew genEclipseRuns，显示"BUILD SUCCESS"则成功；显示失败则需要重试，考虑网络问题导致失败
- 修改.gitignore