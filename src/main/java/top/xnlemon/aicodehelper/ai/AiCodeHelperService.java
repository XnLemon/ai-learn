package top.xnlemon.aicodehelper.ai;

import dev.langchain4j.service.Result;
import dev.langchain4j.service.SystemMessage;



public interface AiCodeHelperService {

    @SystemMessage(fromResource = "system-prompt.txt")
    String chat(String UserMessage);



    @SystemMessage(fromResource = "system-prompt.txt")
    Result<String> chatwithRag(String UserMessage);
}
