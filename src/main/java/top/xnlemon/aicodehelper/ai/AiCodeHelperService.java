package top.xnlemon.aicodehelper.ai;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.Result;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import reactor.core.publisher.Flux;


public interface AiCodeHelperService {

    @SystemMessage(fromResource = "system-prompt.txt")
    String chat(String UserMessage);



    @SystemMessage(fromResource = "system-prompt.txt")
    Result<String> chatwithRag(String UserMessage);

    @SystemMessage(fromResource = "system-prompt.txt")// 流式对话
    Flux<String> chatStream(@MemoryId int memoryId, @UserMessage String userMessage);
}
