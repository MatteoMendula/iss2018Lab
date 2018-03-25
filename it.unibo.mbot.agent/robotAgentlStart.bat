echo off
 title robotAgentlStart.bat
 :: This line is a comment
 echo START ROBOT EXECUTOR
 cd C:\Didattica2018Run\it.unibo.ctxMbotExecutor.MainCtxMbotExecutor-1.0
 START /B  java -jar it.unibo.mbot-1.0.jar > ../logRobotExecutor.txt
 echo START ROBOT CONTROL
 cd C:\Didattica2018Run\it.unibo.ctxMbotAgent.MainCtxMbotAgent-1.0
 START /B java -jar it.unibo.mbot.agent-1.0.jar > ../logRobotAgent.txt
 cd C:\Didattica2018Work\iss2018Lab\it.unibo.mbot.agent