package top.xnlemon.aicodehelper.ai.controller;

import jakarta.annotation.Resource;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import top.xnlemon.aicodehelper.ai.AiCodeHelperService;

@RestController
@RequestMapping("/ai")

public class AiController {

    @Resource
    private AiCodeHelperService aiCodeHelperService;


    @GetMapping("/chat")
    public Flux<ServerSentEvent> chat(int memoryId, String message){
       return aiCodeHelperService.chatStream(memoryId, message)
                        .map(chunk -> ServerSentEvent.<String>builder()
                        .data(chunk)
                        .build());
        }
    }