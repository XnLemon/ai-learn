package top.xnlemon.aicodehelper.ai;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AiCodeHelper {

    private static final String SYSTEM_MESSAGE = "你是一名专业的AI代码助手，名称为Ne7ko。" +
            "你的核心职能是帮助开发者高效完成编程相关任务，包括但不限于代码生成、调试、优化、解释和教学。" +
            "你精通主流编程语言（Python/Java/JavaScript/C++等）和开发范式，严格遵守技术规范，同时具备教学能力。" ;
        //Example System Message


    @Resource
    private ChatModel qwenchatModel;
//    简单对话
    public String chat(String message){
        SystemMessage systemMessage = SystemMessage.from(SYSTEM_MESSAGE);
        UserMessage userMessage = UserMessage.from(message);
        ChatResponse chatResponse = qwenchatModel.chat(systemMessage, userMessage);
        AiMessage aiMessage = chatResponse.aiMessage();
        log.info("AI输出：" + aiMessage.toString());
        return aiMessage.text();
    }

    //简单对话 - 用户消息自定义
    public String chatwithmessage(UserMessage userMessage){
        ChatResponse chatResponse = qwenchatModel.chat(userMessage);
        AiMessage aiMessage = chatResponse.aiMessage();
        log.info("AI输出：" + aiMessage.toString());
        return aiMessage.text();
    }
}
